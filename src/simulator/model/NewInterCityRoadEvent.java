package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {

	protected String _id;
	protected String _srcJ;
	protected String _destJ;
	protected int _length;
	protected int _co2Limit;
	protected int _maxSpeed;
	protected Weather _weather;
	
	public NewInterCityRoadEvent(int time, String id, String srcJun, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		_id = id;
		_srcJ = srcJun;
		_destJ = destJunc;
		_length = length;
		_co2Limit = co2Limit;
		_maxSpeed = maxSpeed;
		_weather = weather;
	}

	@Override
	Road createRoadObject(RoadMap map) throws Exception {
		return new InterCityRoad(_id, map.getJunction(_srcJ), map.getJunction(_destJ), _maxSpeed, _co2Limit, _weather, _length);
	}
}
