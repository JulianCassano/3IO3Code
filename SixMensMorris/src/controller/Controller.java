package controller;

/**
 * The Controller class instantiates NodeCreator and GameController classes
 * thus beginning the main execution loop
 * 
 * @version 1.0
 *
 */
public class Controller {
	
	/**
	 * Initializes model.NodeCreator and instantiates GameController
	 * Execution loop then defers to GameController
	 * 
	 * @see java.lang.Class#newInstance()
	 */
	public static void main(String[] args){	
		try {
			Class.forName(model.NodeCreator.class.getName());   // Uses reflective method to instantiate GameController with initialized NodeCreator
			GameController.class.newInstance();                 // to implement the project following the Model-View-Controller (MVC) design 
		} catch (Exception e) {
			System.out.println("Error loading classes");		// If any errors during runtime due to the use of reflection, 
			e.printStackTrace();                                // the game exits as to not impede or risk the system it is running on.
			System.exit(-1);
		}
	}
}

