package confcost.view2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TabSend tabSend;
	private TabReceive tabReceive;
	private TabStatistics tabStatistics;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		changeLookAndFeel();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void changeLookAndFeel() {
		try {
		    for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setMinimumSize(new Dimension(450, 300));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		tabSend = new TabSend(this);
		tabbedPane.addTab("Send", null, tabSend, null);
		
		tabReceive = new TabReceive();
		tabbedPane.addTab("Receive", null, tabReceive, null);
		
		tabStatistics = new TabStatistics();
		tabbedPane.addTab("Statisctics", null, tabStatistics, null);
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
}