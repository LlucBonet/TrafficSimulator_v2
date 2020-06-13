package simulator.view;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends TableModel<Vehicle> implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller _ctrl;
	private String[] _colNames = {"Id", "Location", "Itinerary", "CO2 Class.", "Max. Speed", "Speed", "Total CO2", "Distance"};
	
	public VehiclesTableModel(Controller ctrl) {
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
			o = "";
			VehicleStatus vs = getList().get(rowIndex).getStatus();
			switch(vs) {
			case PENDING:
				o = "Pending";
				break;
			case TRAVELING:
				o += getList().get(rowIndex).getRoad().toString() 
				  + ":" + getList().get(rowIndex).getLocation();
				break;
			case WAITING:
				o = "Waiting:" + getList().get(rowIndex).getRoad().getDest();
				break;
			case ARRIVED:
				o = "Arrived";
				break;
			}
			break;
		case 2:
			o = getList().get(rowIndex).getItinerary().toString();
			break;
		case 3:
			o = getList().get(rowIndex).getContClass();
			break;
		case 4:
			o = getList().get(rowIndex).getMaxSpeed();
			break;
		case 5:
			o = getList().get(rowIndex).getActualSpeed();
			break;
		case 6:
			o = getList().get(rowIndex).getTotalCont();
			break;
		case 7:
			o = getList().get(rowIndex).getTotalDistance();
			break;
	}
		return o;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setList(map.getVehicles());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setList(map.getVehicles());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setList(map.getVehicles());
	}

	@Override
	public void onError(String err) {}

}
