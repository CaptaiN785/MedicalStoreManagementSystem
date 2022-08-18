package UI;

import java.awt.Dimension;

import javax.swing.*;

public class AddMedicine extends JInternalFrame{
	public AddMedicine(){
		super("Add medicine", false, true);
		this.setVisible(true);
		this.setPreferredSize(new Dimension(500, 300));
	}
}
