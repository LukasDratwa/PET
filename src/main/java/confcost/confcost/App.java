package confcost.confcost;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import confcost.controller.Controller;
import confcost.model.Model;
import confcost.view2.MainFrame;

/**
 * Hello world!
 *
 */
public class App 
{
	@SuppressWarnings("unused")
	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
			System.err.println("Unable to set look and feel!");
			e.printStackTrace();
	    } 
	}
	
	private static void setNimbusLookAndFeel() {
		try {
		    for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
    public static void main( String[] args ) throws IOException
    {
    	setNimbusLookAndFeel();
        Model model = new Model();
        MainFrame view = new MainFrame("confcost", model);
        Controller controller = new Controller(model, view);
        controller.start();
    }
}
