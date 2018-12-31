package socketpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			ServerSocket sc = new ServerSocket(5000);
			System.out.println("## 서버 실행");
			while(true) {
				Socket s = sc.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				
				PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
				pw.println("서버에서 전송");
				
				System.out.println("## 클라이언트 연결됨");
				System.out.println("## 클라이언트 메세지 : " + br.readLine());
				br.close();
				pw.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
