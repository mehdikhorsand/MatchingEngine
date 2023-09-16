package tools.activity;

public class MethodFinish extends Action {
    public MethodFinish(StackTraceElement element) {
        super(element);
    }

    @Override
    public String toString() {
        return "@finished:" + line_number;
    }
}
