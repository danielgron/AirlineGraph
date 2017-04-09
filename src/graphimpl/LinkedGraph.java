/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphimpl;

import airportassignment.Graph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author danie
 * @param <Data>
 * @param <Weight>
 *
 */
public class LinkedGraph<Data, Weight> implements Graph<Data, Weight> {

    LinkedElement<LinkedVertex> vertices;
    LinkedElement<Edge> edges;
    int size;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        LinkedGraph<Person, Integer> g = new LinkedGraph();
        g.addVertex(new Person("Bob"));
        g.addVertex(new Person("John"));

        g.addEdge(0, g.vertexOf(new Person("Bob")), g.vertexOf(new Person("John")), true);
    }

    @Override
    public void addVertex(Data... data) {
        for (Data d : data) {
            Vertex v = new LinkedVertex(d);
            vertices = new LinkedElement(v, vertices);
            size++;
        }
    }

    @Override
    public void addEdge(Weight weight, Vertex<Data, Weight> tail, Vertex<Data, Weight> head, boolean directed) {

        LinkedEdge edge = new LinkedEdge(tail, head, weight);
//        System.out.println(head);
        edges = new LinkedElement(edge, edges);
        if(tail != null || tail != null){
            if (tail.getClass()== LinkedVertex.class)((LinkedVertex)tail).addEdge(edge);
            System.out.println("SUCCESS");
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Vertex<Data, Weight>> getVertices() {
        List<Vertex<Data, Weight>> v = new ArrayList();

        LinkedElement<LinkedVertex> traverse = vertices;
        while (traverse != null) {
            v.add(traverse.getData());
            traverse = traverse.rest;
        }
        return v;
    }

    @Override
    public Collection<Edge<Data, Weight>> getEdges() {

        List<Edge<Data, Weight>> e = new ArrayList();

        LinkedElement<Edge> traverse = edges;
        while (traverse != null) {
            e.add(traverse.getData());
            traverse = traverse.rest;
        }
        return e;

    }

    @Override
    public Vertex<Data, Weight> vertexOf(Data data) {
        LinkedElement<LinkedVertex> traverse = vertices;
        while (traverse != null) {
            System.out.println(".." + traverse.getData().getData());
            System.out.println(".." + data);
            System.out.println("DONE");
            if (traverse.getData().getData() == data){
                System.out.println("return" + data);
                return traverse.getData();
            }
            traverse = traverse.rest;
        }
            System.out.println("STOP");
        return null;
    }

    @Override
    public Collection<Edge<Data, Weight>> getEdgesFrom(Vertex<Data, Weight> vertex) {
        Collection<Edge<Data, Weight>> adjacentEdges =null;
        if (vertex.getClass() == LinkedVertex.class) {
            adjacentEdges = ((LinkedVertex)vertex).getAdjacentEdges();
        }
        return adjacentEdges;
    }

    private class LinkedVertex implements Vertex<Data, Weight> {

        //LinkedElement<Data> data;
        Data d;
        LinkedElement<Edge> localEdges;

        LinkedVertex(Data d) {
            this.d = d;
        }

        public void addEdge(LinkedEdge edge) {
            localEdges = new LinkedElement(edge, localEdges);
        }
        @Override
        public String toString(){
            return d.toString();
        }

        @Override
        public Data getData() {
            return d;
        }

        @Override
        public Collection<Edge<Data, Weight>> getAdjacentEdges() {
            List<Edge<Data, Weight>> al = new ArrayList();
            LinkedElement<Edge> traverse = localEdges;
            while (traverse!= null) {
                al.add(traverse.getData());
                traverse = traverse.rest;
            }
            return al;
        }
    }

    private class LinkedEdge implements Edge<Data, Weight> {

        Weight w;
        Vertex tail;
        Vertex head;

        public LinkedEdge(Vertex tail, Vertex head, Weight weight) {
            this.tail = tail;
            this.head = head;
            this.w = weight;

        }

        @Override
        public Weight getWeight() {
            return w;
        }

        @Override
        public Vertex<Data, Weight> getHeadVertex() {
            return head;
        }

        @Override
        public Object getDestination() {
            return tail;
        }
    }

}
