import java.io.FileNotFoundException;

public interface FrontendInterface {

  /**
   * constructor that accepts a reference to the backend and a java.util.Scanner instance
   * to read user input (commented out)
   */
  //public Frontend(Backend backend, Scanner scanner);

  /**
   * Command to specify and load a data file.
   */
  void loadDataset(String fileName) throws IllegalArgumentException, FileNotFoundException;

  /**
   * Command to show statistics about the dataset, including the number of buildings (nodes),
   * the number of edges, and the total walking time (sum of all edge weights) in the graph.
   */
  void showDatasetStatistics();

  /**
   * Command to find and display the shortest path between two buildings. This method should ask the
   * user for a start and destination building names, list the shortest path between those
   * buildings, estimated walking time for each segment, and total walking time.
   */
  void findShortestPath();

  /**
   * Command to exit the application.
   */
  void exitApp();

  /**
   * Method to start the main command loop for the user.
   * This method should display a menu, prompt the user for commands, and execute them.
   */
  void startCommand();

}

