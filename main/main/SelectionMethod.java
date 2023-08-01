import java.util.ArrayList;

public class SelectionMethod {

    public TestCase best_candidate(TestCase[] candidate_set) {
        return null;
    }

    public static ArrayList<SelectionMethod> get_methods() {
        ArrayList<SelectionMethod> methods = new ArrayList<>();
        ArrayList<SelectionMethod> sub_classes = new ArrayList<>();
        sub_classes.add(new RT());
        sub_classes.add(new ART_ISP());
        for(String method : Settings.get_methods())
            for(SelectionMethod sub_class : sub_classes)
                if(sub_class.getClass().getName().equals(method))
                    methods.add(sub_class);
        return methods;
    }
}
