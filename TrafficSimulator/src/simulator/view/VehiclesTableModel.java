package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller _ctrl;
	private List<Vehicle> _vehicles;
	private String[] _colNames = {"Id", "Location", "Itinerary", "CO2 Class.", "Max. Speed", "Speed", "Total CO2", "Distance"};
	
	public VehiclesTableModel(Controller ctrl) {
		_ctrl = ctrl;
		_vehicles = null;
	}

	public void update() {
		fireTableDataChanged();
	}
	
	public void setRoadList(List<Vehicle> v) {
		_vehicles = v;
		update();
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	
	@Override
	public int getRowCount() {
		return _vehicles == null ? 0 : _vehicles.size();
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
			o = _vehicles.get(rowIndex).getId();
			break;
		case 1:
			o = _vehicles.get(rowIndex).getLocation();
			break;
		case 2:
			o = _vehicles.get(rowIndex).getItinerary();
			break;
		case 3:
			o = _vehicles.get(rowIndex).getContClass();
			break;
		case 4:
			o = _vehicles.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			o = _vehicles.get(rowIndex).getActualSpeed();
			break;
		case 6:
			o = _vehicles.get(rowIndex).getTotalCont();
			break;
		case 7:
			o = _vehicles.get(rowIndex).getTotalDistance();
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
