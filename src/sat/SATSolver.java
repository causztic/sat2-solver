package sat;

import immutable.EmptyImList;
import immutable.ImList;
import sat.env.Bool;
import sat.env.Environment;
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
    	Environment env = new Environment();
    	
    	for (Clause clause: formula.getClauses()){
    		if (clause.isUnit()){
    			// single clause, set it to be true always.
    			Literal literal = clause.chooseLiteral();
    			env = env.put(literal.getVariable(), literal.getClass() == PosLiteral.class ? Bool.TRUE : Bool.FALSE);
    		}
    			
    	}
    	return solve(formula.getClauses(), env);
        // throw new RuntimeException("not yet implemented.");
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
    	Literal literal = clauses.first().chooseLiteral();
    	clauses = substitute(clauses, literal);
    	if (clauses.isEmpty()){
    		env = env.put(literal.getVariable(), literal.getClass() == PosLiteral.class ? Bool.TRUE : Bool.FALSE);
    		return env;
    	} else
    		return null;
        //throw new RuntimeException("not yet implemented.");
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
    private static ImList<Clause> substitute(ImList<Clause> clauses,
            Literal l) {
    	ImList<Clause> c = new EmptyImList<>();
    	for (Clause clause: clauses){
    		c.add(clause.reduce(l));
    	}
        return c;
        //throw new RuntimeException("not yet implemented.");
    }

}
