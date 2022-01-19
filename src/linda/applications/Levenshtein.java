package linda.applications;
import java.util.*;

public class Levenshtein {
	  
	    static int compute_Levenshtein_distance(String str1,
	                                            String str2) {
	        // If str1 is empty, all
	        // characters of str2 are
	        // inserted into str1, which is
	        // of the only possible method of
	        // conversion with minimum
	        // operations.
	  
	        if (str1.isEmpty())
	        {
	            return str2.length();
	        }
	  
	        // If str2 is empty, all
	        // characters of str1 are
	        // removed, which is the
	        // only possible
	        // method of conversion with minimum
	        // operations.
	  
	        if (str2.isEmpty()) 
	        {
	            return str1.length();
	        }
	  
	        // calculate the number of distinct characters to be
	        // replaced in str1
	        // by recursively traversing each substring
	  
	        int replace = compute_Levenshtein_distance(
	              str1.substring(1), str2.substring(1))
	              + NumOfReplacement(str1.charAt(0),str2.charAt(0));
	  
	        // calculate the number of insertions in str1
	        // recursively
	        int insert = compute_Levenshtein_distance(
	                         str1, str2.substring(1))+ 1;
	  
	        // calculate the number of deletions in str1
	        // recursively
	        int delete = compute_Levenshtein_distance(
	                         str1.substring(1), str2)+ 1;
	  
	        // returns minimum of three operatoins
	        
	        return minm_edits(replace, insert, delete);
	    }
	  
	    static int NumOfReplacement(char c1, char c2)
	    {
	        // check for distinct characters
	        // in str1 and str2
	        
	        return c1 == c2 ? 0 : 1;
	    }
	  
	    static int minm_edits(int... nums)
	    {
	        // receives the count of different
	        // operations performed and returns the
	        // minimum value among them.
	        
	        return Arrays.stream(nums).min().orElse(
	            Integer.MAX_VALUE);
	    }
	  
	    // Driver Code
	    public static void main(String args[])
	    {
	        String s1 = "glomax";
	        String s2 = "folmax";
	  
	        System.out.println(compute_Levenshtein_distance(s1, s2));
	    }

}
