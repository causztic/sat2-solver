package sat;

import sat.env.*;
import sat.formula.*;


public class SATSolverTest {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();

	
	public void testTopological(FormulaReader reader, Boolean dangerous){
		Graph graph = reader.getGraph();
		System.out.println("SAT solver with topological sorting");
		long started = System.nanoTime(); 
		try {
			graph.topologicalSort(dangerous);
		} catch(Exception e){
			System.out.println(e);
		} finally {
			long time = System.nanoTime();
			long timeTaken= time - started;
			System.out.println("Time:" + timeTaken/1_000_000.0 + "ms");
			System.out.println(graph.getEnvironment());
		}
	}
	public void testDPLL(Formula f){
		System.out.println("SAT solver starts!!!");
		long started = System.nanoTime(); 
		Environment e = SATSolver.solve(f);
		long time = System.nanoTime();
		long timeTaken= time - started;
		System.out.println("Time:" + timeTaken/1_000_000.0 + "ms");
		if (e == null){
			System.out.println("UNSATISFIABLE.");
		} else 
			System.out.println(e);
	}
	
	/*
	 * Main method for the SAT Solver Test.
	 * @param	arg[0]	the file name relative to the directory	
	 * @param	arg[1]	set to "dangerous" (no quotes) to run the sat solver without satisfiability check if it is 2SAT.
	 */
	public static void main(String[] args){
		if (args.length > 0){
	        String fileName = args[0];
	        SATSolverTest r = new SATSolverTest();
	        FormulaReader reader = new FormulaReader();
	        Formula f = reader.readFile(fileName);
	        if (reader.is2SAT()){
	        	boolean dangerous = false;
	        	if (args.length >= 2 && args[1].equals("dangerous"))
	        		dangerous = true;
	        	r.testTopological(reader, dangerous);
	        } else {
	        	r.testDPLL(f);
	        }
		} else {
			System.out.println("Supply the location of the file. Syntax: solver.jar file");
			System.exit(1);
		}
	}
}