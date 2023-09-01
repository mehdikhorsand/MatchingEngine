package methods.ART_ISP;

import randomOrder.CancelOrder;
import randomOrder.OrderInput;

import java.util.ArrayList;
import java.util.Objects;

public class C6_CancelOrderResult extends Characteristic{

    private boolean p1(OrderInput order){
        return Objects.equals(order.order_result.new_order_status, "Accepted");
    }

    private boolean p2(OrderInput order){
        return Objects.equals(order.order_result.new_order_status, "Rejected");
    }

    @Override
    public ArrayList<Integer> get_result(OrderInput order) {
        ArrayList<Integer> res = new ArrayList<>();
        if(order.getClass() == CancelOrder.class) {
            res.add(cast_to_int(p1(order)));
            res.add(cast_to_int(p2(order)));
        }
        else {
            res.add(0);
            res.add(0);
        }
        return res;
    }
}
