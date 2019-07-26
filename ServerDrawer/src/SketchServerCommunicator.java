import java.awt.Color;
import java.io.*;
import java.net.Socket;

/**
 * Handles communication between the server and one client, for SketchServer
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate SketchServerCommunicator
 */
public class SketchServerCommunicator extends Thread {
	private Socket sock;					// to talk with client
	private BufferedReader in;				// from client
	private PrintWriter out;				// to client
	private SketchServer server;			// handling communication for
	//private int ID = 0;

	public SketchServerCommunicator(Socket sock, SketchServer server) {
		this.sock = sock;
		this.server = server;
	}

	/**
	 * Sends a message to the client
	 * @param msg
	 */
	public void send(String msg) {
		out.println(msg);
	}
	
	/**
	 * Keeps listening for and handling (your code) messages from the client
	 */
	public void run() {
		try {
			System.out.println("someone connected");
			
			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);

			// Tell the client the current state of the world
			// TODO: YOUR CODE HERE
			
			for(int id: server.getSketch().getMap().navigableKeySet()) {
				send("DR " + server.getSketch().getMap().get(id).toString() + " " + id);
			}

			
			// Keep getting and handling messages from the client
			// TODO: YOUR CODE HERE
			
			String command = "";
			
			while((command = in.readLine()) != null) {
				String[] commands = command.split(" ");
				
				if(commands[0].equals("DR")) {
					Shape shape = null;
					if(commands[1].equals("ellipse")) {
						Color color = new Color(Integer.parseInt(commands[6]));
						shape = new Ellipse(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]), Integer.parseInt(commands[5]), color);
					}
					else if(commands[1].equals("rectangle")) {
						Color color = new Color(Integer.parseInt(commands[6]));
						shape = new Rectangle(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]), Integer.parseInt(commands[5]), color);
					}
					else if(commands[1].equals("segment")) {
						Color color = new Color(Integer.parseInt(commands[6]));
						shape = new Segment(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]), Integer.parseInt(commands[5]), color);
					}
					int ID = server.getID();
					server.getSketch().add(ID, shape);
					server.broadcast(command + " " + ID);
				}
				
				if(commands[0].equals("M")) {
					server.getSketch().getMap().get(Integer.parseInt(commands[1])).moveBy(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]));
					server.broadcast(command);
				}
				
				if(commands[0].equals("R")) {
					server.getSketch().getMap().get(Integer.parseInt(commands[1])).setColor(new Color(Integer.parseInt(commands[2])));
					server.broadcast(command);
				}
				
				if(commands[0].equals("D")) {
					server.getSketch().delete(Integer.parseInt(commands[1]));
					server.broadcast(command);
				}
			
			}

			// Clean up -- note that also remove self from server's list so it doesn't broadcast here
			server.removeCommunicator(this);
			out.close();
			in.close();
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
