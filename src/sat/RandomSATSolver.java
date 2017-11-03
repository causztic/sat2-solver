package sat;
import sat.formula.*;
import sat.env.*;
import java.util.*;
import immutable.*;

public class RandomSATSolver {
    Environment env = new Environment();

    protected ImList<Clause> clauses;
    private int numberOfVariables;
    private List<Clause> unsatClauses = new ArrayList<>();

    public RandomSATSolver(Formula formula){
        clauses = formula.getClauses();
        for (Clause clause: clauses){
        	numberOfVariables += clause.size();
        }
    }
    public Environment solve(){
    	long outerCounter = 0;
    	// repeat outside log2n times to reinitialize environment randomly
    	while (outerCounter < (Math.log(numberOfVariables / Math.log(2))) ){
	    	Environment env = new Environment();
	 
	    	// set initial environment
	    	for (Clause clause: clauses){
	    		Iterator<Literal> it = clause.iterator();
	    		while (it.hasNext()){
	    			Literal lit = it.next();
	    			env = env.put(lit.getVariable(), new Random().nextBoolean() ? Bool.TRUE : Bool.FALSE);
	    		}
	    	}
	    	
	    	long counter = 0;
	    	// repeat inside for at most 2n^2 
	    	while (counter < (2 * Math.pow(numberOfVariables, 2)) ){
	    		
	    		unsatClauses.clear(); // reset unsat clauses.
	    		
	    		for (Clause clause: clauses){
	    			// loop through the clauses and determine which one is satisfiable.
	    			Clause testClause = clause;
	    			Iterator<Literal> it = testClause.iterator();
	    			while (it.hasNext()){
	    				// set the clause based on literal in environment
	    				Literal tempLit = it.next();
	    				Bool value = env.get(tempLit.getVariable());
	    				// if tempLit is NegLiteral and value is TRUE, flip to PosLiteral.
	    				// if tempLit is NegLiteral and value is FALSE, do nothing.
	    				// if tempLit is PosLiteral and value is TRUE, do nothing.
	    				// if tempLit is PosLiteral and value is FALSE, flip to NegLiteral.
	    				if ((tempLit instanceof NegLiteral && value.equals(Bool.TRUE)) || 
	    					(tempLit instanceof PosLiteral && value.equals(Bool.FALSE))	){
	    					tempLit = tempLit.getNegation();
	    				}
	    				if (testClause != null)
	    					testClause = testClause.reduce(tempLit);
	    			}
	    			if (testClause != null && testClause.isEmpty()){
	    				// unsatisfiable, add the original clause to the arraylist
	    				unsatClauses.add(clause);
	    			}
	    		}
	    		
	    		if (unsatClauses.size() > 0){
	    			// there are unsatisfied clauses.
	    			// get an random clause in the arraylist 
	    			// get a random literal from the clause to flip.
	    			Clause chosen = unsatClauses.get(new Random().nextInt(unsatClauses.size()));
	    			Iterator<Literal> literalIt = chosen.iterator();
	    			Literal tempLit = literalIt.next();
	    			while (literalIt.hasNext()){
	    				Literal nextLit = literalIt.next();
	    				if (new Random().nextBoolean()) {
	    					// choose randomly
	    					tempLit = nextLit;
	    				}
	    			}
	    			// flip the Bool in env.
	    			env = env.put(tempLit.getVariable(), env.get(tempLit.getVariable()).not());
	    		} else {
	    			return env;
	    		}
	    		
	    		counter++;
	    	}
	    	outerCounter++;
    	}	
	    return null;
    }
}
