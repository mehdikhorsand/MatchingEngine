import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;


public class TestCaseRunner{
    @Test
    public void tester () throws Exception {
        for(int i=0; i<Settings.testcase_number; i++) {
            new TCRunner(Settings.get_target_testcase_path(i), Settings.get_target_output_path(i));
            evaluation(Settings.get_target_output_path(i), Settings.get_target_oracle_path(i));
        }
    }

    public static void evaluation(String output_location, String oracle_location) throws Exception {
        String output = read_file(output_location);
        String oracle = read_file(oracle_location);
        String error_msg = "";

        error_msg += evaluate(output, oracle, "OrderRs");
        error_msg += evaluate(output, oracle, "Trades");
        error_msg += evaluate(output, oracle, "Trade");
        error_msg += evaluate(output, oracle, "Orders");
        error_msg += evaluate(output, oracle, "Order");
        error_msg += evaluate(output, oracle, "Ownerships");
        error_msg += evaluate(output, oracle, "Ownership");
        error_msg += evaluate(output, oracle, "Credits");
        error_msg += evaluate(output, oracle, "Credit");

        if (!error_msg.equals(""))
            throw new Exception("\nerror_messages:\n" + error_msg);
    }

    public static String read_file(String file_address) {
        File file = new File(file_address);
        try{
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\Z");
            return scanner.next();
        } catch (FileNotFoundException e) {
            return "";
        }
    }

    @Before
    public void setup(){
        System.out.println("\n---------------------------------------------");
    }

    @After
    public void tearDown(){
        System.out.println("\n---------------------------------------------");
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
                "\noracle:   " + expected +
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
