package methods.ART_TFC;

import main.SelectionMethod;
import randomTestcase.TestCase;

import java.util.ArrayList;

public class ART_TFC extends SelectionMethod {
//    frequency transform distance
    static ArrayList<TrisectionFrequencyConversion> executed_set = new ArrayList<>();

    @Override
    public TestCase best_candidate(TestCase[] candidate_set) {
        TrisectionFrequencyConversion[] candidates_tfc = new TrisectionFrequencyConversion[candidate_set.length];
        for(int i=0; i<candidate_set.length; i++)
            candidates_tfc[i] = new TrisectionFrequencyConversion(candidate_set[i]);
        TrisectionFrequencyConversion furthest_candidate = candidates_tfc[0];
        double max_min_distance = 0;
        for (TrisectionFrequencyConversion tc_tfc : candidates_tfc) {
            double min_distance = get_tc_min_distance(tc_tfc);
            if (max_min_distance < min_distance) {
                max_min_distance = min_distance;
                furthest_candidate = tc_tfc;
            }
        }
        executed_set.add(furthest_candidate);
        return furthest_candidate.testcase;
    }

    @Override
    public void reset() {
        executed_set = new ArrayList<>();
    }

    public static double get_tc_min_distance(TrisectionFrequencyConversion tc_tfc) {
        double min_distance = (int) Double.POSITIVE_INFINITY;
        for(TrisectionFrequencyConversion eft : executed_set) {
            double distance = TrisectionFrequencyConversion.get_distance(eft, tc_tfc);
            if(distance < min_distance){
                min_distance = distance;
            }
        }
        return min_distance;
    }
}
