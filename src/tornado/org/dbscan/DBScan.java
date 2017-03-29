package tornado.org.dbscan;

import tornado.org.generic.generators.PointGenerator;
import tornado.org.generic.objects.graph.Cluster;
import tornado.org.generic.objects.graph.Point;
import tornado.org.util.DataSetParser;
import tornado.org.util.EuclideanDistance;
import tornado.org.util.ResultFileWriter;

import java.util.ArrayList;
import java.util.List;

public class DBScan {

    public static final int MU = 10;
    private static final double EPSILON = 0.05;
    private List<Point> points = new ArrayList<>();
    private final List<Cluster> clusters = new ArrayList<>();
    private final EuclideanDistance euclideanDistance = new EuclideanDistance();
    private DataSetParser dataSetParser = new DataSetParser("data/stars.csv");

    public void init() {
        Object[][] dataSet = dataSetParser.getData();

        PointGenerator pointGenerator = new PointGenerator();
        points = pointGenerator.create(dataSet);

        generateClusters();

        System.out.println("Total amount of clusters: " + clusters.size());

        Plotter plotter = new Plotter();

        try {
            plotter.draw(clusters, points);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultFileWriter.writeDBScanResults(clusters, points);
    }

    //Create a new cluster.
    //If the cluster's beginning point does not have at least a mu amount of neighbours;
    //Mark that point as noise, and move on to the next potential starting point.
    //Otherwise, initiate and expand the cluster.
    private void generateClusters() {
        int clusterIndex = 0;

        for (Point point : points) {
            if (!point.isVisited()) {
                point.setVisited();
                List<Point> neighbours = getNeighbours(point);
                if (neighbours.size() < MU) {
                    point.setNoise();
                } else {
                    Cluster cluster = new Cluster(clusterIndex);

                    point.setAssigned(cluster.getId());
                    cluster.addPoint(point);

                    expandCluster(cluster, neighbours);
                    clusterIndex++;
                }
            }
        }
    }

    //Expand the current cluster by finding all relevant neighbours.
    //If a Point is within mu amount of neighbours of a given point, it's considered a neighbour and added to the list.
    //Continue this until no more points with a minimum of mu amount of neighbours are found.
    private void expandCluster(Cluster cluster, List<Point> neighbours) {
        for (int i = 0; i < neighbours.size(); i++) {
            Point neighbour = neighbours.get(i);

            if (!neighbour.isVisited()) {
                neighbour.setVisited();
                List<Point> newNeighbours = getNeighbours(neighbour);
                if (newNeighbours.size() >= MU) {
                    neighbours.addAll(newNeighbours);
                }
            }
            if (!neighbour.isAssigned()) {
                neighbour.setAssigned(cluster.getId());
                cluster.addPoint(neighbour);
            }
        }
        clusters.add(cluster);
    }

    //Returns a list of neighbours within epsilon of a point.
    private List<Point> getNeighbours(Point p) {
        List<Point> neighbours = new ArrayList<>();

        for (Point point : points) {
            if (euclideanDistance.calculate(p, point) <= EPSILON) {
                neighbours.add(point);
            }
        }
        return neighbours;
    }
}
