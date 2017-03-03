package tornado.org.Generic.generators;

import tornado.org.Generic.objects.Feature;
import tornado.org.Generic.objects.Index;

import java.util.ArrayList;
import java.util.List;

public class IndexGenerator {


    public List<Index> create(List<String> header, List<Feature> features, int TARGET_CLASSIFICATION) {
        List<Index> indexes = new ArrayList<>();
        for (int i = 0; i < header.size(); i++) {
            if (i != TARGET_CLASSIFICATION) {
                Index index = new Index(i, header.get(i));
                for (Feature f : features) {
                    if (f.getIndex() == index.getIndex()) {
                        index.addFeature(f);
                    }
                }
                indexes.add(index);
            }
        }
        return indexes;
    }
}
