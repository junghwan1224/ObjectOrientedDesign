package chatpack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MultiChatUI extends JFrame {
	
	private JPanel loginPanel;
	
	protected JButton loginButton;
	
	private JLabel inLabel; // 대화명 라벨
	protected JLabel outLabel; // 대화명 출력 라벨
	protected JTextField idInput; // 대화명 입력 텍스트 필드
	
	private JPanel logoutPanel;
	protected JButton logoutButton;
	private JPanel msgPanel; // 메세지 입력 패널
	protected JTextField msgInput; // 메세지 입력 텍스트 필드
	protected JTextArea msgOut; // 메세지 내용 출력 text area
	protected JButton exitButton; // 종료 버튼
	
	protected Container tab; // 로그인, 로그아웃 상태를 담아줄 컨테이너
	protected CardLayout cardLayout; // 로그인, 로그아웃 상태를 카드 레이아웃으로 처리
	
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
		
		// 수직 스크롤바는 항상 나타내고 수평 스크롤바는 필요할 때 나타나도록 설계
		JScrollPane jsp = new JScrollPane(msgOut, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// 처음 접속할 땐 로그인으로, 로그인 뒤에는 로그아웃 패널을 보여주기 위해 카드 레이아웃 적용
		tab = new JPanel();
		cardLayout = new CardLayout();
		tab.setLayout(cardLayout);
		tab.add(loginPanel, "login");
		tab.add(logoutPanel, "logout");
		
		// JFrame을 상속한 MultiChatUI 클래스에 추가
		this.add(tab, BorderLayout.NORTH);
		this.add(msgOut, BorderLayout.CENTER);
		this.add(msgPanel, BorderLayout.SOUTH);
		
		// setVisible 메소드는 프레임에 컴포넌트들을 추가해준 뒤에 작성해야 결과물 화면에 표시된다.
		this.setVisible(true);
	
	}

	public void addButtonActionListener(ActionListener listener) { // 이벤트 핸들러 등록 메서드, 모든 버튼 이벤트를 여기서 등록
		loginButton.addActionListener(listener);
		logoutButton.addActionListener(listener);
		exitButton.addActionListener(listener);
		msgInput.addActionListener(listener);
	}

}
