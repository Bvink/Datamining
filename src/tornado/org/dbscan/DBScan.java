package tornado.org.dbscan;

import tornado.org.generic.generators.graph.ClusterGenerator;
import tornado.org.generic.generators.graph.PointGenerator;
import tornado.org.generic.objects.graph.Cluster;
import tornado.org.generic.objects.graph.Point;
import tornado.org.util.DataSetParser;
import tornado.org.util.ResultFileWriter;

import java.util.List;

public class DBScan {

    public static final int MU = 10;
    private static final double EPSILON = 0.15;
    private DataSetParser dataSetParser = new DataSetParser("data/stars.csv");

    public void init() {
        Object[][] dataSet = dataSetParser.getData();

        PointGenerator pointGenerator = new PointGenerator();
        List<Point> points = pointGenerator.create(dataSet);

        ClusterGenerator clusterGenerator = new ClusterGenerator(MU, EPSILON, points);
        List<Cluster> clusters = clusterGenerator.create(points);

        System.out.println("Total amount of clusters: " + clusters.size());

        Plotter plotter = new Plotter();

        try {
            plotter.draw(clusters, points);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultFileWriter.writeDBScanResults(clusters, points);
    }


}
