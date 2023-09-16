package methods.ART_ISP_hs;

import randomOrder.CancelOrder;
import randomOrder.OrderInput;

import java.util.ArrayList;
import java.util.Objects;

public class C2_OrdersPostConditions extends Characteristic{

    private boolean p1(OrderInput order){
        if(order.order_result.is_there_x_order_in_new_order_book(!order.is_buy))
            if(order.order_result.has_all_new_orders_quantity_with_type_x_been_changed(!order.is_buy))
                return true;
        return false;
    }

    private boolean p2(OrderInput order){
        if(!order.order_result.is_there_x_order_in_new_order_book(!order.is_buy))
            return true;
        return false;
    }

    private boolean p3(OrderInput order){
        if(order.order_result.is_there_x_order_in_new_order_book(!order.is_buy))
            if(!order.order_result.has_all_new_orders_quantity_with_type_x_been_changed(!order.is_buy))
                return true;
        return false;
    }

    @Override
    public ArrayList<Integer> get_result(OrderInput order) {
        ArrayList<Integer> res = new ArrayList<>();
        if(order.getClass() != CancelOrder.class && Objects.equals(order.order_result.new_order_status, "Accepted")
                && order.order_result.is_there_x_order_in_previous_order_book(!order.is_buy)) {
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
