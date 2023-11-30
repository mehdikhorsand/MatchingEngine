package tools.activity;

public class Variable extends Action{
    String variable_name;
    public Variable(StackTraceElement element, String variable_name) {
        super(element);
        this.variable_name = variable_name;
    }

    @Override
    public String toString() {
        return method.class_name + "." + method.method_name + ":" + variable_name;
    }
}
