package linda.applications;

import java.util.concurrent.Callable;

public class CalculPremier implements Callable<String> {
	
	private int indiceDebut;
	private int indiceFin;
	
	CalculPremier(int deb, int fin) {
		indiceDebut = deb;
		indiceFin = fin;
	}
	
	public boolean isPremier(int nbr) {
		int reste;
		  boolean flag = true;
		        
		  if (nbr != 2) {
			  for(int i=2; i <= nbr/2; i++)
			  {
			     //nombre est divisible par lui-meme
			     reste = nbr%i;
			            
			     //si le reste est 0, alors arrete la boucle. Sinon continuer la boucle
			     if(reste == 0)
			     {
			        flag = false;
			        break;
			     }
			  }
		  }
		  return flag;
	}

	@Override
	public String call() throws Exception {
		int i,j;
		int debut = indiceDebut;
		int sup=indiceFin;
		String resultat="";
	    boolean []premiers = new boolean [sup-1];
	    System.out.println("debut = " + debut +" fin= " + sup);
	    for (i=debut;i<=premiers.length-1;i++) 
	    {
	    	premiers[i]=true;
	    }
	    
	    for (i=debut+1;i<=sup;i++) 
	    {
	    	if (isPremier(i-2)){ 
		        j=i+1;
		        while (j<=sup)
	 	        {
			        if ((j%i)==0) premiers[j-2]=false;
			        j++;
		        }
		   	    	resultat = resultat + i +" ";
	    	}
	    }
		return resultat;
	}

}
