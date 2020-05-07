package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private JMenuBar _menu;
	private ControlPanel _cp;
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		this.initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		_cp = new ControlPanel(_ctrl);
		
		mainPanel.add(_cp, BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl), BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		//menu
		_menu = createMenu();
		this.setJMenuBar(_menu);
		
		//tables
		JPanel eventsView = 
				createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);
		
		JPanel vehiclesView =
				createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesView);
		
		JPanel roadsView =
				createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
		roadsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsView);
		
		JPanel junctionsView =
				createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
		junctionsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsView);
		
		
		//maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		
		JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
		mapByRoadView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapByRoadView);
		
		
	
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	private JMenuBar createMenu() {
			JMenuBar menuBar = new JMenuBar();
		
		JMenu file = new JMenu("File"); 
		JMenuItem loadF = new JMenuItem("Load");
		loadF.setActionCommand("load");
		loadF.addActionListener(_cp);
		JMenuItem saveF = new JMenuItem("Save");
		saveF.setActionCommand("save");
		saveF.addActionListener(_cp);
		JMenuItem changeCO2 = new JMenuItem("Change CO2 Class");
		changeCO2.setActionCommand("changeCO2");
		changeCO2.addActionListener(_cp);
		JMenuItem changeW = new JMenuItem("Change Weather");
		changeW.setActionCommand("changeWeather");
		changeW.addActionListener(_cp);
		JMenuItem exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				 ActionEvent.SHIFT_MASK));
		exit.addActionListener(_cp);
		
		file.add(loadF);
		file.add(saveF);
		file.addSeparator();
		file.add(changeCO2);
		file.add(changeW);
		file.addSeparator();
		file.add(exit);
		file.setMnemonic(KeyEvent.VK_F); //Alt f
		menuBar.add(file);
		
		JMenu execution = new JMenu("Execution");
		JMenuItem start = new JMenuItem("Start");
		start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				 ActionEvent.SHIFT_MASK));
		start.setActionCommand("run");
		start.addActionListener(_cp);
		JMenuItem stop = new JMenuItem("Stop");
		stop.setActionCommand("stop");
		stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				 ActionEvent.CTRL_MASK));
		stop.addActionListener(_cp);
		JMenuItem reset = new JMenuItem("Reset");
		reset.setActionCommand("reset");
		reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				 ActionEvent.SHIFT_MASK));
		reset.addActionListener(_cp);
		
		execution.add(start);
		execution.add(stop);
		execution.add(reset);
		execution.setMnemonic(KeyEvent.VK_E); //Alt s
		menuBar.add(execution);
		
		return menuBar;
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), title));
		p.add(new JScrollPane(c));
		return p;
	}
}
