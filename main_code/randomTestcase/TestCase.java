package randomTestcase;
import randomOrder.CancelOrder;
import randomOrder.NewOrder;
import randomOrder.OrderInput;
import randomOrder.ReplaceOrder;
import tools.MyRandom;
import main.Settings;
import tools.TCWriter;
import tools.Terminal;

import java.util.ArrayList;

public class TestCase {
    public int tickSize;
    public int lotSize;
    public int referencePrice;
    public float staticPriceBoundLowerLimit;
    public float staticPriceBoundUpperLimit;
    public int totalShares;
    public float ownerShipUpperLimit;
    public int[] credits;
    public int[] ownerShips;
    public ArrayList<OrderInput> orders = new ArrayList<>();
    public TestCase() {
        tickSize = get_random_tick_lot_size();
        lotSize = get_random_tick_lot_size();
        referencePrice = 5;
        staticPriceBoundLowerLimit = 0.6F;
        staticPriceBoundUpperLimit = 0.8F;
        totalShares = 100;
        ownerShipUpperLimit = 0.2F;
        ownerShips = MyRandom.generate_random_number(Settings.shareholder_number, MyRandom.getInt(0,5), MyRandom.getInt(15, 20));
        credits = MyRandom.generate_random_number(Settings.broker_number, MyRandom.getInt(0, 20), MyRandom.getInt(80, 100));
        generate_random_orders();
        set_execution_result();
    }

    private int get_random_tick_lot_size() {
        if(MyRandom.getBoolean(10))
            return 2;
        else
            return 1;
    }

    private void generate_random_orders() {
        while (!finish_creating_new_orders()) {
            orders.add(new NewOrder());
            if((orders.size()+1) % 10 == 0) {
                orders.add(new ReplaceOrder());
                orders.get(orders.size()-1).order_id = orders.size()-1;
                ((NewOrder)orders.get(orders.size()-1)).shareholder_id = ((NewOrder)orders.get(orders.size()-2)).shareholder_id;
                ((NewOrder)orders.get(orders.size()-1)).broker_id = ((NewOrder)orders.get(orders.size()-2)).broker_id;
                orders.get(orders.size()-1).is_buy = orders.get(orders.size()-2).is_buy;
            }
            if((orders.size()+6) % 10 == 0) {
                orders.add(new CancelOrder());
                orders.get(orders.size()-1).order_id = orders.size()-1;
                orders.get(orders.size()-1).is_buy = orders.get(orders.size()-2).is_buy;
            }
        }
    }

    private boolean finish_creating_new_orders() {
        return orders.size() >= Settings.order_number;
    }

    public String run_this(){
        String testcase_path = Settings.temp + Settings.test_file_name + Settings.testcase_format;
        TCWriter.write_into_txt_format(testcase_path, this);
        String tc_result = Terminal.run_oracle(testcase_path);
        Terminal.rm(testcase_path);
        return tc_result;
    }

    public void set_execution_result(){
        String tc_result = run_this();
        set_TC_orders_result(tc_result);
    }

    private void set_TC_orders_result(String tcResult) {
        String[] orders_output = tcResult.split("OrderRs\t");
        orders_output[0] = "";
        for(int i=0; i<orders.size(); i++)
            orders.get(i).set_order_result(orders_output[i], orders_output[i+1]);
    }
}
