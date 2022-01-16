package linda.shm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


import linda.Callback;
import linda.Linda;
import linda.Tuple;

/** Shared memory implementation of Linda. */
public class CentralizedLinda implements Linda {
	
	private static List<Tuple> tuples;
	
	private static List<Event> readE; 
	private static List<Event> takeE;
	
	
    public CentralizedLinda() {
    	
    	// TO BE COMPLETED
    	tuples = new ArrayList<Tuple>();
    	readE = new ArrayList<Event>();
    	takeE = new ArrayList<Event>();
    }

    @Override
	public void write(Tuple t) {
		tuples.add(t);
		
	}
	
	// A compléter: take et read sont bloquantes

	@Override	
	public Tuple take(Tuple template) {
		Tuple t = tryTake(template);
		if (t != null) {
			//si on a un tuple correspondant au motif, on le retourne
			// il est déjà retiré de l'espace des tuples via tryTake
			return t;
		} else {
			ReentrantLock moniteur = new ReentrantLock();
			Condition condition = moniteur.newCondition();
			RTCallback callback = new RTCallback(moniteur, condition);
			eventRegister(eventMode.READ, eventTiming.FUTURE, template, callback);
			try {
				moniteur.lock();
				condition.await();
				moniteur.unlock();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return callback.retourResultat();
		}
	}
	
	@Override
	public Tuple read(Tuple template) {
		Tuple t = tryRead(template);
		if (t != null) {
			return t;
		} else {
			ReentrantLock moniteur = new ReentrantLock();
			Condition condition = moniteur.newCondition();
			RTCallback callback = new RTCallback(moniteur, condition);
			eventRegister(eventMode.READ, eventTiming.FUTURE, template, callback);
			try {
				moniteur.lock();
				condition.await();
				moniteur.unlock();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return callback.retourResultat();
		}
	}

	@Override
	// take non bloquante
	public Tuple tryTake(Tuple template) {
		boolean find = false;
		int i = 0;
		while(!find && i< tuples.size()) {
			if (tuples.get(i).matches(template)) {
				Tuple elt = tuples.get(i);
				tuples.remove(i);
				return elt;
			}
			
			i++;
		}
		// Sortie du while sans return = aucun tuple ne correspond
		return null;
	}

	@Override
	// read non bloquante
	public Tuple tryRead(Tuple template) {
		boolean find = false;
		int i = 0;
		while(!find && i< tuples.size()) {
			if (tuples.get(i).matches(template)) {
				return tuples.get(i);
			}
			
			i++;
		}
		// Sortie du while sans return = aucun tuple ne correspond
		return null;
	}

	@Override
	public Collection<Tuple> takeAll(Tuple template) {
		ArrayList<Tuple> resultat = new ArrayList<>();
		for (int i=0; i<tuples.size(); i++) {
			if(tuples.get(i).matches(template)) {
				resultat.add(tuples.get(i));
				tuples.remove(i);
			}
		}
		return resultat;
	}

	@Override
	public Collection<Tuple> readAll(Tuple template) {
		ArrayList<Tuple> resultat = new ArrayList<>();
		for(Tuple t: tuples) {
			if (t.matches(template))
				resultat.add(t);
		}
		return resultat;
	}

	
	// callback invoqué dans un write dans le contexte du thread déposeur,
	// il ne doit jamais se bloquer
	
	@Override
	public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
		
		if (timing == eventTiming.IMMEDIATE) {
				if (mode == eventMode.READ) {
					// Read, on chercher si le tuple appartient à l'espace.
					// Sinon, on l'ajoute aux processus en attente.
					// On utilise tryRead car Read est bloquante.
					Tuple t = tryRead(template);
					if (t != null) 
						callback.call(t);
					else 
						readE.add(new Event(template, callback));
				}
				else if (mode == eventMode.TAKE) {
					// Take, et même raisonnement que Read.
					Tuple t = tryTake(template);
					if (t != null) 
						callback.call(t);
					else takeE.add(new Event(template, callback));
				}
		} else if (timing == eventTiming.FUTURE) {
			// Si abonnement au futur, pas besoin de chercher si tuple existe
			if (mode == eventMode.READ) {
				readE.add(new Event(template, callback));
			} else {
				takeE.add(new Event(template, callback));
			}
		}
		
		
	}

	@Override
	public void debug(String prefix) {
		// Print the tuples in tuplespace
		String affichage = prefix;
		for (Tuple t: tuples) {
			affichage += t.toString();
		}
		System.out.println(affichage);
		
	}
    
    // TO BE COMPLETED

}
