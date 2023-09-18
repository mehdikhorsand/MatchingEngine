package main;

import randomTestcase.CandidateSet;
import randomTestcase.TestCase;
import tools.ExecutionAnalysis;
import tools.TCWriter;
import tools.Terminal;

public class TCCreator {
    public static void create_testcase_files() {
        for (int i = 0; i<Settings.testcase_number; i++)
            write_test_files(i);
        write_invalid_orders_testcase();
    }

    public static void write_invalid_orders_testcase() {
        for(SelectionMethod method : Settings.get_methods2()) {
            Terminal.run_command("cp " + Settings.invalid_orders_testcase_file + " " +
                    get_testcase_file_name(method.get_name(), Settings.testcase_number));
        }
    }

    private static String get_testcase_file_name(String method, int index) {
        return Settings.temp + method + Settings.testcases + Settings.test_file_name + index + Settings.testcase_format;
    }

    private static void write_test_files(int i) {
        CandidateSet candidate_set = new CandidateSet();
        for(SelectionMethod method : Settings.get_methods2()) {
            TestCase selected_testcase = method.best_candidate(candidate_set.C);
            String testcase_path = get_testcase_file_name(method.get_name(), i);
            TCWriter.write_into_txt_format(testcase_path, selected_testcase);
            ExecutionAnalysis.write_into_txt(method.get_name(), i);
        }
    }
}
