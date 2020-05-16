package simulator.view;

import java.awt.Frame;

import simulator.model.Weather;
import simulator.model.Road;
import simulator.model.RoadMap;

public class ChangeWeatherDialog extends AddEventDialog<Road, Weather> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Weather[] _w = Weather.values();
	
	public ChangeWeatherDialog(Frame frame) {
		super(frame, "Change Weather");
		initGUI();
	}
		
	public void initGUI() {
		this.setLabels("Road: ", "Weather: ");
		for(int i = 0; i < _w.length; i++) {
			  this.get_toChange().addItem(_w[i]);
		  }	

	}
	
	protected int open(RoadMap map) {
		this.get_object().removeAllItems();
		for(Road r : map.getRoads()) {
			this.get_object().addItem(r);
		}	
		
		setLocation(getParent().getWidth() / 2, getParent().getHeight() / 2);
		setVisible(true);
		return this.getStatus();
	}
	
	protected Road getRoad() {
		return (Road) this.get_object().getSelectedItem();
		
	}
	
	protected Weather getWeather() {
		return (Weather) this.get_toChange().getSelectedItem();
	}
	
	protected int getTicks() {
		return this.get_ticks();
	}
}