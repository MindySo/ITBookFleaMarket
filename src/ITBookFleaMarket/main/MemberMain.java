package ITBookFleaMarket.main;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import ITBookFleaMarket.dao.MemberDAO;
import ITBookFleaMarket.vo.MemberVO;
import ITBookFleaMarket.dao.BankTransactionDAO;
import ITBookFleaMarket.vo.BankTransactionVO;

public class MemberMain {
	public static String loginId;
	private Scanner sc;
	private MemberVO mvo;
	private MemberDAO mdao;
	private BankTransactionVO btvo;
	private BankTransactionDAO btdao;
	private QuizMain qm;
	
	public MemberMain() {
		sc = new Scanner(System.in);
		mvo = MemberVO.getInstance();
		mdao = new MemberDAO();
		btvo = new BankTransactionVO();
		btdao = new BankTransactionDAO();
		qm = new QuizMain();
	}
	
	// 1. 메인메뉴
	public void menu() {
		while (true) {
			System.out.println(">> 중고 IT 서적 거래 ----------------------");
			System.out.println("   1.가입   2.로그인   3.ID찾기   4.PW찾기   5.사이트 나가기");
			System.out.print(">> 선택: ");
			String input = sc.nextLine().trim();
			
			int num = 0;
			try {
				num = Integer.parseInt(input);
				
				if (num < 1 || num > 5) {
					System.out.println(">> 1 / 2 / 3 / 4 / 5 중 하나의 숫자를 입력해 주세요.");
					System.out.println();
				}
				
			} catch (NumberFormatException e) {
				System.out.println(">> 숫자를 입력해 주세요.");
				System.out.println();
			}
			
			if (num == 1) { join(); };
			if (num == 2) { login(); };
			if (num == 3) { };
			if (num == 4) { };
			if (num == 5) {
				System.out.println(">> 사이트를 나갑니다.");
				sc.close();
				System.exit(0); 
			};
		}
	}
	
	// 2. 로그인 화면 - 사용자
	public void login() {
		while(true) {
			System.out.println();
			System.out.println(">> 로그인 ----------------------");
			System.out.print("   아이디 : ");
			String id = sc.nextLine().trim();
			System.out.print("   비밀번호 : ");
			String pw = sc.nextLine().trim();
			
			// 로그인 확인
			if (mdao.invalidLogin(id, pw)) {
				System.out.println(">> 아이디 또는 비밀번호가 일치하지 않습니다.");
				System.out.println();
				break;
			}
			
			if (mdao.chkInactivated(id)) {
				System.out.println(">> 해당 계정은 정지되어 로그인할 수 없습니다.");
				break;
			}
			
			loginId = id;
			
			if (mdao.selectMember(id).isAdmin()) {
				adminMenu(); break;
			} else {
				userMenu(); break;
			}
			
		}
	}
	
	// 3. 로그인 후 사용자 메뉴 화면 - 거래 / 마이페이지 / 로그아웃
	public void userMenu() {
		while (true) {
			if (mdao.selectMember(loginId) == null) {
				System.out.println(">> 회원 메뉴 불러오기 실패!");
				System.out.println(">> 존재하지 않는 회원입니다.");
				System.out.println(">> 메인 메뉴로 돌아갑니다.");
				System.out.println();
				break;
			}
			
			System.out.println();
			System.out.println(">> 메뉴 화면 -----------------------");
			System.out.println("   1.거래   2.마이페이지  3.이벤트참여   4.로그아웃");
			System.out.print(">> 선택: ");
			String input = sc.nextLine().trim();
			
			int num = 0;
			try {
				num = Integer.parseInt(input);
				
				if (num < 1 || num > 4) {
					System.out.println(">> 1 / 2 / 3 / 4 중 하나의 숫자를 입력해 주세요.");
					System.out.println();
				}
				
			} catch (NumberFormatException e) {
				System.out.println(">> 숫자를 입력해 주세요.");
				System.out.println();
			}
			
			if (num == 1) { /* 거래글 */ }
			if (num == 2) { userInfo(loginId); }
			if (num == 3) { qm.userEvent(); }
			if (num == 4) {
				System.out.println(">> 로그아웃이 완료되었습니다.");
				System.out.println();
				loginId = "";
				return;
			}
		}
	}
	
