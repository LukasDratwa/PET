package confcost.confcost;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import confcost.controller.Controller;
import confcost.model.Model;
import confcost.view.MainFrame;

/**
 * The application main class
 */
public class App 
{
	/**
	 * The application's version
	 */
	public static final String VERSION = "RC1";
	
	/**
	 * Sets the L&amp;F to the specified L&amp;F. If no L&amp;F with the specified name exists, the systems' L&amp;F is used instead. 
	 * @param lf	The L&amp;F name
	 */
	public void setLookAndFeel(final String lf) {
		try {
			boolean found = false;
			
		    for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if (info.getName().equals(lf)) {
		            UIManager.setLookAndFeel(info.getClassName());
		            found = true;
		            break;
		        }
		    }
		    
		    if (!found)
		    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		    e.printStackTrace();
		}
	}
	
	/**
	 * Starts the application.
	 * 
	 * @throws IOException	If an IO error occurred
	 */
	public void start() throws IOException {
        Model model = new Model();
		model.setVersion(App.VERSION);
		
        MainFrame view = new MainFrame("confcost", model);
        Controller controller = new Controller(model, view);
        controller.start();
	}
	
	/**
	 * The main function
	 * @param args	Command line arguments
	 * @throws IOException	If an IO error occurred
	 */
    public static void main( String[] args ) throws IOException {		
    	App app = new App();
    	app.setLookAndFeel("Nimbus");
    	app.start();
    }
}
