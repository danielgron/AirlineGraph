package graphimpl;

import airportimport.Airport;
import airportimport.Path;
import airportimport.Route;
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
public class DijkstraAlgo {

    private final List<Airport> vertices;
    private final List<Route> edges;
    private Set<Airport> visited;
    private Set<Airport> unvisited;
    private Map<Airport, Airport> predecessors;
    private Map<Airport, Path> distance;

    public DijkstraAlgo(List<Airport> vertices, List<Route> edges) {
        // create a copy of the array so that we can operate on this array
        this.vertices = vertices;
        this.edges = edges;
    }

    public void execute(Airport source) {
        visited = new HashSet<Airport>();
        unvisited = new HashSet<Airport>();
        distance = new HashMap<Airport, Path>();
        predecessors = new HashMap<Airport, Airport>();
        distance.put(source, null);
        unvisited.add(source);
        while (unvisited.size() > 0) {
            System.out.println(unvisited.size());
            System.out.println("DONE EXECUTE" + visited.toString());
            Airport a = getMinimum(unvisited);
            visited.add(a);
            unvisited.remove(a);
            findMinimalDistances(a);
        }

        System.out.println(unvisited.size());
    }

    private void findMinimalDistances(Airport a) {
        List<Airport> adjacentNodes = getNeighbors(a);
        for (Airport target : adjacentNodes) {
            Path p = getDistance(a, target);
            if (getShortestDistance(target) > getShortestDistance(a)
                    + p.getDistance()) {
                distance.put(target, new Path(getShortestDistance(a)
                        + p.getDistance(), 0.0));
                predecessors.put(target, a);
                unvisited.add(target);
            }
        }

    }

    private Path getDistance(Airport v, Airport target) {
        for (Route edge : edges) {
            if (edge.getSource().equals(v)
                    && edge.getDestination().equals(target)) {
                return edge.getPath();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Airport> getNeighbors(Airport v) {
        List<Airport> neighbors = new ArrayList<Airport>();
        for (Route edge : edges) {
            if (edge.getSource().equals(v)
                    && !isSettled(edge.getDestination())) {
                neighbors.add((Airport) edge.getDestination());
            }
        }
        return neighbors;
    }

    private Airport getMinimum(Set<Airport> vertices) {
        Airport minimum = null;
        for (Airport vertex : vertices) {
            if (minimum == null) {
                minimum = vertex;
            } else if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                minimum = vertex;
            }
        }
        return minimum;
    }

    private boolean isSettled(Airport vertex) {
        return visited.contains(vertex);
    }

    private Double getShortestDistance(Airport destination) {
        Path d = distance.get(destination);
        Double dist = d.getDistance();
        if (dist == null) {
            return Double.MAX_VALUE;
        } else {
            return dist;
        }
    }

    /*
         * This method returns the path from the source to the selected target and
         * NULL if no path exists
     */
    public LinkedList<Airport> getPath(Airport target) {
        LinkedList<Airport> path = new LinkedList<Airport>();
        Airport step = target;
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
