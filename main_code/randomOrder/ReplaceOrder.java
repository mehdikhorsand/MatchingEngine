package randomOrder;

public class ReplaceOrder extends NewOrder {

    public ReplaceOrder() {
        super();
    }

    @Override
    public String getOrderParameters() {
        return String.format("\t%d%s",order_id, super.getOrderParameters());
    }

    @Override
    public String getHaskellCommand() {
        return "ReplaceOrderRq" + getOrderParameters();
    }
}
