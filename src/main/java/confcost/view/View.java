package confcost.view;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import confcost.controller.ViewListener;
import confcost.model.Model;

/**
 * Main View class
 * 
 * @author Marc Eichler
 *
 */
public class View extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5317039148938674808L;
	
	/**
	 * The registered {@link ViewListener}
	 */
	private final @NonNull Set<ViewListener> listener = new HashSet<>();
	
	/**
	 * The {@link Model}
	 */
	private final @NonNull Model model;
	
	private final @NonNull SendPanel sendPanel;
	
	/**
	 * Creates a new {@link View} based on the specified {@link Model}.
	 * 
	 * @param model	The {@link Model}
	 * @param title	The window title
	 */
	public View(@NonNull Model model, @Nullable String title) {
		this.model = model;
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setTitle(title);
		
		this.setLayout(new BorderLayout());
		
		this.sendPanel = new SendPanel(this, model.getModes());
		
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Send", sendPanel);
		pane.addTab("Recv", new ReceivePanel(model.getReceiveModel()));
		
		this.add(pane, BorderLayout.CENTER);
		
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
				for(ViewListener l : listener) l.notifyClosing(View.this);
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
	}
	
	/**
	 * Adds a {@link ViewListener}.
	 * 
	 * @param listener	The listener
	 */
	public void addViewListener(ViewListener listener) {
		this.listener.add(listener);
	}

	public void notifySendButtonPressed(SendModeView sendModeView) {
		for (ViewListener l : listener) l.notifySendButtonPressed(sendModeView);
	}

	/**
	 * Adds a {@link SendViewListener}
	 * 
	 * @param listener	The listener
	 */
//	public void addSendViewListener(SendViewListener listener) {
//		this.sendPanel.addSendViewListener(listener);
//	}
}
