package ITBookFleaMarket.vo;

import java.sql.Date;

public class MemberVO {
	private static MemberVO instance;
	private String id;
	private String pw;
	private String name;
	private String email;
	private String accountNo;
	private int reportNo;
	private boolean isInactivated;
	private Date inactiveDate;
	private boolean isAdmin;
	private Date joinDate;
	private int mileage;
	private int purchaseCnt;
	private int saleCnt;
	
	private MemberVO() { };
	
	public MemberVO(String id, String pw, String name, String email, String accountNo) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.accountNo = accountNo;
	}
	
	public MemberVO(String id, String pw, String name, String email, String accountNo,
			boolean isInactivated, int mileage) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.accountNo = accountNo;
		this.isInactivated = isInactivated;
		this.mileage = mileage;
	}
	
	public static MemberVO getInstance() {
		if (instance == null) {
			instance = new MemberVO();
		}
		return instance;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public int getReportNo() {
		return reportNo;
	}

	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}

	public boolean isInactivated() {
		return isInactivated;
	}

	public void setInactivated(boolean isInactivated) {
		this.isInactivated = isInactivated;
	}

	public Date getInactiveDate() {
		return inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public int getPurchaseCnt() {
		return purchaseCnt;
	}

	public void setPurchaseCnt(int purchaseCnt) {
		this.purchaseCnt = purchaseCnt;
	}

	public int getSaleCnt() {
		return saleCnt;
	}

	public void setSaleCnt(int saleCnt) {
		this.saleCnt = saleCnt;
	}

	public static void setInstance(MemberVO instance) {
		MemberVO.instance = instance;
	}
	
	
}
