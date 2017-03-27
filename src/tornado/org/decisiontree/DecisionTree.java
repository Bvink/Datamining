package tornado.org.decisiontree;


import tornado.org.decisiontree.algorithm.DecisionTreeAlgorithm;
import tornado.org.generic.objects.Classification;
import tornado.org.generic.objects.Node;
import tornado.org.util.DataSetParser;
import tornado.org.util.TreePrinter;
import tornado.org.util.Util;

import java.util.Arrays;

public class DecisionTree {

    public static final int TARGET_CLASSIFICATION = 0;
    public static final long SEED = 1;
    private static final double trainingSetPercentage = 0.33;
    DataSetParser dataSetParser = new DataSetParser("mushrooms.csv");

    public void init() {
        Object[][] dataSet = dataSetParser.getData();
        Object[][] trainingData = dataSetParser.createSubSet(SEED, trainingSetPercentage);

        DecisionTreeAlgorithm dt = new DecisionTreeAlgorithm(trainingData, dataSetParser.getHeader(), TARGET_CLASSIFICATION);
        Node rootNode = dt.getCurrentNode();

        accuracyTest(dataSet, rootNode);

        TreePrinter tp = new TreePrinter();
        tp.print(rootNode, "");
    }

    private void accuracyTest(Object[][] dataSet, Node rootNode) {
        int correct = 0;
        Classifier classifier = new Classifier();

        for (Object[] row : dataSet) {
            Classification classifiedRow = classifier.classify(Arrays.asList(row), rootNode);
            if (classifiedRow.getName().equals(row[TARGET_CLASSIFICATION])) {
                correct++;
            }
        }
        System.out.println(Util.accuracy("Decision Tree", correct, dataSet.length));
    }

}
