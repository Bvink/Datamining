package tornado.org.decisiontree;


import tornado.org.Generic.objects.Feature;
import tornado.org.Generic.objects.Node;
import tornado.org.util.DataSetParser;
import tornado.org.util.TreePrinter;

public class DecisionTree {

    public static final int TARGET_CLASSIFICATION = 0;
    public static final long SEED = 1;
    private static final double trainingSetPercentage = 0.33;
    DataSetParser dataSetParser = new DataSetParser("mushrooms.csv");

    public void init() {
        double correct = 0;
        Object[][] dataSet = dataSetParser.getData();
        Object[][] trainingData = dataSetParser.createTrainingDataSet(SEED, trainingSetPercentage);



        DecisionTreeAlgorithm dt = new DecisionTreeAlgorithm(trainingData, dataSetParser.getHeader(), TARGET_CLASSIFICATION);
        Node rootNode = dt.getRootNode();
        TreePrinter tp = new TreePrinter();
        tp.print(rootNode, "");



        /*

        for (int i = 1; i < dataSet.length; i++) {
            Object indexedClass = classify(Arrays.asList(dataSet[i]));
            if (indexedClass != null) {
                if (indexedClass.equals(dataSet[i][TARGET_CLASSIFICATION])) {
                    correct++;
                }
            }
        }
        System.out.println(Util.accuracy("Decision Tree", correct, dataSet.length - 1));
        */
    }


}
