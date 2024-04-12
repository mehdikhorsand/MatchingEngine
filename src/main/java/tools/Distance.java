package tools;

import java.util.ArrayList;

public class Distance {
    public static Double euclidean_distance(ArrayList<Integer> a, ArrayList<Integer> b) {
        double x = 0;
        for(int i=0; i<a.size(); i++)
            x += Math.pow(a.get(i) - b.get(i), 2);
        return Math.sqrt(x);
    }

    public static Double euclidean_distance_with_mean_value(ArrayList<Integer> a, ArrayList<Double> b) {
        double x = 0;
        for(int i=0; i<a.size(); i++)
            x += Math.pow(a.get(i) - b.get(i), 2);
        return Math.sqrt(x);
    }
}
