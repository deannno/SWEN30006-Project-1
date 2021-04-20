package automail;

public class Charge {
	private static final double LOOKUP_ACTIVITY_UNIT = 0.1;
	private static final double MOVE_ACTIVITY_UNIT = 5.0;
	
	private static double activity_unit_price;
	private static double markup_percentage;
	private int num_lookups;
	private double extra_cost = 0.0;
	private double activity_units;
	private double activity_cost;
	private double service_fee;
	private double total_charge;

	public Charge() {
		this.num_lookups = 0;
	}

	public static void setActivityUnitPrice(double newPrice) {
		activity_unit_price = newPrice;
	}

	public static void setMarkupPercentage(double newPercentage) {
		markup_percentage = newPercentage;
	}

	public double getActivityUnits() {
		return activity_units;
	}

	public double getServiceFee() {
		return service_fee;
	}

	public double getActivityCost() {
		return activity_cost;
	}

	public double getTotalCharge() {
		return total_charge;
	}

	public double calculateActivityUnits(int floors_moved) {
		int lookups_charged;
		if (num_lookups > 0) {
			lookups_charged = 1;
		} else {
			lookups_charged = 0;
		}
		return (MOVE_ACTIVITY_UNIT * floors_moved + LOOKUP_ACTIVITY_UNIT * lookups_charged);
	}

	public void calculateCharge(int floors_moved, int destination_floor) {
		activity_units = calculateActivityUnits(floors_moved);
		activity_cost = roundTwoDecimalPlaces(activity_unit_price * activity_units);
		service_fee = roundTwoDecimalPlaces(lookupServiceFee(destination_floor));
		total_charge = roundTwoDecimalPlaces((service_fee + activity_cost + extra_cost) * (1 + markup_percentage));
	}

	public double lookupServiceFee(int floor) {
		num_lookups++;
		return WifiModemQuerier.getInstance().lookupServiceFee(floor);
	}

	private double roundTwoDecimalPlaces(double number) {
		return Math.round(number * 100.0) / 100.0;
	}

}
