package methods.ART_ISP_hs;

import randomOrder.CancelOrder;
import randomOrder.NewOrder;
import randomOrder.OrderInput;

import java.util.ArrayList;
import java.util.Objects;

public class C3_MinQtyOrdersPostConditions extends Characteristic{

    private boolean p1(OrderInput order){
        if(!Objects.equals(order.order_result.new_order_status, "Accepted"))
            if(order.order_result.is_x_the_min_qty_order_rejection_reason(order, "min_qty"))
                return true;
        return false;
    }

    private boolean p2(OrderInput order){
        if(!Objects.equals(order.order_result.new_order_status, "Accepted"))
            if(order.order_result.is_x_the_min_qty_order_rejection_reason(order, "price"))
                return true;
        return false;
    }

    private boolean p3(OrderInput order){
        if(Objects.equals(order.order_result.new_order_status, "Accepted"))
            if(!order.order_result.is_new_order_added_to_the_order_book)
                return true;
        return false;
    }

    private boolean p4(OrderInput order) {
        if(Objects.equals(order.order_result.new_order_status, "Accepted"))
            if(order.order_result.is_new_order_added_to_the_order_book)
                if(order.order_result.new_order_remained_quantity() ==
                        ((NewOrder) order).quantity - ((NewOrder) order).min_qty)
                    return true;
        return false;
    }

    private boolean p5(OrderInput order){
        if(Objects.equals(order.order_result.new_order_status, "Accepted"))
            if(order.order_result.is_new_order_added_to_the_order_book)
                if(order.order_result.new_order_remained_quantity() <
                        ((NewOrder) order).quantity - ((NewOrder) order).min_qty)
                    return true;
        return false;
    }

    @Override
    public ArrayList<Integer> get_result(OrderInput order) {
        ArrayList<Integer> res = new ArrayList<>();
        if(order.getClass() != CancelOrder.class && ((NewOrder) order).min_qty > 0
                && order.order_result.is_there_x_order_in_previous_order_book(!order.is_buy)) {
            res.add(cast_to_int(p1(order)));
            res.add(cast_to_int(p2(order)));
            res.add(cast_to_int(p3(order)));
            res.add(cast_to_int(p4(order)));
            res.add(cast_to_int(p5(order)));
        }
        else {
            res.add(0);
            res.add(0);
            res.add(0);
            res.add(0);
            res.add(0);
        }
        return res;
    }
}
