package sat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import immutable.*;
import sat.formula.*;

public class Graph {

	// This class represents a directed graph using adjacency
	// list representation
//	private int V; // No. of vertices
	private HashMap<Literal, LinkedList<Literal>> vertices = new HashMap<>();
	//private LinkedList<Integer> adj[]; // Adjacency List

//	// Constructor
//	Graph(int v) {
//		V = v;
//		adj = new LinkedList[v];
//		for (int i = 0; i < v; ++i)
//			adj[i] = new LinkedList();
//	}
//
//	// Function to add an edge into the graph
//	void addEdge(int v, int w) {
//		adj[v].add(w);
//	}
//	
	void addEdge(Literal v, Literal w){
		// link the literal v to the literal w.
		LinkedList<Literal> edges = vertices.get(v);
		if (edges == null)
			// instantiate the linkedList if it doesn't exist.
			edges = new LinkedList<Literal>();
		edges.add(w);
		vertices.put(v, edges);
	}

	// A recursive function used by topologicalSort
//	void topologicalSortUtil(int v, boolean visited[], Stack<Integer> stack) {
//		// Mark the current node as visited.
//		visited[v] = true;
//		Integer i;
//
//		// Recur for all the vertices adjacent to this
//		// vertex
//		Iterator<Integer> it = adj[v].iterator();
//		while (it.hasNext()) {
//			i = it.next();
//			if (!visited[i])
//				topologicalSortUtil(i, visited, stack);
//		}
//
//		// Push current vertex to stack which stores result
//		stack.push(new Integer(v));
//	}
	
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
			System.out.print(stack.pop() + " ");
	}
//	void topologicalSort() {
//		Stack stack = new Stack();
//
//		// Mark all the vertices as not visited
//		boolean visited[] = new boolean[V];
//		for (int i = 0; i < V; i++)
//			visited[i] = false;
//
//		// Call the recursive helper function to store
//		// Topological Sort starting from all vertices
//		// one by one
//		for (int i = 0; i < V; i++)
//			if (visited[i] == false)
//				topologicalSortUtil(i, visited, stack);
//
//		// Print contents of stack
//		while (stack.empty() == false)
//			System.out.print(stack.pop() + " ");
//	}

	// Driver method
	public static void main(String args[]) {

	}
}
