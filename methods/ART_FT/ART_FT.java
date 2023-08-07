import java.util.ArrayList;

public class ART_FT  extends SelectionMethod{
//    frequency transform distance
    static ArrayList<FrequencyTransform> executed_set = new ArrayList<>();

    @Override
    public TestCase best_candidate(TestCase[] candidate_set) {
        FrequencyTransform[] candidates_ft = new FrequencyTransform[candidate_set.length];
        for(int i=0; i<candidate_set.length; i++)
            candidates_ft[i] = new FrequencyTransform(candidate_set[i]);
        FrequencyTransform furthest_candidate = candidates_ft[0];
        double max_min_distance = 0;
        for (FrequencyTransform tc_ft : candidates_ft) {
            double min_distance = get_tc_min_distance(tc_ft);
            if (max_min_distance < min_distance) {
                max_min_distance = min_distance;
                furthest_candidate = tc_ft;
            }
        }
        executed_set.add(furthest_candidate);
        return furthest_candidate.testcase;
    }

    @Override
    public void reset() {
        executed_set = new ArrayList<>();
    }

    public static double get_tc_min_distance(FrequencyTransform tc_ft) {
        double min_distance = (int) Double.POSITIVE_INFINITY;
        for(FrequencyTransform eft : executed_set) {
            double distance = FrequencyTransform.get_distance(eft, tc_ft);
            if(distance < min_distance){
                min_distance = distance;
            }
        }
        return min_distance;
    }

//    public static ArrayList<FrequencyTransform> clustering_analysis(ArrayList<FrequencyTransform> original_data, int k) {
////        if(original_data.size() <= k)
////            return original_data;
//        ArrayList<FrequencyTransform> mean_value = new ArrayList<>();
//        ArrayList<FrequencyTransform> clustering = original_data; //output
//        for(int i=0; i<Math.min(k, original_data.size()); i++){
//            int random = MyRandom.getInt(0, original_data.size()-1);
//            mean_value.add(original_data.get(random));
//            original_data.remove(original_data.get(random));
//        }
//        boolean change = true;
//        while (change) {
//            // for remained testcases in the original_data input
//            for(FrequencyTransform de : original_data) {
//                double min_dis = Double.POSITIVE_INFINITY;
//                int nearest_cluster = 0;
//                for(int j=0; j<k; j++) {
//                    double distance = FrequencyTransform.get_distance(de, mean_value.get(j));
//                    if(distance <= min_dis) {
//                        min_dis = distance;
//                        nearest_cluster = j;
//                    }
//                }
//                clustering.get(nearest_cluster).add(de);
//            }
//        }
//        return clustering;
//    }
}
