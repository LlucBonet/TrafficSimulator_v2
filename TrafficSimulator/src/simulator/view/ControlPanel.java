package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	private File _file;
	
	private final String LOAD = "load";
	private final String SAVE = "save";
	private final String RESUME = "resume";
	private final String CHANGECO2 = "changeCO2";
	private final String CHANGEWEATHER = "changeWeather";
	private final String RUN = "run";
	private final String STOP = "stop";
	private final String RESET = "reset";
	private final String EXIT = "exit";
	
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
		
		//_menuBar = createMenuBar();
		//add(_menuBar, BorderLayout.PAGE_START);
		_toolBar = createToolBar();
		add(_toolBar, BorderLayout.PAGE_START);
	}
	
	public JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
		
		//load
		_fc = new JFileChooser();
		JButton loadButton = new JButton();
		loadButton.setToolTipText("Load a file");
		loadButton.setActionCommand(LOAD);
		loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		loadButton.addActionListener(this);
		toolBar.add(loadButton);
		
		//save
		JButton saveButton = new JButton();
		saveButton.setToolTipText("Save progress");
		saveButton.setActionCommand(SAVE);
		saveButton.setIcon(new ImageIcon("resources/icons/save.png"));
		saveButton.addActionListener(this);
		toolBar.add(saveButton);
		
		//resume
		JButton resumeButton = new JButton();
		resumeButton.setToolTipText("Resume");
		resumeButton.setActionCommand(SAVE);
		resumeButton.setIcon(new ImageIcon("resources/icons/resume.jpg"));
		resumeButton.addActionListener(this);
		toolBar.add(resumeButton);
		
		toolBar.addSeparator();
		
		//change co2 class
		JButton changeCO2ClassButton = new JButton();
		changeCO2ClassButton.setToolTipText("Change CO2 class of a vehicle");
		changeCO2ClassButton.setIcon(new ImageIcon("resources/icons/co2class.png"));
		changeCO2ClassButton.setActionCommand(CHANGECO2);
		changeCO2ClassButton.addActionListener(this);
		toolBar.add(changeCO2ClassButton);
		
		//change weather
		JButton changeWeatherButton = new JButton();
		changeWeatherButton.setToolTipText("Change Weather of a road");
		changeWeatherButton.setIcon(new ImageIcon("resources/icons/weather.png"));
		changeWeatherButton.setActionCommand(CHANGEWEATHER);
		changeWeatherButton.addActionListener(this);
		toolBar.add(changeWeatherButton);
		
		toolBar.addSeparator();
	    
		//run 
		JButton runButton = new JButton();
		runButton.setToolTipText("Run");
		runButton.setActionCommand(RUN);
		runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		runButton.addActionListener(this);
		toolBar.add(runButton);
		
		//stop
		JButton stopButton = new JButton();
		stopButton.setToolTipText("Stop");	
		stopButton.setActionCommand(STOP);
		stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		stopButton.addActionListener(this);
		toolBar.add(stopButton);
		
		//reset
		JButton resetButton = new JButton();
		resetButton.setToolTipText("Reset");
		resetButton.setActionCommand(RESET);
		resetButton.setIcon(new ImageIcon("resources/icons/reset.jpg"));
		resetButton.addActionListener(this);
		toolBar.add(resetButton);
		
		//ticks
		JLabel ticks = new JLabel("Ticks: ");
		ticks.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		toolBar.add(ticks);
		_ticksSpinner = new JSpinner();
		_ticksSpinner.setToolTipText("1-1000");
		_ticksSpinner.setValue(10);
		toolBar.add(_ticksSpinner);
		
		toolBar.add(Box.createHorizontalGlue());
		toolBar.addSeparator();
		
		//exit
		JButton exitButton = new JButton();
		exitButton.setToolTipText("Exit");
		exitButton.setActionCommand(EXIT);
		exitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		exitButton.addActionListener(this);
		toolBar.add(exitButton, -1);
		
		return toolBar;
	}

	
	private void loadFile() {
		_fc.setCurrentDirectory(new File("resources/examples"));
		final int selection = _fc.showOpenDialog(_parent);
		if (selection == JFileChooser.APPROVE_OPTION) {
			_file = _fc.getSelectedFile();
			System.out.println("loading " + _file.getName());
			try {
				_ctrl.reset();
				InputStream in = new FileInputStream(_file);
				try {
					_ctrl.loadEvents(in);
				} catch (Exception e) {
					onError("Archivo no valido: " + e.getLocalizedMessage());
				}
			} catch (FileNotFoundException e) {
				onError(e.getLocalizedMessage());
			}
		}
		else {
			System.out.println("load cancelled by user");
		}
	}
	
	private void saveFile() {
		_fc.setCurrentDirectory(new File("resources/tmp"));
		final int selection = _fc.showOpenDialog(_parent);
		if(selection == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			writeFile(file);
		}
	}
	
	private void writeFile(File file) {
		OutputStream out;
		try {
			out = new FileOutputStream(file);
			if(out != null) {
				PrintStream p = new PrintStream(out);
				JSONObject obj = new JSONObject();
				JSONArray arr = new JSONArray();
				arr.put(_ctrl.report());
	
				obj.put("time_" + _ctrl.getSimulatedTime(), arr);
				p.println(obj.toString());
				p.close();
			}	
		} catch (FileNotFoundException e) {
			onError(e.getLocalizedMessage());
		}
		
	}
	
	void resume() {
//		run_sim()
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
		try {
			_ctrl.loadEvents(null);
		} catch (Exception e) {
			onError("Archivo no válido: " + e.getLocalizedMessage());
		}
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
		else if(SAVE.equals(e.getActionCommand())) saveFile();
		else if(RESUME.equals(e.getActionCommand())) resume();
		else if(CHANGECO2.equals(e.getActionCommand())) changeCO2Class();
		else if(CHANGEWEATHER.equals(e.getActionCommand())) changeWeather();
		else if(RUN.equals(e.getActionCommand())) run();
		else if(STOP.equals(e.getActionCommand())) stop();
		else if(RESET.equals(e.getActionCommand())) reset();
		else if(EXIT.equals(e.getActionCommand())) exit();	
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
