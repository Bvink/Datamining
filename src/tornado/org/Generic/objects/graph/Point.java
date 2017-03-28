package tornado.org.generic.objects.graph;

public class Point {

    private int id;
    private double x, y, z;
    private boolean visited = false;
    private boolean assigned = false;
    private int clusterID = -1;

    public Point(int id, double x, double y, double z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return getX() + ", " + getY() + ", " + getX();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        this.visited = true;
    }

    public void setNoise() {
        this.visited = false;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(int clusterID) {
        this.assigned = true;
        this.clusterID = clusterID;
    }

    public int getClusterID() {
        return clusterID;
    }
}
