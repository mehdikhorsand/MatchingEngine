package methods.ART_AutoISP2_C;

import methods.ART_AutoISP2.MethodInvocationPathCoverage;
import randomTestcase.TestCase;
import source.TCRunner;
import tools.activity.Action;

import java.util.ArrayList;

public class ConditionPathCoverage extends MethodInvocationPathCoverage {
    public ConditionPathCoverage(TestCase testcase) {
        super(testcase);
    }

    @Override
    public ArrayList<ArrayList<Action>> get_paths() {
        return TCRunner.method_and_condition_coverage_paths;
    }

    @Override
    public boolean is_characteristic(Action action) {
        return action.check_type("Condition");
    }
}
