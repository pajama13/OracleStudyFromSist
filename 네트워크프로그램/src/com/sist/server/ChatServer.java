package com.sist.server;
import java.io.*;
import java.net.*;
import java.util.*;
/*

class ChatServer
{
	Client의 정보 저장 --> IP, PORT
	---------------------------- 접속 시마다 저장 (담당)
	----------------------------
	class Client extends Thread  --> 접속마다 통신을 담당하는 쓰레드가 필요
	{ 통신만 담당 }
	----------------------------
}

내부 클래스 : 쓰레드, 네트워크 --> 공유하는 데이터가 많기 때문에 멤버클래스 이용해서 공유
-멤버클래스
  class A
  {
     class B { A가 가진 모든 데이터를 공유받을 목적} //A안에 있으므로 A의 private변수에도 접근 가능
  }
-익명의 클래스 : 상속 없이 오버라이딩을 할 때 사용


*/

//접속 시 클라이언트 정보 저장 --> 교환 소켓
public class ChatServer implements Runnable {
	//클라이언트 정보 저장
	private Vector<Client> waitVc=new Vector<Client>();

	//서버 가동
	private ServerSocket ss; //교환 소켓 --> 접속 시에만 처리
	//서버는 PORT번호가 고정돼 있음, IP도 고정
	private final static int PORT=3355; // ≒전화선(연결번호)
	/* PORT 범위 : 0~65535
	   0~1023번 알려진 포트번호 : 사용금지
	    -FTP : 21
	    -SMTP : 25
	    -HTTP : 80
	    -TELNET : 23
	    p --> 프로토콜(약속)
	   1521 : 오라클 사용
	   3306 : MySQL
	   3000 : React 디폴트 포트
	   8080 : 스프링 디폴트 포트
	   
	   음성채팅 : 20000번대
	   화상채팅 : 30000번대
	   
	*/
	
	private ChatServer()
	{
		try
		{
			/* ★서버는 두번 실행하면 안 됨(한번만 실행 가능) --> 여러 번 실행 시 PORT 변경하여 사용
			
			   서버 종류
			     P2P : 클라이언트 프로그램 안에서 서버가 작동하는 것 (게임) - 방만든 사람이 서버 작동시킴 
			
			*/
			ss=new ServerSocket(PORT); //50명까지 접근 --> 인트라넷(사내에서 사용)
			//인원 100으로 늘리고 싶으면 : new ServerSocket(PORT,100)
			System.out.println("Server Start..."); //포트만 지정해주면 서버 구동됨
			// PORT 생성자 안 메소드 --> bind(IP,PORT) : 개통, listen() : 대기
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	public void run() //접속 시 정보를 저장
	{
		try
		{
			while(true)
			{
				Socket client=ss.accept();
				//클라이언트가 접속한 경우에만 호출 (비접속 시 대기상태) - 특수메소드
				//접속한 클라이언트의 정보(IP)를 가져옴
				//IP와 PORT를 가진 클래스 : Socket
				//System.out.println("접속한 클라이언트 IP:"+client.getInetAddress().getHostAddress());
				//System.out.println("접속한 클라이언트 PORT 확인"+client.getPort());
				//서버는 고정 PORT 이용, 클라이언트는 자동 PORT 이용 (중복 없음)
				Client c=new Client(client);
				waitVc.add(c); //저장
				c.start();
			}
		}catch(Exception ex){}
	}
	public static void main(String[] args) {
		ChatServer server=new ChatServer();
		new Thread(server).start(); //서버가 갖고 있는 run()호출하기 - start()안에 run() 있음
	}
	//통신 : 사용자 요청 받기, 사용자 요청 처리 후 응답하기 --> 통신 소켓 (접속마다 생성해야 됨 --> 접속자마다 따로 통신 수행)
	class Client extends Thread //내부클래스 --> ChatServer 변수에 접근 가능
	{
		private Socket s; //클라이언트 연결 소켓 (각자 담당하는 쓰레드가 클라이언트의 정보를 갖고 있음) --> 소켓 통해 IP확인
		private OutputStream out; //클라이언트로 값을 전송
		private BufferedReader in; //클라이언트로부터 요청값 받기
		
		public Client(Socket s)
		{
			try
			{
				this.s=s;
				in=new BufferedReader(new InputStreamReader(s.getInputStream()));
				//s.getInputStream : 클라이언트가 보낸 값을 읽겠음
				out=s.getOutputStream(); // 서버 메모리에 저장하면 해당 위치에서 클라이언트가 읽어감 : 신뢰성 높음 --> TCP
			}catch(Exception ex) {}
		}
		public void run()
		{
			try
			{
				while(true)
				{
					//클라이언트로부터 요청값 받기
					String msg=in.readLine(); //in : 클라이언트가 데이터를 저장해둔 메모리
					System.out.println("Client가 전송한 값:"+msg);
					//접속한 모든 클라이언트에게 값 보내기 (모두가 채팅내용 봐야 하니까!)
					for(Client c:waitVc)
					{
						c.out.write((msg+"\n").getBytes());
						//★만드시 \n를 추가해야 함, 그래야 위 readLine()으로 한줄씩 읽을 수 있음(??)
					}
				}
			}catch(Exception ex) {}
		}
		
	}

}
