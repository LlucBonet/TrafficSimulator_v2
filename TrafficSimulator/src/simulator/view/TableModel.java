//package simulator.view;
//
//import java.util.List;
//
//import javax.swing.table.AbstractTableModel;
//
//import simulator.control.Controller;
//import simulator.model.Event;
//import simulator.model.Junction;
//import simulator.model.RoadMap;
//import simulator.model.SimulatedObject;
//import simulator.model.TrafficSimObserver;
//
//public class TableModel<T extends SimulatedObject> extends AbstractTableModel implements TrafficSimObserver {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	private String[] _colNames;
//	private Controller _ctrl;
//	private List<T> _list;
//	
//	
//	public TableModel(Controller ctrl) {
//		_ctrl = ctrl;
//		_ctrl.addObserver(this);
//		_list = null;
//	}
//
//	@Override
//	public boolean isCellEditable(int row, int column) {
//		return false;
//	}
//	
//	public void update() {
//		this.fireTableDataChanged();
//	}
//	
//	public void setList(List<T> l) {
//		_list = l;
//		update();
//	}
//	
//	public void setColumnName(String[] colNames) {
//		_colNames = colNames;
//	}
//	
//	@Override
//	public String getColumnName(int col) {
//		return _colNames[col];
//	}
//	
//	@Override
//	public int getRowCount() {
//		return _list == null ? 0 : _list.size();
//	}
//
//	@Override
//	public int getColumnCount() {
//		return _colNames.length;
//	}
//
//	@Override
//	public Object getValueAt(int rowIndex, int columnIndex) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}
//
//	@Override
//	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onReset(RoadMap map, List<Event> events, int time) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onRegister(RoadMap map, List<Event> events, int time) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onError(String err) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
