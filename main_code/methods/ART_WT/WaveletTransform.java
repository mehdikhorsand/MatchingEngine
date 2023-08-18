package methods.ART_WT;

import methods.ART_FT.FrequencyTransform;
import randomTestcase.TestCase;
import tools.Distance;

import java.util.ArrayList;

public class WaveletTransform {
    TestCase testcase;
    ArrayList<Integer> A;
    ArrayList<Integer> B = new ArrayList<>();

    public WaveletTransform(TestCase testcase) {
        this.testcase = testcase;
        FrequencyTransform ft = new FrequencyTransform(testcase);
        A = ft.frequency_vector;
        ArrayList<Integer> B1 = ft.get_frequency_vector(ft.method_invocation_sequence,
                0, ft.method_invocation_sequence.size()/2);
        ArrayList<Integer> B2 = ft.get_frequency_vector(ft.method_invocation_sequence,
                (ft.method_invocation_sequence.size()/2)+1, ft.method_invocation_sequence.size()-1);
        for(int i=0; i<A.size(); i++)
            B.add(B1.get(i) - B2.get(i));
    }

    public static double get_distance(WaveletTransform a, WaveletTransform b) {
        return Distance.euclidean_distance(a.A, b.A) + Distance.euclidean_distance(a.B, b.B);
    }
}
