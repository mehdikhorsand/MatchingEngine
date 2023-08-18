package main;
import java.util.ArrayList;

public class Settings {
    public static final int candidate_set_size = 10;
    public static final int testcase_number = 10;
    public static final int order_number = 20;
    public static final int shareholder_number = 7;
    public static final int broker_number = 7;
    public static final int max_order_price = 10;
    public static final int max_order_quantity = 8;
    public static final int repetition_number = 10;
    public static ArrayList<String> get_methods() {
        ArrayList<String> methods = new ArrayList<>();
//        methods.add("RT");
//        methods.add("ART_FT");
//        methods.add("ART_WT");
        methods.add("ART_TFC");
//        methods.add("ART_ISP");
        methods.add("ART_AutoISP");
//        methods.add("ART_AutoISP_C");
//        methods.add("ART_AutoISP_MC");
        return methods;
    }


//////////////************************************************************************//////////////


    static final String jacoco_coverage_location = "target/site/jacoco/";
    static final String pitest_report_location = "target/report/";
    public static final String project_location = "/home/mehdi/IdeaProjects/MatchingEngine/";
    public static final String temp = "temp/";
    public static final String coverage = "/coverage";
    public static final String output = "/output";
    static final String target_method = "target_method";
    public static final String oracle = "/oracle";
    public static final String average_coverage = "average_coverage/";
    static final String testcases = "/testcases";
    static final String pitest = "/pitest";
    public static final String test_file_name = "/tc";
    public static final String testcase_format = ".txt";
    public static final String result_location = project_location + "reported_result/";
    public static final String report_location = result_location + "report-";

    public static String get_target_testcase_path(int index) {
        return temp + target_method + testcases + test_file_name + index + testcase_format;
    }

    public static String get_target_oracle_path(int index) {
        return temp + target_method + oracle + oracle + index + testcase_format;
    }

    public static String get_target_output_path(int index) {
        return temp + target_method + output + output + index + testcase_format;
    }

    public static String get_target_coverage_path() {
        return temp + target_method + coverage;
    }

    public static String get_target_pitest_path() {
        return temp + target_method + pitest;
    }

    public static String get_report_location(int index) {
        return Settings.report_location + index;
    }

    public static String[][] jacoco_report_files_for_avg() {
        String[][] output = new String[6][2];
        output[0][0] = "/coverage";
        output[0][1] = "/index.html";
        output[1][0] = "/coverage/matchingEngine";
        output[1][1] = "/index.html";
        output[2][0] = "/coverage";
        output[2][1] = "/jacoco.csv";
        output[3][0] = "/coverage";
        output[3][1] = "/jacoco.xml";
        output[4][0] = "/pitest";
        output[4][1] = "/index.html";
        output[5][0] = "/pitest/matchingEngine";
        output[5][1] = "/index.html";
        return output;
    }

    public static String[][] jacoco_report_files_for_copy_in_avg_result() {
        String[][] output = new String[2][2];
        output[0][0] = "/coverage";
        output[0][1] = "/jacoco-resources";
        output[1][0] = "/pitest";
        output[1][1] = "/style.css";
        return output;
    }
}
