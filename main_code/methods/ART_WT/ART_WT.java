package methods.ART_WT;

import main.SelectionMethod;
import randomTestcase.TestCase;

import java.util.ArrayList;

public class ART_WT  extends SelectionMethod {
//    wavelet transform distance
    static ArrayList<WaveletTransform> executed_set = new ArrayList<>();

    @Override
    public TestCase best_candidate(TestCase[] candidate_set) {
        WaveletTransform[] candidates_wt = new WaveletTransform[candidate_set.length];
        for(int i=0; i<candidate_set.length; i++)
            candidates_wt[i] = new WaveletTransform(candidate_set[i]);
        WaveletTransform furthest_candidate = candidates_wt[0];
        double max_min_distance = 0;
        for (WaveletTransform tc_wt : candidates_wt) {
            double min_distance = get_tc_min_distance(tc_wt);
            if (max_min_distance < min_distance) {
                max_min_distance = min_distance;
                furthest_candidate = tc_wt;
            }
        }
        executed_set.add(furthest_candidate);
        return furthest_candidate.testcase;
    }

    @Override
    public void reset() {
        executed_set = new ArrayList<>();
    }

    public static double get_tc_min_distance(WaveletTransform tc_wt) {
        double min_distance = (int) Double.POSITIVE_INFINITY;
        for(WaveletTransform ewt : executed_set) {
            double distance = WaveletTransform.get_distance(ewt, tc_wt);
            if(distance < min_distance){
                min_distance = distance;
            }
        }
        return min_distance;
    }
}
