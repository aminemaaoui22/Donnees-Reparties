package linda.applications;

public class Premier {

	public static void main(String[] args) {
		
		int i,j;
		int borne_sup=Integer.parseInt(args[0]);
	    boolean []tableau_premiers = new boolean [borne_sup-1];
	    
	    for (i=0;i<=tableau_premiers.length-1;i++) 
	    {
	        tableau_premiers[i]=true;
	    }
	    
	    for (i=2;i<=borne_sup;i++) 
	    {
	    	if (tableau_premiers[i-2]==true){ 
		        j=i+1;
		        while (j<=borne_sup) 
	 	        {
			        if ((j%i)==0) tableau_premiers[j-2]=false;
			        j++;
		        }
		   	    	System.out.println(i+" ");
	    	}
	    }
	}

}
