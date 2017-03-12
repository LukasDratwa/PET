package confcost.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.ConnectionModelListener;
import confcost.model.Connection;
import confcost.model.ConnectionModel;
import confcost.model.Model;

public class InfoBar extends JPanel implements ConnectionModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4329780647269898775L;

	private static final String NO_CONNECTIONS = "<No connections>";
	/**
	 * The {@link ConnectionModel}
	 */
	private final @NonNull ConnectionModel connectionModel;
	
	/**
	 * The label displaying connection data
	 */
	private final @NonNull JLabel connectionLabel; 
	
	/**
	 * Constructor
	 * @param model	The main {@link Model}
	 */
	public InfoBar(@NonNull Model model) {
		this.setLayout(new BorderLayout());	
		
		connectionModel = model.getConnectionModel();
		connectionModel.addConnectionModelListener(this);
		
		connectionLabel = new JLabel(NO_CONNECTIONS);
		this.add(connectionLabel, BorderLayout.CENTER);
		
		this.add(new JLabel(model.getVersion()), BorderLayout.EAST);
		
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
	}

	@Override
	public void notifyConnectionAdded(ConnectionModel model, Connection connection) {
		this.update();
	}

	@Override
	public void notifyAnyConnectionChanged(ConnectionModel model, Connection connection) {
		this.update();
	}
	
	public void update() {
		int active = 0;
		int done = 0;
		int error = 0;
		
		for (final @NonNull Connection c : connectionModel.getConnections()) {
			switch(c.getStatus()) {
			case DONE: done++; break;
			case SETUP:
			case TRANSMITTING: active++; break;
			default: error++;
			}
		}
		
		String text = "Active: "+active+"\tDone: "+done+"\tError: "+error;
		this.connectionLabel.setText(text.replaceAll("\t", "    "));
	}
}
