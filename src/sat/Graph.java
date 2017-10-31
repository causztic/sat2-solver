package sat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import sat.formula.*;

public class Graph {

	private HashMap<Literal, LinkedList<Literal>> vertices = new HashMap<>();
	private ArrayList<Literal> results = new ArrayList<>();
	
	ArrayList<Literal> getResults(){
		return results;
	}
	void addEdge(Literal v, Literal w){
		// link the literal v to the literal w.
		LinkedList<Literal> edges = vertices.get(v);
		if (edges == null)
			// instantiate the linkedList if it doesn't exist.
			edges = new LinkedList<Literal>();
		edges.add(w);
		vertices.put(v, edges);
	}
	
	void topologicalSortUtil(Literal l, HashMap<Literal, Boolean> visited, Stack<Literal> stack){
		// mark the node as visited.
		visited.put(l, true);
		Literal adjacentLiteral;
		
		// Recur for all the vertices adjacent to this vertex.
		if (vertices.get(l) != null){
			// it has 2 units
			Iterator<Literal> it = vertices.get(l).iterator();
			while (it.hasNext()){
				adjacentLiteral = it.next();
				Boolean isVisited = visited.get(adjacentLiteral);
				if (isVisited == null || !isVisited)
					topologicalSortUtil(adjacentLiteral, visited, stack);	
			}
		} // otherwise it is a trival solution

		// Push current vertex to stack which stores result
		stack.push(l);
	}

	// The function to do Topological Sort. It uses
	// recursive topologicalSortUtil()
	void topologicalSort(){
		Stack<Literal> stack = new Stack<>();
		HashMap<Literal, Boolean> visited = new HashMap<>();
		// Call the recursive helper function to store
		// Topological Sort starting from all vertices
		// one by one
		for (Literal key: vertices.keySet()){
			Boolean isVisited = visited.get(key);
			if (isVisited == null || !isVisited)
				topologicalSortUtil(key, visited, stack);
		}
		
		while (!stack.isEmpty())
			results.add(stack.pop());	
	}
}
