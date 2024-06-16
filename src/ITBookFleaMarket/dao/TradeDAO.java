package ITBookFleaMarket.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ITBookFleaMarket.common.DBConn;
import ITBookFleaMarket.vo.BookVO;
import ITBookFleaMarket.vo.MemberVO;
import ITBookFleaMarket.vo.TradeLogVO;
import ITBookFleaMarket.vo.TradePostVO;

public class TradeDAO {
	private String query;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private boolean result;

	public List<TradePostVO> selectMemberPost(String seller) { // 내가 등록한 거래글 조회 by seller(사용자)
		query = "SELECT postno, t.bookno, trlogno, memo, postregdate, posthitno "
				+ "FROM t_tradepost t, t_book b WHERE  t.bookno = b.bookno AND seller = '" + seller + "'";

		List<TradePostVO> tpvoList = new ArrayList<TradePostVO>();

		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery(query);

			while (rs.next()) {
				TradePostVO tpvo = new TradePostVO();
				tpvo.setPostNo(rs.getInt("postNo"));
				tpvo.setBookNo(rs.getInt("bookNo"));
				tpvo.setTrLogNo(rs.getInt("trLogNo"));
				tpvo.setMemo(rs.getString("memo"));
				tpvo.setPostRegdate(rs.getDate("postRegdate"));
				tpvo.setPostHitNo(rs.getInt("postHitNo"));
				tpvoList.add(tpvo);
			}
			DBConn.close(pstmt, rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tpvoList; // 목록
	}

	// 거래글 수정
	public boolean updatePost(TradePostVO tvo, int postNo) { // 거래글 수정_tradepost
		try {
			query = "UPDATE t_tradepost SET memo = ? WHERE postno = " + postNo;
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, tvo.getMemo());

			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean updatePost(BookVO bvo, int postNo) { // 거래글 수정_book
		try {
			query = "UPDATE t_book SET bookprice = ?, bookstate = ? WHERE bookno = (SELECT bookno FROM t_tradepost WHERE postno ="
					+ postNo + ")";

			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, bvo.getBookPrice());
			pstmt.setString(2, bvo.getBookState());

			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 거래글 삭제
	public boolean updatePostLog(int postno) { // 거래글 삭제 시 거래내역 업데이트(선)
		try {
			query = "UPDATE t_tradelog SET tradestate = '글삭제' "
					+ "WHERE trlogno = (SELECT trlogno FROM t_tradepost WHERE postno = " + postno + ")";

			pstmt = DBConn.getConnection().prepareStatement(query);
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean deletePost(int postno) { // 거래글 삭제(후)
		query = " DELETE FROM t_tradepost WHERE postno = " + postno;
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int bringbookNo(int isbn, String seller) { // bookNO 반환 메서드
		query = "SELECT bookno FROM t_book WHERE isbn = " + isbn + " AND seller = '" + seller + "'";
		int booknocurr = 0;
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery(query);
			if (rs.next()) {
				booknocurr = rs.getInt("bookNo");
			}
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return booknocurr;
	}

	// 거래글 등록
	public boolean bookSellerCheck(int isbn, String loginId) { // 판매자의 동일 책이 있는지 조회
		query = "SELECT * FROM t_book WHERE isbn = " + isbn + " AND seller = '" + loginId + "'";
		BookVO bvo = null;
		boolean result = false;
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery(query);
			if (rs.next()) {
				result = false;
			} else
				result = true;
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean makePost(BookVO bvo, String seller) { // 거래글 등록_book(1)
		query = "INSERT INTO t_book VALUES(SEQ_bookNo.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, '" + seller + "')";

		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, bvo.getBookName());
			pstmt.setString(2, bvo.getBookGroup());
			pstmt.setInt(3, bvo.getBookPrice());
			pstmt.setString(4, bvo.getBookState());
			pstmt.setString(5, bvo.getAuthor());
			pstmt.setString(6, bvo.getPublisher());
			pstmt.setInt(7, bvo.getIsbn());

			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean makePost(TradeLogVO tlvo, int bookNoinput) { // 거래글 등록_tradeLog(2)
		query = "INSERT INTO t_tradelog(trlogno, bookno, tradestate) VALUES(SEQ_trLogNo.NEXTVAL, " + bookNoinput
				+ ", '판매중')";
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean makePost(TradePostVO tvo, int bookNoinput) { // 거래글 등록_tradepost(3)
		query = "INSERT INTO t_tradepost VALUES(SEQ_postNo.NEXTVAL, " + bookNoinput
				+ ", (SELECT trlogno FROM t_tradelog WHERE bookno = " + bookNoinput + "), ?, SYSDATE, 0)";

		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, tvo.getMemo());

			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<TradePostVO> selectTradePost(String order) { // 전체 거래글 목록 조회(조회수/등록순)
		query = "SELECT * FROM t_tradePost ORDER BY " + order;

		List<TradePostVO> evoList = new ArrayList<TradePostVO>();

		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery(query);

			while (rs.next()) {
				TradePostVO tpvo = new TradePostVO();
				tpvo.setPostNo(rs.getInt("postNo"));
				tpvo.setBookNo(rs.getInt("bookNo"));
				tpvo.setTrLogNo(rs.getInt("trLogNo"));
				tpvo.setMemo(rs.getString("memo"));
				tpvo.setPostRegdate(rs.getDate("postRegdate"));
				tpvo.setPostHitNo(rs.getInt("postHitNo"));
				evoList.add(tpvo);
			}
			DBConn.close(pstmt, rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return evoList; // 목록
	}

	public List<TradePostVO> selectTradePost(String groupInput, String strInput) { // 거래글 검색(분류 + 문자열)
		query = "SELECT postno, p.bookno, trlogno, memo, postregdate, posthitno " + "FROM t_tradePost p JOIN t_book b "
				+ "ON p.bookno = b.bookno " + "WHERE bookGroup LIKE '%" + groupInput + "%' AND (bookname LIKE '%"
				+ strInput + "%' OR author LIKE '%" + strInput + "%' OR publisher LIKE '%" + strInput
				+ "%' OR seller LIKE '%" + strInput + "%')";
		List<TradePostVO> evoList = new ArrayList<TradePostVO>();

		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery(query);

			while (rs.next()) {
				TradePostVO tpvo = new TradePostVO();
				tpvo.setPostNo(rs.getInt("postNo"));
				tpvo.setBookNo(rs.getInt("bookNo"));
				tpvo.setTrLogNo(rs.getInt("trLogNo"));
				tpvo.setMemo(rs.getString("memo"));
				tpvo.setPostRegdate(rs.getDate("postRegdate"));
				tpvo.setPostHitNo(rs.getInt("postHitNo"));
				evoList.add(tpvo);
			}
			DBConn.close(pstmt, rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return evoList; // 목록
	}

	public TradePostVO selectTradePost(int postNo) { // 거래글 상세 조회
		query = "SELECT * FROM t_tradePost WHERE postNo = " + postNo;
		TradePostVO tpvo = null;
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery(query);
			if (rs.next()) {
				tpvo = new TradePostVO();
				tpvo.setPostNo(rs.getInt("postNo"));
				tpvo.setBookNo(rs.getInt("bookNo"));
				tpvo.setTrLogNo(rs.getInt("trLogNo"));
				tpvo.setMemo(rs.getString("memo"));
				tpvo.setPostRegdate(rs.getDate("postRegdate"));
				tpvo.setPostHitNo(rs.getInt("postHitNo"));
			}
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tpvo;
	}

	public boolean addHitNo(int postNo) {	//거래글 조회수 올리기
		try {
			query = "UPDATE t_tradepost SET posthitno = posthitno + 1 WHERE postno = " + postNo;

			pstmt = DBConn.getConnection().prepareStatement(query);
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public BookVO selectBook(int bno) { // 도서조회
		query = "SELECT * FROM t_book WHERE bookNo = " + bno;
		BookVO bvo = null;
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery(query);
			if (rs.next()) {
				bvo = new BookVO();
				bvo.setBookNo(rs.getInt("bookNo"));
				bvo.setBookName(rs.getString("bookName"));
				bvo.setBookGroup(rs.getString("bookGroup"));
				bvo.setBookPrice(rs.getInt("bookPrice"));
				bvo.setBookState(rs.getString("bookState"));
				bvo.setAuthor(rs.getString("author"));
				bvo.setPublisher(rs.getString("publisher"));
				bvo.setIsbn(rs.getInt("isbn"));
				bvo.setSeller(rs.getString("seller"));
			}
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bvo;
	}

	public int bringMileage(String loginId) {	// 마일리지 반환 메서드
		query = "SELECT mileage FROM t_member WHERE ID = '" + loginId + "'";
		int userMileage = 0;
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery(query);
			if (rs.next()) {
				userMileage = rs.getInt("mileage");
			}
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userMileage;
	}
	
	public boolean transactionIn(MemberVO mvo, int finalPrice) { // 입금 처리
		query = "INSERT INTO t_account VALUES(SEQ_transactionNo.NEXTVAL, '입금', '" + mvo.getName() + "', '" + mvo.getId()
				+ "', " + finalPrice + "', SYSDATE)";
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean updateMileage(String loginId, int usedMg, int earnedMg) {	//회원테이블 마일리지 업데이트
		query = "UPDATE t_member SET mileage = mileage " + (-usedMg) + earnedMg + "WHERE id = '" + loginId;
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean addMileageLog(String loginId, String type, int amount) {	//마일리지 내역 추가
		query = "INSERT INTO t_mileage_log VALUES(SEQ_MILOGNO.NEXTVAL, '" + loginId + "', '" + type + "', " + amount + ", SYSDATE)";

		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public boolean reportMember(String seller) { // 사용자 신고
		query = "UPDATE t_member SET reportno = reportno + 1)";
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 전체 거래 내역
	// 거래 내역 상세 보기
	// 특정 회원의 거래 내역
	// 특정 회원 거래 내역 상세보기
}
