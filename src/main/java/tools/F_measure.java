package tools;

import main.SelectionMethod;
import main.Settings;

import java.util.ArrayList;

public class F_measure {
    private static ArrayList<ArrayList<Integer>> f_measures;
    private static ArrayList<ArrayList<Integer>> f_times;

    public static void create_f_measure_files() {
        Terminal.touch(Settings.result_location + Settings.average + Settings.f_measure,
                Settings.f_measure + Settings.csv_file_format);
        Terminal.touch(Settings.result_location + Settings.average + Settings.f_measure,
                Settings.f_time + Settings.csv_file_format);
        write_methods_name(get_file_path(Settings.f_measure));
        write_methods_name(get_file_path(Settings.f_time));
        f_measures = new ArrayList<>();
        f_measures.add(new ArrayList<>());
        f_times = new ArrayList<>();
    }

    public static void compute_f_measure() {
        int f = Terminal.count_files(Settings.get_target_testcases_path());
        ArrayList<Integer> last_line = f_measures.get(f_measures.size()-1);
        last_line.add(f);
        if(last_line.size() == Settings.get_methods().size() && f_measures.size() < Settings.repetition_number) {
            f_measures.add(new ArrayList<>());
        }
    }

    public static void compute_f_times() {
        for(int i=0; i<Settings.repetition_number; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for(SelectionMethod method : Settings.get_methods()) {
                temp.add(method.get_f_time(i));
            }
            f_times.add(temp);
        }
    }

    public static void write_list(ArrayList<ArrayList<Integer>> output, String file_name) {
        for(int i=0; i<output.size(); i++){
            Terminal.echo(get_file_path(file_name), i + ",");
            for(Integer f : output.get(i)) {
                Terminal.echo(get_file_path(file_name), f + ",");
            }
            Terminal.echo_newline(get_file_path(file_name));
        }
    }

    public static void report_f_measures() {
        compute_f_times();
        write_list(f_measures, Settings.f_measure);
        write_list(f_times, Settings.f_time);
        report_average_f_measure(f_measures, Settings.f_measure);
        report_average_f_measure(f_times, Settings.f_time);
    }

    private static void report_average_f_measure(ArrayList<ArrayList<Integer>> output, String file_name) {
        Terminal.echo(get_file_path(file_name), ",");
        for(int m=0; m<Settings.get_methods().size(); m++) { // m : method number
            int sum = 0;
            for (ArrayList<Integer> list : output) { // i : line
                sum += list.get(m);
            }
            float avg = ((float) sum)/Settings.repetition_number;
            Terminal.echo(get_file_path(file_name), avg + ",");
        }
    }

    private static void write_methods_name(String des_path) {
        Terminal.echo(des_path, ",");
        for(SelectionMethod method : Settings.get_methods())
            Terminal.echo(des_path, method.get_name() + ",");
        Terminal.echo_newline(des_path);
    }

    public static String get_file_path(String file_name) {
        return Settings.result_location + Settings.average + Settings.f_measure
                + "/" + file_name + Settings.csv_file_format;
    }
}
