package ITBookFleaMarket.vo;

import java.sql.Date;

public class TradePostVO {
	private int postNo;
	private int bookNo;
	private int trLogNo;
	private String memo;
	private Date postRegdate;
	private int postHitNo;

	
	
	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public int getBookNo() {
		return bookNo;
	}

	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	public int getTrLogNo() {
		return trLogNo;
	}

	public void setTrLogNo(int trLogNo) {
		this.trLogNo = trLogNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getPostRegdate() {
		return postRegdate;
	}

	public void setPostRegdate(Date postRegdate) {
		this.postRegdate = postRegdate;
	}

	public int getPostHitNo() {
		return postHitNo;
	}

	public void setPostHitNo(int postHitNo) {
		this.postHitNo = postHitNo;
	}

	public TradePostVO(int postNo, int bookNo, int trLogNo, String memo, Date postRegdate, int postHitNo) {
		this.postNo = postNo;
		this.bookNo = bookNo;
		this.trLogNo = trLogNo;
		this.memo = memo;
		this.postRegdate = postRegdate;
		this.postHitNo = postHitNo;
	}

	public TradePostVO() {
	}
}
