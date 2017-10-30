package sat;

import immutable.EmptyImList;
import immutable.ImList;
import sat.env.Environment;
import sat.env.Variable;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.NegLiteral;
import sat.formula.PosLiteral;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {
	/**
	 * Solve the problem using a simple version of DPLL with backtracking and
	 * unit propagation. The returned environment binds literals of class
	 * bool.Variable rather than the special literals used in clausification of
	 * class clausal.Literal, so that clients can more readily use it.
	 * 
	 * @return an environment for which the problem evaluates to Bool.TRUE, or
	 *         null if no such environment exists.
	 */
	public static Environment solve(Formula formula) {
		return solve(formula.getClauses(), new Environment());
	}

	/**
	 * Takes a partial assignment of variables to values, and recursively
	 * searches for a complete satisfying assignment.
	 * 
	 * @param clauses
	 *            formula in conjunctive normal form
	 * @param env
	 *            assignment of some or all variables in clauses to true or
	 *            false values.
	 * @return an environment for which all the clauses evaluate to Bool.TRUE,
	 *         or null if no such environment exists.
	 */
	private static Environment solve(ImList<Clause> clauses, Environment env) {
        if (clauses.isEmpty())
            return env;

        Clause minClause = null;
        
        
        for (Clause c : clauses) {
            if (c.size() == 1) {
            	// if it is 1, break immediately.
                minClause = c;
                break;
            }
            if (c.isEmpty()) // an empty clause is an unsolvable clause
                return null;
        }
        
        if (minClause == null)
        	minClause = clauses.first();

        Literal l = minClause.chooseLiteral();
        if (minClause.isUnit()) {
        	// if minimum size is 1, use it and recursively solve.
        	Environment newEnv = null;
        	ImList<Clause> newClauses = new EmptyImList<Clause>();
      	   if (l instanceof PosLiteral) {
      		   newEnv = env.putTrue(l.getVariable());
      		   newClauses = substitute(clauses,l);
      	   } else {
      		   newEnv = env.putFalse(l.getVariable());
      		   newClauses = substitute(clauses,l);
      	   }
  		   return solve(newClauses, newEnv);
        } else {
      	   // If minimum sized clause has size greater than 1, choose an arbitrary literal; assign it to true and then recurse
      	   
      	   Literal literal = minClause.chooseLiteral();
      	   Variable var = literal.getVariable();
      	   Environment newEnv = null;
      	   
      	   if (literal instanceof PosLiteral){
      		   newEnv = env.putTrue(var);
      	   } else {
      		   newEnv = env.putFalse(var);
      	   }
      	   
      	   ImList<Clause> newClauses = substitute(clauses, literal);
      	   Environment solution = solve(newClauses, newEnv);
      	   if (solution == null) {
      		   // If solution fails, back propagate
      		   literal = literal.getNegation();
      		   
          	   if (literal instanceof PosLiteral){
          		   newEnv = env.putTrue(var);
          	   } else {
          		   newEnv = env.putFalse(var);
          	   }
          	   
      		   newClauses = substitute(clauses, literal);
      		   return solve(newClauses, newEnv);
      	   }
      	   return solution;
        }
	}

	/**
	 * given a clause list and literal, produce a new list resulting from
	 * setting that literal to true
	 * 
	 * @param clauses
	 *            , a list of clauses
	 * @param l
	 *            , a literal to set to true
	 * @return a new list of clauses resulting from setting l to true
	 */
	private static ImList<Clause> substitute(ImList<Clause> clauses, Literal l) {
        ImList<Clause> result = new EmptyImList<Clause>();
        for (Clause c : clauses) {
            Clause temp = c.reduce(l);
            if (temp != null) {
                result = result.add(temp);
            }
        }
        return result;
		// throw new RuntimeException("not yet implemented.");
	}

}
