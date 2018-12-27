package productManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.PreparedStatement;

public class ProductDAO {

	String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	String jdbcUrl = "jdbc:mysql://localhost:3306/javadb?characterEncoding=UTF-8&serverTimezone=UTC";
	Connection conn;
	
	PreparedStatement pstmt;
	ResultSet rs;
	
	// 콤보박스 아이템 관리를 위한 벡터
	Vector<String> items = null;
	String sql;
	
	public ProductDAO() {
		connectDB();
	}
	
	public void connectDB() {
		try {
			Class.forName(jdbcDriver);
			
			conn = DriverManager.getConnection(jdbcUrl, "username", "password"); // url, username, password
			System.out.println("connect success");
			
			
			
		}
		catch(ClassNotFoundException e) {
			System.out.println("driver loading connect failed");
		}
		catch(SQLException e) {
			System.out.println("error: " + e);
		}
	}
	
	public void closeDB() {
		try{
			if(pstmt != null) {
				pstmt.close();
			}
			
			if(conn != null && ! conn.isClosed()) {
				pstmt.close();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Product> getAll() {
		//connectDB();
		sql = "select * from product";
		
		ArrayList<Product> datas = new ArrayList<Product>();
		
		items = new Vector<String>();
		items.add("전체");
		
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Product p = new Product();
				p.setPrcode(rs.getInt("prcode"));
				p.setPrname(rs.getString("prname"));
				p.setPrice(rs.getInt("price"));
				p.setManufacture(rs.getString("manufacture"));
				datas.add(p);
				items.addElement(String.valueOf(rs.getInt("prcode")));
			}
			
			
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			System.out.println("select query error in getAll method");
		}
		
		return datas;
		
	}
	
	public Product getProduct(int prcode) {
		sql = "select * from product where prcode=?";
		Product p = null;
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prcode);
			rs = pstmt.executeQuery();
			
			rs.next();
			p = new Product();
			p.setPrcode(rs.getInt("prcode"));
			p.setPrname(rs.getString("prname"));
			p.setPrice(rs.getInt("price"));
			p.setManufacture(rs.getString("manufacture"));
			
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			System.out.println("select query error in getProduct method");
		}
		
		return p;
		
	}
	
	public boolean newProduct(Product product) {
		sql = "insert into product (prname, price, manufacture) values (?, ?, ?)";
		boolean flag = false;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, product.getPrname());
			pstmt.setInt(2, product.getPrice());
			pstmt.setString(3, product.getManufacture());
			
			result = pstmt.executeUpdate();
			
			if(result != 0)
				flag = true;
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			System.out.println("error in newProduct method");
		}
		
		return flag;
	}
	
	public boolean delProduct(int prcode) {
		sql = "delete from product where prcode=?";
		boolean flag = false;
		int result = 0;
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prcode);
			result = pstmt.executeUpdate();
			
			if(result != 0)
				flag = true;
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			System.out.println("error in delProduct method");
		}
		
		return flag;
	}
	
	public boolean updateProduct(Product product) {
		sql = "update product set prname=?, price=?, manufacture=? where prcode=?";
		boolean flag = false;
		int result = 0;
		
		try{
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, product.getPrname());
			pstmt.setInt(2, product.getPrice());
			pstmt.setString(3, product.getManufacture());
			pstmt.setInt(4, product.getPrcode());
			result = pstmt.executeUpdate();
			
			if(result != 0)
				flag = true;
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return flag;
	}
	
	public Vector<String> getItems() {
		return items;
	}
}
