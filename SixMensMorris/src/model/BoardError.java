package model;

import java.util.ArrayList;

/**
 *	The BoardError class wraps relevant error data together after checks have been made, and provides public methods to access this data.
 */
public class BoardError {
	private int errType; // 0 = no error, 1 = piece collision, 2 = too many pieces
	private ArrayList<Piece> pieces; // The offending pieces
	// Shared by all BoardError objects, errorStates effectively passes on all previous data to a newly created BoardError object
	private static ArrayList<BoardError> errorStates = new ArrayList<BoardError>();
	
	/**
	 *	Constructs a BoardError object, which retains information from all previously created BoardError, appends new error data, and allows for error data access.
	 *	@param t Represents an int error value, where 0 is no error, 1 means a piece collision, and 2 means too many pieces.
	 *	@param pcs Represents an arraylist of pieces that have been determined to cause an error that should be dealt with.
	 */
	public BoardError(int t, ArrayList<Piece> pcs){
		errType = t;
		pieces = pcs;
		errorStates.add(this);
	}

	/**
	 *	@return An int error type: 0 is no error, 1 means a piece collision, and 2 means too many pieces.
	 */
	public int getType(){ return this.errType; }

	/**
	 *	@return An arraylist of pieces that have been determined to cause an error that should be dealt with.
	 */
	public ArrayList<Piece> getPieces() { return this.pieces; }

	/**
	 *	@return Returns an array of all BoardError states.
	 */
	public static ArrayList<BoardError> getErrors(){
		return errorStates;
	}
}