	// 3.2., 4.?. (공통) 사용자 마이페이지 - 정보 출력 + 수정 / 거래 내역 / 탈퇴
	public void userInfo(String id) {
		while (true) {
			mvo = mdao.selectMember(id);
			
			System.out.println("   아이디\t\t : " + mvo.getId());
			System.out.println("   이름\t\t : " + mvo.getName());
			System.out.println("   이메일\t\t : " + mvo.getEmail());
			System.out.println("   계좌번호\t : " + mvo.getAccountNo());
			System.out.println("   신고 당한 횟수\t : " + mvo.getReportNo());
			System.out.println("   가입일자\t : " + mvo.getJoinDate());
			System.out.println("   마일리지\t : " + mvo.getMileage());
			
			int opt = userInfoMenu(id);
			
			if (opt == 3 || opt == 4) { return; }
		}
	}
	
	// 3.3. 사용자 마이페이지 - 수정 / 거래 내역 / 탈퇴 / 이전으로
	public int userInfoMenu(String id) {
		System.out.println();
		System.out.println("   1.수정   2.거래 내역   3.탈퇴   4.이전으로");
		System.out.print(">> 선택 : ");
		String input = sc.nextLine().trim();
		
		int num = 0;
		try {
			num = Integer.parseInt(input);

			if (num < 1 || num > 4) {
				System.out.println(">> 1 / 2 / 3 / 4 중 하나의 숫자를 입력해 주세요.");
			}

		} catch (NumberFormatException e) {
			System.out.println(">> 숫자를 입력해 주세요.");
		}

		if (num == 1) { updateInfo(id); }
		if (num == 2) { /* 거래내역 */ }
		if (num == 3) {
			boolean isAdmin = mdao.selectMember(loginId).isAdmin();
			if (isAdmin) { withdraw(id); } 
			else { withdraw(loginId); }
		}

		return num;
	}
	
	// 3.4, 4.?. 특정 사용자 정보 수정
	public void updateInfo(String id) {
		boolean isAdmin = mdao.selectMember(loginId).isAdmin();
		MemberVO mvo = mdao.selectMember(id);
		String newId = mvo.getId();
		String newInactivatedInput = "";
		boolean newInactivated = mvo.isInactivated();
		String newMileageInput = "";
		int newMileage = mvo.getMileage();
		
		System.out.println();
		System.out.println(">> 사용자 정보 수정 -------------------");
		
		if (isAdmin) {
			System.out.print("   변경할 아이디(기존값: " + id + "): ");
			newId = sc.nextLine().trim();
			if (newId.equals("")) { newId = mvo.getId(); }
		}
		
		System.out.print("   변경할 비밀번호(기존값: " + mvo.getPw() + "): ");
		String newPw = sc.nextLine().trim();
		if (newPw.equals("")) { newPw = mvo.getPw(); }
		if (mdao.invalidPwLen(newPw)) {
			System.out.println(">> 8 ~ 12 이내 혹은 빈 값(기존값 유지)을 입력해 주세요.");
			return;
		}
		
		System.out.print("   변경할 이름(기존값: " + mvo.getName() + "): ");
		String newName = sc.nextLine().trim();
		if (newName.equals("")) { newName = mvo.getName(); }
		
		System.out.print("   변경할 이메일(기존값: " + mvo.getEmail() + "): ");
		String newEmail = sc.nextLine().trim();
		if (newEmail.equals("")) { newEmail = mvo.getEmail(); }
		if (mdao.invalidEmailFmt(newEmail)) {
			System.out.println(">> 올바른 이메일 형식 혹은 빈 값(기존값 유지)을 입력해 주세요.");
			return;
		}
		
		System.out.print("   변경할 계좌 번호(기존값: " + mvo.getAccountNo() + "): ");
		String newAccountNo = sc.nextLine().trim();
		if (newAccountNo.equals("")) { newAccountNo = mvo.getAccountNo(); }
		if (mdao.invalidAccountNoFmt(newAccountNo)) {
			System.out.println(">> 10 ~ 14 자리의 숫자 혹은 빈 값(기존값 유지)을 입력해 주세요.");
			return;
		}
		
		if (isAdmin) {
			System.out.print("   계정 비활성화 여부(기존값: " + mvo.isInactivated() + "): ");
			newInactivatedInput = sc.nextLine().trim();
			if (newInactivatedInput.equals("")) { 
				newInactivatedInput = mvo.isInactivated() ? "T" : "F";
			}
			if (mdao.invalidInactivated(newInactivatedInput)) {
				System.out.println(">> T / F 혹은 빈 값(기존값 유지)을 입력해 주세요");
				return;
			}
			newInactivated = newInactivatedInput.equals("T");
			
			System.out.print("   변경할 마일리지(기존값: " + mvo.getMileage() + "): ");
			newMileageInput = sc.nextLine().trim();
			if (newMileageInput.equals("")) { newMileageInput = mvo.getMileage() + ""; }
			try {
				Integer.parseInt(newMileageInput);
			} catch(NumberFormatException e) {
				System.out.println(">> 숫자 혹은 빈 값(기존값 유지)을 입력해 주세요.");
			}
			newMileage = Integer.parseInt(newMileageInput);
		}
		
		MemberVO updateInfo = new MemberVO(newId, newPw, newName, newEmail,
										   newAccountNo, newInactivated, newMileage);
		
		if (mdao.updateMember(id, updateInfo)) {
			System.out.println(">> 회원 정보 업데이트가 완료되었습니다.");
		} else {
			System.out.println(">> 회원 정보 업데이트에 실패했습니다.");
		}
		
		System.out.println();
	}
	
