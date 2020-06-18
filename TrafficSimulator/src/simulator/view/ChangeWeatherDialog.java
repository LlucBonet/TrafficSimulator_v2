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

import simulator.model.Weather;
import simulator.model.Road;
import simulator.model.RoadMap;

public class ChangeWeatherDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _status;
	
	private JComboBox<Road> _road;
	private JLabel _roadL;
	private JComboBox<Weather> _weather; 
	private JLabel _weatherL;
	private JSpinner _ticksSpinner;
	private Weather[] _w = Weather.values();
	
	public ChangeWeatherDialog(Frame frame) {
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
		help.setText("Schedule an event to change the weather of a road after a given"
				+ " number of simulation ticks from now.");
		help.setBackground(mainPanel.getBackground());
		help.setWrapStyleWord(true);
		help.setLineWrap(true);
		help.setFont(new Font("TextArea.font", Font.BOLD, 14));
		help.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
		
		mainPanel.add(help, BorderLayout.PAGE_START);
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.X_AXIS));
		  
		 _roadL = new JLabel("Road: ");
		  settingsPanel.add(_roadL);
		  
		  _road = new JComboBox<>();
		  _road.setBackground(Color.WHITE);
		  settingsPanel.add(_road);
		  
		  settingsPanel.add(Box.createHorizontalGlue());
		  
		  _weatherL = new JLabel("Weather: ");
		  settingsPanel.add(_weatherL);
		  
		  _weather = new JComboBox<>();
		  _weather.setBackground(Color.WHITE);
		  
		  settingsPanel.add(_weather);
		  
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
				if (_road.getSelectedItem() != null && Integer.parseInt(_ticksSpinner.getValue().toString()) > 0) {
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
		
		for(int i = 0; i < _w.length; i++) {
			  _weather.addItem(_w[i]);
		  }	

	}
	
	protected int open(RoadMap map) {
		_road.removeAllItems();
		for(Road r : map.getRoads()) {
			_road.addItem(r);
		}	
		
		setLocation(getParent().getWidth() / 2, getParent().getHeight() / 2);
		setVisible(true);
		return _status;
	}
	
	protected Road getRoad() {
		return  (Road) _road.getSelectedItem();
		
	}
	
	protected Weather getWeather() {
		return  (Weather) _weather.getSelectedItem();
	}
	
	protected int getTicks() {
		return Integer.parseInt(_ticksSpinner.getValue().toString());
	}
}