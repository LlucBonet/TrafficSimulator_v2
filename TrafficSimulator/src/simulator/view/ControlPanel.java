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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

public class ControlPanel extends JPanel implements TrafficSimObserver, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller _ctrl;
	private RoadMap _map;
	
	private JToolBar _toolBar;
	private JMenuBar _menuBar;
	
	private final String LOAD = "load";
	private final String CHANGECO2 = "changeCO2";
	private final String CHANGEWEATHER = "changeWeather";
	private final String START = "start";
	private final String STOP = "stop";
	private final String RESET = "reset";
	private final String EXIT = "exit";
	
	private JButton _loadButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JButton _exitButton;
	private JButton _changeCO2ClassButton;
	private JButton _changeWeatherButton;
	private JButton _resetButton;
	private JSpinner _ticksSpinner;
	
	private JFileChooser _fc;
	
	private boolean _stopped;
	
	private MainWindow _parent; //para JDialog
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_ctrl.addObserver(this);
		_stopped = true;
		initGUI();
	}

	ControlPanel(Controller ctrl, MainWindow parent){
		_ctrl = ctrl;
		_ctrl.addObserver(this);
		_stopped = true;
		initGUI();
		_parent = parent;
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
		
		//reset
		_resetButton = new JButton();
		_resetButton.setToolTipText("Reset");
		_resetButton.setIcon(new ImageIcon("resources/icons/reset.jpg"));
		_resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		_toolBar.add(_resetButton);
		
		//ticks
		JLabel ticks = new JLabel("Ticks: ");
		ticks.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		_toolBar.add(ticks);
		_ticksSpinner = new JSpinner();
		_ticksSpinner.setToolTipText("1-1000");
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
		
		initMenuBar();
	}
	
	public void initMenuBar() {
		_menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Menu"); 
		JMenuItem loadF = new JMenuItem("Load File");
		loadF.setActionCommand(LOAD);
		loadF.addActionListener(this);
		JMenuItem changeCO2 = new JMenuItem("Change CO2 Class");
		changeCO2.setActionCommand(CHANGECO2);
		changeCO2.addActionListener(this);
		JMenuItem changeW = new JMenuItem("Change Weather");
		changeW.setActionCommand(CHANGEWEATHER);
		changeW.addActionListener(this);
		JMenuItem exit = new JMenuItem("Exit");
		exit.setActionCommand(EXIT);
		exit.addActionListener(this);
		
		menu.add(loadF);
		menu.add(changeCO2);
		menu.add(changeW);
		menu.add(exit);
		_menuBar.add(menu);
		
		JMenu execution = new JMenu("Execution");
		JMenuItem start = new JMenuItem("Start");
		start.setActionCommand(START);
		start.addActionListener(this);
		JMenuItem stop = new JMenuItem("Stop");
		stop.setActionCommand(STOP);
		stop.addActionListener(this);
		JMenuItem reset = new JMenuItem("Reset");
		reset.setActionCommand(RESET);
		reset.addActionListener(this);
		
		execution.add(start);
		execution.add(stop);
		execution.add(reset);
		_menuBar.add(execution);
		
		this.add(_menuBar);
	}
	
	private void loadFile() {
		
		final int selection = _fc.showOpenDialog(_parent);
		if (selection == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			System.out.println("loading " + file.getName());
			try {
				_ctrl.reset();
				InputStream in = new FileInputStream(file);
				_ctrl.loadEvents(in);
			} catch (FileNotFoundException e) {
				onError(e.getLocalizedMessage());
			}
		}
		else {
			System.out.println("load cancelled by user");
		}
	}
	
	public void changeCO2Class() {
		ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog((Frame) SwingUtilities.getWindowAncestor(this));
		int status = dialog.open(_map);
		System.out.println(status);
		
		if(status == 1) {
			List<Pair<String, Integer>> cs = new ArrayList<>();
			cs.add(new Pair<String, Integer>(dialog.getVehicle().getId(), dialog.getCO2Class()));
			try {
				_ctrl.addEvent(new SetContClassEvent(_ctrl.getSimulatedTime() + dialog.getTicks(), cs));
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
				_ctrl.addEvent(new SetWeatherEvent(_ctrl.getSimulatedTime() + dialog.getTicks(), ws));
			}catch(Exception e) {
				onError("Something went wrong: " + e.getLocalizedMessage());
			}
		}
	}
	
	private void run() {
		final int n = Integer.parseInt(_ticksSpinner.getValue().toString());
		_stopped = false;
		run_sim(n);
	}
	
	private void run_sim(final int n) {
		if(n > 0 && !_stopped) {
			try {
				_ctrl.run(1, null);
			}catch(Exception e) {
				this.onError(e.getLocalizedMessage());
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
	
	private void reset() {
		_ctrl.reset();
		_ctrl.loadEvents(null);
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

	//ACTION LISTENER
	@Override
	public void actionPerformed(ActionEvent e) {
		if(LOAD.equals(e.getActionCommand())) loadFile();
		if(CHANGECO2.equals(e.getActionCommand())) changeCO2Class();
		if(CHANGEWEATHER.equals(e.getActionCommand())) changeWeather();
		if(START.equals(e.getActionCommand())) run();
		if(STOP.equals(e.getActionCommand())) stop();
		if(RESET.equals(e.getActionCommand())) reset();
		if(EXIT.equals(e.getActionCommand())) exit();
		
	}
	
	//IMPLEMENTS TRAFFICSIMOBSERVER
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_map = map;
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_map = map;
	}
	
	@Override
	public void onError(String err) {
		JOptionPane.showMessageDialog(this.getParent(), err);
	}
}
