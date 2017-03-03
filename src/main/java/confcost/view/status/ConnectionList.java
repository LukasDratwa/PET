package confcost.view.status;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
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
		this.content.setLayout(new BoxLayout(this.content, BoxLayout.PAGE_AXIS));
		
		this.scrollPane = new JScrollPane(content);
		
		this.add(scrollPane, BorderLayout.CENTER);
		model.getConnectionModel().addConnectionModelListener(this);
	}

	@Override
	public void notifyConnectionAdded(final @NonNull ConnectionModel model, final @NonNull Connection connection) {
		this.addConnectionPanel(connection);
	}
	
	/**
	 * Adds a new panel for the specified connection
	 * @param connection
	 */
	public void addConnectionPanel(Connection connection) {
		this.content.add(new ConnectionPanel(connection));
		this.content.revalidate();
	}

	@Override
	public void notifyAnyConnectionChanged(ConnectionModel model, Connection connection) {}
}
