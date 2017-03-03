package tornado.org.util;

public class Util {

    public static String accuracy(String type, double numerator, double denominator) {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        sb.append(" Accuracy: ");
        sb.append(numerator/denominator);
        return sb.toString();
    }
}
