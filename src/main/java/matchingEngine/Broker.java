package matchingEngine;

import java.util.ArrayList;
// total branches : 18
// never covered with valid orders : 1

public class Broker {
    static ArrayList<Broker> list = new ArrayList<>();
    int id;
    int credit;
    int free_credit;
    public Broker(int broker_id, int credit) {
        this.id = broker_id;
        this.credit = credit;
        this.free_credit = credit;
        list.add(this);
    }

    @Override
    public String toString() {
        return "\tCredit\t" + id + "\t" + credit;
    }

    public static Broker get_broker_by_id(int broker_id) {
        for(Broker broker : list)
            if (broker.id == broker_id)
                return broker;
        return null;
    }

    public static String print_credits() {
        StringBuilder result = new StringBuilder("\n\tCredits\t" + list.size());
        for(Broker broker : list)
            result.append("\n").append(broker.toString());
        return result.toString();
    }

    public void added_new_order(Order order) {
        if(order.is_buy)
            free_credit -= order.value();
    }

    public void deleted_old_order(Order order) {
        if(order.is_buy)
            free_credit += order.value();
    }

    public void increase_credit(Trade trade) {
        free_credit += trade.value;
        credit += trade.value;
    }

    public void decrease_credit(Trade trade) {
        credit -= trade.value;
        if(!trade.buy_order_id.is_in_queue)
            free_credit -= trade.value;
    }

    public boolean credit_validation(Order order) {
        return !order.is_buy || free_credit >= order.value();
    }

    public void rollback_increase_credit(Trade trade) {
        free_credit -= trade.value;
        credit -= trade.value;
    }

    public void rollback_decrease_credit(Trade trade) {
        credit += trade.value;
        if(!trade.buy_order_id.is_in_queue)
            free_credit += trade.value;
    }
}
