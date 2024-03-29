package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	protected RoadMap _map;
	protected List<Event> _eventList;
	protected int _simulatedTime;
	protected List<TrafficSimObserver> _observers;
	
	public TrafficSimulator() {
		_map = new RoadMap();
		_eventList = new SortedArrayList<Event>();
		_simulatedTime = 0;
		_observers = new ArrayList<TrafficSimObserver>();
	}
	
	
	public void addEvent(Event e) {
		_eventList.add(e);
		for(TrafficSimObserver ob : _observers) {
			ob.onEventAdded(_map, _eventList, e, _simulatedTime);
		}
	}
	
	public void advance() throws Exception {
		
		_simulatedTime++; //paso 1
		
		for(TrafficSimObserver ob : _observers) {
			ob.onAdvanceStart(_map, _eventList, _simulatedTime);
		}
		
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
		
		for(TrafficSimObserver ob : _observers) {
			ob.onAdvanceEnd(_map, _eventList, _simulatedTime);
		}
		try{
			Thread.sleep(150);
		}catch(InterruptedException e){
			for(TrafficSimObserver ob : _observers) {
				ob.onError(e.getLocalizedMessage());
			}
		}
	}
	
	public void reset() {
		_map.reset();
		_eventList.clear();
		_simulatedTime = 0;
		for(TrafficSimObserver ob : _observers) {
			ob.onReset(_map, _eventList, _simulatedTime);
		}
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
		_observers.add(o);
		for(TrafficSimObserver ob : _observers) {
			ob.onRegister(_map, _eventList, _simulatedTime);
		}
	}
	@Override
	public void removeObserver(TrafficSimObserver o) {
		_observers.remove(o);
	}
	
	//GETTERS && SETTERS
	public int getSimulatedTime() {
		return _simulatedTime;
	}
}
