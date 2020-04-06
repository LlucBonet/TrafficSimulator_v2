package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> _inRoad; //lista de todas las carreteras q entran al cruce. El cruce es su destino
	private Map<Junction,Road> _outRoad; //mapa de carreteras que salen de este cruce y entran en el cruce Junction del mapa
	private List<List<Vehicle>> _queues; //lista de los vehiculos esperando en el cruce
	private Map<Road,List<Vehicle>> _queueByRoad;
	private int _greenLightIndex;
	private int _lastSwitchingTime;
	private LightSwitchStrategy _lss;
	private DequeuingStrategy _dqs;
	private int _xCoor, _yCoor;
	
	public Junction(String id, LightSwitchStrategy lsStrategy, DequeuingStrategy dqStrategy, 
			int xCoor, int yCoor){
		super(id);

		if(lsStrategy == null) 
			throw new IllegalArgumentException("Invalid value for the light switching strategy of junction " + _id);
		else _lss = lsStrategy;
		
		if(dqStrategy == null) 
			throw new IllegalArgumentException("Invalid value for the dequeuing strategy of junction" + _id);
		else _dqs = dqStrategy;
		
		if(xCoor < 0 || yCoor < 0) 
			throw new IllegalArgumentException("Invalid value for the coordinates of junction" + _id); 
		else {
			_xCoor = xCoor;
			_yCoor = yCoor;
		}
		
		_greenLightIndex = -1;
		_lastSwitchingTime = 0;
		_inRoad = new ArrayList<>();
		_queues = new ArrayList<>();
		_outRoad = new HashMap<>();
		_queueByRoad = new HashMap<>();
	}

	void addIncomingRoad(Road r) throws IllegalArgumentException {
		if(!this.equals(r.getDest())) //si el destino de r no coincide con este cruce
			throw new IllegalArgumentException("addIncomingRoad() to junction "+ _id + ". This road's dest is not this junction");
		
		List<Vehicle> q = new ArrayList<Vehicle>(); 
		_inRoad.add(r);
		_queues.add(q);
		_queueByRoad.put(r, q);
	}
	
	void addOutGoingRoad(Road r) throws IllegalArgumentException {
		Junction j = r.getDest();
		for(Map.Entry<Junction, Road> entry : _outRoad.entrySet()) {
			if(entry.getValue().getSrc() == this && entry.getKey() == j) {
				throw new IllegalArgumentException("addOutGoingRoad from junction" + _id + ". This road already exists");
			}
		}
		if(!(r.getSrc() == this)) 
			throw new IllegalArgumentException("addOutgoingRoad from juction " + _id + ": road doesn't belong to this juction");
		_outRoad.put(j, r);
	}
	
	void enter(Vehicle v) {
		Road r = v.getRoad();
	
		for(Map.Entry<Road, List<Vehicle>> entry : _queueByRoad.entrySet()) {
			if(entry.getKey().equals(r)) {
				entry.getValue().add(v);
				break;
			}
		}
	}
	
	Road roadTo(Junction j) { 
		Road r = null;
		for(Map.Entry<Junction, Road> entry : _outRoad.entrySet()) {
			if(entry.getValue().getSrc() == this && entry.getKey() == j) {
				r = entry.getValue();
				return r;
			}
		}
		return r;
	}
	
	//IMPLEMENTS SIMULATED OBJECT//
	@Override
	void advance(int time) throws Exception {
	
		if(_greenLightIndex != -1) {
			//Calcula la lista de vehiculos que deben avanzar a su siguiente carretera
			List<Vehicle> vl = _dqs.dequeue(_queueByRoad.get(_inRoad.get(_greenLightIndex)));
			for(int i = 0; i < vl.size(); i++) {
				vl.get(i).moveToNextRoad();
				//_dqs.dequeue(_queueByRoad.get(_inRoad.get(_greenLightIndex))).remove(vl.get(i));
				_queueByRoad.get(_inRoad.get(_greenLightIndex)).remove(vl.get(i));
			}
		}
		//Calcula el indice de la siguiente carretera a la que hay que poner su semaforo en verde
		int indice = _lss.chooseNextGreen(_inRoad, _queues, _greenLightIndex, _lastSwitchingTime, time);
		if(indice != _greenLightIndex) {
			_greenLightIndex = indice;
			_lastSwitchingTime = time;
		}
	}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		

		obj.put("id", _id);
		if(_greenLightIndex == -1) {
			obj.put("green", "none");
		}
		else {
			obj.put("green", _inRoad.get(_greenLightIndex).getId());
		}
		
		for(int i = 0; i < _inRoad.size(); i++) {
			JSONObject obj2 = new JSONObject();
			List<String> v = new ArrayList<>();
 			obj2.put("road", _inRoad.get(i).getId());
 			
 			for(int j = 0; j < _queueByRoad.get(_inRoad.get(i)).size(); j++) {
 				v.add(_queueByRoad.get(_inRoad.get(i)).get(j).getId());
	
 			}
			obj2.put("vehicles", v);
			arr.put(obj2);
		}		
		obj.put("queues", arr);

		return obj;
	}
	
	
	//GETTERS & SETTERS//
	int get_xCoor() {
		return this._xCoor;
	}
	
	int get_yCoor() {
		return this._yCoor;
	}
}
