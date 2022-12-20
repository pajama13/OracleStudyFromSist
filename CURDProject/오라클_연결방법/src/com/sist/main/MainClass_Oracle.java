/*


데이터베이스 ≒ 폴더
테이블 ≒ 파일
컬럼 ≒ 클래스의 인스턴스변수
ROW, RECORD ≒ 객체 데이터

*/

package com.sist.main;
import java.sql.*;
public class MainClass_Oracle {

	public static void main(String[] args) {
		
		//드라이버 등록 (thin) --> ojdbc8.jar 
		try
		{
		
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//오라클 연결
			String url="jdbc:oracle:thin:@localhost:1521:XE"; //1521 : 포트번호, XE : 자동설정되는 데이터베이스명
			
			Connection conn=DriverManager.getConnection(url,"hr","happy");
			//SQL문장 전송
			String sql="SELECT ename,job,hiredate FROM emp"; //sql문장만 오라클로 전송
			PreparedStatement ps=conn.prepareStatement(sql); //실행하기
			ResultSet rs=ps.executeQuery(); //실행된 결과 받기
			
			//실행 결과값 가지고 오기
			while(rs.next()) // rs.next() : cursor위치 맨위로 이동
			{
				System.out.println(rs.getString(1)+" "
						+rs.getString(2)+" "
						+rs.getDate(3).toString());
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
	
	//	
	}

}
