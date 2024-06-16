package ITBookFleaMarket.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ITBookFleaMarket.common.DBConn;
import ITBookFleaMarket.vo.BookVO;
import ITBookFleaMarket.vo.QuizVO;
import ITBookFleaMarket.vo.ReviewVO;
import ITBookFleaMarket.vo.TradePostVO;


public class ReviewDAO {
	//테이블에 데이터를 추가, 변경, 삭제, 조회, 전체목록
			private String query = "";
			private PreparedStatement pstmt;
			private boolean result;
			private ResultSet rs;
			
	//구매 후기 등록
	public boolean insertReview(ReviewVO rvo, int test) {
		query = " INSERT INTO t_review VALUES(seq_reviewno.NEXTVAL, "+ test +", ?, ?, SYSDATE) ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, rvo.getRate());
			pstmt.setString(2, rvo.getReviewcontent());
			
			//쿼리 실행 후 결과받기 + 반환
			result = pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//구매 후기 상세보기
	public ReviewVO reviewDetail(int test) {
		query = " SELECT * FROM t_review WHERE trlogno = "+ test;
		ReviewVO rvo = null;
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if (result = rs.next()) {
				rvo = new ReviewVO();
				rvo.setReviewno(rs.getInt("reviewno"));
				rvo.setTrlogno(rs.getInt("trlogno"));
				rvo.setRate(rs.getInt("rate"));
				rvo.setReviewcontent(rs.getString("reviewcontent"));
				rvo.setReviewdate(rs.getDate("reviewdate")); 
				
			}
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rvo;
	}
	//도서상세보기에서 도서이름 가져오기
	public BookVO bookName(int test) {
		query = " SELECT * FROM t_tradelog tl, t_book b WHERE tl.bookno = b.bookno AND trlogno = "+ test;
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
	
	//후기 수정
	public boolean reviewUpdate(ReviewVO rvo, int test) {
		query = " UPDATE t_review SET rate = ?, reviewcontent = ?, reviewdate = SYSDATE WHERE trlogno = "+ test ;
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, rvo.getRate());
			pstmt.setString(2, rvo.getReviewcontent());
			
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//후기 삭제
	public boolean reviewDelete(int test) {
		try {
			query = " DELETE FROM t_review WHERE trlogno = "+ test ;
			pstmt = DBConn.getConnection().prepareStatement(query);
		
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	//구매 후기 전체목록(관리자)
	public List<ReviewVO> reviewList(int test) { 
		query = " SELECT * FROM t_review WHERE trlogno = "+ test;
		List<ReviewVO> rList = new ArrayList<ReviewVO>();
		 
		try { 
			pstmt = DBConn.getConnection().prepareStatement(query); 
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ReviewVO rvo = new ReviewVO();
				rvo.setReviewno(rs.getInt("reviewno")); 
				rvo.setTrlogno(rs.getInt("trlogno"));
				rvo.setRate(rs.getInt("rate"));
				rvo.setReviewcontent(rs.getString("reviewcontent"));
				rvo.setReviewdate(rs.getDate("reviewdate")); 
				
				rList.add(rvo); 
			}
			DBConn.close(pstmt, rs); 
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
		return rList; 
	}
	 
	//구매후기 상세보기(관리자)
	public ReviewVO reviewAdminDetail(int rno1) {
		query = " SELECT * FROM t_review WHERE reviewno = "+ rno1 ;
		ReviewVO rvo = null;
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if (result = rs.next()) {
				rvo = new ReviewVO();
				rvo.setReviewno(rs.getInt("reviewno"));
				rvo.setTrlogno(rs.getInt("trlogno"));
				rvo.setRate(rs.getInt("rate"));
				rvo.setReviewcontent(rs.getString("reviewcontent"));
				rvo.setReviewdate(rs.getDate("reviewdate")); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rvo;
	}
	
	//후기 삭제(관리자) ->후기번호로 확인 삭제
		public boolean reviewAdminDelete(int rno1) {
			try {
				query = " DELETE FROM t_review WHERE reviewno = "+ rno1 ;
				pstmt = DBConn.getConnection().prepareStatement(query);
			
				result = pstmt.executeUpdate() == 1;
				DBConn.close(pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
	
	
}
