package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	protected List<Pair<String, Weather>> _ws;
	
	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		
		if(ws == null) 
			throw new IllegalArgumentException ("ws == null in SetWeatherEvent class");
		_ws = ws;
	}

	@Override
	void execute(RoadMap map) throws Exception {
		for(Pair<String, Weather> w : _ws) {
			Road r = map.getRoad(w.getFirst());
			if(r == null) 
				throw new Exception("road " + w.getFirst() + " doesn't exist in map");
			r.setWeather(w.getSecond());
		}
	}

}
