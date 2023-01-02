/*

    1/2
    맛집상세정보
    
    fno NUMBER,
    fcno NUMBER,
    poster VARCHAR2(4000) CONSTRAINT fd_poster_nn_4 NOT NULL,
    name VARCHAR2(200) CONSTRAINT fd_name_nn_4 NOT NULL,
    score NUMBER(2,1) CONSTRAINT fd_score_nn_4 NOT NULL,
    addr VARCHAR2(300)CONSTRAINT fd_addr_nn_4 NOT NULL, 
    tel VARCHAR2(30) CONSTRAINT fd_tel_nn_4 NOT NULL,
    type VARCHAR2(100) CONSTRAINT fd_type_nn_4 NOT NULL,
    price VARCHAR2(100),
    time VARCHAR2(200),
    menu VARCHAR2(1000),
    parking VARCHAR2(200),
    jjim NUMBER DEFAULT 0,
    flike NUMBER DEFAULT 0,
    hit NUMBER DEFAULT 0,
    good NUMBER,
    soso NUMBER,
    bad NUMBER,

*/
package com.sist.dao;

import java.util.*;
import java.sql.*;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	//private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private final String URL="jdbc:oracle:thin:@211.63.89.131:1521:XE";
	
	public FoodDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
		
	}
	
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
		
	}
	
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	
	
	
	
	//맛집 카테고리 추가
	public void foodCategoryInsert(CategoryVO vo) //카테고리 30개 들어감
	{
		try
		{
			getConnection();
			String sql="INSERT INTO foodCategory_4 VALUES(gfc_fcno_seq_4.nextval,?,?,?,?)";
			//sql문장 전송
			ps=conn.prepareStatement(sql);
			//실행 전에 ?에 값 넣기
			ps.setString(1, vo.getTitle());		//첫번째 물음표에 넣기
			ps.setString(2, vo.getSubtitle());
			ps.setString(3, vo.getImage());
			ps.setString(4, vo.getLink());
			
			//실행요청
			ps.executeUpdate(); //커밋 포함
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
	
	//카테고리에서 카테고리 번호, 링크, 제목
	public ArrayList<CategoryVO> foodCategoryInfoData()
	{
		ArrayList<CategoryVO> list=new ArrayList<CategoryVO>();
		try
		{
			//1. 연결
			getConnection();
			//2. sql문장 제작
			String sql="SELECT fcno,title,link FROM gg_foodCategory_4 ORDER BY fcno ASC";
			//3. sql문장 전송
			ps=conn.prepareStatement(sql);
			//4. 실행 후 결과값 저장
			ResultSet rs=ps.executeQuery();
			//5. ArrayList에 담기
			while(rs.next())
			{
				CategoryVO vo=new CategoryVO();
				vo.setFcno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setLink("https://www.mangoplate.com"+rs.getString(3));
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
	
	//맛집상세정보 넣기
	public void foodDetailInsert(FoodVO vo)
	{
		try
		{
			getConnection();
			String sql="INSERT INTO gg_foodDetail_4(fno,fcno,poster,name,score,addr,tel,type,price,time,menu,"
						+"parking,good,soso,bad) VALUES(gfd_fno_seq_4.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			//?에 값 채우기
			ps.setInt(1, vo.getFcno());
			ps.setString(2, vo.getPoster());
			ps.setString(3, vo.getName());
			ps.setDouble(4, vo.getScore());
			ps.setString(5, vo.getAddr());
			ps.setString(6, vo.getTel());
			ps.setString(7, vo.getType());
			ps.setString(8, vo.getPrice());
			ps.setString(9, vo.getTime());
			ps.setString(10, vo.getMenu());
			ps.setString(11, vo.getParking());
			ps.setInt(12, vo.getGood());
			ps.setInt(13, vo.getSoso());
			ps.setInt(14, vo.getBad());
			//실행
			ps.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
	
	
}
