package com.sist.dao;
import java.util.*;

import oracle.jdbc.OracleTypes;

import java.sql.*;
//데이터베이스 연동 자바 프로그램 - 285p
//JDBC --> DBCP 웹 일반화 : ORM 라이브러리 --> MyBatis, JPA
public class StudentDAO {
	private Connection conn;
	private CallableStatement cs;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	//드라이버 연결
	public StudentDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
		
	}
	//연결
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	//해제
	public void disConnection()
	{
		try
		{
			if(cs!=null) cs.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	//기능 설정
	//1. 목록 출력
	/*
	 	CREATE OR REPLACE PROCEDURE studentListData(
  			pResult OUT SYS_REFCURSOR
		)
		IS
		BEGIN
		    OPEN pResult FOR
		      SELECT * FROM student;
		END;
	 	/
	 */
	public ArrayList<StudentVO> studentListData()
	{
		ArrayList<StudentVO> list=new ArrayList<StudentVO>();
		try
		{
			//1. 연결
			getConnection();
			//2. sql문장 제작
			String sql="{CALL studentListData(?)}";
			//3. 오라클 전송
			cs=conn.prepareCall(sql); //함수 호출 요청이어서 prepare"call"
			//4. ?에 값 채우기
			cs.registerOutParameter(1, OracleTypes.CURSOR);  //오라클 데이터형과 맞추기
			//5. 실행 요청
			cs.executeQuery();
			//6. 임시 저장된 메모리에서 데이터 가져오기
			ResultSet rs=(ResultSet)cs.getObject(1);
			while(rs.next())
			{
				StudentVO vo=new StudentVO();
				vo.setHakbun(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setKor(rs.getInt(3));
				vo.setEng(rs.getInt(4));
				vo.setMath(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
	
	//2. 데이터 추가
	/*
	 CREATE OR REPLACE PROCEDURE studentInsert(
	    pName student.name%TYPE, --IN 생략된 것 (=pName IN ~)
	    pKor student.kor%TYPE,
	    pEng student.eng%TYPE,
	    pMath student.math%TYPE
	 )
	 IS
    	pMax NUMBER:=0;
	 BEGIN
    	SELECT NVL(MAX(hakbun)+1,1) INTO pMax
    	FROM student;
    
    	INSERT INTO student VALUES(pMax,pName,pKor,pEng,pMath);
    	COMMIT;
  	END;
	/
	 
	 */
	public void studentInsert(StudentVO vo)
	{
		try
		{
			//1. 연결
			getConnection();
			//2. sql문장 제작
			String sql="{CALL studentInsert(?,?,?,?)}";
			//3. 프로시저 호출
			cs=conn.prepareCall(sql);
			//4. ?에 값 채우기
			cs.setString(1, vo.getName());
			cs.setInt(2, vo.getKor());
			cs.setInt(3, vo.getEng());
			cs.setInt(4, vo.getMath());
			
			//5. 실행요청
			cs.executeQuery(); //프로시저 안에 커밋 있어서 무조건 executeQuery() 사용해야 함
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		finally
		{
			//닫기
			disConnection();
		}
	}
	//3. 데이터 수정
	/*
	CREATE OR REPLACE PROCEDURE studentUpdate(
	    pHakbun student.hakbun%TYPE,
	    pName student.name%TYPE,
	    pKor student.kor%TYPE,
	    pEng student.eng%TYPE,
	    pMath student.math%TYPE
	)
	IS
	BEGIN
	    UPDATE student SET
	    name=pName,kor=pKor,eng=pEng,math=pMath
	    WHERE hakbun=pHakbun;
	    COMMIT;
	END;
	/
	 
	 */
	//4. 데이터 삭제
	/*
	 CREATE OR REPLACE PROCEDURE studentDelete(
    	pHakbun student.hakbun%TYPE
	)
	IS
	BEGIN
	    DELETE FROM student
	    WHERE hakbun=pHakbun;
	    COMMIT;
	END;
	/
	 
	 */
	
	//5. 데이터 검색
	/*
	 
	CREATE OR REPLACE PROCEDURE studentDetailData(
	    pHakbun student.hakbun%TYPE,
	    pName OUT student.name%TYPE,
	    pKor OUT student.kor%TYPE,
	    pEng OUT student.eng%TYPE,
	    pMath OUT student.math%TYPE,
	    pTotal OUT NUMBER,
	    pAvg OUT NUMBER
	)
	IS
	BEGIN
	    SELECT name,kor,eng,math,(kor+eng+math),ROUND((kor+eng+math)/3) INTO pName,pKor,pEng,pMath,pTotal,pAvg
	    FROM student
	    WHERE hakbun=pHakbun;
	    
	    DBMS_OUTPUT.PUT_LINE(pName);
	    DBMS_OUTPUT.PUT_LINE(pKor);
	    DBMS_OUTPUT.PUT_LINE(pEng);
	    DBMS_OUTPUT.PUT_LINE(pMath);
	    DBMS_OUTPUT.PUT_LINE(pTotal);
	    DBMS_OUTPUT.PUT_LINE(pAvg);
	END;
	/
	 
	 */
}
