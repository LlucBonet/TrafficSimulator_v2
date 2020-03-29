package simulator.model;

public class NewJunctionEvent extends Event {
	protected String _id;
	
	protected LightSwitchStrategy _lss;
	protected DequeuingStrategy _dqs;
	
	protected int _xCoor, _yCoor;

	public NewJunctionEvent(int time, String id, LightSwitchStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(time);
		_id = id;
		_lss = lsStrategy;
		_dqs = dqStrategy;
		_xCoor = xCoor;
		_yCoor = yCoor;
	}

	@Override
	void execute(RoadMap map) {
		map.addJunction(new Junction(_id, _lss, _dqs, _xCoor, _yCoor));
	}

}
