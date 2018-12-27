package notepadpack;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class NotePadViewMenu extends JMenu {
	
	//public NotePadTextArea text = new NotePadTextArea();
	NotePadTextArea text;
	JMenuItem small = new JMenuItem("작게");
	JMenuItem medium = new JMenuItem("보통");
	JMenuItem big = new JMenuItem("크게");
	
	public NotePadViewMenu(NotePadTextArea npt) {
		this.setText("View");
//		JMenuItem small = new JMenuItem("작게");
//		JMenuItem medium = new JMenuItem("보통");
//		JMenuItem big = new JMenuItem("크게");
		
		this.text = npt;
		
		FontListener font = new FontListener();
		
		this.add(small);
		this.add(medium);
		this.add(big);
		
		small.addActionListener(font);
		medium.addActionListener(font);
		big.addActionListener(font);
	}
	
	private class FontListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(small)) { // 글씨 작게
				Font f = new Font("serif", Font.PLAIN, 10);
				text.setFont(f);
			}
			else if(e.getSource().equals(medium)) { // 글씨 보통
				Font f = new Font("serif", Font.PLAIN, 30);
				text.setFont(f);
			}
			else { // 글씨 크게
				Font f = new Font("serif", Font.PLAIN, 50);
				text.setFont(f);
			}
		}
	}
}
