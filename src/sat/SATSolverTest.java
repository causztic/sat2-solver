package sat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import sat.env.*;
import sat.formula.*;


public class SATSolverTest {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();



	
	// TODO: add the main method that reads the .cnf file and calls SATSolver.solve to determine the satisfiability
	public void readFile(String filename){
		String line = null;
        boolean hasP = false;
        boolean[] variables = null;
        Clause[] clauses = null;
        int clausePointer = 0;
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
                			clauses = new Clause[Integer.parseInt(parameters[3])];
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
                	String[] temp = line.substring(0, line.length() - 2).split("\\s+");
                	Literal[] lit = new Literal[temp.length];
                	for (int i = 0; i < temp.length; i++){
                		int test = Integer.parseInt(temp[i]);
                		lit[i] = test > 0 ? PosLiteral.make(temp[i]) : NegLiteral.make(temp[i].substring(1));
                	}
                	clauses[clausePointer] = makeCl(lit);
                	clausePointer++;
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
        
        SATSolver.solve(makeFm(clauses));
	}
	
	public static void main(String[] args){
		if (args.length > 0){
	        String fileName = args[0];
	        SATSolverTest r = new SATSolverTest();
	        r.readFile(fileName);
		} else {
			System.out.println("Supply the location of the file. Syntax: solver.jar file");
			System.exit(1);
		}
	}
	
    public void testSATSolver1(){
    	// (a v b)
    	Environment e = SATSolver.solve(makeFm(makeCl(a,b))	);
/*
    	assertTrue( "one of the literals should be set to true",
    			Bool.TRUE == e.get(a.getVariable())  
    			|| Bool.TRUE == e.get(b.getVariable())	);
    	
*/    	
    }
    
    
    public void testSATSolver2(){
    	// (~a)
    	Environment e = SATSolver.solve(makeFm(makeCl(na)));
/*
    	assertEquals( Bool.FALSE, e.get(na.getVariable()));
*/    	
    }
    
    private static Formula makeFm(Clause... e) {
        Formula f = new Formula();
        for (Clause c : e) {
            f = f.addClause(c);
        }
        return f;
    }
    
    private static Clause makeCl(Literal... e) {
        Clause c = new Clause();
        for (Literal l : e) {
            c = c.add(l);
        }
        return c;
    }
    
    
    
}