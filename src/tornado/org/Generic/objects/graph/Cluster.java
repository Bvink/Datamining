package tornado.org.generic.objects.graph;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

    private int id;
    private List<Point> points;

    public Cluster(int id) {
        this.id = id;
        points = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }
}
