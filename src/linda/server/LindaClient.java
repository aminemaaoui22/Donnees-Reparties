package linda.server;

import java.util.ArrayList;
import java.util.Collection;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

/** Client part of a client/server implementation of Linda.
 * It implements the Linda interface and propagates everything to the server it is connected to.
 * */
public class LindaClient implements Linda {
	
	private static ArrayList<Tuple> tuples = new ArrayList();
	
    /** Initializes the Linda implementation.
     *  @param serverURI the URI of the server, e.g. "rmi://localhost:4000/LindaServer" or "//localhost:4000/LindaServer".
     */
    public LindaClient(String serverURI) {
        // TO BE COMPLETED
    	
    	// Serveur URI à compléter
    	
    }

	@Override
	public void write(Tuple t) {
		tuples.add(t);
		
	}
	
	// A compléter: take et read sont bloquantes

	@Override
	// on renvoie le 1er tuple correspondant au motif
	public Tuple take(Tuple template) {
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
	public Tuple read(Tuple template) {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(String prefix) {
		// TODO Auto-generated method stub
		
	}
    
    // TO BE COMPLETED
    
    

}
