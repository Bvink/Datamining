package tornado.org;

import tornado.org.dbscan.DBScan;
import tornado.org.decisiontree.DecisionTree;
import tornado.org.naivebayes.NaiveBayes;

public class Main {

    public static void main(String[] args) {

        NaiveBayes nb = new NaiveBayes();
        nb.init();

        DecisionTree dt = new DecisionTree();
        dt.init();

        DBScan dbs = new DBScan();
        dbs.init();
    }
}
