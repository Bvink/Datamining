package tornado.org.naivebayes.generators;

import tornado.org.naivebayes.NaiveBayes;
import tornado.org.naivebayes.objects.Classification;

import java.util.HashMap;
import java.util.Map;

public class ClassificationGenerator {

    //Obtain the amount of classifications (In this case, poisonous and not-poisonous, "p" and "e" respectively).
    //Then count the amount of each in the dataSet, and save the frequency + total set size for each classification.
    public Map<Object, Classification> create(Object[][] dataSet) {
        final int TARGET_CLASSIFICATION = NaiveBayes.TARGET_CLASSIFICATION;
        Map<Object, Classification> classificationMap = new HashMap<>();

        for (Object[] row : dataSet) {
            if (classificationMap.containsKey(row[TARGET_CLASSIFICATION])) {
                Classification currentClassification = classificationMap.get(row[TARGET_CLASSIFICATION]);
                currentClassification.increaseFrequency();
            } else {
                Classification currentClassification = new Classification(row[TARGET_CLASSIFICATION].toString(), dataSet.length);
                classificationMap.put(row[TARGET_CLASSIFICATION], currentClassification);

            }
        }
        return classificationMap;
    }
}
