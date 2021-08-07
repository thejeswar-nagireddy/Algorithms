package com.thejeswar.dsalgo.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.thejeswar.dsalgo.datastructures.priorityqueue.BinaryHeap;

/**
 * @author thejeswar
 *
 */
public class DijkstrasShortestPath {

	private int[] dist;
	private boolean[] visited;
	private Node[] parent;
	// Representing Graph in Adjacency List
	private List<List<Edge>> graph;

	public DijkstrasShortestPath(int n) {
		graph = new ArrayList<List<Edge>>(n);
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<Edge>());
		}
		parent = new Node[n];
		visited = new boolean[n];
		dist = new int[n];
		Arrays.fill(dist, Integer.MAX_VALUE);
	}

	public void addEdge(int from, int to, int weight) {
		graph.get(from).add(new Edge(weight, from, to));
	}

	/*
	 * Steps in Dijkstra’s algorithm… 
	 * 1. Find the “cheapest” node. This is the node you can get to in least amount of time. Sounds greedy… 
	 * 2. Check whether there is a cheaper path to the neighbor of this node, if so, update their costs. 
	 * 3. Repeat until you’ve done this for every node in the graph. 
	 * 4. Calculate the final path.
	 * 
	 */
	public int dijkstra(int startId, int endId) {
		BinaryHeap<Node> pQueue = new BinaryHeap<>();
		pQueue.add(new Node(startId, 0));
		dist[startId] = 0;
		while (!pQueue.isEmpty()) {
			Node node = pQueue.poll();
			visited[node.id] = true;
			List<Edge> edges = graph.get(node.id);
			int distance = dist[node.id];
			for (Edge edge : edges) {
				if (visited[edge.to])
					continue;
				int distNew = distance + edge.weight;
				if (distNew < dist[edge.to]) {
					parent[edge.to] = node;
					dist[edge.to] = distNew;
					pQueue.add(new Node(edge.to, distNew));
				}
			}
			if (node.id == endId)
				break;
		}
		return dist[endId];
	}

	/*
	 *  Constructs path from the parent array
	 */
	public List<Integer> reconstructPath(int startId, int endId) {
		dijkstra(startId, endId);
		List<Integer> path = new ArrayList<>();
		if (dist[endId] == Integer.MAX_VALUE)
			return path;
		while (true) {
			path.add(endId);
			if (endId == startId)
				break;
			endId = parent[endId].id;
		}
		Collections.reverse(path);
		return path;
	}

	static class Node implements Comparable<Node> {
		int id;
		int value;

		public Node(int id, int value) {
			this.id = id;
			this.value = value;
		}

		@Override
		public int compareTo(Node node) {
			return value - node.value;
		}
	}

	static class Edge {
		int weight;
		int to;
		int from;

		public Edge(int weight, int from, int to) {
			this.weight = weight;
			this.from = from;
			this.to = to;
		}
	}

	/**
	 * For testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DijkstrasShortestPath dsp = new DijkstrasShortestPath(10);
		dsp.addEdge(0, 1, 6);
		dsp.addEdge(0, 2, 3);
		dsp.addEdge(1, 3, 2);
		dsp.addEdge(2, 1, 2);
		dsp.addEdge(2, 3, 5);
		dsp.addEdge(3, 4, 5);
		// System.out.println("Distance from 0 to 4 : " + dsp.dijkstra(0, 4));
		List<Integer> path = dsp.reconstructPath(0, 4);
		for (Integer pathElement : path) {
			System.out.print(pathElement + " ");
		}
	}

}
