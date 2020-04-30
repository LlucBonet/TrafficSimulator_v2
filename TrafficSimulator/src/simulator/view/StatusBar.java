package simulator.view;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Controller _ctrl;
	JTextArea info = new JTextArea("Info: ");
	JLabel _time;

	public StatusBar(Controller ctrl) {
		_ctrl = ctrl;
		_ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		_time = new JLabel("Time: " + 0);
		_time.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 35));
		_time.setFont(new Font("time.font", Font.PLAIN, 14));
		
		this.add(_time);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		info.setBackground(this.getBackground());
		info.setEditable(false);
		info.setWrapStyleWord(true);
		info.setLineWrap(true);
		info.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		info.setFont(new Font("info.font", Font.PLAIN, 14));
		this.add(info);
	}
	
	public void setInfo(String infoGame) {
		info.setText("Info: " + infoGame);
	}

	//IMPLEMENTS TRAFFICSIMOBSERVER
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_time.setText("Time: " + time);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {}
	
	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		info.setText(e.toString());
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		info.setText("Info: Nuevo fichero cargado");
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onError(String err) {
		info.setText(err);
		info.setForeground(Color.RED);
	}

	
}
