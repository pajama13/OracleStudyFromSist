
package com.sist.dao;
import java.util.*;
import java.sql.*;



public class TripDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@211.63.89.131:1521:XE";
		
	//드라이버 등록
	public TripDAO()
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
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	
	//명소 상세정보 넣기 (카테고리 1번)
	public void landmarkDetailInsert(TripVO vo)
	{
		try
		{
			getConnection();
			
			String sql="INSERT INTO gg_trip_4(tno,tcno,name,image,content,addr) VALUES(gt_tno_seq_4.nextval,1,?,?,?,?)";
			
			ps=conn.prepareStatement(sql);
			//?에 값 채우기
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getImage());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getAddr());
			//실행
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
	
	//자연 상세정보 넣기 (카테고리 2번)
	public void natureDetailInsert(TripVO vo)
	{
		try
		{
			getConnection();
			
			String sql="INSERT INTO gg_trip_4(tno,tcno,name,image,content,addr) VALUES(gt_tno_seq_4.nextval,2,?,?,?,?)";
			
			ps=conn.prepareStatement(sql);
			//?에 값 채우기
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getImage());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getAddr());
			//실행
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
	
	//즐길거리 상세정보 넣기 (카테고리 3번)
	public void entertainmentDetailInsert(TripVO vo)
	{
		try
		{
			getConnection();
			
			String sql="INSERT INTO gg_trip_4(tno,tcno,name,image,content,addr) VALUES(gt_tno_seq_4.nextval,3,?,?,?,?)";
			
			ps=conn.prepareStatement(sql);
			//?에 값 채우기
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getImage());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getAddr());
			//실행
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
	
	//쇼핑 상세정보 넣기 (카테고리 4번)
	public void shoppingDetailInsert(TripVO vo)
	{
		try
		{
			getConnection();
			
			String sql="INSERT INTO gg_trip_4(tno,tcno,name,image,content,addr) VALUES(gt_tno_seq_4.nextval,4,?,?,?,?)";
			
			ps=conn.prepareStatement(sql);
			//?에 값 채우기
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getImage());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getAddr());
			//실행
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
	
	//행사
	public void festivalDetailInsert(FestivalVO vo)
	{
		try
		{
			getConnection();
			
			String sql="INSERT INTO gg_festival_4(fno,name,image,content,period,web,addr) VALUES(gf_fno_seq_4.nextval,?,?,?,?,?,?)";
			
			ps=conn.prepareStatement(sql);
			//?에 값 채우기
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getImage());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPeriod());
			ps.setString(5, vo.getWeb());
			ps.setString(6, vo.getAddr());
			//실행
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
}
