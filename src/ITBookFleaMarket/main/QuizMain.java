package ITBookFleaMarket.main;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;


import ITBookFleaMarket.dao.QuizDAO;
import ITBookFleaMarket.vo.MemberVO;
import ITBookFleaMarket.vo.MileageLogVO;
import ITBookFleaMarket.vo.QuizVO;
import ITBookFleaMarket.vo.QuizeAttendVO;

public class QuizMain {
	public Scanner sc;
	private QuizVO qvo;
	private QuizeAttendVO qavo;
	private QuizDAO qdao;
	private MileageLogVO mlvo;
	private MemberVO mvo;
	
	public QuizMain() {
		sc = new Scanner(System.in);
		qvo = new QuizVO();
		qdao = new QuizDAO();
		qavo = new QuizeAttendVO();
		mlvo = new MileageLogVO();
		mvo = MemberVO.getInstance();
	}
	
	//사용자단 퀴즈&설문 선택
	public void userEvent() {
		while (true) {
			System.out.println();
			System.out.println(">> EVENT only User -----------------------");
			System.out.println("   1.퀴즈이벤트   2.이전으로");
			System.out.print(">> 선택 : ");
			String input = sc.nextLine();
			
			switch (input) {	 
			case "1":	quizUserMenu();		break;
			case "2":	return; 
			
			default:System.out.println(">> 1 ~ 2을 입력해주세요."); 
			}
			System.out.println();
		}
	}
	
	//관리자단 퀴즈&설문 선택
	public void adminEvent() {
		while (true) {
			System.out.println();
			System.out.println(">> EVENT only User -----------------------");
			System.out.println("   1.퀴즈이벤트   2.이전으로");
			System.out.print(">> 선택 : ");
			String input = sc.nextLine();
			
			switch (input) {	 
			case "1":	quizAdminMenu();		break;
			case "2":	return; 
			
			default:System.out.println(">> 1 ~ 2을 입력해주세요."); 
			}
			System.out.println();
		}
	}
	
	//퀴즈 관리자단
	public void quizAdminMenu() {
		while (true) {
			System.out.println();
			System.out.println(">> Quiz only Admin -----------------------");
			System.out.println("   1.퀴즈목록   2.진행중인 퀴즈   3.종료된 퀴즈   4.퀴즈등록	  5.이전으로");
			System.out.print(">> 선택 : ");
			String input = sc.nextLine();
			
			switch (input) {	 
			case "1":	quizAdminList();		break;
			case "2":	proceQuiz();	break; 
			case "3":	endQuiz();	break; 
			case "4":	insertQuiz();	break; 
			case "5":	return; 
			
			default:System.out.println(">> 1 ~ 5을 입력해주세요."); 
			}
			System.out.println();
		}
	}
	
	//퀴즈 등록
	public void insertQuiz() {
		qvo = new QuizVO();
		
		System.out.println();
		System.out.println(">> Quiz 등록 ---------");
		System.out.print("  퀴즈 주제 : ");
		qvo.setQztitle(sc.nextLine());
		
		System.out.print("  퀴즈 보기1 : ");
		qvo.setQznum1(sc.nextLine());
		
		System.out.print("  퀴즈 보기2 : ");
		qvo.setQznum2(sc.nextLine());
		
		System.out.print("  퀴즈 보기3 : ");
		qvo.setQznum3(sc.nextLine());
		
		System.out.print("  퀴즈 정답 번호 : ");
		int rs = Integer.parseInt(sc.nextLine());
		qvo.setQzcorrectnum(rs);
		
		System.out.print("  퀴즈 보상 : ");
		rs = Integer.parseInt(sc.nextLine());
		qvo.setQzreward(rs);
		
		System.out.print("  퀴즈 시작 기간(yyyy-mm-dd) : ");
		String start = sc.nextLine();
		qvo.setQzstartdate(Date.valueOf(start));
		
		System.out.print("  퀴즈 종료 기간(yyyy-mm-dd) : ");
		String end = sc.nextLine();
		qvo.setEnddate(Date.valueOf(end));
		
		boolean result = qdao.insertQuiz(qvo);
		if(result == true) {	//등록 성공
			System.out.println(">> 퀴즈 등록이 완료되었습니다.");
		} else {				//등록 실패
			System.out.println(">> 퀴즈 등록이 실패했습니다.");
		}
		System.out.println();
	}
	
