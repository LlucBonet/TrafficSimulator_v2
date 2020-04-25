package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller _ctrl;
	private List<Event> _events;
	private String[] _colNames = {"Time", "Desc."};
	
	public EventsTableModel(Controller ctrl) {
		_ctrl = ctrl;
		_events = null;
	}
	
	public void update() {
		fireTableDataChanged();
	}

	public void setEventList(List<Event> e) {
		_events = e;
		update();
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	@Override
	public int getRowCount() {
		return _events == null ? 0 : _events.size();
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
			o = _events.get(rowIndex).getTime();
			break;
		case 1:
			o = _events.get(rowIndex).toString();
			break;
		}
		return o;
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

}
