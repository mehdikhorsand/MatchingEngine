package methods.RT;

import main.SelectionMethod;
import randomTestcase.TestCase;
import tools.MyRandom;
import main.Settings;


public class RT  extends SelectionMethod {
    @Override
    public TestCase best_candidate(TestCase[] candidate_set) {
        return candidate_set[MyRandom.getInt(0, Settings.candidate_set_size - 1)];
    }
}
