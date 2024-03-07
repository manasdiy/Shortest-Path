import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;


/**
 * This class extends the BaseGraph data structure with additional methods for computing the total
 * cost and list of node data along the shortest path connecting a provided starting to ending
 * nodes. This class makes use of Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
    implements GraphADT<NodeType, EdgeType> {

  /**
   * While searching for the shortest path between two nodes, a SearchNode contains data about one
   * specific path between the start node and another node in the graph. The final node in this path
   * is stored in its node field. The total cost of this path is stored in its cost field. And the
   * predecessor SearchNode within this path is referened by the predecessor field (this field is
   * null within the SearchNode containing the starting node in its node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost SearchNode has the
   * highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;

    public SearchNode(Node node, double cost, SearchNode predecessor) {
      this.node = node;
      this.cost = cost;
      this.predecessor = predecessor;
    }

    public int compareTo(SearchNode other) {
      if (cost > other.cost)
        return +1;
      if (cost < other.cost)
        return -1;
      return 0;
    }
  }

  /**
   * Constructor that sets the map that the graph uses.
   *
   * @param map the map that the graph uses to map a data object to the node object it is stored in
   */
  public DijkstraGraph(MapADT<NodeType, Node> map) {
    super(map);
  }

  /**
   * This helper method creates a network of SearchNodes while computing the shortest path between
   * the provided start and end locations. The SearchNode that is returned by this method is
   * represents the end of the shortest path that is found: it's cost is the cost of that shortest
   * path, and the nodes linked together through predecessor references represent all of the nodes
   * along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found or when either start or
   *                                end data do not correspond to a graph node
   */
  protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    // implement in step 5.3

    PriorityQueue<SearchNode> priorQueue = new PriorityQueue<>();
    MapADT<NodeType, SearchNode> nodeVisited = new PlaceholderMap<>();

    priorQueue.add(new SearchNode(nodes.get(start), 0, null));
    // while the queue is not empty
    while (!priorQueue.isEmpty()) {

      SearchNode currNode = priorQueue.poll();

      // if the current node is the end node, return it
      if (currNode.node.data.equals(end)) {
        return currNode;
      }

      // if the node is already visited, skip it
      if (nodeVisited.containsKey(currNode.node.data)) {
        continue;
      }

      nodeVisited.put(currNode.node.data, currNode);
      // loop through the edges leaving the current node
      for (Edge edge : currNode.node.edgesLeaving) {
        // if the edge is not visited, add it to the queue
        if (!nodeVisited.containsKey(edge.successor.data)) {
          // add the edge to the queue
          priorQueue.add(
              new SearchNode(edge.successor, currNode.cost + edge.data.doubleValue(), currNode));
        }
      }
    }

    throw new NoSuchElementException("There is no path from start to end found");
  }

  /**
   * Returns the list of data values from nodes along the shortest path from the node with the
   * provided start value through the node with the provided end value. This list of data values
   * starts with the start value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shorteset path. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return list of data item from node along this shortest path
   */
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    // implement in step 5.4

    // create a linked list to store the data of the nodes in the shortest path
    List<NodeType> shortPath = new LinkedList<>();
    // get the current node
    SearchNode currNode = computeShortestPath(start, end);

    // loop through the nodes in the shortest path
    while (currNode != null) {
      // add the data of the node to the list
      shortPath.add(0, currNode.node.data);
      currNode = currNode.predecessor;
    }
    return shortPath;
  }

  /**
   * Returns the cost of the path (sum over edge weights) of the shortest path freom the node
   * containing the start data to the node containing the end data. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  public double shortestPathCost(NodeType start, NodeType end) {
    // implement in step 5.4
    return computeShortestPath(start, end).cost;
  }

}

