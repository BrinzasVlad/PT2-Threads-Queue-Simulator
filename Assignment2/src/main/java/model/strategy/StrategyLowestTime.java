package model.strategy;

import java.util.List;

import model.Customer;
import model.CustomerServer;

public class StrategyLowestTime implements Strategy {

	/**
	 * Assigns the given Customer to the CustomerServer with the lowest
	 * waiting time from the given list. This guarantees that each client
	 * will be served in the lowest possible time.
	 * @param cust - the Customer to be added to a queue
	 * @param queues - the CustomerServer queues to which to add the Customer
	 */
	@Override
	public void dispatchTask(Customer cust, List<CustomerServer> queues) {
		// Find the CustomerServer with the lowest waiting time
		CustomerServer target = queues.get(0);
		for(CustomerServer server : queues) {
			if(server.getWaitingTime() < target.getWaitingTime()
					&& server.getRemainingCapacity() > 0) target = server;
		}
		
		// Assign the Customer there
		target.addCustomer(cust);
	}

}
