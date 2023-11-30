package tools.activity;

public class Condition extends Action {
    public Condition(StackTraceElement element) {
        super(element);
    }

    @Override
    public String toString() {
        return "@condition:" + line_number;
    }
}
