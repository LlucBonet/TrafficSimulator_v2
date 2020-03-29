package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	public MoveAllStrategy() {
		
	}

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> vl = Collections.unmodifiableList(new ArrayList<Vehicle>(q));
		return vl;
	}

}
