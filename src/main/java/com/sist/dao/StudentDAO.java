package com.sist.dao;
import java.util.*;
import java.sql.*;
public class StudentDAO {
	private Connection conn; 
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE"; //오라클 주소
	
	//드라이버 등록 : 한번만 실행하는 것(자동로그인, 쿠키읽기 등), 변수 초기화 --> 생성자 사용
	public StudentDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	//오라클 연결
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	//오라클 연결 해제
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	//-------------------필수 공통사항
	//기능
	//1. 추가
	public void studentInsert(StudentVO vo) //학생 1명(테이블 1줄) 정보
	{
		try
		{
			//1. 연결
			getConnection();
			//2. sql문장 만들기
			//값이 없을 땐 NVL()사용, 자동증가는 서브쿼리 & MAX()+1 사용
			String sql="INSERT INTO student VALUES((SELECT NVL(MAX(hakbun)+1,1) FROM student),?,?,?,?)";
			//3. sql문장 전송
			ps=conn.prepareStatement(sql);
			//4. 실행 전에 ?에 값 채우기
			//setString : 자동으로 작은 따옴표 붙여줌
			ps.setString(1, vo.getName());  //1번째 물음표
			ps.setInt(2, vo.getKor());	    //2번째 물음표...
			ps.setInt(3, vo.getEng());
			ps.setInt(4, vo.getMath());
			//INSERT 실행하기 --> 자바는 오토커밋 (자동저장)
			//INSERT, UPDATE, DELETE --> executeUpdate() 사용 : 커밋 날림 (오토커밋) --> 취소하려면 트랜잭션-롤백(스프링 **)
			//SELECT --> executeQuery() 사용 : 커밋 없음
			ps.executeUpdate();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
	//2. 데이터 읽기
	public ArrayList<StudentVO> stuArrayList()
	{
		ArrayList<StudentVO> list=new ArrayList<StudentVO>();
		try
		{
			//1. 연결
			getConnection();
			//2. sql문장 만들기
			String sql="SELECT hakbun,name,kor,eng,math,(kor+eng+math) total,"
					+"ROUND((kor+eng+math)/3,2) avg "
					+"FROM student";
			//sql문장 전송
			ps=conn.prepareStatement(sql);
			//실행 후 데이터 요청
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				StudentVO vo=new StudentVO();
				vo.setHakbun(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setKor(rs.getInt(3));
				vo.setEng(rs.getInt(4));
				vo.setMath(rs.getInt(5));
				vo.setTotal(rs.getInt(6));
				vo.setAvg(rs.getDouble(7));
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
	//3. 수정
	//4. 삭제
}
