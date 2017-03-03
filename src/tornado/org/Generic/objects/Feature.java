package tornado.org.Generic.objects;

import java.util.HashMap;
import java.util.Map;

public class Feature {
    private String name;                                                                    //Name of the feature
    private String result;                                                                  //Classification in case entropy is 0, only used in Decision Tree.
    private int index;                                                                      //Current index of the feature, features can have the same name but different indexes.
    private double entropy;                                                                 //Entropy of the feature, only used in Decision Tree.
    private boolean leaf;                                                                   //Indication if the feature is a leaf, only used in Decision Tree.
    private final Map<Classification, Integer> classificationFrequencies = new HashMap<>(); //Total number of the feature in combination with the classification it's attached to.

    public Feature(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getIndex() {
        return index;
    }

    public double getEntropy() {
        return entropy;
    }

    public void setEntropy(double entropy) {
        this.entropy = entropy;
        if(entropy == 0) {
            this.leaf = true;
        }
    }

    public boolean isLeaf() {
        return leaf;
    }

    public Map<Classification, Integer> getClassificationFrequencies() {
        return classificationFrequencies;
    }

    public void addToClassificationMap(Classification classification) {
        classificationFrequencies.put(classification, 1);
    }

    public boolean existsInClassificationMap(Classification key) {
        return classificationFrequencies.containsKey(key);
    }

    public void incrementFoundClassification(Classification currentClassification) {
        classificationFrequencies.put(currentClassification, classificationFrequencies.get(currentClassification) + 1);
    }

}
