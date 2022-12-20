package com.sist.dao;
import java.util.*;
import java.sql.*;
/*

회원가입의 일부 --> 아이디 중복체크, 우편번호 검색

*/
public class ZipcodeDAO {
	private Connection conn; //오라클 연결 --> 인터페이스
	private PreparedStatement ps; //송수신 --> sql문장 전송 / 실행 후 결과값 받기
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//드라이버 설정
	public ZipcodeDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Class.forName : 클래스 정보를 읽어옴 --> 메모리 할당, 메소드 호출, 변수 초기화
			//리플렉션 --> 주로 사용 (스프링, 마이바티스)
		}catch(Exception ex) {}
	}
	//연결 --> 검색(입력값)
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
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex){}
	}
	
	//기능 2개
	//1. 우편번호 검색
	public ArrayList<ZipcodeVO> postFind(String dong)
	{
		ArrayList<ZipcodeVO> list=new ArrayList<ZipcodeVO>();
		try
		{
			//1. 연결
			getConnection();
			//2. sql문장 제작
			String sql="SELECT zipcode,sido,gugun,dong,NVL(bunji,' ') "
					+"FROM zipcode "
					+"WHERE dong LIKE '%'||?||'%'";
			//3. sql문장 오라클로 전송
			ps=conn.prepareStatement(sql);
			//4. ?에 값 채우기
			ps.setString(1, dong);
			//5. 실행 후 결과값 가져오기
			ResultSet rs=ps.executeQuery();
			while(rs.next())  //검색결과가 여러 개일 수 있으니 while문 사용
			{
				ZipcodeVO vo=new ZipcodeVO();
				vo.setZipcode(rs.getString(1));
				vo.setSido(rs.getString(2));
				vo.setGugun(rs.getString(3));
				vo.setDong(rs.getString(4));
				vo.setBunji(rs.getString(5));
				list.add(vo);
			}
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
	//2. 검색 개수
	public int postCount(String dong)
	{
		int count=0;
		try
		{
			//연결
			getConnection();
			//sql문장 제작
			String sql="SELECT COUNT(*) "
						+"FROM zipcode "
						+"WHERE dong LIKE '%'||?||'%'";
			//오라클 전송
			ps=conn.prepareStatement(sql);
			//?에 값을 채우기
			ps.setString(1, dong);
			//실행 후 결과값 가져오기
			ResultSet rs=ps.executeQuery();
			//count에 저장
			rs.next();
			count=rs.getInt(1);
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			//해제
			disConnection();
		}
		return count;
	}
	
	
	
}
