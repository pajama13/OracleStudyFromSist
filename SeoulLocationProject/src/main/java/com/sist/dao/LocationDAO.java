package com.sist.dao;
//오라클 연결
import java.util.*; //ArrayList로 데이터 묶기
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.sql.*; //오라클 연결하여 송수신 : SQL문장 전송, 데이터 수신
/*

	LocationVO
	오라클에 저장 - 1개 명소 저장 --> new LocationVO()
	오라클에 저장 - 1개 명소 저장 --> new LocationVO()
	오라클에 저장 - 1개 명소 저장 --> new LocationVO()

*/
public class LocationDAO {
	//데이터베이스 연결객체
	private Connection conn;
	//데이터베이스 송수신 가능하게 하기
	private PreparedStatement ps;
	//SQL문장 전송, 데이터 받기
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//드라이버등록
	public LocationDAO()
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
	//닫기
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	//---------------------------여기까지 모든 DAO파일이 동일
	//기능 - SQL문장
	public ArrayList<LocationVO> locationListData()
	{
		ArrayList<LocationVO> list=new ArrayList<LocationVO>();
		try
		{
			//1. 연결
			getConnection();
			//2. SQL문장 제작
			String sql="SELECT no,title,poster,msg,rownum " //rownum으로 원하는 갯수만큼 잘라오기
						+"FROM seoul_location "
						+"WHERE rownum<=20"; 		//=WHERE no BETWEEN 1 AND 20
			//3. 오라클 전송
			ps=conn.prepareStatement(sql);
			//4. 실행 후 결과값 받기
			ResultSet rs=ps.executeQuery();		//rs에 오라클에서 실행된 결과값 저장하기
			//5. 결과값을 ArrayList에 담기 --> 이후 브라우저에서 ArrayList 읽어서 출력
			while(rs.next())
			{
				LocationVO vo=new LocationVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				vo.setMsg(rs.getString(4));
				list.add(vo);	//추출된 데이터 list에 전부 모아둠
			}
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
		return list;
	}
}
