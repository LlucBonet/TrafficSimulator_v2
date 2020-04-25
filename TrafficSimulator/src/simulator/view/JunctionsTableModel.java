package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] _colNames = {"Id", "Green", "Queues"};
	private Controller _ctrl;
	private List<Junction> _junctions;
	
	public JunctionsTableModel(Controller ctrl) {
		_ctrl = ctrl;
		_junctions = null;
	}
	
	public void update() {
		this.fireTableDataChanged();
	}
	
	public void setJunctionList(List<Junction> j) {
		_junctions = j;
		update();
	}

	@Override
	public int getRowCount() {
		return _junctions == null ? 0 : _junctions.size();
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
			o = _junctions.get(rowIndex).getId();
			break;
		case 1:
			o = _junctions.get(rowIndex).getGreenLightIndex();
			break;
		case 2:
			o = _junctions.get(rowIndex).getQueueByRoad();
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
