package tornado.org.generic.objects;

public class Classification {
    private String name;       //Name of the classification
    private int frequency = 1; //Total number of the classification in the dataset

    public Classification(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getFrequency() {
        return frequency;
    }

    public void increaseFrequency() {
        this.frequency += 1;
    }
}
