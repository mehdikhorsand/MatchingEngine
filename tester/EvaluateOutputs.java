import java.util.Arrays;
import java.util.Objects;

public class EvaluateOutputs {

    public static void evaluation(String java_res_addr, String hs_res_addr) {
        String java_res = Terminal.run_command_with_output("cat " + java_res_addr, true);
        String haskell_res = Terminal.run_command_with_output("cat " + hs_res_addr, true);
        String error_msg = "";

        error_msg += evaluate(java_res, haskell_res, "OrderRs");
        error_msg += evaluate(java_res, haskell_res, "Trades");
        error_msg += evaluate(java_res, haskell_res, "Trade");
        error_msg += evaluate(java_res, haskell_res, "Orders");
        error_msg += evaluate(java_res, haskell_res, "Order");
        error_msg += evaluate(java_res, haskell_res, "Ownerships");
        error_msg += evaluate(java_res, haskell_res, "Ownership");
        error_msg += evaluate(java_res, haskell_res, "Credits");
        error_msg += evaluate(java_res, haskell_res, "Credit");

        if (!error_msg.equals(""))
            throw new IllegalArgumentException("\nerror_messages:\n" + error_msg);
    }

    public static String evaluate(String java_res, String haskell_res, String template) {
        String[] hs = find(haskell_res, template);
        String[] java = find(java_res, template);
        for(int i=0; i<hs.length; i++){
            if(i < java.length){
                if(!Objects.equals(hs[i], java[i])){
                    return error_template(hs[i], java[i], template, i);
                }
            } else {
                return error_template(hs[i], "Nothing", template, i);
            }
        }
        return "";
    }

    public static String error_template (String expected, String output, String template, int index) {
        int div = (Objects.equals(template, "Ownership") || Objects.equals(template, "Credit"))? 7:1;
        return "\nError matching [" + template + "]! " +
                ((Objects.equals(template, "Order") || Objects.equals(template, "Trade"))? "":"order_id") +
                " = " + (int)Math.ceil((double) (index + 1) / div) +
                "\nexpected:   " + expected +
                "\noutput:     " + output + "\n";
    }

    public static String[] find(String testcase_output, String template) {
        String[] split_output = testcase_output.split(template + "\t");
        for(int i=1; i<split_output.length; i++){
            split_output[i] = split_output[i].split("\n")[0];
        }
        return Arrays.copyOfRange(split_output, 1, split_output.length);
    }
}
