package ITBookFleaMarket.vo;

import java.sql.Date;

public class ReviewVO {
	private int reviewno;
	private int trlogno;
	private int rate;
	private String reviewcontent;
	private Date reviewdate;
	
	
	public Date getReviewdate() {
		return reviewdate;
	}
	public void setReviewdate(Date reviewdate) {
		this.reviewdate = reviewdate;
	}
	public int getTrlogno() {
		return trlogno;
	}
	public void setTrlogno(int trlogno) {
		this.trlogno = trlogno;
	}
	public int getReviewno() {
		return reviewno;
	}
	public void setReviewno(int reviewno) {
		this.reviewno = reviewno;
	}

	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getReviewcontent() {
		return reviewcontent;
	}
	public void setReviewcontent(String reviewcontent) {
		this.reviewcontent = reviewcontent;
	}
	
	
}
