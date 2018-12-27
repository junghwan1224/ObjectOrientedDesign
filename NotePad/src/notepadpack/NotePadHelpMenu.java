package notepadpack;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class NotePadHelpMenu extends JMenu {
	public NotePadHelpMenu(NotePadTextArea npt) {
		this.setText("Help");
		JMenuItem pinfo = new JMenuItem("프로그램 정보");
		this.add(pinfo);
	}

}
