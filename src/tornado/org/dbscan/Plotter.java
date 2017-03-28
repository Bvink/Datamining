package tornado.org.dbscan;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import tornado.org.generic.objects.graph.Cluster;
import tornado.org.generic.objects.graph.Point;
import tornado.org.util.ColorUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plotter {

    private static final boolean SHOW_OUTLIERS = true;

    public void draw(List<Cluster> clusters, List<Point> points) throws Exception {

        Coord3d[] coord3dsPoints = new Coord3d[getSize(clusters, points)];
        Color[] colors = new Color[getSize(clusters, points)];
        Map<Cluster, Color> colorMap = generateColors(clusters);

        int i = 0;
        for (Point point : points) {
            if(SHOW_OUTLIERS || point.getClusterID() != -1) {
                coord3dsPoints[i] = new Coord3d(point.getX(), point.getY(), point.getZ());
                colors[i] = getColor(colorMap, clusters, point);
                i++;
            }
        }
        generateScatterPlot(coord3dsPoints, colors);
    }

    //Generate a scatterplot using jz3d and jogl.
    private void generateScatterPlot(Coord3d[] coord3dsPoints, Color[] colors) throws Exception {
        Scatter scatter = new Scatter(coord3dsPoints, colors);
        AnalysisLauncher.open(new AbstractAnalysis() {
            @Override
            public void init() throws Exception {
                chart = AWTChartComponentFactory.chart(Quality.Nicest);
                chart.getScene().add(scatter);
            }
        });

    }

    //Generate a map of visually distinct colours using ColorUtils by Melinda Green.
    //Add the default colour "Black" for outliers.
    //Bind every generated color to a cluster.
    private Map<Cluster, Color> generateColors(List<Cluster> clusters) {
        Map<Cluster, Color> colorMap = new HashMap<>();
        colorMap.put(null, new Color(0, 0, 0));
        ColorUtils cu = new ColorUtils();
        java.awt.Color[] colors = cu.generateVisuallyDistinctColors(clusters.size(), .8f, .3f);
        int i = 0;
        for (Cluster cluster : clusters) {
            colorMap.put(cluster, new Color(colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue(), colors[i].getAlpha()));
            i++;
        }
        return colorMap;
    }

    //Find which color belongs to a point, depending on which cluster it belongs to.
    private Color getColor(Map<Cluster, Color> colorMap, List<Cluster> clusters, Point point) {
        for (Cluster cluster : clusters) {
            if (point.getClusterID() == cluster.getId()) {
                return colorMap.get(cluster);
            }
        }
        return colorMap.get(null);
    }

    //Return the amount of points that will be displayed, depending on whether outliers will be shown or not.
    private int getSize(List<Cluster> clusters, List<Point> points) {
        if(SHOW_OUTLIERS) {
            return points.size();
        } else {
            return clusteredStarSize(clusters);
        }
    }

    //Returns the amount of stars that belong to a cluster.
    private int clusteredStarSize(List<Cluster> clusters) {
        int sum = 0;
        for (Cluster cluster : clusters) {
            sum += cluster.getPoints().size();
        }
        return sum;
    }

}
