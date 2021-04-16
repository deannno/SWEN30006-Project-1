package automail;

public class Charge {
	public static double activity_unit_price = 0.224;
	public static double markup_percentage = 0.059;
	private int num_lookups;
	private int extra_cost;
	private double activity_cost;
	private double service_fee;
	
	public Charge() {
		this.num_lookups = 0;
	}
	
	public double getServiceFee() {
		return service_fee;
	}
	
	public double getActivityCost() {
		return activity_cost;
	}
	
	public double caculateActivityUnits(int floors_moved) {
		return (5 * floors_moved + 0.1 * num_lookups);
	}
	
	public double calculateCharge(int floors_moved, int destination_floor) {
		double bill_charged;
		activity_cost = activity_unit_price * caculateActivityUnits(floors_moved);
		service_fee = lookupServiceFee(destination_floor);
		bill_charged = (service_fee + activity_cost) * (1 + markup_percentage);
		return bill_charged;
	}
	
	public double lookupServiceFee(int floor) {
		num_lookups++;
		return WifiModemQuerier.getInstance().lookupServiceFee(floor);
	}
	
}
