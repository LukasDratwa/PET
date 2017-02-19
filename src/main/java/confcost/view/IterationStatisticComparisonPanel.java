package confcost.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import confcost.model.CryptoPass;

/**
 * <hr>Created on 19.02.2017<hr>
 * @author <a href="mailto:lukasdratwa@yahoo.de">Lukas Dratwa</a>
 */
public class IterationStatisticComparisonPanel extends JPanel {
	private static final long serialVersionUID = -6436736247904526338L;

	public IterationStatisticComparisonPanel(List<CryptoPass> passes) {
		this.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Statistic of passes");
		this.add(label, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JPanel container = new JPanel(new GridLayout(6, 1)); // x rows and 1 column
		
		for(int x=0; x<6; x++) { // 6 metrics
			Object[][] rowValues = new Object[passes.size()][8]; // 8 = name + 7 measured statistic values
			// (name, min, mean, square mean, max, standard deviation, variance, population variance)
			
			
			JLabel scrollpanelHeader = new JLabel("default header");
			
			int i = -1;
			for(CryptoPass pass : passes) {
				i++;
				
				pass.initCryptoPassStatistic();
				
				switch(x) {
					case 0:
						// Init
						scrollpanelHeader.setText("Metric: Init time");
						
						rowValues[i][0] = pass.toString();
						rowValues[i][1] = pass.getCryptoPassStatistic().getSumStatisticsInitTime().getMin();
						rowValues[i][2] = pass.getCryptoPassStatistic().getSumStatisticsInitTime().getMean();
						rowValues[i][3] = pass.getCryptoPassStatistic().getSumStatisticsInitTime().getQuadraticMean();
						rowValues[i][4] = pass.getCryptoPassStatistic().getSumStatisticsInitTime().getMax();
						rowValues[i][5] = pass.getCryptoPassStatistic().getSumStatisticsInitTime().getStandardDeviation();
						rowValues[i][6] = pass.getCryptoPassStatistic().getSumStatisticsInitTime().getVariance();
						rowValues[i][7] = pass.getCryptoPassStatistic().getSumStatisticsInitTime().getPopulationVariance();
						break;
						
					case 1:
						// Remote init
						scrollpanelHeader.setText("Metric: Remote init time");
						
						rowValues[i][0] = pass.toString();
						rowValues[i][1] = pass.getCryptoPassStatistic().getSumStatisticsRemoteInitTime().getMin();
						rowValues[i][2] = pass.getCryptoPassStatistic().getSumStatisticsRemoteInitTime().getMean();
						rowValues[i][3] = pass.getCryptoPassStatistic().getSumStatisticsRemoteInitTime().getQuadraticMean();
						rowValues[i][4] = pass.getCryptoPassStatistic().getSumStatisticsRemoteInitTime().getMax();
						rowValues[i][5] = pass.getCryptoPassStatistic().getSumStatisticsRemoteInitTime().getStandardDeviation();
						rowValues[i][6] = pass.getCryptoPassStatistic().getSumStatisticsRemoteInitTime().getVariance();
						rowValues[i][7] = pass.getCryptoPassStatistic().getSumStatisticsRemoteInitTime().getPopulationVariance();
						break;
						
					case 2:
						// Encryption
						scrollpanelHeader.setText("Metric: Encryption time");
						
						rowValues[i][0] = pass.toString();
						rowValues[i][1] = pass.getCryptoPassStatistic().getSumStatisticsEncryption().getMin();
						rowValues[i][2] = pass.getCryptoPassStatistic().getSumStatisticsEncryption().getMean();
						rowValues[i][3] = pass.getCryptoPassStatistic().getSumStatisticsEncryption().getQuadraticMean();
						rowValues[i][4] = pass.getCryptoPassStatistic().getSumStatisticsEncryption().getMax();
						rowValues[i][5] = pass.getCryptoPassStatistic().getSumStatisticsEncryption().getStandardDeviation();
						rowValues[i][6] = pass.getCryptoPassStatistic().getSumStatisticsEncryption().getVariance();
						rowValues[i][7] = pass.getCryptoPassStatistic().getSumStatisticsEncryption().getPopulationVariance();
						break;
						
					case 3:
						// Decryption
						scrollpanelHeader.setText("Metric: Decryption time");
						
						rowValues[i][0] = pass.toString();
						rowValues[i][1] = pass.getCryptoPassStatistic().getSumStatisticsDecryption().getMin();
						rowValues[i][2] = pass.getCryptoPassStatistic().getSumStatisticsDecryption().getMean();
						rowValues[i][3] = pass.getCryptoPassStatistic().getSumStatisticsDecryption().getQuadraticMean();
						rowValues[i][4] = pass.getCryptoPassStatistic().getSumStatisticsDecryption().getMax();
						rowValues[i][5] = pass.getCryptoPassStatistic().getSumStatisticsDecryption().getStandardDeviation();
						rowValues[i][6] = pass.getCryptoPassStatistic().getSumStatisticsDecryption().getVariance();
						rowValues[i][7] = pass.getCryptoPassStatistic().getSumStatisticsDecryption().getPopulationVariance();
						break;
						
					case 4:
						// Msg length
						scrollpanelHeader.setText("Metric: Message length");
						
						rowValues[i][0] = pass.toString();
						rowValues[i][1] = pass.getCryptoPassStatistic().getSumStatisticsMsgLength().getMin();
						rowValues[i][2] = pass.getCryptoPassStatistic().getSumStatisticsMsgLength().getMean();
						rowValues[i][3] = pass.getCryptoPassStatistic().getSumStatisticsMsgLength().getQuadraticMean();
						rowValues[i][4] = pass.getCryptoPassStatistic().getSumStatisticsMsgLength().getMax();
						rowValues[i][5] = pass.getCryptoPassStatistic().getSumStatisticsMsgLength().getStandardDeviation();
						rowValues[i][6] = pass.getCryptoPassStatistic().getSumStatisticsMsgLength().getVariance();
						rowValues[i][7] = pass.getCryptoPassStatistic().getSumStatisticsMsgLength().getPopulationVariance();
						break;
						
					case 5:
						// Key Length
						scrollpanelHeader.setText("Metric: Key length");
						
						rowValues[i][0] = pass.toString();
						rowValues[i][1] = pass.getCryptoPassStatistic().getSumStatisticsKeyLength().getMin();
						rowValues[i][2] = pass.getCryptoPassStatistic().getSumStatisticsKeyLength().getMean();
						rowValues[i][3] = pass.getCryptoPassStatistic().getSumStatisticsKeyLength().getQuadraticMean();
						rowValues[i][4] = pass.getCryptoPassStatistic().getSumStatisticsKeyLength().getMax();
						rowValues[i][5] = pass.getCryptoPassStatistic().getSumStatisticsKeyLength().getStandardDeviation();
						rowValues[i][6] = pass.getCryptoPassStatistic().getSumStatisticsKeyLength().getVariance();
						rowValues[i][7] = pass.getCryptoPassStatistic().getSumStatisticsKeyLength().getPopulationVariance();
						break;
					
					default:
						System.err.println("Something failed in statistic comparison");
				}
			}
			
			JPanel metricComparisonContainer = new JPanel(new BorderLayout());
			JScrollPane scrollpanel = new JScrollPane();
			JTable table = new JTable(new IterationStatisticTableModel(rowValues));
			scrollpanel.setViewportView(table);
			
			scrollpanel.setPreferredSize(new Dimension(0, 40));
			
			metricComparisonContainer.add(scrollpanelHeader, BorderLayout.NORTH);
			metricComparisonContainer.add(scrollpanel, BorderLayout.CENTER);
			container.add(metricComparisonContainer);
		}
		
		
		scrollPane.setViewportView(container);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	class IterationStatisticTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1445146757994573243L;

		private String[] columnNames = {"Pass", "Min", "Mean", "Squared Mean",
										"Max", "Standard Deviation", "Variance", "Population variance"};
	    private Object[][] data;
	    
	    public IterationStatisticTableModel(Object[][] values) {
	    	data = values;
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