	///////////////////////////////////////////////
	//공통 전체퀴즈 (관리자)
	public void quizAdminList() {
		List<QuizVO> qvoList = qdao.quizList();
		if (qvoList == null || qvoList.size() < 1) {
			System.out.println(">> 등록된 퀴즈가 없습니다.");
		} else {		//그렇지 않은 경우
			System.out.println("번호\t|퀴즈제목\t|보기1\t|보기2\t|보기3\t|퀴즈정답번호\t|퀴즈보상\t|퀴즈시작일\t\t|퀴즈종료일\t\t|퀴즈등록일\t\t|퀴즈수정일");
			System.out.println("-------------------------------------------------------------------------");
			
			for (QuizVO qvo : qvoList) {
				System.out.println(qvo.getQzno()+"\t|"+
									qvo.getQztitle()+"\t|"+
									qvo.getQznum1()+"\t|"+
									qvo.getQznum2()+"\t|"+
									qvo.getQznum3()+"\t|"+
									qvo.getQzcorrectnum()+"\t|"+
									qvo.getQzreward()+"포인트\t|"+
									qvo.getQzstartdate()+"\t|"+
									qvo.getEnddate()+"\t|"+
									qvo.getQzregdate()+"\t|"+
									qvo.getQzmoddate());
			}
			System.out.println("\n");
			System.out.print("상세보기할 퀴즈 번호를 입력해주세요: ");
			String qzno = sc.nextLine();
			
			qvo = qdao.quizOneList(qzno);
			if (qvo != null) {
				System.out.println("퀴즈번호: "+ qvo.getQzno());
				System.out.println("퀴즈주제: "+ qvo.getQztitle());
				System.out.println("퀴즈주제: "+ qvo.getQznum1());
				System.out.println("퀴즈주제: "+ qvo.getQznum2());
				System.out.println("퀴즈주제: "+ qvo.getQznum3());
				System.out.println("퀴즈정답번호: "+ qvo.getQzcorrectnum());
				System.out.println("퀴즈보상: "+ qvo.getQzreward()+"포인트");
				System.out.println("퀴즈시작일: "+ qvo.getQzstartdate());
				System.out.println("퀴즈종료일: "+ qvo.getEnddate());
				System.out.println("퀴즈등록일: "+ qvo.getQzregdate());
				System.out.println("퀴즈수정일: "+ qvo.getQzmoddate());
				
				// 상세보기 수정/삭제
					System.out.println();
					System.out.println("   1.글수정   2.글삭제   3.이전으로");
					System.out.print(">> 선택 : ");		
					String input = sc.nextLine();	
					
					switch (input) {	 			
					case "1":	quizUpdate(qvo);	break;
					case "2":	quizDelete(qvo, qzno);	break;
					case "3":	return;
					default:System.out.println(">> 1 ~ 3을 입력해주세요."); 
					}
			} else {
				System.out.println("입력한 퀴즈번호가 존재하지 않습니다.");
			}
			System.out.println();
		}
	}
	
	//퀴즈글 수정
	public void quizUpdate(QuizVO qvo) {
		System.out.println();
		int curQzno = qvo.getQzno();
		
		System.out.println(">> Quiz 등록 ---------");
		System.out.print("  퀴즈 주제 : ");
		qvo.setQztitle(sc.nextLine());
		
		System.out.print("  퀴즈 정답 번호 : ");
		int rs = Integer.parseInt(sc.nextLine());
		qvo.setQzcorrectnum(rs);
		
		System.out.print("  퀴즈 보상 : ");
		rs = Integer.parseInt(sc.nextLine());
		qvo.setQzreward(rs);
		
		System.out.print("  퀴즈 시작 기간(yyyy-mm-dd) : ");
		String start = sc.nextLine();
		qvo.setQzstartdate(Date.valueOf(start));
		
		System.out.print("  퀴즈 종료 기간(yyyy-mm-dd) : ");
		String end = sc.nextLine();
		qvo.setQzstartdate(Date.valueOf(end));
		
		boolean result = qdao.updateQuiz(qvo, curQzno);
		System.out.println(result);
		if (result == true) {
			System.out.println(">> 회원정보 수정이 완료 되었습니다.");
		} else {
			System.out.println(">> 회원정보 수정이 실패하였습니다.");
			System.out.println(">> 잠시 후 다시 이용해주세요.");
		}
		quizAdminList();  //글 수정 후 전체리스트
	}
	
	//퀴즈글 삭제
	public void quizDelete(QuizVO qvo, String qzno) {
		if (Integer.parseInt(qzno) == qvo.getQzno()) {
			qdao.quizDelete(qzno);
			System.out.println("퀴즈글이 삭제되었습니다.");
		} else {
			System.out.println("퀴즈글이 삭제되지 않았습니다.");
		}
	}
	
