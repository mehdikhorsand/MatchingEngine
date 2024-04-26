package main;

import tools.AverageFile;
import tools.ConsoleColors;
import tools.F_measure;
import tools.Terminal;


public class Main {
    public static void main(String[] args) {
        clean_required_paths();
        for(int i=0; i<Settings.repetition_number; i++) {
            TCCreator.create_testcase_files();
            String target = Settings.temp + Settings.target_method;
            for(SelectionMethod method : Settings.get_methods()) {
                String source = Settings.temp + method.get_name();
                Terminal.mv(source, target);
                compute_testcases_oracle(); // todo: move this part to before the for loop
                if(Settings.f_measure_evaluation) {
                    F_measure.compute_f_measure();
                }
                else {
                    if (Settings.report_coverage) {
                        print_progress(i, method.get_name());
                        Terminal.run_jacoco("test");
                        copy_jacoco_report();
                    }
                    if (Settings.report_mutation_result) {
                        print_progress(i, method.get_name());
                        Terminal.run_pitest();
                        copy_pitest_report();
                    }
                }
                Terminal.mv(target, source);
                clear_system();
            }
            report_all_results(i);
        }
        if(!Settings.f_measure_evaluation) {
            AverageFile.create_avg_files();
            open_coverage_and_pitest_report_files();
        }
        else{
            F_measure.report_f_measures();
        }
    }

    private static void print_progress(int i, String method) {
        System.out.println(ConsoleColors.YELLOW_BOLD + "------------ round " + (i+1) + " " + method + " ------------" +
                ConsoleColors.RESET);
    }

    private static void copy_pitest_report() {
        Terminal.copy(Settings.pitest_report_location + "*", Settings.get_target_pitest_path());
        Terminal.rm(Settings.pitest_report_location);
    }

    private static void open_coverage_and_pitest_report_files() {
        StringBuilder file_paths = new StringBuilder();
        String coverage_file = "/index.html ";
        for(SelectionMethod method : Settings.get_methods())
            if(Settings.report_coverage)
                file_paths.append(Settings.result_location).append(Settings.average).append(method.get_name())
                        .append(Settings.coverage).append(coverage_file);
        for(SelectionMethod method : Settings.get_methods())
            if(Settings.report_mutation_result)
                file_paths.append(Settings.result_location).append(Settings.average).append(method.get_name())
                        .append(Settings.pitest).append(coverage_file);
        if(!Settings.just_show_avg_report)
            for(int i=0; i<Settings.repetition_number; i++) {
                if(Settings.report_coverage)
                    for (SelectionMethod method : Settings.get_methods())
                        file_paths.append(get_destination_location(i, method.get_name())).append(Settings.coverage).append(coverage_file);
                if(Settings.report_mutation_result)
                    for (SelectionMethod method : Settings.get_methods())
                        file_paths.append(get_destination_location(i, method.get_name())).append(Settings.pitest).append(coverage_file);
            }
        Terminal.browse(String.valueOf(file_paths));
    }

    private static void report_all_results(int index) {
        Terminal.copy(Settings.temp + "*", Settings.get_report_location(index));
        for(SelectionMethod method : Settings.get_methods()) {
            Terminal.rm(Settings.temp + method.get_name() + Settings.testcases + "/*");
            Terminal.rm(Settings.temp + method.get_name() + Settings.execution_analysis + "/*");
            Terminal.rm(Settings.temp + method.get_name() + Settings.oracle + "/*");
            Terminal.rm(Settings.temp + method.get_name() + Settings.coverage + "/*");
            Terminal.rm(Settings.temp + method.get_name() + Settings.output + "/*");
        }
    }

    public static void copy_jacoco_report() {
        Terminal.copy(Settings.jacoco_coverage_location + "*", Settings.get_target_coverage_path());
        Terminal.rm(Settings.jacoco_coverage_location);
    }

    public static String get_destination_location(int index){
        return Settings.report_location + index;
    }

    public static String get_destination_location(int index, String method){
        return get_destination_location(index) + "/" + method + "/";
    }

    private static void clear_system() {
        SelectionMethod.reset_selection_methods();
        Terminal.rm("target/maven-status target/site target/surefire-reports target/jacoco.exec");
    }

    public static void compute_testcases_oracle() {
        for(int i=0; i<Terminal.count_target_testcases(); i++)
            Terminal.run_oracle(Settings.get_target_testcase_path(i), Settings.get_target_oracle_path(i));
    }

    private static void clean_required_paths() {
        Terminal.rm(Settings.result_location);
        Terminal.rm(Settings.temp);
        Terminal.mkdir(Settings.result_location + Settings.average);
        if(Settings.f_measure_evaluation)
            F_measure.create_f_measure_files();
        for(SelectionMethod method : Settings.get_methods()) {
            Terminal.mkdir(Settings.temp + method.get_name() + Settings.testcases);
            Terminal.mkdir(Settings.temp + method.get_name() + Settings.execution_analysis);
            Terminal.mkdir(Settings.temp + method.get_name() + Settings.oracle);
            Terminal.mkdir(Settings.temp + method.get_name() + Settings.coverage);
            Terminal.mkdir(Settings.temp + method.get_name() + Settings.output);
        }
    }
}
