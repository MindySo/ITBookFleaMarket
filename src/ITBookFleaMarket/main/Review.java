package ITBookFleaMarket.main;

import java.util.List;
import java.util.Scanner;

import ITBookFleaMarket.dao.ReviewDAO;
import ITBookFleaMarket.vo.BookVO;
import ITBookFleaMarket.vo.ReviewVO;
import ITBookFleaMarket.vo.TradePostVO;

public class Review {
	public Scanner sc;
	private TradePostVO tpvo;
	private ReviewVO rvo;
	private ReviewDAO rdao;
	private BookVO bvo;
	
	public Review() {
		sc = new Scanner(System.in);
		tpvo = new TradePostVO();
		rvo = new ReviewVO();
		rdao = new ReviewDAO();
		bvo = new BookVO();
	}
	//리뷰 사용자단 메뉴
	public void reviewUserMenu() {
		while (true) {
			System.out.println();
			System.out.println(">> 구매 후기 -----------------------");
			System.out.println("   1.구매후기 작성   2.구매후기 상세보기   3.이전으로   4.후기전체보기(임시테스트용)");
			System.out.print(">> 선택 : ");
			String input = sc.nextLine();
			//임시 거래번호 --도서VO(임시로 String도서이름) 넣기
			int test = 1;
			switch (input) {	 
			case "1":	insertReview(test);	break;
			case "2":	reviewDetail(test);	break;
			case "3":	return; 
			case "4":	reviewList(test);	break;
			
			default:System.out.println(">> 1 ~ 3을 입력해주세요."); 
			}
			System.out.println();
		}
	}
	
