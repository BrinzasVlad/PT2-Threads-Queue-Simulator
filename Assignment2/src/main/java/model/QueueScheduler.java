package model;

import java.util.ArrayList;
import java.util.List;

import model.strategy.Strategy;
import model.strategy.Strategy.Policy;
import model.strategy.StrategyLowestNumber;
import model.strategy.StrategyLowestTime;

public class QueueScheduler {
	
	private final String[] names = {
			"Bill",
			"John",
			"Vance",
			"Emma",
			"Jill",
			"Buck",
			"Bob",
			"Jimmy",
			"Ashe",
			"Irma",
			"Mira"
	};

	// Unused fields, kept because they may be useful in the future
	private int maxNoQueues;
	private int maxCustomersPerQueue;
	
	private List<CustomerServer> queues;
	
	private Strategy strategy;
	
	/**
	 * Creates a new QueueScheduler object with the given maximum number of
	 * queues and maximum number of customers per queue. This constructor
	 * defaults to a {@link Policy#SHORTEST_QUEUE} strategy.
	 * @param simulationTime - maximum time to run the simulation for
	 * @param maxNoQueues - maximum number of queues that can be active
	 * @param maxCustomersPerQueue - maximum number of customers for each queue
	 */
	public QueueScheduler(int simulationTime, int maxNoQueues, int maxCustomersPerQueue) {
		// Initialise attributes
		this.maxNoQueues = maxNoQueues;
		this.maxCustomersPerQueue = maxCustomersPerQueue;
		this.strategy = new StrategyLowestNumber();
		
		// Initialise CustomerServers
		queues = new ArrayList<CustomerServer>(maxNoQueues);
		for(int i = 0; i < maxNoQueues; ++i) {
			CustomerServer server = new CustomerServer(names[i % names.length], maxCustomersPerQueue);
			
			queues.add(server);
		}
	}
	
	/**
	 * Sets the strategy for assigning Customers to queues based on the given
	 * policy.
	 * @param policy - the policy to follow for assignments
	 * @see Policy#SHORTEST_QUEUE
	 * @see Policy#SHORTEST_TIME
	 */
	public void setStrategy(Policy policy) {
		switch(policy) {
			case SHORTEST_QUEUE:
				this.strategy = new StrategyLowestNumber();
				break;
			case SHORTEST_TIME:
				this.strategy = new StrategyLowestTime();
				break;
		}
	}
	
	/**
	 * Assigns the given Customer to one of the CustomerServers. The exact
	 * CustomerServer depends on the chosen strategy, as set with {@link #setStrategy(Policy)}.
	 * @param cust - the Customer to be assigned to a queue
	 */
	public void dispatchCustomer(Customer cust) {
		strategy.dispatchTask(cust, queues);
	}
	
	/**
	 * Starts the simulation as per the conditions set by {@link #QueueScheduler(int, int, int)}
	 * and returns a list of Threads containing the threads that were generated.
	 * @return a list of Threads containing one thread for each queue generated
	 */
	public List<Thread> start() {
		List<Thread> threads = new ArrayList<Thread>();
		
		for (CustomerServer server : queues) {
			Thread t = new Thread(server);
			threads.add(t);
			t.start();
		}
		
		return threads;
	}
	
	/**
	 * Returns a text description for each CustomerServer managed by
	 * this QueueScheduler.
	 * @return a list of String descriptions
	 * @see CustomerServer#toString()
	 */
	public List<String> getQueueDescriptions() {
		List<String> descriptions = new ArrayList<String>(maxNoQueues);
		
		for(CustomerServer ser : queues) {
			descriptions.add(ser.toString());
		}
		
		return descriptions;
	}
	
	/**
	 * Marks the simulation as over, causing all the CustomerServer objects
	 * associated with this QueueScheduler to finish their current duties then
	 * terminate.
	 * @see CustomerServer#endSimulation()
	 */
	public void endSimulation() {
		for(CustomerServer s : queues) {
			s.endSimulation();
		}
	}
}