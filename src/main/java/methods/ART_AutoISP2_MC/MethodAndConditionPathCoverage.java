package methods.ART_AutoISP2_MC;

import methods.ART_AutoISP2.MethodInvocationPathCoverage;
import randomTestcase.TestCase;
import source.TCRunner;
import tools.activity.Action;

import java.util.ArrayList;

public class MethodAndConditionPathCoverage extends MethodInvocationPathCoverage {
    public MethodAndConditionPathCoverage(TestCase testcase) {
        super(testcase);
    }

    @Override
    public ArrayList<ArrayList<Action>> get_paths() {
        return TCRunner.method_and_condition_coverage_paths;
    }
}
