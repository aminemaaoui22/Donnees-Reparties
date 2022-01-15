package linda.server;

import java.util.ArrayList;
import java.util.Collection;

import linda.Callback;
import linda.Linda;
import linda.Tuple;
import ILinda;

/** Client part of a client/server implementation of Linda.
 * It implements the Linda interface and propagates everything to the server it is connected to.
 * */
public class LindaClient implements Linda {
	
	private static ArrayList<Tuple> tuples = new ArrayList();
	private ILinda linda;
	
    /** Initializes the Linda implementation.
     *  @param serverURI the URI of the server, e.g. "rmi://localhost:4000/LindaServer" or "//localhost:4000/LindaServer".
     */
    public LindaClient(String serverURI) {
        // TO BE COMPLETED
        System.out.println("The server is : " + serverURI);
        
    	// Serveur URI à compléter
		this.linda = (LindaRMI) Naming.lookup(serverURI);
    	
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
		// On cree un thread pour eviter le bloque 

		new Thread() {
            @Override
            public void run() {

                System.out.println("Doing Callback: " + template);
				//Get the tuple

                try {

					//creation of the tuple for geting
                    Tuple tuple = linda.waitEvent(mode, timing, template);
                    
                    callback.call(tuple);

                } catch (RemoteException ex) {
                    Logger.getLogger(LindaClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Callback on tuple " + template);
            }

        }.start();
		
	}

	@Override
	public void debug(String prefix) {
		// TODO Auto-generated method stub
		this.linda.debug(prefix);
	}
    
    // TO BE COMPLETED
    

	 /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		String URL;
		
		URL = "//" + InetAddress.getLocalHost().getHostName() + ":7000/server";
		LindaClient client = new LindaClient(URL);
    }
    

}
