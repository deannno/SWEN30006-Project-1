package automail;

public class Charge {
	public static double activity_unit_price = 0.224;
	public static double markup_percentage = 0.059;
	private int num_lookups;
	private double extra_cost = 0.0;
	private double activity_units;
	private double activity_cost;
	private double service_fee;
	private double total_charge;
	
	public Charge() {
		this.num_lookups = 0;
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
		if (num_lookups > 0)  {
			lookups_charged = 1;
		}
		else {
			lookups_charged = 0;
		}
		return (5 * floors_moved + 0.1 * lookups_charged);
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
		double rounded = Math.round(number * 100.0) / 100.0;
		return rounded;
	}
	
}
