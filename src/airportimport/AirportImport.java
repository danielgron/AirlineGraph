/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportimport;


import airportassignment.Graph;
import airportassignment.Graphs;
import airportassignment.RootedTree;
import graphimpl.LinkedGraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class AirportImport {
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
        
        
        
        AirportImport ai = new AirportImport();
        System.out.println("Getting airlines");
        ai.getAirlines();
        System.out.println("Getting airports");
        ai.getAirports();
        System.out.println("Getting routes");
        ai.getRoutes();
        System.out.println("Doing breadth first search");
        RootedTree<Airport, Path> breadthFirst = Graphs.breadthFirst(ai.g, ai.g.vertexOf(ai.airports.get("GKA")));
        Graph graph = ai.graphFromAirline("BA");
        System.out.println("Using DepthFirst to see if connected: ");
        Graphs.isReachableDepthFirst(graph, graph.vertexOf(ai.airports.get("GKA")), graph.vertexOf(ai.airports.get("WWK")));
        System.out.println("Using BreadthFirst to see if connected: ");
        Graphs.isReachableBreadthFirst(graph, graph.vertexOf(ai.airports.get("GKA")), graph.vertexOf(ai.airports.get("WWK")));
        System.out.println("Using DepthFirst to see if connected: ");
        Graphs.isReachableDepthFirst(graph, graph.vertexOf(ai.airports.get("FLR")), graph.vertexOf(ai.airports.get("CPH")));
        System.out.println("Using BreadthFirst to see if connected: ");
        Graphs.isReachableBreadthFirst(graph, graph.vertexOf(ai.airports.get("FLR")), graph.vertexOf(ai.airports.get("CPH")));
    
        
    }
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
            //List<Airline> airlines = new ArrayList();
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
            //System.out.println("Line: "+i++);
//            for (String string : airport) {
//                System.out.println(string);
//            }
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
            //System.out.println("Line: "+i++);
            
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
