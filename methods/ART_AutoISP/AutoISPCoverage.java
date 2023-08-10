import java.util.ArrayList;
import java.util.Objects;

public class AutoISPCoverage {

    static ArrayList<AutoCharacteristic> isp_coverage_situation = new ArrayList<>();

    public static double get_score_based_on_isp_coverage(MethodEdgePairCoverage c_mep) {
        double score = 0;
        for(AutoCharacteristic tc_ch : c_mep.characteristics) {
            boolean found_characteristic = false;
            for(AutoCharacteristic ch : isp_coverage_situation) {
                if(Objects.equals(ch.starting_method, tc_ch.starting_method)) {
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

    public static void select_testcase(MethodEdgePairCoverage furthestCandidate) {
        System.out.println("**************************\nisp_coverage_situation:\n");
        System.out.println(get_isp_partitions_in_string(isp_coverage_situation));
        System.out.println("furthest_candidates characteristics:\n");
        System.out.println(get_isp_partitions_in_string(furthestCandidate.characteristics));
        for(AutoCharacteristic tc_ch : furthestCandidate.characteristics) {
            boolean found_characteristic = false;
            for(AutoCharacteristic ch : isp_coverage_situation) {
                if(Objects.equals(ch.starting_method, tc_ch.starting_method)) {
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
        System.out.println("isp_coverage_situation after adding selected testcase: \n");
        System.out.println(get_isp_partitions_in_string(isp_coverage_situation));
    }

    public static String get_isp_partitions_in_string(ArrayList<AutoCharacteristic> characteristics) {
        StringBuilder res = new StringBuilder();
        for (AutoCharacteristic characteristic : characteristics) {
            res.append(characteristic.starting_method).append("\n");
            for(AutoPartitions partition : characteristic.partitions) {
                res.append(" |___ ").append(partition.behavior).append("\n");
            }
        }
        return res.toString();
    }
}
