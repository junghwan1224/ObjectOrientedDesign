package chatpack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MultiChatUI extends JFrame {
	
	private JPanel loginPanel;
	
	protected JButton loginButton;
	
	private JLabel inLabel;
	protected JLabel outLabel;
	protected JTextField idInput;
	
	private JPanel logoutPanel;
	protected JButton logoutButton;
	private JPanel msgPanel;
	protected JTextField msgInput;
	protected JTextArea msgOut;
	protected JButton exitButton;
	
	protected Container tab;
	protected CardLayout cardLayout;
	
	protected String id;
	
	public MultiChatUI() {
		
		super("::멀티챗::");
		this.setLayout(new BorderLayout());
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loginPanel = new JPanel();
		loginPanel.setLayout(new BorderLayout());
		
		idInput = new JTextField(15);
		loginButton = new JButton("로그인");
		
		inLabel = new JLabel("대화명 ");
		loginPanel.add(inLabel, BorderLayout.WEST);
		loginPanel.add(idInput, BorderLayout.CENTER);
		loginPanel.add(loginButton, BorderLayout.EAST);
		
		logoutPanel = new JPanel();
		logoutPanel.setLayout(new BorderLayout());
		
		outLabel = new JLabel();
		logoutButton = new JButton("로그아웃");
		
		logoutPanel.add(outLabel, BorderLayout.CENTER);
		logoutPanel.add(logoutButton, BorderLayout.EAST);
		
		msgPanel = new JPanel();
		msgPanel.setLayout(new BorderLayout());
		
		msgInput = new JTextField(15);
		exitButton = new JButton("종료");
		
		msgPanel.add(msgInput, BorderLayout.CENTER);
		msgPanel.add(exitButton, BorderLayout.EAST);
		
		msgOut = new JTextArea("", 10, 30);
		msgOut.setEditable(false);
		
		JScrollPane jsp = new JScrollPane(msgOut, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		tab = new JPanel();
		cardLayout = new CardLayout();
		tab.setLayout(cardLayout);
		tab.add(loginPanel, "login");
		tab.add(logoutPanel, "logout");
		
		
		this.add(tab, BorderLayout.NORTH);
		this.add(msgOut, BorderLayout.CENTER);
		this.add(msgPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	
	}

	public void addButtonActionListener(ActionListener listener) {
		loginButton.addActionListener(listener);
		logoutButton.addActionListener(listener);
		exitButton.addActionListener(listener);
		msgInput.addActionListener(listener);
	}

}
