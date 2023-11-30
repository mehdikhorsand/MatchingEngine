package randomOrder;

import main.Settings;
import tools.MyRandom;

public class NewOrder extends OrderInput {
    public int price;
    public int quantity;
    public int peak_size;
    public int min_qty;
    public int shareholder_id;
    public int broker_id;
    boolean fill_and_kill; // don't add order to the order book. just trade as much as possible and remove the remaining order.

    public NewOrder() {
        setQuantity();
        setFillAndKill();
        setPrice();
        setPeakSize();      // if it's an iceberg order
        setMinQty();        // > 0 && < peak_size if peak_size > 0 else quantity
        setShareholderId();
        setBrokerId();
    }

    private void setFillAndKill() {
        fill_and_kill = MyRandom.getBoolean(25);
    }

    private void setBrokerId() {
        broker_id = MyRandom.getInt(1, Settings.broker_number);
    }

    private void setShareholderId() {
        shareholder_id = MyRandom.getInt(1, Settings.shareholder_number);
    }

    private void setMinQty() {
//        min_qty = 0;
        if(MyRandom.getBoolean() && !fill_and_kill) { // order should have min_qty > 0.
            if (peak_size > 0)
                min_qty = MyRandom.getInt(1, peak_size);
            else
                min_qty = MyRandom.getInt(1, quantity);
        }
        else
            min_qty = 0;
    }

    private void setPeakSize() {
        if (MyRandom.getBoolean() && !fill_and_kill)
            peak_size = MyRandom.getInt(1, quantity);
        else
            peak_size = 0;
    }

    private void setPrice() {
        if(MyRandom.getBoolean(5))
            price = MyRandom.getInt(1, Settings.max_order_price);
        else
            price = MyRandom.getInt(2, Settings.max_order_price - 1);
    }

    private void setQuantity() {
        quantity = MyRandom.getInt(1, Settings.max_order_quantity);
    }

    public String getOrderParameters() {
        return String.format("\t%d\t%d\t%d\t%d\t%s\t%d\t%s\t%d",
                broker_id, shareholder_id, price, quantity, getHaskellBooleanFormat(is_buy),
                min_qty, getHaskellBooleanFormat(fill_and_kill), peak_size);
    }

    @Override
    public String getHaskellCommand() {
        return "NewOrderRq" + getOrderParameters();
    }
}
