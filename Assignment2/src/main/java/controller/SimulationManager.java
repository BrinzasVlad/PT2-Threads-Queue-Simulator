package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Customer;
import model.QueueScheduler;
import model.strategy.Strategy.Policy;
import view.SimulatorFrame;

public class SimulationManager implements Runnable {
	
	// Default values to be overwritten by reading from the UI
	private int timeLimit = 100;
	private int minProcesingTime = 2;
	private int maxProcessingTime = 9;
	private int numberOfQueues = 4;
	private int numberOfClients = 100;
	private int maxClientsPerQueue = 10;
	private Policy selectionPolicy = Policy.SHORTEST_TIME;
	
	private SimulatorFrame frame;
	private QueueScheduler scheduler;
	private List<Customer> generatedClients;
	
	private boolean starting = false;
	
	public SimulationManager() {
		frame = new SimulatorFrame();
		frame.addStartButtonListener(e -> {starting = true;});
	}
	
	/**
	 * Generates a list of randomised Customers with the given parameters. The
	 * list is guaranteed to be sorted in ascended order of arrival times. It is guaranteed
	 * that the ideal finishing time of each client (that is, the finish time assuming they
	 * are served as soon as they arrive) is no greater than <b>maxTime</b>.
	 * @param number - the number of clients to generate
	 * @param minProcTime - the minimum processing time for each client
	 * @param maxProcTime - the maximum processing time for each client
	 * @param maxTime - a maximum bound on the client's time as per the description
	 * @return a list of randomised Customers
	 */
	private List<Customer> generateRandomClients(int number, int minProcTime, int maxProcTime, int maxTime) {
		List<Customer> customers = new ArrayList<Customer>(numberOfClients);
		
		Random rand = new Random();
		for(int i = 0; i < number; ++i) {
			int procTime = minProcTime + rand.nextInt(maxProcTime - minProcTime);
			int arrivTime = rand.nextInt(maxTime - procTime);
			
			customers.add(new Customer(arrivTime, procTime));
		}
		
		// Sort customers in ascending order of arrival, for commodity
		customers.sort( (Customer a, Customer b) -> Integer.compare(a.getArrivalTime(), b.getArrivalTime()) );
		
		return customers;
	}
	
	/**
	 * Collects the input data from the UI.
	 * Currently gathered data:
	 * <ul>
	 * 	<li>None, just the signal to start</li>
	 * </ul>
	 */
	private void gatherInputs() {
		; // TODO: actually gather data
	}

	@Override
	public void run() {
		while(true) {
			// Hacky way to not start until the button is pressed
			// and to repeat whenever it's pressed again
			while(!starting) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					System.out.println("Interrupted while waiting to start");
					e1.printStackTrace(); // TODO: same as always
				}
			}
			starting = false; // TODO: unneeded
			
			// Preparatory phase
			gatherInputs();
			frame.getLoggingArea().setText("");
			
			// Initialise values
			scheduler = new QueueScheduler(timeLimit, numberOfQueues, maxClientsPerQueue);
			scheduler.setStrategy(selectionPolicy);
			
			generatedClients = generateRandomClients(numberOfClients, minProcesingTime, maxProcessingTime, timeLimit);
			
			// Begin simulation
			scheduler.start();
			for(int currentTime = 0; currentTime <= timeLimit; ++currentTime) {
				frame.getLoggingArea().addTextLine("Time moment " + currentTime + ".");
				
				// Have Customers arrive
				for(Customer c : generatedClients) {
					if (c.getArrivalTime() == currentTime) {
						frame.getLoggingArea().addTextLine("Dispatching client " + c.toString());
						scheduler.dispatchCustomer(c);
					}
				}
				
				// Update UI
				frame.displayData(scheduler.getQueueDescriptions());
				
				// Skip one second
				try {
					Thread.sleep(1000);
					//this.wait(1000);
				} catch (InterruptedException e) {
					System.out.println("Interrupted in SimulationManager#run!");
					e.printStackTrace(); // TODO do something better than this, since printStackTrace() pretty much makes our output above obsolete anyway
				}
			}
			
			scheduler.endSimulation();
			starting = false; // Set this value to false at the end, so the button must be re-pressed
		}
	}
	
	public static void main(String args[]) {
		SimulationManager manager = new SimulationManager();
		manager.run();
	}

}