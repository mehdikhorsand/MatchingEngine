package methods.ART_AutoISP_noPartition;

import methods.ART_AutoISP.MethodEdgePairCoverage;
import randomTestcase.TestCase;
import source.TCRunner;
import tools.activity.Action;
import tools.activity.Activity;

import java.util.ArrayList;

public class MethodAndConditionEdgePairCoverageNoPartition extends MethodEdgePairCoverage {
    public MethodAndConditionEdgePairCoverageNoPartition(TestCase testcase) {
        super(testcase);
    }

    @Override
    public ArrayList<Activity> get_activities() {
        run_tc_runner();
        for(Activity activity : TCRunner.method_and_condition_coverage2)
            System.out.println(activity);
        return TCRunner.method_and_condition_coverage2;
    }

    @Override
    public void add_to_behavior(ArrayList<Action> behavior, Action action){}
}
