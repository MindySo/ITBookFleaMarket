package ITBookFleaMarket.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ITBookFleaMarket.common.DBConn;
import ITBookFleaMarket.vo.MemberVO;

public class MemberDAO {
	private String query;
	private PreparedStatement pstmt;
	private boolean result;
	private ResultSet rs;
	
	// 로그인 검증
	public boolean invalidLogin(String id, String pw) {
		MemberVO mvo = selectMember(id);

		// 아이디가 없을 경우 || 아이디에 대한 비밀번호가 틀릴 경우 -> 로그인 실패(false)
		return (mvo == null || !mvo.getPw().equals(pw));

	}
	
	// 로그인 - 정지 계정 여부 확인
	public boolean chkInactivated(String id) {
		MemberVO mvo = selectMember(id);
		
		return mvo.isInactivated();
	}
	
	// 아이디 중복체크(회원가입)
	public boolean isDuplicated(String id) {
		MemberVO mvo = selectMember(id);
		
		return mvo != null;
	}
	
	// 회원가입
	public boolean insertMember(MemberVO mvo) {
		query = " INSERT INTO t_member "
				+ "VALUES(?, ?, ?, ?, ?, 0, 'F', NULL, 'F', SYSDATE, 0, 0, 0) ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, mvo.getId());
			pstmt.setString(2, mvo.getPw());
			pstmt.setString(3, mvo.getName());
			pstmt.setString(4, mvo.getEmail());
			pstmt.setInt(5, Integer.parseInt(mvo.getAccountNo()));
			
			result = pstmt.executeUpdate() == 1;
			
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 비밀번호 길이 검증
	public boolean invalidPwLen(String pw) {
		return (pw.length() < 8 || pw.length() > 12);
	}
	
	// 이메일 형식 검증
	public boolean invalidEmailFmt(String email) {
		return (!email.contains("@") || 
					( !email.contains(".com") && !email.contains(".net") && 
						!email.contains(".co.kr") ));
	}
	
	// 계좌번호 형식 검증
	public boolean invalidAccountNoFmt(String accountNo) {
		// 계좌번호 길이 검증(10 ~ 14)
		if (accountNo.length() < 10 || accountNo.length() > 14) {
			return true;
		}
		
		// 계좌번호 숫자인지 검증 - 나중에 정규표현식으로
		try {
			Long.parseLong(accountNo);
		} catch (NumberFormatException e) { 
			return true; 
		}
		
		return false;
	}
	
	// 아이디로 특정 회원 정보 조회
	public MemberVO selectMember(String id) {
		MemberVO mvo = null;
		query = " SELECT * FROM t_member WHERE id = ? ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				mvo = new MemberVO(rs.getString("id"), rs.getString("pw"), rs.getString("name"),
						rs.getString("email"), rs.getLong("accountNo") + "");
				mvo.setReportNo(rs.getInt("reportNo"));
				mvo.setInactivated(rs.getBoolean("isInactivated"));
				mvo.setAdmin(rs.getBoolean("isAdmin"));
				mvo.setJoinDate(rs.getDate("joinDate"));
				mvo.setMileage(rs.getInt("mileage"));
				mvo.setPurchaseCnt(rs.getInt("purchaseCnt"));
				mvo.setSaleCnt(rs.getInt("saleCnt"));
			}
			
			DBConn.close(pstmt, rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mvo;
	}
	
	// 모든 회원 정보 조회
	public List<MemberVO> selectMember() {
		MemberVO mvo = null;
		List<MemberVO> members = new ArrayList<MemberVO>();
		
		query = " SELECT * FROM t_member ORDER BY joinDate ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				mvo = new MemberVO(rs.getString("id"), rs.getString("pw"), rs.getString("name"),
						rs.getString("email"), rs.getString("accountNo"), 
						rs.getBoolean("isInactivated"), rs.getInt("mileage"));
				mvo.setReportNo(rs.getInt("reportNo"));
				mvo.setInactiveDate(rs.getDate("inactiveDate"));
				mvo.setAdmin(rs.getBoolean("isAdmin"));
				mvo.setJoinDate(rs.getDate("joinDate"));
				mvo.setPurchaseCnt(rs.getInt("purchaseCnt"));
				mvo.setSaleCnt(rs.getInt("saleCnt"));
				
				members.add(mvo);
			}
			
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return members;
	}
	
	// 비활성화 여부 입력값 검증(관리자 회원정보수정)
	public boolean invalidInactivated(String isInactivated) {
		if (!isInactivated.equals("T") && !isInactivated.equals("F") 
				&& !isInactivated.equals("")) {
			return true;
		}
		
		return false;
	}
	
	// 회원정보 수정(사용자/관리자 회원 수정)
	public boolean updateMember(String currentId, MemberVO updateInfo) {
		query = " UPDATE t_member SET id = ?, pw = ?, name = ?, email = ?, "
				+ "accountNo = ?, isInactivated = ?, mileage = ? "
				+ "WHERE id = ? ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, updateInfo.getId());
			pstmt.setString(2, updateInfo.getPw());
			pstmt.setString(3, updateInfo.getName());
			pstmt.setString(4, updateInfo.getEmail());
			pstmt.setLong(5, Long.parseLong(updateInfo.getAccountNo()));
			pstmt.setString(6, updateInfo.isInactivated() ? "T" : "F");
			pstmt.setInt(7, updateInfo.getMileage());
			pstmt.setString(8, currentId);
			
			result = pstmt.executeUpdate() == 1;
			
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	// 회원탈퇴(일반 사용자)
	public boolean deleteMember(String id, String pw) {
		query = " DELETE t_member WHERE id = ? AND pw = ? ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			result = pstmt.executeUpdate() == 1;
			
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	// 회원탈퇴(관리자)
	public boolean deleteMember(String id) {
		query = " DELETE t_member WHERE id = ? ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate() == 1;
			
			DBConn.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 입출금내역 아이디로 회원 정보에서 계좌번호 가져오기
	public String selectAccountNo(String id) {
		MemberVO mvo = selectMember(id);
		
		return mvo != null ? mvo.getAccountNo() : "0000000000";
	}
}
