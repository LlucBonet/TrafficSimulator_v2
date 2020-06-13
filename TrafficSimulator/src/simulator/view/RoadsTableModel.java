package simulator.view;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends TableModel<Road> implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller _ctrl;
	private String[] _colNames = {"Id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"}; 
	
	public RoadsTableModel(Controller ctrl) {
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
			o = getList().get(rowIndex).getId();
			break;
		case 1:
			o = getList().get(rowIndex).getLength();
			break;
		case 2:
			o = getList().get(rowIndex).getWeatherCond().toString();
			break;
		case 3:
			o = getList().get(rowIndex).getMaxSpeed();
			break;
		case 4:
			o = getList().get(rowIndex).getSpeedLimit();
			break;
		case 5:
			o = getList().get(rowIndex).getTotalCont();
			break;
		case 6:
			o = getList().get(rowIndex).getContLimit();
			break;
		}
		return o;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setList(map.getRoads());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setList(map.getRoads());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setList(map.getRoads());
	}

	@Override
	public void onError(String err) {}

}
