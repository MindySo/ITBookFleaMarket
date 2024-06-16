package ITBookFleaMarket.vo;

import java.util.Date;

public class QuizeAttendVO {
	private int qzattendno;
	private int qzno;
	private String id;
	private int qzanswer;
	private Date qzattenddate;
	
	public QuizeAttendVO() { }
	
	public QuizeAttendVO(int qzno, String id, int qzanswer) {
		this.qzno = qzno;
		this.id = id;
		this.qzanswer = qzanswer;
	}
	
	public int getQzattendno() {
		return qzattendno;
	}
	public void setQzattendno(int qzattendno) {
		this.qzattendno = qzattendno;
	}
	public int getQzno() {
		return qzno;
	}
	public void setQzno(int qzno) {
		this.qzno = qzno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getQzanswer() {
		return qzanswer;
	}
	public void setQzanswer(int qzanswer) {
		this.qzanswer = qzanswer;
	}
	public Date getQzattenddate() {
		return qzattenddate;
	}
	public void setQzattenddate(Date qzattenddate) {
		this.qzattenddate = qzattenddate;
	}
	
	
}
