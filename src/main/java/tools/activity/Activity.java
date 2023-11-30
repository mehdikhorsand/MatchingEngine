package tools.activity;

import tools.SourceDetector;

public class Activity {
    public ParentMethod parent_method;
    public Action action;

    public Activity(ParentMethod parent_method, Action action) {
        this.parent_method = parent_method;
        this.action = action;
    }

    public Activity (Action action) {
        this.parent_method = null;
        this.action = action;
    }

    public static Activity method_called(Throwable stack) {
        ParentMethod parent_method = new ParentMethod(stack.getStackTrace()[2]);
        Action child_method = new MethodCall(stack.getStackTrace()[1]);
        for(Class src_class : SourceDetector.get_src_classes())
            if(src_class.getName().equals(parent_method.class_path))
                return new Activity(parent_method, child_method);
        return new Activity(child_method);
    }

    public static Activity method_finished(Throwable stack) {
        // todo: set parent_method in here and change algorithm to get that.
        Action finished_method = new MethodFinish(stack.getStackTrace()[1]);
        return new Activity(finished_method);
    }

    public static Activity condition_covered(Throwable stack) {
        ParentMethod method = new ParentMethod(stack.getStackTrace()[1]);
        Action condition = new Condition(stack.getStackTrace()[1]);
        return new Activity(method, condition);
    }

    public static Activity loop(Throwable stack, int loop_index, boolean is_ended) {
        ParentMethod method = new ParentMethod(stack.getStackTrace()[1]);
        Action loop = new Loop(stack.getStackTrace()[1], loop_index, is_ended);
        return new Activity(method, loop);
    }

    public static Activity start_loop(Throwable stack, int loop_index) {
        return loop(stack, loop_index, false);
    }

    public static Activity end_loop(Throwable stack, int loop_index) {
        return loop(stack, loop_index, true);
    }

    @Override
    public String toString() {
        return ((parent_method == null)? "":(parent_method + " -> ")) + action;
    }
}
