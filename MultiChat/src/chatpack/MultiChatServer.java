package chatpack;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class MultiChatServer {

	private ServerSocket ss = null;
	private Socket s = null;
	
	// 연결된 클라이언트 스레드를 관리하는 ArrayList
	ArrayList<ChatThread> chatThreads = new ArrayList<ChatThread>();
	
	Logger logger;
	
	public MultiChatServer() {
		start(); // 해당 클래스 객체가 생성될 때 서버 실행
	}
	
	public void start() { // 서버의 메인 실행 메서드, 클라이언트 연결 및 스레드 생성 처리
		logger = Logger.getLogger(this.getClass().getName());
		
		try{
			// 서버 소켓 생성
			ss = new ServerSocket(8888);
			logger.info("MultiChatServer start");
			
			// 무한루프 돌리면서 클라이언트 연결 대기
			while(true) {
				s = ss.accept();
				
				// 연결된 클라이언트에 대해 스레드 클래스 생성
				ChatThread chat = new ChatThread();
				// ArrayList에 생성된 스레드 클래스 추가
				chatThreads.add(chat);
				// 스레드 시작
				chat.start();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.info("MultiChatServer start method error");
		}
	}
	
	public void msgSendAll(String msg) { // 서버가 수신한 메세지를 연결된 모든 클라이언트에 전송하는 메서드
		for(ChatThread ct : chatThreads) {
			ct.outMsg.println(msg);
		} // 연결된 모든 클라이언트에 메세지 중계
	}
	
	/* 각 클라이언트와 연결을 유지하며, 메세지 송수신을 담당하는 스레드 클래스 */
	class ChatThread extends Thread {
		String msg; // 수신 메세지 및 파싱 메세지 처리를 위한 변수
		Message m; // 메세지 객체
		Gson gson = new Gson(); // json 파서를 위해 gson 라이브러리 사용
		
		// 입출력 스트림
		private BufferedReader inMsg = null;
		private PrintWriter outMsg = null;
		
		
		public void run() { // 스레드 동작 메소드, 생성된 각 스레드에서 따로 동작
			boolean status = true; // 스레드 상태를 나타내기 위한 boolean 변수
			
			try{
				// 입출력 스트림 초기화, 안해주면 NullPointer 에러 발생
				inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
				outMsg = new PrintWriter(s.getOutputStream(), true);
				
				while(true) {
					
					msg = inMsg.readLine(); // 수신된 메세지 저장
					m = gson.fromJson(msg, Message.class); // json 형식을 Message 클래스 객체로 매핑
					
					if(m.getType().equals("logout")) { // 메세지 타입이 logout 일 때
						chatThreads.remove(this); // 해당 클라이언트 스레드 종료
						msgSendAll(gson.toJson(new Message(m.getId(), "", "님이 종료했습니다.", "server")));
						// 채팅창에 해당 유저가 종료한 것을 표시
						
						status = false; // 스레드를 종료 했으므로 status 값을 false로 변경
						
						// 루프를 벗어나면 클라이언트 연결이 종료되므로 스레드 인터럽트를 해준다.
						// 근데 루프 밖(밑)에 적어주면 이상하게 에러가 발생(빨간줄 막 뜬다)하여 로그아웃 시 종료를 하는 것으로 일단 해놓았다.
						this.interrupt();
						logger.info(this.getName() + " 종료됨");
					}
					
					else if(m.getType().equals("login")) { // 메세지 타입이 login 일 때 
						msgSendAll(gson.toJson(new Message(m.getId(), "", "님이 로그인했습니다.", "server")));
						// 유저가 접속한 것을 채팅 창에 표시
					}
					
					else { // 나머지 타입은 메세지를 보내는 것을 의미하므로
						msgSendAll(msg); // 수신된 메세지를 그대로 다른 클라이언트들에게 보내준다.
					}
					
					
				} // while
				
//				this.interrupt();
//				logger.info(this.getName() + " 종료됨");
				
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("error in ChatThread in run method");
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 서버 실행
		MultiChatServer server= new MultiChatServer();
	}
}
