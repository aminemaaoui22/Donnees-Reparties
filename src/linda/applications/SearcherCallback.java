package linda.applications;

import linda.Callback;
import linda.Tuple;

public class SearcherCallback implements Callback {

	@Override
	public void call(Tuple t) {
		System.out.println("le mot " + t.toString() + " existe");
		
	}

}
