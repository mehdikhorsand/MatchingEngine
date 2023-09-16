package methods.ART_ISP_hs;

import randomOrder.CancelOrder;
import randomOrder.OrderInput;

import java.util.ArrayList;
import java.util.Objects;

public class C1_MatchingOrders extends Characteristic{

    private boolean p1(OrderInput order){
//        trades: 1;    orders quantity: --1;   order remains: false;
        if(order.order_result.get_new_trades_quantity() == 1)
            if(order.order_result.get_order_book_size_difference() == -1)
                if(!order.order_result.is_new_order_added_to_the_order_book)
                    return true;
        return false;
    }

    private boolean p2(OrderInput order){
//        trades: 1; order quantity: no change; order remains: false;
        if(order.order_result.get_new_trades_quantity() == 1)
            if(order.order_result.get_order_book_size_difference() == 0)
                if(!order.order_result.is_new_order_added_to_the_order_book)
                    return true;
        return false;
    }

    private boolean p3(OrderInput order){
//        trades: >1; order remains : false;
        if(order.order_result.get_new_trades_quantity() > 1)
            if(!order.order_result.is_new_order_added_to_the_order_book)
                return true;
        return false;
    }

    private boolean p4(OrderInput order) {
//        trades: 0;
        if(order.order_result.get_new_trades_quantity() == 0)
            return true;
        return false;
    }

    private boolean p5(OrderInput order){
//        trades: 0; order remains: true;
        if(order.order_result.get_new_trades_quantity() >= 1)
            if(order.order_result.is_new_order_added_to_the_order_book)
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
