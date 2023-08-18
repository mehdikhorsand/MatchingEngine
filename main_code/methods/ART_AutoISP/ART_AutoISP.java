package methods.ART_AutoISP;

import java.util.ArrayList;
import main.SelectionMethod;
import randomTestcase.TestCase;

public class ART_AutoISP extends SelectionMethod {
    @Override
    public TestCase best_candidate(TestCase[] candidate_set) {
        ArrayList<MethodEdgePairCoverage> testcases_mep = new ArrayList<>();
        for(TestCase c : candidate_set)
            testcases_mep.add(new MethodEdgePairCoverage(c));
        MethodEdgePairCoverage furthest_candidate = testcases_mep.get(0);
        double max_x_distance = 0;
        for (MethodEdgePairCoverage c_mep:testcases_mep) {
            double x_distance = AutoISPCoverage.get_score_based_on_isp_coverage(c_mep);
            if (max_x_distance < x_distance) {
                max_x_distance = x_distance;
                furthest_candidate = c_mep;
            }
        }
        AutoISPCoverage.select_testcase(furthest_candidate);
        return furthest_candidate.testcase;
    }

    @Override
    public void reset() {
        AutoISPCoverage.isp_coverage_situation = new ArrayList<>();
    }
}
