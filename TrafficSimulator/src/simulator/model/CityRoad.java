package simulator.model;

public class CityRoad extends Road {

	public CityRoad(String id, Junction srcJunc, Junction destJunct, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
		super(id, srcJunc, destJunct, maxSpeed, contLimit, length, weather);
	}

	
	@Override
	void reduceTotalContamination() throws Exception {
		int x;
		
		if(this.getWeatherCond() == Weather.WINDY || this.getWeatherCond() == Weather.STORM)
			x = 10;
		else
			x = 2;
		if(this.getTotalCont() >= x)
			this.setTotalCont(this.getTotalCont() - x);
		else throw new Exception("TotalCont turns negative, CityRoad " + this._id);
	}

	@Override
	int calculateVehicleSpeed(Vehicle c) { 
		return (int) Math.ceil(((11.0-c.getContClass()) / 11.0) * this.getSpeedLimit());
	}

	@Override
	void updateSpeedLimit() {}

}
