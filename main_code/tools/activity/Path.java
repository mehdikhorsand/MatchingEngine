package tools.activity;

import main.Settings;
import tools.SourceDetector;

import java.util.ArrayList;

public class Path {
    public static ArrayList<Action> get_method_invocation_path(Throwable stack) {
        ArrayList<Action> method_invocation_path = new ArrayList<>();
        for(StackTraceElement element : stack.getStackTrace()) {
            for (Class src_class : SourceDetector.get_src_classes()) {
                Action new_method = new MethodCall(element);
                if(src_class.getName().equals(new_method.method.class_path)) {
                    method_invocation_path.add(0, new_method);
                    break;
                }
            }
            if(method_invocation_path.size() == Settings.maximum_path_length)
                break;
        }
        return method_invocation_path;
    }

    public static ArrayList<Action> get_condition_coverage_path(Throwable stack) {
        ArrayList<Action> condition_coverage_path = get_method_invocation_path(stack);
        condition_coverage_path.add(new Condition(stack.getStackTrace()[1]));
        return condition_coverage_path;
    }

//    public static ArrayList<Action> get_value_quantification_path(Throwable stack, String variable_name) {
//        ArrayList<Action> value_quantification_path = get_method_invocation_path(stack);
//        value_quantification_path.add(new Variable(stack.getStackTrace()[1], variable_name));
//        return value_quantification_path;
//    }
}
