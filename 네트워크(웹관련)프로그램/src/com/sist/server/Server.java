package com.sist.server;
import java.util.*;
import java.io.*;
import java.net.*;
public class Server implements Runnable{
	//클라이언트 정보 저장공간 만들기 --> 웹에서는 HttpSession에 저장됨(실행중일 때만 저장됨)
	private Vector<Client> waitVc=new Vector<Client>();
	/*
		복습 필수 : 컬렉션, 제네릭스 
	*/
	//ServerSocket 생성 (서버 구동 위함)
	private ServerSocket ss;
	//클라이언트와 연결 번호 : PORT (0~65535)
	private final int PORT=3355;
	//서버 구동 시작점
	//시작과 동시에 구동돼야 함
	/*
	 	생성자 역할
	 	-초기화 목적
	 	-시작과 동시에 처리해야되는 기능을 수행해줌 (ex 자동로그인, db연결, 화면ui 등)
	 */
	public Server()
	{
		try
		{
			ss=new ServerSocket(PORT); //bind():소켓생성, listen():대기상태 --> 자동 호출됨
			System.out.println("Server Start...");
		}catch(Exception ex){}
	}
	public static void main(String[] args) {
		Server server=new Server();
		new Thread(server).start();
	}
	//접속 시 정보를 받아 저장함
	@Override
	public void run() {
		try
		{
			while(true)
			{
				Socket s=ss.accept(); //클라이언트가 접속 시 정보를 얻어옴 : Socket(IP, PORT)
				//발신자 정보 받으면, 쓰레드에 넘겨줌
				//쓰레드 각각 1대1로 통신 시작 : 멀티서버 <--> 에코서버
				Client client=new Client(s); //쓰레드에 접속자 정보를 넘겨줌
				client.start(); //통신 시작
			}
		}catch(Exception ex) {}
	}
	
	//실제 클라이언트와의 통신 --> 쓰레드 이용 : 1대1 통신
	class Client extends Thread
	{
		private String id,name,sex;
		private OutputStream out; //통신도구 - 결과값 보내기
		private BufferedReader in; //통신도구 - 요청받기
		private Socket s; //연결 기계 역할 --> 접속한 클라이언트 정보를 받음 (s에)
		//초기화
		public Client(Socket s)
		{
			try
			{
				this.s=s;
				in=new BufferedReader(new InputStreamReader(s.getInputStream()));
				out=s.getOutputStream();
			}catch(Exception ex){}
		}
		//실제 통신 --> 쓰레드
		public void run()
		{
			try
			{
				while(true)
				{
					//사용자 요청정보 읽기
					String msg=in.readLine();
					//로그인 정보 자르기 : 100|id|name|sex
					StringTokenizer st=new StringTokenizer(msg,"|");
					int protocol=Integer.parseInt(st.nextToken()); //구분자로 잘라 가장 앞 문자열 숫자를 정수형으로 변환
					switch(protocol)
					{
						case 100: //로그인이면
						{
							id=st.nextToken();
							name=st.nextToken();
							sex=st.nextToken();
							
							//로직 처리 - 알고리즘을 외워야 함
							// 1. 전체 접속자에게 로그인한 유저의 정보를 전송
							messageAll(100+"|"+id+"|"+name+"|"+sex);
							// 2. waitVc에 저장
							waitVc.add(this);
							// 2-2. 로그인창 --> 대기실창 변경
							messageTo(110+"|"+id);
							// 3. 로그인 유저에게 접속자 정보를 전체 전송
							for(Client user:waitVc)
							{
								messageTo(100+"|"+user.id+"|"+user.name+"|"+user.sex);
							}
						}
						break;
						case 900:
						{
							String mid=st.nextToken();
							messageAll(900+"|"+id); //테이블에서 id를 제거 (남아 있는 사람 처리)
							//제거
							for(int i=0; i<waitVc.size();i++)
							{
								Client c=waitVc.get(i);
								if(c.id.equals(mid))
								{
									messageTo(990+"|"); //윈도우를 종료한다 (나가는 사람 처리)
									waitVc.remove(i);  //저장된 아이디 삭제
									in.close();
									out.close(); //통신 정지하기
									break; //
								}
							}
						}
						break;
						case 300:
						{
							messageAll(300+"|["+name+"]"+st.nextToken());
						}
						break;
					}
				}
			}catch(Exception ex) {}
		}
		//전체적으로 전송
		public synchronized void messageAll(String msg)
		{
			try
			{
				for(Client client :waitVc) //전체 사용자에게
				{
					client.messageTo(msg);  //대기실이나 채팅에서 사용
				}
			}catch(Exception ex) {}
		}
		//개인적으로 전송
		public synchronized void messageTo(String msg)
		{
			try
			{
				out.write((msg+"\n").getBytes());
			}catch(Exception ex) {}
		}
	}

}
