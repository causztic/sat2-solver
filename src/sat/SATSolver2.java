package sat;

import sat.env.*;
import sat.formula.*;
import immutable.*;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver2 {
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     *
     * @return an environment for which the problem evaluates to Bool.TRUE, or
     * null if no such environment exists.
     */
    public static Environment solve(Formula formula) {
        ImList<Clause> clauselist = formula.getClauses();
        Environment env = new Environment();
        return solve(clauselist, env);
    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     *
     * @param clauses formula in conjunctive normal form
     * @param env     assignment of some or all variables in clauses to true or
     *                false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     * or null if no such environment exists.
     */

    private static Environment solve(ImList<Clause> clauses, Environment env) {
        int smallestClauseL = 100;
        Clause smallest = new Clause();
        if (clauses.isEmpty()){
            return env;
        }
        for (Clause i:clauses){
            if (i.isEmpty()){
                return null;
            }
            else {
                if (i.size() < smallestClauseL){
                    smallestClauseL = i.size();
                    if (i.isUnit()){
                        smallest = i;
                        Literal lit = smallest.chooseLiteral();
                        Environment newenv;
                        ImList<Clause> newclauses;
                        if (lit instanceof NegLiteral){
                            newenv = env.putFalse(lit.getVariable());
                            newclauses = substitute(clauses, lit);
                        }
                        else {
                            newenv = env.putTrue(lit.getVariable());
                            newclauses = substitute(clauses, lit);
                        }
                        Environment eunit = solve(newclauses, newenv);
                        return eunit;
                    }
                }
            }
        }
        for (Clause j:clauses){
            if (j.size() == smallestClauseL){
                smallest = j;
            }
        }
        Literal lit = smallest.chooseLiteral();
        if (lit instanceof NegLiteral) {
            lit = lit.getNegation();
        }

        Environment solve = solve(substitute(clauses, lit), env.put(lit.getVariable(), Bool.TRUE));
        if (solve == null)
            return solve(substitute(clauses, lit.getNegation()), env.put(lit.getVariable(), Bool.FALSE));
        else
            return solve;
    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     *
     * @param clauses , a list of clauses
     * @param l       , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */

    private static ImList<Clause> substitute(ImList<Clause> clauses,
                                             Literal l) {
        Clause j;
        for (Clause i : clauses) {
            if (i.contains(l) || i.contains(l.getNegation())) {
                j = i.reduce(l);
                clauses = clauses.remove(i);
                if (j != null) {
                    clauses = clauses.add(j);
                }
            }
        }
        return clauses;
    }
}