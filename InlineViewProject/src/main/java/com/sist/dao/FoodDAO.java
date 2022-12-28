/*

아래 sql문장 수정하기

*/

package com.sist.dao;
import java.util.*;
import java.sql.*;
// VIEW 응용 : 페이징, 인기 Top-N
public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	//드라이버 등록
	public FoodDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(Exception ex) {}
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
	//------------------------
	//기능 - 페이징 기법 : 인라인뷰 --> 이미지 5개
	public ArrayList<FoodVO> foodListData(int page) //매개변수 : 사용자로부터 받는 값
	{
		ArrayList<FoodVO> list=new ArrayList<FoodVO>();
		
							//		try
							//		{
							//			//1. 연결
							//			getConnection();
							//			//2. sql문장 제작
							//			String sql="SELECT fno,name,poster "
							//						+"FROM food_location "
							//						+"ORDER BY fno ASC";
							//			//3. 오라클 전송
							//			ps=conn.prepareStatement(sql);
							//			//4. 실행 후 결과값 가져오기
							//			ResultSet rs=ps.executeQuery();
							//			
							//			int i=0; //20개씩 나눠주는 변수
							//			int j=0; //while문의 횟수
							//			final int rowSize=20; //1페이지의 개수
							//			int pagecnt=(page*rowSize)-rowSize; //시작위치 : ArrayList에 저장 시점
							//			
							//			while(rs.next())
							//			{
							//				if(i<rowSize && j>=pagecnt)
							//				{
							//					FoodVO vo=new FoodVO();
							//					vo.setFno(rs.getInt(1));
							//					vo.setName(rs.getString(2));
							//					String poster=rs.getString(3);
							//					poster=poster.substring(0,poster.indexOf("^"));
							//					vo.setPoster(poster);
							//					list.add(vo);
							//					i++;
							//				}
							//				j++;
							//			}
							//			rs.close();
							//			
							//			
							//			//5. 저장된 데이터를 ArrayList에 저장 - 브라우저의 요청마다 출력할 데이터를 보관
							//		}catch(Exception ex)
							//		{
							//			ex.printStackTrace();
							//		}
							//		finally
							//		{
							//			disConnection();
							//		}
		//------인라인뷰 --> 상위 5개, 페이지
		//적용되는 위치
		try
		{
			//1. 연결
			getConnection();
			//2. sql문장 제작
			String sql="SELECT fno,name,poster,num "
						+"FROM (SELECT fno,name,poster,rownum as num "
						+"FROM (SELECT fno,name,poster "
						+"FROM food_location ORDER BY fno ASC)) "
						+"WHERE num BETWEEN ? AND ?";
			//3. 오라클 전송
			ps=conn.prepareStatement(sql);
			//4. ?에 값 채우기
			int rowSize=20; //1p에 20개
			int start=(rowSize*page)-(rowSize-1);
				// 1p : 1~20
				// 2p : 21~40
			int end=rowSize*page;
			
			ps.setInt(1, start);  //1번째 물음표
			ps.setInt(2, end);	  //2번째 물음표
			
			//5. 결과값 받기
			ResultSet rs=ps.executeQuery();
			//6. ArrayList에 담기
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				String poster=rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^")); //망고플레이트 이미지가 5개씩 붙어있어서 맨앞에 있는 것 잘라오기
				vo.setPoster(poster);
				
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
	public int foodTotalPage()
	{
		int total=0;
		try
		{
			//1. 연결
			getConnection();
			//2. sql문장 제작
			//★★★★sql문장 수정하기 (전체페이지가 안 나옴)
			String sql="SELECT CEIL(COUNT(*)/20.0) FROM food_location";
			//3. 오라클 전송
			ps=conn.prepareStatement(sql);
			//4. 실행 후 결과값 가져오기
			ResultSet rs=ps.executeQuery();
			//5. 커서 위치를 실제 출력된 메모리위치로 변경
			rs.next();
			total=rs.getInt(1);
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return total;
	}
	//기능 - 맞집찾기 : LIKE문
	//기능 - 상세보기 : 주소 잘라서 출력
	public FoodVO foodDetailData(int fno)
	{
		FoodVO vo=new FoodVO();
		try
		{
			//1. 연결
			getConnection();
			//2. sql문장 제작
			String sql="SELECT fno,name,tel,score,type,time,parking,menu,poster "
						+"FROM (SELECT * FROM food_location) "
						+"WHERE fno="+fno;
			//3. 오라클 전송
			ps=conn.prepareStatement(sql);
			//4. 결과값 저장
			ResultSet rs=ps.executeQuery();
			//5. vo에 저장
			rs.next();	//저장된 데이터 읽기
			vo.setFno(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setTel(rs.getString(3));
			vo.setScore(rs.getDouble(4));
			vo.setType(rs.getString(5));
			vo.setTime(rs.getString(6));
			vo.setParking(rs.getString(7));
			vo.setMenu(rs.getString(8));
			vo.setPoster(rs.getString(9));
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return vo;
	}
	//-----------------------------------------------------
	public static void main(String[] args) {
		FoodDAO dao=new FoodDAO();
		Scanner scan=new Scanner(System.in);
		int totalpage=dao.foodTotalPage();
		System.out.println("1~"+totalpage+" 사이의 페이지 입력:");
		int page=scan.nextInt();
		
		ArrayList<FoodVO> list=dao.foodListData(page);
		
		System.out.println("----- 결과값 출력 -----");
		for(FoodVO vo:list)
		{
			System.out.println(vo.getFno()+" "+vo.getName());
		}
	}
}
