package simulator.view;

import java.awt.Frame;

import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends AddEventDialog<Vehicle, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public ChangeCO2ClassDialog(Frame frame) {
		super(frame, "Change CO2 Class");
		initGUI();
	}
	
	public void initGUI() {
		this.setLabels("Vehicle: ", "CO2 Class: ");
		for(int i = 1; i <= 10; i++) {
			  this.get_toChange().addItem(i);
		  }
	}
	
	
	protected int open(RoadMap map) {
		this.get_object().removeAllItems();
		for(Vehicle v : map.getVehicles()) {
			this.get_object().addItem(v);
		}	
		setLocation(getParent().getWidth() / 2, getParent().getHeight() / 2);
		setVisible(true);
		return this.getStatus();
	}
	
	protected Vehicle getVehicle() {
		return (Vehicle) this.get_object().getSelectedItem();
		
	}
	
	protected int getCO2Class() {
		return (int) this.get_toChange().getSelectedItem();
	}
	
	protected int getTicks() {
		return this.get_ticks();
	}
	
	protected void changeLabels(String object, String value) {
		this.setLabels(object, value);
	}
}
