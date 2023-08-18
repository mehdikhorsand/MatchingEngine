package methods.ART_AutoISP_MC;

import main.SelectionMethod;
import randomTestcase.TestCase;

import java.util.ArrayList;

public class ART_AutoISP_MC extends SelectionMethod {
    @Override
    public TestCase best_candidate(TestCase[] candidate_set) {
        ArrayList<MethodAndConditionEdgePairCoverage> testcases_mep = new ArrayList<>();
        for(TestCase c : candidate_set)
            testcases_mep.add(new MethodAndConditionEdgePairCoverage(c));
        MethodAndConditionEdgePairCoverage furthest_candidate = testcases_mep.get(0);
        double max_x_distance = 0;
        for (MethodAndConditionEdgePairCoverage c_mep:testcases_mep) {
            double x_distance = AutoISPMCCoverage.get_score_based_on_isp_coverage(c_mep);
            if (max_x_distance < x_distance) {
                max_x_distance = x_distance;
                furthest_candidate = c_mep;
            }
        }
        AutoISPMCCoverage.select_testcase(furthest_candidate);
        return furthest_candidate.testcase;
    }

    @Override
    public void reset() {
        AutoISPMCCoverage.isp_coverage_situation = new ArrayList<>();
    }
}
