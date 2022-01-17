package linda.applications;

public class SeqPremier {

	public static void main(String[] args) {
		
		int i,j;
		int sup=Integer.parseInt(args[0]);
	    boolean []premiers = new boolean [sup-1];
	    
	    for (i=0;i<=premiers.length-1;i++) 
	    {
	    	premiers[i]=true;
	    }
	    
	    for (i=2;i<=sup;i++) 
	    {
	    	if (premiers[i-2]==true){ 
		        j=i+1;
		        while (j<=sup)
	 	        {
			        if ((j%i)==0) premiers[j-2]=false;
			        j++;
		        }
		   	    	System.out.println(i+" ");
	    	}
	    }

	}

}
