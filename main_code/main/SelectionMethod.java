package main;
import methods.ART_AutoISP.ART_AutoISP;
import methods.ART_AutoISP_noPartition.ART_AutoISP_noPartition;
import methods.ART_AutoISP_C.ART_AutoISP_C;
import methods.ART_AutoISP_MC.ART_AutoISP_MC;
import methods.ART_TFC.ART_TFC;
import methods.ART_WT.ART_WT;
import methods.ART_ISP_hs.ART_ISP_hs;
import methods.ART_FT.ART_FT;
import randomTestcase.TestCase;

import java.util.ArrayList;
import methods.RT.RT;

public class SelectionMethod {

    public TestCase best_candidate(TestCase[] candidate_set) {
        return null;
    }

    public static ArrayList<SelectionMethod> get_methods() {
        ArrayList<SelectionMethod> methods = new ArrayList<>();
        ArrayList<SelectionMethod> sub_classes = new ArrayList<>();
        sub_classes.add(new RT());
        sub_classes.add(new ART_ISP_hs());
        sub_classes.add(new ART_FT());
        sub_classes.add(new ART_WT());
        sub_classes.add(new ART_TFC());
        sub_classes.add(new ART_AutoISP());
        sub_classes.add(new ART_AutoISP_noPartition());
        sub_classes.add(new ART_AutoISP_MC());
        sub_classes.add(new ART_AutoISP_C());
//        sub_classes.add(new ART_G());
        for(String method : Settings.get_methods())
            for(SelectionMethod sub_class : sub_classes)
                if(sub_class.get_name().equals(method))
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

    public String get_name() {
        return this.getClass().getName().replace(this.getClass().getPackageName()+".", "");
    }
}
