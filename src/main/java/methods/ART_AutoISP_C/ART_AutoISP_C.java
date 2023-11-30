package methods.ART_AutoISP_C;

import methods.ART_AutoISP.ART_AutoISP;
import randomTestcase.TestCase;
import tools.AutoISP.*;
import java.util.ArrayList;

public class ART_AutoISP_C extends ART_AutoISP {
    static ArrayList<AutoCharacteristic> isp_coverage_situation = new ArrayList<>();
    @Override
    public TestCaseRepresentation represent_testcase(TestCase tc) {
        return new ConditionEdgePairCoverage(tc);
    }

    @Override
    public ArrayList<AutoCharacteristic> get_isp_coverage_situation() {
        return ART_AutoISP_C.isp_coverage_situation;
    }

    @Override
    public void reset() {
        ART_AutoISP_C.isp_coverage_situation = new ArrayList<>();
    }
}
