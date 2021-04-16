package automail;

import java.util.Map;
import java.util.TreeMap;

// import java.util.UUID;

/**
 * Represents a mail item
 */
public class MailItem {
	
    /** Represents the destination floor to which the mail is intended to go */
    protected final int destination_floor;
    /** The mail identifier */
    protected final String id;
    /** The time the mail item arrived */
    protected final int arrival_time;
    /** The weight in grams of the mail item */
    protected final int weight;
    private int floors_moved;
    private Charge charge;


    /**
     * Constructor for a MailItem
     * @param dest_floor the destination floor intended for this mail item
     * @param arrival_time the time that the mail arrived
     * @param weight the weight of this mail item
     */
    public MailItem(int dest_floor, int arrival_time, int weight){
        this.destination_floor = dest_floor;
        this.id = String.valueOf(hashCode());
        this.arrival_time = arrival_time;
        this.weight = weight;
        this.floors_moved = 0;
        this.charge = new Charge();
        estimateCharge();

    }

    @Override
    public String toString(){
    	String.format("Charge: %2d | Cost: %2d | Fee: %2d | Activity: %2d", finaliseCharge(), charge.getActivityCost(), charge.getServiceFee(), charge.caculateActivityUnits(floors_moved));
        return String.format("Mail Item:: ID: %6s | Arrival: %4d | Destination: %2d | Weight: %4d", id, arrival_time, destination_floor, weight);
    }

    /**
     *
     * @return the destination floor of the mail item
     */
    public int getDestFloor() {
        return destination_floor;
    }
    
    /**
     *
     * @return the ID of the mail item
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return the arrival time of the mail item
     */
    public int getArrivalTime(){
        return arrival_time;
    }

    /**
    *
    * @return the weight of the mail item
    */
   public int getWeight(){
       return weight;
   }
   
   /**
    * increment the floors moved by the mail item
    */
   public void moveFloor() {
	   floors_moved++;
   }
   
   public double estimateCharge() {
	   int estimated_floors_moved = destination_floor - 1;
	   return charge.calculateCharge(estimated_floors_moved, destination_floor);
   }
   
   public double finaliseCharge() {
	   return charge.calculateCharge(floors_moved, destination_floor);
   }
   
   
	static private int count = 0;
	static private Map<Integer, Integer> hashMap = new TreeMap<Integer, Integer>();

	@Override
	public int hashCode() {
		Integer hash0 = super.hashCode();
		Integer hash = hashMap.get(hash0);
		if (hash == null) { hash = count++; hashMap.put(hash0, hash); }
		return hash;
	}


	public void finaliseCharge(){
	    return;
    }

	public double estimateCharge(){
	    return 0;
    }
}
