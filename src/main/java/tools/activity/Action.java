package tools.activity;

public class Action {
    public ParentMethod method;
    int line_number;
    public Action(StackTraceElement element) {
        this.method = new ParentMethod(element);
        line_number = element.getLineNumber();
    }

    @Override
    public String toString() {
        return method.class_name + ":" + line_number;
    }

    public boolean check_type(String action_type) {
        return this.getClass().getName().equals(this.getClass().getPackageName() + "." +action_type);
    }

    @Override
    public boolean equals(Object obj){
        try{
            Action another = (Action) obj;
            return this.method.equals(another.method) && this.getClass().equals(another.getClass()) &&
                    (this.line_number == another.line_number || check_type("MethodCall"));
        }
        catch (Exception e) {
            return false;
        }
    }
}
