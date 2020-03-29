package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchStrategy> {

	public MostCrowdedStrategyBuilder(String type) {
		super(type);
	}

	@Override
	protected LightSwitchStrategy createTheInstance(JSONObject data) {
		if(data.has("timeslot")) {
			return new MostCrowdedStrategy(data.getInt("timeslot"));
		}
		return new MostCrowdedStrategy(1); // 1 es el default timeslot
	}

}
