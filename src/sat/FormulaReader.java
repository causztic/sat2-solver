package sat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.NegLiteral;
import sat.formula.PosLiteral;

public class FormulaReader {
	private boolean is2SAT;
	private Graph graph;
	
	public FormulaReader(){
		is2SAT = true;
		graph = new Graph();
	}
	

	public boolean is2SAT() {
		return is2SAT;
	}
	
	public Graph getGraph(){
		return graph;
	}
	
	
	public Formula readFile(String filename){
		System.out.println("Reading file: " + filename);
		boolean hasP = false;
		Clause[] clauses = null;
		int clausePointer = 0;
		Scanner sc = null;

		try {
			sc = new Scanner(new File(filename));
			String input = null;
			
			while (sc.hasNextLine()){
				input = sc.nextLine();
				// skip if empty or is a comment
				if (input.isEmpty() || input.charAt(0) == 'c')
					continue;
				if (input.charAt(0) == 'p'){
					String[] temp = input.split(" ");
					clauses = new Clause[Integer.parseInt(temp[temp.length - 1])];
					clausePointer = Integer.parseInt(temp[temp.length - 1]) - 1;
					hasP = true;
					break;
				}
					
			}
			
			if (hasP){
				sc.useDelimiter(" 0");
				while (sc.hasNext()){
					String next = sc.next();
					String[] values = next.trim().split(" ");
					if (values.length > 2){
						// throw new IOException("The .cnf file has more than 2 literals in a clause.");
						is2SAT = false;
					}
					Literal[] literals = new Literal[values.length];
					for (int i = 0; i < literals.length; i++){
						String temp = values[i].trim();
						if (temp.length() > 0)
							literals[i] = temp.charAt(0) == '-' ? NegLiteral.make(temp.substring(1)) : PosLiteral.make(temp);
					}
					
					if (literals[0] != null){
						clauses[clausePointer] = makeCl(literals);
						// if there is a negation of itself in the same clause
						if (clauses[clausePointer] == null)
							throw new IOException("UNSATISFIABLE");
						
						// based on the current clause, construct the directed graph.
						Iterator<Literal> it;
						it = clauses[clausePointer].iterator();
						if (it.hasNext()){
							Literal firstItem = it.next();
							Literal secondItem = it.hasNext() ? it.next() : firstItem;
							graph.addEdge(firstItem.getNegation(), secondItem);
							graph.addEdge(secondItem.getNegation(), firstItem);
						}
						clausePointer--;
					}
					

				}
			} else {
				throw new IOException("invalid format for CNF. no P found.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			sc.close();
		}
		return makeFm(clauses);
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
