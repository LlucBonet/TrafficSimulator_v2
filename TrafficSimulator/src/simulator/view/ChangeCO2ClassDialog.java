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
	
	private JComboBox<Vehicle> _vehicles;
	private JComboBox<Integer> _CO2Class;
	private JSpinner _ticksSpinner;
	
	public ChangeCO2ClassDialog(Frame frame) {
		super(frame, true);
		initGUI();
	}
	
	public ChangeCO2ClassDialog() {
		super();
		initGUI();
	}

	public void initGUI(){
		_status = 0;
		
		this.setTitle("Change CO2 Class");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);
		
	    JTextArea help = new JTextArea();
		help.setEditable(false);
		help.setText("Schedule an event to change the CO2 class of a vehicle after a given"
				+ " number o simulation ticks from now.");
		help.setBackground(mainPanel.getBackground());
		help.setWrapStyleWord(true);
		help.setLineWrap(true);
		help.setFont(new Font("TextArea.font", Font.BOLD, 14));
		help.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
		
		mainPanel.add(help, BorderLayout.PAGE_START);
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.X_AXIS));
		  
		  JLabel vehicle = new JLabel("Vehicle: ");
		  settingsPanel.add(vehicle);
		  
		  _vehicles = new JComboBox<>();
		  _vehicles.setBackground(Color.WHITE);
		  settingsPanel.add(_vehicles);
		  
		  settingsPanel.add(Box.createHorizontalGlue());
		  
		  JLabel CO2Class = new JLabel("CO2 Class: ");
		  settingsPanel.add(CO2Class);
		  
		  _CO2Class = new JComboBox<>();
		  _CO2Class.setBackground(Color.WHITE);
		  for(int i = 0; i < 10; i++) {
			  _CO2Class.addItem(i + 1);
		  }
		  settingsPanel.add(_CO2Class);
		  
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
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_vehicles.getSelectedItem() != null) {
					_status = 1;
					ChangeCO2ClassDialog.this.setVisible(false);
				}
			}
		});
		buttonsPanel.add(okButton);

		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 5));
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		setPreferredSize(new Dimension(600, 200));
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	protected int open(RoadMap map) {
		_vehicles.removeAllItems();
		for(Vehicle v : map.getVehicles()) {
			_vehicles.addItem(v);
		}	
		setLocation(getParent().getWidth() / 2, getParent().getHeight() / 2);
		setVisible(true);
		return _status;
	}
	
	protected Vehicle getVehicle() {
		return (Vehicle) _vehicles.getSelectedItem();
		
	}
	
	protected int getCO2Class() {
		return (int) _CO2Class.getSelectedItem();
	}
	
	protected int getTicks() {
		return Integer.parseInt(_ticksSpinner.getValue().toString());
	}
}
