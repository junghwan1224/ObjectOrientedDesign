package DbTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventRegist er = new EventRegist();
//		Connection conn = null;
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver"); // 최신 버전은 가운데 cj 추가
//			
//			String url = "jdbc:mysql://localhost:3306/javadb?characterEncoding=UTF-8&serverTimezone=UTC";  // localhost/db이름
//			// ?부터는 인코딩 및 타임존 설정을 위해 추가.
//			conn = DriverManager.getConnection(url, "username", "password"); // url, username, password
//			System.out.println("connect success");
//			
//			
//			
//		}
//		catch(ClassNotFoundException e) {
//			System.out.println("driver loading connect failed");
//		}
//		catch(SQLException e) {
//			System.out.println("error: " + e);
//		}
//		finally {
//			try{
//				if(conn != null && ! conn.isClosed()) {
//					conn.close();
//				}
//			}
//			catch(SQLException e) {
//				e.printStackTrace();
//			}
//		}
	}

}
