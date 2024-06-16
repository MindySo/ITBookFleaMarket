
/* Drop Triggers */

DROP TRIGGER TRI_t_account_transactionNo;
DROP TRIGGER TRI_t_bookDetails_detailNo;
DROP TRIGGER TRI_t_book_bookNo;
DROP TRIGGER TRI_t_mileage_log_mlLogNo;
DROP TRIGGER TRI_t_qna_qno;
DROP TRIGGER TRI_t_quiz_attend_aNo;
DROP TRIGGER TRI_t_quiz_attend_attendNo;
DROP TRIGGER TRI_t_quiz_attend_qzAttendNo;
DROP TRIGGER TRI_t_quiz_quizNo;
DROP TRIGGER TRI_t_quiz_qzNo;
DROP TRIGGER TRI_t_review_reviewNo;
DROP TRIGGER TRI_t_rouletteAttend_rtAttendNo;
DROP TRIGGER TRI_t_rtAttend_rtAttendNo;
DROP TRIGGER TRI_t_tradeLog_trLogNo;
DROP TRIGGER TRI_t_tradePost_postNo;
DROP TRIGGER TRI_t_trade_tradeNo;



/* Drop Tables */

DROP TABLE t_account CASCADE CONSTRAINTS;
DROP TABLE t_review CASCADE CONSTRAINTS;
DROP TABLE t_tradePost CASCADE CONSTRAINTS;
DROP TABLE t_tradeLog CASCADE CONSTRAINTS;
DROP TABLE t_book CASCADE CONSTRAINTS;
DROP TABLE t_mileage_log CASCADE CONSTRAINTS;
DROP TABLE t_qna CASCADE CONSTRAINTS;
DROP TABLE t_quiz_attend CASCADE CONSTRAINTS;
DROP TABLE t_member CASCADE CONSTRAINTS;
DROP TABLE t_quiz CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE SEQ_t_account_transactionNo;
DROP SEQUENCE SEQ_t_bookDetails_detailNo;
DROP SEQUENCE SEQ_t_book_bookNo;
DROP SEQUENCE SEQ_t_mileage_log_mlLogNo;
DROP SEQUENCE SEQ_t_qna_qno;
DROP SEQUENCE SEQ_t_quiz_attend_aNo;
DROP SEQUENCE SEQ_t_quiz_attend_attendNo;
DROP SEQUENCE SEQ_t_quiz_attend_qzAttendNo;
DROP SEQUENCE SEQ_t_quiz_quizNo;
DROP SEQUENCE SEQ_t_quiz_qzNo;
DROP SEQUENCE SEQ_t_review_reviewNo;
DROP SEQUENCE SEQ_t_rouletteAttend_rtAttendNo;
DROP SEQUENCE SEQ_t_rtAttend_rtAttendNo;
DROP SEQUENCE SEQ_t_tradeLog_trLogNo;
DROP SEQUENCE SEQ_t_tradePost_postNo;
DROP SEQUENCE SEQ_t_trade_tradeNo;




/* Create Sequences */

CREATE SEQUENCE SEQ_t_account_transactionNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_bookDetails_detailNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_book_bookNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_mileage_log_mlLogNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_qna_qno INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_quiz_attend_aNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_quiz_attend_attendNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_quiz_attend_qzAttendNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_quiz_quizNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_quiz_qzNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_review_reviewNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_rouletteAttend_rtAttendNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_rtAttend_rtAttendNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_tradeLog_trLogNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_tradePost_postNo INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_t_trade_tradeNo INCREMENT BY 1 START WITH 1;



/* Create Tables */

CREATE TABLE t_account
(
	transactionNo number NOT NULL,
	transactionType varchar2(2) NOT NULL,
	accountHolder varchar2(20) NOT NULL,
	transactionId varchar2(20) NOT NULL,
	amount number,
	transactionDate date,
	PRIMARY KEY (transactionNo)
);


