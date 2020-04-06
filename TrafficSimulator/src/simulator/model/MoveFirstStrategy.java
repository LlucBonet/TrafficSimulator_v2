package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	public MoveFirstStrategy() {
	}

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) { //devuelve una lista con el primer elemento de q
		List<Vehicle> vl = new ArrayList<Vehicle>();	
		if(!(q.isEmpty())) 
			vl.add(q.get(0));
		return vl;
	}

}
