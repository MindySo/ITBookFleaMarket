package ITBookFleaMarket.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {
	// 1.공유 Connection 객체 con을 클래스 외부에서 접근할 수 없도록 선언
	private static Connection con;

	// 2.기본 생성자를 클래스 외부에서 접근할 수 없도록 정의
	private DBConn() { }

	// 3.con 객체가 null이 아닌 경우 그냥 반환하고, 그렇지 않으면
	// 생성하여 반환하는 공유 메서드 getConnection 정의
	public static Connection getConnection() {
		if (con == null) {
			String driver = "oracle.jdbc.OracleDriver";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String username = "dev";
			String password = "1111";
					
			try {
				Class.forName(driver); // 드라이버 로딩
//				System.out.println("드라이버 로딩 ok");
				con = DriverManager.getConnection(url, username, password);
					
//					con.setAutoCommit(false);  //자동커밋 X
//					con.commit();				//DB에 DML 반영
//					con.rollback();				//DB에 DML 반영한거 되돌리기
//					con.setAutoCommit(true);	//자동커밋 O
//				System.out.println("con ok");
			} catch (SQLException e) {
				System.err.println(">> DB 연결 실패 또는 SQLException 예외 발생");
			} catch (ClassNotFoundException e) {
				System.err.println(">> 드라이버를 찾을 수 없습니다.");
			}
		}
		return con;
	}

	// 4.con 객체가 null이 아닌 경우 닫기 처리하는 공유 메서드 close 정의
	public static void close() {
		try {
			if (con != null) {
				 con.close();
			}
//			System.out.println("con closed!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
			
	//PreparedStatement 객체를 매개변수로 받아서
	//null이 아닌 경우 닫기 처리를 하는 공유 메서드 close를 오버로딩 
	public static void close(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
//			System.out.println("pstmt closed!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
			
	//Statement 객체를 매개변수로 받아서
	//null이 아닌 경우 닫기 처리를 하는 공유 메서드 close를 오버로딩 
	public static void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
//			System.out.println("stmt closed!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
			
	//PreparedStatement와 Resultset 객체를 받아서 null이 아닌 경우 닫기 처리
	//close 오버로딩
	public static void close(PreparedStatement pstmt,
								 ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
//			System.out.println("rs closed!");
			close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
