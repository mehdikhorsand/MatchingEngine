package methods.ART_AutoISP2_MC;

import main.SelectionMethod;
import methods.ART_AutoISP2.ART_AutoISP2;
import methods.ART_AutoISP2.MethodInvocationPathCoverage;
import methods.ART_AutoISP2_C.ART_AutoISP2_C;
import methods.ART_AutoISP2_C.ConditionPathCoverage;
import randomTestcase.TestCase;
import tools.AutoISP.AutoCharacteristic;
import tools.AutoISP.AutoISPCoverage;
import tools.AutoISP.TestCaseRepresentation;

import java.util.ArrayList;

public class ART_AutoISP2_MC extends ART_AutoISP2 {
    static ArrayList<AutoCharacteristic> isp_coverage_situation = new ArrayList<>();

    @Override
    public TestCaseRepresentation represent_testcase(TestCase tc) {
        return new MethodAndConditionPathCoverage(tc);
    }

    @Override
    public ArrayList<AutoCharacteristic> get_isp_coverage_situation() {
        return ART_AutoISP2_MC.isp_coverage_situation;
    }

    @Override
    public void reset() {
        ART_AutoISP2_MC.isp_coverage_situation = new ArrayList<>();
    }
}
