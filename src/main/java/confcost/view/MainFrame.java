package confcost.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.eclipse.jdt.annotation.NonNull;

import confcost.controller.SendButtonListener;
import confcost.model.Model;

/**
 * Class to represent the main frame of the application.
 * 
 * <hr>Created on 24.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TabSend tabSend;
	private TabReceive tabReceive;
	private TabStatistics tabStatistics;
	
	private final @NonNull Model model;
	
	public MainFrame(String title, final @NonNull Model model) {
		setTitle(title);
		this.model = model;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setMinimumSize(new Dimension(450, 300));
		setPreferredSize(new Dimension(700, 600));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		tabSend = new TabSend(this, model.getModes());
		tabbedPane.addTab("Send", null, tabSend, null);
		
		tabReceive = new TabReceive(this);
		tabbedPane.addTab("Receive", null, tabReceive, null);
		
		tabStatistics = new TabStatistics(this, model.getStatModel());
		tabbedPane.addTab("Statistics", null, tabStatistics, null);
	}

	/**
	 * @return the tabSend
	 */
	public TabSend getTabSend() {
		return tabSend;
	}

	/**
	 * @param tabSend the tabSend to set
	 */
	public void setTabSend(TabSend tabSend) {
		this.tabSend = tabSend;
	}

	/**
	 * @return the tabReceive
	 */
	public TabReceive getTabReceive() {
		return tabReceive;
	}

	/**
	 * @param tabReceive the tabReceive to set
	 */
	public void setTabReceive(TabReceive tabReceive) {
		this.tabReceive = tabReceive;
	}

	/**
	 * @return the tabStatistics
	 */
	public TabStatistics getTabStatistics() {
		return tabStatistics;
	}

	/**
	 * @param tabStatistics the tabStatistics to set
	 */
	public void setTabStatistics(TabStatistics tabStatistics) {
		this.tabStatistics = tabStatistics;
	}
	
	public void addSendButtonListener(SendButtonListener listener) {
		this.tabSend.addSendButtonListener(listener);
	}

	/**
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}
}