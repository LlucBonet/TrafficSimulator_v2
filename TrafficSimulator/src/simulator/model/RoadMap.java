package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	private List<Junction> _junctionList;
	private List<Road> _roadList;
	private List<Vehicle> _vehicleList;
	private Map<String, Junction> _junctionMap;
	private Map<String, Road> _roadMap;
	private Map<String, Vehicle> _vehicleMap;
	
	RoadMap(){
		_junctionList = new ArrayList<>();
		_roadList = new ArrayList<>();
		_vehicleList = new ArrayList<>();
		_junctionMap = new HashMap<>();
		_roadMap = new HashMap<>();
		_vehicleMap = new HashMap<>();
		
	}
	
	void addJunction(Junction j) {
		for(int i = 0; i < _junctionList.size(); i++) {
			if(_junctionList.get(i).getId() == j.getId())
				throw new IllegalArgumentException("AddJuction() in RoadMap: juction " + j.getId() +" already exists in _juctionList.");
		}
		_junctionList.add(j);
		_junctionMap.put(j.getId(), j);
	}
	
	void addRoad(Road r) {
		boolean src = false;
		boolean dest = false;
		for(int i = 0; i < _roadList.size(); i++) { //Comprueba que no existe aun la carretera a añadir
			if(_roadList.get(i).getId() == r.getId())
				throw new IllegalArgumentException("AddRoad() in RoadMap: road " + r.getId() +" already exists in _roadList.");
		}
		for(Map.Entry<String, Junction> entry : _junctionMap.entrySet()) {//comprueba si existen src y dest en _juctionMap
			if(r.getSrc() == entry.getValue())
				src = true;
			else if(r.getDest() == entry.getValue())
				dest = true;
		}
		if(!src || !dest)
			throw new IllegalArgumentException("AddRoad() in RoadMap: dest juction or source junction from road " + r.getId() + "don't exist in _juctionMap");
		
		r.getSrc().addOutGoingRoad(r);
		r.getDest().addIncomingRoad(r);
		_roadList.add(r);
		_roadMap.put(r.getId(), r);
		
	}
	
	void addVechicle(Vehicle v) {
		for(int i = 0; i < _vehicleList.size(); i++) {
			if(_vehicleList.get(i).getId() == v.getId())
				throw new IllegalArgumentException("AddVehicle() in RoadMap: vehicle " + v.getId() +" already exists in _vehicleList.");
		}
		for(int i = 0; i < v.getItinerary().size()- 1; i++) {
			if(v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1)) == null)
				throw new IllegalArgumentException("AddVehicle() in RoadMap: there aren't any roads connecting junctions " + v.getItinerary().get(i) + 
						" and " + v.getItinerary().get(i+1));
		}
		
		_vehicleList.add(v);
		_vehicleMap.put(v.getId(), v);
	}
	
	//GETTERS & SETTERS//
	public Junction getJunction(String id) {
		return _junctionMap.get(id);
		
	}
	
	public Road getRoad(String id) {
		return _roadMap.get(id);
	}
	
	public Vehicle getVehicle(String id) {
		return _vehicleMap.get(id);
	}
	
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(new ArrayList<>(_junctionList));
	}
	
	public List<Road> getRoads(){
		return Collections.unmodifiableList(new ArrayList<>(_roadList));
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(new ArrayList<>(_vehicleList));
	}
	
	void reset() {
		_junctionList.clear();
		 _roadList.clear();
		 _vehicleList.clear();
		 _junctionMap.clear();
		_roadMap.clear();
		_vehicleMap.clear();
	}
	
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		JSONArray arrJ = new JSONArray();
		JSONArray arrR = new JSONArray();
		JSONArray arrV = new JSONArray();

		
		for(int i = 0; i < _junctionList.size(); i++) {
			arrJ.put(_junctionList.get(i).report());
		}
		obj.put("junctions", arrJ);
		
		for(int i = 0; i < _roadList.size(); i++) {
			arrR.put(_roadList.get(i).report());
		}
		obj.put("roads", arrR);
		
		for(int i = 0; i < _vehicleList.size(); i++) {
			arrV.put(_vehicleList.get(i).report());
		}
		obj.put("vehicles", arrV);
		return obj;
	}
}
