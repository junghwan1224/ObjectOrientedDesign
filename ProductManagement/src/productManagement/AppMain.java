package productManagement;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class AppMain {
		
	JFrame jf;
	JPanel p1,p2,p3;
	JLabel ml;
	
	JTextArea ta;
	JScrollPane scroll;
	JComboBox cb;
	
	JTextArea prnameTa, priceTa, manuTa;
	
	JButton enrollbtn;
	JButton searchbtn;
	JButton updatebtn;
	JButton delbtn;
	
	boolean editmode;
	ProductDAO dao;
	ArrayList<Product> datas;
	Vector<String> items;
	
	public AppMain() {
		dao = new ProductDAO();
		datas = new ArrayList<Product>();
		items = new Vector<String>();
	}
	
	public void startUI() {
		jf = new JFrame();
		jf.setTitle("상품 관리 프로그램");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(700, 400);
		jf.setVisible(true);
		jf.setLayout(new BorderLayout());
		
		ml = new JLabel();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		cb = new JComboBox();
		ta = new JTextArea(10, 40);
		scroll = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		ButtonClick bc = new ButtonClick();
		enrollbtn = new JButton("등록");
		searchbtn = new JButton("조회");
		updatebtn = new JButton("수정");
		delbtn = new JButton("삭제");
		
		enrollbtn.addActionListener(bc);
		searchbtn.addActionListener(bc);
		updatebtn.addActionListener(bc);
		delbtn.addActionListener(bc);
		
		jf.add(ml, BorderLayout.PAGE_START);
		jf.add(p1, BorderLayout.LINE_START);
		jf.add(p2, BorderLayout.CENTER);
		jf.add(scroll, BorderLayout.LINE_END);
		jf.add(p3, BorderLayout.PAGE_END);
		
		JLabel lb1 = new JLabel("관리번호");
		JLabel lb2 = new JLabel("상품명");
		JLabel lb3 = new JLabel("단가");
		JLabel lb4 = new JLabel("제조사");
		
		p1.setLayout(new GridLayout(4,1));
		p1.add(lb1);
		p1.add(lb2);
		p1.add(lb3);
		p1.add(lb4);
		
		prnameTa = new JTextArea(20,20);
		priceTa = new JTextArea(20,20);
		manuTa = new JTextArea(20,20);
		
		p2.setLayout(null);
		p2.add(cb);
		p2.add(prnameTa);
		p2.add(priceTa);
		p2.add(manuTa);
		
		cb.setBounds(30, 33, 120, 40);
		prnameTa.setBounds(30, 105, 120, 40);
		priceTa.setBounds(30, 175, 120, 40);
		manuTa.setBounds(30, 245, 120, 40);
		
		p3.add(enrollbtn);
		p3.add(searchbtn);
		p3.add(updatebtn);
		p3.add(delbtn);
		
		ml.setText("상품 관리 프로그램입니다.");
		
		refreshData();
	}
	
	public void refreshData() {
		ta.setText("");
		clearField();
		editmode = false;
		
		ta.append("관리번호\t상품명\t\t단가\t제조사\n");
		datas = dao.getAll();
		
		cb.setModel(new DefaultComboBoxModel(dao.getItems()));;
		
		if(datas != null) {
			for(Product p: datas) {
				StringBuffer sb = new StringBuffer();
				sb.append(p.getPrcode() + "\t");
				sb.append(p.getPrname() + "\t\t");
				sb.append(p.getPrice() + "\t");
				sb.append(p.getManufacture() + "\n");
				ta.append(sb.toString());
			}
		} // if
		
		else {
			ta.append("등록된 상품이 없습니다. \n 상품을 등록해 주세요.");
		} // else
	}
	
	public void clearField() {
		prnameTa.setText("");
		priceTa.setText("");
		manuTa.setText("");
	}
	
	private class ButtonClick implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == enrollbtn) {
				Product p = new Product();
				boolean newFlag = false;
				
				p.setPrname(prnameTa.getText());
				p.setPrice(Integer.parseInt(priceTa.getText()));
				p.setManufacture(manuTa.getText());
				
				newFlag = dao.newProduct(p);
				
				if(newFlag)
					refreshData();
			} // enroll button if
			
			else if(e.getSource() == searchbtn) {
				String cbValStr = (String)cb.getSelectedItem();
				Product p = new Product();
				
				if(cbValStr == "전체" || cbValStr.length() == 0) {
					refreshData();
				}
				
				else {
					int cbVal = Integer.parseInt(cbValStr);
					p = dao.getProduct(cbVal);
					if(p != null) {
						prnameTa.setText(p.getPrname());
						priceTa.setText(String.valueOf(p.getPrice()));
						manuTa.setText(p.getManufacture());
					}
					else {
						System.out.println("일치하는 결과가 없음");
						System.out.println(cbVal);
						refreshData();
					}
				}
				
				
			} // search btn else if
			
			else if(e.getSource() == updatebtn) {
				int cbVal =  Integer.parseInt((String)cb.getSelectedItem());
				Product p = new Product();
				boolean updateFlag = false;
				
				p.setPrcode(cbVal);
				p.setPrname(prnameTa.getText());
				p.setPrice(Integer.parseInt(priceTa.getText()));
				p.setManufacture(manuTa.getText());
				
				updateFlag = dao.updateProduct(p);
				
				if(updateFlag)
					refreshData();
			} // update btn else if
			
			else {
				int cbVal =  Integer.parseInt((String)cb.getSelectedItem());
				boolean delFlag = false;
				
				delFlag = dao.delProduct(cbVal);
				
				if(delFlag)
					refreshData();
			} // delete btn else
		}
	}
	
	protected void finalize() throws Throwable {
		dao.closeDB();
		super.finalize();
	}

}
