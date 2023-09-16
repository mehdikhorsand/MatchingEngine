package methods.ART_AutoISP;

import main.Settings;
import randomTestcase.TestCase;
import source.TCRunner;
import tools.TCWriter;
import tools.Terminal;
import tools.activity.*;

import java.util.ArrayList;
import java.util.Objects;

public class MethodEdgePairCoverage {
    public TestCase testcase;
    public ArrayList<AutoCharacteristic> characteristics = new ArrayList<>();
    public ArrayList<Activity> activities;
    public MethodEdgePairCoverage(TestCase testcase) {
        this.testcase = testcase;
        this.activities = get_activities();
        set_characteristics();
    }

    public AutoCharacteristic get_characteristic(Action observed_action) {
        for(AutoCharacteristic ch : characteristics)
            if(Objects.equals(ch.action, observed_action))
                return ch;
        AutoCharacteristic new_characteristic = new AutoCharacteristic(observed_action);
        characteristics.add(new_characteristic);
        return new_characteristic;
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
            if(activity.action.check_type("condition"))
                System.out.println();
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

    public void run_tc_runner() {
        String src_path = Settings.temp + Settings.test_file_name + Settings.testcase_format;
        String des_path = Settings.temp + Settings.output + Settings.testcase_format;
        TCWriter.write_into_txt_format(src_path, testcase);
        new TCRunner(src_path, des_path);
        Terminal.rm(src_path);
        Terminal.rm(des_path);
    }

    public ArrayList<Activity> get_activities() {
        run_tc_runner();
//        for(int i=0; i<TCRunner.method_invocation_edge2.size(); i++)
//            System.out.println(i + "-" + TCRunner.method_invocation_edge2.get(i));
        return TCRunner.method_invocation_edge2;
    }
}
