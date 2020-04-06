package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

	Factory<LightSwitchStrategy> _lssFactory;
	Factory<DequeuingStrategy> _dqsFactory;
	
	public NewJunctionEventBuilder(String type, Factory<LightSwitchStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super(type);
		_lssFactory = lssFactory;
		_dqsFactory = dqsFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {

		int time = data.getInt("time");
		String id = data.getString("id");
		LightSwitchStrategy lss = _lssFactory.createInstance(data.getJSONObject("ls_strategy"));
		DequeuingStrategy dqs = _dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
		int xCoor = data.getJSONArray("coor").getInt(0);
		int yCoor = data.getJSONArray("coor").getInt(1);
		return new NewJunctionEvent(time, id, lss, dqs, xCoor, yCoor);
	}

}
