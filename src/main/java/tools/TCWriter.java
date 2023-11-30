package tools;
import main.Settings;
import randomOrder.OrderInput;
import randomTestcase.TestCase;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class TCWriter {

    static PrintWriter writer;

    public static void write_into_txt_format(String file_name, TestCase tc) {
        try {
            writer = new PrintWriter(Settings.project_location + file_name, "UTF-8");
            write_SetTickSizeRq(tc);
            write_SetLotSizeRq(tc);
            write_SetReferencePriceRq(tc);
            write_SetStaticPriceBandLimitRq(tc);
            write_SetTotalSharesRq(tc);
            write_SetOwnershipUpperLimitRq(tc);
            write_SetCreditRqs(tc);
            write_SetOwnershipRq(tc);
            write_OrderRqs(tc);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void write_OrderRqs(TestCase tc) {
        for (OrderInput order : tc.orders)
            writer.println(order.getHaskellCommand());
    }

    private static void write_SetOwnershipRq(TestCase tc) {
        for (int i=1; i<=tc.ownerShips.length; i++)
            writer.println("SetOwnershipRq\t" + i + "\t" + tc.credits[i-1]);
    }

    private static void write_SetCreditRqs(TestCase tc) {
        for (int i=1; i<=tc.credits.length; i++)
            writer.println("SetCreditRq\t" + i + "\t" + tc.ownerShips[i-1]);
    }

    private static void write_SetOwnershipUpperLimitRq(TestCase tc) {
        writer.println("SetOwnershipUpperLimitRq\t" + tc.ownerShipUpperLimit);
    }

    private static void write_SetTotalSharesRq(TestCase tc) {
        writer.println("SetTotalSharesRq\t" + tc.totalShares);
    }

    private static void write_SetStaticPriceBandLimitRq(TestCase tc) {
        writer.println("SetStaticPriceBandLowerLimitRq\t" + tc.staticPriceBoundLowerLimit);
        writer.println("SetStaticPriceBandUpperLimitRq\t" + tc.staticPriceBoundUpperLimit);
    }

    private static void write_SetReferencePriceRq(TestCase tc) {
        writer.println("SetReferencePriceRq\t" + tc.referencePrice);
    }

    private static void write_SetLotSizeRq(TestCase tc) {
        writer.println("SetLotSizeRq\t" + tc.lotSize);
    }

    public static void write_SetTickSizeRq(TestCase tc) {
        writer.println("SetTickSizeRq\t" + tc.tickSize);
    }
}
