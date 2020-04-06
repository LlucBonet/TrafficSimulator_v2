package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {
	
	protected List<Pair<String, Integer>> _cs;

	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if(cs == null) 
			throw new IllegalArgumentException("cs == null in SetContClassEvent class");
		_cs = cs;
	}

	@Override
	void execute(RoadMap map) throws Exception {
	

		for(Pair<String, Integer> c : _cs) {
			Vehicle v = map.getVehicle(c.getFirst());
			if(v == null) 
				throw new Exception("vehicle " + c.getFirst() + " doesn't exist in map");
			v.setContClass(c.getSecond());
		}
	}

}
