package tornado.org.Generic.objects;

import java.util.ArrayList;
import java.util.List;

public class Index {

    private int index;
    private String name;
    private double entropy;
    private double gain;
    private final List<Feature> features = new ArrayList<Feature>();

    public Index(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public double getEntropy() {
        return entropy;
    }

    public void setEntropy(double entropy) {
        this.entropy = entropy;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void addFeature(Feature feature) {
        features.add(feature);
    }
}
