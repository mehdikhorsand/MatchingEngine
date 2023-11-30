package main;
import randomTestcase.TestCase;


public class SelectionMethod {

    public TestCase best_candidate(TestCase[] candidate_set) {
        return null;
    }

    public static void reset_selection_methods() {
        for(SelectionMethod method : Settings.get_methods()){
            method.reset();
        }
    }

    public void reset() {
        // clear executed set or other data that has been saved in the selection methods.
    }

    public String get_name() {
        return this.getClass().getName().replace(this.getClass().getPackageName()+".", "");
    }
}
