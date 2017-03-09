package model;

import java.util.ArrayList;

public class BoardError {
	private int errType; // 0 = no error, 1 = piece collision, 2 = too many pieces
	private ArrayList<Piece> pieces; // The offending pieces
	private static ArrayList<BoardError> errorStates = new ArrayList<BoardError>();
	
	public BoardError(int t, ArrayList<Piece> pcs){
		errType = t;
		pieces = pcs;
		errorStates.add(this);
	}
	
	public int getType(){ return this.errType; }
	public ArrayList<Piece> getPieces() { return this.pieces; }
	public static ArrayList<BoardError> getErrors(){
		return errorStates;
	}
}
