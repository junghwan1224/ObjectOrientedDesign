package notepadpack;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class NotePadPanel extends JPanel {
	protected NotePadTextArea npt;
	
	public NotePadPanel() {
		npt = new NotePadTextArea();
		this.setLayout(new GridLayout());

		this.add(npt);
	}

}
