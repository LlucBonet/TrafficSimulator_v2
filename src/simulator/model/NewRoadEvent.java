package simulator.model;

public abstract class NewRoadEvent extends Event {

	public NewRoadEvent(int time) {
		super(time);
	}

	@Override
	void execute(RoadMap map) throws Exception {
		Road r = createRoadObject(map);
		map.addRoad(r);
	}
	
	abstract Road createRoadObject(RoadMap map) throws Exception; //he a�adido RoadMap map 

}
