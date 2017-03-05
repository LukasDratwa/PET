package confcost.view.status;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.Model;

/**
 * {@link JPanel} to represent a tab for the receive-configurations.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class TabStatus extends JPanel {
	private static final long serialVersionUID = 1L;

	public TabStatus(final @NonNull Model model) {
		this.setLayout(new BorderLayout());
		
		this.add(new ConnectionList(model), BorderLayout.CENTER);
	}
}