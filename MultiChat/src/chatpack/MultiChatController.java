package chatpack;

import java.util.logging.*;
import java.awt.event.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import com.google.gson.*;

public class MultiChatController implements Runnable {

	private final MultiChatUI v; // 뷰 클래스 참조 객체
	private final MultiChatData chatData; // 데이터 클래스 참조 객체
	
	private Logger logger;
	
	private String ip = "127.0.0.1"; // 테스트는 로컬 환경에서 실행
	private Socket socket;
	
	// 입출력 스트림
	private BufferedReader inMsg;
	private PrintWriter outMsg;
	
	// json 파서를 위한 Gson 라이브러리 이용
	private Gson gson;
	private Message m;
	
	// 메세지 수신을 위한 스레드
	private Thread thread;
	
	// 스레드 상태를 나타내기 위한 boolean 변수
	private boolean status = true;
	
	public MultiChatController(MultiChatData chatData, MultiChatUI v) {
		
		logger = Logger.getLogger(this.getClass().getName());
		
		this.v = v;
		this.chatData = chatData;
		
		this.gson = new Gson(); // Gson 인스턴스 생성
	}
	
	public void appMain() { // 컨트롤러 클래스의 메인 로직 부분, UI에서 발생한 이벤트를 위임받아 처리
		// 데이터 객체에서 테이터 변화를 처리할 UI 객체 추가
		chatData.addObj(v.msgOut);
		
		// UI 버튼 동작 리스너
		v.addButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				
				// 종료 버튼
				if(obj == v.exitButton) {
					System.exit(0); // 종료
				}
				
				// 로그인 버튼
				else if(obj == v.loginButton) {
					// 대화명 라벨에 입력한 대화명 표시
					v.id = v.idInput.getText();
					v.outLabel.setText(" 대화명 : " + v.id);
					
					// 로그인 했으므로 logout 패널을 띄워준다.
					v.cardLayout.show(v.tab, "logout");
					
					// 서버 연결
					connectServer();
				}
				
				// 로그아웃 버튼
				else if(obj == v.logoutButton) {
					// 로그아웃 메세지 전송
					outMsg.println(gson.toJson(new Message(v.id, "", "", "logout")));
					
					// 대화창 초기화
					v.msgOut.setText("");
					
					// 로그아웃 했으므로 로그인 패널 띄워준다.
					v.cardLayout.show(v.tab, "login");
					
					// 출력 스트림 close
					outMsg.close();
					
					try{
						// 로그아웃 했으므로 소켓 및 입력 스트림 close
						inMsg.close();
						socket.close();
					}
					catch(IOException ex) {
						ex.printStackTrace();
					}
					
					// 종료했으므로 status 값 false로 변경
					status = false;
				}
				
				// 메세지 입력할 때, 여기서 엔터 입력시 메세지 전송되는 것도 자동적으로 처리해주는 거 같음
				else if(obj == v.msgInput) {
					// 메세지 전송
					outMsg.println(gson.toJson(new Message(v.id, "", v.msgInput.getText(), "msg")));
					
					// 메세지 전송 했으니 메세지 입력 텍스트 초기화
					v.msgInput.setText("");
					
					// 보낸 메세지를 채팅창 화면에 띄워줘야 하니 refresh
					chatData.refreshData(v.msgInput.getText());
				}
			}
		});
	}
	
	public void connectServer() { // 채팅 서버 접속
		// 서버와 연결하고 입출력 스트림을 만든 후 수신에 필요한 스레드 생성
		
		try{
			
			// 소켓 생성
			socket = new Socket(ip, 8888);
			System.out.println("connect success");
			
			// 입출력 스트림 생성
			inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outMsg = new PrintWriter(socket.getOutputStream(),true);
			
			// 서버에 로그인 메세지 전달
			m = new Message(v.id, "", "", "login");
			outMsg.println(gson.toJson(m));
			
			// 메세지 수신을 위한 스레드 생성
			thread = new Thread(this);
			thread.start();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("connectServer error in controller class");
		}
	}
	
	public void run() { // 서버 연결 후 메세지 수신을 UI 동작과 상관 없이 독립적으로 처리하는 스레드 실행
		
		String msg; // 수신한 메세지를 저장할 변수
		status = true; // 스레드를 실행하여 메세지를 수신하므로 status 값은 true로 변경
		
		// 스레드가 종료될 때까지 반복문을 계속 실행하면서 서버에서 전송하는 메세지를 읽어온다.
		while(status) {
			
			try{
				
				// 메세지 수신
				msg = inMsg.readLine();
				
				// 수신된 메세지 파싱
				m = gson.fromJson(msg, Message.class);
				
				// 채팅창에 메세지 표시
				chatData.refreshData(m.getId() + ">" + m.getMsg() + "\n");
				
				// 커서를 현재 대화 메세지에 표시
				v.msgOut.setCaretPosition(v.msgOut.getDocument().getLength());
			}
			catch(IOException e) {
				System.out.println("run method error in controller class");
				e.printStackTrace();
			}
			
			
		}
		
		System.out.println(thread.getName() + " 메세지 수신 스레드 종료됨");
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 컨트롤러 객체 생성
		MultiChatController app = new MultiChatController(new MultiChatData(), new MultiChatUI());
		
		// UI 메인 로직 메소드 실행
		app.appMain();
	}

}
