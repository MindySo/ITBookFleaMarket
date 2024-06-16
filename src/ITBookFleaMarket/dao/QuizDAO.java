package ITBookFleaMarket.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ITBookFleaMarket.common.DBConn;
import ITBookFleaMarket.vo.MileageLogVO;
import ITBookFleaMarket.vo.QuizVO;
import ITBookFleaMarket.vo.QuizeAttendVO;

public class QuizDAO {
	//테이블에 데이터를 추가, 변경, 삭제, 조회, 전체목록
		private String query = "";
		private PreparedStatement pstmt;
		private boolean result;
		private ResultSet rs;
		
	//퀴즈 전체보기(관리자)
	public List<QuizVO> quizList() {
		query = " SELECT * FROM t_quiz ";
		List<QuizVO> qvoList = new ArrayList<QuizVO>();
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				QuizVO qvo = new QuizVO();
				qvo.setQzno(rs.getInt("qzno"));
				qvo.setQztitle(rs.getString("qztitle"));
				qvo.setQznum1(rs.getString("qznum1"));
				qvo.setQznum2(rs.getString("qznum2"));
				qvo.setQznum3(rs.getString("qznum3"));
				qvo.setQzcorrectnum(rs.getInt("qzcorrectnum"));
				qvo.setQzreward(rs.getInt("qzreward"));
				qvo.setQzstartdate(rs.getDate("qzstartdate"));
				qvo.setEnddate(rs.getDate("enddate"));
				qvo.setQzregdate(rs.getDate("qzregdate"));
				qvo.setQzmoddate(rs.getDate("qzmoddate"));
				qvoList.add(qvo);
			}
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qvoList;
	}
	
	//퀴즈 상세보기
	public QuizVO quizOneList(String qzno) {
		int re = Integer.parseInt(qzno);
		query = " SELECT * FROM t_quiz WHERE qzno = "+ re ;
		QuizVO qvo = null;
			
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if (result = rs.next()) {
				qvo = new QuizVO();
				qvo.setQzno(rs.getInt("qzno"));
				qvo.setQztitle(rs.getString("qztitle"));
				qvo.setQznum1(rs.getString("qznum1"));
				qvo.setQznum2(rs.getString("qznum2"));
				qvo.setQznum3(rs.getString("qznum3"));
				qvo.setQzcorrectnum(rs.getInt("qzcorrectnum"));
				qvo.setQzreward(rs.getInt("qzreward"));
				qvo.setQzstartdate(rs.getDate("qzstartdate"));
				qvo.setEnddate(rs.getDate("enddate"));
				qvo.setQzregdate(rs.getDate("qzregdate"));
				qvo.setQzmoddate(rs.getDate("qzmoddate"));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qvo;
			
	}
	
	//퀴즈글 등록
	public boolean insertQuiz(QuizVO qvo) {
		query = " INSERT INTO t_quiz VALUES(seq_qzno.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, NULL) ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, qvo.getQztitle());
			pstmt.setString(2, qvo.getQznum1());
			pstmt.setString(3, qvo.getQznum2());
			pstmt.setString(4, qvo.getQznum3());
			pstmt.setInt(5, qvo.getQzcorrectnum());
			pstmt.setInt(6, qvo.getQzreward());
			pstmt.setDate(7, qvo.getQzstartdate());
			pstmt.setDate(8, qvo.getEnddate());
			
			//쿼리 실행 후 결과받기 + 반환
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//퀴즈글 수정
	public boolean updateQuiz(QuizVO qvo, int curQzno) {
		query = " UPDATE t_quiz SET qztitle = ?, qznum1 = ?, qznum2 = ?, qznum3 = ?,qzcorrectnum = ?, qzreward = ?, qzstartdate = ?, enddate = ? , qzmoddate = SYSDATE WHERE qzNo = "+ curQzno ;
		
//		System.out.println(qvo);
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, qvo.getQztitle());
			pstmt.setString(2, qvo.getQznum1());
			pstmt.setString(3, qvo.getQznum2());
			pstmt.setString(4, qvo.getQznum3());
			pstmt.setInt(5, qvo.getQzcorrectnum());
			pstmt.setInt(6, qvo.getQzreward());
			pstmt.setDate(7, qvo.getQzstartdate());
			pstmt.setDate(8, qvo.getEnddate());
			
			//쿼리 실행 후 결과받기 + 반환
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("result "+ result);
		return result;
	}
	
	//퀴즈 삭제
	public boolean quizDelete(String qzno) {
		try {
			int re = Integer.parseInt(qzno);
			query = " DELETE FROM t_quiz WHERE qzno = "+ qzno ;
			pstmt = DBConn.getConnection().prepareStatement(query);
			
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//진행중인 퀴즈(관리자)
	public List<QuizVO> proceQuiz(String currdate) {
		query = " SELECT * FROM t_quiz WHERE enddate >= '"+ currdate +"' ";
		List<QuizVO> qList = new ArrayList<QuizVO>();
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				QuizVO qvo = new QuizVO();
				qvo.setQzno(rs.getInt("qzno"));
				qvo.setQztitle(rs.getString("qztitle"));
				qvo.setQznum1(rs.getString("qznum1"));
				qvo.setQznum2(rs.getString("qznum2"));
				qvo.setQznum3(rs.getString("qznum3"));
				qvo.setQzcorrectnum(rs.getInt("qzcorrectnum"));
				qvo.setQzreward(rs.getInt("qzreward"));
				qvo.setQzstartdate(rs.getDate("qzstartdate"));
				qvo.setEnddate(rs.getDate("enddate"));
				qvo.setQzregdate(rs.getDate("qzregdate"));
				qvo.setQzmoddate(rs.getDate("qzmoddate"));
				
				qList.add(qvo);
			}
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qList;
	}
	
	//종료된 퀴즈(관리자)
	public List<QuizVO> endQuiz(String currdate) {
		query = " SELECT * FROM t_quiz WHERE enddate < '"+ currdate +"' ";
		List<QuizVO> qList = new ArrayList<QuizVO>();
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				QuizVO qvo = new QuizVO();
				qvo.setQzno(rs.getInt("qzno"));
				qvo.setQztitle(rs.getString("qztitle"));
				qvo.setQznum1(rs.getString("qznum1"));
				qvo.setQznum2(rs.getString("qznum2"));
				qvo.setQznum3(rs.getString("qznum3"));
				qvo.setQzcorrectnum(rs.getInt("qzcorrectnum"));
				qvo.setQzreward(rs.getInt("qzreward"));
				qvo.setQzstartdate(rs.getDate("qzstartdate"));
				qvo.setEnddate(rs.getDate("enddate"));
				qvo.setQzregdate(rs.getDate("qzregdate"));
				qvo.setQzmoddate(rs.getDate("qzmoddate"));
				
				qList.add(qvo);
			}
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qList;
	}
	
	//퀴즈 참여여부 확인(사용자)
	public boolean isAttended(String loginId, int num) {
		query = " SELECT * FROM t_quiz_attend WHERE qzno = "+ num +" AND id = '"+ loginId +"' ";
		QuizeAttendVO qavo = null;
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if (result = rs.next()) {
				qavo = new QuizeAttendVO();
			}
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//퀴즈 참여하기(사용자)
	public boolean insertAttend(QuizeAttendVO qavo) {
		query = " INSERT INTO t_quiz_attend VALUES(seq_qzattendno.NEXTVAL, ?, ?, ?, SYSDATE) ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, qavo.getQzno());
			pstmt.setString(2, qavo.getId());
			pstmt.setInt(3, qavo.getQzanswer());
				
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//퀴즈 당첨자 마일리지 적립(마일리지테이블)
	public boolean saveMile(MileageLogVO mlvo, QuizVO qvo) {
		query = " INSERT INTO t_mileage_log VALUES(seq_milogno.NEXTVAL, ?, '퀴즈정답', ?, SYSDATE) ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, mlvo.getUserid());
			pstmt.setInt(2, qvo.getQzreward());
				
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//퀴즈 당첨자 마일리지 적립(사용자테이블)
	public boolean saveUserMile(MileageLogVO mlvo, QuizVO qvo, int uMile) {
		query = " UPDATE t_member SET mileage = mileage + ? WHERE id = '"+ mlvo.getUserid() +"' ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, qvo.getQzreward());
			
			result = pstmt.executeUpdate() == 1;
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//기간지난 퀴즈 참여여부
	public boolean isEndAttended(String currdate) {
		query = " SELECT * FROM t_quiz WHERE enddate >= '"+ currdate +"' ";
		QuizVO qvo = null;
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if (result = rs.next()) {
				qvo = new QuizVO();
			}
			
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//내가 참여한 퀴즈
	public QuizVO myQuiz(String loginId) {
		query = " SELECT q.qzno, qztitle, qzcorrectnum, qzreward  FROM t_quiz_attend qa, t_quiz q WHERE q.qzno = qa.qzno AND id = '"+ loginId +"' ";			
		QuizVO qvo = null;
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				qvo = new QuizVO();
				qvo.setQzno(rs.getInt("qzno"));
				qvo.setQztitle(rs.getString("qztitle"));
				qvo.setQzcorrectnum(rs.getInt("qzcorrectnum"));
				qvo.setQzreward(rs.getInt("qzreward"));
			}
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qvo;	
	}
	
	//참여한 퀴즈 목록
	public QuizeAttendVO selectMyquiz(String loginId) {
		query = " SELECT * FROM t_quiz_attend WHERE id = '"+ loginId +"' ";
		QuizeAttendVO qavo = new QuizeAttendVO();
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				qavo.setQzno(rs.getInt("qzno"));
				qavo.setId(rs.getString("id"));
				qavo.setQzanswer(rs.getInt("qzanswer"));
			}
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return qavo;
	}
	
	
}
