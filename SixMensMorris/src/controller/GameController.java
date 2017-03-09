package controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import model.Board;

/**
 * The GameController class is responsible for reacting to user input
 * by calling appropriate methods from within the controller, view and model packages
 * <br>
 * The GameController class implements java.awt.MouseListener in order to
 * receive user input from mouse actions
 *
 */
public class GameController implements MouseListener {	
	
	private static JFrame gameWindow;
	private static JFrame controlPanel;
	private static model.Board board;
	private static view.GameInterface GM;
	private static view.UserInterface UI;
	
	private static int startPos = -1;
	
	/**
	 * Instantiates the GameInterface and UserInterface thus starting the game
	 */
	public GameController(){		
		
		initGameManager();
		initUserInterface();						
	}	
	
	/**
	 * Instantiates the GameInterface
	 * @see view.GameInterface
	 */
	private void initGameManager(){
		GM = new view.GameInterface();
		gameWindow = new JFrame();
		gameWindow.setSize(720, 720);
		gameWindow.add(GM);
		GM.addMouseListener(this);
		gameWindow.setLocationRelativeTo(null);		
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setVisible(true);
	}
	
	/**
	 * Instantiates the UserInterface
	 * @see view.UserInterface
	 */
	private void initUserInterface(){
								
		int xpos = (int)gameWindow.getLocation().getX() + gameWindow.getWidth();
		int ypos = (int)gameWindow.getLocation().getY() + gameWindow.getHeight()/2 - 50;
				
		UI = new view.UserInterface();
		controlPanel = new JFrame();
		controlPanel.setSize(300, 110);		
		controlPanel.setLocation(xpos, ypos);
		controlPanel.add(UI);
		controlPanel.addMouseListener(this);
		controlPanel.getContentPane().setBackground(Color.LIGHT_GRAY);
		controlPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlPanel.setVisible(true);	
		startGame();
	}
	
	/**
	 * Creates a new game and updates the GameInterface
	 */
	private void startGame(){
		board = new model.Board();
		UI.setState(view.UserInterface.Status.PLAY_GAME);
		GM.update();		
	}
	
	/**
	 * Resets the state of the game (including the current board setup)
	 */
	private void resetGame(){
		model.Board.resetBoard();
		board = new model.Board();
		UI.setState(view.UserInterface.Status.CONTINUE);
		GM.update();	
	}
	
	/**
	 * Analyzes the current state of the game looking for possible errors
	 */
	private void analyzeGame(){
		board.validSetup();
		UI.setState(view.UserInterface.Status.CONTINUE);
		GM.update();
	}
	
	/**
	 * Performs first phase of turn - piece selection
	 * @param e @see java.awt.event.MouseEvent
	 * @return position of selected piece, in terms of the node it is placed on
	 */
	private int getStartPosition(MouseEvent e){
		Point click = new Point(e.getX(), e.getY());
		for (int x = 0; x < 16; x++){
			if (model.BoardLayout.distanceTo(x, click) < 40){
				int[] position = model.BoardLayout.getNodePosition(x);
				model.Piece current = Board.getPiece(position);
				if (current != null && current.getColor() == Board.getTurn()) return x;				
			}
		}
		return -1;
	}
	
	/**
	 * Performs the two-stage user move - piece selection then movement 
	 * @param e @see java.awt.event.MouseEvent
	 */
	private void makeMove(MouseEvent e){
		
		if (startPos == -1) {
			startPos = getStartPosition(e);
			return;
		}
		
		Point click = new Point(e.getX(), e.getY());
		for (int x = 0; x < 16; x++){
			if (model.BoardLayout.distanceTo(x, click) < 40){
				int[] position = model.BoardLayout.getNodePosition(x);
				model.Piece current = Board.getPiece(position);
				if (current == null && model.BoardLayout.isConnected(startPos, x)) {
					model.Piece selected = Board.getPiece(model.BoardLayout.getNodePosition(startPos));
					selected.setCoordinate(position);
					startPos = -1;
					Board.switchTurn();
					GM.update();					
					return;				
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * Receives mouse click as input and calls appropriate methods based on input properties
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
								
		if (Board.getNumPieces() == 12) {
			makeMove(e);
			return;
		}
		Point click = new Point(e.getX(), e.getY());				
		
		for (int x = 0; x < 16; x++){
			if (model.BoardLayout.distanceTo(x, click) < 40){
				int[] position = model.BoardLayout.getNodePosition(x);
				if (Board.getPiece(position) != null) return;
				board.addPiece(model.Board.getTurn(), position);
				UI.setState(view.UserInterface.Status.PLAY_GAME);
				GM.update();
			}
		}	
		model.Board.switchTurn();
	}

	/* (non-Javadoc)
	 * Receives mouse entering component as input and calls appropriate methods based on input properties
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {		
		if (e.getSource() == controlPanel) {
			e.consume();
			return;
		}		
		if (UI.getState() == view.UserInterface.Status.NEW_GAME) resetGame();
		else if (UI.getState() == view.UserInterface.Status.PLACE_PIECE) startGame();
		else if (UI.getState() == view.UserInterface.Status.CHECK_POSITION) analyzeGame();
		else if (UI.getState() == view.UserInterface.Status.SWITCH_COLOR) Board.switchTurn();
		else e.consume();
	}

	/* (non-Javadoc)
	 * Receives mouse exiting component as input and calls appropriate methods based on input properties
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == gameWindow) {
			e.consume();
			return;
		}		
		if (UI.getState() == view.UserInterface.Status.NEW_GAME) resetGame();
		else if (UI.getState() == view.UserInterface.Status.PLACE_PIECE) startGame();			
		else if (UI.getState() == view.UserInterface.Status.CHECK_POSITION) analyzeGame();		
		else e.consume();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		e.consume();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		e.consume();
	}	
}