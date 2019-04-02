package model.strategy;

import java.util.List;

import model.Customer;
import model.CustomerServer;

public class StrategyLowestNumber implements Strategy {

	/**
	 * Assigns the given Customer to the CustomerServer with the lowest number
	 * of clients in the given list. This is a more natural representation of
	 * customer behaviour, who often judge based on "rules of thumb" such as
	 * the number of people in each queue.
	 * @param cust - the Customer to be added to a queue
	 * @param queues - the CustomerServer queues to which to add the Customer
	 */
	@Override
	public void dispatchCustomer(Customer cust, List<CustomerServer> queues) {
		// Find the CustomerServer with the lowest number of clients
		CustomerServer target = queues.get(0);
		for(CustomerServer server : queues) {
			if(server.getNoOfClients() < target.getNoOfClients()
					&& server.getRemainingCapacity() > 0) target = server;
		}
		
		// Assign the Customer there
		target.addCustomer(cust);
	}

}
