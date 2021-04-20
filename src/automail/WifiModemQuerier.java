package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;

import simulation.Building;

public class WifiModemQuerier {
    private static WifiModemQuerier SINGLE_INSTANCE;
    private int success_lookups;
    private int failed_lookups;
    private double[] latest_service_fees;
    private WifiModem wifi_modem;

    public static WifiModemQuerier getInstance() throws Exception {
        if (SINGLE_INSTANCE == null) {
            return getInstance(Building.MAILROOM_LOCATION);
        }
        return SINGLE_INSTANCE;
    }

    public static WifiModemQuerier getInstance(int installedFloor) throws Exception {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new WifiModemQuerier();
            SINGLE_INSTANCE.wifi_modem = WifiModem.getInstance(installedFloor);
            SINGLE_INSTANCE.instantiateServiceFees(SINGLE_INSTANCE.latest_service_fees);
        }
        return SINGLE_INSTANCE;
    }

    /**
     *
     * @param wifi_modem
     */
    private WifiModemQuerier() {
        success_lookups = 0;
        failed_lookups = 0;
        latest_service_fees = new double[Building.FLOORS];
    }

    /**
     * At the start of the program, we want to store the service fees for each floor
     *
     * @param service_fees the array of doubles to be filled with fees, where
     *                     service_fees[i] = the service fee for floor i +
     *                     building's first floor
     */
    private void instantiateServiceFees(double[] service_fees) {
        for (int i = 0; i < service_fees.length; i++) {
            service_fees[i] = 0;
        }
        for (int i = 0; i < service_fees.length; i++) {
            service_fees[i] = lookupServiceFee(i + Building.LOWEST_FLOOR);
        }
        success_lookups = 0;
        failed_lookups = 0;
    }

    /**
     * Make a lookup call for the service fee of floor
     *
     * @param floor
     * @return if failed return the latest known service fees, if succeed return the
     *         value returned by wifi_modem
     */
    public double lookupServiceFee(int floor) {
        double fee = wifi_modem.forwardCallToAPI_LookupPrice(floor);

        // lookups fail when the value returned is negative
        if (fee >= 0) {
            success_lookups++;
            updateFloorFee(floor, fee);
            return fee;
        } else {
            failed_lookups++;
            return latest_service_fees[floor - Building.LOWEST_FLOOR];
        }
    }

    /**
     *
     * @param floor
     * @param newFee
     */
    private void updateFloorFee(int floor, double newFee) {
        latest_service_fees[floor - Building.LOWEST_FLOOR] = newFee;
    }

    public int getFailed_lookups() {
        return failed_lookups;
    }

    public int getSuccess_lookups() {
        return success_lookups;
    }

    public String TurnOn() throws Exception {
        return wifi_modem.Turnon();
    }

    public String TurnOff() {
        return wifi_modem.Turnoff();
    }
}
