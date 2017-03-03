package confcost.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.eclipse.jdt.annotation.NonNull;

import confcost.model.ConnectionModel;
import confcost.model.Model;

public class InfoBar extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4329780647269898775L;

	private final @NonNull ConnectionModel connectionModel;
	
	/**
	 * Constructor
	 * @param model	The main {@link Model}
	 */
	public InfoBar(@NonNull Model model) {
		this.setLayout(new BorderLayout());
		this.setText("ASDASDASD");
		
		connectionModel = model.getConnectionModel();
		
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
	}
}
