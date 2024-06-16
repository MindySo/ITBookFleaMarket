package ITBookFleaMarket.vo;

import java.sql.Date;

public class TradeLogVO {
	private int trLog;
	private int bookNo;
	private int tradePrice;
	private Date tradeDate;
	private String tradeState;
	private Date invoiceNo;
	private String buyer;
	private int usedMileage;

	
	
	public int getTrLog() {
		return trLog;
	}

	public void setTrLog(int trLog) {
		this.trLog = trLog;
	}

	public int getBookNo() {
		return bookNo;
	}

	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	public int getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(int tradePrice) {
		this.tradePrice = tradePrice;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public Date getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(Date invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public int getUsedMileage() {
		return usedMileage;
	}

	public void setUsedMileage(int usedMileage) {
		this.usedMileage = usedMileage;
	}

	public TradeLogVO(int trLog, int bookNo, int tradePrice, Date tradeDate, 
					String tradeState, Date invoiceNo, String buyer, int usedMileage) {
		this.trLog = trLog;
		this.bookNo = bookNo;
		this.tradePrice = tradePrice;
		this.tradeDate = tradeDate;
		this.tradeState = tradeState;
		this.invoiceNo = invoiceNo;
		this.buyer = buyer;
		this.usedMileage = usedMileage;
	}

	public TradeLogVO() {
		
	}
}
