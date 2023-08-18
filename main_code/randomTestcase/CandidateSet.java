package randomTestcase;

import main.Settings;

public class CandidateSet {
    public final TestCase[] C;

    public CandidateSet () {
        C = new TestCase[Settings.candidate_set_size];
        for (int i=0; i<Settings.candidate_set_size; i++){
            C[i] = new TestCase();
        }
    }
}