CREATE TABLE t_book
(
	bookNo number NOT NULL,
	bookName varchar2(10) NOT NULL,
	bookGroup varchar2(10) NOT NULL,
	bookPrice number NOT NULL,
	bookState varchar2(2) NOT NULL,
	author varchar2(10) NOT NULL,
	publisher varchar2(10) NOT NULL,
	isbn number NOT NULL,
	seller varchar2(20) NOT NULL,
	PRIMARY KEY (bookNo)
);


CREATE TABLE t_member
(
	id varchar2(20) NOT NULL UNIQUE,
	pw varchar2(20) NOT NULL,
	name varchar2(20) NOT NULL,
	email varchar2(50) NOT NULL,
	accountNo number NOT NULL,
	reportNo number,
	isInactivated char(1),
	inactiveDate date,
	isAdmin char(1),
	joinDate date,
	mileage number,
	purchaseCnt number,
	saleCnt number,
	PRIMARY KEY (id)
);


CREATE TABLE t_mileage_log
(
	mlLogNo number NOT NULL,
	userId varchar2(20) NOT NULL UNIQUE,
	mlLogType varchar2(20),
	mlAmount number,
	mlLogDate date,
	PRIMARY KEY (mlLogNo)
);


CREATE TABLE t_qna
(
	qno number NOT NULL,
	qId varchar2(20) NOT NULL,
	questionTitle varchar2(10) NOT NULL,
	questionContent varchar2(100) NOT NULL,
	questioRregdate date DEFAULT SYSDATE NOT NULL,
	answerState varchar2(5),
	answerContent varchar2(100),
	answerDate date,
	PRIMARY KEY (qno)
);


CREATE TABLE t_quiz
(
	qzNo number NOT NULL,
	qzTitle varchar2(100),
	qzCorrectNum number,
	qzReward number,
	qzStartDate date,
	endDate date,
	qzRegDate date,
	qzModDate date,
	PRIMARY KEY (qzNo)
);


CREATE TABLE t_quiz_attend
(
	qzAttendNo number NOT NULL,
	qzNo number NOT NULL,
	id varchar2(20) NOT NULL UNIQUE,
	qzAnswer number,
	qzAttendDate date,
	PRIMARY KEY (qzAttendNo)
);


CREATE TABLE t_review
(
	reviewNo number NOT NULL,
	postNo number NOT NULL,
	rate number NOT NULL,
	reviewContent varchar2(1000),
	reviewRegdate date,
	PRIMARY KEY (reviewNo)
);


CREATE TABLE t_tradeLog
(
	trLogNo number NOT NULL UNIQUE,
	bookNo number NOT NULL,
	tradePrice number,
	tradeDate date,
	tradeState varchar2(20) NOT NULL,
	invoiceNo number,
	buyer varchar2(20),
	mlLogNo number,
	PRIMARY KEY (trLogNo)
);


CREATE TABLE t_tradePost
(
	postNo number NOT NULL,
	bookNo number NOT NULL,
	trLogNo number NOT NULL UNIQUE,
	memo varchar2(1000),
	postRegdate date,
	postHitNo number,
	PRIMARY KEY (postNo)
);



/* Create Foreign Keys */

ALTER TABLE t_tradeLog
	ADD FOREIGN KEY (bookNo)
	REFERENCES t_book (bookNo)
;


ALTER TABLE t_tradePost
	ADD FOREIGN KEY (bookNo)
	REFERENCES t_book (bookNo)
;


ALTER TABLE t_account
	ADD FOREIGN KEY (transactionId)
	REFERENCES t_member (id)
;


ALTER TABLE t_book
	ADD FOREIGN KEY (seller)
	REFERENCES t_member (id)
;


ALTER TABLE t_mileage_log
	ADD FOREIGN KEY (userId)
	REFERENCES t_member (id)
;


ALTER TABLE t_qna
	ADD FOREIGN KEY (qId)
	REFERENCES t_member (id)
;


ALTER TABLE t_quiz_attend
	ADD FOREIGN KEY (id)
	REFERENCES t_member (id)
;


ALTER TABLE t_tradeLog
	ADD FOREIGN KEY (buyer)
	REFERENCES t_member (id)
;


