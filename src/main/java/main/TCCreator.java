package main;

import matchingEngine.TCRunner;
import randomTestcase.CandidateSet;
import randomTestcase.TestCase;
import tools.Evaluation;
import tools.ExecutionAnalysis;
import tools.TCWriter;
import tools.Terminal;

import java.util.ArrayList;
import java.util.Set;

public class TCCreator {
    static ArrayList<SelectionMethod> removed_methods = new ArrayList<>();
//    static ArrayList<SelectionMethod> methods;
    public static void create_testcase_files() {
//        methods = Settings.get_methods();
        if(Settings.f_measure_evaluation){
            removed_methods = new ArrayList<>();
            int i = 0;
            while (removed_methods.size() != Settings.get_methods().size()) {
//            while (!methods.isEmpty()) {
                write_and_check_test_files(i);
                i++;
            }
        }
        else {
            for (int i = 0; i<Settings.testcase_number; i++)
                write_test_files(i);
//            write_invalid_orders_testcase();
        }
    }

    private static void write_and_check_test_files(int i) {
        System.out.println("testcase" + i);
        double start_creating_candidates_time = System.currentTimeMillis();
        CandidateSet candidate_set = new CandidateSet();
        double end_creating_candidates_time = System.currentTimeMillis();
        for(SelectionMethod method : Settings.get_methods()){
            if(!method_is_removed(method)) {
                double start_testcase_selection_and_running = System.currentTimeMillis();
                select_testcase(candidate_set, method, i);
                new TCRunner(Settings.get_testcase_path(method, i), Settings.get_output_path(method, i));
                Terminal.run_oracle(Settings.get_testcase_path(method, i), Settings.get_oracle_path(method, i));
                try{
                    Evaluation.evaluation(Settings.get_output_path(method, i), Settings.get_oracle_path(method, i));
                } catch (Exception e) {
                    removed_methods.add(method);
                }
                double end_testcase_selection_and_running = System.currentTimeMillis();
                double total_time_spend = (end_creating_candidates_time - start_creating_candidates_time) +
                        (end_testcase_selection_and_running - start_testcase_selection_and_running);
                method.add_execution_time(total_time_spend);
            }
        }
    }

    private static boolean method_is_removed(SelectionMethod method) {
        for(SelectionMethod m : removed_methods)
            if(m.get_name().equals(method.get_name()))
                return true;
        return false;
    }

    private static void select_testcase(CandidateSet candidate_set, SelectionMethod method, int i) {
        TestCase selected_testcase = method.best_candidate(candidate_set.C);
        String testcase_path = get_testcase_file_name(method.get_name(), i);
        TCWriter.write_into_txt_format(testcase_path, selected_testcase);
        ExecutionAnalysis.write_into_txt(method.get_name(), i);
    }

    public static void write_invalid_orders_testcase() {
        for(SelectionMethod method : Settings.get_methods()) {
            Terminal.run_command("cp " + Settings.invalid_orders_testcase_file + " " +
                    get_testcase_file_name(method.get_name(), Settings.testcase_number));
        }
    }

    private static String get_testcase_file_name(String method, int index) {
        return Settings.temp + method + Settings.testcases + Settings.test_file_name + index + Settings.testcase_format;
    }

    private static void write_test_files(int i) {
        CandidateSet candidate_set = new CandidateSet();
        for(SelectionMethod method : Settings.get_methods()) {
            select_testcase(candidate_set, method, i);
        }
    }
}
