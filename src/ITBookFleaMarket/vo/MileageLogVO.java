package ITBookFleaMarket.vo;

import java.sql.Date;

public class MileageLogVO {
	private int mllogno;
	private String userid;
	private String mllogtype;
	private int mlamount;
	private Date mllogdate;
	
	public int getMllogno() {
		return mllogno;
	}
	public void setMllogno(int mllogno) {
		this.mllogno = mllogno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMllogtype() {
		return mllogtype;
	}
	public void setMllogtype(String mllogtype) {
		this.mllogtype = mllogtype;
	}
	public int getMlamount() {
		return mlamount;
	}
	public void setMlamount(int mlamount) {
		this.mlamount = mlamount;
	}
	public Date getMllogdate() {
		return mllogdate;
	}
	public void setMllogdate(Date mllogdate) {
		this.mllogdate = mllogdate;
	}
	
	
}
