package tornado.org.decisiontree;

import tornado.org.generic.objects.Classification;
import tornado.org.generic.objects.Feature;
import tornado.org.generic.objects.Node;

import java.util.List;

//Classify whether a mushroom belongs to, in this case, the "p" or "e" classification.
//This is done by going through the rootNode, grabbing the local Index, and then going through its Features.
//If it finds out that it does not end up at a leaf, it'll iterate through the node's children, and find the correct child Node that is compatible with the current branch.
//It'll then recursively keep doing this until it reaches a leaf.
//Finally, it'll return the predicted result of that leaf.
public class Classifier {

    public Classification classify(List<Object> featuresToClassify, Node node) {
        for (Feature f : node.getIndex().getFeatures()) {
            if (f.getName().equals(featuresToClassify.get(node.getIndex().getIndex()))) {
                if (f.isLeaf()) {
                    return new Classification(f.getResult());
                } else {
                    for (Node n : node.getChildren()) {
                        if (n.getPreviousFeature() == null || n.getPreviousFeature() == f) {
                            return classify(featuresToClassify, n);
                        }
                    }
                }
            }
        }
        return new Classification("404");
    }
}
