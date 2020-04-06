package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewVehicleEvent extends Event {

	protected String _id;
	protected int _maxSpeed;
	protected int _contClass;
	protected List<String> _itinerary;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);

		_id = id;
		_maxSpeed = maxSpeed;
		_contClass = contClass;
		_itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		
	}

	@Override
	void execute(RoadMap map) throws Exception {
		List<Junction> itinerary = new ArrayList<Junction>(_itinerary.size());
		for(String juncId : _itinerary) {
			itinerary.add(map.getJunction(juncId));
		}
		Vehicle v = new Vehicle(_id, _maxSpeed, _contClass, itinerary);
		map.addVechicle(v);
		v.moveToNextRoad();
	}

}
