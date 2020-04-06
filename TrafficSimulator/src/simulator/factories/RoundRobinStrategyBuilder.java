package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchStrategy> {

	public RoundRobinStrategyBuilder(String type) {
		super(type);

	}

	@Override
	protected LightSwitchStrategy createTheInstance(JSONObject data) {
		if(data.has("timeslot")) {
			return new RoundRobinStrategy(data.getInt("timeslot"));
		}
		return new RoundRobinStrategy(1); // 1 es el default timeslot
	}

}
