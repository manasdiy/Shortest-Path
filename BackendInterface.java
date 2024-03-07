import java.io.FileNotFoundException;
import java.util.List;

public interface BackendInterface {
  //    /**
  //     * An interface for the backend that takes an instance of the GraphADT as a constructor
  //     * @param graphADT an ADT that represents a directed graph data structure with only positive
  //     * edge weights. Duplicate node values are not allowed.
  //     */
  //    public IndividualBackendInterface(GraphADT graphADT);

  /**
   * Read data from the provided file,
   *
   * @param fileName name of the file to read
   * @throws FileNotFoundException if file is invalid
   */
  void loadDataFile(String fileName) throws FileNotFoundException;

  /**
   * Get the shortest path from a start to a destination building in the dataset. Finds this value
   * from finding the shortest path between the provided nodes.
   *
   * @param startBuilding       starting location
   * @param destinationBuilding destination
   * @return shortest path from start to destination building
   */
  ShortestPathSearchInterface getShortestPath(String startBuilding, String destinationBuilding);

  /**
   * Get a string with statistics about the dataset that includes the number of nodes (buildings),
   * the number of edges, and the total walking time (sum of weights) for all edges in the graph.
   *
   * @return string representation of the number of nodes and edges and total walking time
   */
  String getStatistics();
}
