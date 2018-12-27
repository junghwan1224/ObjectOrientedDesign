package DbTest;

import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class EventRegist {
	Connection conn = null;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public EventRegist() {
		System.out.println("## 이벤트 등록을 위해 이름과 이메일을 입력하세요");
		System.out.print("이름: ");
		String uname = new Scanner(System.in).next();
		System.out.print("이메일: ");
		String email = new Scanner(System.in).next();
		
		connectDB();
		registUser(uname, email);
		printList();
		closeDB();
		
	}
	
	public void connectDB() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // 최신 버전은 가운데 cj 추가
			
			String url = "jdbc:mysql://localhost:3306/javadb?characterEncoding=UTF-8&serverTimezone=UTC";  // localhost/db이름
			// ?부터는 인코딩 및 타임존 설정을 위해 추가.
			conn = DriverManager.getConnection(url, "username", "password"); // url, username, password
			System.out.println("connect success");
			
			
			
		}
		catch(ClassNotFoundException e) {
			System.out.println("driver loading connect failed");
		}
		catch(SQLException e) {
			System.out.println("error: " + e);
		}
	}
	
	public void registUser(String uname, String email) {
		String sql = "insert into event values(?, ?)";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uname);
			pstmt.setString(2, email);
			pstmt.executeUpdate();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void printList() {
		System.out.println("# 등록자 명단");
		String sql = "select * from event";
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("uname") + "," + rs.getString(2));
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void closeDB() {
		try{
			if(conn != null && ! conn.isClosed()) {
				conn.close();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
