package tools;

import main.Settings;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class ExecutionAnalysis {
    static String execution_analysis = "";
    static PrintWriter writer;

    public static void write(String analysis) {
        execution_analysis += analysis + "\n";
//        System.out.println(analysis);
    }

    public static void write_selected_tc_analysis(String selected_testcase_analysis) {
        execution_analysis = selected_testcase_analysis +
                "\n**********************************************************************************************\n" +
                execution_analysis + "\n";
//        System.out.println(selected_testcase_analysis);
    }

    public static void write_into_txt(String method, int index) {
        if(!execution_analysis.equals("")) {
            String file_name = Settings.temp + method + Settings.execution_analysis +
                    Settings.test_file_name + index + Settings.testcase_format;
            try {
                writer = new PrintWriter(Settings.project_location + file_name, "UTF-8");
                writer.println(execution_analysis);
                execution_analysis = "";
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
