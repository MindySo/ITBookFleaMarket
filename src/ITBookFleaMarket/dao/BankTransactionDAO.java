package ITBookFleaMarket.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ITBookFleaMarket.common.DBConn;
import ITBookFleaMarket.vo.BankTransactionVO;

public class BankTransactionDAO {
	private String query;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 조회할 항목, 정렬 조건 입력받아 조회
	public List<BankTransactionVO> selectBankTransaction(int num, int sort) {
		BankTransactionVO btvo = null;
		List<BankTransactionVO> transactions = new ArrayList<BankTransactionVO>();
		String condition = "'입금', '출금'";
		
		if (num == 2) { condition = "'입금'"; }
		if (num == 3) { condition = "'출금'"; }
		
		String order = sort == 1 ? "DESC" : "ASC";
		
		query = " SELECT * FROM t_account WHERE transactionType IN(" + condition + ") "
				+ "ORDER BY transactionNo " + order + " ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				btvo = new BankTransactionVO();
				btvo.setTransactionNo(rs.getInt("transactionNo"));
				btvo.setTransactionType(rs.getString("transactionType"));
				btvo.setAccountHolder(rs.getString("accountHolder"));
				btvo.setTransactionId(rs.getString("transactionId"));
				btvo.setAmount(rs.getInt("amount"));
				btvo.setTransactionDate(rs.getDate("transactionDate"));
				
				transactions.add(btvo);
				
			}
			
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return transactions;
	}

	// 내역 번호로 하나 조회
	public BankTransactionVO selectBankTransaction(int transactionNo) {
		BankTransactionVO btvo = null;
		query = " SELECT * FROM t_account WHERE transactionNo = ?";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, transactionNo);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				btvo = new BankTransactionVO();
				btvo.setTransactionNo(rs.getInt("transactionNo"));
				btvo.setTransactionType(rs.getString("transactionType"));
				btvo.setAccountHolder(rs.getString("accountHolder"));
				btvo.setTransactionId(rs.getString("transactionId"));
				btvo.setAmount(rs.getInt("amount"));
				btvo.setTransactionDate(rs.getDate("transactionDate"));
			}
			
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return btvo;
	}

	// 조회할 회원 아이디, 정렬 조건 받아 목록 조회
	public List<BankTransactionVO> selectBankTransaction(String id, int sort) {
		BankTransactionVO btvo = null;
		List<BankTransactionVO> transactions = new ArrayList<BankTransactionVO>();
		String order = sort == 1 ? "DESC" : "ASC";
		
		query = " SELECT * FROM t_account WHERE transactionId = ? " 
				+ "ORDER BY transactionNo " + order + " ";
		
		try {
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				btvo = new BankTransactionVO();
				btvo.setTransactionNo(rs.getInt("transactionNo"));
				btvo.setTransactionType(rs.getString("transactionType"));
				btvo.setAccountHolder(rs.getString("accountHolder"));
				btvo.setTransactionId(rs.getString("transactionId"));
				btvo.setAmount(rs.getInt("amount"));
				btvo.setTransactionDate(rs.getDate("transactionDate"));
				
				transactions.add(btvo);
			}
			
			DBConn.close(pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return transactions;
	}
}
