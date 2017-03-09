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
			Class.forName(model.NodeCreator.class.getName());
			GameController.class.newInstance();
		} catch (Exception e) {
			System.out.println("Error loading classes");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}

