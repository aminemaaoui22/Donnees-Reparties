package linda.applications;

import java.util.ArrayList;
import java.util.List;

import linda.Linda;
import linda.Tuple;

public class Manager {
	
	private Linda linda;
	private Tuple motif;
	private List<Tuple> donnees;
	
	public Manager(Tuple m, List<Tuple> d) {
		motif = m;
		donnees = d;
		Searcher searcher = new Searcher(motif, donnees);
		searcher.start();
	}
	
		
		
		public static void main(String[] args) {
			
			List<Tuple> d = new ArrayList<Tuple>();
			d.add(new Tuple("bonjour"));
			d.add(new Tuple("enseeiht"));
			d.add(new Tuple("bonsoir"));
			 Tuple m = new Tuple("bonjour");
			 Manager manager = new Manager(m, d);
			 
			 Tuple t = new Tuple("hello");
			 Manager mg = new Manager(t, d);
			
		}

}
 