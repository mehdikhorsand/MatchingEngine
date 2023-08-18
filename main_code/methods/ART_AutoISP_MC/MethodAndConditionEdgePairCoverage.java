package methods.ART_AutoISP_MC;

import main.Settings;
import matchingEngine.TCRunner;
import methods.ART_AutoISP.MethodEdgePairCoverage;
import randomTestcase.TestCase;
import tools.TCWriter;
import tools.Terminal;

import java.util.ArrayList;

public class MethodAndConditionEdgePairCoverage extends MethodEdgePairCoverage {
    public MethodAndConditionEdgePairCoverage(TestCase testcase) {
        super(testcase);
    }

    @Override
    public ArrayList<ArrayList<String>> get_method_invocation_edges() {
        String src_path = Settings.temp + Settings.test_file_name + Settings.testcase_format;
        String des_path = Settings.temp + Settings.output + Settings.testcase_format;
        TCWriter.write_into_txt_format(src_path, testcase);
        new TCRunner(src_path, des_path);
        Terminal.rm(src_path);
        Terminal.rm(des_path);
        return TCRunner.method_and_condition_coverage_edge;
    }
}
