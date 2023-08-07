import java.util.ArrayList;
import java.util.Objects;

public class FrequencyTransform {
    TestCase testcase;
    ArrayList<String> method_invocation_sequence;
    ArrayList<Integer> frequency_vector;

    public FrequencyTransform(TestCase tc) {
        this.testcase = tc;
        set_method_invocation_sequence();
        this.frequency_vector = get_frequency_vector(method_invocation_sequence,
                0, method_invocation_sequence.size() - 1);
    }

    private void set_method_invocation_sequence() {
        String src_path = Settings.temp + Settings.test_file_name + Settings.testcase_format;
        String des_path = Settings.temp + Settings.output + Settings.testcase_format;
        TCWriter.write_into_txt_format(src_path, testcase);
        new TCRunner(src_path, des_path);
        method_invocation_sequence = TCRunner.method_invocation_sequence;
        Terminal.rm(src_path);
        Terminal.rm(des_path);
    }

    public ArrayList<Integer> get_frequency_vector(ArrayList<String> MIS, int from, int to) {
        ArrayList<String> src_methods_name = TCRunner.get_src_methods_name();
        ArrayList<Integer> output = new ArrayList<>();
        for(String ignored : src_methods_name)
            output.add(0);
        for(int i=from; i<=to; i++)
            for(int j=0; j<src_methods_name.size(); j++)
                if(Objects.equals(MIS.get(i), src_methods_name.get(j)))
                    output.set(j, output.get(j)+1);
        return output;
    }

    public static double get_distance(FrequencyTransform a, FrequencyTransform b) {
        return Distance.euclidean_distance(a.frequency_vector, b.frequency_vector);
    }
}
