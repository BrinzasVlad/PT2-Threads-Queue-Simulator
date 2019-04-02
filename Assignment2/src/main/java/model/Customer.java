package model;

public class Customer {
	
	private int arrivalTime;
	private int servingTime;
	private int finishTime;
	
	/**
	 * Generates a new Customer
	 * @param arrivalTime - time in the simulation (in seconds) when the customer arrives and is placed in queue
	 * @param servingTime - time it takes during the simulation (in seconds) for the customer to be served
	 */
	public Customer(int arrivalTime, int servingTime) {
		this.arrivalTime = arrivalTime;
		this.servingTime = servingTime;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getServingTime() {
		return servingTime;
	}

	public void setServingTime(int servingTime) {
		this.servingTime = servingTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
	
	/**
	 * Computes the finish time for this Customer, given the time it had to wait in queue
	 * @param waitingTime - the time the Customer waited before s/he started being served
	 */
	public void computeFinishTime(int waitingTime) {
		this.finishTime = arrivalTime + waitingTime + servingTime;
	}

	/**
	 * Returns the arrival and servicing time for this Customer, in the 
	 * (arrival time, serving time) format. E.g. for a Customer with arrival
	 * time 7 and serving time 2, it will return "(7, 2)".
	 */
	@Override
	public String toString() {
		return "(" + arrivalTime + ", " + servingTime + ")";
	}
}
