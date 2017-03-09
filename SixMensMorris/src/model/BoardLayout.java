package model;

import java.awt.Point;

public class BoardLayout extends Board{
	
	private BoardLayout(){};
	
	public static void setNode(int index, int x, int y, int[] coords){
		Board.nodes[index] = new Node(index, x, y, coords);
	}
	
	public static int getNodeX(int index){
		return Board.nodes[index].getX();
	}
	
	public static int getNodeY(int index){
		return Board.nodes[index].getY();
	}
	
	public static int[] getNodePosition(int index){
		return Board.nodes[index].getCoords();
	}
	
	public static boolean isConnected(int first, int second){
		return Node.isConnected(Board.nodes[first], Board.nodes[second]);
	}
	
	public static void addConnection(int x, int y){
		addConnection(Board.nodes[x], Board.nodes[y], x, y);
	}	
	
	private static void addConnection(Node first, Node second, int x, int y){
		first.addConnection(y);
		second.addConnection(x);
	}
	
	public static int distanceTo(int index, Point p){
		return (int) Math.sqrt(Math.pow(Board.nodes[index].getX() - p.getX(), 2) + Math.pow(Board.nodes[index].getY() - p.getY(), 2));
	}	
}
