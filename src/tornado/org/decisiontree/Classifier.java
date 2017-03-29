package tornado.org.decisiontree;

import tornado.org.generic.objects.Classification;
import tornado.org.generic.objects.Feature;
import tornado.org.generic.objects.Node;

import java.util.List;


public class Classifier {

    //Classify whether a mushroom belongs to, in this case, the "p" or "e" classification.
    //This is done by going through the rootNode, grabbing the Index of its feature, and then going through the features at that index.
    //It then matches the possible features at that index with the current mushroom's feature at the same index.
    //If it finds out that it does not end up at a leaf, it'll iterate through the node's children, and find the correct child Node that is compatible with the current branch.
    //It'll then recursively keep doing this until it reaches a leaf.
    //Finally, it'll return the predicted result of that leaf.
    public Classification classify(List<Object> featuresToClassify, Node node) {
        for (Feature f : node.getIndex().getFeatures()) {
            if (f.getName().equals(getCurrentFeatureName(featuresToClassify, node.getIndex().getIndex()))) {
                if (f.isLeaf()) {
                    return new Classification(f.getResult());
                } else {
                    return findFeatureToClassify(node, f, featuresToClassify);
                }
            }
        }
        return new Classification("404");
    }

    //Returns the name of the feature to be classified, at the requested index.
    private String getCurrentFeatureName(List<Object> features, int index) {
        return (String) features.get(index);
    }

    //If we don't arrive at a leaf, we follow the tree down to the next node.
    //The next node, however, depends on the feature we're splitting off from.
    private Classification findFeatureToClassify(Node node, Feature f, List<Object> featuresToClassify) {
        for (Node n : node.getChildren()) {
            if (n.getPreviousFeature() == null || n.getPreviousFeature() == f) {
                return classify(featuresToClassify, n);
            }
        }
        return new Classification("404");
    }
}


