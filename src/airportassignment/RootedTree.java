package airportassignment;

/**
 * A rooted tree is a connected directed graph with no cycles.
 * Implementing this interface, requires that this contract is fulfilled.
 */
public interface RootedTree<D,W> extends Graph<D,W> {
  Vertex<D,W> getRoot();
  void add(Edge<D,W> edge);
  }

