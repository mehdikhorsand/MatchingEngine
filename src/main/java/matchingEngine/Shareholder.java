package matchingEngine;

import java.util.ArrayList;
// total branches : 22
// never covered with valid orders : 1

public class Shareholder {
    static ArrayList<Shareholder> list = new ArrayList<>();
    int id;
    int ownership;
    int free_ownership;
    int booked_buy_orders_qty;

    public Shareholder(int shareholder_id, int ownership) {
        this.id = shareholder_id;
        this.ownership = ownership;
        this.free_ownership = ownership;
        this.booked_buy_orders_qty = 0;
        list.add(this);
    }

    @Override
    public String toString() {
        return "\tOwnership\t" + id + "\t" + ownership;
    }

    public static Shareholder get_shareholder_by_id(int shareholder_id) {
        for (Shareholder shareholder : list)
            if (shareholder.id == shareholder_id)
                return shareholder;
        return null;
    }

    public static String print_ownerships() {
        StringBuilder result = new StringBuilder("\n\tOwnerships\t" + list.size());
        for(Shareholder shareholder : list)
            result.append("\n").append(shareholder.toString());
        return result.toString();
    }

    public void added_new_order(Order order) {
        if (order.is_buy)
            booked_buy_orders_qty += order.quantity;
        else
            free_ownership -= order.quantity;
    }

    public void deleted_old_order(Order order) {
        if (order.is_buy)
            booked_buy_orders_qty -= order.quantity;
        else
            free_ownership += order.quantity;
    }

    public void increase_ownership(Trade trade) {
        ownership += trade.quantity;
        free_ownership += trade.quantity;
        if (trade.buy_order_id.is_in_queue)
            booked_buy_orders_qty -= trade.quantity;
    }

    public void decrease_ownership(Trade trade) {
        ownership -= trade.quantity;
        if (!trade.sell_order_id.is_in_queue)
            free_ownership -= trade.quantity;
    }

    public boolean ownership_validation(Order order) {
        return order.is_buy || free_ownership >= order.quantity;
    }

    public void rollback_increase_ownership(Trade trade) {
        ownership -= trade.quantity;
        free_ownership -= trade.quantity;
        if (trade.buy_order_id.is_in_queue)
            booked_buy_orders_qty += trade.quantity;
    }

    public void rollback_decrease_ownership(Trade trade){
        ownership +=trade.quantity;
        if(!trade.sell_order_id.is_in_queue)
            free_ownership += trade.quantity;
    }
}
