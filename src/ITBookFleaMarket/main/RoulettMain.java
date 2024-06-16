package ITBookFleaMarket.main;

import java.util.Scanner;

import ITBookFleaMarket.dao.RouletteDAO;
import ITBookFleaMarket.dao.RtAttendDAO;
import ITBookFleaMarket.vo.RouletteVO;
import ITBookFleaMarket.vo.RtAttendVO;

public class RoulettMain {
	public Scanner sc;
	private RouletteVO rvo;
	private RtAttendVO ravo;
	private RouletteDAO rdao;
	private RtAttendDAO radao;
	private MemberMain mm;
	
	public RoulettMain() {
		sc = new Scanner(System.in);
		rvo = new RouletteVO();
		ravo = new RtAttendVO();
		rdao = new RouletteDAO();
		radao = new RtAttendDAO();
		mm = new MemberMain();
	}	
	
	public void rtAdminMenu() {
		
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
	}
}
