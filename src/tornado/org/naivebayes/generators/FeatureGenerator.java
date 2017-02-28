package tornado.org.naivebayes.generators;

import tornado.org.naivebayes.NaiveBayes;
import tornado.org.naivebayes.objects.Classification;
import tornado.org.naivebayes.objects.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeatureGenerator {

    //Checks if a feature exists.
    //If not, add that feature and then move on to check how many times it occurs for our classifications( "p" and "e", in this case).
    //Increment the feature's classification count each time it's found.
    public List<Feature> create(Object[][] dataSet, Map<Object, Classification> classificationMap) {
        final int TARGET_CLASSIFICATION = NaiveBayes.TARGET_CLASSIFICATION;
        List<Feature> features = new ArrayList<>();

        for (Object[] row : dataSet) {
            for (int i = 0; i < row.length; i++) {
                Feature feature = new Feature();
                feature.setName(row[i].toString());
                feature.setIndex(i);
                Classification currentClassification = classificationMap.get(row[TARGET_CLASSIFICATION]);

                if (!featureExistsInList(features, feature) && i != TARGET_CLASSIFICATION) {
                    feature.addToClassificationMap(currentClassification);
                    features.add(feature);
                } else {
                    Feature foundFeature = getFeatureFromFeatures(features, feature);
                    if (foundFeature.existsInClassificationMap(currentClassification)) {
                        foundFeature.incrementFoundClassification(currentClassification);
                    } else {
                        foundFeature.addToClassificationMap(currentClassification);
                    }
                    foundFeature.setTotalFrequency();
                }

            }
        }
        return features;
    }

    //Check if a feature already exists.
    private boolean featureExistsInList(List<Feature> features, Feature targetFeature) {
        for (Feature feature : features) {
            if (feature.getName().equals(targetFeature.getName()) && feature.getIndex() == targetFeature.getIndex()) {
                return true;
            }
        }
        return false;
    }

    //Retrieve an existing feature by its name and position in the row.
    //Both these keys are important, as several features may have the same name but a different location.
    private Feature getFeatureFromFeatures(List<Feature> features, Feature targetFeature) {
        for (Feature feature : features) {
            if (feature.getName().equals(targetFeature.getName()) && feature.getIndex() == targetFeature.getIndex()) {
                return feature;
            }
        }
        return new Feature();
    }
}
