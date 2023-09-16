package methods.ART_ISP_hs;

import randomOrder.CancelOrder;
import randomOrder.NewOrder;
import randomOrder.OrderInput;

import java.util.ArrayList;
import java.util.Objects;

public class C4_MinQtyOrdersPreConditions extends Characteristic{

    private boolean p1(OrderInput order){
        if(((NewOrder) order).min_qty == ((NewOrder) order).quantity)
            return true;
        return false;
    }

    private boolean p2(OrderInput order){
        if(((NewOrder) order).min_qty < ((NewOrder) order).quantity && ((NewOrder) order).min_qty > 0)
            return true;
        return false;
    }

    private boolean p3(OrderInput order) {
        if(((NewOrder) order).min_qty == 0)
            return true;
        return false;
    }

    @Override
    public ArrayList<Integer> get_result(OrderInput order) {
        ArrayList<Integer> res = new ArrayList<>();
        if(order.getClass() != CancelOrder.class && Objects.equals(order.order_result.new_order_status, "Accepted")) {
            res.add(cast_to_int(p1(order)));
            res.add(cast_to_int(p2(order)));
            res.add(cast_to_int(p3(order)));
        }
        else {
            res.add(0);
            res.add(0);
            res.add(0);
        }
        return res;
    }
}
