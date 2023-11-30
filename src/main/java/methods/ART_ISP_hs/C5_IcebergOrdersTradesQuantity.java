package methods.ART_ISP_hs;

import randomOrder.CancelOrder;
import randomOrder.NewOrder;
import randomOrder.OrderInput;

import java.util.ArrayList;
import java.util.Objects;

public class C5_IcebergOrdersTradesQuantity extends Characteristic{

    private boolean p1(OrderInput order){
        int order_qty_before_trades = ((NewOrder) order).quantity;
        int order_qty_after_trades = order.order_result.new_order_remained_quantity();
        int peak_size = ((NewOrder) order).peak_size;
        if (order_qty_after_trades < order_qty_before_trades)
            if (order_qty_after_trades > order_qty_before_trades - peak_size)
                return true;
        return false;
    }

    private boolean p2(OrderInput order){
        int order_qty_before_trades = ((NewOrder) order).quantity;
        int order_qty_after_trades = order.order_result.new_order_remained_quantity();
        int peak_size = ((NewOrder) order).peak_size;
        if (order_qty_after_trades < order_qty_before_trades)
            if(order_qty_after_trades <= order_qty_before_trades - peak_size)
                return true;
        return false;
    }

    private boolean p3(OrderInput order) {
        int order_qty_before_trades = ((NewOrder) order).quantity;
        int order_qty_after_trades = order.order_result.new_order_remained_quantity();
        if (order_qty_after_trades == order_qty_before_trades)
            return true;
        return false;
    }

    @Override
    public ArrayList<Integer> get_result(OrderInput order) {
        ArrayList<Integer> res = new ArrayList<>();
        if(order.getClass() != CancelOrder.class && ((NewOrder) order).peak_size > 0 &&
                Objects.equals(order.order_result.new_order_status, "Accepted")) {
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
