package tornado.org.util;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSetParser {

    private static File file;
    private List<String> header;
    private Object[][] data;


    public DataSetParser(String fileLocation) {
    file = new File(fileLocation);

    try {
        parseData();
    } catch (IOException e) {
        Logger logger = Logger.getLogger("logger");
        logger.log(Level.SEVERE, "File not found: ", e);
    }
}

    public List<String> getHeader() {
        return header;
    }

    public Object[][] getData() {
        return data;
    }

    private void parseData() throws IOException {
        String del = ",";
        String line;
        BufferedReader r = new BufferedReader(new FileReader(file));
        List<String[]> list = new ArrayList<>();
        header = Arrays.asList(r.readLine().split(del));
        while ((line = r.readLine()) != null)
            list.add(line.split(del));

        data = new Object[list.size()][];
        list.toArray(data);

        r.close();
    }

    public Object[][] createTrainingDataSet(long SEED, double trainingSetPercentage) {
        int trainingSetSize = (int) Math.floor(data.length * trainingSetPercentage);
        Set<Object[]> subset = new HashSet<>(trainingSetSize);
        Random random = new Random(SEED);

        for (int i = 0; i < trainingSetSize; i++) {
            int index = random.nextInt(data.length);
            Object[] mushroom = data[index];
            while (subset.contains(mushroom)) {
                index = (index + 1) % data.length;
                mushroom = data[index];
            }
            subset.add(mushroom);
        }
        return subset.toArray(new Object[subset.size()][]);
    }
}