	// 3.5., 4.? 특정 사용자 탈퇴
	public void withdraw(String id) {
		System.out.println();
		System.out.println(">> 회원 탈퇴 --------------");
		System.out.print("   정말로 탈퇴하시겠습니까?(y): ");
		String input = sc.nextLine().trim();
		String inputPw = "";
		
		if (input.equalsIgnoreCase("y")) {
			boolean isAdmin = mdao.selectMember(loginId).isAdmin();
			
			if (!isAdmin) {
				System.out.print("   탈퇴 처리를 위해 비밀번호를 입력해주세요: ");
				inputPw = sc.nextLine().trim();
			}
			
			boolean isDeleted = isAdmin ? mdao.deleteMember(id) : mdao.deleteMember(id, inputPw);

			if (isDeleted) {
				System.out.println(">> 탈퇴가 완료되었습니다.");
				System.out.println();
				return;
			}

			System.out.println(">> 비밀번호가 일치하지 않습니다.");
		} else {
			System.out.println(">> 회원 탈퇴를 취소했습니다.");
		}
	}
	
	// 4. 관리자 메뉴 - 계좌관리 / 전체거래내역 / 회원관리
	public void adminMenu() {
		while (true) {
			System.out.println();
			System.out.println(">> 메뉴 화면 -----------------------");
			System.out.println("   1.계좌관리   2.전체거래내역   3.회원관리   4.이벤트관리   5.로그아웃");
			System.out.print(">> 선택: ");
			String input = sc.nextLine().trim();
			
			int num = 0;
			try {
				num = Integer.parseInt(input);
				
				if (num < 1 || num > 4) {
					System.out.println(">> 1 / 2 / 3 / 4 중 하나의 숫자를 입력해 주세요.");
					System.out.println();
				}
				
			} catch (NumberFormatException e) {
				System.out.println(">> 숫자를 입력해 주세요.");
				System.out.println();
			}
			
			if (num == 1) { bankAccountInfo(); }
			if (num == 2) { /* 거래내역 */ }
			if (num == 3) { manageMember(); }
			if (num == 4) { qm.adminEvent(); }
			if (num == 5) {
				System.out.println(">> 로그아웃이 완료되었습니다.");
				System.out.println();
				loginId = "";
				return;
			}
		}
	}
	
