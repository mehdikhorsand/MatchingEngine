package tools;

import java.util.Random;
public class MyRandom {
    static Random random = new Random();

    public static Boolean getBoolean() {
        return getBoolean(50);
    }

    public static Boolean getBoolean(int true_possibility) {
        return (getInt(1, 100) <= true_possibility);
    }

    public static Integer getInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static int[] generate_random_number(int length, int min, int max) {
        int[] list = new int[length];
        for(int i=0; i<length; i++)
            list[i] = getInt(min, max);
        return list;
    }
}
