import matchingEngine.TCRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Settings;
import tools.Evaluation;
import tools.Terminal;


public class TestCaseRunner{
    @Test
    public void tester () throws Exception {
        for(int i = 0; i< Terminal.count_target_testcases(); i++) {
            new TCRunner(Settings.get_target_testcase_path(i), Settings.get_target_output_path(i));
            Evaluation.evaluation(Settings.get_target_output_path(i), Settings.get_target_oracle_path(i));
        }
    }

    @Before
    public void setup() {
        System.out.println("\n---------------------------------------------");
    }

    @After
    public void tearDown() {
        System.out.println("\n---------------------------------------------");
    }
}
