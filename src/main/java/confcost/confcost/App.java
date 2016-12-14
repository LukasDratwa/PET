package confcost.confcost;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import confcost.controller.Controller;
import confcost.model.Model;
import confcost.view.View;

/**
 * Hello world!
 *
 */
public class App 
{
	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
			System.err.println("Unable to set look and feel!");
			e.printStackTrace();
	    } 
	}
	
    public static void main( String[] args ) throws IOException
    {
    	App.setLookAndFeel();
    	
        Model model = new Model();
        View view = new View(model, "ConfCost");
        Controller controller = new Controller(model, view);
        controller.start();
    }
}
