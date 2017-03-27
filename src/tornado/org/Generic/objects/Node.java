package tornado.org.generic.objects;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Index index;                                        //The Index tied to this Node, the Index with the highest gain.
    private Feature previousFeature;                            //Remember the branch feature that this Node is connected to.
    private List<Node> children;                                //If the Index contains a Feature that is not a Leaf, a child Node will be added after the next Index with the highest gain is calculated.
                                                                // Multiple children can exist, as multiple Features can be "not leaves".

    public Node() {
        this.children = new ArrayList<>();
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public Feature getPreviousFeature() {
        return previousFeature;
    }

    public void setPreviousFeature(Feature previousFeature) {
        this.previousFeature = previousFeature;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }
}
