package com.sist.client;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import com.sist.dao.*;

//네트워크 관련 라이브러리
import java.net.*; //네트워크 연결 관련 - 소켓
import java.io.*;  //서버와 입출력 통신 관련 - BufferedReader(서버의 결과값 읽기) / OutputStream (서버에 요청 보내기)
/*

오라클 서버 : OutputStream, BufferedReader --> PreparedStatement 클래스 사용
웹 서버 : OutputStream    --> HttpServeletRequest 클래스
		BufferedReader  --> HttpServeletResponse 클래스

*/
import java.util.*;  
public class ClientMain extends JFrame implements ActionListener,Runnable{
		CardLayout card=new CardLayout();
		Login login=new Login();
		WaitRoom wr=new WaitRoom();
		//MemberDAO dao=new MemberDAO();
		private Socket s; //서버 연결용
		private OutputStream out; //out.write()서버에 요청 보내기 --> 자체 처리 필요
		private BufferedReader in; //in.readLine() 서버에서 보내준 값 읽기 --> 쓰레드 이용 (입출력 동시에 불가하므로) :
		//단어 구분 --> StringTokenizer()
		//ex  100|id|name|sex (구분자 이용) --> 오라클 구분자 : SELECT ... / 웹 구분자 : 파일명
		private String id;
	public ClientMain()
	{
		setLayout(card);
		
		add("LOGIN",login);
		add("WR",wr);
		setSize(1150,850);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE); //X버튼 클릭 시 메모리 해제
		login.b1.addActionListener(this); //로그인
		wr.b6.addActionListener(this); //나가기
		wr.tf.addActionListener(this); //채팅 --> 300번
	}
	public static void main(String[] args) {
		try
		{
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel"); //
		}catch(Exception ex) {}
	new ClientMain();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==login.b1) //b1 : 로그인버튼
		{
			String id=login.tf1.getText();
			if(id.trim().length()<1)
			{
				JOptionPane.showMessageDialog(this, "아이디를 입력하세요");
				login.tf1.requestFocus();
				return;
			}
			String name=login.tf2.getText();
			if(name.trim().length()<1)
			{
				JOptionPane.showMessageDialog(this, "이름을 입력하세요");
				login.tf2.requestFocus();
				return;
			}
			
			String sex="";
			if(login.rb1.isSelected()) //남자 라디오버튼 선택됐다면
			{
				sex="남자";
			}
			else
			{
				sex="여자";
			}
			//클라이언트 : 입력된 데이터를 서버로 전송 --> 서버 : 데이터 받아서 저장 후, 통신 시 사용 가능하게 만듬
					
			connection(id,name,sex);
		}
		//나가기
		else if(e.getSource()==wr.b6)
		{
			try
			{
				out.write((900+"|"+id+"\n").getBytes());
			}catch(Exception ex) {}
		}
		//채팅 요청
		else if(e.getSource()==wr.tf)
		{
			String msg=wr.tf.getText(); //사용자가 입력한 값
			if(msg.trim().length()<1) //입력이 안 됐을 때
				return; //메소드 종료
			try
			{
				//채팅 문자열 전송 --> 서버로 전송할 때 out.write()
				out.write((300+"|"+msg+"\n").getBytes());
			}catch(Exception ex) {}
			wr.tf.setText(""); //엔터 치고 입력란 공백으로
		}
	}
	//로그인 처리 메소드 : 로그인 버튼 누르면 서버와 연결됨
	public void connection(String id,String name,String sex)
	{
		//소켓(=전화)으로 서버에 연결
		//로그인 요청 : 사용자 정보 필요(id,name,sex) --> 서버에 결과값이 있으면 받아서 출력
		try
		{
			s=new Socket("localhost",3355); // localhost 또는 실제 IP
			//서버에서 보내준 데이터 읽기
			in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			//s = 서버의 요청 결과값을 서버 메모리에 저장
			//InputStreamReader : 필터스트림 : byte --> char 변환시켜주는 클래스
			//서버로 전송할 데이터 저장 공간
			out=s.getOutputStream();  //추상클래스
			/*
				new 이용하여 생성 : 일반클래스
				new 없이 생성 : 추상클래스 (불완전 클래스로 new로 메모리 할당 불가)
				
				*** 추상클래스 vs 인터페이스 차이
				--> 인터페이스 사용이 거의 대부분을 차지함, 스프링의 기본임
				--> 각각 언제 사용하는지 알아두기
			*/
			//로그인 요청
			out.write((100+"|"+id+"|"+name+"|"+sex+"\n").getBytes());
		}catch(Exception ex) {}
		//서버로부터 결과값 받아오기 : Thread는 보내기/읽기를 동시 수행이 가능하도록 함	
		new Thread(this).start();
	}
	//쓰레드 만들기 : 서버에서 보내준 데이터를 출력하는 역할
	@Override
	public void run() {
		try
		{
			while(true)
			{
				//서버에서 보내준 값 받기
				String msg=in.readLine();
				//100+"|"+id+"|"+name+"|"+sex+"|"+"\n"
				System.out.println("서버에서 보낸 값:"+msg);
				StringTokenizer st=new StringTokenizer(msg,"|"); 
				int protocol=Integer.parseInt(st.nextToken()); //1개 자르기
				//서버에서 클라이언트로 명령내림 : 기능별로 프로토콜번호를 지정함 : 100, 110...
				switch(protocol)
				{
					case 100: //로그인 처리
					{
						String[] data= {
							st.nextToken(), //id
							st.nextToken(), //name
							st.nextToken()  //sex
						};
						wr.model2.addRow(data);  //서버가 보내준 데이터를 화면에 출력
					}
					break;
					case 110: //화면 변경 처리 : 로그인창 --> 대기실창
					{
						id=st.nextToken(); //서버에서 보내준 id를 저장
						setTitle(id); //윈도우 제목 출력
						card.show(getContentPane(), "WR"); //화면 변경
					}
					break;
					case 990: //나가는 사람 윈도우 종료
					{
						dispose(); //메모리 해제
						System.exit(0); //프로그램 종료 --> 모든 명령은 서버에서 내림
					}
					break;
					case 900: //남아 있는 사람 처리
					{
						String mid=st.nextToken();
						String temp="";
						for(int i=0;i<wr.model2.getRowCount();i++)
						{
							temp=wr.model2.getValueAt(i, 0).toString();
							if(mid.equals(temp))
							{
								wr.model2.removeRow(i);
								break;
							}
						}
					}
					break;
					case 300: //채팅 문장열이 들어온 경우
					{
						wr.ta.append(st.nextToken()+"\n"); //서버에서 전송되면, 받아서 출력
					}
					break;
				}
			}
		}catch(Exception ex) {}
	}
}

//---------오라클 db 연동 부분---------------------------------------
//String result=dao.isLogin(id, pwd); //로그인 처리 요청
//if(result.equals("NOID"))
//{
//	JOptionPane.showMessageDialog(this, "아이디가 존재하지 않습니다");
//	login.tf1.setText(""); //아이디 없애기
//	login.tf2.setText("");
//	
//}
//else if(result.equals("NOPWD"))
//{
//	JOptionPane.showMessageDialog(this, "비밀번호가 존재하지 않습니다");
//	login.tf2.setText(""); 
//}
//else
//{
//	JOptionPane.showMessageDialog(this, result+"님 로그인되었습니다");
//}
//----------------------------------------------------------------