ALTER TABLE t_tradeLog
	ADD FOREIGN KEY (mlLogNo)
	REFERENCES t_mileage_log (mlLogNo)
;


ALTER TABLE t_quiz_attend
	ADD FOREIGN KEY (qzNo)
	REFERENCES t_quiz (qzNo)
;


ALTER TABLE t_tradePost
	ADD FOREIGN KEY (trLogNo)
	REFERENCES t_tradeLog (trLogNo)
;


ALTER TABLE t_review
	ADD FOREIGN KEY (postNo)
	REFERENCES t_tradePost (postNo)
;



/* Create Triggers */

CREATE OR REPLACE TRIGGER TRI_t_account_transactionNo BEFORE INSERT ON t_account
FOR EACH ROW
BEGIN
	SELECT SEQ_t_account_transactionNo.nextval
	INTO :new.transactionNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_bookDetails_detailNo BEFORE INSERT ON t_bookDetails
FOR EACH ROW
BEGIN
	SELECT SEQ_t_bookDetails_detailNo.nextval
	INTO :new.detailNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_book_bookNo BEFORE INSERT ON t_book
FOR EACH ROW
BEGIN
	SELECT SEQ_t_book_bookNo.nextval
	INTO :new.bookNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_mileage_log_mlLogNo BEFORE INSERT ON t_mileage_log
FOR EACH ROW
BEGIN
	SELECT SEQ_t_mileage_log_mlLogNo.nextval
	INTO :new.mlLogNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_qna_qno BEFORE INSERT ON t_qna
FOR EACH ROW
BEGIN
	SELECT SEQ_t_qna_qno.nextval
	INTO :new.qno
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_quiz_attend_aNo BEFORE INSERT ON t_quiz_attend
FOR EACH ROW
BEGIN
	SELECT SEQ_t_quiz_attend_aNo.nextval
	INTO :new.aNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_quiz_attend_attendNo BEFORE INSERT ON t_quiz_attend
FOR EACH ROW
BEGIN
	SELECT SEQ_t_quiz_attend_attendNo.nextval
	INTO :new.attendNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_quiz_attend_qzAttendNo BEFORE INSERT ON t_quiz_attend
FOR EACH ROW
BEGIN
	SELECT SEQ_t_quiz_attend_qzAttendNo.nextval
	INTO :new.qzAttendNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_quiz_quizNo BEFORE INSERT ON t_quiz
FOR EACH ROW
BEGIN
	SELECT SEQ_t_quiz_quizNo.nextval
	INTO :new.quizNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_quiz_qzNo BEFORE INSERT ON t_quiz
FOR EACH ROW
BEGIN
	SELECT SEQ_t_quiz_qzNo.nextval
	INTO :new.qzNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_review_reviewNo BEFORE INSERT ON t_review
FOR EACH ROW
BEGIN
	SELECT SEQ_t_review_reviewNo.nextval
	INTO :new.reviewNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_rouletteAttend_rtAttendNo BEFORE INSERT ON t_rouletteAttend
FOR EACH ROW
BEGIN
	SELECT SEQ_t_rouletteAttend_rtAttendNo.nextval
	INTO :new.rtAttendNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_rtAttend_rtAttendNo BEFORE INSERT ON t_rtAttend
FOR EACH ROW
BEGIN
	SELECT SEQ_t_rtAttend_rtAttendNo.nextval
	INTO :new.rtAttendNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_tradeLog_trLogNo BEFORE INSERT ON t_tradeLog
FOR EACH ROW
BEGIN
	SELECT SEQ_t_tradeLog_trLogNo.nextval
	INTO :new.trLogNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_tradePost_postNo BEFORE INSERT ON t_tradePost
FOR EACH ROW
BEGIN
	SELECT SEQ_t_tradePost_postNo.nextval
	INTO :new.postNo
	FROM dual;
END;

/

CREATE OR REPLACE TRIGGER TRI_t_trade_tradeNo BEFORE INSERT ON t_trade
FOR EACH ROW
BEGIN
	SELECT SEQ_t_trade_tradeNo.nextval
	INTO :new.tradeNo
	FROM dual;
END;

/




