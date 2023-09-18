package tools.AutoISP;

import tools.activity.Action;

import java.util.ArrayList;
import java.util.Objects;

public class AutoCharacteristic {
    public Action action;
    public ArrayList<AutoPartitions> partitions = new ArrayList<>();
    public AutoCharacteristic(Action action) {
        this.action = action;
    }
    public void check_behavior(ArrayList<Action> behavior) {
        for(AutoPartitions partition : partitions)
            if(partition.same_behavior(behavior)) {
                partition.covered_qty += 1;
                return;
            }
        partitions.add(new AutoPartitions(behavior));
    }

    @Override
    public String toString() {
        return ((action.check_type("MethodCall")? "":action.method + " -> ")) + action;
    }
}
