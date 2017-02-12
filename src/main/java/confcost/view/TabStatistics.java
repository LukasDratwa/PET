package confcost.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.CryptoPass;
import confcost.model.StatModel;
import confcost.model.StatModelListener;

/**
 * {@link JPanel} to represent a tab for the statistics-configurations.
 * 
 * <hr>Created on 22.01.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class TabStatistics extends JPanel implements StatModelListener {
	private static final long serialVersionUID = 1L;
	
	private final JList<CryptoPass> listIterations;
	
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
		
		listIterations = new JList<>();
//		listIterations.setModel(new AbstractListModel<String>() {
//			private static final long serialVersionUID = 1L;
//			String[] values = new String[] {"23.01.17, 22:00", "23.01.17, 22:03"};
//			public int getSize() {
//				return values.length;
//			}
//			public String getElementAt(int index) {
//				return values[index];
//			}
//		});

		PassPanel passPanel = new PassPanel();
		this.add(passPanel, BorderLayout.CENTER);
		
		listIterations.addListSelectionListener(new ListSelectionListener() {	
			@Override
			public void valueChanged(ListSelectionEvent e) {
				passPanel.set(listIterations.getSelectedValue());
			}
		});
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportView(listIterations);
		panelWestLeftIterationList.add(scrollPane);
		
	}
	
	@Override
	public void statModelChanged(@NonNull StatModel model) {
		System.out.println("Stat model changed!");
		this.listIterations.setListData(model.getCryptoPasses());
	}
}