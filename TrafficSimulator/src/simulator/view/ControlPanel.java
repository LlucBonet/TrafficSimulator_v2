package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
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

public class ControlPanel extends JPanel implements TrafficSimObserver, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller _ctrl;
	private RoadMap _map;
	private int _time;
	
	private JToolBar _toolBar;
	
	private File _file;
	
	private JButton _loadButton;
	private JButton _changeCO2ClassButton;
	private JButton _changeWeatherButton;
	private JButton _runButton;
	private JButton _stopButton;

	
	private final String LOAD = "load";
	private final String CHANGECO2 = "changeCO2";
	private final String CHANGEWEATHER = "changeWeather";
	private final String RUN = "run";
	private final String STOP = "stop";
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
		
		_toolBar = createToolBar();
		add(_toolBar, BorderLayout.PAGE_START);
	}
	
	public JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
		
		//load
		_fc = new JFileChooser();
		_loadButton = new JButton();
		_loadButton.setToolTipText("Load a file");
		_loadButton.setActionCommand(LOAD);
		_loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		_loadButton.addActionListener(this);
		toolBar.add(_loadButton);
		
		toolBar.addSeparator();
		
		//change co2 class
		_changeCO2ClassButton = new JButton();
		_changeCO2ClassButton.setToolTipText("Change CO2 class of a vehicle");
		_changeCO2ClassButton.setIcon(new ImageIcon("resources/icons/co2class.png"));
		_changeCO2ClassButton.setActionCommand(CHANGECO2);
		_changeCO2ClassButton.addActionListener(this);
		toolBar.add(_changeCO2ClassButton);
		
		//change weather
		_changeWeatherButton = new JButton();
		_changeWeatherButton.setToolTipText("Change Weather of a road");
		_changeWeatherButton.setIcon(new ImageIcon("resources/icons/weather.png"));
		_changeWeatherButton.setActionCommand(CHANGEWEATHER);
		_changeWeatherButton.addActionListener(this);
		toolBar.add(_changeWeatherButton);
		
		toolBar.addSeparator();
	    
		//run 
		_runButton = new JButton();
		_runButton.setToolTipText("Run");
		_runButton.setActionCommand(RUN);
		_runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		_runButton.addActionListener(this);
		toolBar.add(_runButton);
		
		//stop
		_stopButton = new JButton();
		_stopButton.setToolTipText("Stop");	
		_stopButton.setActionCommand(STOP);
		_stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		_stopButton.addActionListener(this);
		toolBar.add(_stopButton);
		
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
					_ctrl.loadEvents(in);

			} catch (Exception e) {
				onError("Archivo no valido: " + e.getLocalizedMessage());
			}
		}
		else {
			System.out.println("load cancelled by user");
		}
	}

//	private void saveFile() {
//		_fc.setCurrentDirectory(new File("resources/tmp"));
//		final int selection = _fc.showOpenDialog(_parent);
//		if(selection == JFileChooser.APPROVE_OPTION) {
//			File file = _fc.getSelectedFile();
//			writeFile(file);
//		}
//	}
//	
//	private void writeFile(File file) {
//		OutputStream out;
//		try {
//			out = new FileOutputStream(file);
//			if(out != null) {
//				PrintStream p = new PrintStream(out);
//				JSONObject obj = new JSONObject();
//				JSONArray arr = new JSONArray();
//				arr.put(_ctrl.report());
//	
//				obj.put("time_" + _time, arr);
//				p.println(obj.toString());
//				p.close();
//			}	
//		} catch (FileNotFoundException e) {
//			onError(e.getLocalizedMessage());
//		}
//		
//	}
	
	
	public void changeCO2Class() {
		ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog((Frame) SwingUtilities.getWindowAncestor(this));
		int status = dialog.open(_map);
		System.out.println(status);
		
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
		_stopped = false;
		enableToolBar(false);
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
	
//	private void reset() {
//		_ctrl.reset();
//		try {
//			_ctrl.loadEvents(null);
//		} catch (Exception e) {
//			onError("Archivo no válido: " + e.getLocalizedMessage());
//		}
//	}
	
	private void enableToolBar(boolean enable) {
		this._loadButton.setEnabled(enable);
		this._changeCO2ClassButton.setEnabled(enable);
		this._changeWeatherButton.setEnabled(enable);
		this._runButton.setEnabled(enable);
		this._stopButton.setEnabled(!_stopped);
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
		else if(CHANGECO2.equals(e.getActionCommand())) changeCO2Class();
		else if(CHANGEWEATHER.equals(e.getActionCommand())) changeWeather();
		else if(RUN.equals(e.getActionCommand())) run();
		else if(STOP.equals(e.getActionCommand())) stop();
		else if(EXIT.equals(e.getActionCommand())) exit();	
	}
	
	//IMPLEMENTS TRAFFICSIMOBSERVER
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_time = time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_map = map;
		_time = time;
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_time = time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_map = map;
		_time = time;
	}
	
	@Override
	public void onError(String err) {
		JOptionPane.showMessageDialog(this.getParent(), err);
	}
}
