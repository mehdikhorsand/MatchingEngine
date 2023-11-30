package tools.AutoISP;

import randomTestcase.TestCase;
import tools.ExecutionAnalysis;

import java.util.ArrayList;
import java.util.Objects;

public class AutoISPCoverage {
    public static TestCase get_best_candidate(ArrayList<TestCaseRepresentation> testcases_rep,
                                              ArrayList<AutoCharacteristic> isp_coverage_situation) {
        TestCaseRepresentation furthest_candidate = testcases_rep.get(0);
        double max_score = 0;
        for (TestCaseRepresentation tc:testcases_rep) {
            double score = get_score_based_on_isp_coverage(tc, isp_coverage_situation);
            if (max_score < score) {
                max_score = score;
                furthest_candidate = tc;
            }
        }
        select_testcase(furthest_candidate, isp_coverage_situation);
        return furthest_candidate.testcase;
    }

    public static double get_score_based_on_isp_coverage(TestCaseRepresentation c_rep,
                                                         ArrayList<AutoCharacteristic> isp_coverage_situation) {
        double score = 0;
        for(AutoCharacteristic tc_ch : c_rep.characteristics) {
            boolean found_characteristic = false;
            for(AutoCharacteristic ch : isp_coverage_situation) {
                if(Objects.equals(ch.action, tc_ch.action)) {
                    found_characteristic = true;
                    for(AutoPartition tc_ch_p : tc_ch.partitions) {
                        boolean found_partition = false;
                        for(AutoPartition ch_p : ch.partitions) {
                            if(ch_p.same_behavior(tc_ch_p.behavior)) {
//                                todo : partition and characteristic found -> do nothing.
                                found_partition = true;
                                score += get_score(ch_p.covered_qty, tc_ch_p.covered_qty);
                                break;
                            }
                        }
                        if(!found_partition){
//                            todo : just characteristic found. -> add new partition.
                            ch.partitions.add(new AutoPartition(tc_ch_p.behavior, 0));
                            score += get_score(0, tc_ch_p.covered_qty);
                        }
                    }
                    break;
                }
            }
            if(!found_characteristic){
//                todo : nothing founded. -> add new characteristic and partitions.
                AutoCharacteristic new_ch = new AutoCharacteristic(tc_ch.action);
                for(AutoPartition tc_ch_p : tc_ch.partitions){
                    score += get_score(0, tc_ch_p.covered_qty);
                    new_ch.partitions.add(new AutoPartition(tc_ch_p.behavior, 0));
                }
                isp_coverage_situation.add(new_ch);
            }
        }
        return score;
    }

    private static double get_score(int partition_covered_quantity, int tc_partition_covered_number) {
        double score = 0;
        int max_covering_partition = 3;  // 3 first one is important
        int base = 5;                    // 1 x (0->1) is better than 5 x (1->2)
        for (int i=1; i<=tc_partition_covered_number; i++)
            score += Math.pow(base, max_covering_partition - (partition_covered_quantity + i));
        return score;
    }

    public static void select_testcase(TestCaseRepresentation furthestCandidate, ArrayList<AutoCharacteristic> isp_coverage_situation) {
//        ExecutionAnalysis.write("Before:\n" + get_isp_partitions_in_string(isp_coverage_situation));
//        ExecutionAnalysis.write_selected_tc_analysis(get_isp_partitions_in_string(furthestCandidate.characteristics));
        for(AutoCharacteristic tc_ch : furthestCandidate.characteristics) {
            boolean found_characteristic = false;
            for(AutoCharacteristic ch : isp_coverage_situation) {
                if(Objects.equals(ch.action, tc_ch.action)) {
                    found_characteristic = true;
                    for(AutoPartition tc_ch_p : tc_ch.partitions) {
                        boolean found_partition = false;
                        for(AutoPartition ch_p : ch.partitions) {
                            if(ch_p.same_behavior(tc_ch_p.behavior)) {
                                found_partition = true;
                                ch_p.covered_qty += tc_ch_p.covered_qty;
                                break;
                            }
                        }
                        if(!found_partition) {
                            ch.partitions.add(tc_ch_p);
                        }
                    }
                    break;
                }
            }
            if(!found_characteristic) {
                isp_coverage_situation.add(tc_ch);
            }
        }
        ExecutionAnalysis.write("After:\n" + get_isp_partitions_in_string(isp_coverage_situation));
    }

    public static String get_isp_partitions_in_string(ArrayList<AutoCharacteristic> characteristics) {
        StringBuilder res = new StringBuilder();
        for(int i=0; i<characteristics.size(); i++) {
            res.append("\nC").append(i+1).append(".\t").append(characteristics.get(i)).append("\n");
            for(int j=0; j<characteristics.get(i).partitions.size(); j++) {
                res.append("|__ P").append(j+1).append(" (").append(characteristics.get(i).partitions.get(j).covered_qty).append(")\t")
                        .append(characteristics.get(i).partitions.get(j).behavior).append("\n");
            }
        }
        return res.toString();
    }
}
