public class OrderInput {
    int order_id;
    boolean is_buy;
    OrderResult order_result;

    public OrderInput() {
        setOrderId();
        setIsBuy();
    }

    String getHaskellBooleanFormat(boolean x) {
        String xs = String.valueOf(x);
        return xs.substring(0, 1).toUpperCase() + xs.substring(1);
    }

    private void setIsBuy() {
        is_buy = MyRandom.getBoolean(48);
    }

    private void setOrderId() {
        order_id = MyRandom.getInt(1, Settings.order_number);
    }

    public String getHaskellCommand() {
        return null;
    }

    public void set_order_result(String previous_order_res, String new_order_res) {
        this.order_result = new OrderResult(previous_order_res, new_order_res);
    }
}
