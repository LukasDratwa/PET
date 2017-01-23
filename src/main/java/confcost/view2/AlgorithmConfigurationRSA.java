package confcost.view2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class to represent the possible configurations for the algorithm RSA.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class AlgorithmConfigurationRSA extends AlgorithmConfiguration {
	private static final long serialVersionUID = 1L;

	public AlgorithmConfigurationRSA(MainFrame mainFrame) {
		super("RSA", mainFrame);
		initSendClickedListener();
	}

	@Override
	protected void initSendClickedListener() {
		getBtnSend().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Send RSA");
			}
		});
	}
}