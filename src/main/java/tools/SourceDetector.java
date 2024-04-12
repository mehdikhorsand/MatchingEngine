package tools;

import source.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class SourceDetector {
    public static ArrayList<String> get_src_methods_name() {
        ArrayList<String> output = new ArrayList<>();
        for(Class src_class : get_src_classes()) {
            Method[] methods = src_class.getDeclaredMethods();
            for(Method method : methods) {
                output.add(src_class.getName().replace(src_class.getPackageName() + ".", "") + "." + method.getName());
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