	//진행중인 퀴즈(관리자)
	public void proceQuiz() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat dateFmt 
			= new SimpleDateFormat("yyyy-MM-dd");
		String currdate = dateFmt.format(date);
		System.out.println(currdate);
		
		List<QuizVO> qList = qdao.proceQuiz(currdate);
		if (qList == null || qList.size() < 1) {
			System.out.println(">> 진행중인 퀴즈가 없습니다.");
		} else {
			System.out.println("번호\t|퀴즈제목\t|퀴즈정답번호\t|퀴즈보상\t|퀴즈시작일\t\t|퀴즈종료일\t\t|퀴즈등록일\t\t|퀴즈수정일");
			System.out.println("-------------------------------------------------------------------------");
			
			for (QuizVO qvo : qList) {
				System.out.println(qvo.getQzno()+"\t|"+
									qvo.getQztitle()+"\t|"+
									qvo.getQzcorrectnum()+"\t|"+
									qvo.getQzreward()+"포인트\t|"+
									qvo.getQzstartdate()+"\t|"+
									qvo.getEnddate()+"\t|"+
									qvo.getQzregdate()+"\t|"+
									qvo.getQzmoddate());
			}
			System.out.println("\n");
			System.out.print("상세보기할 퀴즈 번호를 입력해주세요: ");
			String qzno = sc.nextLine();
			
			qvo = qdao.quizOneList(qzno);
			if (qvo != null) {
				System.out.println("퀴즈번호: "+ qvo.getQzno());
				System.out.println("퀴즈주제: "+ qvo.getQztitle());
				System.out.println("퀴즈정답번호: "+ qvo.getQzcorrectnum());
				System.out.println("퀴즈보상: "+ qvo.getQzreward()+"포인트");
				System.out.println("퀴즈시작일: "+ qvo.getQzstartdate());
				System.out.println("퀴즈종료일: "+ qvo.getEnddate());
				System.out.println("퀴즈등록일: "+ qvo.getQzregdate());
				System.out.println("퀴즈수정일: "+ qvo.getQzmoddate());
				
				// 상세보기 수정/삭제
					System.out.println();
					System.out.println("   1.글수정   2.글삭제	3.이전으로");
					System.out.print(">> 선택 : ");		
					String input = sc.nextLine();	
					
					switch (input) {	 			
					case "1":	quizUpdate(qvo);	break;
					case "2":	quizDelete(qvo, qzno);	break;
					case "3":	return;
					default:System.out.println(">> 1 ~ 3을 입력해주세요."); 
					}
			} else {
				System.out.println("입력한 퀴즈번호가 존재하지 않습니다.");
			}
			System.out.println();
		}
	}
	
	////////////////////////////////////////////////공통 END
	
	//종료된 퀴즈(관리자)
	public void endQuiz() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat dateFmt 
			= new SimpleDateFormat("yyyy-MM-dd");
		String currdate = dateFmt.format(date);
		System.out.println(currdate);
		
		List<QuizVO> qList = qdao.endQuiz(currdate);
		
		if (qList == null || qList.size() < 1) {
			System.out.println(">> 진행중인 퀴즈가 없습니다.");
		} else {
			System.out.println("번호\t|퀴즈제목\t|퀴즈정답번호\t|퀴즈보상\t|퀴즈시작일\t\t|퀴즈종료일\t\t|퀴즈등록일\t\t|퀴즈수정일");
			System.out.println("-------------------------------------------------------------------------");
			
			for (QuizVO qvo : qList) {
				System.out.println(qvo.getQzno()+"\t|"+
									qvo.getQztitle()+"\t|"+
									qvo.getQzcorrectnum()+"\t|"+
									qvo.getQzreward()+"포인트\t|"+
									qvo.getQzstartdate()+"\t|"+
									qvo.getEnddate()+"\t|"+
									qvo.getQzregdate()+"\t|"+
									qvo.getQzmoddate());
			}
			System.out.println("\n");
			System.out.print("상세보기할 퀴즈 번호를 입력해주세요: ");
			String qzno = sc.nextLine();
			//퀴즈 상세보기
			qvo = qdao.quizOneList(qzno);
			if (qvo != null) {
				System.out.println("퀴즈번호: "+ qvo.getQzno());
				System.out.println("퀴즈주제: "+ qvo.getQztitle());
				System.out.println("퀴즈보기1: "+ qvo.getQznum1());
				System.out.println("퀴즈보기2: "+ qvo.getQznum2());
				System.out.println("퀴즈보기3: "+ qvo.getQznum3());
				System.out.println("퀴즈정답번호: "+ qvo.getQzcorrectnum());
				System.out.println("퀴즈보상: "+ qvo.getQzreward()+"포인트");
				System.out.println("퀴즈시작일: "+ qvo.getQzstartdate());
				System.out.println("퀴즈종료일: "+ qvo.getEnddate());
				System.out.println("퀴즈등록일: "+ qvo.getQzregdate());
				System.out.println("퀴즈수정일: "+ qvo.getQzmoddate());
				
				// 상세보기 수정/삭제
					System.out.println();
					System.out.println("   1.글수정   2.글삭제   3.이전으로");
					System.out.print(">> 선택 : ");		
					String input = sc.nextLine();	
					
					switch (input) {	 			
					case "1":	quizUpdate(qvo);	break;
					case "2":	quizDelete(qvo, qzno);	break;
					case "3":	return;
					default:System.out.println(">> 1 ~ 3을 입력해주세요."); 
					}
			} else {
				System.out.println("입력한 퀴즈번호가 존재하지 않습니다.");
			}
			System.out.println();
		}
	}
	
	//퀴즈 사용자단
	public void quizUserMenu() {
		while (true) {
			System.out.println();
			System.out.println(">> Quiz only User -----------------------");
			System.out.println("   1.전체퀴즈   2.진행중인 퀴즈   3.내가 참여한 퀴즈   4.이전으로");
			System.out.print(">> 선택 : ");
			String input = sc.nextLine();
			
			switch (input) {	 
			case "1":	quizUserList();		break;
			case "2":	proceUserQuiz();	break; 
			case "3":	myQuiz();	break; 
			case "4":	return; 
			
			default:System.out.println(">> 1 ~ 5을 입력해주세요."); 
			}
			System.out.println();
		}
	}
	
	//퀴즈 전체목록(사용자)
	public void quizUserList() {
		List<QuizVO> qvoList = qdao.quizList();
		if (qvoList == null || qvoList.size() < 1) {
			System.out.println(">> 등록된 퀴즈가 없습니다.");
		} else {		//그렇지 않은 경우
			System.out.println("번호\t|퀴즈제목\t|퀴즈보기1\t|퀴즈보기2\t|퀴즈보기3\t|퀴즈정답번호\t|퀴즈보상\t|퀴즈시작일\t\t|퀴즈종료일");
			System.out.println("---------------------------------------------------------");
			
			for (QuizVO qvo : qvoList) {
				System.out.println(qvo.getQzno()+"\t|"+
									qvo.getQztitle()+"\t|"+
									qvo.getQznum1()+"\t|"+
									qvo.getQznum2()+"\t|"+
									qvo.getQznum3()+"\t|"+
									qvo.getQzcorrectnum()+"\t|"+
									qvo.getQzreward()+"포인트\t|"+
									qvo.getQzstartdate()+"\t|"+
									qvo.getEnddate());
			}
			System.out.println("\n");
			System.out.print("상세보기할 퀴즈 번호를 입력해주세요: ");
			String qzno = sc.nextLine();
			//퀴즈 상세보기
			qvo = qdao.quizOneList(qzno);
			if (qvo != null) {
				System.out.println("퀴즈번호: "+ qvo.getQzno());
				System.out.println("퀴즈주제: "+ qvo.getQztitle());
				System.out.println("퀴즈보기1: "+ qvo.getQznum1());
				System.out.println("퀴즈보기2: "+ qvo.getQznum2());
				System.out.println("퀴즈보기3: "+ qvo.getQznum3());
				System.out.println("퀴즈정답번호: "+ qvo.getQzcorrectnum());
				System.out.println("퀴즈보상: "+ qvo.getQzreward()+"포인트");
				System.out.println("퀴즈시작일: "+ qvo.getQzstartdate());
				System.out.println("퀴즈종료일: "+ qvo.getEnddate());
			}
				joinQuiz(MemberMain.loginId, qvo);
		}
	}
	
	//진행중인 퀴즈(사용자)
	public void proceUserQuiz() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat dateFmt 
			= new SimpleDateFormat("yyyy-MM-dd");
		String currdate = dateFmt.format(date);
		System.out.println(currdate);
		
		List<QuizVO> qList = qdao.proceQuiz(currdate);
		if (qList == null || qList.size() < 1) {
			System.out.println(">> 진행중인 퀴즈가 없습니다.");
		} else {
			System.out.println("번호\t|퀴즈제목\t|퀴즈정답번호\t|퀴즈보상\t|퀴즈시작일\t\t|퀴즈종료일\t\t|퀴즈등록일\t\t|퀴즈수정일");
			System.out.println("-------------------------------------------------------------------------");
			
			for (QuizVO qvo : qList) {
				System.out.println(qvo.getQzno()+"\t|"+
									qvo.getQztitle()+"\t|"+
									qvo.getQzcorrectnum()+"\t|"+
									qvo.getQzreward()+"포인트\t|"+
									qvo.getQzstartdate()+"\t|"+
									qvo.getEnddate()+"\t|"+
									qvo.getQzregdate()+"\t|"+
									qvo.getQzmoddate());
			}
			System.out.println("\n");
			System.out.print("상세보기할 퀴즈 번호를 입력해주세요: ");
			String qzno = sc.nextLine();
			
			qvo = qdao.quizOneList(qzno);
			if (qvo != null) {
				System.out.println("퀴즈번호: "+ qvo.getQzno());
				System.out.println("퀴즈주제: "+ qvo.getQztitle());
				System.out.println("퀴즈보상: "+ qvo.getQzreward()+"포인트");
				System.out.println("퀴즈시작일: "+ qvo.getQzstartdate());
				System.out.println("퀴즈종료일: "+ qvo.getEnddate());
				joinQuiz(MemberMain.loginId, qvo);
			} else {
				System.out.println("입력한 퀴즈번호가 존재하지 않습니다.");
			}
			System.out.println();
		}
	}
	
	//사용자 퀴즈 참여메뉴
	public void joinQuiz(String loginId, QuizVO qvo) {
		int qznum = 0;
		qznum = qvo.getQzno();
		
		System.out.println();
		System.out.println("   1.퀴즈참여   2.이전으로");
		System.out.print(">> 선택 : ");
		String input = sc.nextLine();
		
		switch (input) {	 
		case "1":	
			boolean result = qdao.isAttended(loginId, qznum);
			if (result == true) { //퀴즈 기참여
				System.out.println("이미 퀴즈에 참여하셨습니다.");
				return;
			} else {	//퀴즈 미참여
				//기간 지난 퀴즈 참여 판단 여부
				java.util.Date date = new java.util.Date();
				SimpleDateFormat dateFmt 
					= new SimpleDateFormat("yyyy-MM-dd");
				String currdate = dateFmt.format(date);
				result = qdao.isEndAttended(currdate);
				System.out.println(result);
				if (result==true) {
					System.out.println("   주제: " + qvo.getQztitle());
					System.out.println("   응답하실 번호를 입력해 주세요.");
					System.out.println("   보기1: " + qvo.getQznum1()+
									   "   보기2: " + qvo.getQznum2()+
									   "   보기3: " + qvo.getQznum3());
					
					System.out.print(">> 입력 : ");
					input = sc.nextLine();
					int qzanum = Integer.parseInt(input);
				
					qavo = new QuizeAttendVO(qznum, loginId, qzanum);
					if (qdao.insertAttend(qavo)) {
						System.out.println(">> 퀴즈 참여가 완료되었습니다.");
						System.out.println("정답은 >> " + qvo.getQzcorrectnum()+ "번입니다.");
						System.out.println();
						//정답여부 확인
						if (qzanum==qvo.getQzcorrectnum()) {
							System.out.println("정답입니다");
							//마일리지적립
							mlvo.setUserid(loginId);  
							int uMile = mvo.getMileage();
							qdao.saveMile(mlvo, qvo);
							qdao.saveUserMile(mlvo, qvo, uMile);
							System.out.println(qvo.getQzreward()+ "포인트 적립!!");
						} else {
							System.out.println("아쉽지만 틀렸습니다. 다음 기회에~~");
						}
					} else {
						System.out.println(">> 퀴즈 참여에 실패했습니다.");
						return;
					}
				}else {
					System.out.println(">> 참여기간이 만료된 퀴즈입니다.");
				}
			}
			break;
		case "2":	return; 
		default:System.out.println(">> 1 ~ 2을 입력해주세요."); 
		}
		
	}
	
	//내가 참여한 퀴즈
	private void myQuiz() {
		QuizVO qvo = qdao.myQuiz(MemberMain.loginId);
		QuizeAttendVO qavo = qdao.selectMyquiz(MemberMain.loginId);
		if (MemberMain.loginId.equals(qavo.getId())) {
		System.out.println("글번호\t|퀴즈주제\t|나의답\t|정답번호\t|보상마일리지");
		System.out.println(qvo.getQzno()+"\t"+
						   qvo.getQztitle()+"\t"+
						   qavo.getQzanswer()+"\t"+
						   qvo.getQzcorrectnum()+"\t"+
						   qvo.getQzreward()
						   );
		}
	} 
}
