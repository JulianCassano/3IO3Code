package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * The class GameInterface is responsible for displaying the primary
 * graphics window containing the game board
 *
 */
public class GameInterface extends JPanel {

	final static int WINDOW_HEIGHT;
	final static int WINDOW_WIDTH;

	final static int BOARD_SIZE;
	final static int TILE_SIZE;
	final static int START_WIDTH;
	final static int START_HEIGHT;	
	final static int RADIUS;

	static {	

		WINDOW_HEIGHT = 720;
		WINDOW_WIDTH = 720;
		BOARD_SIZE = 6*WINDOW_HEIGHT/10;
		TILE_SIZE = 75;
		RADIUS = 40;

		assert(WINDOW_HEIGHT == WINDOW_WIDTH);			//The window and board should be square		
		assert(new File("resources/texture2.bmp").exists());
		assert(new File("resources/texture3.bmp").exists());

		START_WIDTH = (WINDOW_WIDTH - BOARD_SIZE)/2 - WINDOW_WIDTH/TILE_SIZE;
		START_HEIGHT = (WINDOW_HEIGHT - BOARD_SIZE)/2 - WINDOW_HEIGHT/TILE_SIZE;			

	}	

	/**
	 * Instantiates the GameInterface
	 */
	public GameInterface(){		
	}		

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);
		drawBoard(g);		
		drawTemplate(g);
		drawNodes(g);
		drawPieces(g);
	}

	/**
	 * Triggers redrawing of the window if any components have changed
	 */
	public void update(){
		super.repaint();
	}	

	/**
	 * Draws the background for the window
	 * @param g java.awt.Graphics obejct
	 */
	private void drawBackground(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		BufferedImage backImage;

		try {		
			backImage = ImageIO.read(new File("resources/texture2.bmp"));
		} catch (IOException e) {			
			System.out.println("Error: Image files missing from resource");
			e.printStackTrace();
			return;
		}

		g2d.setPaint(new TexturePaint(backImage, new Rectangle(75,75)));		
		g2d.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
	}

	/**
	 * Draws the game board on the screen
	 * @param g java.awt.Graphics object
	 */
	private void drawBoard(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;								

		BufferedImage boardImage;
		try {
			boardImage = ImageIO.read(new File("resources/texture3.bmp"));
		} catch (IOException e) {
			System.out.println("Error: image file missing from resources");
			e.printStackTrace();
			return;
		}

		g2d.setPaint(new TexturePaint(boardImage, new Rectangle(TILE_SIZE,TILE_SIZE)));
		g2d.fillRect(START_WIDTH, START_HEIGHT, BOARD_SIZE, BOARD_SIZE);
	}

	/**
	 * Calls methods to draw all of the nodes and connections on the board
	 * @param g java.awt.Graphics object
	 */
	private void drawNodes(Graphics g){
		Graphics2D g2d = (Graphics2D) g;

		drawCircles(g2d);
		drawOutlines(g2d);		
		drawOuterLines(g2d);
		drawInnerLines(g2d);
		drawJointLines(g2d);
	}

	/**
	 * Draws stock pieces on the top and bottom of the board 
	 * @param g java.awt.Graphics object
	 */
	private void drawTemplate(Graphics g){
		
		for (int x = -3; x < 3; x++){
			g.setColor(Color.RED);
			g.fillOval(START_WIDTH + BOARD_SIZE/2 + (int)(x*(1.1*RADIUS)), START_HEIGHT - (int)(2.6*RADIUS), RADIUS, RADIUS);
			g.setColor(Color.BLUE);
			g.fillOval(START_WIDTH + BOARD_SIZE/2 + (int)(x*(1.1*RADIUS)), START_HEIGHT + BOARD_SIZE + RADIUS, RADIUS, RADIUS);
		}
	}
	
	/**
	 * Draws the nodes on the board
	 * @param g java.awt.Graphics2D object
	 */
	private void drawCircles(Graphics2D g){
		Color cream = new Color (255,229,204);
		g.setColor(cream);
		for (int i = 0; i < 16; i++){
			g.fillOval(model.BoardLayout.getNodeX(i) - RADIUS/2, model.BoardLayout.getNodeY(i) - RADIUS/2, RADIUS, RADIUS);
		}
	}

	/**
	 * Draws outlines for each of the nodes on the board
	 * @param g
	 */
	private void drawOutlines(Graphics2D g){
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(4.0f));
		for (int i = 0; i < 16; i++){
			g.drawOval(model.BoardLayout.getNodeX(i) - RADIUS/2, model.BoardLayout.getNodeY(i) - RADIUS/2, RADIUS, RADIUS);
		}
	}


	/**
	 * Draws connections on the outer frame of the board
	 * @param g java.awt.Graphics2D object
	 */
	private void drawOuterLines(Graphics2D g){
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(5.0f));

		for (int x = START_WIDTH; x < START_WIDTH + BOARD_SIZE; x+=BOARD_SIZE/2){
			for (int y = START_HEIGHT; y < START_HEIGHT + BOARD_SIZE; y+=BOARD_SIZE){	
				g.drawLine(x + RADIUS/2, y, x + BOARD_SIZE/2 - RADIUS/2, y);
			}
		}	
		for (int x = START_WIDTH; x <= START_WIDTH + BOARD_SIZE; x+=BOARD_SIZE){
			for (int y = START_HEIGHT; y < START_HEIGHT + BOARD_SIZE; y+=BOARD_SIZE/2){			 
				g.drawLine(x, y + RADIUS/2, x, y + BOARD_SIZE/2 - RADIUS/2);
			}
		}
	}

	/**
	 * Draws connections on the inner frame of the board
	 * @param g java.awt.Graphics2D object
	 */
	private void drawInnerLines(Graphics2D g){
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(5.0f));

		for (int x = START_WIDTH + BOARD_SIZE/4; x < START_WIDTH + 3*BOARD_SIZE/4; x += BOARD_SIZE/4){	
			for (int y = START_HEIGHT + BOARD_SIZE/4; y <= START_HEIGHT + 3*BOARD_SIZE/4; y+= BOARD_SIZE/2){
				g.drawLine(x + RADIUS/2, y, x + BOARD_SIZE/4 - RADIUS/2, y);				
			}
		}
		for (int x = START_WIDTH + BOARD_SIZE/4; x < START_WIDTH + BOARD_SIZE; x+= BOARD_SIZE/2){
			for (int y = START_HEIGHT + BOARD_SIZE/4; y < START_HEIGHT + 3*BOARD_SIZE/4; y+= BOARD_SIZE/4){
				g.drawLine(x, y + RADIUS/2, x, y + BOARD_SIZE/4 - RADIUS/2);
			}
		}
	}

	/**
	 * Draws connections joining the inner and outer frame of the board
	 * @param g java.awt.Graphics2D object
	 */
	private void drawJointLines(Graphics2D g){
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(5.0f));

		for (int x = START_WIDTH; x < START_WIDTH + BOARD_SIZE; x+= 3*BOARD_SIZE/4){
			g.drawLine(x + RADIUS/2, START_HEIGHT + BOARD_SIZE/2, x + BOARD_SIZE/4 - RADIUS/2, START_HEIGHT + BOARD_SIZE/2);
		}
		for (int x = START_HEIGHT; x < START_HEIGHT + BOARD_SIZE; x+= 3*BOARD_SIZE/4){
			g.drawLine(START_WIDTH + BOARD_SIZE/2, x + RADIUS/2, START_WIDTH + BOARD_SIZE/2, x + BOARD_SIZE/4 - RADIUS/2);
		}		
	}

	/**
	 * Draws all the placed pieces in their respective positions
	 * @param g java.awt.Graphics object
	 */
	private void drawPieces(Graphics g){

		//Draws all the pieces on the board
		for (int x = 0; x < 3; x++){
			for (int y = 0; y < 3; y++){
				for (int z = 0; z < 2; z++){
					int[] position = {x,y,z}; 
					model.Piece current = model.Board.getPiece(position);				
					if (current != null){						
						drawPiece(g,x,y,z,current.getColor());						
					}
				}
			}
		}

		//Checks for errors in the current state and highlights all pieces involved
		ArrayList<model.BoardError> errors = model.BoardError.getErrors();
		for (int num = 0; num < errors.size(); num++){
			for (int x = 0; x < 3; x++){
				for (int y = 0; y < 3; y++){
					for (int z = 0; z < 2; z++){					
						ArrayList<model.Piece> current = errors.get(num).getPieces();				
						if (current != null){						
							for (int i = 0; i < current.size(); i++){
								int[] position = current.get(i).getCoordinate();
								highlightPiece(g,position[0],position[1],position[2],current.get(i).getColor(), errors.get(num).getType());
							}
						}
					}
				}
			}
		}

	}

	/**
	 * Draws a piece of the specified colour in the given location
	 * @param g java.awt.Graphics object
	 * @param x row of the board {0=bottom,1=middle,2=top}
	 * @param y column of the board {0=left,1=middle,2=right}
	 * @param z inner/outer frame of the board {0=inner,1=outer}
	 * @param color true if 1st player (red) other false (blue)
	 */
	private void drawPiece(Graphics g, int x, int y, int z, boolean color) {
		int height, width;

		if (z == 1) {
			height = getOuterHeight(x,y);
			width = getOuterWidth(x,y);
		}
		else {
			height = getInnerHeight(x,y);
			width = getInnerWidth(x,y);
		}
		
		if (color == model.Board.RED) g.setColor(Color.RED);
		else if (color == model.Board.BLUE) g.setColor(Color.BLUE);		
		g.fillOval(width - RADIUS/2, height - RADIUS/2, RADIUS, RADIUS);		
	}

	/**
	 * Draws a piece of the specified error state for the given location
	 * @param g java.awt.Graphics object
	 * @param x row of the board {0=bottom,1=middle,2=top}
	 * @param y column of the board {0=left,1=middle,2=right}
	 * @param z inner/outer frame of the board {0=inner,1=outer}
	 * @param color true if 1st player (red) other false (blue)
	 * @param type indicator representing error status of the piece
	 */
	private void highlightPiece(Graphics g, int x, int y, int z, boolean color, int type) {

		int height, width;
		Graphics2D g2d = (Graphics2D) g;
		if (z == 1) {
			height = getOuterHeight(x,y);
			width = getOuterWidth(x,y);
		}
		else {
			height = getInnerHeight(x,y);
			width = getInnerWidth(x,y);
		}

		//Overlapping pieces (ie. multiple placed on same node)
		if (type == 1){
			if (color == model.Board.RED) g2d.setColor(Color.RED);
			else g2d.setColor(Color.BLUE);	
			
			g2d.fillOval(width - RADIUS/2, height - RADIUS/2, RADIUS, RADIUS);
			
			if (color == model.Board.RED) g2d.setColor(Color.BLUE);
			else g2d.setColor(Color.RED);
			
			g2d.fillOval(width - 15, height - 15, RADIUS/2, RADIUS/2);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(10.0f));		
			g2d.drawOval(width - RADIUS/2, height - RADIUS/2, RADIUS, RADIUS);

		} else if (type == 2){
			//Too many pieces placed on the board (highlight in yellow)
			
			if (color == model.Board.RED) g2d.setColor(Color.RED);
			else g2d.setColor(Color.BLUE);	
			
			g2d.fillOval(width - RADIUS/2, height - RADIUS/2, RADIUS, RADIUS);
			
			g2d.setColor(Color.YELLOW);
			g2d.setStroke(new BasicStroke(10.0f));		
			g2d.drawOval(width - RADIUS/2, height - RADIUS/2, RADIUS, RADIUS);
		}

	}

	/**
	 * Convert between screen location and enumerated position on board
	 * @param x column of board
	 * @param h row of board
	 * @return position
	 */
	private int getOuterWidth (int x, int h){
		switch (h){
		case 0: return START_WIDTH;		
		case 1: return START_WIDTH + BOARD_SIZE/2;
		case 2: return START_WIDTH + BOARD_SIZE;
		default: return -60;
		}
	}

	/**
	 * Convert between screen location and enumerated position on board
	 * @param x column of board
	 * @param h row of board
	 * @return position
	 */
	private int getInnerWidth (int x, int h){
		switch (h){
		case 0: return START_WIDTH + BOARD_SIZE/4;
		case 1: return START_WIDTH + BOARD_SIZE/2;
		case 2: return START_WIDTH + 3*BOARD_SIZE/4;
		default: return -60;
		}
	}

	/**
	 * Convert between screen location and enumerated position on board
	 * @param x column of board
	 * @param h row of board
	 * @return position
	 */
	private int getOuterHeight (int x, int h){
		switch (x){
		case 0: return START_HEIGHT + BOARD_SIZE;
		case 1: return START_HEIGHT + BOARD_SIZE/2;
		case 2: return START_HEIGHT;
		default: return -60;
		}
	}

	/**
	 * Convert between screen location and enumerated position on board
	 * @param x column of board
	 * @param h row of board
	 * @return position
	 */
	private int getInnerHeight (int x, int h){
		switch (x){
		case 0: return START_HEIGHT + 3*BOARD_SIZE/4;
		case 1: return START_HEIGHT + BOARD_SIZE/2;
		case 2: return START_HEIGHT + BOARD_SIZE/4;
		default: return -60;
		}
	}			
}