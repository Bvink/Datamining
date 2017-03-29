package tornado.org.naivebayes;

import tornado.org.generic.generators.ClassificationGenerator;
import tornado.org.generic.generators.FeatureGenerator;
import tornado.org.generic.objects.Classification;
import tornado.org.generic.objects.Feature;
import tornado.org.util.DataSetParser;
import tornado.org.util.ResultFileWriter;
import tornado.org.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NaiveBayes {

    public static final int TARGET_CLASSIFICATION = 0;
    public static final long SEED = 1;
    private static final double trainingSetPercentage = 0.33;
    DataSetParser dataSetParser = new DataSetParser("data/mushrooms.csv");

    public void init() {
        Object[][] dataSet = dataSetParser.getData();
        Object[][] trainingData = dataSetParser.createTrainingSet(SEED, trainingSetPercentage);

        ClassificationGenerator classificationGenerator = new ClassificationGenerator();
        Map<Object, Classification> classificationMap = classificationGenerator.create(trainingData, TARGET_CLASSIFICATION);

        FeatureGenerator featureGenerator = new FeatureGenerator();
        List<Feature> features = featureGenerator.create(trainingData, classificationMap, TARGET_CLASSIFICATION);

        accuracyTest(trainingData.length, features, classificationMap, dataSet);
    }

    private void accuracyTest(int trainingDataLength, List<Feature> features, Map<Object, Classification> classificationMap, Object[][] dataSet) {
        double correct = 0;

        List<String> results = new ArrayList<>();
        Classifier classifier = new Classifier(trainingDataLength);

        for (Object[] row : dataSet) {
            Classification classifiedRow = classifier.classify(Arrays.asList(row), features, classificationMap);
            StringBuilder sb = new StringBuilder(classifiedRow.getName());
            if (classifiedRow.getName().equals(row[TARGET_CLASSIFICATION])) {
                correct++;
                sb.append(", ✓");
            } else {
                sb.append(", ✕");
            }
            results.add(sb.toString());
        }
        System.out.println(Util.accuracy("Naive Bayes", correct, dataSet.length));
        ResultFileWriter.writeNaiveBayesResults(results, dataSet);
    }

}