	// 4.1 계좌 관리 - 전체 / 입금 / 출금 / 특정사용자
	public void bankAccountInfo() {
		while(true) {
			System.out.println();
			System.out.println("   조회 방식을 선택해 주세요.");
			System.out.println("   1.전체입출금내역   2.입금내역   3.출금내역   4.특정사용자입출금   5.이전화면");
			System.out.print(">> 선택: ");
			String input = sc.nextLine().trim();
			
			int num = 0;
			try {
				num = Integer.parseInt(input);
				
				if (num < 1 || num > 5) {
					System.out.println(">> 1 / 2 / 3 / 4 / 5 중 하나의 숫자를 입력해 주세요.");
					System.out.println();
					return;
				}
				
			} catch (NumberFormatException e) {
				System.out.println(">> 숫자를 입력해 주세요.");
				System.out.println();
				return;
			}
			
			if (num == 5) { return; }
			
			System.out.println();
			System.out.println("   정렬 방식을 선택해 주세요.");
			System.out.println("   1.최신순   2.날짜순");
			System.out.print(">> 선택: ");
			input = sc.nextLine().trim();
			
			int sort = 0;
			try {
				sort = Integer.parseInt(input);
				
				if (sort < 1 || sort > 2) {
					System.out.println(">> 1 / 2 중 하나의 숫자를 입력해 주세요.");
					System.out.println();
				}
				
			} catch (NumberFormatException e) {
				System.out.println(">> 숫자를 입력해 주세요.");
				System.out.println();
				return;
			}
			
			if (num == 1 || num == 2 || num == 3) {
				List<BankTransactionVO> transactions = btdao.selectBankTransaction(num, sort);
				showBankTransaction(transactions);
			}
			if (num == 4) { bankTransactionInfoById(sort); }
			
		}
	}
	
	// 4.1.1 계좌 내역들 출력 포맷 - 상세 조회
	public void showBankTransaction(List<BankTransactionVO> transactions) {
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy. MM. dd a hh:mm:ss");
		
		if (transactions.size() < 1) {
			System.out.println(">> 내역이 존재하지 않습니다.");
		} else {
			System.out.println();
		}
		
		while(true) {
			System.out.printf("%5s\t|%4s\t|%8s\t|%4s\t|%4s\t|%4s\t|%14s\n", 
					"내역번호", "항목", "계좌번호", "예금자명", "아이디", "금액" , "입출금일시");
			System.out.println("-".repeat(90));
			
			for (BankTransactionVO transaction : transactions) {
				System.out.printf("%s\t|%s\t|%s\t|%s\t|%s\t|%s\t|"
									+ dateFmt.format(transaction.getTransactionDate()) + "\n",
									transaction.getTransactionNo(), transaction.getTransactionType(),
									mdao.selectAccountNo(transaction.getTransactionId()), 
									transaction.getAccountHolder(),
									transaction.getTransactionId(), transaction.getAmount());
			}
			
			int opt = goDetailMenu();
			
			if (opt == 1) { bankTransactionDetail(); }
			if (opt == 2) { return; }
		}
	}
	
	// 4.1.4 계좌 내역 출력 - 특정 사용자만
	public void bankTransactionInfoById(int sort) {
		System.out.println();
		System.out.print("   조회할 회원 아이디: ");
		String id = sc.nextLine().trim();
		
		if ( (mvo = mdao.selectMember(id)) == null) {
			System.out.println(">> 존재하지 않는 사용자입니다.");
			return;
		}
		
		List<BankTransactionVO> transactions = btdao.selectBankTransaction(id, sort);
		
		showBankTransaction(transactions);
		
	}
	
	// 4.1.0 (공통)상세 보기 메뉴 - 상세보기/이전메뉴로
	public int goDetailMenu() {
		int num = 0;
		
		System.out.println();
		System.out.println("   1.상세보기   2.이전메뉴로");
		System.out.print(">> 선택 : ");
		String input = sc.nextLine().trim();
		
		try {
			num = Integer.parseInt(input);

			if (num < 1 || num > 2) {
				System.out.println(">> 1 / 2 중 하나의 숫자를 입력해 주세요.");
				System.out.println();
			}

		} catch (NumberFormatException e) {
			System.out.println(">> 숫자를 입력해 주세요.");
			System.out.println();
		}
		
		return num;
	}
	
	// 4.1.1.2 계좌 내역 상세 보기 번호 받아 출력
	public void bankTransactionDetail() {
		System.out.println();
		System.out.println(">> 계좌 내역 상세보기 -----");
		
		System.out.print("   선택할 내역 번호: ");
		String input = sc.nextLine().trim();
		
		try {
			int transactionNo = Integer.parseInt(input);
			if ( (btvo = btdao.selectBankTransaction(transactionNo)) == null ) {
				System.out.println(">> 해당하는 내역 번호가 존재하지 않습니다.");
				return;
			}
			
			System.out.println("   내역번호  : " + btvo.getTransactionNo());
			System.out.println("   항목\t: " + btvo.getTransactionType());
			System.out.println("   계좌번호  : " + mdao.selectAccountNo(btvo.getTransactionId()));
			System.out.println("   예금자명  : " + btvo.getAccountHolder());
			System.out.println("   아이디\t: " + btvo.getTransactionId());
			System.out.println("   금액\t: " + btvo.getAmount());
			System.out.println("   입출금입자 : " + btvo.getTransactionDate());
			
			
		} catch (NumberFormatException e) {
			System.out.println(">> 숫자를 입력해 주세요.");
		}
	}
	
