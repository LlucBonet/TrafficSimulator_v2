package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewInterCityRoadEventBuilder(String type) {
		super(type);
	}

	@Override
	protected Event createTheRoad(int time, String id, String src, String dest, int length, int co2limit, int maxspeed,
			Weather weather) {

		return new NewInterCityRoadEvent(time, id, src, dest, length, co2limit, maxspeed, weather);
	}

}
