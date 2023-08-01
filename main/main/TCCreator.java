public class TCCreator {
    public static void create_testcase_files() {
        for (int i = 0; i< Settings.testcase_number; i++)
            write_test_files(i);
    }

    private static String get_testcase_file_name(String method, int index) {
//        write selected testcases in haskell-art-testcases
        return Settings.temp + method + Settings.testcases + Settings.test_file_name + index + Settings.testcase_format;
    }

    private static void write_test_files(int i) {
        CandidateSet candidate_set = new CandidateSet();
        for(SelectionMethod method : SelectionMethod.get_methods()){
            TestCase selected_testcase = method.best_candidate(candidate_set.C);
            String testcase_path = get_testcase_file_name(method.getClass().getName(), i);
            TCWriter.write_into_txt_format(testcase_path, selected_testcase);
        }
    }
}
