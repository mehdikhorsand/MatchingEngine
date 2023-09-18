package source;
// total branches : 12
// never covered with valid orders : 0

public class Environment {
    float ownership_upper_limit;
    int total_shares;
    float static_price_band_upper_limit;
    float static_price_band_lower_limit;
    int reference_price;
    int lot_size;
    int tick_size;

    @Override
    public String toString() {
        TCRunner.method_called();
        return "\tReferencePrice\t" + reference_price +
                "\n\tStaticPriceBandLowerLimit\t" + static_price_band_lower_limit +
                "\n\tStaticPriceBandUpperLimit\t" + static_price_band_upper_limit +
                "\n\tTotalShares\t" + total_shares +
                "\n\tOwnershipUpperLimit\t" + ownership_upper_limit +
                "\n\tTickSize\t" + tick_size +
                "\n\tLotSize\t" + lot_size;
    }

    public void set_tick_size_rq(int tick_size) {
        TCRunner.method_called();
        this.tick_size = tick_size;
    }

    public void set_lot_size_rq(int lot_size) {
        TCRunner.method_called();
        this.lot_size = lot_size;
    }

    public void set_reference_price_rq(int reference_price) {
        TCRunner.method_called();
        this.reference_price = reference_price;
    }

    public void set_static_price_band_lower_limit_rq(float static_price_band_lower_limit) {
        TCRunner.method_called();
        this.static_price_band_lower_limit = static_price_band_lower_limit;
    }

    public void set_static_price_band_upper_limit_rq(float static_price_band_upper_limit) {
        TCRunner.method_called();
        this.static_price_band_upper_limit = static_price_band_upper_limit;
    }

    public void set_total_shares_rq(int total_shares) {
        TCRunner.method_called();
        this.total_shares = total_shares;
    }

    public void set_ownership_upper_limit_rq(float ownership_upper_limit) {
        TCRunner.method_called();
        this.ownership_upper_limit = ownership_upper_limit;
    }

    public boolean validate_order_price_limit(Order order) {
        TCRunner.method_called();
        if(order.price % tick_size == 0) {
            TCRunner.condition_covered();
            int lower_price_limit = reference_price - (int)(reference_price * static_price_band_lower_limit);
            int upper_price_limit = reference_price + (int)(reference_price * static_price_band_upper_limit);
            return lower_price_limit <= order.price && order.price <= upper_price_limit;
        }
        else {
            TCRunner.condition_uncovered();
            return false;
        }
    }

    public boolean validate_order_quantity_limit(Order order) {
        TCRunner.method_called();
        if(order.quantity % lot_size == 0) {
            TCRunner.condition_covered();
            if (order.is_buy) {
                TCRunner.condition_covered();
                int owned_qty = order.shareholder_id.ownership;
                int booked_orders_qty = order.shareholder_id.booked_buy_orders_qty;
                int max_ownership = (int) (total_shares * ownership_upper_limit);
                return owned_qty + order.quantity + booked_orders_qty < max_ownership;
            }
            else {
                TCRunner.condition_uncovered();
                return true;
            }
        }
        else {
            TCRunner.condition_uncovered();
            return false;
        }
    }
}
