package tornado.org.naivebayes;

import tornado.org.generic.objects.Classification;
import tornado.org.generic.objects.Feature;

import java.util.List;
import java.util.Map;

public class Classifier {

    public static final double EPSILON = 1;
    private int trainingSetSize;

    public Classifier(int trainingSetSize) {
        this.trainingSetSize = trainingSetSize;
    }

    //Classify whether a mushroom belongs to, in this case, the "p" or "e" classification.
    //By going through all the features, one by one, and testing what the highest total probabilityLog is, then overwriting this if higher probabilityLog has been found for another classification.
    //This means the first check will always succeed, and then every consecutive check is checked against the previous best one.
    //When the highest probability for a classification has been established, that classification is given to the mushroom and passed on as the "correct" classification.
    //This is done by taking the log() of the classification count divided by the total, added to the sum of log() of the frequency of the feature for that classification divided by the total of the classification count, of each feature.
    //In the case of the feature having 0 occurrences for a classification, epsilon will be used instead. https://www.youtube.com/watch?v=mFaxEvc1Jr0
    //In the case of a feature not existing in the feature list, we skip it! https://www.youtube.com/watch?v=EqjyLfpv5oA
    public Classification classify(List<Object> featuresToClassify, List<Feature> features, Map<Object, Classification> classificationMap) {
        double bestProbability = Double.NEGATIVE_INFINITY;
        double probability;
        Classification bestClassification = null;

        for (Map.Entry<Object, Classification> mapEntry : classificationMap.entrySet()) {
            Classification classification = mapEntry.getValue();

            probability = initialProbability(classification);
            probability += getTotalProbability(featuresToClassify, features, classification);

            if (probability > bestProbability) {
                bestProbability = probability;
                bestClassification = classification;
            }
        }
        return bestClassification;
    }

    //Calculate the initial probability, by using the total frequency of the total classification and the size of the training set.
    private double initialProbability(Classification classification) {
        return probabilityLog(classification.getFrequency(), trainingSetSize);
    }

    //Calculate the probability of all features combined.
    private double getTotalProbability(List<Object> featuresToClassify, List<Feature> features, Classification classification) {
        int sum = 0;
        for (int i = 0; i < featuresToClassify.size(); i++) {
            String featureToClassify = getCurrentFeatureName(featuresToClassify, i);
            for (Feature feature : features) {
                if (feature.getName().equals(featureToClassify) && feature.getIndex() == i) {
                    sum += calculateProbability(feature, classification);
                }
            }
        }
        return sum;
    }

    //Returns the name of the feature to be classified, at the requested index.
    private String getCurrentFeatureName(List<Object> features, int index) {
        return (String) features.get(index);
    }

    //If the feature has a classification (it existed in the training set), increment the probabilityLog based on that.
    //Otherwise, increment the probabilityLog by epsilon.
    private double calculateProbability(Feature feature, Classification classification) {
        if (feature.existsInClassificationMap(classification)) {
            return probabilityLog(feature.getClassificationFrequencies().get(classification), classification.getFrequency());
        } else {
            return probabilityLog(EPSILON, classification.getFrequency());
        }
    }

    //Returns the log of a probability.
    private double probabilityLog(double numerator, double denominator) {
        return Math.log(numerator / denominator);
    }
}
