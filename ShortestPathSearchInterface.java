import java.util.List;

public interface ShortestPathSearchInterface {
  //    /**
  //     * An interface that gets the path, walking times, and total time spent on a path
  //     */
  //    public ShortestPathSearchInterface();
  /**
   * Get the list of buildings along the path
   * @return list of buildings along the path
   */
  List<String> getPath();

  /**
   * Get the list of the walking times of the path segments (the time it takes to walk from one
   * building to the next
   *
   * @return list of the walking times of the path segments
   */
  List<Double> getWalkingTimes();

  /**
   * Get the total path cost as the estimated time it takes to walk from the start to the
   * destination building.
   *
   * @return estimated time it takes to walk from the start to the
   * destination building.
   */
  double getTotalTimeEntireCampus();

}
