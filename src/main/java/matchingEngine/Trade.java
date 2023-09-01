package matchingEngine;
// total branches : 2
// never covered with valid orders : 0

public class Trade {
    Order buy_order_id;
    Order sell_order_id;
    int quantity;
    int price;
    int value;
    public Trade(int quantity, Order buy_order_id, Order sell_order_id) {
        this.buy_order_id = buy_order_id;
        this.sell_order_id = sell_order_id;
        this.quantity = quantity;
        this.price = get_price();
        this.value = quantity * price;
//        update order quantities
        buy_order_id.update_order_quantities(this);
        sell_order_id.update_order_quantities(this);
//        update credits and ownerships
        buy_order_id.broker_id.decrease_credit(this);
        sell_order_id.broker_id.increase_credit(this);
        buy_order_id.shareholder_id.increase_ownership(this);
        sell_order_id.shareholder_id.decrease_ownership(this);
    }

    @Override
    public String toString() {
        return "\n\tTrade\t" + price + "\t" + quantity + "\t" + buy_order_id.id + "\t" + sell_order_id.id;
    }

    public int get_price() {
        if(buy_order_id.is_in_queue)
            return buy_order_id.price;
        else
            return sell_order_id.price;
    }

    public void rollback_trade() {
        buy_order_id.rollback_update_order_quantities(this);
        sell_order_id.rollback_update_order_quantities(this);
        buy_order_id.broker_id.rollback_decrease_credit(this);
        sell_order_id.broker_id.rollback_increase_credit(this);
        buy_order_id.shareholder_id.rollback_increase_ownership(this);
        sell_order_id.shareholder_id.rollback_decrease_ownership(this);
    }
}
