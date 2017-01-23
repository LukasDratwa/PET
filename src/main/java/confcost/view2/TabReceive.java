package confcost.view2;

import javax.swing.JPanel;

/**
 * {@link JPanel} to represent a tab for the receive-configurations.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class TabReceive extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;

	public TabReceive(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * @return the mainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * @param mainFrame the mainFrame to set
	 */
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
}