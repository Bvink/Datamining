package tornado.org.decisiontree;

import tornado.org.Generic.generators.ClassificationGenerator;
import tornado.org.Generic.generators.FeatureGenerator;
import tornado.org.Generic.generators.IndexGenerator;
import tornado.org.Generic.objects.Classification;
import tornado.org.Generic.objects.Feature;
import tornado.org.Generic.objects.Index;
import tornado.org.Generic.objects.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DecisionTreeAlgorithm {

    private static double log2(double value) {
        return Math.log(value) / Math.log(2);
    }
    private Node rootNode;
    private double setSize;
    private Map<Object, Classification> classificationMap;
    private List<Index> indexes;

    public DecisionTreeAlgorithm(Object[][] dataSet, List<String> header, int TARGET_CLASSIFICATION) {

        rootNode = new Node();

        ClassificationGenerator classificationGenerator = new ClassificationGenerator();
        classificationMap = classificationGenerator.create(dataSet, TARGET_CLASSIFICATION);

        FeatureGenerator featureGenerator = new FeatureGenerator();
        List<Feature> features = featureGenerator.create(dataSet, classificationMap, TARGET_CLASSIFICATION);

        IndexGenerator indexGenerator = new IndexGenerator();
        indexes = indexGenerator.create(header, features, TARGET_CLASSIFICATION);
        this.setSize = dataSet.length;
        double entropy = calcClassificationEntropy();
        calcIndexValues(entropy);
        Index root = getRoot(indexes);

        rootNode.setIndex(root);
        setChildren(root, dataSet, header, TARGET_CLASSIFICATION);
    }

    private double calcClassificationEntropy() {
        double entropy = 0;

        for (Map.Entry<Object, Classification> mapEntry : classificationMap.entrySet()) {
            Classification classification = mapEntry.getValue();
            double percentage = classification.getFrequency() / setSize;
            entropy += -(percentage * log2(percentage));
        }
        return entropy;
    }

    private void calcIndexValues(double entropy) {
        for(Index index : indexes) {
            calcIndexEntropy(index);
            index.setGain(entropy - index.getEntropy());
        }
    }

    private double calcIndexEntropy(Index index) {
        double entropy = 0;
        for(Feature f : index.getFeatures()) {

            entropy += getFeatureFrequency(f) / setSize * calcFeatureEntropy(f);
        }
        index.setEntropy(entropy);
        return entropy;
    }

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
        if(f.isLeaf()) {
            f.setResult(getResult(f));
        }
        return entropy;
    }

    private String getResult(Feature f) {
        for (Map.Entry<Object, Classification> mapEntry : classificationMap.entrySet()) {
            Classification classification = mapEntry.getValue();
            if (f.getClassificationFrequencies().get(classification) != null) {
                return classification.getName();
            }
        }
        return "Something went horribly wrong classifying a leaf.";
    }

    private double getFeatureFrequency(Feature f) {
        double featureFrequency = 0;
        for (int i : f.getClassificationFrequencies().values()) {
            featureFrequency += i;
        }
        return featureFrequency;
    }

    private Index getRoot(List<Index> indexes) {
        Index highestGainIndex = new Index(999, "end");
        highestGainIndex.setGain(0);
        for(Index index : indexes) {
            if(index.getGain() > highestGainIndex.getGain())
                highestGainIndex = index;
        }
        return highestGainIndex;
    }

    public Node getRootNode() {
        return rootNode;
    }

    private void setChildren(Index root, Object[][] dataSet, List<String> header, int TARGET_CLASSIFICATION) {
        for(Feature f : root.getFeatures()) {
            if(!f.isLeaf()) {
                Object[][] subset = getDataSubset(f, dataSet);
                DecisionTreeAlgorithm dt = new DecisionTreeAlgorithm(subset, header, TARGET_CLASSIFICATION);
                rootNode.addChild(dt.getRootNode());
            }
        }
    }

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
