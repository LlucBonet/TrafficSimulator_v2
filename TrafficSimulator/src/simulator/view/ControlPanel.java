package simulator.view;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller _ctrl;
	private boolean _stopped;
	
	public ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initControlPanel();
		//_ctrl.addObserver(o);
	}

//	public ControlPanel(LayoutManager layout) {
//		super(layout);
//		// TODO Auto-generated constructor stub
//	}
//
//	public ControlPanel(boolean isDoubleBuffered) {
//		super(isDoubleBuffered);
//		// TODO Auto-generated constructor stub
//	}
//
//	public ControlPanel(LayoutManager layout, boolean isDoubleBuffered) {
//		super(layout, isDoubleBuffered);
//		// TODO Auto-generated constructor stub
//	}
//	
	private void initControlPanel() {
		JToolBar tb = createJToolBar();
		this.add(tb, BorderLayout.PAGE_START);
	}
	
	private InputStream selectOpenFile() {
		InputStream in = null;
		final int selection;
		
		final JFileChooser fc = new JFileChooser();
		
		selection = fc.showOpenDialog(null);
		fc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selection == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					System.out.println("loading " + file.getName());
				}
				else {
					System.out.println("load cancelled by user");
				}
			}
		});
		
		return in;
	}

	private JToolBar createJToolBar() {
		JToolBar toolBar = new JToolBar();
		
		JButton load = new JButton();
		load.setActionCommand("load");
		load.setToolTipText("Load a file");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//InputStream in =
				//_ctrl.reset();
				//_ctrl.loadEvents(in);
				//si el fichero no existe o loadEvents lanza una excepcion, muestra un
				//dialogo con un mensaje de error.
			}
		});
		load.setIcon(new ImageIcon("icons/open.png"));
		toolBar.add(load);
		
		JButton changeContClass = new JButton();
		changeContClass.setActionCommand("changeContClass");
		changeContClass.setToolTipText("Change CO2 class");
		changeContClass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ChangeCO2ClassDialog(_ctrl);
				//_ctrl.addEvent();
			}
		});
		changeContClass.setIcon(new ImageIcon("icons/co2class.png"));
		toolBar.add(changeContClass);
		
		JButton changeWeather = new JButton();
		changeWeather.setActionCommand("changeWeather");
		changeWeather.setToolTipText("Change road weather");
		changeWeather.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		changeWeather.setIcon(new ImageIcon("icons/weather.png"));
		toolBar.add(changeWeather);
		
		JButton run = new JButton();
		run.setActionCommand("run");
		run.setToolTipText("Run");
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		run.setIcon(new ImageIcon("icon/run.png"));
		toolBar.add(run);
		
		JButton stop = new JButton();
		stop.setActionCommand("stop");
		stop.setToolTipText("Stop");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		stop.setIcon(new ImageIcon("icon/stop.png"));
		toolBar.add(stop);
		
		JButton exit = new JButton();
		exit.setActionCommand("exit");
		exit.setToolTipText("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		exit.setIcon(new ImageIcon("icon/exit.png"));
		toolBar.add(exit);
		
		return toolBar;
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

	
	private void run_sim(int n) {
		if(n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
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
}
