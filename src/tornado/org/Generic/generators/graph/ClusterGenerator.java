package tornado.org.generic.generators.graph;

import tornado.org.generic.objects.graph.Cluster;
import tornado.org.generic.objects.graph.Point;
import tornado.org.util.EuclideanDistance;

import java.util.ArrayList;
import java.util.List;

public class ClusterGenerator {

    private int mu;
    private double epsilon;
    private List<Point> points;
    private List<Cluster> clusters;


    private EuclideanDistance euclideanDistance = new EuclideanDistance();

    public ClusterGenerator(int mu, double epsilon, List<Point> points) {
        this.mu = mu;
        this.epsilon = epsilon;
        this.points = points;
        clusters = new ArrayList<Cluster>();
    }

    //Create a new cluster.
    //If the cluster's beginning point does not have at least a mu amount of neighbours;
    //Mark that point as noise, and move on to the next potential starting point.
    //Otherwise, initiate and expand the cluster.
    public List<Cluster> create(List<Point> points) {
        int clusterIndex = 0;

        for (Point point : points) {
            if (!point.isVisited()) {
                point.setVisited();
                List<Point> neighbours = getNeighbours(point);
                if (neighbours.size() < mu) {
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

        return clusters;
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
                if (newNeighbours.size() >= mu) {
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
            if (euclideanDistance.calculate(p, point) <= epsilon) {
                neighbours.add(point);
            }
        }
        return neighbours;
    }
}
