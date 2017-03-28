package tornado.org.util;

import tornado.org.generic.objects.Feature;
import tornado.org.generic.objects.Node;
import tornado.org.generic.objects.graph.Cluster;
import tornado.org.generic.objects.graph.Point;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResultFileWriter {

    public static final String LOCATION = "res/";
    public static final String SPACING = "\t";

    public static void writeNaiveBayesResults(List<String> results, Object[][] dataSet) {
        try {
            PrintWriter writer = new PrintWriter(LOCATION + "NaiveBayesResults", "UTF-8");
            writeResults(writer, results, dataSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDecisionTreeResults(List<String> results, Object[][] dataSet) {
        try {
            PrintWriter writer = new PrintWriter(LOCATION + "DecisionTreeResults", "UTF-8");
            writeResults(writer, results, dataSet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeResults(PrintWriter writer, List<String> results, Object[][] dataSet) {
        int i = 0;
        for (Object[] row : dataSet) {
            StringBuilder sb = new StringBuilder();
            sb.append(results.get(i));
            sb.append(", ");
            for (Object s : row) {
                sb.append(s.toString());
                sb.append(", ");
            }
            writer.println(sb.toString());
            i++;
        }
        writer.close();
    }

    public static void writeDecisionTree(Node node) {
        try {
            PrintWriter writer = new PrintWriter(LOCATION + "DecisionTree", "UTF-8");

            treeWriter(writer, node, "");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void treeWriter(PrintWriter writer, Node node, String spacing) {

        spacing = spacing + SPACING;
        int count = 0;
        writer.println(spacing + node.getIndex().getName());
        for (Feature f : node.getIndex().getFeatures()) {
            if (f.isLeaf()) {
                writer.println(spacing + f.getName() + " -> " + f.getResult());
            } else {
                writer.println(spacing + f.getName() + " -> " + "NOT A LEAF!");
                treeWriter(writer, node.getChildren().get(count), spacing);
                count++;
            }
        }
    }

    public static void writeDBScanResults(List<Cluster> clusters, List<Point> points) {
        try {
            PrintWriter writer = new PrintWriter(LOCATION + "DBScanResults", "UTF-8");

            for (Cluster cluster : clusters) {
                writer.println("Cluster ID: " + cluster.getId());
                for (Point point : cluster.getPoints()) {
                    writer.println(SPACING + "ID: " + point.getId() + ", Location: " + point.getLocation());
                }
            }
            writer.println("Cluster ID: OUTLIERS");
            for (Point point : points) {
                if (point.getClusterID() == -1) {
                    writer.println(SPACING + "ID: " + point.getId() + ", Location: " + point.getLocation());
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
