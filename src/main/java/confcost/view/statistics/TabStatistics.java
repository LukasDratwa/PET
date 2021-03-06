package confcost.view.statistics;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.StatModel;
import confcost.model.StatModelListener;
import confcost.model.statistics.CryptoPass;
import confcost.view.MainFrame;

/**
 * {@link JPanel} to represent a tab for the statistics-configurations.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class TabStatistics extends JPanel implements StatModelListener {
	private static final long serialVersionUID = 1L;
	
	/**
	 * List of {@link CryptoPass}
	 */
	private final JList<CryptoPass> passes;
	
	public TabStatistics(MainFrame mainFrame, final @NonNull StatModel statModel) {
		setLayout(new BorderLayout(0, 0));
		
		statModel.addListener(this);
		
		JPanel panelWest = new JPanel();
		add(panelWest, BorderLayout.WEST);
		panelWest.setLayout(new BorderLayout(0, 0));
		
		JPanel panelWestLeft = new JPanel();
		panelWest.add(panelWestLeft, BorderLayout.WEST);
		panelWestLeft.setLayout(new BorderLayout(0, 0));
		
		JPanel panelWestLeftHeader = new JPanel();
		panelWestLeft.add(panelWestLeftHeader, BorderLayout.NORTH);
		
		JLabel lblWestLeftHeader = new JLabel("Passes");
		panelWestLeftHeader.add(lblWestLeftHeader);
		
		JPanel panelWestLeftIterationList = new JPanel();
		panelWestLeft.add(panelWestLeftIterationList, BorderLayout.CENTER);
		panelWestLeftIterationList.setLayout(new BorderLayout(0, 0));
		
		passes = new JList<>();

		PassPanel passPanel = new PassPanel();
		this.add(passPanel, BorderLayout.CENTER);
		
		passes.addListSelectionListener(new ListSelectionListener() {	
			@Override
			public void valueChanged(ListSelectionEvent e) {
				passPanel.set(passes.getSelectedValuesList());
			}
		});
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportView(passes);
		panelWestLeftIterationList.add(scrollPane);
		
	}
	
	@Override
	public void statModelChanged(@NonNull StatModel model) {
		System.out.println("Stat model changed!");
		this.passes.setListData(model.getCryptoPasses());
	}
}