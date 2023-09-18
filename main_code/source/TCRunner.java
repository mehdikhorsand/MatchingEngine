package source;

import main.Settings;
import randomTestcase.TestCase;
import tools.TCWriter;
import tools.Terminal;
import tools.activity.Action;
import tools.activity.Activity;
import tools.activity.Path;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
// total branch in matching engine source code : 182
// branches not covered by valid orders : 12

public class TCRunner {
    static String destination_file_path = null;
    Environment environment;
    MatchingEngine matching_engine;
    static FileWriter writer;
    public static ArrayList<String> method_invocation_sequence;
    public static ArrayList<Activity> method_invocation_edge;
    public static ArrayList<Activity> method_and_condition_coverage;
    public static ArrayList<ArrayList<Action>> method_invocation_paths;
    public static ArrayList<ArrayList<Action>> value_quantification_paths;
    public static ArrayList<ArrayList<Action>> method_and_condition_coverage_paths;

    public TCRunner(String input_file_path, String output_file_path) {
        method_invocation_sequence = new ArrayList<>();
        method_invocation_edge = new ArrayList<>();
        method_and_condition_coverage = new ArrayList<>();
        method_invocation_paths = new ArrayList<>();
        value_quantification_paths = new ArrayList<>();
        method_and_condition_coverage_paths = new ArrayList<>();
        TCRunner.destination_file_path = output_file_path;
        try {
            Scanner scanner = new Scanner(new File(input_file_path));
            writer = new FileWriter(output_file_path);
            environment = new Environment();
            matching_engine = new MatchingEngine(environment);
            while (scanner.hasNextLine()) {
                execute_line(scanner.nextLine());
            }
            scanner.close();
            writer.close();
            reset_matching_engine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reset_matching_engine() {
        Order.reset_order_counter();
        Broker.list = new ArrayList<>();
        Shareholder.list = new ArrayList<>();
    }

    public void execute_line(String line) {
        String[] rq_args = Arrays.stream(line.replace("\t", " ")
                .split(" ")).filter(s -> !s.isEmpty()).toArray(String[]::new);
        String request = rq_args[0];
        String[] args = Arrays.copyOfRange(rq_args, 1, rq_args.length);
        switch(request) {
            case "SetTickSizeRq":
                environment.set_tick_size_rq(Integer.parseInt(args[0]));
                break;
            case "SetLotSizeRq":
                environment.set_lot_size_rq(Integer.parseInt(args[0]));
                break;
            case "SetReferencePriceRq":
                environment.set_reference_price_rq(Integer.parseInt(args[0]));
                break;
            case "SetStaticPriceBandLowerLimitRq":
                environment.set_static_price_band_lower_limit_rq(Float.parseFloat(args[0]));
                break;
            case "SetStaticPriceBandUpperLimitRq":
                environment.set_static_price_band_upper_limit_rq(Float.parseFloat(args[0]));
                break;
            case "SetTotalSharesRq":
                environment.set_total_shares_rq(Integer.parseInt(args[0]));
                break;
            case "SetOwnershipUpperLimitRq":
                environment.set_ownership_upper_limit_rq(Float.parseFloat(args[0]));
                break;
            case "SetCreditRq":
                new Broker(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                break;
            case "SetOwnershipRq":
                new Shareholder(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                break;
            case "NewOrderRq":
                new_order_rq(args);
                break;
            case "ReplaceOrderRq":
                replace_order_rq(args);
                break;
            case "CancelOrderRq":
                cancel_order_rq(args);
                break;
        }
    }

    public void new_order_rq(String[] args) {
        Order new_order = create_order(args);
        matching_engine.new_order_request(new_order);
        print_output(matching_engine.toString());
    }

    public void replace_order_rq(String[] args) {
        Order replace_order = create_order(Arrays.copyOfRange(args, 1, args.length));
        matching_engine.replace_order_request(Integer.parseInt(args[0]), replace_order);
        print_output(matching_engine.toString());
    }

    public void cancel_order_rq(String[] args) {
        Order.new_cancel_order();
        matching_engine.cancel_order_request(Integer.parseInt(args[0]), get_boolean(args[1]));
        print_output(matching_engine.toString());
    }

    public static void print_output(String output) {
        try{
            writer.write(output);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean get_boolean(String input) {
        return Objects.equals(input, "True");
    }

    public static Order create_order(String[] args) {
        return new Order(
                Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                Integer.parseInt(args[3]), get_boolean(args[4]), Integer.parseInt(args[5]),
                get_boolean(args[6]), Integer.parseInt(args[7]));
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void method_called() {
        Throwable stack = new Throwable();
        String class_name = stack.getStackTrace()[1].getClassName();
        String method_name = stack.getStackTrace()[1].getMethodName();
        method_invocation_sequence.add(class_name.split("\\.")[1] + "." + method_name);
        Activity activity = Activity.method_called(stack);
        method_invocation_edge.add(activity);
        method_and_condition_coverage.add(activity);
        ArrayList<Action> path = Path.get_method_invocation_path(stack);
        method_invocation_paths.add(path);
        method_and_condition_coverage_paths.add(path);
    }

    public static void method_finished() {
        Throwable stack = new Throwable();
        Activity activity = Activity.method_finished(stack);
        method_invocation_edge.add(activity);
        method_and_condition_coverage.add(activity);
    }

    public static void start_loop(int loop_index) {
        Throwable stack = new Throwable();
        Activity activity = Activity.start_loop(stack, loop_index);
        method_invocation_edge.add(activity);
        method_and_condition_coverage.add(activity);
        condition_covered2(stack);
    }

    public static void end_loop(int loop_index) {
        Throwable stack = new Throwable();
        Activity activity = Activity.end_loop(stack, loop_index);
        method_invocation_edge.add(activity);
        method_and_condition_coverage.add(activity);
        condition_covered2(stack);
    }

    public static void condition_covered() {
        Throwable stack = new Throwable();
        method_and_condition_coverage.add(Activity.condition_covered(stack));
        condition_covered2(stack);
    }

    public static void condition_uncovered() {
        Throwable stack = new Throwable();
        condition_covered2(stack);
    }

    public static void condition_covered2(Throwable stack) {
        method_and_condition_coverage_paths.add(Path.get_condition_coverage_path(stack));
    }

    public static void run(TestCase testcase) {
        String src_path = Settings.temp + Settings.test_file_name + Settings.testcase_format;
        String des_path = Settings.temp + Settings.output + Settings.testcase_format;
        TCWriter.write_into_txt_format(src_path, testcase);
        new TCRunner(src_path, des_path);
        Terminal.rm(src_path);
        Terminal.rm(des_path);
    }

//    public static void value_changed() {
//        String variable_name = ""; // todo: set variable name
//        Throwable stack = new Throwable();
//        ArrayList<Action> action = Path.get_value_quantification_path(stack, variable_name);
//        System.out.println(action);
//        value_quantification_paths.add(action);
//    }
}
