import java.util.ArrayList;
// total branches : 21
// never covered with valid orders : 1

public class Broker {
    static ArrayList<Broker> list = new ArrayList<>();
    int id;
    int credit;
    int free_credit;
    public Broker(int broker_id, int credit) {
        TCRunner.method_called(new Throwable());
        this.id = broker_id;
        this.credit = credit;
        this.free_credit = credit;
        list.add(this);
    }

    @Override
    public String toString() {
        TCRunner.method_called(new Throwable());
        return "\tCredit\t" + id + "\t" + credit;
    }

    public static Broker get_broker_by_id(int broker_id) {
        TCRunner.method_called(new Throwable());
        for(Broker broker : list)
            if(broker.id == broker_id)
                return broker;
        return null;
    }

    public static String print_credits() {
        TCRunner.method_called(new Throwable());
        StringBuilder result = new StringBuilder("\n\tCredits\t" + list.size());
        for(Broker broker : list)
            result.append("\n").append(broker.toString());
        return result.toString();
    }

    public void added_new_order(Order order) {
        TCRunner.method_called(new Throwable());
        if(order.is_buy)
            free_credit -= order.value();
    }

    public void deleted_old_order(Order order) {
        TCRunner.method_called(new Throwable());
        if(order.is_buy)
            free_credit += order.value();
    }

    public void increase_credit(Trade trade) {
        TCRunner.method_called(new Throwable());
        free_credit += trade.value;
        credit += trade.value;
    }

    public void decrease_credit(Trade trade) {
        TCRunner.method_called(new Throwable());
        credit -= trade.value;
        if(!trade.buy_order_id.is_in_queue)
            free_credit -= trade.value;
    }

    public boolean credit_validation(Order order) {
        TCRunner.method_called(new Throwable());
        return !order.is_buy || free_credit >= order.value();
    }

    public void rollback_increase_credit(Trade trade) {
        TCRunner.method_called(new Throwable());
        free_credit -= trade.value;
        credit -= trade.value;
    }

    public void rollback_decrease_credit(Trade trade) {
        TCRunner.method_called(new Throwable());
        credit += trade.value;
        if(!trade.buy_order_id.is_in_queue)
            free_credit += trade.value;
    }
}
