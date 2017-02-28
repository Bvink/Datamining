package tornado.org.naivebayes.objects;

public class Classification {
    private String name;
    private int frequency = 1;
    private int setsize;

    public Classification(String name, int setsize) {
        this.name = name;
        this.setsize = setsize;
    }

    public String getName() {
        return name;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getSetsize() {
        return setsize;
    }

    public void increaseFrequency() {
        this.frequency+=1;
    }
}
