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
	
	private static List<Tuple> tuples = new ArrayList<Tuple>();
	
	private static List<Event> readE = new ArrayList<Event>(); 
	private static List<Event> takeE = new ArrayList<Event>();
	
	ReentrantLock moniteur = new java.util.concurrent.locks.ReentrantLock();
	
	Condition condition = moniteur.newCondition();
	
    public CentralizedLinda() {
    	
    	// TO BE COMPLETED
    }

    @Override
	public void write(Tuple t) {
		tuples.add(t);
		
	}
	
	// A compléter: take et read sont bloquantes

	@Override
	// on renvoie le 1er tuple correspondant au motif
	public Tuple take(Tuple template) {
		moniteur.lock();
		boolean find = false;
		int i = 0;
		while(!find && i< tuples.size()) {
			if (tuples.get(i).matches(template)) {
				Tuple elt = tuples.get(i);
				tuples.remove(i);
				moniteur.unlock();
				return elt;
			}
			
			i++;
		}
		// Sortie du while sans return = aucun tuple ne correspond
		moniteur.unlock();
		return null;
	}

	@Override
	public Tuple read(Tuple template) {
		moniteur.lock();
		boolean find = false;
		int i = 0;
		while(!find && i< tuples.size()) {
			if (tuples.get(i).matches(template)) {
				moniteur.unlock();
				return tuples.get(i);
			}
			
			i++;
		}
		// Sortie du while sans return = aucun tuple ne correspond
		moniteur.unlock();
		return null;
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
					Tuple t = read(template);
					if (t != null) 
						callback.call(t);
					else 
						readE.add(new Event(template, callback));
				}
				else if (mode == eventMode.TAKE) {
					Tuple t = take(template);
					if (t != null) 
						callback.call(t);
					else takeE.add(new Event(template, callback));
				}
		} else if (timing == eventTiming.FUTURE) {
			if (mode == eventMode.READ) {
				readE.add(new Event(template, callback));
			} else {
				takeE.add(new Event(template, callback));
			}
		}
		
		
	}

	@Override
	public void debug(String prefix) {
		// TODO Auto-generated method stub
		
	}
    
    // TO BE COMPLETED

}
