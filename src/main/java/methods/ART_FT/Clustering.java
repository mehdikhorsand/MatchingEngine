package methods.ART_FT;

import tools.MyRandom;

import java.util.ArrayList;

public class Clustering {
    public static ArrayList<Cluster> clustering_analysis(ArrayList<FrequencyTransform> originalData, int k) {
        ArrayList<Cluster> clustering = new ArrayList<>();
        for(int i=0; i<k; i++){
            FrequencyTransform initial_testcase = originalData.get(MyRandom.getInt(0, originalData.size()-1));
            clustering.add(new Cluster(initial_testcase));
            originalData.remove(initial_testcase);
        }
        boolean change = true;
        while(change) {
            for(FrequencyTransform tc_ft : originalData) {
                double minDis = Double.POSITIVE_INFINITY;
                for(int j=0; j<k; j++) {
                    double distance = FrequencyTransform.get_distance(tc_ft, clustering.get(j).get_mean_value());
                }
            }
        }
        return clustering;
    }
}
