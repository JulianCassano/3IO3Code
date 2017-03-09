package model;

import java.util.ArrayList;
import java.util.Random;


public class Board {
	// Board constants
	public static final boolean RED = true;
	public static final boolean BLUE = false;
	private static final int MAXPIECES = 6;

	protected static Node[] nodes = new Node[16];

	private static Boolean[][] threeInARow = new Boolean[4][2]; //First index indicates which side of the square
	// has 3-in-a-row, starting with 0 at the top
	// and numbered clockwise. 2nd index = 0 for inner square
	// True for Red, False for Blue, null for neither

	private static Boolean[][][] position = new Boolean[3][3][2]; //[x][y][z] Holds the color of the piece at this position
	// null when no piece is at that position
	private static ArrayList<Piece> pieces = new ArrayList<Piece>();	// Collection of pieces on the board
	private static int numPieces = 0;
	private static boolean turn;	// Represents whose turn it is

	private static int maxRed; //Max number of red pieces that can be on the board
	private static int maxBlue; // Max number of blue pieces that can be on the board


	// Randomly decide first turn on construction
	public Board(){
		Random random = new Random();
		Board.turn = random.nextBoolean();
		maxRed = MAXPIECES;
		maxBlue = MAXPIECES;
	}

	// Method to be called when adding pieces to the board
	// See how coordinates have been defined in the Piece class
	public void addPiece(boolean color, int[] coordinate){
		int x = coordinate[0];
		int y = coordinate[1];
		int z = coordinate[2];
		pieces.add(new Piece(color,coordinate));
		position[x][y][z] = color;
		numPieces++;
	}
	public void addPiece(boolean color, int x, int y, int z){
		int[] coordinate = new int[3];
		coordinate[0] = x;
		coordinate[1] = y;
		coordinate[2] = z;
		pieces.add(new Piece(color,coordinate));
		position[x][y][z] = color;
		numPieces++;		
	}


	// Method to be called when clicking on pieces on the board
	public static Piece getPiece(int[] coordinate){		
		for (int i = 0; i < pieces.size(); i++){			
			int[] pc = pieces.get(i).getCoordinate();
			if (pc[0] == coordinate[0] && pc[1] == coordinate[1] && pc[2] == coordinate[2])
				return pieces.get(i);
		}
		return null; // Return null if there is no piece on that part of the board.
	}


	// Make sure the board setup is legal
	public BoardError validSetup(){
		ArrayList<Piece> errPieces = new ArrayList<Piece>();

		// First make sure no two pieces occupy the same space
		for (int i = 0; i < pieces.size() - 1; i++){
			for (int j = i+1; j < pieces.size(); j++){
				int[] x = new int[3], y = new int[3];
				for(int k = 0; k < 3; k++){
					x[k] = pieces.get(i).getCoordinate()[k];
					y[k] = pieces.get(j).getCoordinate()[k];
				}
				if (x[0] == y[0] && x[1] == y[1] && x[2] == y[2]){
					// If there are pieces in the same spot, add them
					if (!errPieces.contains(pieces.get(i))) errPieces.add(pieces.get(i));
					if (!errPieces.contains(pieces.get(j))) errPieces.add(pieces.get(j));
				}
			}
		}
		if (errPieces.size() > 0) return new BoardError(1,errPieces); // Return error type along with erroneous pieces

		// Count how many three-in-a-rows each side has
		updateThreeInARow();
		int red3inARowCount = 0;
		int blu3inARowCount = 0;
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 2; j++){
				if (threeInARow[i][j] != null){
					if (threeInARow[i][j] == RED) {
						red3inARowCount++;
					}
					else if (threeInARow[i][j] == BLUE) {
						blu3inARowCount++;
					}
				}
			}
		}

		// Update the max number of pieces each side can have respectively
		maxRed -= blu3inARowCount;
		maxBlue -= red3inARowCount;

		// Make sure neither side exceeds their max amount 
		int redCnt = 0, bluCnt = 0;
		for (Piece piece : pieces){
			if (piece.getColor() == RED) redCnt++;
			else bluCnt++;
		}
		if (redCnt > maxRed || bluCnt > maxBlue) {
			if (redCnt > maxRed){
				for(Piece piece : pieces){
					if (piece.getColor() == RED) errPieces.add(piece);
				}
			}
			if (bluCnt > maxBlue){
				for(Piece piece : pieces){
					if (piece.getColor() == BLUE) errPieces.add(piece);
				}
			}
		}
		if (errPieces.size() > 0) return new BoardError(2,errPieces);

		return new BoardError(0,null); // In case of no error
	}

	// Update the existence of three-in-a-rows
	// Have this return Boolean[][] so that it can be compared to the previous state to see if 
	// any of the three in rows are new
	private void updateThreeInARow(){
		for (int i = 0; i < 3; i+=2){
			for (int j = 0; j < 2; j++){
				// Take care of horizontal three-in-rows
				if (position[i][0][j] != null && position[i][1][j] != null && position[i][2][j] != null){
					if (position[i][0][j].equals(position[i][1][j]) && position[i][1][j].equals(position[i][2][j])){
						threeInARow[2-i][j] = new Boolean(position[i][0][j].booleanValue());
					}
				}
				else threeInARow[2-i][j] = null;

				// take care of vertical three-in-rows
				if (position[0][i][j] != null && position[1][i][j] != null && position[2][i][j] != null){
					if (position[0][i][j].equals(position[1][i][j]) && position[1][i][j].equals(position[2][i][j])){
						threeInARow[3-i][j] = new Boolean(position[i][0][j].booleanValue());
					}
				}
				else threeInARow[3-i][j] = null;
			}
		}
	}

	public static int getNumPieces(){
		return numPieces;
	}

	public static void resetBoard(){
		threeInARow = new Boolean[4][2];
		position = new Boolean[3][3][2];
		pieces = new ArrayList<Piece>();
		numPieces = 0;
	}

	public static boolean getTurn(){
		return turn;
	}

	public static void switchTurn(){
		turn = !turn;
	}

	/**
	 * main() method to be used for unit testing of this module
	 */
	public static void main(String[] args) {
		Board board = new Board();
		board.addPiece(RED,0,0,0);
		board.addPiece(RED,1,0,0);
		board.addPiece(RED,2,0,0);
		board.addPiece(BLUE,0,0,1);
		board.addPiece(BLUE,0,2,1);
		board.addPiece(BLUE,2,2,1);
		board.addPiece(BLUE,2,0,1);
		board.addPiece(BLUE,2,1,0);
		board.addPiece(BLUE,0,1,0);

		ArrayList<Piece> pcs = Board.pieces;
		int[] x = {2,0,1};
		Piece pc = Board.getPiece(x);

		BoardError be = board.validSetup();
		System.out.println(be.getType());
	}	
}