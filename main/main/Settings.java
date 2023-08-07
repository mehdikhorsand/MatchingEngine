import java.util.ArrayList;

public class Settings {
    static final int candidate_set_size = 10;
    static final int testcase_number = 10;
    static final int order_number = 20;
    static final int shareholder_number = 7;
    static final int broker_number = 7;
    static final int max_order_price = 10;
    static final int max_order_quantity = 8;
    static final int repetition_number = 20;
    public static ArrayList<String> get_methods() {
        ArrayList<String> methods = new ArrayList<>();
        methods.add("RT");
        methods.add("ART_FT");
        methods.add("ART_WT");
        methods.add("ART_TFC");
        methods.add("ART_ISP");
        return methods;
    }


//////////////************************************************************************//////////////


    static final String x_coverage = "avg";
//    static final String x_coverage = "max";
    static final String jacoco_coverage_location = "target/site/jacoco/";
    static final String project_location = "/home/mehdi/IdeaProjects/MatchingEngine/";
    static final String temp = "temp/";
    static final String coverage = "/coverage";
    static final String output = "/output";
    static final String target_method = "target_method";
    static final String oracle = "/oracle";
    static final String average_coverage = "average_coverage/";
    static final String testcases = "/testcases";
    static final String test_file_name = "/tc";
    static final String testcase_format = ".txt";
    static final String result_location = project_location + "reported_result/";
    static final String report_location = result_location + "report-";

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

    public static String get_report_location(int index) {
        return Settings.report_location + index;
    }

    public static String[][] jacoco_report_files_for_avg() {
        String[][] output = new String[4][2];
        output[0][0] = "";
        output[0][1] = "/index.html";
        output[1][0] = "/default";
        output[1][1] = "/index.html";
        output[2][0] = "";
        output[2][1] = "/jacoco.csv";
        output[3][0] = "";
        output[3][1] = "/jacoco.xml";
        return output;
    }

    public static String[] jacoco_report_files_for_copy_in_avg_result() {
        String[] output = new String[1];
        output[0] = "/jacoco-resources";
        return output;
    }
}
