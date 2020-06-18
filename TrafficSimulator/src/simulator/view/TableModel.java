//package simulator.view;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import javax.swing.table.AbstractTableModel;
//
//
//abstract public class TableModel<T> extends AbstractTableModel{
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	
//	protected List<T> _list;
//	
//	public TableModel() {
//		_list = new ArrayList<>();
//	}
//	
//	@Override
//	public boolean isCellEditable(int row, int column) {
//		return false;
//	}
//	
//	@Override
//	public int getRowCount() {
//		return getList() == null ? 0 : getList().size();
//	}
//	public void update() {
//		fireTableDataChanged();
//	}
//
//	public void setList(List<T> e) {
//		_list = e;
//		update();
//	}
//	
//	public List<T> getList(){
//		return Collections.unmodifiableList(_list);
//	}
//	
////	@Override
////	abstract public String getColumnName(int col);
////	
////	@Override
////	abstract public int getColumnCount();
////	
////	@Override
////	abstract public Object getValueAt(int rowIndex, int columnIndex);
//
//}
