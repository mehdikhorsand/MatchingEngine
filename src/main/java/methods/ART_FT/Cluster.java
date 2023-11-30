package methods.ART_FT;

import java.util.ArrayList;

public class Cluster {
    ArrayList<FrequencyTransform> testcases = new ArrayList<>();
    public Cluster(FrequencyTransform initial_testcase) {
        testcases.add(initial_testcase);
    }

    public FrequencyTransform get_mean_value() {
        return (testcases.isEmpty())? null:testcases.get(0);
    }
}
