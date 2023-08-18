package methods.ART_AutoISP;

import java.util.ArrayList;
import java.util.Objects;

public class AutoCharacteristic {
    public String starting_method;
    public ArrayList<AutoPartitions> partitions = new ArrayList<>();
    public AutoCharacteristic(String starting_method) {
        this.starting_method = starting_method;
    }

    public void check_method_behavior(ArrayList<ArrayList<String>> methodInvocationEdges, int begging_index) {
        ArrayList<String> method_behavior = new ArrayList<>();
        for(int i=begging_index; i<methodInvocationEdges.size(); i++) {
            ArrayList<String> edge = methodInvocationEdges.get(i);
            String child_method = edge.get(edge.size()-1);
            String parent_method = (edge.size() == 2)? edge.get(0):null;
            if(Objects.equals(child_method, starting_method)){
                break;
            }
            if(Objects.equals(parent_method, starting_method)){
                method_behavior.add(child_method);
            }
        }
        for(AutoPartitions p : partitions) {
            if(p.same_behavior(method_behavior)){
                p.covered_qty += 1;
                return;
            }
        }
        AutoPartitions new_partition = new AutoPartitions(method_behavior);
        partitions.add(new_partition);
    }
}
