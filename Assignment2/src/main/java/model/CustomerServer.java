package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerServer implements Runnable {
	
	private String name;
	
	private BlockingQueue<Customer> queue;
	private AtomicInteger waitingTime;
	
	private boolean simulationOver = false;

	/**
	 * Creates a new CustomerServer with the given name, with an empty queue
	 * of the given capacity and a default waiting time of initially zero.
	 * @param name - the name of this CustomerServer, which will be displayed
	 * 				 in its {@link #toString()} method.
	 */
	public CustomerServer(String name, int maxTasks) {
		this.name = name;
		
		this.queue = new LinkedBlockingQueue<Customer>(maxTasks);
		this.waitingTime = new AtomicInteger(0);
	}

	/**
	 * Creates a new CustomerServer with the given name, using the given BlockingQueue
	 * to store Customers and with the given integer as initial starting time. The
	 * waiting time provided should match the contents of the given queue, else the
	 * waiting time will be inaccurate.
	 * @param name - the name of this CustomerServer, which will be displayed
	 * 				 in its {@link #toString()} method.
	 * @param queue - a BlockingQueue storing the initial Customers
	 * @param waitingTime - the initial waiting time associated to the given queue
	 */
	public CustomerServer(String name, BlockingQueue<Customer> queue, int waitingTime) {
		this.name = name;
		
		this.queue = queue;
		this.waitingTime = new AtomicInteger(waitingTime);
	}
	
	/**
	 * Adds the given Customer to the queue of this CustomerServer. This method
	 * automatically increments the waiting time for the queue.
	 * @param cust - the Customer to be added to queue
	 */
	public void addCustomer(Customer cust) { //TODO make sure we don't need "synchronised" here
		try {
			cust.computeFinishTime(waitingTime.get());
			queue.put(cust);
			
			waitingTime.addAndGet(cust.getServingTime());
			
		} catch (InterruptedException e) {
			System.out.println("Interrupted in addCustomer!");
			e.printStackTrace(); // TODO do something better than this, since printStackTrace() pretty much makes our output above obsolete anyway
		}
	}
	
	/**
	 * Marks the simulation as over, causing this CustomerServer to finish all of
	 * its current duties, then stop. (This usually means it will finish serving its
	 * current Customer before stopping.)
	 */
	public void endSimulation() {
		this.simulationOver = true;
	}
	
	/**
	 * Returns a string identifying this CustomerServer and its contained Customers.
	 * For example, for a CustomerServer named Maurice which has two Customers, it will
	 * return "Maurice: C1 C2", where C1 and C2 are the string representations of the
	 * Customers, as given by {@link Customer#toString()}.
	 */
	@Override
	public String toString() {
		String toReturn = name + ":";
		for(Customer cust : queue) {
			toReturn += " " + cust.toString();
		}
		
		return toReturn;
	}
	
	/**
	 * Returns the current waiting time for this CustomerServer.
	 * @return an integer representing the time it would take until a newly-added
	 * 		   Customer would start being served
	 */
	public int getWaitingTime() {
		return waitingTime.get();
	}
	
	/**
	 * Returns the current number of Customers waiting to be served
	 * for this CustomerServer.
	 * @return an integer representing the number of Customers in queue
	 */
	public int getNoOfClients() {
		return queue.size();
	}
	
	/**
	 * Return the number of Customers that could, in absence of physical
	 * limitations such as memory, be added to this CustomerServer, as per
	 * {@link BlockingQueue#remainingCapacity()}.
	 * @return - the remaining capacity of this CustomerServer
	 */
	public int getRemainingCapacity() {
		return queue.remainingCapacity();
	}

	/**
	 * Simulates the behaviour of a queue by serving clients in order and waiting
	 * an appropriate amount for each client. New clients may be added while the
	 * simulation runs by inserting them with the {@link #addCustomer(Customer)}
	 * method of this class.
	 */
	@Override
	public void run() {
		while (!simulationOver) {
			try {
				Customer currentlyServed = queue.take();
				
				// Wait in intervals of one second at a time, for better accuracy
				// of remaining waiting time for new-arriving Customers
				for(int i = currentlyServed.getServingTime(); i > 0; --i) {
					waitingTime.decrementAndGet();
					Thread.sleep(1000);
					//this.wait(1000);
				}
				
			} catch (InterruptedException e) {
				System.out.println("Interrupted in CustomerServer#run!");
				e.printStackTrace(); // TODO do something better than this, since printStackTrace() pretty much makes our output above obsolete anyway
			}
		}
	}

}
