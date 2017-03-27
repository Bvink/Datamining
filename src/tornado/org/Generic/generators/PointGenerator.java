package tornado.org.generic.generators;

import tornado.org.generic.objects.graph.Point;

import java.util.ArrayList;
import java.util.List;

public class PointGenerator {

    public List<Point> create(Object[][] dataSet) {
        List<Point> points = new ArrayList<Point>();

        for (Object[] row : dataSet) {
            Point point = new Point(getVal(row[0]), getVal(row[1]), getVal(row[2]));
            points.add(point);
        }
        return points;
    }

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
