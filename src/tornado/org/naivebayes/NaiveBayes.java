package tornado.org.naivebayes;

import tornado.org.generic.generators.ClassificationGenerator;
import tornado.org.generic.generators.FeatureGenerator;
import tornado.org.generic.objects.Classification;
import tornado.org.generic.objects.Feature;
import tornado.org.util.DataSetParser;
import tornado.org.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NaiveBayes {
    public static final int TARGET_CLASSIFICATION = 0;
    public static final long SEED = 1;
    private static final double trainingSetPercentage = 0.33;
    DataSetParser dataSetParser = new DataSetParser("mushrooms.csv");

    public void init() {
        double correct = 0;
        Object[][] dataSet = dataSetParser.getData();
        Object[][] trainingData = dataSetParser.createSubSet(SEED, trainingSetPercentage);

        ClassificationGenerator classificationGenerator = new ClassificationGenerator();
        Map<Object, Classification> classificationMap = classificationGenerator.create(trainingData, TARGET_CLASSIFICATION);

        FeatureGenerator featureGenerator = new FeatureGenerator();
        List<Feature> features = featureGenerator.create(trainingData, classificationMap, TARGET_CLASSIFICATION);

        Classifier classifier = new Classifier(trainingData.length);

        for (Object[] row : dataSet) {
            Classification ClassifiedRow = classifier.classify(Arrays.asList(row), features, classificationMap);

            if (ClassifiedRow.getName().equals(row[TARGET_CLASSIFICATION])) {
                correct++;
            }
        }
        System.out.println(Util.accuracy("Naive Bayes", correct, dataSet.length));
    }

}
