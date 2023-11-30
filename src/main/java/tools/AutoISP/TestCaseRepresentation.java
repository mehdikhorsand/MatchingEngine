package tools.AutoISP;

import randomTestcase.TestCase;
import source.TCRunner;
import tools.activity.Action;

import java.util.ArrayList;
import java.util.Objects;

public class TestCaseRepresentation {
    public TestCase testcase;
    public ArrayList<AutoCharacteristic> characteristics = new ArrayList<>();

    public TestCaseRepresentation(TestCase testcase) {
        TCRunner.run(testcase);
        this.testcase = testcase;
    }

    public AutoCharacteristic get_characteristic(Action observed_action) {
        for(AutoCharacteristic ch : characteristics)
            if(Objects.equals(ch.action, observed_action))
                return ch;
        AutoCharacteristic new_characteristic = new AutoCharacteristic(observed_action);
        characteristics.add(new_characteristic);
        return new_characteristic;
    }
}
