package tornado.org.util;

import tornado.org.generic.objects.Feature;
import tornado.org.generic.objects.Node;

public class TreePrinter {

    public void print(Node node, String spacing) {
        spacing = spacing + "\t";
        int count = 0;
        System.out.println(spacing + node.getIndex().getName());
        for (Feature f : node.getIndex().getFeatures()) {
            if (f.isLeaf()) {
                System.out.println(spacing + f.getName() + " -> " + f.getResult());
            } else {
                System.out.println(spacing + f.getName() + " -> " + "NOT A LEAF!");
                print(node.getChildren().get(count), spacing);
                count++;
            }
        }
    }

}
