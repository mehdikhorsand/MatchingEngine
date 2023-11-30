package methods.ART_TFC;

import methods.ART_FT.FrequencyTransform;
import randomTestcase.TestCase;
import tools.Distance;

import java.util.ArrayList;
import java.util.Objects;

public class TrisectionFrequencyConversion {
    TestCase testcase;
    ArrayList<String> MIS; // method invocation sequence
    ArrayList<Integer> A;
    ArrayList<Integer> B;
    ArrayList<Integer> C;
    public TrisectionFrequencyConversion(TestCase testcase) {
        this.testcase = testcase;
        FrequencyTransform ft = new FrequencyTransform(testcase);
        this.MIS = ft.method_invocation_sequence;
        A = ft.frequency_vector;
        B = ft.get_frequency_vector(MIS, 0, MIS.size()/2);
        C = ft.get_frequency_vector(MIS, (MIS.size()/2)+1, MIS.size()-1);
    }

    public static double get_distance(TrisectionFrequencyConversion a, TrisectionFrequencyConversion b) {
        double length_difference = a.MIS.size() - b.MIS.size();
        double ordering_difference = (length_difference > 0)? length_difference:-length_difference;
        for(int i=0; i<Math.min(a.MIS.size(), b.MIS.size()); i++)
            if(!Objects.equals(a.MIS.get(i), b.MIS.get(i)))
                ordering_difference += 1;
        return Distance.euclidean_distance(a.A, b.A) + Distance.euclidean_distance(a.B, b.B) +
                Distance.euclidean_distance(a.C, b.C) + ordering_difference;
    }
}
