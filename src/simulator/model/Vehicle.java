package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;


public class Vehicle extends SimulatedObject implements Comparable<Vehicle> {
	private String _id;
	private List<Junction> _itinerary;
	private int _maxSpeed;
	private int _actualSpeed;
	private VehicleStatus _vStatus;
	private Road _road;
	private int _location;
	private int _contClass;
	private int _totalCont;
	private int _totalDistance;
	private int _cont;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		
		_totalDistance = 0;
		_location = 0;
		_totalCont = 0;
		_actualSpeed = 0;
		_cont = 0;
		
		_vStatus = VehicleStatus.PENDING;
		_road = null;
		_id = id;
		
		if(maxSpeed < 0) throw new IllegalArgumentException("Invalid maxSpeed for vehicle " + _id);
		else _maxSpeed = maxSpeed;
		if(contClass < 0 || contClass > 10) throw new IllegalArgumentException("Invalid contClass for vehicle " + _id);
		else _contClass = contClass; 
		if(itinerary.size() < 2) throw new IllegalArgumentException("Invalid itinerary for vehicle" + _id);
		else _itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
	   
	}
	
	void moveToNextRoad() throws Exception {
		Junction src;
		Junction dest;
		if(this._vStatus == VehicleStatus.PENDING) {
			//primer cruce del itinerario
			src = _itinerary.get(_cont);
			_cont++;
			dest = _itinerary.get(_cont);
			_road = src.roadTo(dest);
			
			this._location = 0;
			this._actualSpeed = 0;

			_road.enter(this);
			this._vStatus = VehicleStatus.TRAVELING;

		}
		else if(this._vStatus == VehicleStatus.WAITING) { 
		//siguiente cruce
			_cont++;
			_road.exit(this);
			_location = 0;
			//_actualSpeed = 0;
			if(_cont >=  _itinerary.size()) {
				this._vStatus = VehicleStatus.ARRIVED;
			}
			else{
				src = _road.getDest();
				dest = _itinerary.get(_cont);
				_road = src.roadTo(dest);
				_road.enter(this);
				this._vStatus = VehicleStatus.TRAVELING;
			}
		
		}
		else 
			throw new IllegalArgumentException("Vehicle "+ _id + " is moving or has arrived.");
	}
	
	//IMPLEMENTS SIMULATED OBJECT//
	
	@Override
	void advance(int time) throws IllegalArgumentException { 
		if(this._vStatus == VehicleStatus.TRAVELING) {
			int l = this._location;
			int c;
			if(this._location + this._actualSpeed > this._road.getLength()) {
				this._location = this._road.getLength(); 
			}
			else {
				this._location += this._actualSpeed;
			}
			
			c = this._contClass * (this._location - l); //c = l*f
			this._totalCont += c;
			this._road.addContamination(c);
			
			if(this._location >= this._road.getLength()) { //vehiculo entra en cola del cruce correspondiente
				Junction j = this._road.getDest();
				j.enter(this);
				//this._cont++;
				this._vStatus = VehicleStatus.WAITING;
				this._actualSpeed = 0;
			}
			this._totalDistance += (this._location - l);
		}
	}

	@Override
	public JSONObject report() { //completo
		JSONObject obj = new JSONObject();
		
		obj.put("id", this._id);
		obj.put("speed", this._actualSpeed);
		obj.put("distance", this._totalDistance);
		obj.put("co2", this._totalCont);
		obj.put("class", this._contClass);
		obj.put("status", this._vStatus);
		if(this._vStatus != VehicleStatus.PENDING && this._vStatus != VehicleStatus.ARRIVED) {
			obj.put("road", this._road._id);
			obj.put("location", this._location);
		}
		
		return obj;
	}
	
	@Override
	public int compareTo(Vehicle o) { //debe estar siempre ordenada por la localizaci�n de los veh�culos
		//(orden descendente). Observa que puede haber varios veh�culos en la misma
//		localizaci�n. Sin embargo, su orden de llegada a esa localizaci�n debe preservarse
//		en la lista
		if(this._location > o._location) return -1;
		if(this._location < o._location) return 1;
		return 0;
	}
	
	//GETTERS & SETTERS//
	void setActualSpeed(int s) throws IllegalArgumentException {
		if(s < 0) throw new IllegalArgumentException("Illegal speed (> 0) for vehicle " + this._id);

		if(s < this._maxSpeed) {
			this._actualSpeed = s;
		}
		else this._actualSpeed = this._maxSpeed;
	}
	
	int getActualSpeed() {
		return this._actualSpeed;
	}
	
	void setContClass(int c) throws Exception {
		if(c >= 0 && c <= 10) {
			this._contClass = c;
		}
		else throw new IllegalArgumentException("Illegal ContClass for vehicle" + this._id);	
	}
	
	int getContClass(){
		return this._contClass;
	}
	
	int getLocation() {
		return this._location;
	}
	
	Road getRoad() {
		return this._road;
	}
	
	VehicleStatus getStatus() {
		return this._vStatus;
	}
	
	List<Junction> getItinerary(){
		return this._itinerary;
	}
}
