package tornado.org.util;

import tornado.org.generic.objects.Feature;
import tornado.org.generic.objects.Node;

public class TreePrinter {

    public static final String SPACING = "\t";

    public void print(Node node, String spacing) {
        System.out.print(SPACING);
        printTree(node, spacing);
    }

    private void printTree(Node node, String spacing) {
        spacing = spacing + SPACING;
        int count = 0;
        System.out.print(node.getIndex().getName());
        System.out.println();
        for (Feature f : node.getIndex().getFeatures()) {
            if (f.isLeaf()) {
                System.out.println(spacing + f.getName() + " -> " + f.getResult());
            } else {
                System.out.print(spacing + f.getName() + " > ");
                printTree(node.getChildren().get(count), spacing);
                count++;
            }
        }
    }

}
