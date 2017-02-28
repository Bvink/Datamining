package tornado.org.naivebayes;

import tornado.org.naivebayes.generators.ClassificationGenerator;
import tornado.org.naivebayes.generators.FeatureGenerator;
import tornado.org.naivebayes.objects.Classification;
import tornado.org.naivebayes.objects.Feature;
import tornado.org.util.DataSetParser;

import java.util.*;

public class NaiveBayes {
    public static final int TARGET_CLASSIFICATION = 0;
    public static final long SEED = 17567;
    private static final double trainingSetPercentage = 0.33;
    DataSetParser dataSetParser = new DataSetParser("mushrooms.csv");

    public void init() {
        double correct = 0;
        Object[][] dataSet = dataSetParser.getData();
        Object[][] trainingData = dataSetParser.createTrainingDataSet(SEED, trainingSetPercentage);

        ClassificationGenerator classificationGenerator = new ClassificationGenerator();
        Map<Object, Classification> classificationMap = classificationGenerator.create(trainingData);

        FeatureGenerator featureGenerator = new FeatureGenerator();
        List<Feature> features = featureGenerator.create(trainingData, classificationMap);

        Classifier classifier = new Classifier();

        for (Object[] row : dataSet) {
            Classification ClassifiedRow = classifier.classify(Arrays.asList(row), features, classificationMap);

            if (ClassifiedRow.getName().equals(row[TARGET_CLASSIFICATION])) {
                correct++;
            }
        }
        System.out.println("Accuracy: " + (correct / dataSet.length));
    }

}
