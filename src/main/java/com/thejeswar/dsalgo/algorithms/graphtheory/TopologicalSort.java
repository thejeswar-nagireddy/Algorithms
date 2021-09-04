/**
 * 
 */
package com.thejeswar.dsalgo.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.List;

/**
 * This topological sort implementation takes an adjacency list of an acyclic
 * graph and returns an array with the indexes of the nodes, which tells you the
 * order (non-unique), in which you need to process the nodes.
 * 
 * From wiki: A topological ordering is an ordering of the nodes in a directed
 * graph where for each directed edge from node A to node B, node A appears
 * before node B in the ordering.
 * 
 * 
 * @author Thejeswar Nagireddy, thejeswarreddyn@hotmail.com
 */
public class TopologicalSort {

	
	public List<Integer> topologicalSort(List<List<Edge>> graph) {
		int length = graph.size();
		boolean[] visitedNodes = new boolean[length];
		List<Integer> ordered = new ArrayList<>(length);
		fillList(ordered, length);
		int index = length - 1;
		for (int at = 0; at < graph.size(); at++) {
			List<Integer> visited = new ArrayList<>();
			dfs(visited, at, visitedNodes, graph);
			for (int visitedIndex : visited) {
				ordered.set(index, visitedIndex);
				index--;
			}
		}
		return ordered;
	}

	private void fillList(List<Integer> list, int size) {
		for (int i = 0; i < size; i++) {
			list.add(0);
		}
	}

	// Helper DFS method
	private void dfs(List<Integer> visited, int at, boolean[] visitedNodes, List<List<Edge>> graph) {
		if (!visitedNodes[at]) {
			visitedNodes[at] = true;
			List<Edge> edges = graph.get(at);
			for (Edge edge : edges) {
				dfs(visited, edge.to, visitedNodes, graph);
			}
			visited.add(at);
		}
	}

	// Helper Edge class to describe edges in the graph
	static class Edge {
		int to;
		int from;
		int weight;

		public Edge(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int length = 6;
		// Representing Graph in Adjacency List
		List<List<Edge>> graph = new ArrayList<List<Edge>>(length);
		for (int i = 0; i < length; i++) {
			graph.add(new ArrayList<Edge>());
		}
		graph.get(0).add(new Edge(0, 1, 3));
		graph.get(0).add(new Edge(0, 2, 2));
		graph.get(0).add(new Edge(0, 5, 3));
		graph.get(1).add(new Edge(1, 3, 1));
		graph.get(1).add(new Edge(1, 2, 6));
		graph.get(2).add(new Edge(2, 3, 1));
		graph.get(2).add(new Edge(2, 4, 10));
		graph.get(3).add(new Edge(3, 4, 5));
		graph.get(5).add(new Edge(5, 4, 7));

		TopologicalSort ts = new TopologicalSort();
		List<Integer> path = ts.topologicalSort(graph);
		for (Integer pathElement : path) {
			System.out.print(pathElement + " ");
		}

	}

}
