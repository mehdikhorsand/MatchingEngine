package tools.activity;

import java.util.Objects;

public class Loop extends Action {
    public int loop_index;
    boolean is_ended;
    public Loop(StackTraceElement element, int loop_index, boolean is_ended) {
        super(element);
        this.loop_index = loop_index;
        this.is_ended = is_ended;
    }

    @Override
    public String toString() {
        return "@" + ((is_ended)? "end":"inside") + "_loop(" + loop_index + "):" + line_number;
    }

    @Override
    public boolean check_type(String action_type) {
        return Objects.equals(action_type, ((is_ended)? "End":"Inside") + "Loop");
    }
}
