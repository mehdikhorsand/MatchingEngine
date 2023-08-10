import java.util.ArrayList;
import java.util.Objects;

public class Node {
    int value = 0;
    String[] key;

    public Node(String[] key) {
        this.key = key;
        increment_value();
    }

    public void increment_value() {
        value += 1;
    }
}
