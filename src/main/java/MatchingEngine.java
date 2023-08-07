import java.util.ArrayList;
import java.util.Objects;
// total branches : 44
// never covered with valid orders : 5

public class MatchingEngine {
    Environment environment;
    OrderBook order_book;
    ArrayList<Trade> trades;
    String last_request_type;

    public MatchingEngine(Environment environment){
        TCRunner.method_called(new Throwable());
        this.environment = environment;
        order_book = new OrderBook();
        trades = new ArrayList<>();
    }

    public void new_request(String req_type, Order order) {
        TCRunner.method_called(new Throwable());
        trades.clear();
        last_request_type = req_type;
        String order_info = (order != null)? order.toString().replace("\tOrder\t", ""):"";
        TCRunner.print_output(order_info + "\n");
    }

    public void cancel_order_request(int order_id, boolean is_buy_order) {
        TCRunner.method_called(new Throwable());
        new_request("Cancel", null);
        Order order = order_book.get_order(order_id);
        if(order != null && order.is_buy == is_buy_order){
            order_book.remove_order(order);
            TCRunner.print_output("CancelOrderRs\tAccepted");
        }
        else
            TCRunner.print_output("CancelOrderRs\tRejected");
    }

    public void replace_order_request(int old_order_id, Order new_order) {
        TCRunner.method_called(new Throwable());
        new_request("Replace", new_order);
        Order old_order = order_book.get_order(old_order_id);
        if (old_order != null &&
            old_order.broker_id.id == new_order.broker_id.id &&
            old_order.shareholder_id.id == new_order.shareholder_id.id &&
            old_order.is_buy == new_order.is_buy && new_order.min_qty == 0) {
            int old_order_index = order_book.get_order_index(old_order);
            order_book.remove_order(old_order);
            String new_order_response = add_order(new_order);
            if (Objects.equals(new_order_response, "Rejected"))
                order_book.insert_order(old_order, old_order_index);
            TCRunner.print_output("ReplaceOrderRs\t" + new_order_response);
        } else
            TCRunner.print_output("ReplaceOrderRs\tRejected");
    }

    public void new_order_request(Order order){
        TCRunner.method_called(new Throwable());
        new_request("New", order);
        String response = add_order(order);
        TCRunner.print_output("NewOrderRs\t" + response);
    }

    public int get_total_traded_qty() {
        TCRunner.method_called(new Throwable());
        int output = 0;
        for(Trade trade : trades)
            output += trade.quantity;
        return output;
    }

    public String add_order(Order order){
        TCRunner.method_called(new Throwable());
        if(order.has_valid_attrs() && environment.validate_order_price_limit(order)
                && environment.validate_order_quantity_limit(order)
                && order.shareholder_id.ownership_validation(order)){
            // trade order
            match(order);
            int total_traded_qty = get_total_traded_qty();
            if(total_traded_qty < order.min_qty || (order.fill_and_kill && total_traded_qty == 0)) {
                // eliminate order
                rollback_by_trades();
                return "Eliminated";
            }
            else if(order.broker_id.credit_validation(order)||(order.fill_and_kill && order.broker_id.free_credit >= 0)) {
                // accept order
                if (order.quantity > 0 && !order.fill_and_kill)
                    order_book.add_order(order);
                order_book.remove_empty_orders();
                return "Accepted";
            }
            else
                rollback_by_trades();
        }
        return "Rejected";
    }

    public void match(Order new_order) {
        TCRunner.method_called(new Throwable());
        Order buy_order = (new_order.is_buy)? new_order:order_book.get_first_buy_order();
        Order sell_order = (!new_order.is_buy)? new_order:order_book.get_first_sell_order();
        Order old_order = (buy_order != new_order)? buy_order:sell_order;
        if(sell_order != null && buy_order != null) {
            if (sell_order.price <= buy_order.price) {
                int trade_qty = Math.min(new_order.quantity, old_order.get_maximum_quantity_to_trade());
                if(trade_qty > 0) {
                    if(old_order.disclosed_quantity == trade_qty) {
                        // iceberg order finished disclosed quantity.
                        order_book.remove_order(old_order);
                        order_book.add_order(old_order);
                    }
                    trades.add(new Trade(trade_qty, buy_order, sell_order));
                    match(new_order);
                }
            }
        }
    }

    public void rollback_by_trades() {
        TCRunner.method_called(new Throwable());
        while(trades.size() > 0) {
            Trade last_trade = trades.get(trades.size()-1);
            last_trade.rollback_trade();
            order_book.rollback_order_book(last_trade);
            trades.remove(last_trade);
        }
    }

    @Override
    public String toString() {
        TCRunner.method_called(new Throwable());
        String output = (Objects.equals(last_request_type, "Cancel"))? "":print_trades();
        output += "\n" + order_book.toString();
        output += Broker.print_credits();
        output += Shareholder.print_ownerships();
        output += "\n" + environment.toString();
        return output;
    }

    public String print_trades() {
        TCRunner.method_called(new Throwable());
        StringBuilder output = new StringBuilder("\n\tTrades\t" + trades.size());
        for(Trade trade : trades)
            output.append(trade);
        return output.toString();
    }
}
