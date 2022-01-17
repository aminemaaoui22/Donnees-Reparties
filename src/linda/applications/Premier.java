package linda.applications;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Premier {

	public static void main(String[] args) throws Exception {
		
		ExecutorService poule=Executors.newFixedThreadPool(2);
		
		int rest = Integer.parseInt(args[0]);
		int debut = 1;
		List<Future<String>> taches = new ArrayList<Future<String>>();
		
		while (rest > 0) {
			if (rest >= 10) {
				Future<String> f = poule.submit(new CalculPremier(debut, debut + 9));
				taches.add(f);
				debut += 10;
				rest -= 10;
			} else {
				// reste < 10
				Future<String> f = poule.submit(new CalculPremier(debut, debut + rest - 1));
				taches.add(f);
				rest = 0;
			}
		}
		
		// Récupération résultats
		
		String res = "";
		
		for(Future<String> j : taches) {
			res += j.get();
		}
		
		System.out.println("Les nombres premiers inférieurs à " + args[0] + " sont " + res);
	}

}
