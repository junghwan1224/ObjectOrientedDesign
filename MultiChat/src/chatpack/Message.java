package chatpack;

/* 클라이언트와 서버 간의 통신에 사용하는 json 규격의 메세지를 쉽게 사용하기 위해 
 * 자바 객체로 변환하기 위해 사용하는 클래스
 * */
public class Message {
	
	private String id;
	private String password;
	private String msg;
	private String type; // 메세지 유형 - 로그인, 로그아웃, 메세지 전달
	
	public Message(String id, String password, String msg, String type) {
		this.id = id;
		this.password = password;
		this.msg = msg;
		this.type = type;
	}
	
	// get
	public String getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public String getType() {
		return type;
	}
	
	// set
	public void setId(String id) {
		this.id = id;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void setType(String type) {
		this.type = type;
	}

}
