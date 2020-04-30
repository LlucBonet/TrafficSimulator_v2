package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
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
		_ctrl.addObserver(this);
		_junctions = null;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void update() {
		this.fireTableDataChanged();
	}
	
	public void setJunctionList(List<Junction> j) {
		_junctions = j;
		update();
	}

	@Override
	public String getColumnName(int col) {
		return _colNames[col];
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
			int index = _junctions.get(rowIndex).getGreenLightIndex();
			if(index == -1) o = "NONE";
			else
				o = _junctions.get(rowIndex).getInRoads().get(index);
			break;
		case 2:
			List<Road> lr = _junctions.get(rowIndex).getInRoads();
			o = "";
//			for(int i = 0;  i < lr.size(); i++) {
//			     o += lr.get(i).getId() + ":[";
//			    for(int j = 0; j < _junctions.get(rowIndex).getQueueByRoad().size(); j++) {
//			    	o += _junctions.get(rowIndex).getQueueByRoad().get(lr.get(i)).toString();
//			    }
//			}
			for(int i = 0; i < lr.size(); i++) {
				o += lr.get(i).getId() + ":" + _junctions.get(rowIndex).getQueueByRoad().get(lr.get(i));
			}
			break;
		}
		return o;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setJunctionList(map.getJunctions());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setJunctionList(map.getJunctions());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_junctions = new ArrayList<>();
	}

	@Override
	public void onError(String err) {}

}
