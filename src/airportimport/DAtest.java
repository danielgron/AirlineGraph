/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportimport;


import airportassignment.Graph;
import airportassignment.Graph.Vertex;
import airportassignment.Graphs;
import airportassignment.RootedTree;
import graphimpl.DijkstraAlgo;
import graphimpl.LinkedGraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class DAtest {
    File f;
    Scanner s;
        Map<String, Airline> airlines = new HashMap(); 
        Map<String, Airport> airports = new HashMap();
        List<Route> routes = new ArrayList();
        Graph<Airport,Path> g = new LinkedGraph();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        
        
        DAtest ai = new DAtest();
        System.out.println("Getting airlines");
        ai.getAirlines();
        System.out.println("Getting airports");
        ai.getAirports();
        System.out.println("Getting routes");
        ai.getRoutes();
        System.out.println("Doing breadth first search");
        RootedTree<Airport, Path> breadthFirst = Graphs.breadthFirst(ai.g, ai.g.vertexOf(ai.airports.get("GKA")));
        Graph graph = ai.graphFromAirline("BA");
        System.out.println("Using DepthFirst to see if connected: "+ Graphs.isReachableDepthFirst(graph, graph.vertexOf(ai.airports.get("FLR")), graph.vertexOf(ai.airports.get("CPH"))));
    
        //test Dijkstra Algorithm - shortest path
        List<Airport> vertices =new ArrayList();
        for (Airport v : ai.airports.values()) {
            vertices.add(v);
        }
        
        DijkstraAlgo da = new DijkstraAlgo(vertices, ai.routes);
        Vertex<Airport, Path> s = ai.g.vertexOf(ai.airports.get("FLR"));
        Airport source = new Airport(s.getData().getCode(), s.getData().getName(), s.getData().getCity(), s.getData().getCountry(), s.getData().getLatitude(), s.getData().getLongitude());
        da.execute(source);
        
        Vertex<Airport, Path> t = ai.g.vertexOf(ai.airports.get("CPH"));
        Airport target = new Airport(t.getData().getCode(), t.getData().getName(), t.getData().getCity(), t.getData().getCountry(), t.getData().getLatitude(), t.getData().getLongitude());
        LinkedList<Airport> path = da.getPath(target);
        for (Airport vertex : path) {
            System.out.println("Route:" + vertex.getCode());
        }
        }

        
 
//   public static void main(String[] args) {
//       
//      DAtest ai = new DAtest();
//      ai.getAirlines();
//      ai.getAirports();
//      ai.getRoutes();
//      Dijkstra d = new Dijkstra(ai.routes);
//      Airport a = ai.airports.get("AER");
//      Airport b = ai.airports.get("KZN");
//       System.out.println("A" + a.toString());
//       System.out.println("B" + b.toString());
//      d.dijkstra(a);
//      d.printPath(b);
//      //g.printAllPaths();
//   }
    
    public void getAirlines(){
        f = new File("airlines.txt");
        try {
            //List<Airline> airlines = new ArrayList();
            s = new Scanner(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AirportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i=0;
        while(s.hasNext()){
            i++;
            String[] airline = s.nextLine().split(";");
            if(airline.length==3){
            //System.out.println("Line: "+i++);
//            for (String string : airline) {
//                System.out.println(string);
//            }
            Airline al = new Airline(airline[0], airline[1], airline[2]);
            airlines.put(al.getCode(),al);
            }
        }
    };
    public void getAirports(){
        f = new File("airports.txt");
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AirportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i=0;
        while(s.hasNext()){
            i++;
            if (i % 500 == 0) System.out.println("Read " + i + " airports into memory");
            String[] airport = s.nextLine().split(";");
            if(airport.length==6 && i>1){
            Airport a = new Airport(airport[0], airport[1], airport[2], airport[3], Double.valueOf(airport[4]), Double.valueOf(airport[5]));
            airports.put(a.getCode(),a);
            g.addVertex(a);
            }
        }
    };
    public void getRoutes(){
        f = new File("routes.txt");
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AirportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int j=0;
        while(s.hasNext()){
            j++;
            String[] route = s.nextLine().split(";");
            if(j>1){
            
            Route newRoute = new Route(airlines.get(route[0]), airports.get(route[1]), airports.get(route[2]),new Path(Double.parseDouble(route[3]),Double.parseDouble(route[4])));
            routes.add(newRoute);
            g.addEdge(newRoute.getPath(), newRoute.getSource(), newRoute.getDestination(), false);
            
            }
        }
        System.out.println("Number of routes: "+routes.size());
    };
    
    public Graph graphFromAirline(String airlineCode){
        Graph<Airport,Path> g = new LinkedGraph();
        Object[] toArray = airports.values().toArray();
        Airport[] a = new Airport[toArray.length];
        for (int i = 0; i < toArray.length; i++) {
            a[i] = (Airport)toArray[i];
        }
        g.addVertex(a);
        for (Route route : routes) {
            if (route.getAirline().getCode().equals(airlineCode)){
                g.addEdge(route.getPath(), route.getSource(), route.getDestination(), false);
            }
        }
        return g;
    }
    
}
