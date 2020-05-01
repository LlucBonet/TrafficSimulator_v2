package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int _JRADIUS = 10;
	
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private static final Color _ROAD_COLOR = Color.BLACK;
	
	private RoadMap _map;
	private Image _car;
	private Image[] _cont = new Image[6];
	private Image[] _weather = new Image[5];
	
	public MapByRoadComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
		setPreferredSize(new Dimension(300, 200));
	}

	public void initGUI() {
		_car = loadImage("car.png");
		for(int i = 0; i < _cont.length; i++) {
			_cont[i] = loadImage("cont_" + i + ".png");
		}
		_weather[0] = loadImage("cloud.png");
		_weather[1] = loadImage("rain.png");
		_weather[2] = loadImage("storm.png");
		_weather[3] = loadImage("sun.png");
		_weather[4] = loadImage("wind.png");	
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
		//	updatePrefferedSize();
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics g) {
		for(int i = 0; i < _map.getRoads().size(); i++) {
			int x1 = 50;
			int x2 = getWidth()-100;
			int y = (i+1)*50;
			
			//dibuja las carreteras
			g.setColor(_ROAD_COLOR);
			g.drawLine(x1, y, x2, y);
			
			//dibuja los cruces
			drawJunctions(g, _map.getRoads().get(i).getSrc(), _map.getRoads().get(i).getDest(), i, x1, x2, y);

			//dibuja los vehiculos
			drawVehicles(g, _map.getRoads().get(i), x1, x2, y);
			
			//dibuja weather
			switch(_map.getRoads().get(i).getWeatherCond()) {
			case CLOUDY:
				drawWeather(g, _weather[0], x2, y);
				break;                      
			case RAINY:                     
				drawWeather(g, _weather[1], x2, y);
				break;                      
			case STORM:                    
				drawWeather(g, _weather[2], x2, y);
				break;                     
			case SUNNY:                     
				drawWeather(g, _weather[3], x2, y);
				break;                      
			case WINDY:                     
				drawWeather(g, _weather[4], x2, y);
				break;
			}
			
			//dibuja CO2 Class
			 int c = (int) Math.floor(Math.min((double) _map.getRoads().get(i).getTotalCont()/(1.0 + (double) _map.getRoads().get(i).getContLimit()),1.0) / 0.19);
			 drawCO2Class(g, _cont[c], x2, y);
		}
	}

	private void drawVehicles(Graphics g, Road r, int x1, int x2, int y) {
		for(Vehicle v : r.getVehicleList()) {
			if(v.getStatus() != VehicleStatus.ARRIVED) {
				g.setColor(Color.BLACK);
				int x = x1 + (int)((x2 - x1) * ((double) v.getLocation()/ (double) r.getLength()));
				g.drawImage(_car, x, y - 10, 16, 16, this);
				g.drawString(v.getId(), x, y - 8);
			}
		}
	}
	
	private void drawJunctions(Graphics g, Junction src, Junction dest, int i, int x1, int x2, int y) {
		g.setColor(_JUNCTION_COLOR);
		g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString(src.getId(), x1, y - _JRADIUS);
		if(dest.getGreenLightIndex() == i) {
			g.setColor(_GREEN_LIGHT_COLOR);
		}
		else {
			g.setColor(_RED_LIGHT_COLOR);
		}
		g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString(src.getId(), x2, y - _JRADIUS);
	}		
	
	private void drawWeather(Graphics g, Image img, int x, int y) {
		g.drawImage(img, x + 10, y - 16, 32, 32, this);
	}
	
	private void drawCO2Class(Graphics g, Image img, int x, int y) {
		g.drawImage(img, x + 50, y-16, 32, 32, this);
	}
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		}catch(IOException e) {
		}
		return i;
	}
	public void update(RoadMap map) {
		_map = map;
		repaint();
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {}

}
