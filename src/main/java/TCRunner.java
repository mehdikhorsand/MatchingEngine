import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
// total branch in matching engine source code : 178
// branches not covered by valid orders : 12

public class TCRunner {
    static String destination_file_path = null;
    Environment environment;
    MatchingEngine matching_engine;
    static FileWriter writer;
    static ArrayList<String> method_invocation_sequence;
    static ArrayList<ArrayList<String>> method_invocation_path;

    public TCRunner(String input_file_path, String output_file_path) {
        method_invocation_sequence = new ArrayList<>();
        method_invocation_path = new ArrayList<>();
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
        Order new_order = get_order(args);
        matching_engine.new_order_request(new_order);
        print_output(matching_engine.toString());
    }

    public void replace_order_rq(String[] args) {
        Order replace_order = get_order(Arrays.copyOfRange(args, 1, args.length));
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

    public static Order get_order(String[] args) {
        return new Order(
                Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                Integer.parseInt(args[3]), get_boolean(args[4]), Integer.parseInt(args[5]),
                get_boolean(args[6]), Integer.parseInt(args[7]));
    }

    public static void method_called(Throwable stack) {
        set_method_invocation_sequence(stack);
        set_method_invocation_path(stack);
    }

    public static void set_method_invocation_sequence(Throwable stack) {
        String class_name = stack.getStackTrace()[0].getClassName();
        String method_name = stack.getStackTrace()[0].getMethodName();
        method_invocation_sequence.add(class_name + "." + method_name);
    }

    public static void set_method_invocation_path(Throwable stack) {
        int path_length = Math.min(Settings.method_invocation_path_length, stack.getStackTrace().length);
        ArrayList<String> new_method_path = new ArrayList<>();
        for(int i=0; i<path_length; i++) {
            String class_name = stack.getStackTrace()[i].getClassName();
            for(Class src_class : get_src_classes()) {
                if(src_class.getName().equals(class_name)) {
                    String method_name = stack.getStackTrace()[i].getMethodName();
                    new_method_path.add(0, class_name + "." + method_name);
                    break;
                }
            }
        }
//        System.out.println(new_method_path);
        method_invocation_path.add(new_method_path);
    }

    public static ArrayList<String> get_src_methods_name() {
        ArrayList<String> output = new ArrayList<>();
        for(Class src_class : get_src_classes()) {
            Method[] methods = src_class.getDeclaredMethods();
            for(Method method : methods) {
                output.add(src_class.getName() + "." + method.getName());
            }
        }
        return output;
    }

    public static ArrayList<Class> get_src_classes() {
        ArrayList<Class> src_classes = new ArrayList<>();
        src_classes.add(Broker.class);
        src_classes.add(Shareholder.class);
        src_classes.add(Order.class);
        src_classes.add(Trade.class);
        src_classes.add(OrderBook.class);
        src_classes.add(MatchingEngine.class);
        src_classes.add(Environment.class);
        return src_classes;
    }
}
