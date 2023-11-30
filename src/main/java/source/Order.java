package source;


import java.util.concurrent.atomic.AtomicInteger;
// total branches : 32
// never covered with valid orders : 4


public class Order {
    static AtomicInteger counter = new AtomicInteger(0);
    int id;
    Broker broker_id;
    Shareholder shareholder_id;
    int price;
    int quantity;
    boolean is_buy;
    int min_qty;
    boolean fill_and_kill;
    int peak_size;
    boolean is_in_queue;
    int disclosed_quantity;
    int traded_qty_after_insertion;

    public Order(int broker_id, int shareholder_id, int price, int  quantity, boolean is_buy, int min_qty,
                 boolean fill_and_kill, int peak_size) {
        TCRunner.method_called();
        this.id = counter.incrementAndGet();
        this.broker_id = Broker.get_broker_by_id(broker_id);
        this.shareholder_id = Shareholder.get_shareholder_by_id(shareholder_id);
        this.price = price;
        this.quantity = quantity;
        this.is_buy = is_buy;
        this.min_qty = min_qty;
        this.fill_and_kill = fill_and_kill;
        this.peak_size = peak_size;
        this.is_in_queue = false;
        this.disclosed_quantity = 0;
        this.traded_qty_after_insertion = 0;
    }

    public static void reset_order_counter() {
        TCRunner.method_called();
        Order.counter = new AtomicInteger(0);
    }

    public static void new_cancel_order() {
        TCRunner.method_called();
        counter.incrementAndGet();
    }

    public int value() {
        TCRunner.method_called();
        return price * quantity;
    }

    public void order_added_to_queue() {
        TCRunner.method_called();
        set_disclosed_quantity();
        broker_id.added_new_order(this);
        shareholder_id.added_new_order(this);
        is_in_queue = true;
    }

    public void order_removed_from_queue() {
        TCRunner.method_called();
        broker_id.deleted_old_order(this);
        shareholder_id.deleted_old_order(this);
        is_in_queue = false;
    }

    private void set_disclosed_quantity() {
        TCRunner.method_called();
        if(peak_size > 0) {
            TCRunner.condition_covered();
            disclosed_quantity = Math.min(quantity, peak_size - (traded_qty_after_insertion % peak_size));
        }
    }

    public boolean has_valid_attrs() {
        TCRunner.method_called();
        boolean fak_validated = !fill_and_kill || (peak_size == 0 && min_qty == 0);
        boolean quantity_validated = peak_size <= quantity && min_qty <= quantity;
        return fak_validated && quantity_validated && broker_id != null && shareholder_id != null;
    }

    public int get_maximum_quantity_to_trade() {
        TCRunner.method_called();
        if(peak_size == 0) {
            TCRunner.condition_covered();
            return quantity;
        }
        else {
            TCRunner.condition_uncovered();
            return disclosed_quantity;
        }
    }

    public void update_order_quantities(Trade trade) {
        TCRunner.method_called();
        quantity -= trade.quantity;
        if(is_in_queue && peak_size > 0) {
            TCRunner.condition_covered();
            traded_qty_after_insertion += trade.quantity;
            set_disclosed_quantity();
        }
    }

    public void rollback_update_order_quantities(Trade trade) {
        TCRunner.method_called();
        quantity += trade.quantity;
        if(is_in_queue && peak_size > 0) {
            TCRunner.condition_covered();
            traded_qty_after_insertion -= trade.quantity;
            set_disclosed_quantity();
        }
    }

    @Override
    public String toString() {
        TCRunner.method_called();
        String type, fak, side;
        if(peak_size == 0) {
            TCRunner.condition_covered();
            type = "Limit";
        }
        else {
            TCRunner.condition_uncovered();
            type = "Iceberg";
        }
        if(is_buy){
            TCRunner.condition_covered();
            side = "BUY ";
        }
        else {
            TCRunner.condition_uncovered();
            side = "SELL";
        }
        if(fill_and_kill){
            TCRunner.condition_covered();
            fak = "FAK";
        }
        else {
            TCRunner.condition_uncovered();
            fak = "---";
        }
        return "\n\tOrder\t" + type + "\t" + id + "\t" + ((broker_id == null)? 0:broker_id.id) +
                "\t" + ((shareholder_id == null)? 0:shareholder_id.id) + "\t" + price + "\t" + quantity + "\t" + side +
                "\t" + min_qty + "\t" + fak + "\t" + disclosed_quantity;
    }
}
