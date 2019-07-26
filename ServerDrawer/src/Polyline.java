import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 */
public class Polyline implements Shape {
	private List<Segment> poly;
	private Color color;
	
	public Polyline(int x1, int y1, Color color) {
		poly = new ArrayList<Segment>();
		Segment segment = new Segment(x1, y1, color);
		poly.add(segment);
		this.color = color;
	}
	
	public Polyline(int x1, int y1, int x2, int y2, Color color) {
		poly = new ArrayList<Segment>();
		Segment segment = new Segment(x1, y1, x2, y2, color);
		poly.add(segment);
		this.color = color;

	}
	
	@Override
	public void moveBy(int dx, int dy) {
		for(Segment segment: poly) {
			segment.moveBy(dx, dy);
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		for(Segment segment: poly) {
			segment.setColor(color);
		}
	}
	
	@Override
	public boolean contains(int x, int y) {
		for(Segment segment: poly) {
			if(segment.contains(x, y)) return true;
		}
		return false;
		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		for(Segment segment: poly) {
			segment.draw(g);
		}
	}
	


	@Override
	public String toString() {
		String s = "freehand ";
		for(Segment segment: poly) {
			s += segment.toString();
		}
		return "polyline " + s + color.getRGB();		
		
	}

}
