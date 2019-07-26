import java.awt.Color;
import java.io.*;
import java.net.Socket;

/**
 * Handles communication to/from the server for the editor
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor;		// handling communication for

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4242);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}
	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			// Handle messages
			// TODO: YOUR CODE HERE
			String command = "";
			while((command = in.readLine()) != null) {
				String[] commands = command.split(" ");
				if(commands[0].equals("DR")) {
					if(commands[1].equals("ellipse")) {
						
						Color color = new Color(Integer.parseInt(commands[6]));
						Shape ellipse = new Ellipse(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]), Integer.parseInt(commands[5]), color);
						//editor.getSketch().add(ellipse);
						editor.getSketch().add(Integer.parseInt(commands[7]), ellipse);
					}
					else if(commands[1].equals("rectangle")) {
						Color color = new Color(Integer.parseInt(commands[6]));
						Shape rectangle = new Rectangle(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]), Integer.parseInt(commands[5]), color);
						//editor.getSketch().add(rectangle);
						editor.getSketch().add(Integer.parseInt(commands[7]), rectangle);
					}
					else if(commands[1].equals("segment")) {
						Color color = new Color(Integer.parseInt(commands[6]));
						Shape segment = new Segment(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]), Integer.parseInt(commands[5]), color);
						//editor.getSketch().add(segment);
						editor.getSketch().add(Integer.parseInt(commands[7]), segment);
					}
					
				}
				
				if(commands[0].equals("M")) {
					editor.getSketch().getMap().get(Integer.parseInt(commands[1])).moveBy(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]));
				}
				
				if(commands[0].equals("R")) {
					editor.getSketch().getMap().get(Integer.parseInt(commands[1])).setColor(new Color(Integer.parseInt(commands[2])));
				}
				
				if(commands[0].equals("D")) {
					editor.getSketch().delete(Integer.parseInt(commands[1]));
				}
				editor.edRepaint();
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("server hung up");
		}
	}	

	// Send editor requests to the server
	// TODO: YOUR CODE HERE
	
}
