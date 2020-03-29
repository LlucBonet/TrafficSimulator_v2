package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {
	private Junction _srcJunc;
	private Junction _destJunc;
	private int _length;
	private int _maxSpeed;
	private int _speedLimit;
	private int _contLimit;
	private Weather _weather;
	private int _totalCont;
	private List<Vehicle> _vehicles;

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
		super(id);
		
		if(maxSpeed <= 0) throw new IllegalArgumentException("MAX SPEED <= 0 for road " + _id);
		else _maxSpeed = maxSpeed;
		if(length <= 0) throw new IllegalArgumentException("ROAD LENGTH <= 0 for road " + _id);
		else _length = length;
		if(contLimit < 0) throw new IllegalArgumentException("CONT LIMIT < 0 for road " + _id);
		else _contLimit = contLimit;
		if(srcJunc == null) throw new NullPointerException("source == null for road " + _id);
		else _srcJunc = srcJunc;
		if(destJunc == null) throw new NullPointerException("destination == null for road " + _id);
		else _destJunc = destJunc;
		if(weather == null) throw new NullPointerException("weatherCond == null  for road " + _id);
		else _weather = weather; 
		
		_speedLimit = _maxSpeed;
		_totalCont = 0;
		_vehicles = new ArrayList<>();
		
	}
	
	void enter(Vehicle v) throws IllegalArgumentException { //añade el vehiculo a la lista de vehiculos 
		if(v.getLocation() == 0 && v.getActualSpeed() == 0)
			_vehicles.add(v);
		else throw new IllegalArgumentException("Location or actual speed != 0 for vehicle " + v.getId());
	}
	
	void exit(Vehicle v) { //elimina el vehiculo de la lista de vehiculos
		_vehicles.remove(v);
	}
	
	void addContamination(int c) throws IllegalArgumentException {
		if(c >= 0)
			this._totalCont += c;
		else throw new IllegalArgumentException("contam < 0 for road " + _id);
	}
	
	//IMPLEMENTS SIMULATED OBJECT//
	@Override
	void advance(int time) throws Exception {
		if(this._totalCont > 2)
			reduceTotalContamination();
		updateSpeedLimit();
		for(Vehicle v : _vehicles) {
			if(v.getStatus() == VehicleStatus.TRAVELING)
				v.setActualSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		
		//ordenar lista de vehiculos por su localizacion
		Collections.sort(_vehicles);
	}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		
		obj.put("id", this._id);
		obj.put("speedlimit", this._speedLimit);
		obj.put("weather", this._weather);
		obj.put("co2", this._totalCont);
		for(int i = 0; i < _vehicles.size(); i++) {
			arr.put(_vehicles.get(i).getId());
		}
		obj.put("vehicles", arr); 
		
		return obj;
	}
	
	//GETTERS & SETTERS//
	void setWeather(Weather w) throws NullPointerException {
		if(w != null)
			this._weather = w;
		else throw new NullPointerException("weather == null  for road " + _id);
	}
	
	Weather getWeatherCond() {
		return this._weather;
	}
	
	int getTotalCont() {
		return this._totalCont;
	}
	
	void setTotalCont(int tc) {
		this._totalCont = tc;	
	}
	
	List<Vehicle> getVehicleList(){
		return Collections.unmodifiableList(new ArrayList<>(_vehicles));
	}
	
	int getSpeedLimit() {
		return this._speedLimit;
	}
	
	void setSpeedLimit(int sl) {
		this._speedLimit = sl;
	}
	
	int getLength() {
		return this._length;
	}
	
	Junction getSrc() {
		return this._srcJunc;
	}
	
	Junction getDest() {
		return this._destJunc;
	}

	int getContLimit() {
		return this._contLimit;
	}

	//ABSTRACT METHODS//
	abstract void reduceTotalContamination() throws Exception;
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	

}
