import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	
	public static void main(String[] args){
		if (args.length > 0){
	        String fileName = args[0];
	        String line = null;
            boolean hasP = false;
            boolean[] variables;
            boolean[] clauses;
	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = new FileReader(fileName);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            
	            while((line = bufferedReader.readLine()) != null) {
	            	// charAt is faster than startsWith
	            	
	            	// skip comments
	                if (line.charAt(0) == 'c')
	                	continue;
	                
	                // if there is no p, set the p parameters
	                if (!hasP){ 
	                	if (line.charAt(0) == 'p'){
	                		hasP = true;
	                		String[] parameters = line.split("\\s+");
	                		if (parameters[1].equals("cnf")){
	                			variables = new boolean[Integer.parseInt(parameters[2])];
	                			clauses = new boolean[Integer.parseInt(parameters[3])];
	                		} else {
	            	        	bufferedReader.close();
	                			throw new IOException("Invalid CNF file. Problem FORMAT must be CNF.");
	                		}
	                	}
	                } else if (line.charAt(0) == 'p'){
	                	// if there is p, and p happens again
	    	        	bufferedReader.close();
	                	throw new IOException("Invalid file. Duplicate p found");
	                } else {
	                	// if there is p, set the variables
	                	
	                }
	                
	                System.out.println(line);
	            }   

	            bufferedReader.close();         
	        }
	        catch (NumberFormatException nfe){
	        	nfe.printStackTrace();
	        }
	        catch(FileNotFoundException ex) {
	        	ex.printStackTrace();
	        }
	        catch(IOException ex) {
	        	ex.printStackTrace();
	        }
		} else {
			System.out.println("Supply the location of the file. Syntax: solver.jar file");
			System.exit(1);
		}
	}
}
