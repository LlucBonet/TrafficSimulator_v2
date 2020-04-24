package simulator.view;

import java.awt.BorderLayout;
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

import simulator.model.Weather;
import simulator.model.Road;
import simulator.model.RoadMap;

public class ChangeWeatherDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _status;
	
	private JComboBox<Road> _roads;
	private JComboBox<Weather> _weather;
	private JSpinner _ticksSpinner;
	
	private Weather[] w = Weather.values();
	
	public ChangeWeatherDialog() {
		super();
		initGUI();
	}
	
	public ChangeWeatherDialog(Frame frame) {
		super(frame, true);
		initGUI();
	}
		

	public void initGUI(){
		_status = 0;
		
		this.setTitle("Change Road Weather");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);
		
	    JTextArea help = new JTextArea();
		help.setEditable(false);
		help.setText("Schedule an event to change the weather of a road after a given"
				+ " number o simulation ticks from now.");
		help.setBackground(mainPanel.getBackground());
		help.setWrapStyleWord(true);
		help.setLineWrap(true);
		help.setFont(new Font("TextArea.font", Font.BOLD, 14));
		help.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
		
		mainPanel.add(help, BorderLayout.PAGE_START);
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.X_AXIS));
		  
		  JLabel vehicle = new JLabel("Road: ");
		  settingsPanel.add(vehicle);
		  
		  _roads = new JComboBox<>();
		  settingsPanel.add(_roads);
		  
		  settingsPanel.add(Box.createHorizontalGlue());
		  
		  JLabel CO2Class = new JLabel("Weather: ");
		  settingsPanel.add(CO2Class);
		  
		  _weather = new JComboBox<>();
		  for(int i = 0; i < w.length; i++) {
			  _weather.addItem(w[i]);
		  }
		  settingsPanel.add(_weather);
		  
		  settingsPanel.add(Box.createHorizontalGlue());
		  
		  JLabel ticks = new JLabel("Ticks:");
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
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);
		

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_roads.getSelectedItem() != null) {
					_status = 1;
					ChangeWeatherDialog.this.setVisible(false);
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
		_roads.removeAllItems();
		for(Road r : map.getRoads()) {
			_roads.addItem(r);
		}	
		setLocation(getParent().getWidth() / 2, getParent().getHeight() / 2);
		setVisible(true);
		return _status;
	}
	
	protected Road getRoad() {
		return (Road) _roads.getSelectedItem();
		
	}
	
	protected Weather getWeather() {
		return (Weather) _weather.getSelectedItem();
	}
	
	protected int getTicks() {
		return Integer.parseInt(_ticksSpinner.getValue().toString());
	}
}