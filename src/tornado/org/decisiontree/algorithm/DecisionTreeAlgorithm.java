package tornado.org.decisiontree.algorithm;

import tornado.org.generic.generators.ClassificationGenerator;
import tornado.org.generic.generators.FeatureGenerator;
import tornado.org.generic.generators.IndexGenerator;
import tornado.org.generic.objects.Classification;
import tornado.org.generic.objects.Feature;
import tornado.org.generic.objects.Index;
import tornado.org.generic.objects.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DecisionTreeAlgorithm {

    private static double log2(double value) {
        return Math.log(value) / Math.log(2);
    }

    private Node currentNode;
    private double setSize;
    private Map<Object, Classification> classificationMap;
    private List<Index> indexes;

    public DecisionTreeAlgorithm(Object[][] dataSet, List<String> header, int TARGET_CLASSIFICATION) {

        currentNode = new Node();

        ClassificationGenerator classificationGenerator = new ClassificationGenerator();
        classificationMap = classificationGenerator.create(dataSet, TARGET_CLASSIFICATION);

        FeatureGenerator featureGenerator = new FeatureGenerator();
        List<Feature> features = featureGenerator.create(dataSet, classificationMap, TARGET_CLASSIFICATION);

        IndexGenerator indexGenerator = new IndexGenerator();
        indexes = indexGenerator.create(header, features, TARGET_CLASSIFICATION);
        this.setSize = dataSet.length;
        double entropy = calcClassificationEntropy();
        calcIndexValues(entropy);
        Index highestGainIndex = getHighestGainIndex(indexes);

        currentNode.setIndex(highestGainIndex);
        setChildren(highestGainIndex, dataSet, header, TARGET_CLASSIFICATION);
    }

    //Look, it's a getter; Returns this tree's node.
    public Node getCurrentNode() {
        return currentNode;
    }

    //Calculate the initial entropy for the classification.
    private double calcClassificationEntropy() {
        double entropy = 0;

        for (Map.Entry<Object, Classification> mapEntry : classificationMap.entrySet()) {
            Classification classification = mapEntry.getValue();
            double percentage = classification.getFrequency() / setSize;
            entropy += -(percentage * log2(percentage));
        }
        return entropy;
    }

    //Calculate the entropy and gain for each Index.
    private void calcIndexValues(double entropy) {
        for (Index index : indexes) {
            calcIndexEntropy(index);
            index.setGain(entropy - index.getEntropy());
        }
    }

    //Calculate the entropy for a single Index.
    private double calcIndexEntropy(Index index) {
        double entropy = 0;
        for (Feature f : index.getFeatures()) {

            entropy += getFeatureFrequency(f) / setSize * calcFeatureEntropy(f);
        }
        index.setEntropy(entropy);
        return entropy;
    }

    //Calculate the entropy for a single Feature.
    //Furthermore, set the Feature as a leaf if the entropy results in zero.
    //Finally, assign the Feature a result value (Based on the target classification) if it is a Leaf.
    private double calcFeatureEntropy(Feature f) {
        double entropy = 0;
        for (Map.Entry<Object, Classification> mapEntry : classificationMap.entrySet()) {
            Classification classification = mapEntry.getValue();
            if (f.getClassificationFrequencies().get(classification) != null) {
                double percentage = f.getClassificationFrequencies().get(classification) / getFeatureFrequency(f);
                entropy += -(percentage * log2(percentage));
            }
        }
        f.setEntropy(entropy);
        if (f.isLeaf()) {
            f.setResult(getResult(f));
        }
        return entropy;
    }

    //Grab the target result of a leaf, depending on which classification isn't "zero" (read: null).
    private String getResult(Feature f) {
        for (Map.Entry<Object, Classification> mapEntry : classificationMap.entrySet()) {
            Classification classification = mapEntry.getValue();
            if (f.getClassificationFrequencies().get(classification) != null) {
                return classification.getName();
            }
        }
        return "Something went horribly wrong classifying a leaf.";
    }

    //Get the total amount of times a feature is present in the data set.
    private double getFeatureFrequency(Feature f) {
        double featureFrequency = 0;
        for (int i : f.getClassificationFrequencies().values()) {
            featureFrequency += i;
        }
        return featureFrequency;
    }

    //Return the Index with the highest gain value.
    //If nothing has a gain over 0 (There is no gain), return end.
    //At this point, a tree simply cannot be classified.
    private Index getHighestGainIndex(List<Index> indexes) {
        Index highestGainIndex = new Index(999, "end");
        highestGainIndex.setGain(0);
        for (Index index : indexes) {
            if (index.getGain() > highestGainIndex.getGain())
                highestGainIndex = index;
        }
        return highestGainIndex;
    }

    //For each feature that isn't a leaf, do the entire thing over again.
    private void setChildren(Index root, Object[][] dataSet, List<String> header, int TARGET_CLASSIFICATION) {
        for (Feature f : root.getFeatures()) {
            if (!f.isLeaf()) {
                Object[][] subset = getDataSubset(f, dataSet);
                DecisionTreeAlgorithm dt = new DecisionTreeAlgorithm(subset, header, TARGET_CLASSIFICATION);
                Node childNode = dt.getCurrentNode();
                childNode.setPreviousFeature(f);
                currentNode.addChild(childNode);
            }
        }
    }

    //Subset the training set to only return the rows that include a given feature at its given Index.
    private Object[][] getDataSubset(Feature f, Object[][] dataSet) {
        Object[][] subset;
        List<Object[]> list = new ArrayList<>();
        for (Object[] row : dataSet) {
            if (row[f.getIndex()].equals(f.getName())) {
                list.add(row);
            }
        }
        subset = new Object[list.size()][];
        list.toArray(subset);
        return subset;
    }
}
