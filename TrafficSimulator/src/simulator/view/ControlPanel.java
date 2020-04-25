package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller _ctrl;
	private RoadMap _map;
	private int  _time;
	
	private JToolBar _toolBar;
	private JButton _loadButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JButton _exitButton;
	private JButton _changeCO2ClassButton;
	private JButton _changeWeatherButton;
	private JSpinner _ticksSpinner;
	
	private JFileChooser _fc;
	
	private boolean _stopped;
	
	private MainWindow _parent; //para JDialog
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		_time = 0;
		initGUI();
		_ctrl.addObserver(this);
	}

	ControlPanel(Controller ctrl, MainWindow parent){
		_ctrl = ctrl;
		_stopped = true;
		_time = 0;
		initGUI();
		_parent = parent;
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		_toolBar = new JToolBar();
		
		//load
		_fc = new JFileChooser();
		_fc.setCurrentDirectory(new File("resources/examples"));
		_loadButton = new JButton();
		_loadButton.setToolTipText("Load a file");
		_loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		_loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadFile();
			}
		});
		_toolBar.add(_loadButton);
		
		_toolBar.addSeparator();
		
		//change co2 class
		_changeCO2ClassButton = new JButton();
		_changeCO2ClassButton.setToolTipText("Change CO2 class of a vehicle");
		_changeCO2ClassButton.setIcon(new ImageIcon("resources/icons/co2class.png"));

		_changeCO2ClassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 changeCO2Class();
			}
		});
		_toolBar.add(_changeCO2ClassButton);
		
		//change weather
		_changeWeatherButton = new JButton();
		_changeWeatherButton.setToolTipText("Change Weather of a road");
		_changeWeatherButton.setIcon(new ImageIcon("resources/icons/weather.png"));

		_changeWeatherButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 changeWeather();
			}
		});
		_toolBar.add(_changeWeatherButton);
		
		_toolBar.addSeparator();
	    
		
		//run 
		_runButton = new JButton();
		_runButton.setToolTipText("Run");
		_runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		_runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(_stopped)
					run();
			}
		});
		_toolBar.add(_runButton);
		
		//stop
		_stopButton = new JButton();
		_stopButton.setToolTipText("Stop");	
		_stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		_stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!_stopped) stop();
			}
		});
		_toolBar.add(_stopButton);
		
		//ticks
		JLabel ticks = new JLabel("Ticks: ");
		ticks.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		_toolBar.add(ticks);
		_ticksSpinner = new JSpinner();
		_ticksSpinner.setValue(10);
		_toolBar.add(_ticksSpinner);
		
		_toolBar.add(Box.createHorizontalGlue());
		_toolBar.addSeparator();
		
		//exit
		_exitButton = new JButton();
		_exitButton.setToolTipText("Exit");
		_exitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
		});
		_toolBar.add(_exitButton, -1);
		add(_toolBar, BorderLayout.PAGE_START);
	}
	
	private void loadFile() {
		
		final int selection = _fc.showOpenDialog(_parent);
		
		_fc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				if (selection == JFileChooser.APPROVE_OPTION) {
					File file = _fc.getSelectedFile();
					System.out.println("loading " + file.getName());
					
					try {
						InputStream in = new FileInputStream(file);
						_ctrl.loadEvents(in);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else {
					System.out.println("load cancelled by user");
				}
			}
		});
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	public void changeCO2Class() {
		ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog(_parent);
		int status = dialog.open(_map);
		
		if(status == 1) {
			List<Pair<String, Integer>> cs = new ArrayList<>();
			cs.add(new Pair<String, Integer>(dialog.getVehicle().getId(), dialog.getCO2Class()));
			try {
				_ctrl.addEvent(new SetContClassEvent(_time + dialog.getTicks(), cs));
			}catch(Exception e) {
				onError("Something went wrong: " + e.getLocalizedMessage());
			}
		}
	}
	
	public void changeWeather(){
		ChangeWeatherDialog dialog = new ChangeWeatherDialog((Frame) SwingUtilities.getWindowAncestor(this));
		int status = dialog.open(_map);
		
		if(status == 1) {
			List<Pair<String, Weather>> ws = new ArrayList<>();
			ws.add(new Pair<String, Weather>(dialog.getRoad().getId(), dialog.getWeather()));
			try {
				_ctrl.addEvent(new SetWeatherEvent(_time + dialog.getTicks(), ws));
			}catch(Exception e) {
				onError("Something went wrong: " + e.getLocalizedMessage());
			}
		}
	}
	
	private void run() {
		final int n = Integer.parseInt(_ticksSpinner.getValue().toString());
		run_sim(n);
	}
	
	private void run_sim(final int n) {
		if(n > 0 && !_stopped) {
			try {
				_ctrl.run(1, null);
			}catch(Exception e) {
				//TODO show error message
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		}else {
			enableToolBar(true);
			_stopped = true;
		}
	}
	
	private void stop() {
		_stopped = true;
	}
	
	private void enableToolBar(boolean enable) {
		_toolBar.setEnabled(enable);
	}
	
	private void exit() {
		int seleccion = JOptionPane.showConfirmDialog(this.getParent(), "Do you want to exit?", "EXIT", JOptionPane.OK_CANCEL_OPTION);
		if(seleccion == 0) {
			System.exit(0); 
		}
		
	}
}
