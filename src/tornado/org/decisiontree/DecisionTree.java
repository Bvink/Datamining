package tornado.org.decisiontree;


import tornado.org.decisiontree.algorithm.DecisionTreeAlgorithm;
import tornado.org.generic.objects.Classification;
import tornado.org.generic.objects.Node;
import tornado.org.util.DataSetParser;
import tornado.org.util.ResultFileWriter;
import tornado.org.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecisionTree {

    public static final int TARGET_CLASSIFICATION = 0;
    public static final long SEED = 1;
    private static final double trainingSetPercentage = 0.33;
    DataSetParser dataSetParser = new DataSetParser("data/mushrooms.csv");

    public void init() {
        Object[][] dataSet = dataSetParser.getData();
        Object[][] trainingData = dataSetParser.createTrainingSet(SEED, trainingSetPercentage);

        DecisionTreeAlgorithm dt = new DecisionTreeAlgorithm(trainingData, dataSetParser.getHeader(), TARGET_CLASSIFICATION);
        Node rootNode = dt.getCurrentNode();

        accuracyTest(dataSet, rootNode);

        //TreePrinter printer = new TreePrinter();
        //printer.print(rootNode, "");
        ResultFileWriter.writeDecisionTree(rootNode);
    }

    private void accuracyTest(Object[][] dataSet, Node rootNode) {
        double correct = 0;

        List<String> results = new ArrayList<>();
        Classifier classifier = new Classifier();

        for (Object[] row : dataSet) {
            Classification classifiedRow = classifier.classify(Arrays.asList(row), rootNode);
            StringBuilder sb = new StringBuilder(classifiedRow.getName());
            if (classifiedRow.getName().equals(row[TARGET_CLASSIFICATION])) {
                correct++;
                sb.append(", ✓");
            } else {
                sb.append(", ✕");
            }
            results.add(sb.toString());
        }
        System.out.println(Util.accuracy("Decision Tree", correct, dataSet.length));
        ResultFileWriter.writeDecisionTreeResults(results, dataSet);
    }

}
