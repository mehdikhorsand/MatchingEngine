package methods.ART_ISP_hs;

import randomOrder.NewOrder;
import randomOrder.OrderInput;

import java.util.Objects;

public class OrderResult {
//    this information of order is important in computing isp coverage in multiple characteristics.
//    and because of that we save the information.
    String new_order_book;
    String previous_order_book;
    String new_trades_list;
    boolean is_new_order_added_to_the_order_book;
    String new_order_status;

    public OrderResult(String previous_order_res, String new_order_res) {
        new_order_book = get_order_book(new_order_res);
        previous_order_book = get_order_book(previous_order_res);
        new_trades_list = get_trades_list(new_order_res);
        is_new_order_added_to_the_order_book = is_new_order_added_to_the_order_book();
        new_order_status = get_order_status(new_order_res);
    }

    private String get_order_status(String order_res) {
        return order_res.trim().split("\n")[0];
    }

    private String get_trades_list(String order_res) {
        return get_string_between(order_res, "Trades", "Orders");
    }

    private String get_order_book(String order_res) {
        return get_string_between(order_res, "Orders", "Credits");
    }

    private String get_string_between(String input, String start, String end) {
        if(Objects.equals(input, ""))
            return "";
        String[] order_res_array = input.split(start);
        if(order_res_array.length < 2) // order got rejected or eliminated.
            return "";
        if(order_res_array[1].trim().equals("0")) // order_book is empty.
            return "";
        return order_res_array[1].split(end)[0];
    }

    public int get_new_trades_quantity() {
        return get_first_number(new_trades_list);
    }

    public int get_new_order_book_size() {
        return get_first_number(new_order_book);
    }

    public int get_previous_order_book_size() {
        return get_first_number(previous_order_book);
    }

    public int[] get_new_order_ids_in_order_book() {
        return get_orders_parameter_int(new_order_book, 1);
    }

    public int[] get_new_order_quantities_in_order_book() {
        return get_orders_parameter_int(new_order_book, 5);
    }

    public int[] get_previous_order_quantities_in_order_book() {
        return get_orders_parameter_int(new_order_book, 5);
    }

    public boolean has_all_new_orders_quantity_with_type_x_been_changed(boolean is_buy) {
        int[] new_order_quantities = get_new_order_quantities_in_order_book();
        int[] previous_order_quantities = get_previous_order_quantities_in_order_book();
        int[] new_order_ids = get_new_order_ids_in_order_book();
        int[] previous_order_ids = get_new_order_ids_in_order_book();
        String[] new_order_types = get_new_order_types_in_order_book();
        for(int i=0; i<new_order_ids.length; i++)
            if((Objects.equals(new_order_types[i], "BUY") && is_buy) ||
                    (Objects.equals(new_order_types[i], "SELL") && !is_buy))
                for(int j=0; j<previous_order_ids.length; j++)
                    if(new_order_ids[i] == previous_order_ids[j]) // same order
                        if(new_order_quantities[i] == previous_order_quantities[j]) // same quantity
                            return false; // there is at least one order with same quantity.
        return true;
    }

    public String[] get_new_order_types_in_order_book() {
        return get_orders_parameter_string(new_order_book, 6);
    }

    public String[] get_previous_order_types_in_order_book() {
        return get_orders_parameter_string(previous_order_book, 6);
    }

    public int[] get_previous_order_prices_in_order_book() {
        return get_orders_parameter_int(previous_order_book, 4);
    }

    public int[] get_previous_order_ids_in_order_book() {
        return get_orders_parameter_int(previous_order_book, 1);
    }

    private int[] get_orders_parameter_int(String order_book, int index) {
        int[] res = new int[get_first_number(order_book)];
        String[] parameters = get_orders_parameter_string(order_book, index);
        for (int i=0; i<parameters.length; i++)
            res[i] = Integer.parseInt(parameters[i]);
        return res;
    }

    private String[] get_orders_parameter_string(String order_book, int index) {
        String[] order_book_array = order_book.split("Order\t");
        String[] res = new String[get_first_number(order_book_array[0])];
        for (int i=1; i<order_book_array.length; i++)
            res[i - 1] = order_book_array[i].split("\t")[index].trim();
        return res;
    }

