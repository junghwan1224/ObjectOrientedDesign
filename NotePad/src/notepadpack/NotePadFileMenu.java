package notepadpack;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.awt.event.*;
import java.util.*;

public class NotePadFileMenu extends JMenu {
	
	NotePadTextArea text;
	JMenuItem newFile = new JMenuItem("새로 만들기");
	JMenuItem open = new JMenuItem("열기");
	JMenuItem save = new JMenuItem("저장");
	JMenuItem saveOther = new JMenuItem("다른 이름으로 저장");
	JMenuItem exitNote = new JMenuItem("끝내기");
	
	JFileChooser choosedCon = new JFileChooser();
    FileNameExtensionFilter filterCon = new FileNameExtensionFilter("TXT files", "txt");
    String filepath = null;
	
	public NotePadFileMenu(NotePadTextArea npt) {
		this.setText("File");
		
		this.text = npt;
		choosedCon.setFileFilter(filterCon);
		FileListener fl = new FileListener();
		
//		JMenuItem newFile = new JMenuItem("새로 만들기");
//		JMenuItem open = new JMenuItem("열기");
//		JMenuItem save = new JMenuItem("저장");
//		JMenuItem saveOther = new JMenuItem("다른 이름으로 저장");
//		JMenuItem exitNote = new JMenuItem("끝내기");
		
		open.addActionListener(fl);
		newFile.addActionListener(fl);
		save.addActionListener(fl);
		saveOther.addActionListener(fl);
		exitNote.addActionListener(fl);
		
		this.add(newFile);
		this.add(open);
		this.add(save);
		this.add(saveOther);
		this.add(exitNote);
	}
	
	private class FileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(open)) { // 열기
				JFileChooser fc = new JFileChooser();
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt files", "txt");
				fc.setFileFilter(filter);
				int fileOpen = fc.showOpenDialog(text);
				
				if(fileOpen == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					String strPath = selectedFile.getPath();
					BufferedReader br = null;
					try {
						br = new BufferedReader(new FileReader(strPath));
						String str;
						
						text.setText("");
						while((str = br.readLine()) != null) {
							text.setText(text.getText() + str + "\n");
						}
						
						br.close();
						
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			
			
			else if(e.getSource().equals(save)) { // 저장
				
			    if(filepath != null) { // 기존에 작성된 파일을 수정 후 저장하는 경우
			    	
			    	BufferedWriter bw;
			    	try {
			    		bw = new BufferedWriter(new FileWriter(filepath));
			    		
			    		for (String s : text.getText().split("\\n")){
				    		bw.write(s+"\r\n");
				    	}
			    		
			    		bw.close();
			    		
			    	}
			    	catch(Exception ex) {
			    		ex.printStackTrace();
			    	}
			    }
			    else { // 처음 파일을 저장하는 경우, 열기 버튼 눌러야 저장됨
			    	
			    	JFileChooser choosed = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");
				    choosed.setFileFilter(filter);
			    	
				    int returnVal = choosed.showOpenDialog(text);

				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    	try {
				    		File file = choosed.getSelectedFile();
					    	FileWriter fw = new FileWriter(file);
					    	
					    	for (String s : text.getText().split("\\n")){
					    		fw.write(s+"\r\n");
					    	}
					    	
					    	fw.close();
					    	
					    	filepath = file.getPath();
					    }
					    
					    catch(Exception ex) {
					    	ex.printStackTrace();
					    }
				    }
			    }
			}
			
			else if(e.getSource().equals(saveOther)) { // 다른 이름으로 저장, 열기 버튼을 눌러야 저장됨
				
				JFileChooser choosed = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");
			    choosed.setFileFilter(filter);
			    int returnVal = choosed.showOpenDialog(text);

			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	try {
			    		File file = choosed.getSelectedFile();
			    		
				    	FileWriter fw = new FileWriter(file);
				    	
				    	for (String s : text.getText().split("\\n")){
				    		fw.write(s+"\r\n");
				    	}
				    	
				    	fw.close();
				    }
				    
				    catch(Exception ex) {
				    	ex.printStackTrace();
				    }
			    }
			    
				
			}
			
			else if(e.getSource().equals(newFile)) { // 새로 만들기
				text.setText("");
			}
			
			else { // 종료
				System.exit(0);
			}
		}
	}

}
