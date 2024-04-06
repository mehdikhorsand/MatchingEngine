package main;

import methods.ART_AutoISP.ART_AutoISP;
import methods.ART_AutoISP_1p.ART_AutoISP_1p;
import methods.ART_AutoISP_C.ART_AutoISP_C;
import methods.ART_ISP_hs.ART_ISP_hs;
import methods.ART_TFC.ART_TFC;
import methods.RT.RT;

import java.util.ArrayList;

public class Settings {
    public static final int candidate_set_size = 10;
    public static final int testcase_number = 10;
    public static final int order_number = 10;
    public static final int shareholder_number = 7;
    public static final int broker_number = 7;
    public static final int max_order_price = 10;
    public static final int max_order_quantity = 8;
    public static final int repetition_number = 10;
    public static final boolean just_show_avg_report = false;
    public static ArrayList<SelectionMethod> get_methods() {
        ArrayList<SelectionMethod> methods = new ArrayList<>();
        methods.add(new RT());
//        methods.add(new ART_FT());
//        methods.add(new ART_WT());
        methods.add(new ART_TFC());
        methods.add(new ART_AutoISP_1p());
        methods.add(new ART_ISP_hs());
        methods.add(new ART_AutoISP());
        methods.add(new ART_AutoISP_C());
//        methods.add(new ART_AutoISP_MC());
//        methods.add(new ART_AutoISP2());
//        methods.add(new ART_AutoISP2_C());
//        methods.add(new ART_AutoISP2_MC());
        return methods;
    }
    public static final boolean f_measure_evaluation = true;
    public static final boolean report_coverage = true;
    public static final boolean report_mutation_result = false;
    public static final int maximum_path_length = 3;


//////////////************************************************************************//////////////


    static final String jacoco_coverage_location = "target/site/jacoco/";
    static final String pitest_report_location = "target/report/";
    public static final String project_location = "/home/mehdi/IdeaProjects/MatchingEngine/";
    public static final String temp = "temp/";
    public static final String coverage = "/coverage";
    public static final String output = "/output";
    static final String target_method = "target_method";
    public static final String oracle = "/oracle";
    public static final String execution_analysis = "/execution_analysis";
    public static final String average = "average/";
    public static final String testcases = "/testcases";
    static final String pitest = "/pitest";
    public static final String test_file_name = "/tc";
    public static final String testcase_format = ".txt";
    public static final String result_location = project_location + "reported_result/";
    public static final String f_measure = "F-measure";
    public static final String f_measure_file_format = ".csv";
    public static final String report_location = result_location + "report-";
    public static final String invalid_orders_testcase_file = "invalid_orders_testcase.txt";

    public static String get_target_testcases_path() {
        return temp + target_method + testcases;
    }

    public static String get_target_testcase_path(int index) {
        return temp + target_method + testcases + test_file_name + index + testcase_format;
    }

    public static String get_testcase_path(SelectionMethod method, int index) {
        return temp + method.get_name() + testcases + test_file_name + index + testcase_format;
    }

    public static String get_target_oracle_path(int index) {
        return temp + target_method + oracle + oracle + index + testcase_format;
    }

    public static String get_oracle_path(SelectionMethod method, int index) {
        return temp + method.get_name() + oracle + oracle + index + testcase_format;
    }

    public static String get_target_output_path(int index) {
        return temp + target_method + output + output + index + testcase_format;
    }

    public static String get_output_path(SelectionMethod method, int index) {
        return temp + method.get_name() + output + output + index + testcase_format;
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

    public static ArrayList<String[]> report_files_for_avg() {
        ArrayList<String[]> result = new ArrayList<>();
        if(report_coverage) {
            String[] output0 = new String[2];
            String[] output1 = new String[2];
            String[] output2 = new String[2];
            String[] output3 = new String[2];
            output0[0] = "/coverage";
            output0[1] = "/index.html";
            output1[0] = "/coverage/matchingEngine";
            output1[1] = "/index.html";
            output2[0] = "/coverage";
            output2[1] = "/jacoco.csv";
            output3[0] = "/coverage";
            output3[1] = "/jacoco.xml";
            result.add(output0);
            result.add(output1);
            result.add(output2);
            result.add(output3);
        }
        if(report_mutation_result) {
            String[] output0 = new String[2];
            String[] output1 = new String[2];
            output0[0] = "/pitest";
            output0[1] = "/index.html";
            output1[0] = "/pitest/matchingEngine";
            output1[1] = "/index.html";
            result.add(output0);
            result.add(output1);
        }
        return result;
    }

    public static ArrayList<String[]> report_files_for_copy_in_avg_result() {
        ArrayList<String[]> result = new ArrayList<>();
        if(report_coverage){
            String[] coverage_output = new String[2];
            coverage_output[0] = "/coverage";
            coverage_output[1] = "/jacoco-resources";
            result.add(coverage_output);
        }
        if(report_mutation_result) {
            String[] pitest_output = new String[2];
            pitest_output[0] = "/pitest";
            pitest_output[1] = "/style.css";
            result.add(pitest_output);
        }
        return result;
    }
}
