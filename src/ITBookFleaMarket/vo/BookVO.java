package ITBookFleaMarket.vo;

public class BookVO {
	private int bookNo;
	private String bookName;
	private String bookGroup;
	private int bookPrice;
	private String bookState;
	private String author;
	private String publisher;
	private int isbn;
	private String seller;

	public int getBookNo() {
		return bookNo;
	}

	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookGroup() {
		return bookGroup;
	}

	public void setBookGroup(String bookGroup) {
		this.bookGroup = bookGroup;
	}

	public int getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getBookState() {
		return bookState;
	}

	public void setBookState(String bookState) {
		this.bookState = bookState;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public BookVO(int bookNo, String bookName, String bookGroup, int bookPrice, String bookState, 
					String author, String publisher, int isbn, String seller) {
		this.bookNo = bookNo;
		this.bookName = bookName;
		this.bookGroup = bookGroup;
		this.bookPrice = bookPrice;
		this.bookState = bookState;
		this.author = author;
		this.publisher = publisher;
		this.isbn = isbn;
		this.seller = seller;
	}

	public BookVO() {
	}
}