	// 4.3 회원 관리(전체회원조회) - 상세보기
	public void manageMember() {
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy. MM. dd a hh:mm:ss");
		
		List<MemberVO> members = mdao.selectMember();
		
		if (members.size() < 1) {
			System.out.println(">> 내역이 존재하지 않습니다.");
		} else {
			System.out.println();
		}
		
		while(true) {
			System.out.println();
			System.out.printf("%5s\t|%4s\t|%8s\t|%14s\n", "아이디", "이름", "이메일", "가입일자");
			System.out.println("-----------------------------------------------------------");

			for (MemberVO member : mdao.selectMember()) {
				System.out.printf("%s\t|%s\t|%s\t|" 
						+ dateFmt.format(member.getJoinDate()) + "\n", 
						member.getId(), member.getName(), member.getEmail());
			}
			
			int opt = goDetailMenu();
			
			if (opt == 1) { memberDetail(); }
			if (opt == 2) { return; }
		}
	}
	
	// 4.3.1 회원수정 / 회원거래내역 / 회원삭제 작업할 사용자 받기
	public void memberDetail() {
		System.out.println();
		System.out.print("   조회할 회원 아이디: ");
		String id = sc.nextLine().trim();
		
		if ( (mvo = mdao.selectMember(id)) == null) {
			System.out.println(">> 존재하지 않는 사용자입니다.");
			return;
		}
		
		userInfo(id);
		
	}
	
	// 5. 회원 가입
	public void join() {
		System.out.println();
		System.out.println(">> 회원 가입 ------------------");
		System.out.print("   아이디\t: ");
		String id = sc.nextLine().trim();
		if (id.equals("")) {
			System.out.println(">> 아이디는 필수항목입니다.");
			return;
		}
		if (mdao.isDuplicated(id)) {
			System.out.println(">> 중복된 아이디입니다.");
			System.out.println(">> 메인 메뉴로 돌아갑니다.");
			System.out.println();
			return;
		}
		
		System.out.print("   비밀번호(8~12자리): ");
		String pw = sc.nextLine().trim();
		if (pw.equals("")) {
			System.out.println(">> 비밀번호는 필수항목입니다.");
			return;
		}
		if (mdao.invalidPwLen(pw)) { 
			System.out.println(">> 비밀번호는 8 ~ 12 사이로 입력해 주세요.");
			return; 
		}
		
		System.out.print("   이름\t: ");
		String name = sc.nextLine().trim();
		if (name.equals("")) {
			System.out.println(">> 이름은 필수항목입니다.");
			return;
		}
		
		System.out.print("   이메일\t: ");
		String email = sc.nextLine().trim();
		if (email.equals("")) {
			System.out.println(">> 이메일은 필수항목입니다.");
			return;
		}
		
		if (mdao.invalidEmailFmt(email)) {
			System.out.println(">> 이메일 형식이 올바르지 않습니다.");
			return;
		}
		
		System.out.print("   계좌번호(숫자만 입력): ");
		String accountNo = sc.nextLine().trim();
		if (accountNo.equals("")) {
			System.out.println(">> 계좌번호는 필수항목입니다.");
			return;
		}
		if (mdao.invalidAccountNoFmt(accountNo)) {
			System.out.println(">> 10 ~ 14 자리의 숫자를 입력해 주세요.");
			return;
		}
		
		mvo = new MemberVO(id, pw, name, email, accountNo);
		
		// db insert 검증
		if (mdao.insertMember(mvo)) {
			System.out.println(">> 회원 가입이 완료되었습니다.");
			System.out.println(">> 로그인 후 이용해 주세요.");
		} else {
			System.out.println(">> 회원 가입에 실패하였습니다.");
			System.out.println(">> 잠시 후 다시 이용해 주세요.");
		}
		
		System.out.println();
	}
	
	public static void main(String[] args) {
		new MemberMain().menu();
	}
}
