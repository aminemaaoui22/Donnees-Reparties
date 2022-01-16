package linda.shm;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import linda.Callback;
import linda.Tuple;

public class RTCallback implements Callback {
	
	// Quand un read ou un take est bloqué, on crée un eventRegistry, on a donc besoin
	// d'un callback qui permet de débloquer ces opérations.
	
	ReentrantLock lock;
	Condition condition;
	Tuple resultat;
	
	public RTCallback(ReentrantLock l, Condition c) {
		lock = l;
		condition = c;
	}

	@Override
	public void call(Tuple t) {
		resultat = t;
		lock.lock();
		condition.signal();
		lock.unlock();
	}

	public Tuple retourResultat() {
		return resultat;
	}
}
