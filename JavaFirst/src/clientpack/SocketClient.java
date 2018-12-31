package clientpack;

import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			Socket s = new Socket("127.0.0.1", 5000);
			System.out.println("## 클라이언트 실행");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			System.out.println("서버 메세지 : " + br.readLine());
			
			PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
			pw.println("클라이언트에서 전송");
			
			
			pw.close();
			s.close();
			br.close();
			System.out.println("## 클라이언트 종료");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
