package confcost.view2;

import java.awt.BorderLayout;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

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
	private MainFrame mainFrame;
	
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
		
		JLabel lblWestLeftHeader = new JLabel("Iterationen");
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
		panelWestLeftIterationList.add(listIterations);
		
		JPanel panelWestRight = new JPanel();
		panelWest.add(panelWestRight, BorderLayout.EAST);
		panelWestRight.setLayout(new BorderLayout(0, 0));
		
		JPanel panelWestRightHeader = new JPanel();
		panelWestRight.add(panelWestRightHeader, BorderLayout.NORTH);
		
		JLabel lblWestRightHeader = new JLabel("Result Objekte");
		panelWestRightHeader.add(lblWestRightHeader);
		
		JPanel panelWestRightResultObjList = new JPanel();
		panelWestRight.add(panelWestRightResultObjList, BorderLayout.CENTER);
		panelWestRightResultObjList.setLayout(new BorderLayout(0, 0));
		
		JList<String> listResultObjects = new JList<String>();
		listResultObjects.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"r1", "r2", "r3", "r4", "r5"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		panelWestRightResultObjList.add(listResultObjects);
		
		JPanel panelCenter = new JPanel();
		add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCenterHeader = new JPanel();
		panelCenter.add(panelCenterHeader, BorderLayout.NORTH);
		
		JLabel lblCenterHeader = new JLabel("Details");
		panelCenterHeader.add(lblCenterHeader);
		
		JPanel panelDetailContainer = new JPanel();
		panelCenter.add(panelDetailContainer, BorderLayout.CENTER);
		panelDetailContainer.setLayout(new BorderLayout(0, 0));
		
		JPanel panelEast = new JPanel();
		add(panelEast, BorderLayout.EAST);
		panelEast.setLayout(new BorderLayout(0, 0));
		
		JPanel panelEastHeader = new JPanel();
		panelEast.add(panelEastHeader, BorderLayout.NORTH);
		
		JLabel lblEastHeader = new JLabel("Statistik");
		panelEastHeader.add(lblEastHeader);
		
		JPanel panelStatisticContainer = new JPanel();
		panelEast.add(panelStatisticContainer, BorderLayout.CENTER);
		panelStatisticContainer.setLayout(new BorderLayout(0, 0));

		
	}

	/**
	 * @return the mainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * @param mainFrame the mainFrame to set
	 */
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void statModelChanged(@NonNull StatModel model) {
		System.out.println("Stat model changed!");
		this.listIterations.setListData(model.getCryptoPasses());
	}
}