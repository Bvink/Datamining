package tornado.org.generic.objects;

import java.util.ArrayList;
import java.util.List;

public class Index {

    private int index;                                              //Index of the selected row, the location at which this Index exists inside a row.
    private String name;                                            //Name of the Index.
    private double entropy;                                         //The Index's individual Entropy.
    private double gain;                                            //The calculated gain of the Index.
    private final List<Feature> features;                           //Every single Feature can exist at this Index on a selected row.

    public Index(int index, String name) {
        this.index = index;
        this.name = name;
        this.features = new ArrayList<>();
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
