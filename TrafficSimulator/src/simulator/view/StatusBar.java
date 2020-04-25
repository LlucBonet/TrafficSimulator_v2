package simulator.view;

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

	public StatusBar(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JLabel time = new JLabel("Time: " + _ctrl.getSimulatedTime());
		time.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 35));
		time.setFont(new Font("time.font", Font.PLAIN, 14));
		
		this.add(time);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		info.setBackground(this.getBackground());
		info.setEditable(false);
		info.setWrapStyleWord(true);
		info.setLineWrap(true);
		info.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		info.setFont(new Font("info.font", Font.PLAIN, 14));
		this.add(info);
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

	public void setInfo(String infoGame) {
		info.setText("Info: " + infoGame);
	}
}
