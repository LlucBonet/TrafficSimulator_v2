package simulator.view;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends TableModel<Event> implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller _ctrl;
	private String[] _colNames = {"Time", "Desc."};
	
	public EventsTableModel(Controller ctrl) {
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}

	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o = null;
		switch(columnIndex) {
		case 0:
			o = getList().get(rowIndex).getTime();
			break;
		case 1:
			o = getList().get(rowIndex).toString();
			break;
		}
		return o;
	}

	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		update();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		setList(events);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_list.clear();
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setList(events);
	}

	@Override
	public void onError(String err) {}

}
