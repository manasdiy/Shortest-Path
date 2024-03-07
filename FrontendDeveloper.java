import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Frontend class for the application.
 */
public class FrontendDeveloper implements FrontendInterface {

  private BackendInterface backend; // Reference to the backend implementation
  private Scanner scanner; // Scanner instance to read user input


  public FrontendDeveloper(BackendPlaceholder backend, Scanner scanner) {
    this.backend = backend;
    this.scanner = scanner;
  }

  /**
   * constructor that accepts a reference to the backend and a java.util.Scanner instance to read
   * user input
   *
   * @param backend Reference to the backend implementation.
   * @param scanner Scanner instance to read user input.
   */
  public FrontendDeveloper(Backend backend, Scanner scanner) {
    this.backend = backend;
    this.scanner = scanner;
  }

  /**
   * Command to specify and load a data file.
   */
  @Override
  public void loadDataset(String fileName) throws IllegalArgumentException, FileNotFoundException {
    try {
      backend.loadDataFile(fileName);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not Found");
    }
  }

  /**
   * Command to show statistics about the dataset, including the number of buildings (nodes), the
   * number of edges, and the total walking time (sum of all edge weights) in the graph.
   */
  @Override
  public void showDatasetStatistics() {
    System.out.println("\n" + backend.getStatistics() + "\n");
  }

  /**
   * Command to find and display the shortest path between two buildings. This method should ask the
   * user for a start and destination building names, list the shortest path between those
   * buildings, estimated walking time for each segment, and total walking time.
   */
  @Override
  public void findShortestPath() {
    System.out.println("Enter the names of the two buildings: ");
    scanner.nextLine();
    String building1 = scanner.nextLine();
    System.out.println("Enter Second building name: ");
    String building2 = scanner.nextLine();

    ShortestPathSearchInterface shortestPath = backend.getShortestPath(building1, building2);

    // If a path exists, display the path details
    if (shortestPath != null) {
      List<String> path = shortestPath.getPath();
      List<Double> walkingTimes = shortestPath.getWalkingTimes();
      double totalTime = shortestPath.getTotalTimeEntireCampus();

      // Displaying the closest connection details (placeholder implementation)
      System.out.println("Closest connection between " + building1 + " and " + building2 + ":");
      System.out.println("Path: " + path);
      System.out.println("Walking Times: " + walkingTimes);
      System.out.println("Total Time: " + totalTime);
    } else {
      System.out.println("Could not find a path between " + building1 + " and " + building2);
    }

  }

  /**
   * Command to exit the application.
   */
  @Override
  public void exitApp() {
    this.scanner.close(); // Closing the scanner
    System.out.println("App Exited. Bye!"); // Prints this goodbye message
  }

  /**
   * Method to start the main command loop for the user. This method should display a menu, prompt
   * the user for commands, and execute them.
   */
  @Override
  public void startCommand() {
    System.out.println("Enter file name: ");

    // Loop until a valid file name is entered
    while (true) {
      try {
        this.loadDataset(scanner.nextLine());
        break;

      } catch (IllegalArgumentException e) {
        System.out.println("Wrong Input! Try again: ");
      } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());
      }
    }
    while (true) {

      System.out.println("Enter your input");
      System.out.println("(1) Shortest Path between Two Buildings");
      System.out.println("(2) Display Map Statistics");
      System.out.println("(3) Exit Application");

      int input;

      try {
        input = scanner.nextInt();
      } catch (Exception e) {

        System.out.println("Wrong Input! Try again");
        continue;
      }
      if (input == 1) {
        // Find and display the shortest path between two buildings
        this.findShortestPath();
      } else if (input == 2) {

        this.showDatasetStatistics();
      } else if (input == 3) {

        exitApp();
        break;
      }
    }
  }

  /**
   * Main method to run the application.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Backend backend = new Backend(new DijkstraGraph<>(new PlaceholderMap<>()));
    FrontendDeveloper frontend = new FrontendDeveloper(backend, scanner);
    frontend.startCommand();

    scanner.close();
  }
}
