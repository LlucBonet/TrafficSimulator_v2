package simulator.view;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends TableModel<Junction> implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] _colNames = {"Id", "Green", "Queues"};
	private Controller _ctrl;
	
	public JunctionsTableModel(Controller ctrl) {
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
			int index = getList().get(rowIndex).getGreenLightIndex();
			if(index == -1) o = "NONE";
			else
				o = getList().get(rowIndex).getInRoads().get(index);
			break;
		case 2:
			List<Road> lr = getList().get(rowIndex).getInRoads();
			o = "";
			for(int i = 0; i < lr.size(); i++) {
				o += lr.get(i).getId() + ":" + getList().get(rowIndex).getQueueByRoad().get(lr.get(i));
			}
			break;
		}
		return o;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setList(map.getJunctions());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setList(map.getJunctions());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setList(map.getJunctions());
	}

	@Override
	public void onError(String err) {}

}
