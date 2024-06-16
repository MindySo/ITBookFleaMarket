package ITBookFleaMarket.vo;

import java.sql.Date;

public class QuizVO {
	private int qzno;
	private String qztitle;
	private String qznum1;
	private String qznum2;
	private String qznum3;
	private int qzcorrectnum;
	private int qzreward;
	private Date qzstartdate;
	private Date enddate;
	private Date qzregdate;
	private Date qzmoddate;
	
	@Override
	public String toString() {
		return "QuizVO [qzno=" + qzno + ", qztitle=" + qztitle + ", qzcorrectnum=" + qzcorrectnum + ", qzreward="
				+ qzreward + ", qzstartdate=" + qzstartdate + ", enddate=" + enddate + ", qzregdate=" + qzregdate
				+ ", qzmoddate=" + qzmoddate + "]";
	}
	public int getQzno() {
		return qzno;
	}
	public void setQzno(int qzno) {
		this.qzno = qzno;
	}
	public String getQztitle() {
		return qztitle;
	}
	public void setQztitle(String qztitle) {
		this.qztitle = qztitle;
	}
	public String getQznum1() {
		return qznum1;
	}
	public void setQznum1(String qznum1) {
		this.qznum1 = qznum1;
	}
	public String getQznum2() {
		return qznum2;
	}
	public void setQznum2(String qznum2) {
		this.qznum2 = qznum2;
	}
	public String getQznum3() {
		return qznum3;
	}
	public void setQznum3(String qznum3) {
		this.qznum3 = qznum3;
	}
	public int getQzcorrectnum() {
		return qzcorrectnum;
	}
	public void setQzcorrectnum(int qzcorrectnum) {
		this.qzcorrectnum = qzcorrectnum;
	}
	public int getQzreward() {
		return qzreward;
	}
	public void setQzreward(int qzreward) {
		this.qzreward = qzreward;
	}
	public Date getQzstartdate() {
		return qzstartdate;
	}
	public void setQzstartdate(Date qzstartdate) {
		this.qzstartdate = qzstartdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public Date getQzregdate() {
		return qzregdate;
	}
	public void setQzregdate(Date qzregdate) {
		this.qzregdate = qzregdate;
	}
	public Date getQzmoddate() {
		return qzmoddate;
	}
	public void setQzmoddate(Date qzmoddate) {
		this.qzmoddate = qzmoddate;
	}
	
}
