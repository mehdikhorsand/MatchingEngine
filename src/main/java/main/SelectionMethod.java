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
        // reset timer
    }

    public String get_name() {
        return this.getClass().getName().replace(this.getClass().getPackageName()+".", "");
    }

    public void add_execution_time(double time_spend) {
        // execution time includes time for creating candidate and testcase selection and running testcases.
    }

    public int get_f_time(int index) {
        // return f_time.get(index);
        return 0;
    }
}
