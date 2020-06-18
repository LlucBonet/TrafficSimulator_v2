package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private int _status;
	
	private JComboBox<Vehicle> _vehicle;
	private JLabel _vehicleL;
	private JComboBox<Integer> _co2class; 
	private JLabel _co2classL;
	private JSpinner _ticksSpinner;
	
	public ChangeCO2ClassDialog(Frame frame) {
		super(frame, true);
		initGUI();
	}
	
	public void initGUI() {
		_status = 0;
		
		this.setTitle("Change Road Weather");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);
		
	    JTextArea help = new JTextArea();
		help.setEditable(false);
		help.setText("Schedule an event to change the CO2 class of a vehicle after a given"
				+ " number of simulation ticks from now.");
		help.setBackground(mainPanel.getBackground());
		help.setWrapStyleWord(true);
		help.setLineWrap(true);
		help.setFont(new Font("TextArea.font", Font.BOLD, 14));
		help.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
		
		mainPanel.add(help, BorderLayout.PAGE_START);
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.X_AXIS));
		  
		 _vehicleL = new JLabel("Vehicle: ");
		  settingsPanel.add(_vehicleL);
		  
		  _vehicle = new JComboBox<>();
		  _vehicle.setBackground(Color.WHITE);
		  settingsPanel.add(_vehicle);
		  
		  settingsPanel.add(Box.createHorizontalGlue());
		  
		  _co2classL = new JLabel("CO2 Class: ");
		  settingsPanel.add(_co2classL);
		  
		  _co2class = new JComboBox<>();
		  _co2class.setBackground(Color.WHITE);
		  
		  settingsPanel.add(_co2class);
		  
		  settingsPanel.add(Box.createHorizontalGlue());
		  
		  JLabel ticks = new JLabel("Ticks: ");
		  settingsPanel.add(ticks);

		  _ticksSpinner = new JSpinner();
		  settingsPanel.add(_ticksSpinner);
		
		  settingsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
		   
		mainPanel.add(settingsPanel, BorderLayout.CENTER);
		
		
		JPanel buttonsPanel = new JPanel();
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_vehicle.getSelectedItem() != null && Integer.parseInt(_ticksSpinner.getValue().toString()) > 0) {
					_status = 1;
					setVisible(false);
				}
				else if(Integer.parseInt(_ticksSpinner.getValue().toString()) < 1) {
					JOptionPane.showMessageDialog(null, "El valor de ticks tiene que ser mayor a 0", "Error", JOptionPane.WARNING_MESSAGE);;
				}
			}
		});
		buttonsPanel.add(okButton);

		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 5));
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		setPreferredSize(new Dimension(600, 200));
		pack();
		setResizable(false);
		setVisible(false);
		
		for(int i = 1; i <= 10; i++) {
			  _co2class.addItem(i);
		  }
	}
	
	
	protected int open(RoadMap map) {
		_vehicle.removeAllItems();
		for(Vehicle v : map.getVehicles()) {
			_vehicle.addItem(v);
		}	
		setLocation(getParent().getWidth() / 2, getParent().getHeight() / 2);
		setVisible(true);
		return _status;
	}
	
	protected Vehicle getVehicle() {
		return (Vehicle) _vehicle.getSelectedItem();
		
	}
	
	protected int getCO2Class() {
		return (int) _co2class.getSelectedItem();
	}
	
	protected int getTicks() {
		return Integer.parseInt(_ticksSpinner.getValue().toString());
	}
}
