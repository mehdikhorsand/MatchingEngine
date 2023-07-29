import java.util.ArrayList;

public class TCCreator {
    static ArrayList<TestCase> executed_set = new ArrayList<>();

    public static void create_testcase_files() {
        for (int i = 0; i< Settings.testcase_number; i++)
            write_test_files(i);
    }

    private static String get_testcase_file_name(String method, int index) {
//        write selected testcases in haskell-art-testcases
        return Settings.temp + method + Settings.testcases + Settings.test_file_name + index + Settings.testcase_format;
    }

    private static void write_test_files(int i) {
        CandidateSet candidateSet = new CandidateSet();
        for(String method: Settings.get_methods())
            TCWriter.write_into_txt_format(
                    get_testcase_file_name(method, i),
                    candidateSet.appropriate_candidate(executed_set, method.equals("ART")));
    }
}
