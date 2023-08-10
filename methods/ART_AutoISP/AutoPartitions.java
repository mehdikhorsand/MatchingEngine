import java.util.ArrayList;
import java.util.Objects;

public class AutoPartitions {
    ArrayList<String> behavior;
    int covered_qty;
    public AutoPartitions(ArrayList<String> behavior) {
        this.behavior = behavior;
    }

//    public AutoPartitions(ArrayList<String> behavior, int covered_qty) {
//        this.behavior = behavior;
//        this.covered_qty = covered_qty;
//    }

//    public void increment_covered_qty() {
//        covered_qty += 1;
//    }

    public boolean same_behavior(ArrayList<String> input_behavior){
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
