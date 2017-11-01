package sat;

import java.util.*;

import sat.env.Variable;
import sat.formula.*;

/*
 * Creates a directed graph, topologically sorted with DFS.
 * During with, a path based SCC is done to determine if the vertex is in the same
 * strongly connected component.
 * 
 * Runtime complexity: O(V+E), V = vertices, E = edges
 * Space complexity: O(V)
 * 
 */

public class Graph {

	private HashMap<Literal, LinkedList<Literal>> vertices = new HashMap<>();
	private HashMap<Variable, Boolean> environment = new HashMap<>();
	private Set<Literal> assignedComponents = new HashSet<>();
	private int C = 0;
	// Stack S contains all the vertices that have not yet been assigned 
	// to a strongly connected component.
	private Stack<Literal> S = new Stack<>();
	// Stack P contains vertices that have not yet been determined to belong to 
	// different strongly connected components from each other. 
	private Stack<Literal> P = new Stack<>();
	private HashMap<Literal, Integer> preOrderNumbers = new HashMap<>();
	
	// returns the environment.
	HashMap<Variable, Boolean> getEnvironment(){
		return environment;
	}
	
	// add an implication to the graph.
	public void addEdge(Literal v, Literal w){
		// link the literal v to the literal w.
		LinkedList<Literal> edges = vertices.get(v);
		if (edges == null)
			// instantiate the linkedList if it doesn't exist.
			edges = new LinkedList<Literal>();
		edges.add(w);
		vertices.put(v, edges);
	}
	

	public void topologicalSort(Literal l, Stack<Literal> stack) throws Exception{
		// mark the node as visited.
		//visited.put(l, true);
		// set preorder number and increment
		preOrderNumbers.put(l, C++);
		// push v into S and P
		S.push(l);
		P.push(l);
		
		Literal adjacentLiteral;
		
		// Recur for all the vertices adjacent to this vertex.
		if (vertices.get(l) != null){
			// it has 2 units
			Iterator<Literal> it = vertices.get(l).iterator();
			// If the preorder number of w has not yet been assigned, recursively search w;
			while (it.hasNext()){
				adjacentLiteral = it.next();
				if (!preOrderNumbers.containsKey(adjacentLiteral)){
					//Boolean isVisited = visited.get(adjacentLiteral);
					//if (isVisited == null || !isVisited)
					topologicalSort(adjacentLiteral, stack);	
				} else if (!assignedComponents.contains(adjacentLiteral)){
					// if w has not yet been assigned to a strongly connected component
					while (preOrderNumbers.get(P.peek()) > preOrderNumbers.get(adjacentLiteral)){
						// Repeatedly pop vertices from P until the top element of P 
						// has a preorder number less than or equal to the preorder number of w.
						P.pop();
					}
				}
			}
		}
		if (l.equals(P.peek())){
			// Pop vertices from S until v has been popped, 
			// and assign the popped vertices to a new component.
			Set<Variable> component = new HashSet<>();
			Literal popped = null;
			do {
				// while adding, systematically check if the variable is inside.
				// if it is already inside, return with an error as the boolean
				// equation is not satisfiable.
				popped = S.pop();
				if (component.contains(popped.getVariable()))
					throw new Exception("Unsatisfiable.");
				component.add(popped.getVariable());
				assignedComponents.add(popped);
			} while(popped != l);
			// Pop v from P.
			P.pop();
		}
		// Push current vertex to stack which stores result
		stack.push(l);
	}

	/*
	 * Calls TopologicalSort recursively with DFS to visit every implication.
	 */
	public void topologicalSort() throws Exception{
		Stack<Literal> stack = new Stack<>();
		//HashMap<Literal, Boolean> visited = new HashMap<>();
		// Call the recursive helper function to store
		// Topological Sort starting from all vertices
		// one by one
		for (Literal key: vertices.keySet()){
			//Boolean isVisited = visited.get(key);
			topologicalSort(key, stack);
			//if (isVisited == null || !isVisited)
			//	topologicalSortUtil(key, visited, stack);
		}
		
		while (!stack.isEmpty()){
			Literal literal = stack.pop();
			Variable v = literal.getVariable();
			if (environment.get(v) == null && environment.get(literal.getNegation().getVariable()) == null)
				environment.put(v, !(literal instanceof PosLiteral));
			//results.add(stack.pop());		
		}
	}
}
