package com.sist.dao;
import java.security.DrbgParameters.Reseed;
import java.sql.*;
import java.util.*;
public class MemberDAO {

	private Connection conn; //DB 연결을 위한 Connection 객체 생성
	private PreparedStatement ps; //SQL을 담는 PreparedStatement 객체 생성
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	
	//드라이버 등록
	public MemberDAO()
	{
		try
		{
			//Driver 클래스 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}catch(Exception ex){}
	}
	//연결 (열기)
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy"); //오라클로 전송 conn hr/happy
		}catch(Exception ex){}
	}
	//해제 (닫기, 종료)
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			//exit
		}catch(Exception ex){}
	}
	//기능 --> 로그인
	public String isLogin(String id,String pwd)
	{
		String result="";
		try
		{
			//연결
			getConnection();
			//SQL문장 제작
			String sql="SELECT COUNT(*) FROM member WHERE id='"+id+"'"; //이 id를 가진 애가 몇 개냐 (0 없음, 1 있
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			if(count==0)
			{
				result="NOID";						
			}
			else
			{
				sql="SELECT pwd,name FROM member WHERE id='"+id+"'"; //id에 해당하는 비밀번호와 이름을 달라
				ps=conn.prepareStatement(sql);
				rs=ps.executeQuery();
				rs.next();
				String db_pwd=rs.getString(1);
				String name=rs.getString(2);
				if(db_pwd.equals(pwd)) //로그인된 상태
				{
					result=name;
				}
				else
				{
					result="NOPWD";
				}
			}
			//오라클 전송
			//결과값 받기
		}catch(Exception ex)
		{
			ex.printStackTrace(); //오류 확인
		}
		finally
		{
			disConnection(); //해제 - 연결되든 오류나든 닫아야 함
		}
		return result;
	}

}
