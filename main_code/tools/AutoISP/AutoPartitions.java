package tools.AutoISP;

import tools.activity.Action;

import java.util.ArrayList;
import java.util.Objects;

public class AutoPartitions {
    public ArrayList<Action> behavior;
    public int covered_qty;
    public AutoPartitions(ArrayList<Action> behavior) {
        this.behavior = behavior;
        this.covered_qty = 1;
    }

    public boolean same_behavior(ArrayList<Action> input_behavior){
        if(input_behavior.size() == behavior.size()){
            for(int i=0; i<behavior.size(); i++) {
                if(!Objects.equals(behavior.get(i), input_behavior.get(i))){
                    return false;
                }
            }
            return true;
        }
        else
            return false;
    }
}
