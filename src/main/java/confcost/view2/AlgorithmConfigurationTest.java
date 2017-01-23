package confcost.view2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import confcost.model.SendMode;

/**
 * Test class to represent algorithm configurations.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class AlgorithmConfigurationTest extends AlgorithmConfiguration {
	private static final long serialVersionUID = 1L;

	public AlgorithmConfigurationTest(MainFrame mainFrame, SendMode sendMode) {
		super("Test", mainFrame, sendMode);
		initSendClickedListener();
	}

	@Override
	protected void initSendClickedListener() {
		getBtnSend().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AlgorithmConfigurationTest.this.notifySendButtonListeners();
			}
		});
	}
}
