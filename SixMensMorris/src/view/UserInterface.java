package view;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The class UserInterface is responsible for displaying actionable buttons that
 * the user can click in order to change the state of the game
 *
 */
public class UserInterface extends JPanel implements MouseListener {

	public static enum Status {
		NEW_GAME, PLACE_PIECE, CHECK_POSITION, SWITCH_COLOR, CONTINUE, PLAY_GAME
	}
	
	private Status state = Status.NEW_GAME;
	
	private final JButton new_game;
	private final JButton place_pcs;
	private final JButton check_pos;
	private final JButton change_turn;
	
	/**
	 * Creates and displays buttons that the user can interact with
	 * in order to change the state of the game
	 */
	public UserInterface(){
		new_game = new JButton("New Game");
		place_pcs = new JButton();
		check_pos = new JButton("Analyze");
		change_turn = new JButton("Switch Color");
		
		JLabel label1 = new JLabel(" Place");
		JLabel label2 = new JLabel("Pieces");
		place_pcs.setLayout(new BorderLayout());
		place_pcs.add(BorderLayout.NORTH,label1);
		place_pcs.add(BorderLayout.SOUTH,label2);
		   
		java.awt.BorderLayout b = new java.awt.BorderLayout();
		this.setLayout(b);
		this.add(new_game, BorderLayout.PAGE_START);
		this.add(place_pcs, BorderLayout.LINE_START);
		this.add(check_pos, BorderLayout.LINE_END);
		this.add(change_turn, BorderLayout.CENTER);
		new_game.addMouseListener(this);
		place_pcs.addMouseListener(this);
		check_pos.addMouseListener(this);
		change_turn.addMouseListener(this);
	}

	/**
	 * @return current state of the game
	 */
	public Status getState() {
		return state;
	}


	/**
	 * @param state declare new state of the game
	 */
	public void setState(Status state) {
		this.state = state;
	}


	/* (non-Javadoc)
	 * Determines which button was clicked and responds accordingly
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == new_game) setState(Status.NEW_GAME);
		else if (e.getSource() == place_pcs) setState(Status.PLACE_PIECE);		
		else if (e.getSource() == check_pos) setState(Status.CHECK_POSITION);
		else if (e.getSource() == change_turn) setState(Status.SWITCH_COLOR);
		else setState(Status.CONTINUE);
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		e.consume();		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		e.consume();	
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