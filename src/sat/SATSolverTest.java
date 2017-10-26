package sat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.*;


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
		boolean hasP = false;
		Clause[] clauses = null;
		int clausePointer = 0;
		try {
			Scanner sc = new Scanner(new File(filename));
			String input = null;
			
			while (sc.hasNextLine()){
				input = sc.nextLine();
				// skip if empty or is a comment
				if (input.isEmpty() || input.charAt(0) == 'c')
					continue;
				if (input.charAt(0) == 'p'){
					String[] temp = input.split(" ");
					clauses = new Clause[Integer.parseInt(temp[temp.length - 1])];
					// ignore P because it is not used in the solver..
					hasP = true;
					break;
				}
					
			}
			
			if (hasP){
				sc.useDelimiter(" 0");
				while (sc.hasNext()){
					String[] values = sc.next().split(" ");
//					if (values.length > 2){
//						throw new IOException("The .cnf file has more than 2 literals in a clause.");
//					}
					Literal[] literals = new Literal[values.length];
					for (int i = 0; i < literals.length; i++){
						String temp = values[i].trim();
						literals[i] = temp.charAt(0) == '-' ? NegLiteral.make(temp.substring(1)) : PosLiteral.make(temp);
					}
					clauses[clausePointer] = makeCl(literals);
					System.out.println(clauses[clausePointer]);
					clausePointer++;
				}
			} else {
				throw new IOException("invalid format for CNF. no P found.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
        // SATSolver.solve(makeFm(clauses));
	}
	
	public static void main(String[] args){
		if (args.length > 0){
	        String fileName = args[0];
	        SATSolverTest r = new SATSolverTest();
	        r.readFile(fileName);
	        r.testSATSolver1();
	        r.testSATSolver2();
		} else {
			System.out.println("Supply the location of the file. Syntax: solver.jar file");
			System.exit(1);
		}
	}
	
    public void testSATSolver1(){
    	// (a v b)
    	Environment e = SATSolver.solve(makeFm(makeCl(a,b))	);
    	System.out.println(e);	
    	assertTrue( "one of the literals should be set to true",
    			Bool.TRUE == e.get(a.getVariable())  
    			|| Bool.TRUE == e.get(b.getVariable())	);
    }
    
    
    public void testSATSolver2(){
    	// (~a)
    	Environment e = SATSolver.solve(makeFm(makeCl(na)));
    	System.out.println(e);
    	assertEquals( Bool.FALSE, e.get(na.getVariable()));
   
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