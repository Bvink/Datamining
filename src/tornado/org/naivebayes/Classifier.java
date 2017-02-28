package tornado.org.naivebayes;

import tornado.org.naivebayes.objects.Classification;
import tornado.org.naivebayes.objects.Feature;

import java.util.List;
import java.util.Map;

public class Classifier {

    public static final double EPSILON = 1;

    //Classify whether a mushroom belongs to, in this case, the "p" or "e" classification.
    //By going through all the features, one by one, and testing what the highest total probability is, then overwriting this if higher probability has been found for another classification.
    //This means the first check will always succeed, and then every consecutive check is checked against the first one.
    //When the highest probability for a classification has been established, that classification is given to the mushroom and passed on as the "correct" classification.
    //In the case of the feature having 0 occurances for a classification, epsilon will be used instead.
    //In the case of a feature not existing in the feature list, we skip it!
    public Classification classify(List<Object> featuresToClassify, List<Feature> features, Map<Object, Classification> classificationMap) {
        double bestProbability = Double.NEGATIVE_INFINITY;
        double probability;
        Classification bestClassification = null;
        for (Map.Entry<Object, Classification> mapEntry : classificationMap.entrySet()) {
            Classification classification = mapEntry.getValue();
            probability = calcProbability(classification.getFrequency(), classification.getSetsize());
            for (int i = 0; i < featuresToClassify.size(); i++) {
                Object featureToClassify = featuresToClassify.get(i);
                for (Feature feature : features) {
                    if (feature.getName().equals(featureToClassify.toString()) && feature.getIndex() == i) {
                        if (feature.existsInClassificationMap(classification)) {
                            probability += calcProbability(feature.getTotalFrequency(), classification.getSetsize());
                        } else {
                            probability += calcProbability(EPSILON, classification.getSetsize());
                        }
                    }
                }
            }
            if (probability > bestProbability) {
                bestProbability = probability;
                bestClassification = classification;
            }
        }
        return bestClassification;
    }

    private double calcProbability(double numerator, double denominator) {
        return Math.log(numerator / denominator);
    }
}
