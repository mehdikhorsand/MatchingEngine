package tools;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import main.SelectionMethod;
import main.Settings;

public class AverageFile {
    static PrintWriter writer;
    static String avg_coverage_location = Settings.result_location + Settings.average;
    static String[] index;

    public static void create_avg_files() {
        if(!Settings.f_measure_evaluation) {
            for(SelectionMethod method : Settings.get_methods()) {
                for(String[] file_to_copy : Settings.report_files_for_copy_in_avg_result()) {
                    Terminal.copy(Settings.report_location + "0/" + method.get_name() + file_to_copy[0] + file_to_copy[1],
                            avg_coverage_location + method.get_name() + file_to_copy[0]);
                }
                for(String[] file_path : Settings.report_files_for_avg()) {
                    Terminal.mkdir(avg_coverage_location + method.get_name() + file_path[0]);
                    compute_avg_file(file_path[0] + file_path[1], method.get_name());
                }
            }
        }
    }

    private static void compute_avg_file(String file_path, String method) {
        try {
            writer = new PrintWriter(avg_coverage_location + method + file_path, "UTF-8");
            set_indexes_content(method, file_path);
            write_avg_coverage_file();
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void set_indexes_content(String method, String file_loc_in_coverage){
        String file_loc_in_report_i = "/" + method + file_loc_in_coverage;
        index = new String[Settings.repetition_number];
        for (int i=0; i<Settings.repetition_number; i++) {
            index[i] = Terminal.cat(Settings.report_location + i + file_loc_in_report_i);
            if(file_loc_in_coverage.endsWith(".html"))
                index[i] = index[i].replace(",", "");
        }
    }

    private static void write_avg_coverage_file() {
        while(true) {
            int sum = 0;
            for (int i = 0; i < index.length; i++)
                sum += find_first_number_in_string(i);
            if (sum >= 0)
                write_number((float) sum / index.length);
            else
                break;
        }
//        else if (Objects.equals(Settings.x_coverage, "max"))
//            while(true) {
//                int max = -1;
//                for (int i = 0; i < index.length; i++) {
//                    int num = find_first_number_in_string(i);
//                    if (max < num)
//                        max = num;
//                }
//                if (max >= 0)
//                    write_number((float) max);
//                else
//                    break;
//            }
    }

    private static int find_first_number_in_string(int index) {
//        if(index == 0) {
//            System.out.println("..................");
//            for(int i=0; i<Settings.repetition_number; i++){
//                System.out.println(AverageFile.index[i].
//                        substring(0, Math.min(20, AverageFile.index[i].length()-1)));
//            }
//        }
        boolean write = index == 0;
        for(int i = 0; i< AverageFile.index[index].length(); i++) {
            int length = get_integer_length(AverageFile.index[index].substring(i));
            if (length > 0) {
                int number = Integer.parseInt(AverageFile.index[index].substring(i, i + length));
                AverageFile.index[index] = AverageFile.index[index].substring(i + length);
                return number;
            }
            else if (write)
                writer.print(AverageFile.index[index].charAt(i));
        }
        return -1; // there is no number in input string.
    }

    private static int get_integer_length(String input) {
        int len = 0;
        while((get_integer_format(input.substring(0, len+1)) >= 0) && (len < input.length()))
            len ++;
        return len;
    }

    private static int get_integer_format(String input) {
        try{
            return Integer.parseInt(input);
        }
        catch (NumberFormatException ex){
            return -1;
        }
    }

    private static void write_number(float float_number) {
//        float_number = (float) ((int) (float_number * 100)) / 100;
        if(float_number == (int) float_number)
            writer.print((int) float_number);
        else
            writer.printf("%.1f", float_number);
    }
}
