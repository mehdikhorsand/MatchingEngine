package methods.ART_AutoISP_C;

import main.SelectionMethod;
import randomTestcase.TestCase;

import java.util.ArrayList;

public class ART_AutoISP_C extends SelectionMethod {
    @Override
    public TestCase best_candidate(TestCase[] candidate_set) {
        ArrayList<ConditionEdgePairCoverage> testcases_mep = new ArrayList<>();
        for(TestCase c : candidate_set)
            testcases_mep.add(new ConditionEdgePairCoverage(c));
        ConditionEdgePairCoverage furthest_candidate = testcases_mep.get(0);
        double max_x_distance = 0;
        for (ConditionEdgePairCoverage c_mep:testcases_mep) {
            double x_distance = AutoISPCCoverage.get_score_based_on_isp_coverage(c_mep);
            if (max_x_distance < x_distance) {
                max_x_distance = x_distance;
                furthest_candidate = c_mep;
            }
        }
        AutoISPCCoverage.select_testcase(furthest_candidate);
        return furthest_candidate.testcase;
    }

    @Override
    public void reset() {
        AutoISPCCoverage.isp_coverage_situation = new ArrayList<>();
    }
}
