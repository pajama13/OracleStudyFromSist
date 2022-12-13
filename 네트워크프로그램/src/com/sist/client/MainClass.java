package com.sist.client;
import java.net.*;
public class MainClass {

	public static void main(String[] args) {
		try
		{
			//서버 연결 - 서버클래스 실행 후, 여기 메인클래스 실행시킬 때마다 포트번호가 바뀜
			// 서버는 클라이언트 정보를, 클라이언트는 서버정보(서버 IP)를 갖고 있어야 함
			Socket s=
					new Socket("localhost",3355); //IP≒전화번호
		}catch(Exception ex) {}
	}
}
