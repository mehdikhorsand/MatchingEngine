package methods.ART_ISP_hs;

import main.SelectionMethod;
import randomTestcase.TestCase;

import java.util.ArrayList;

public class ART_ISP_hs extends SelectionMethod {
    @Override
    public TestCase best_candidate(TestCase[] candidate_set) {
        TestCase furthest_candidate = candidate_set[0];
        double max_x_distance = 0;
        for (TestCase c:candidate_set) {
            float x_distance = ISPCoverage.get_score_based_on_isp_coverage(c);
            if (max_x_distance < x_distance) {
                max_x_distance = x_distance;
                furthest_candidate = c;
            }
        }
        ISPCoverage.select_testcase(furthest_candidate);
        return furthest_candidate;
    }

    @Override
    public void reset() {
        ISPCoverage.isp_partitions_situation = new ArrayList<>();
    }
}
