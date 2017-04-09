
package graphimpl;

import airportassignment.Graph;
import airportassignment.Graph.Edge;
import airportassignment.Graph.Vertex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Cherry Rose Semeña
 */
public class DijkstraAlgo<D,W>{
    
        private final List<Vertex<D,W>> vertices;
        private final List<Edge<D,W>> edges;
        private Set<Vertex<D,W>> visited;
        private Set<Vertex<D,W>> unvisited;
        private Map<Vertex<D,W>, Vertex<D,W>> predecessors;
        private Map<Vertex<D,W>, Integer> distance;

        public DijkstraAlgo(Graph graph) {
                // create a copy of the array so that we can operate on this array
                this.vertices = new ArrayList<Vertex<D,W>>(graph.getVertices());
                this.edges = new ArrayList<Edge<D,W>>(graph.getEdges());
        }

        public void execute(Vertex source) {
                visited = new HashSet<Vertex<D,W>>();
                unvisited = new HashSet<Vertex<D,W>>();
                distance = new HashMap<Vertex<D,W>, Integer>();
                predecessors = new HashMap<Vertex<D,W>, Vertex<D,W>>();
                distance.put(source, 0);
                unvisited.add(source);
                while (unvisited.size() > 0) {
                        Vertex<D,W> v = getMinimum(unvisited);
                        visited.add(v);
                        unvisited.remove(v);
                        findMinimalDistances(v);
               }
        }

        private void findMinimalDistances(Vertex<D,W> v) {
                List<Vertex<D,W>> adjacentNodes = getNeighbors(v);
                for (Vertex<D,W> target : adjacentNodes) {
                        if (getShortestDistance(target) > getShortestDistance(v)
                                        + getDistance(v, target)) {
                                distance.put(target, getShortestDistance(v)
                                                + getDistance(v, target));
                                predecessors.put(target, v);
                                unvisited.add(target);
                        }
                }

        }

        private int getDistance(Vertex<D,W> v, Vertex<D,W> target) {
                for (Edge edge : edges) {
                        if (edge.getHeadVertex().equals(v)
                                        && edge.getDestination().equals(target)) {
                                return (int)edge.getWeight();
                        }
                }
                throw new RuntimeException("Should not happen");
        }

        private List<Vertex<D,W>> getNeighbors(Vertex<D,W> v) {
                List<Vertex<D,W>> neighbors = new ArrayList<Vertex<D,W>>();
                for (Edge edge : edges) {
                        if (edge.getHeadVertex().equals(v)
                                        && !isSettled((Vertex<D, W>) edge.getDestination())) {
                                neighbors.add((Vertex<D, W>) edge.getDestination());
                        }
                }
                return neighbors;
        }

        private Vertex<D,W> getMinimum(Set<Vertex<D,W>> vertices) {
                Vertex<D,W> minimum = null;
                for (Vertex<D,W> vertex : vertices) {
                        if (minimum == null) {
                                minimum = vertex;
                        } else {
                                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                                        minimum = vertex;
                                }
                        }
                }
                return minimum;
        }

        private boolean isSettled(Vertex<D,W> vertex) {
                return visited.contains(vertex);
        }

        private int getShortestDistance(Vertex<D,W> destination) {
                Integer d = distance.get(destination);
                if (d == null) {
                        return Integer.MAX_VALUE;
                } else {
                        return d;
                }
        }

         /*
         * This method returns the path from the source to the selected target and
         * NULL if no path exists
         */
        public LinkedList<Vertex<D,W>> getPath(Vertex<D,W> target) {
                LinkedList<Vertex<D,W>> path = new LinkedList<Vertex<D,W>>();
                Vertex<D,W> step = target;
                // check if a path exists
                if (predecessors.get(step) == null) {
                        return null;
                }
                path.add(step);
                while (predecessors.get(step) != null) {
                        step = predecessors.get(step);
                        path.add(step);
                }
                // Put it into the correct order
                Collections.reverse(path);
                return path;
        }


}
