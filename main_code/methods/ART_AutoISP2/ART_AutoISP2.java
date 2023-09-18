package methods.ART_AutoISP2;

import main.SelectionMethod;
import methods.ART_AutoISP.ART_AutoISP;
import methods.ART_AutoISP_C.ART_AutoISP_C;
import methods.ART_AutoISP_C.ConditionEdgePairCoverage;
import randomTestcase.TestCase;
import tools.AutoISP.*;

import java.util.ArrayList;

public class ART_AutoISP2 extends ART_AutoISP {
    static ArrayList<AutoCharacteristic> isp_coverage_situation = new ArrayList<>();

    @Override
    public TestCaseRepresentation represent_testcase(TestCase tc) {
        return new MethodInvocationPathCoverage(tc);
    }

    @Override
    public ArrayList<AutoCharacteristic> get_isp_coverage_situation() {
        return ART_AutoISP2.isp_coverage_situation;
    }

    @Override
    public void reset() {
        ART_AutoISP2.isp_coverage_situation = new ArrayList<>();
    }
}
