package notepadpack;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class NotePadMenuBar extends JMenuBar {
	NotePadMenuBar(NotePadTextArea npt) {
		
		NotePadFileMenu fm = new NotePadFileMenu(npt);
		NotePadViewMenu vm = new NotePadViewMenu(npt);
		NotePadHelpMenu hm = new NotePadHelpMenu(npt);
		
		this.add(fm);
		this.add(vm);
		this.add(hm);
		
	}

}
