package model;

/**
 * The class Piece encapsulates all the properties of a given Piece
 *
 */
public class Piece {

	private final boolean color;
	private int[] coordinate = new int[3];
	
	// Constructors
	public Piece(boolean color, int x, int y, int z){
		this.color = color;
		this.coordinate[0] = x;
		this.coordinate[1] = y;
		this.coordinate[2] = z;
		this.coordinate = checkCoordinates(this.coordinate);
	}
	public Piece(boolean color, int[] x){
		this.color = color;
		this.coordinate = x;
		this.coordinate = checkCoordinates(this.coordinate);
	}
	public Piece(boolean color){
		this.color = color;
		this.coordinate[0] = 0;
		this.coordinate[1] = 0;
		this.coordinate[2] = 0;
	}
	public Piece(){
		this.color = Board.RED;
		this.coordinate[0] = 0;
		this.coordinate[1] = 0;
		this.coordinate[2] = 0;
	}
	
	public boolean getColor(){
		return this.color;
	}
	public int[] getCoordinate(){
		return this.coordinate;
	}
	
	public void setCoordinate(int x, int y, int z){
		this.coordinate[0] = x;
		this.coordinate[1] = y;
		this.coordinate[2] = z;
		this.coordinate = checkCoordinates(this.coordinate);
	}
	public void setCoordinate(int[] x){
		this.coordinate = x;
	}
	
	/**
	 * In the case of an invalid coordinate array being passed
	 * move to the closest valid position
	 * @param coordinates to be tested
	 * @return valid coordinates from the original
	 */
	private int[] checkCoordinates(int[] coordinates){
		if (coordinates[0] == 1 && coordinates[1] == 1){ coordinates[0] = 0; coordinates[1] = 0; }
		for (int i = 0; i < 3; i++){
			if (coordinates[i] > 2) coordinates[i] = 2;
			if (coordinates[i] < 0) coordinates[i] = 0;
		}
		if (coordinates[2] > 1) coordinates[1] = 1;
		return coordinates;
	}
}
