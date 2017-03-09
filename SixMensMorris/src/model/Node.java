package model;

import java.util.ArrayList;

/**
 * The class Node represents a valid position for a piece
 * on the game board.
 * <br>
 * Node provides similar usage to that of Piece objects but
 * each node also includes a list of connections 
 *
 */
public class Node {

	private final int x;
	private final int y;	
	private final int position;
	private final int[] map = new int[3];
	
	private ArrayList<Integer> connections = new ArrayList<Integer>();	
	
	/**
	 * @param position location on board using clockwise grid starting at top left
	 * and going from outer to inner frame
	 * @param x coordinate of the node in respect to the window (column)
	 * @param y coordinate of the node in respect to the window (row)
	 * @param joints list of nodes this Node is connected to
	 */
	public Node(int position, int x, int y, ArrayList<Integer> joints){
		this.x = x;
		this.y = y;
		this.position = position;
		this.setConnections(joints);
	}
	
	/**
	 * @param position location on board using clockwise grid starting at top left
	 * and going from outer to inner frame
	 * @param x coordinate of the node in respect to the window (column)
	 * @param y coordinate of the node in respect to the window (row)
	 * @param coordinates position on the board with respect to Piece coordinate system
	 */
	public Node(int position, int x, int y, int[] coordinates){
		this.x = x;
		this.y = y;
		this.position = position;
		
		for(int i = 0; i < 3; i++) map[i] = coordinates[i];		
	}
	
	/**
	 * @param position position location on board using clockwise grid starting at top left
	 * and going from outer to inner frame
	 * @param x coordinate of the node in respect to the window (column)
	 * @param y coordinate of the node in respect to the window (row)
	 */
	public Node(int position, int x, int y){
		this.x = x;
		this.y = y;
		this.position = position;
	}
	
	/**
	 * Adds a new connection to the list of active connections
	 * @param pos position of connected Node
	 */
	public void addConnection(int pos){
		getConnections().add(pos);
	}
	
	/**
	 * @return column of node with respect to screen
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * @return row of node with respect to screen
	 */
	public int getY(){
		return this.y;
	}
	
	/**
	 * @return position on board in terms of Piece coordinate system
	 */
	public int[] getCoords(){
		return this.map;
	}
	
	/**
	 * Checks if two Nodes are connected
	 * @param first Node to be checked
	 * @param second Node to be checked against
	 * @return true is the Nodes are connected, otherwise false
	 */
	public static boolean isConnected(Node first, Node second){
				
		for (int i = 0; i < first.getConnections().size(); i++){			
			if (first.getConnections().get(i) == second.getPosition()) return true;			
		}
		return false;
	}	
	
	/**
	 * @return position of the Node in terms of Piece coordinate system
	 */
	private int getPosition() {
		return this.position;
	}

	/**
	 * @return list of connections that this Node has
	 */
	public ArrayList<Integer> getConnections() {
		return connections;
	}

	/**
	 * Defines the list of connections that this Node has
	 * @param connections Overwrite connections of the current Piece
	 */
	public void setConnections(ArrayList<Integer> connections) {
		this.connections = connections;
	}
}