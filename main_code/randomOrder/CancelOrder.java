package randomOrder;

public class CancelOrder extends OrderInput {
    @Override
    public String getHaskellCommand() {
        return String.format("CancelOrderRq\t%d\t%s",
                order_id, getHaskellBooleanFormat(is_buy));
    }
}
