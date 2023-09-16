package methods.ART_AutoISP_noPartition;

import main.SelectionMethod;
import methods.ART_AutoISP.AutoCharacteristic;
import methods.ART_AutoISP.AutoISPCoverage;
import methods.ART_AutoISP.MethodEdgePairCoverage;
import randomTestcase.TestCase;

import java.util.ArrayList;

public class ART_AutoISP_noPartition extends SelectionMethod {
    static ArrayList<AutoCharacteristic> isp_coverage_situation = new ArrayList<>();
    @Override
    public TestCase best_candidate(TestCase[] candidate_set) {
        ArrayList<MethodEdgePairCoverage> testcases_mep = new ArrayList<>();
        for(TestCase c : candidate_set)
            testcases_mep.add(new MethodAndConditionEdgePairCoverageNoPartition(c));
        return AutoISPCoverage.get_best_candidate(testcases_mep, isp_coverage_situation);
    }

    @Override
    public void reset() {
        isp_coverage_situation = new ArrayList<>();
    }
}
