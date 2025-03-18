package org.example;

import java.io.*;
import java.util.*;

/**
 * Graph class represents a directed graph using an adjacency list
 * and implements the logic to find the shortest cycle using Dijkstra's algorithm.
 */
class Graph {
    private final int V; // Number of vertices in the graph
    private final List<List<int[]>> adj; // Adjacency list representation

    public Graph(int V) {
        this.V = V;
        this.adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    // Method to add a directed edge with a given weight
    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new int[]{v, weight});
    }

    /**
     * Uses Dijkstra’s algorithm to find the shortest cycle in the graph.
     * The algorithm iterates through all nodes as potential starting points.
     */
    public int findShortestCycle() {
        int shortestCycle = Integer.MAX_VALUE;

        for (int start = 0; start < V; start++) {
            int cycleLength = dijkstraFindCycle(start);
            if (cycleLength > 0) {
                shortestCycle = Math.min(shortestCycle, cycleLength);
            }
        }

        return (shortestCycle == Integer.MAX_VALUE) ? 0 : shortestCycle;
    }

    /**
     * Implements Dijkstra’s algorithm to find the shortest cycle starting from a given node.
     */
    private int dijkstraFindCycle(int start) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        int[] dist = new int[V]; // Distance array to track shortest paths
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        pq.offer(new int[]{start, 0});

        int shortestCycle = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0], d = curr[1];

            if (d > dist[u]) continue; // Ignore outdated distances

            for (int[] edge : adj.get(u)) {
                int v = edge[0], weight = edge[1];

                if (dist[v] > d + weight) { // Update shortest known distance to v
                    dist[v] = d + weight;
                    pq.offer(new int[]{v, dist[v]});
                } else if (v == start) { // If we return to the start node, a cycle is found
                    shortestCycle = Math.min(shortestCycle, d + weight);
                }
            }
        }

        return (shortestCycle == Integer.MAX_VALUE) ? -1 : shortestCycle;
    }
}
