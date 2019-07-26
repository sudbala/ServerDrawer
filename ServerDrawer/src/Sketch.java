import java.util.TreeMap;

public class Sketch {
	public TreeMap<Integer, Shape> shapes;
	private int id;
	
	public Sketch() {
		shapes = new TreeMap<Integer, Shape>();
		id = 0;
	}
	
	public void add(Shape shape) {
		shapes.put(id, shape);
		id++;
	}
	
	public void add(int id, Shape shape) {
		shapes.put(id, shape);
	}
	
	public void delete(int id) {
		shapes.remove(id);
		System.out.println("BUREE");
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public TreeMap<Integer, Shape> getMap(){
		return shapes;
	}
	
	public String toString() {
		return shapes.toString();
	}
	
	
	

}
