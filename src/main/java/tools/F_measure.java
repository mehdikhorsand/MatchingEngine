package tools;

import main.SelectionMethod;
import main.Settings;

import java.util.ArrayList;

public class F_measure {
    private static ArrayList<ArrayList<Integer>> f_measures;

    public static void create_f_measure_file() {
        Terminal.touch(Settings.result_location + Settings.average + Settings.f_measure,
                Settings.f_measure + Settings.f_measure_file_format);
        write_methods_name();
        f_measures = new ArrayList<>();
        f_measures.add(new ArrayList<>());
    }

    public static void compute_f_measure() {
        int f = Terminal.count_files(Settings.get_target_testcases_path());
        ArrayList<Integer> last_line = f_measures.get(f_measures.size()-1);
        last_line.add(f);
        if(last_line.size() == Settings.get_methods().size() && f_measures.size() < Settings.repetition_number){
            f_measures.add(new ArrayList<>());
        }
    }

    public static void report_f_measures() {
        for(int i=0; i<f_measures.size(); i++){
            Terminal.echo(get_f_measure_path(), i + ",");
            for(Integer f : f_measures.get(i)){
                Terminal.echo(get_f_measure_path(), f + ",");
            }
            Terminal.echo_newline(get_f_measure_path());
        }
        report_average_f_measure();
    }

    private static void report_average_f_measure() {
        Terminal.echo(get_f_measure_path(), ",");
        for(int m=0; m<Settings.get_methods().size(); m++) { // m : method number
            int sum = 0;
            for(int i=0; i<f_measures.size(); i++) { // i : line
                sum += f_measures.get(i).get(m);
            }
            float avg = ((float) sum)/Settings.repetition_number;
            Terminal.echo(get_f_measure_path(), avg + ",");
        }
    }

    private static void write_methods_name() {
        Terminal.echo(get_f_measure_path(), ",");
        for(SelectionMethod method : Settings.get_methods())
            Terminal.echo(get_f_measure_path(), method.get_name() + ",");
        Terminal.echo_newline(get_f_measure_path());
    }

    public static String get_f_measure_path() {
        return Settings.result_location + Settings.average + Settings.f_measure
                + "/" + Settings.f_measure + Settings.f_measure_file_format;
    }
}
