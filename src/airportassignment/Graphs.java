package airportassignment;


import airportassignment.Graph.Edge;
import airportassignment.Graph.Vertex;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for graph related functions.
 */
public class Graphs {

    public static <D, W> boolean isReachableDepthFirst(Graph<D, W> graph, Vertex<D, W> root, Vertex<D, W> target) {
        Set<Vertex<D, W>> marked = new HashSet<>();
        ArrayStack<Vertex<D, W>> stack = new ArrayStack(1000);
        int i = 0;
        stack.push(root);
        Vertex<D, W> current;
        while (!stack.isEmpty()) {
            i++;
            while (marked.contains(current = stack.pop()));
            if (current.equals(target)){
                System.out.println("Found target! Number of evaluations: "+i);
                while(!stack.isEmpty())System.out.println(stack.pop());
                return true;
            }
            marked.add(current);
            Collection<Edge<D, W>> edgesFrom = graph.getEdgesFrom(current);
            for (Edge<D, W> edge : edgesFrom) {
                if (!marked.contains(edge.getHeadVertex())) {
                    stack.push(edge.getHeadVertex());
                }
            }
        }

        return false;
    }

    public static <D, W> RootedTree<D, W> depthFirst(Graph<D, W> graph, Vertex<D, W> root) {
        Set<Vertex<D, W>> marked = new HashSet<>();
        RootedTree<D, W> edgesTo = new SimpleRootedTree<>(graph, root);

        ArrayStack<Vertex<D, W>> stack = new ArrayStack(1000);
        stack.push(root);
        Vertex<D, W> current;
        while (!stack.isEmpty()) {
            while (marked.contains(current = stack.pop()));
            System.out.println(current.getData().toString());
            marked.add(current);
            Collection<Edge<D, W>> edgesFrom = graph.getEdgesFrom(current);
            for (Edge<D, W> edge : edgesFrom) {
                if (!marked.contains(edge.getHeadVertex())) {
                    
                    stack.push(edge.getHeadVertex());
                }
            }
        }

        return edgesTo;
    }
    
    public static <D, W> boolean isReachableBreadthFirst(Graph<D, W> graph, Vertex<D, W> root, Vertex<D, W> target){
        Set<Vertex<D, W>> marked = new HashSet<>();
        Queue<Vertex<D, W>> queue = new LinkedQueue<>();
        RootedTree<D, W> edgesTo = new SimpleRootedTree<>(graph, root);
        int i=0;

        queue.enqueue(root);
        Vertex<D, W> current;
        while (!queue.isEmpty()) {
            i++;
            current = queue.dequeue();
            //System.out.println(current.getData().toString());
            if (current.equals(target)){
                System.out.println("Found target! Number of evaluations: "+i);
                
                return true;
            }
            marked.add(current);
            Collection<Edge<D, W>> edgesFrom = graph.getEdgesFrom(current);
            for (Edge<D, W> edge : edgesFrom) {
                if (!marked.contains(edge.getHeadVertex())) {
                    queue.enqueue(edge.getHeadVertex());
                }
            }

        }

        return false;
    }

    public static <D, W> RootedTree<D, W> breadthFirst(Graph<D, W> graph, Vertex<D, W> root) {
        Set<Vertex<D, W>> marked = new HashSet<>();
        Queue<Vertex<D, W>> queue = new LinkedQueue<>();
        RootedTree<D, W> edgesTo = new SimpleRootedTree<>(graph, root);

        queue.enqueue(root);
        Vertex<D, W> current;
        while (!queue.isEmpty()) {
            current = queue.dequeue();
            //System.out.println(current.getData().toString());
            marked.add(current);
            Collection<Edge<D, W>> edgesFrom = graph.getEdgesFrom(current);
            for (Edge<D, W> edge : edgesFrom) {
                if (!marked.contains(edge.getHeadVertex())) {
                    queue.enqueue(edge.getHeadVertex());
                }
            }

        }

        // TODO: implement
        return edgesTo;
    }

}
