package simulator.model;

public class InterCityRoad extends Road {
	int contLimit;
	int maxSpeed;
	Weather weather;

	InterCityRoad(String id, Junction srcJunc, Junction destJunct, int maxSpeed, int contLimit, Weather weather, int length) throws Exception {
		super(id, srcJunc, destJunct, maxSpeed, contLimit, length, weather);

		this.contLimit = contLimit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}

	@Override
	void reduceTotalContamination() {
		int x = 0; 
		int c;
		
		if(this.weather == Weather.SUNNY) 
			x = 2;
		else if(this.weather == Weather.CLOUDY)
			x = 3;
		else if(this.weather == Weather.RAINY)
			x = 10;
		else if(this.weather == Weather.WINDY)
			x = 15;
		else if(this.weather == Weather.STORM)
			x = 20;
		
		c = (int) (((100.0 - x)/100.0) * getTotalCont());
	
		setTotalCont(c);
	}

	@Override
	int calculateVehicleSpeed(Vehicle c) {//Devuelve la velocidad limite de la carretera
		if(this.getWeatherCond() == Weather.STORM) {
			return (int)(this.getSpeedLimit()*0.8);
		}
		else return (int)(this.getSpeedLimit());
	}
	
	void updateSpeedLimit(){
		if(getTotalCont() > this.contLimit) {
			this.setSpeedLimit((int)(maxSpeed*0.5)); 
		}
		else {
			this.setSpeedLimit(maxSpeed);
		}
	}

}
