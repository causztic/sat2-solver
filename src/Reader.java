import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Reader {
	private String filename;
	
	public Reader(String filename){
		this.filename = filename;
	}
	
	public Result readFile(){
		String line = null;
        boolean hasP = false;
        boolean[] variables = null;
        List<int[]> clauses = null;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(filename);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
            	// charAt is faster than startsWith
            	
            	// skip comments and empty lines
                if (line.trim().isEmpty() || line.charAt(0) == 'c')
                	continue;
                
                // if there is no p, set the p parameters
                if (!hasP){ 
                	if (line.charAt(0) == 'p'){
                		hasP = true;
                		String[] parameters = line.split("\\s+");
                		if (parameters[1].equals("cnf")){
                			variables = new boolean[Integer.parseInt(parameters[2])];
                			clauses = new ArrayList<int[]>(Integer.parseInt(parameters[3]));
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
                	String[] temp = line.split("\\s+");
                	int[] i = new int[temp.length-1];
                	for (int idx = 0; idx < i.length; idx++){
                		i[idx] = Integer.parseInt(temp[idx]);
                	}
                	clauses.add(i);
                }
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
        return new Result(variables, clauses);
	}
	
	public static void main(String[] args){
		if (args.length > 0){
	        String fileName = args[0];
	        Result r = new Reader(fileName).readFile();
	        System.out.println(Arrays.toString(r.getVariables()));
	        for(int[] clause: r.getClauses()){
		        System.out.println(Arrays.toString(clause));
	        }

		} else {
			System.out.println("Supply the location of the file. Syntax: solver.jar file");
			System.exit(1);
		}
	}
}
