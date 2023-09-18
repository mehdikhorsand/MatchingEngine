package methods.ART_ISP_hs;

import randomTestcase.TestCase;
import tools.ExecutionAnalysis;

import java.util.ArrayList;

public class ISPCoverage {

    static ArrayList<ArrayList<Integer>> isp_partitions_situation = new ArrayList<>();
    static float get_score_based_on_isp_coverage(TestCase c) {
        ArrayList<ArrayList<Integer>> tc_isp_res = Characteristic.get_testcase_isp_result(c);
        float score = 0;
//        todo : what is this commented code? is it useful?
//        int base = ArrayOperations.max(ArrayOperations.sum(tc_isp_res, isp_partitions_situation));
//        int factor = ArrayOperations.max(isp_partitions_situation);
//        for (int i=0; i<tc_isp_res.size(); i++) {
//            for (int j=0; j<tc_isp_res.get(i).size(); j++) {
//                if (tc_isp_res.get(i).get(j) > 0) {
//                    if (factor > 0)
//                        score += (base - tc_isp_res.get(i).get(j)) * (factor - isp_partitions_situation.get(i).get(j));
//                    else
//                        score += (base - tc_isp_res.get(i).get(j));
//                }
//            }
//        }
        for (int i=0; i<tc_isp_res.size(); i++) {
            for (int j=0; j<tc_isp_res.get(i).size(); j++) {
                if (isp_partitions_situation.size() > 0)
                    score += get_score(isp_partitions_situation.get(i).get(j), tc_isp_res.get(i).get(j));
                else
                    score += get_score(0, tc_isp_res.get(i).get(j));
            }
        }
        ExecutionAnalysis.write("----------------------------------------------------------------");
        ExecutionAnalysis.write("isp: " + isp_partitions_situation);
        ExecutionAnalysis.write("tc: " + tc_isp_res);
        ExecutionAnalysis.write("score: " + score);
        return score;
    }

    private static float get_score(int partition_covered_quantity, int tc_partition_covered_number) {
        float score = 0;
        int max_covering_partition = 3; // 3 first one is important
        int base = 10;                   // one 0->1 is better than ten 1->2
        for (int i=1; i<=tc_partition_covered_number; i++)
            score += Math.pow(base, max_covering_partition - (partition_covered_quantity + i));
        return score;
    }

    private static ArrayList<ArrayList<Integer>> add_to_isp_partitions_situations(ArrayList<ArrayList<Integer>> tc_isp_res) {
        ArrayList<ArrayList<Integer>> isp_result_tmp = new ArrayList<>(isp_partitions_situation);
        if (isp_result_tmp.size() == 0)
            return tc_isp_res;
        for(int i=0; i<isp_result_tmp.size(); i++)
            for(int j=0; j<isp_result_tmp.get(i).size(); j++)
                isp_result_tmp.get(i).set(j, isp_result_tmp.get(i).get(j) + tc_isp_res.get(i).get(j));
        return isp_result_tmp;
    }

    static void select_testcase(TestCase tc) {
        ArrayList<ArrayList<Integer>> tc_isp_res = Characteristic.get_testcase_isp_result(tc);
        isp_partitions_situation = add_to_isp_partitions_situations(tc_isp_res);
    }
}
