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

	@Override
	public String toString() {
		String text = "Change CO2 Class [";
		for(int i = 0; i < _cs.size(); i++) {
			text += "(" + _cs.get(i).getFirst() + ", " + _cs.get(i).getSecond() + ")";
			if(i < _cs.size()-1) text += ", ";
		}
	
		text += "]";
		return text;
	}
}
