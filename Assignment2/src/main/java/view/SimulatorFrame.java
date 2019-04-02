package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class SimulatorFrame extends JFrame {
	// This is just a default value; we don't really need it
	private static final long serialVersionUID = 1L;
	
	private static final String[] strategies = {"Shortest queue", "Shortest time"};
	
	private JPanel mainPanel;
	private JPanel settingsPanel;
	private JPanel simulationPanel;
	private LoggingArea logDisplay;
	private JButton startButton = new JButton("Start");
	
	private JTextField timeField = new JTextField("100");
	private JTextField queuesField = new JTextField("4");
	private JTextField minServField = new JTextField("2");
	private JTextField maxServField = new JTextField("9");
	private JTextField clientsField = new JTextField("100");
	private JComboBox<String> strategyBox = new JComboBox<String>(strategies);

	private static final int WIDTH = 600, HEIGHT = 600;
	
	public SimulatorFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		
		mainPanel = new JPanel();
		//mainPanel.setLayout(new BorderLayout());
		mainPanel.setLayout(new GridLayout(0, 1));
		this.add(mainPanel); // TODO: should we set it as the content pane, too?
			settingsPanel = new JPanel();
			settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
			mainPanel.add(settingsPanel);
				JPanel hiddenSettingsPanel = new JPanel(new GridLayout(3,4));
				settingsPanel.add(hiddenSettingsPanel);
					hiddenSettingsPanel.add(new JLabel("Simulation time"));
					hiddenSettingsPanel.add(timeField);
					
					hiddenSettingsPanel.add(new JLabel("No. of queues"));
					hiddenSettingsPanel.add(queuesField);
					
					hiddenSettingsPanel.add(new JLabel("Min. serve time"));
					hiddenSettingsPanel.add(minServField);
					
					hiddenSettingsPanel.add(new JLabel("Max. serve time"));
					hiddenSettingsPanel.add(maxServField);
					
					hiddenSettingsPanel.add(new JLabel("No. of clients"));
					hiddenSettingsPanel.add(clientsField);
					
					hiddenSettingsPanel.add(new JLabel("Strategy"));
					hiddenSettingsPanel.add(strategyBox);
				settingsPanel.add(startButton);
			simulationPanel = new JPanel();
			//mainPanel.add(simulationPanel, BorderLayout.CENTER); // TODO: add it in a good position relative to the other elements to come
			mainPanel.add(simulationPanel);
		
			logDisplay = new LoggingArea();
			JScrollPane hiddenDisplayScroller = new JScrollPane(logDisplay);
			//mainPanel.add(hiddenDisplayScroller, BorderLayout.SOUTH);
			mainPanel.add(hiddenDisplayScroller);
		this.setVisible(true);
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
	
	public String getTime() {
		return timeField.getText();
	}
	
	public String getClients() {
		return clientsField.getText();
	}
	
	public String getMinServeTime() {
		return minServField.getText();
	}
	
	public String getMaxServeTime() {
		return maxServField.getText();
	}
	
	public String getNoQueues() {
		return queuesField.getText();
	}
	
	/**
	 * Returns the selected strategy, with the following meanings:
	 * <ol start="0">
	 * <li> Shortest queue </li>
	 * <li> Shortest time </li>
	 * </ol>
	 * @return an integer representing the selected strategy
	 */
	public int getStrategy() {
		return strategyBox.getSelectedIndex();
	}

}
