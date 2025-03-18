package org.example;

import java.io.*;
import java.util.*;

/**
 * This class implements a program to find the shortest cycle in a directed graph using Dijkstra's algorithm.
 */
public class ShortestCycleFinder {
    public static void main(String[] args) {
        // Ensure a file path is provided as a command-line argument
        if (args.length != 1) {
            System.out.println("Usage: java ShortestCycleFinder <input_file>");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            Map<Integer, List<int[]>> graphData = new HashMap<>(); // Stores adjacency list representation of the graph
            int maxVertex = -1; // Stores the highest indexed vertex for defining graph size

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; // Ignore empty lines

                String[] parts = line.split("\\s+"); // Split line into parts based on whitespace
                if (parts.length < 2) continue; // Ignore invalid lines

                int u = Integer.parseInt(parts[0].replace(":", "")); // Extract source vertex
                for (int i = 1; i < parts.length - 1; i += 2) {
                    if (i + 1 >= parts.length) break; // Prevents ArrayIndexOutOfBoundsException

                    int v = Integer.parseInt(parts[i]); // Destination vertex
                    int weight = Integer.parseInt(parts[i + 1]); // Edge weight
                    graphData.computeIfAbsent(u, k -> new ArrayList<>()).add(new int[]{v, weight});
                    maxVertex = Math.max(maxVertex, Math.max(u, v)); // Track the largest vertex index
                }
            }

            // Create a graph object with the required size
            Graph graph = new Graph(maxVertex + 1);
            for (Map.Entry<Integer, List<int[]>> entry : graphData.entrySet()) {
                for (int[] edge : entry.getValue()) {
                    graph.addEdge(entry.getKey(), edge[0], edge[1]); // Add directed edges to the graph
                }
            }

            // Compute the shortest cycle using Dijkstra’s algorithm
            int result = graph.findShortestCycle();
            System.out.println("The length of the shortest cycle is: " + result);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing input file: " + e.getMessage());
        }
    }
}