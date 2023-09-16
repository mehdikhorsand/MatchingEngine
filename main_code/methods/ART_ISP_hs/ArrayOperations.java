package methods.ART_ISP_hs;

import java.util.ArrayList;

public class ArrayOperations {
    public static ArrayList<ArrayList<Integer>> sum(
            ArrayList<ArrayList<Integer>> a, ArrayList<ArrayList<Integer>> b) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (a.size() == 0)
            return b;
        if (b.size() == 0)
            return a;
        for(int i=0; i<a.size(); i++) {
            ArrayList<Integer> sub_array = new ArrayList<>();
            for (int j = 0; j < a.get(i).size(); j++)
                sub_array.add(a.get(i).get(j) + b.get(i).get(j));
            result.add(sub_array);
        }
        return result;
    }

    public static int max(ArrayList<ArrayList<Integer>> array) {
        int max = 0;
        for(int i=0; i<array.size(); i++)
            for (int j=0; j<array.get(i).size(); j++){
                if (array.get(i).get(j) > max)
                    max = array.get(i).get(j);
            }
        return max;
    }
}
