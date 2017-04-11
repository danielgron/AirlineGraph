/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportimport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 *
 * @author Cherry Rose Semeña
 */
public class Dijkstra {
    private final Map<Airport, Airport> graph; // mapping of vertex names to Airport objects, built from a set of Edges
  
   /** Builds a graph from a set of edges */
   public Dijkstra(List<Route> edges) {
      graph = new HashMap<>(edges.size());
 
      //one pass to find all vertices
      for (Route e : edges) {
         if (!graph.containsKey(e.getSource())) graph.put(e.getSource(), new Airport(e.getDestination()));
         if (!graph.containsKey(e.getDestination())) graph.put(e.getDestination(), new Airport(e.getDestination()));
      }
 
      //another pass to set neighbouring vertices
      for (Route e : edges) {
         graph.get(e.getSource()).neighbours.put(graph.get(e.getDestination()), e.getPath().getDistance());
         //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
      }
   }
 
   /** Runs dijkstra using a specified source vertex */ 
   public void dijkstra(Airport startName) {
      if (!graph.containsKey(startName)) {
         System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
         return;
      }
      final Airport source = graph.get(startName);
      NavigableSet<Airport> q = new TreeSet<>();
 
      // set-up vertices
      for (Airport v : graph.values()) {
         v.previous = v == source ? source : null;
         v.dist = v == source ? 0 : Integer.MAX_VALUE;
         q.add(v);
      }
 
      dijkstra(q);
   }
 
   /** Implementation of dijkstra's algorithm using a binary heap. */
   private void dijkstra(final NavigableSet<Airport> q) {      
        Airport u;
        Airport v;
      while (!q.isEmpty()) {
 
         u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
         if (u.dist == Integer.MAX_VALUE) break; // we can ignore u (and any other remaining vertices) since they are unreachable
 
         //look at distances to each neighbour
         for (Map.Entry<Airport, Double> a : u.neighbours.entrySet()) {
            v = a.getKey(); //the neighbour in this iteration
 
            final double alternateDist = u.dist + a.getValue();
            if (alternateDist < v.dist) { // shorter path to neighbour found
               q.remove(v);
               v.dist = alternateDist;
               v.previous = u;
               q.add(v);
            } 
         }
      }
   }
 
   /** Prints a path from the source to the specified vertex */
   public void printPath(Airport endName) {
      if (!graph.containsKey(endName)) {
         System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
         return;
      }
 
      graph.get(endName).printPath();
      System.out.println();
   }
   /** Prints the path from the source to every vertex (output order is not guaranteed) */
   public void printAllPaths() {
      for (Airport v : graph.values()) {
         v.printPath();
         System.out.println();
      }
   }
   
   
   
}
