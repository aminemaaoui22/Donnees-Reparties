package linda.applications;

import java.util.Arrays;
import java.util.List;

import linda.Linda;
import linda.Linda.eventMode;
import linda.Linda.eventTiming;
import linda.Tuple;
import linda.shm.CentralizedLinda;

public class Searcher extends Thread {
	
	private Linda linda;
	private Tuple requete;
	private List<Tuple> donnees;
	
	public Searcher(Tuple m, List<Tuple> d) {
		requete = m;
		donnees = d;
	}
	
	static int distance_Levenshtein(String str1, String str2) {

		if (str1.isEmpty()) {
		return str2.length();
		}
		
		
		if (str2.isEmpty())  {
		return str1.length();
		}
		
		int replace = distance_Levenshtein(
		str1.substring(1), str2.substring(1))
		+ NumOfReplacement(str1.charAt(0),str2.charAt(0));
		
		int insert = distance_Levenshtein(
		str1, str2.substring(1))+ 1;
		
		int delete = distance_Levenshtein(
		str1.substring(1), str2)+ 1;
		
		return minm_edits(replace, insert, delete);
		}
		
		static int NumOfReplacement(char c1, char c2)
		{
		return c1 == c2 ? 0 : 1;
		}
		
		static int minm_edits(int... nums)
		{
		return Arrays.stream(nums).min().orElse(
		Integer.MAX_VALUE);
		}
		
		public void run() {
//			linda = new CentralizedLinda(donnees);
//			Tuple resultat = linda.tryTake(motif);
//			if (resultat != null) {
//				return resultat;
//			} else {
//				System.out.println("Pas de tuple correspondant");
//				return null;
//			}
			boolean res = false;
			for(Tuple t: donnees) {
				int distance = distance_Levenshtein(t.toString(),requete.toString());
				if (distance==0) {
					res = true;
					break;
				}
			}
			if (res == true)
				 System.out.println("mot existe");
			else {
				System.out.println("eventRegister");
				linda = new CentralizedLinda(donnees);
				SearcherCallback callback = new SearcherCallback();
				linda.eventRegister(eventMode.READ, eventTiming.FUTURE, requete, callback);
				 System.out.println("mot n'existe pas");
			}
				
			
		}

}