	//구매 후기 등록
	public void insertReview(int test) {
		rvo = new ReviewVO();
		
		System.out.println();
		System.out.println(">> 구매 후기 등록 ---------");
		//별점 유효성
		System.out.print("  별점 (0 ~ 5점까지 입력해주세요): ");
		try {
			int rate = Integer.parseInt(sc.nextLine());
			if (0 <= rate && rate <= 5) { //0에서 5값을 넣어줄때
				rvo.setRate(rate); //vo에 담아줌
			}else {
				System.out.println("0 ~ 5 값을 입력해주세요");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("정수만 입력해주세요!");
			return;
		}
		
		System.out.print("  리뷰내용 : ");
		rvo.setReviewcontent(sc.nextLine());
		
		boolean result = rdao.insertReview(rvo, test);
		if(result == true) {	//등록 성공
			System.out.println(">> 후기 등록이 완료되었습니다.");
		} else {				//등록 실패
			System.out.println(">> 후기 등록이 실패했습니다.");
		}
		System.out.println();
	}
	
	//후기 상세보기
	public void reviewDetail(int test) {
		rvo = rdao.reviewDetail(test);
		bvo = rdao.bookName(test);	//도서이름 가져오기
//		System.out.println("vo테스트"+rvo.getTrlogno());
		if (rvo != null) {
			System.out.println("도서이름 : "+ bvo.getBookName());
			System.out.print("별점 : ");
			//별점 출력
			int rate = rvo.getRate();
			if (rate == 0) {
					System.out.println("☆");
			}else {
				for (int i = 1; i <= rate; i++) {
					System.out.print("★");				
				}
			}
			System.out.println();
			System.out.println("후기내용 : "+ rvo.getReviewcontent());
			
			System.out.println();
			System.out.println("   1.후기수정   2.후기삭제   3.이전으로");
			System.out.print(">> 선택 : ");
			String input = sc.nextLine();
			switch (input) {
			case "1":	reviewUpdate(rvo, test);	break;
			case "2":	reviewDelete(rvo, test);	break;
			case "3": 	return;
			default: System.out.println(">> 1 ~ 3을 입력해주세요."); 
			}
		} else {
			System.out.println("불러올 구매 후기가 없습니다.");
		}
	}
	
	//후기 수정
	public void reviewUpdate(ReviewVO rvo, int test) {
		System.out.println();
		int rno = rvo.getReviewno();
		//별점유효성
		System.out.print("  별점 (0 ~ 5점까지 입력해주세요): ");
		try {
			int rate = Integer.parseInt(sc.nextLine());
			if (0 <= rate && rate <= 5) { //0에서 5값을 넣어줄때
				rvo.setRate(rate); //vo에 담아줌
			}else {
				System.out.println("0 ~ 5 값을 입력해주세요");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("정수만 입력해주세요!");
			return;
		}
		
		System.out.print("  리뷰내용 : ");
		rvo.setReviewcontent(sc.nextLine());
		
		boolean result = rdao.reviewUpdate(rvo, test);
		
		if (result == true) {
			System.out.println(">> 후기 수정이 완료 되었습니다.");
		} else {
			System.out.println(">> 후기 수정이 실패하였습니다.");
			System.out.println(">> 잠시 후 다시 이용해주세요.");
		}
		reviewDetail(test);  //후기 상세보기 페이지로
	}
	
	//후기 삭제->거래번호로 확인 삭제
	public void reviewDelete(ReviewVO rvo, int test) {
		if (test == rvo.getTrlogno()) {
			rdao.reviewDelete(test);
			System.out.println("후기글이 삭제 되었습니다.");
		} else {
			System.out.println("후기글이 삭제되지 않았습니다.");
		}
	}
	
	
	//거래후기 전체목록(관리자만)
	public void reviewList(int test) { 
		List<ReviewVO> rList = rdao.reviewList(test);
		
		if (rList == null || rList.size() < 1) {
			System.out.println("작성된 구매후기가 없습니다."); 
		} else {
			System.out.println("후기번호\t|거래번호\t|별점\t|리뷰내용\t|리뷰일자"); 
		 	System.out.println("=====================================");
		 	
		 	for (ReviewVO rvo : rList) {
				System.out.print(rvo.getReviewno()+"\t|"+
								   rvo.getTrlogno()+"\t|"+
								   rvo.getRate()+"\t|"+
								   rvo.getReviewcontent()+"\t|"+
								   rvo.getReviewdate());
			}
		 	System.out.println("\n");
		 	System.out.println("   1.상세보기   2.이전으로");
			System.out.print(">> 선택 : ");		
			String input = sc.nextLine();	
			
			switch (input) {	 			
			case "1":	System.out.print("상세보기할 퀴즈 번호를 입력해주세요: ");
						System.out.print(">> 선택 : ");
						String rno = sc.nextLine();
						reviewAdminDetail(rno);
						break;
			case "2":	return;
			default:System.out.println(">> 1 ~ 3을 입력해주세요."); 
			}
			
		}
	}
	
	//리뷰상세보기(관리자)
	public void reviewAdminDetail(String rno) {
		int rno1 = Integer.parseInt(rno);
		rvo = rdao.reviewAdminDetail(rno1);
		if (rvo != null) {
			System.out.println("후기번호 : "+rvo.getReviewno());
			System.out.println("거래번호 : "+ rvo.getTrlogno());
			System.out.print("별점 : ");
			//별점 출력
			int rate = rvo.getRate();
			if (rate == 0) {
					System.out.println("☆");
			}else {
				for (int i = 1; i <= rate; i++) {
					System.out.print("★");				
				}
			}
			System.out.println();
			System.out.println("후기내용 : "+ rvo.getReviewcontent());
			
			System.out.println();
			System.out.println("   1.후기삭제   2.이전으로");
			System.out.print(">> 선택 : ");
			String input = sc.nextLine();
			switch (input) {
			case "1":	reviewAdminDelete(rno1);	break;
			case "2": 	return;
			default: System.out.println(">> 1 ~ 2을 입력해주세요."); 
			}
		} else {
			System.out.println("불러올 구매 후기가 없습니다.");
		}
	}
	
	//후기 삭제(관리자) ->후기번호로 확인 삭제
		public void reviewAdminDelete(int rno1) {
			if (rno1 == rvo.getReviewno()) {
				rdao.reviewAdminDelete(rno1);
				System.out.println("후기글이 삭제 되었습니다.");
			} else {
				System.out.println("후기글이 삭제되지 않았습니다.");
			}
		}
	
	public static void main(String[] args) {
		new Review().reviewUserMenu();
	}
}
