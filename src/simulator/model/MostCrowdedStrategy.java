package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchStrategy {
	private int timeSlot;
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		int max = 0;
		int pos = -1;
		
		if(roads.isEmpty()) //caso 1
			return -1;
		
		if(currGreen == -1) { //caso 2
			for (int i = 0; i < qs.size(); i++) {
				if(qs.get(i).size() > max) {
					max = qs.get(i).size();
					pos = i;
				}
			}
			return pos;
		}
		if(currTime - lastSwitchingTime < timeSlot) //caso 3
			return currGreen;
		
		//caso 4
		for(int i = (currGreen + 1) % qs.size(); i < qs.size(); i++) {
			if(qs.get(i).size() > max) {
				max = qs.get(i).size();
				pos = i;
			}
		}
		return pos;
	}

}
