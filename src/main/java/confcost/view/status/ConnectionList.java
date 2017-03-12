package confcost.view.status;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.ConnectionModelListener;
import confcost.model.Connection;
import confcost.model.ConnectionModel;
import confcost.model.Model;

/**
 * Displays the data of a {@link ConnectionModel}
 * 
 * @author Marc Eichler
 *
 */
public class ConnectionList extends JPanel implements ConnectionModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6317446185964060475L;
	
	private final @NonNull JScrollPane scrollPane;
	
	private final @NonNull JPanel content;
	
	/**
	 * Constructor
	 * @param model	The main {@link Model}
	 */
	public ConnectionList(final @NonNull Model model) {
		this.setLayout(new BorderLayout());
		
		this.content = new JPanel();
		this.content.setLayout(new GridBagLayout());
		
		this.scrollPane = new JScrollPane(content);
		
		this.add(scrollPane);
		model.getConnectionModel().addConnectionModelListener(this);
	}

	@Override
	public void notifyConnectionAdded(final @NonNull ConnectionModel model, final @NonNull Connection connection) {
		this.addConnectionPanel(connection);
	}
	
	/**
	 * Adds a new panel for the specified {@link Connection}
	 * @param connection	the {@link Connection}
	 */
	public void addConnectionPanel(Connection connection) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		this.content.add(new ConnectionPanel(connection), c);
	}

	@Override
	public void notifyAnyConnectionChanged(ConnectionModel model, Connection connection) {}
}
