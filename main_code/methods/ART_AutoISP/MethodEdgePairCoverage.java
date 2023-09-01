package methods.ART_AutoISP;

import main.Settings;
import randomTestcase.TestCase;
import tools.TCWriter;
import tools.Terminal;

import java.util.ArrayList;
import java.util.Objects;
import source.TCRunner;

public class MethodEdgePairCoverage {
    public TestCase testcase;
    public ArrayList<AutoCharacteristic> characteristics = new ArrayList<>();
    public MethodEdgePairCoverage(TestCase testcase) {
        this.testcase = testcase;
        set_characteristics(get_method_invocation_edges());
    }

    public void set_characteristics(ArrayList<ArrayList<String>> method_invocation_edges) {
        for(int i=0; i<method_invocation_edges.size(); i++) {
            ArrayList<String> edge = method_invocation_edges.get(i);
            String called_method = edge.get(edge.size()-1);
            if(called_method.split(":").length > 1)
                continue;
            boolean found_characteristic = false;
            for(AutoCharacteristic ch : characteristics) {
                if(Objects.equals(ch.starting_method, called_method)){
                    found_characteristic = true;
                    ch.check_method_behavior(method_invocation_edges, i+1);
                    break;
                }
            }
            if(!found_characteristic){
                AutoCharacteristic new_characteristic = new AutoCharacteristic(called_method);
                characteristics.add(new_characteristic);
                new_characteristic.check_method_behavior(method_invocation_edges, i+1);
            }
        }
    }

    public ArrayList<ArrayList<String>> get_method_invocation_edges() {
        String src_path = Settings.temp + Settings.test_file_name + Settings.testcase_format;
        String des_path = Settings.temp + Settings.output + Settings.testcase_format;
        TCWriter.write_into_txt_format(src_path, testcase);
        new TCRunner(src_path, des_path);
        Terminal.rm(src_path);
        Terminal.rm(des_path);
        return TCRunner.method_invocation_edge;
    }
}
