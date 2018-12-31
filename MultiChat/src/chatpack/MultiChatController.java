package chatpack;

import java.util.logging.*;
import java.awt.event.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import com.google.gson.*;

public class MultiChatController implements Runnable {

	private final MultiChatUI v;
	private final MultiChatData chatData;
	
	private Logger logger;
	
	private String ip = "127.0.0.1";
	private Socket socket;
	private BufferedReader inMsg;
	private PrintWriter outMsg;
	private Gson gson = new Gson();
	private Message m;
	private Thread thread;
	private boolean status = true;
	
	public MultiChatController(MultiChatData chatData, MultiChatUI v) {
		
		logger = Logger.getLogger(this.getClass().getName());
		
		this.v = v;
		this.chatData = chatData;
	}
	
	public void appMain() {
		chatData.addObj(v.msgOut);
		
		v.addButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				
				if(obj == v.exitButton) {
					System.exit(0);
				}
				else if(obj == v.loginButton) {
					v.id = v.idInput.getText();
					v.outLabel.setText(" 대화명 : " + v.id);
					v.cardLayout.show(v.tab, "logout");
					connectServer();
				}
				else if(obj == v.logoutButton) {
					outMsg.println(gson.toJson(new Message(v.id, "", "", "logout")));
					
					v.msgOut.setText("");
					v.cardLayout.show(v.tab, "login");
					outMsg.close();
					
					try{
						inMsg.close();
						socket.close();
					}
					catch(IOException ex) {
						ex.printStackTrace();
					}
					
					status = false;
				}
				else if(obj == v.msgInput) {
					outMsg.println(gson.toJson(new Message(v.id, "", v.msgInput.getText(), "msg")));
					v.msgInput.setText("");
					chatData.refreshData(v.msgInput.getText());
				}
			}
		});
	}
	
	public void connectServer() {
		try{
			socket = new Socket(ip, 8888);
			System.out.println("connect success");
			
			inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outMsg = new PrintWriter(socket.getOutputStream(),true);
			
			m = new Message(v.id, "", "", "login");
			outMsg.println(gson.toJson(m));
			
			thread = new Thread(this);
			thread.start();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("connectServer error in controller class");
		}
	}
	
	public void run() {
		
		String msg;
		status = true;
		
		while(status) {
			
			try{
				msg = inMsg.readLine();
				m = gson.fromJson(msg, Message.class);
				
				chatData.refreshData(m.getId() + ">" + m.getMsg() + "\n");
				
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
		
		MultiChatController app = new MultiChatController(new MultiChatData(), new MultiChatUI());
		
		app.appMain();
	}

}
