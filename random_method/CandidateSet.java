import java.util.ArrayList;

public class CandidateSet {
    private final TestCase[] C;

    public CandidateSet () {
        C = new TestCase[Settings.candidate_set_size];
        for (int i=0; i<Settings.candidate_set_size; i++){
            C[i] = new TestCase();
        }
    }

    public TestCase appropriate_candidate(ArrayList<TestCase> E, boolean art) {
        // return: furthest candidate from executed set.
        if(!art)
            return C[0];
        TestCase furthest_candidate = C[0];
        double max_x_distance = 0;
        for (TestCase c:C) {
             float x_distance = ISPCoverage.get_score_based_on_isp_coverage(c);
             if (max_x_distance < x_distance) {
                max_x_distance = x_distance;
                furthest_candidate = c;
            }
        }
        ISPCoverage.select_testcase(furthest_candidate);
        return furthest_candidate;
    }
}
