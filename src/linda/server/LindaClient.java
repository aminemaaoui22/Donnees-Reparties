package linda.server;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Collection;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

/** Client part of a client/server implementation of Linda.
 * It implements the Linda interface and propagates everything to the server it is connected to.
 * */
public class LindaClient implements Linda {
	
	private Linda linda;
	
    /** Initializes the Linda implementation.
     *  @param serverURI the URI of the server, e.g. "rmi://localhost:4000/LindaServer" or "//localhost:4000/LindaServer".
     */
    public LindaClient(String serverURI) {
        // TO BE COMPLETED
    	
    	// Serveur URI à compléter
    	
    	try {
			// Obtenir le stub serveur
			RemoteLinda obj = (RemoteLinda) Naming.lookup(serverURI);
		} catch(Exception e) {
			System.out.println("Erreur client");
			e.printStackTrace();
		}
    	
    }

	@Override
	public void write(Tuple t) {
		linda.write(t);
		
	}

	@Override
	public Tuple take(Tuple template) {
		return linda.take(template);
	}

	@Override
	public Tuple read(Tuple template) {
		return linda.read(template);
	}

	@Override
	public Tuple tryTake(Tuple template) {
		return linda.tryTake(template);
	}

	@Override
	public Tuple tryRead(Tuple template) {
		return linda.tryRead(template);
	}

	@Override
	public Collection<Tuple> takeAll(Tuple template) {
		return linda.takeAll(template);
	}

	@Override
	public Collection<Tuple> readAll(Tuple template) {
		return linda.readAll(template);
	}

	@Override
	public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
		linda.eventRegister(mode, timing, template, callback);
		
	}

	@Override
	public void debug(String prefix) {
		linda.debug(prefix);
		
	}
    
    // TO BE COMPLETED
	
	public static void main(String[] args) {
		
		LindaClient client = new LindaClient("rmi://127.0.0.1:4000/LindaRMI");
	}
    
    

}
