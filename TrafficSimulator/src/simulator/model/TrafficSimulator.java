package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	protected RoadMap _map;
	protected List<Event> _eventList;
	protected int _simulatedTime;
	
	
	public TrafficSimulator() {
		_map = new RoadMap();
		_eventList = new SortedArrayList<Event>();
		_simulatedTime = 0;
	}
	
	public void addEvent(Event e) {
		_eventList.add(e);
	}
	
	public void advance() throws Exception {
		
		_simulatedTime++; //paso 1
		
		while (!(_eventList.isEmpty()) && _eventList.get(0).getTime() == _simulatedTime) {//j < _eventList.size()
			_eventList.get(0).execute(_map);
			_eventList.remove(0);
			
		}// paso 2
		
		for(int i = 0; i < _map.getJunctions().size(); i++) {
			_map.getJunctions().get(i).advance(_simulatedTime);
		}//paso 3
		
		for(int i = 0; i < _map.getRoads().size(); i++) {
			_map.getRoads().get(i).advance(_simulatedTime);
		}//paso 4
	}
	
	public void reset() {
		_map.reset();
		_eventList.clear();
		_simulatedTime = 0;
	}
	
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		JSONObject obj2 = _map.report();
		
		obj.put("time", _simulatedTime);
		obj.put("state", obj2);
		
		return obj;
	}
}
