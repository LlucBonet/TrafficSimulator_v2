package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller _ctrl;
	private List<Road> _roads;
	private String[] _colNames = {"Id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"}; 
	
	public RoadsTableModel(Controller ctrl) {
		_ctrl = ctrl;
		_roads = null;
	}

	public void update() {
		fireTableDataChanged();
	}
	
	public void setRoadList(List<Road> r) {
		_roads = r;
		update();
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	@Override
	public int getRowCount() {
		return _roads == null ? 0 : _roads.size();
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
			o = _roads.get(rowIndex).getId();
		case 1:
			o = _roads.get(rowIndex).getLength();
		case 2:
			o = _roads.get(rowIndex).getWeatherCond();
		case 3:
			o = _roads.get(rowIndex).getMaxSpeed();
		case 4:
			o = _roads.get(rowIndex).getSpeedLimit();
		case 5:
			o = _roads.get(rowIndex).getTotalCont();
		case 6:
			o = _roads.get(rowIndex).getContLimit();
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