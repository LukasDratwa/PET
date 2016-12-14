package confcost.view;

import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.JPanel;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.SendMode;

/**
 * The "Send" panel.
 * 
 * <p>
 * This panel contains all necessary elements to send data. 
 * </p>
 * 
 * @author Marc Eichler
 *
 */
public class SendPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8481268429507860139L;
	
	private final @NonNull View view;
	
	
	/**
	 * Creates a new {@link SendPanel}.
	 * 
	 * @param model	The model
	 */
	public SendPanel(@NonNull View view, @NonNull Collection<SendMode> modes) {
		this.view = view;
		
		this.setLayout(new GridLayout(modes.size(), 1));
		
		for (SendMode m : modes)
			this.add(new SendModeView(this, m));
	}


	public void notifySendButtonPressed(SendModeView sendModeView) {
		this.view.notifySendButtonPressed(sendModeView);
	}
}
