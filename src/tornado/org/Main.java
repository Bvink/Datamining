package tornado.org;

import tornado.org.decisiontree.DecisionTree;
import tornado.org.naivebayes.NaiveBayes;

public class Main {

    public static void main(String[] args) {

        NaiveBayes nb = new NaiveBayes();
        nb.init();

        DecisionTree dt = new DecisionTree();
        dt.init();
    }
}
