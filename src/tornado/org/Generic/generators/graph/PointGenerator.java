package tornado.org.generic.generators.graph;

import tornado.org.generic.objects.graph.Point;

import java.util.ArrayList;
import java.util.List;

public class PointGenerator {

    public List<Point> create(Object[][] dataSet) {
        List<Point> points = new ArrayList<Point>();

        int i = 0;
        for (Object[] row : dataSet) {
            Point point = new Point(i, getVal(row[0]), getVal(row[1]), getVal(row[2]));
            points.add(point);
            i++;
        }
        return points;
    }

    //Return the value from an object as a double.
    private double getVal(Object obj) {
        double d = 0d;
        try {
            d = Double.parseDouble(obj.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return d;
    }
}
