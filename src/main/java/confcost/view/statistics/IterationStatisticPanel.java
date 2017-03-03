package confcost.view.statistics;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import confcost.model.CryptoPass;
import confcost.model.CryptoPassStatistic;

/**
 * <hr>Created on 12.02.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class IterationStatisticPanel extends JPanel {
	private static final long serialVersionUID = 5811821084252979623L;
	
	public IterationStatisticPanel(CryptoPass pass) {
		this.setLayout(new BorderLayout());
		setPreferredSize(new Dimension(0, 140));
		
		JLabel label = new JLabel("Statistic of pass: " + pass + ", values in μs");
		this.add(label, BorderLayout.NORTH);
		
		JScrollPane tableContainer = new JScrollPane();
		JTable table = new JTable(new IterationStatisticTableModel(pass));
		tableContainer.setViewportView(table);
		this.add(tableContainer, BorderLayout.CENTER);
	}
	
	public class IterationStatisticTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1445146757994573243L;

		private String[] columnNames = {"Metric", "Min", "Mean", "Squared Mean",
										"Max", "Standard Deviation", "Variance", "Population variance"};
	    private Object[][] data;
	    
	    public IterationStatisticTableModel(CryptoPass pass) {
	    	pass.initCryptoPassStatistic();
	    	CryptoPassStatistic cps = pass.getCryptoPassStatistic();
	    	
	    	data = new Object[][]{
	    	    	{
	    	    		"Init",
	    	    		cps.getSumStatisticsInitTime().getMin(),
	    	    		cps.getSumStatisticsInitTime().getMean(),
	    	    		cps.getSumStatisticsInitTime().getQuadraticMean(),
	    	    		cps.getSumStatisticsInitTime().getMax(),
	    	    		cps.getSumStatisticsInitTime().getStandardDeviation(),
	    	    		cps.getSumStatisticsInitTime().getVariance(),
	    	    		cps.getSumStatisticsInitTime().getPopulationVariance()
	    	    	},
	    	    	{
	    	    		"Remote init",
	    	    		cps.getSumStatisticsRemoteInitTime().getMin(),
	    	    		cps.getSumStatisticsRemoteInitTime().getMean(),
	    	    		cps.getSumStatisticsRemoteInitTime().getQuadraticMean(),
	    	    		cps.getSumStatisticsRemoteInitTime().getMax(),
	    	    		cps.getSumStatisticsRemoteInitTime().getStandardDeviation(),
	    	    		cps.getSumStatisticsRemoteInitTime().getVariance(),
	    	    		cps.getSumStatisticsRemoteInitTime().getPopulationVariance()
	    	    	},
	    	    	{
	    	    		"Encryption",
	    	    		cps.getSumStatisticsEncryption().getMin(),
	    	    		cps.getSumStatisticsEncryption().getMean(),
	    	    		cps.getSumStatisticsEncryption().getQuadraticMean(),
	    	    		cps.getSumStatisticsEncryption().getMax(),
	    	    		cps.getSumStatisticsEncryption().getStandardDeviation(),
	    	    		cps.getSumStatisticsEncryption().getVariance(),
	    	    		cps.getSumStatisticsEncryption().getPopulationVariance()
	    	    	},
	    	    	{
	    	    		"Decription",
	    	    		cps.getSumStatisticsDecryption().getMin(),
	    	    		cps.getSumStatisticsDecryption().getMean(),
	    	    		cps.getSumStatisticsDecryption().getQuadraticMean(),
	    	    		cps.getSumStatisticsDecryption().getMax(),
	    	    		cps.getSumStatisticsDecryption().getStandardDeviation(),
	    	    		cps.getSumStatisticsDecryption().getVariance(),
	    	    		cps.getSumStatisticsDecryption().getPopulationVariance()
	    	    	}
	    	    	,
	    	    	{
	    	    		"Msg length",
	    	    		cps.getSumStatisticsMsgLength().getMin(),
	    	    		cps.getSumStatisticsMsgLength().getMean(),
	    	    		cps.getSumStatisticsMsgLength().getQuadraticMean(),
	    	    		cps.getSumStatisticsMsgLength().getMax(),
	    	    		cps.getSumStatisticsMsgLength().getStandardDeviation(),
	    	    		cps.getSumStatisticsMsgLength().getVariance(),
	    	    		cps.getSumStatisticsMsgLength().getPopulationVariance()
	    	    	},
	    	    	{
	    	    		"Key length",
	    	    		cps.getSumStatisticsKeyLength().getMin(),
	    	    		cps.getSumStatisticsKeyLength().getMean(),
	    	    		cps.getSumStatisticsKeyLength().getQuadraticMean(),
	    	    		cps.getSumStatisticsKeyLength().getMax(),
	    	    		cps.getSumStatisticsKeyLength().getStandardDeviation(),
	    	    		cps.getSumStatisticsKeyLength().getVariance(),
	    	    		cps.getSumStatisticsKeyLength().getPopulationVariance()
	    	    	}
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