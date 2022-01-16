package linda.server;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

import linda.Callback;
import linda.Linda;
import linda.Linda.eventMode;
import linda.Linda.eventTiming;
import linda.shm.CentralizedLinda;
import linda.Tuple;

public class RemoteLindaImpl extends UnicastRemoteObject implements RemoteLinda {

	private static final long serialVersionUID = 1L;
	
	private Linda linda;
	
	public RemoteLindaImpl() throws RemoteException {
		super();
		this.linda = new CentralizedLinda();
	}

	/**
	 * 
	 */

	@Override
	public void write(Tuple t) throws RemoteException {
		linda.write(t);

	}

	@Override
	public Tuple take(Tuple template) throws RemoteException {
		// TODO Auto-generated method stub
		return linda.take(template);
	}

	@Override
	public Tuple read(Tuple template) throws RemoteException {
		// TODO Auto-generated method stub
		return linda.read(template);
	}

	@Override
	public Tuple tryTake(Tuple template) throws RemoteException {
		// TODO Auto-generated method stub
		return linda.tryTake(template);
	}

	@Override
	public Tuple tryRead(Tuple template) throws RemoteException {
		// TODO Auto-generated method stub
		return linda.tryRead(template);
	}

	@Override
	public Collection<Tuple> takeAll(Tuple template) throws RemoteException {
		// TODO Auto-generated method stub
		return linda.takeAll(template);
	}

	@Override
	public Collection<Tuple> readAll(Tuple template) throws RemoteException {
		// TODO Auto-generated method stub
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
	
	public static void main(String[] args) {
		
		// le constructeur new Integer is deprecated, 
		//donc on affecte le numéro de port statiquement
			//Integer I = new Integer(args[0]); 
		int port = 1099;
			
		try {
			//naming service 
			Registry registry = LocateRegistry.createRegistry(port);
			// Créer une instance du serveur
			RemoteLinda obj = new RemoteLindaImpl();
			
			// Naming service
			String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/LindaRMI";
			System.out.println("Objet enregistré, url : " + url);
			Naming.rebind(url, obj);
			System.out.println("le serveur est lancé");
		} catch(Exception ex) {
			System.out.println("Une erreur est survenue! ");
			ex.printStackTrace();
		}
		
	}

}
