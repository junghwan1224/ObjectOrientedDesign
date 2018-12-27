package notepadpack;

import java.awt.*;
import javax.swing.*;

public class NotePadMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		NotePadPanel panel = new NotePadPanel();
		NotePadMenuBar menubar = new NotePadMenuBar(panel.npt);
		
		frame.setTitle("Note pad");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menubar);
		frame.add(panel);
		frame.setSize(700, 700);
		frame.setVisible(true);
	}

}
