package chatpack;

import java.awt.*;
import javax.swing.*;

public class MultiChatData {
	
	JTextArea msgOut;
	
	public void addObj(Component comp) {
		this.msgOut = (JTextArea)comp;
	}
	
	public void refreshData(String msg) {
		msgOut.append(msg);
	}

}
