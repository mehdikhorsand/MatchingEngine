package methods.ART_AutoISP;

import randomTestcase.TestCase;
import source.TCRunner;
import tools.AutoISP.*;
import tools.activity.*;

import java.util.ArrayList;

public class MethodEdgePairCoverage extends TestCaseRepresentation {
    public ArrayList<Activity> activities;
    public MethodEdgePairCoverage(TestCase testcase) {
        super(testcase);
        this.activities = get_activities();
        set_characteristics();
    }

    public void set_characteristics() {
        if(activities.size() > 0) {
            Action first_function = activities.get(0).action;
            activities.remove(0);
            set_characteristics(get_characteristic(first_function));
            set_characteristics();
        }
    }

    public void set_characteristics(AutoCharacteristic characteristic) {
        ArrayList<Action> behavior = new ArrayList<>();
        while(activities.size() > 0) {
            Activity activity = activities.get(0);
            if(characteristic.action.method.equals(activity.parent_method)) {
                if(activity.action.check_type("EndLoop") && characteristic.action.check_type("InsideLoop")
                        && ((Loop)characteristic.action).loop_index == ((Loop)activity.action).loop_index)
                    break;
                else {
                    activities.remove(0);
                    if(characteristic.action.equals(activity.action) && activity.action.check_type("InsideLoop")){
                        set_characteristics(characteristic);
                        break;
                    }
                    else {
                        add_to_behavior(behavior, activity.action);
                        if(activity.action.check_type("InsideLoop") || activity.action.check_type("MethodCall"))
                            set_characteristics(get_characteristic(activity.action));
                    }
                }
            }
            else {
                if (activity.action.check_type("MethodFinish"))
                    if(characteristic.action.method.equals(activity.action.method)) {
                        activities.remove(0);
                        add_to_behavior(behavior, activity.action);
                    }
                break;
            }
        }
        characteristic.check_behavior(behavior);
    }

    public void add_to_behavior(ArrayList<Action> behavior, Action method){
        behavior.add(method);
    }

    public ArrayList<Activity> get_activities() {
//        for(int i=0; i<TCRunner.method_invocation_edge.size(); i++)
//            ExecutionAnalysis.write(i + "-" + TCRunner.method_invocation_edge.get(i));
        return TCRunner.method_invocation_edge;
    }
}
