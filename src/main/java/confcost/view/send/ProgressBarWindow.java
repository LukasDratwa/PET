package confcost.view.send;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * JFrame to show progress of iterations
 * @author Aziani
 *
 */
public class ProgressBarWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8832288233526217626L;
	
	JProgressBar progressBar;
	static JScrollPane scrollPane;
	static JTextArea textArea;
	
	public ProgressBarWindow(String algoName, int maxIterations){
		this.setBounds(100,100,400,200);
		this.setTitle(algoName + " Progress");
		
		// progressBar showing progress of this run
		progressBar = new JProgressBar(0,maxIterations);
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
	
		// text area showing finished iterations
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
	
		this.add(progressBar, BorderLayout.SOUTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	/**
	 * update progress bar
	 * @param iteration number of finished iterations
	 */
	public void setProgressBarValue(int iteration){
		progressBar.setIndeterminate(false);
		progressBar.setValue(iteration);
	}
	
	/**
	 * update text field
	 * @param text next line of text
	 */
	public static void setListValue(String text){
		textArea.append(text+"\n");
		JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.setValue(verticalScrollBar.getMaximum());
	}
}
