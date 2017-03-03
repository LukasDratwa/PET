package confcost.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.SendButtonListener;
import confcost.controller.SettingsListener;
import confcost.model.Model;
import confcost.view.send.TabSend;
import confcost.view.statistics.TabStatistics;
import confcost.view.status.TabStatus;

/**
 * Class to represent the main frame of the application.
 * 
 * <hr>Created on 24.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The JPanel containing all content within this {@link JFrame}
	 */
	private JPanel contentPane;
	
	/**
	 * The "Send" tab to initiate an encryption run
	 */
	private TabSend tabSend;
	
	/**
	 * The "Status" tab to view current incoming and outgoing runs
	 */
	private TabStatus tabStatus;
	
	/**
	 * The "Statistics" tab to view statistical data
	 */
	private TabStatistics tabStatistics;
	
	/**
	 * The main {@link Model}
	 */
	private final @NonNull Model model;
	
	/**
	 * Constructor
	 * @param title	The window title
	 * @param model	The main {@link Model}
	 */
	public MainFrame(String title, final @NonNull Model model) {
		setTitle(title);
		this.model = model;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setMinimumSize(new Dimension(450, 300));
		setPreferredSize(new Dimension(700, 600));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		tabSend = new TabSend(model);
		tabbedPane.addTab("Send", null, tabSend, null);
		
		tabStatus = new TabStatus(model);
		tabbedPane.addTab("Status", null, tabStatus, null);
		
		tabStatistics = new TabStatistics(this, model.getStatModel());
		tabbedPane.addTab("Statistics", null, tabStatistics, null);
		
		InfoBar infoBar = new InfoBar(model);
		contentPane.add(infoBar, BorderLayout.SOUTH);
	}

	/**
	 * Adds a new {@link SendButtonListener} to be notified.
	 * @param listener	The listener
	 */
	public void addSendButtonListener(SendButtonListener listener) {
		this.tabSend.addSendButtonListener(listener);
	}

	/**
	 * Adds a new {@link SettingsListener} to be notified.
	 * @param listener	The listener
	 */
	public void addSettingsListener(SettingsListener listener) {
		this.tabSend.addSettingsListener(listener);
	}

	/**
	 * Sets the host.
	 * @param host the host 
	 */
	public void setHost(@NonNull String host) {
		this.tabSend.setHost(host);
	}

	/**
	 * Sets the port.
	 * @param port the port 
	 */
	public void setPort(int port) {
		this.tabSend.setPort(port);
	}


	/**
	 * Sets the number of iterations.
	 * @param iterations	The iterations
	 */
	public void setIterations(int iterations) {
		this.tabSend.setIterations(iterations);
	}
	/**
	 * Sets whether or not key exchange should be performed every iteration
	 * @param b	The value
	 */
	public void setKeyExchangeEveryIteration(boolean b) {
		this.tabSend.setKeyExchangeEveryIteration(b);
	}
	
	/**
	 * @return the {@link Model}
	 */
	public Model getModel() {
		return model;
	}
}