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
        sub_classes.add(new ART_FT());
        sub_classes.add(new ART_WT());
        sub_classes.add(new ART_TFC());
        for(String method : Settings.get_methods())
            for(SelectionMethod sub_class : sub_classes)
                if(sub_class.getClass().getName().equals(method))
                    methods.add(sub_class);
        return methods;
    }

    public static void reset_selection_methods() {
        for(SelectionMethod method : get_methods()){
            method.reset();
        }
    }

    public void reset() {
        // clear executed set or other data that has been saved in the selection methods.
    }
}
