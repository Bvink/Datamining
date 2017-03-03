package tornado.org.Generic.objects;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Index index;
    private List<Node> children = new ArrayList<Node>();

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }
}
