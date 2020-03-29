package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {

	private TrafficSimulator _trafficSimulator;
	private Factory<Event> _eventFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventFactory) {
		if(sim == null)
			throw new IllegalArgumentException("sim == null in Controller constructor");
		else 
			_trafficSimulator = sim;
		if(eventFactory == null)
			throw new IllegalArgumentException("eventFactory == null in Controller constructor");
		else 
			_eventFactory = eventFactory;
	}

	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray events = jo.getJSONArray("events");
		
		for(int i = 0; i < events.length(); i++) {
			_trafficSimulator.addEvent(_eventFactory.createInstance(events.getJSONObject(i)));
		}
	}
	
	public void run(int n, OutputStream out) throws Exception {
		PrintStream p = new PrintStream(out);
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		for(int i = 0; i < n; i++) {
			_trafficSimulator.advance();
			arr.put(_trafficSimulator.report());
		}
		obj.put("states", arr);
		p.println(obj.toString());
		p.close();
	}
	
	public void reset() {
		_trafficSimulator.reset();
	}
}
