package methods.ART_AutoISP2_C;

import main.SelectionMethod;
import methods.ART_AutoISP2.ART_AutoISP2;
import methods.ART_AutoISP_C.ConditionEdgePairCoverage;
import randomTestcase.TestCase;
import tools.AutoISP.AutoCharacteristic;
import tools.AutoISP.AutoISPCoverage;
import tools.AutoISP.TestCaseRepresentation;

import java.util.ArrayList;

public class ART_AutoISP2_C extends ART_AutoISP2 {
    static ArrayList<AutoCharacteristic> isp_coverage_situation = new ArrayList<>();
    @Override
    public TestCaseRepresentation represent_testcase(TestCase tc) {
        return new ConditionPathCoverage(tc);
    }

    @Override
    public ArrayList<AutoCharacteristic> get_isp_coverage_situation() {
        return ART_AutoISP2_C.isp_coverage_situation;
    }

    @Override
    public void reset() {
        ART_AutoISP2_C.isp_coverage_situation = new ArrayList<>();
    }
}
