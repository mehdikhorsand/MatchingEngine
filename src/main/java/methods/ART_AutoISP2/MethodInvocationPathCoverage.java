package methods.ART_AutoISP2;

import randomTestcase.TestCase;
import source.TCRunner;
import tools.activity.Action;
import tools.AutoISP.*;

import java.util.ArrayList;

public class MethodInvocationPathCoverage extends TestCaseRepresentation {
    public ArrayList<ArrayList<Action>> paths;
    public MethodInvocationPathCoverage(TestCase testcase) {
        super(testcase);
        this.paths = get_paths();
        set_characteristics();
    }

    public ArrayList<ArrayList<Action>> get_paths() {
        return TCRunner.method_invocation_paths;
    }

    public void set_characteristics() {
        for(ArrayList<Action> path:paths) {
            Action child = path.get(path.size()-1);
            if(is_characteristic(child)) {
                path.remove(child);
                AutoCharacteristic characteristic = get_characteristic(child);
                characteristic.check_behavior(path);
            }
        }
    }

    public boolean is_characteristic(Action action) {
        return true;
    }
}
