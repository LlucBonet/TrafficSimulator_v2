package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

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
		onEventAdded(_map, _eventList, e, _simulatedTime);
	}
	
	public void advance() throws Exception {
		
		_simulatedTime++; //paso 1
		
		onAdvanceStart(_map, _eventList, _simulatedTime);
		
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
		
		onAdvanceEnd(_map, _eventList, _simulatedTime);
	}
	
	public void reset() {
		_map.reset();
		_eventList.clear();
		_simulatedTime = 0;
		onReset(_map, _eventList, _simulatedTime);
	}
	
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		JSONObject obj2 = _map.report();
		
		obj.put("time", _simulatedTime);
		obj.put("state", obj2);
		
		return obj;
	}

	//IMPLEMENTS OBSERVABLE<TRAFFICSIMOBSERVER>
	@Override
	public void addObserver(TrafficSimObserver o) {
		
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		
	}
}
