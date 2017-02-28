package tornado.org.naivebayes.objects;

import java.util.HashMap;
import java.util.Map;

public class Feature {
    private String name;
    private int index;
    private int totalFrequency;
    private final Map<Classification, Integer> classificationMap = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTotalFrequency() {
        return totalFrequency;
    }

    public void setTotalFrequency() {
        totalFrequency = 0;
        for (int value : classificationMap.values()) {
            totalFrequency += value;
        }
    }

    public Map<Classification, Integer> getClassificationMap() {
        return classificationMap;
    }

    public void addToClassificationMap(Classification classification) {
        classificationMap.put(classification, 1);
    }

    public boolean existsInClassificationMap(Classification key) {
        return classificationMap.containsKey(key);
    }

    public void incrementFoundClassification(Classification currentClassification) {
        classificationMap.put(currentClassification, classificationMap.get(currentClassification) + 1);
    }

    public void increaseFrequencyExistingClassification(Classification key) {
        classificationMap.get(key);
    }

}
