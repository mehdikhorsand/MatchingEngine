package methods.ART_ISP_hs;

import randomOrder.OrderInput;
import randomTestcase.TestCase;

import java.util.ArrayList;

public class Characteristic {

    public static ArrayList<Characteristic> get_all_characteristics() {
        ArrayList<Characteristic> all = new ArrayList<>();
        all.add(new C1_MatchingOrders());
        all.add(new C2_OrdersPostConditions());
        all.add(new C3_MinQtyOrdersPostConditions());
        all.add(new C4_MinQtyOrdersPreConditions());
        all.add(new C5_IcebergOrdersTradesQuantity());
        all.add(new C6_CancelOrderResult());
        all.add(new C7_ReplaceOrderResult());
        return all;
    }

    public ArrayList<Integer> get_result(OrderInput order) {
        return null;
    }

    private static ArrayList<ArrayList<Integer>> get_orders_isp_result(OrderInput order) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (Characteristic C : get_all_characteristics())
            res.add(C.get_result(order));
        return res;
    }

    public static ArrayList<ArrayList<Integer>> get_testcase_isp_result(TestCase tc) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (OrderInput order : tc.orders) {
            res = ArrayOperations.sum(res, get_orders_isp_result(order));
        }
        return res;
    }

    public static int cast_to_int(boolean input){
        if(input)
            return 1;
        else
            return 0;
    }
}
