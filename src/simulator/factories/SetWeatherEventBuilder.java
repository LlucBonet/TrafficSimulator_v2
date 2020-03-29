package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder(String type) {
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		List<Pair<String, Weather>> ws = new ArrayList<>();
		JSONArray infoArray = new JSONArray();
		infoArray = data.getJSONArray("info");
		String w, r;
		Weather weather;
		Pair<String, Weather> p;
		for(int i = 0; i < infoArray.length(); i++) {
			w = infoArray.getJSONObject(i).getString("weather").toUpperCase();
			weather = Weather.valueOf(w);
			r = infoArray.getJSONObject(i).getString("road");
			p = new Pair<String, Weather>(r, weather);
			ws.add(p);
		}
		
		return new SetWeatherEvent(time, ws);
	}

}
