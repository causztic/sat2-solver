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
		ImList<Clause> clauses = formula.getClauses();
		for (Clause clause: clauses) {
			if (clause.isUnit()) {
				// single clause, set it to be true always.
				Literal literal = clause.chooseLiteral();
				clauses = substitute(formula.getClauses(), literal);
				env = env.put(literal.getVariable(), literal.getClass() == PosLiteral.class ? Bool.TRUE : Bool.FALSE);
			}
		}
		if (clauses.isEmpty()) {
			System.out.println("Solved without recursion..");
			return env;
		} else {
			return solve(clauses, env);
		}

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
		System.out.print("Setting variable: ");
		System.out.print(literal + " ");
		System.out.println(literal.getVariable());
		env = env.put(literal.getVariable(), literal.getClass() == PosLiteral.class ? Bool.TRUE : Bool.FALSE);
		clauses = substitute(clauses, literal);
		if (clauses.isEmpty()) {
			return env;
		} else {
			return solve(clauses, env);
		}
		// throw new RuntimeException("not yet implemented.");
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
		ImList<Clause> c = new EmptyImList<Clause>();
		System.out.print("Substituing: ");
		System.out.println(clauses);
		for (Clause clause : clauses) {
			clause = clause.reduce(l);
			if (clause != null && !clause.isEmpty())
				c = c.add(clause);
		}
		System.out.print("Substituted to: ");
		System.out.println(c);
		return c;
		// throw new RuntimeException("not yet implemented.");
	}

}
