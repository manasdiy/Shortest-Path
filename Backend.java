import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Backend implements BackendInterface {
    private GraphADT<String, Double> graph;
    private double totalWalkingTime;

    // Constructor accepting a GraphADT instance
    public Backend(GraphADT<String, Double> graph) {
        this.graph = graph;
        this.totalWalkingTime = 0;
    }

    @Override
    public void loadDataFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.contains("--") || !line.contains("[")) {
                continue; // Skip invalid lines
            }

            try {
                String[] parts = line.split(" -- ");
                String startNode = parts[0].trim().replace("\"", "");
                String endNode = parts[1].substring(0, parts[1].indexOf("[")).trim().replace("\"", "");
                String weightStr = parts[1].substring(parts[1].indexOf("=") + 1, parts[1].indexOf("]"));
                double weight = Double.parseDouble(weightStr);

                graph.insertNode(startNode);
                graph.insertNode(endNode);
                graph.insertEdge(startNode, endNode, weight);
                this.totalWalkingTime += weight; // This assumes all edges are unique
            } catch (Exception e) {
                System.err.println("Skipping line due to parsing error: " + line);
            }
        }
        scanner.close();
    }

    @Override
    public ShortestPathSearchInterface getShortestPath(String startBuilding, String destinationBuilding) {
        List<String> path = graph.shortestPathData(startBuilding, destinationBuilding);
        double totalCost = graph.shortestPathCost(startBuilding, destinationBuilding);

        return new ShortestPathSearchInterface() {
            @Override
            public List<String> getPath() {
                return path;
            }

            @Override
            public List<Double> getWalkingTimes() {
                return calculateWalkingTimes(path);
            }

            @Override
            public double getTotalTimeEntireCampus() {
                return totalCost;
            }
        };
    }

    private List<Double> calculateWalkingTimes(List<String> path) {
        List<Double> walkingTimes = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            walkingTimes.add(graph.getEdge(path.get(i), path.get(i + 1)).doubleValue());
        }
        return walkingTimes;
    }

    @Override
    public String getStatistics() {
        int nodeCount = graph.getNodeCount();
        int edgeCount = graph.getEdgeCount();
        return "Nodes: " + nodeCount + ", Edges: " + edgeCount + ", Total Walking Time: " + this.totalWalkingTime;
    }
}


