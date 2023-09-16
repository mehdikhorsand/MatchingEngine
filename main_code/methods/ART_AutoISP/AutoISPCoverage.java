package methods.ART_AutoISP;

import randomTestcase.TestCase;

import java.util.ArrayList;
import java.util.Objects;

public class AutoISPCoverage {
    public static TestCase get_best_candidate(ArrayList<MethodEdgePairCoverage> testcases_mep,
                                              ArrayList<AutoCharacteristic> isp_coverage_situation) {
        MethodEdgePairCoverage furthest_candidate = testcases_mep.get(0);
        double max_score = 0;
        for (MethodEdgePairCoverage c_mep:testcases_mep) {
            double score = get_score_based_on_isp_coverage(c_mep, isp_coverage_situation);
            if (max_score < score) {
                max_score = score;
                furthest_candidate = c_mep;
            }
        }
        select_testcase(furthest_candidate, isp_coverage_situation);
        return furthest_candidate.testcase;
    }

    public static double get_score_based_on_isp_coverage(MethodEdgePairCoverage c_mep, ArrayList<AutoCharacteristic> isp_coverage_situation) {
        double score = 0;
        for(AutoCharacteristic tc_ch : c_mep.characteristics) {
            boolean found_characteristic = false;
            for(AutoCharacteristic ch : isp_coverage_situation) {
                if(Objects.equals(ch.action, tc_ch.action)) {
                    found_characteristic = true;
                    for(AutoPartitions tc_ch_p : tc_ch.partitions) {
                        boolean found_partition = false;
                        for(AutoPartitions ch_p : ch.partitions) {
                            if(ch_p.same_behavior(tc_ch_p.behavior)) {
                                found_partition = true;
                                score += get_score(ch_p.covered_qty, tc_ch_p.covered_qty);
                                break;
                            }
                        }
                        if(!found_partition){
                            score += get_score(0, tc_ch_p.covered_qty);
                        }
                    }
                    break;
                }
            }
            if(!found_characteristic){
                for(AutoPartitions tc_ch_p : tc_ch.partitions){
                    score += get_score(0, tc_ch_p.covered_qty);
                }
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

    public static void select_testcase(MethodEdgePairCoverage furthestCandidate, ArrayList<AutoCharacteristic> isp_coverage_situation) {
//        System.out.println("**************************\nisp_coverage_situation:\n");
//        System.out.println(get_isp_partitions_in_string(isp_coverage_situation));
//        System.out.println("furthest_candidates characteristics:\n");
//        System.out.println(get_isp_partitions_in_string(furthestCandidate.characteristics));
        for(AutoCharacteristic tc_ch : furthestCandidate.characteristics) {
            boolean found_characteristic = false;
            for(AutoCharacteristic ch : isp_coverage_situation) {
                if(Objects.equals(ch.action, tc_ch.action)) {
                    found_characteristic = true;
                    for(AutoPartitions tc_ch_p : tc_ch.partitions) {
                        boolean found_partition = false;
                        for(AutoPartitions ch_p : ch.partitions) {
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
        System.out.println("isp_coverage_situation: new method \n");
        System.out.println(get_isp_partitions_in_string(isp_coverage_situation));
    }

    public static String get_isp_partitions_in_string(ArrayList<AutoCharacteristic> characteristics) {
        StringBuilder res = new StringBuilder();
        int i = 0;
        for (AutoCharacteristic characteristic : characteristics) {
            i++;
//            if(characteristic.partitions.size() > 1) {
                res.append("\nC").append(i).append(".\t").append(characteristic).append("\n");
                int j = 0;
                for (AutoPartitions partition : characteristic.partitions) {
                    j++;
                    res.append("|__ P").append(j).append(" (").append(partition.covered_qty).append(")\t")
                            .append(partition.behavior).append("\n");
                }
//            }
        }
        return res.toString();
    }
}
