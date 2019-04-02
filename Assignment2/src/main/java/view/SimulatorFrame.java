package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SimulatorFrame extends JFrame {

	// This is just a default value; we don't really need it
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	private JPanel simulationPanel;
	private JButton startButton;
	private LoggingArea logDisplay;
	
	private static final int WIDTH = 600, HEIGHT = 600;
	
	public SimulatorFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		
		mainPanel = new JPanel();
		//mainPanel.setLayout(new BorderLayout());
		mainPanel.setLayout(new GridLayout(0, 1));
		this.add(mainPanel); // TODO: should we set it as the content pane, too?
		
		startButton = new JButton("Start");
		//mainPanel.add(startButton, BorderLayout.NORTH);
		mainPanel.add(startButton);
		
		simulationPanel = new JPanel();
		//mainPanel.add(simulationPanel, BorderLayout.CENTER); // TODO: add it in a good position relative to the other elements to come
		mainPanel.add(simulationPanel);
		
		logDisplay = new LoggingArea();
		JScrollPane hiddenDisplayScroller = new JScrollPane(logDisplay);
		//mainPanel.add(hiddenDisplayScroller, BorderLayout.SOUTH);
		mainPanel.add(hiddenDisplayScroller);
		
		this.setVisible(true); // TODO: should we really set it visible here?
	}
	
	/**
	 * Displays the given data pertaining to the queues in the simulation
	 * window. The data set is expected to contain one String representing
	 * the current state for each queue.
	 * @param queuesData - the data to be displayed
	 */
	public void displayData(List<String> queuesData) {
		simulationPanel.removeAll();
		
		JScrollPane dataPane = createDataScrollPane(queuesData);
		
		simulationPanel.add(dataPane);
		simulationPanel.repaint();
		simulationPanel.validate();
	}
	
	/**
	 * Creates a new JScrollPane that holds the data pertaining to the queues
	 * that has been passed as a parameter.
	 * @param queuesData - the data to be displayed
	 * @return a JScrollPane that contains the given data
	 */
	private JScrollPane createDataScrollPane(List<String> queuesData) {
		// We use toArray because JList does not support
		// getting initial data from a List, but only from an array
		JList<String> queueList = new JList<String>(queuesData.toArray(new String[0]));
		return new JScrollPane(queueList);
	}
	
	/**
	 * Adds an ActionListener to the Start button of this SimulatorFrame.
	 * @param act - the ActionListener to be added
	 */
	public void addStartButtonListener(ActionListener act) {
		startButton.addActionListener(act);
	}
	
	public LoggingArea getLoggingArea() {
		return logDisplay;
	}

}
