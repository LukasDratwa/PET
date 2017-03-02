package confcost.confcost;

import java.io.IOException;
import java.net.UnknownHostException;

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
	 * Sets the L&F to the specified L&F. If no L&F with the specified name exists, the systems' L&F is used instead. 
	 * @param lf	The L&F name
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
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void start() throws UnknownHostException, IOException {
        Model model = new Model();
        MainFrame view = new MainFrame("confcost", model);
        Controller controller = new Controller(model, view);
        controller.start();
	}
	
	/**
	 * The main function
	 * @param args	CL args
	 * @throws UnknownHostException
	 * @throws IOException
	 */
    public static void main( String[] args ) throws UnknownHostException, IOException {
    	App app = new App();
    	app.setLookAndFeel("Nimbus");
    	app.start();
    }
}
