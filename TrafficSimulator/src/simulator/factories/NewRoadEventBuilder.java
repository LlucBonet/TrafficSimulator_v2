package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	NewRoadEventBuilder(String type) {
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String src = data.getString("src");
		String dest = data.getString("dest");
		int length = data.getInt("length");
		int co2limit = data.getInt("co2limit");		
		int maxspeed = data.getInt("maxspeed");
		String w = data.getString("weather").toUpperCase();
		Weather weather = Weather.valueOf(w);
		NewRoadEventBuilder be;
		if(this._type == "new_city_road") {
			be =  new NewCityRoadEventBuilder(_type);
			return be.createTheRoad(time, id, src, dest, length, co2limit, maxspeed, weather);
		}
		else {
			be = new NewInterCityRoadEventBuilder(_type);
			return be.createTheRoad(time, id, src, dest, length, co2limit, maxspeed, weather);
		}
		
	}
	
	
	abstract protected Event createTheRoad(int time, String id, String src, String dest,
			int length, int co2limit, int maxspeed, Weather weather);

}
