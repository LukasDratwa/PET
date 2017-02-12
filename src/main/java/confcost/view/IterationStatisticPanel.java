package confcost.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import confcost.model.CryptoPass;

/**
 * <hr>Created on 12.02.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class IterationStatisticPanel extends JPanel {
	private static final long serialVersionUID = 5811821084252979623L;
	
	public IterationStatisticPanel(CryptoPass pass) {
		this.setLayout(new BorderLayout());
		setPreferredSize(new Dimension(0, 125));
		
		JLabel label = new JLabel("Statistic of pass: " + pass);
		this.add(label, BorderLayout.NORTH);
		
		JScrollPane tableContainer = new JScrollPane();
		JTable table = new JTable(new IterationStatisticTableModel(pass));
		tableContainer.setViewportView(table);
		this.add(tableContainer, BorderLayout.CENTER);
	}
	
	class IterationStatisticTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1445146757994573243L;

		private String[] columnNames = {"Metric", "Min (ms)", "Avg (ms)", "Max (ms)"};
	    private Object[][] data;
	    
	    public IterationStatisticTableModel(CryptoPass pass) {
	    	data = new Object[][]{
	    	    	{"Init", pass.getMinInitTime().getInitTime(), pass.getAvgInitTime(), pass.getMaxInitTime().getInitTime()},
	    	    	{"Remote init", pass.getMinRemoteInitTime().getRemoteInitTime(), pass.getAvgRemoteInitTime(), pass.getMaxRemoteInitTime().getRemoteInitTime()},
	    	    	{"Encryption", pass.getMinEncryptionTime().getEncryptionTime(), pass.getAvgEncryptionTime(), pass.getMaxEncryptionTime().getEncryptionTime()},
	    	    	{"Decription", pass.getMinDecryptionTime().getDecryptionTime(),pass.getAvgDecryptionTime(), pass.getMaxDecryptionTime().getEncryptionTime()}
	    	   };
	    }

	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return data.length;
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }
	}
}