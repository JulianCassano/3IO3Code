package model;

/**
 * The class NodeCreator contains static methods to define the 
 * properties of the BoardLayout and the associated Nodes
 *
 */
public class NodeCreator {

    // These will be assigned values in terms of pixels
	final static int WINDOW_HEIGHT;
	final static int WINDOW_WIDTH;

	final static int BOARD_SIZE;
	final static int TILE_SIZE;
	final static int START_WIDTH;
	final static int START_HEIGHT;


	static {
		// Set default values for int variables relating to drawing window and board assets; in units of pixels.
		WINDOW_HEIGHT = 720;
		WINDOW_WIDTH = 720;
		BOARD_SIZE = 6*WINDOW_HEIGHT/10;
		TILE_SIZE = 75;

		assert(WINDOW_HEIGHT == WINDOW_WIDTH);		// The window and more specifically board as well should be square		

		START_WIDTH = (WINDOW_WIDTH - BOARD_SIZE)/2 - WINDOW_WIDTH/TILE_SIZE;
		START_HEIGHT = (WINDOW_HEIGHT - BOARD_SIZE)/2 - WINDOW_HEIGHT/TILE_SIZE;			

		createNodes();
		defineConnections();		
	}

		//Numbering for Nodes starts at 0 in top left outer square, goes clockwise, then inner square and repeat
	    // Current 2D pixel location will be with respect to the upper left of the window.
	    // Only applies for typical Six Men's Morris setup.
		//Coordinates stored in array {x,y,z} x(row)={0,1,2}, y(column)={0,1,2}, z(inner/outer)={0,1}
	private static void createNodes() {
		
		// Top row: Evenly distributes space for the three nodes along the top of the board. 
		//          Node 0 is along the left of the board, node 2 is on the right, and node 1 is halfway between the two.
		for (int x = START_WIDTH, y = 0; x <= START_WIDTH + BOARD_SIZE; x+=BOARD_SIZE/2, y++){
			BoardLayout.setNode(y, x, START_HEIGHT, new int[]{2,(x-START_WIDTH) / (BOARD_SIZE/2),1});
		}
		// Middle right: Place node 3 halfway up the window along the right of the board.
		BoardLayout.setNode(3, START_WIDTH + BOARD_SIZE, START_HEIGHT + BOARD_SIZE/2, new int[]{1,2,1});

		// Bottom row: Evenly distributes space for the three nodes along the bottom of the board. 
		//             Node 6 is along the left of the board, node 4 is on the right, and node 5 is halfway between the two.
		for (int x = START_WIDTH + BOARD_SIZE, y = 4; x >= START_WIDTH; x-=BOARD_SIZE/2, y++){
			BoardLayout.setNode(y, x, START_HEIGHT + BOARD_SIZE, new int[]{0,(x-START_WIDTH) / (BOARD_SIZE/2),1});
		}
		// Middle left: Place node 7 halfway up the window, along the left of the board.
		BoardLayout.setNode(7, START_WIDTH, START_HEIGHT + BOARD_SIZE/2, new int[]{1,0,1});

		// Inside top row: Evenly distributes space for the three nodes 1/4 the board height from the top edge of the board. 
		//                 Node 8 is 1/4 the width of the board from the left edge of the board, node 10 is the same distance from the right 
		//                 edge of the board, and node 9 is halfway between the two.
		for (int x = START_WIDTH + BOARD_SIZE/4, y = 8; x <= START_WIDTH + 3*BOARD_SIZE/4; x += BOARD_SIZE/4, y++){
			BoardLayout.setNode(y, x, START_HEIGHT + BOARD_SIZE/4, new int[]{2,((x-START_WIDTH) / (BOARD_SIZE/4)) - 1, 0});
		}
		// Inside middle right: Place node 11 halfway up the window, 1/4 the width of the board from the right edge of the board.
		BoardLayout.setNode(11, START_WIDTH + 3*BOARD_SIZE/4, START_HEIGHT + BOARD_SIZE/2, new int[]{1,2,0});

		// Inside bottom row: Evenly distributes space for the three nodes 1/4 the board height from the bottom edge of the board. 
		//                    Node 14 is 1/4 the width of the board from the left edge of the board, node 12 is the same distance from the right 
		//                    edge of the board, and node 13 is halfway between the two.
		for (int x = START_WIDTH + 3*BOARD_SIZE/4, y = 12; x >= START_WIDTH + BOARD_SIZE/4; x -= BOARD_SIZE/4, y++){
			BoardLayout.setNode(y, x, START_HEIGHT + 3*BOARD_SIZE/4, new int[]{0,((x-START_WIDTH) / (BOARD_SIZE/4)) - 1, 0});
		}
		// Inside middle left: Place node 15 halfway up the window, 1/4 the width of the board from the left edge of the board.
		BoardLayout.setNode(15, START_WIDTH + BOARD_SIZE/4, START_HEIGHT + BOARD_SIZE/2, new int[]{1,0,0});	
	}
	
	/**
	 * Defines all the connections of the nodes on the board
	 */
	static void defineConnections() {		
		for (int x = 0; x < 8; x++){			
			if (x == 7) {
				BoardLayout.addConnection(x, 0);
				BoardLayout.addConnection(x+8, x);
			} else {
				BoardLayout.addConnection(x, x+1);
				BoardLayout.addConnection(x+7, x+8);
			}
		}
		for (int x = 1; x <= 7; x+=2) BoardLayout.addConnection(x, x+8);
	}
	
}