    private int get_first_number(String input) {
        if (Objects.equals(input, ""))
            return 0;
        else
            try{
                return Integer.parseInt(input.trim().split("\n")[0].split("\t")[0]);
            } catch (StringIndexOutOfBoundsException error) {
                return Integer.parseInt(input.trim());
            }
    }

    private boolean is_new_order_added_to_the_order_book() {
        return !x_subset_y(get_new_order_ids_in_order_book(), get_previous_order_ids_in_order_book());
    }

    private boolean x_subset_y(int[] x_array, int[] y_array) {
        if(x_array.length > y_array.length)
            return false;
        boolean res;
        for (int x : x_array) {
            res = false;
            for (int y : y_array)
                if (x == y) {
                    res = true;
                    break;
                }
            if (!res)
                return false;
        }
        return true;
    }

    public int get_order_book_size_difference() {
        return get_new_order_book_size() - get_previous_order_book_size();
    }

    public int[] get_previous_order_quantities_to_trade() {
        int[] previous_order_quantities = get_previous_order_quantities_in_order_book();
        int[] previous_order_peak_sizes = get_previous_order_peak_sizes_in_order_book();
        for(int i=0; i<previous_order_quantities.length; i++) {
            if (previous_order_peak_sizes[i] > 0)
                previous_order_quantities[i] = Math.min(previous_order_peak_sizes[i], previous_order_quantities[i]);
            else
                previous_order_quantities[i] = previous_order_quantities[i];
        }
        return previous_order_quantities;
    }

    public boolean is_x_the_min_qty_order_rejection_reason(OrderInput order, String reason) {
        if (((NewOrder) order).min_qty > 0){
            int[] previous_order_prices = get_previous_order_prices_in_order_book();
            int[] previous_order_quantities_to_trade = get_previous_order_quantities_to_trade();
            String[] previous_order_types = get_previous_order_types_in_order_book();
            int order_price = ((NewOrder) order).price;
            int order_min_qty = ((NewOrder) order).min_qty;
            String type_filter, criterion;
            if(order.is_buy){
                type_filter = "SELL";
                criterion = "LT";
            } else {
                type_filter = "Buy";
                criterion = "GT";
            }
            if (Objects.equals(reason, "min_qty"))
                return array_check(previous_order_quantities_to_trade, previous_order_types, type_filter, "GT", order_min_qty);
            if (Objects.equals(reason, "price"))
                return array_check(previous_order_prices, previous_order_types, type_filter, criterion, order_price);
        }
        return false;
    }

    private int[] get_previous_order_peak_sizes_in_order_book() {
        return get_orders_parameter_int(previous_order_book, 9);
    }

    private boolean array_check(int[] order_x, String[] order_type, String type_filter, String criterion, int c) {
        for (int i=0; i<order_x.length; i++)
            if (Objects.equals(order_type[i], type_filter))
                if ((c >= order_x[i] && Objects.equals(criterion, "LT")) ||
                        (c <= order_x[i] && Objects.equals(criterion, "GT")))
                    return false;
        return true;
    }

    public int new_order_remained_quantity() {
        int[] new_order_ids = get_new_order_ids_in_order_book();
        int[] previous_order_ids = get_previous_order_ids_in_order_book();
        int[] new_order_quantities = get_new_order_quantities_in_order_book();
        for (int i=0; i<new_order_ids.length; i++) {
            boolean new_order_founded = true;
            for (int p_order : previous_order_ids) {
                if (new_order_ids[i] == p_order) { // not the order that i want;
                    new_order_founded = false;
                    break;
                }
            }
            if(new_order_founded)
                return new_order_quantities[i];
        }
        return 0;
    }

    public boolean is_there_x_order_in_previous_order_book(boolean is_buy) {
        if(is_buy)
            return is_there_x_order_in_order_book("BUY", previous_order_book);
        else
            return is_there_x_order_in_order_book("SELL", previous_order_book);
    }

    public boolean is_there_x_order_in_new_order_book(boolean is_buy) {
        if(is_buy)
            return is_there_x_order_in_order_book("BUY", new_order_book);
        else
            return is_there_x_order_in_order_book("SELL", new_order_book);
    }

    private boolean is_there_x_order_in_order_book(String type, String order_book) {
        return order_book.trim().split(type).length > 1;
    }
}
