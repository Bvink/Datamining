package tornado.org.util;

import tornado.org.generic.objects.graph.Point;

public class EuclideanDistance {

    public double calculate(Point pointOne, Point pointTwo) {
        double x = distance(pointOne.getX(), pointTwo.getX());
        double y = distance(pointOne.getY(), pointTwo.getY());
        double z = distance(pointOne.getZ(), pointTwo.getZ());
        return Math.sqrt(x + y + z);
    }

    private double distance(double a, double b) {
        return Math.pow((a - b), 2);
    }
}
