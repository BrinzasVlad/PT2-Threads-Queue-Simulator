package model.strategy;

import java.util.List;

import model.Customer;
import model.CustomerServer;

public interface Strategy {
	
	public enum Policy {
		/**
		 * Customers are assigned to the queue with the fewest people
		 */
		SHORTEST_QUEUE,
		/**
		 * Customers are assigned to the queue with the shortest waiting time
		 */
		SHORTEST_TIME
	}
	
	/**
	 * Assigns the given Customer to one of the CustomerServers given.
	 * The exact CustomerServer chosen depends on the implementation.
	 * @param cust - the Customer to be added to a queue
	 * @param queues - the CustomerServer queues to which to add the Customer
	 * @see StrategyLowestNumber#dispatchTask(Customer, List)
	 * @see StrategyLowestTime#dispatchTask(Customer, List)
	 */
	public void dispatchTask(Customer cust, List<CustomerServer> queues);
}
