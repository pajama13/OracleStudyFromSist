package com.sist.client;
import java.util.*; //StringTokenizer
import java.io.*; //OutputStream, BufferedReader
import java.net.*; //Socket
import javax.swing.*; //JFrame
import java.awt.*;
import java.awt.event.*;

public class ChatClient extends JFrame implements ActionListener,Runnable{
	JTextArea ta;
	JTextField tf;
	JButton b1,b2;
	private String name;
	//네트워크 관련 라이브러리
	private Socket s; //연결 기기
	private OutputStream out; //서버를 값 전송할 때
	private BufferedReader in; //서버로부터 값 읽기
	//입출력 동시처리를 위해 쓰레드 사용
	public ChatClient()
	{
		ta=new JTextArea();
		JScrollPane js=new JScrollPane(ta);
		ta.setEditable(false);
		tf=new JTextField(30); //유저의 입력란
		tf.setEnabled(false);
		b1=new JButton("접속");
		b2=new JButton("종료"); //종료 버튼 필수
		
		JPanel p=new JPanel();
		p.add(tf);
		p.add(b1);
		p.add(b2);
		
		add("Center",js);
		add("South",p);
		setSize(520,600);
		setVisible(true);
		
		b1.addActionListener(this);
		//클릭 시 처리
		b2.addActionListener(this); //CallBack --> 시스템에서 자동호출 (자바스크립트는 콜백함수 중심)
		//CallBack 대표 메소드 : main() 메소드
		//button --> 클릭, mouseClick() 방법이 여러 개여서 지정해주기
		tf.addActionListener(this); //enter 쳤을 때 입력란의 내용 전송
	}
	public static void main(String[] args) {
		new ChatClient();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b1)
		{
			name=JOptionPane.showInputDialog("이름 입력:");
			//서버연결
			try
			{
				s=new Socket("localhost",3355); //연결 ≒서버에 전화 걸기, Socket()안에 다른 IP넣으면 거기에 연결되는 것
				in=new BufferedReader(new InputStreamReader(s.getInputStream())); //서버로부터 값을 받는다
				out=s.getOutputStream(); //서버로 값을 보내기
			}catch(Exception ex) {}
			b1.setEnabled(false); //접속 완료되면 접속버튼 비활성화 (접속 두번 되면 안 되니까) 
			tf.setEnabled(true); //채팅창 입력란 활성화
			tf.requestFocus();
		}
		if(e.getSource()==b2)
		{
			dispose(); //윈도우 메모리 회수(가비지컬렉션) : 콘솔창 옆 빨간네모 자동으로 꺼짐
			System.exit(0); //프로그램 종료
		}
		if(e.getSource()==tf)
		{
			//입력한 문자 읽기
			String msg=tf.getText();
			if(msg.trim().length()<1) //입력이 안 됐다면
				return;
			//엔터치면 입력값을 서버로 전송
			try
			{
				out.write(("["+name+"]"+msg+"\n").getBytes());
				//서버의 out --> 서버로 전송
			}catch(Exception ex) {}
			//ta.append(msg+"\n");
			new Thread(this).start();
			tf.setText(""); //입력란을 공백으로 만들기 (입력값 전송 후)
		
		}
	}
	//서버에서 보내준 데이터 출력해주는 역할
	@Override
	public void run() {
		try
		{
			while(true)
			{
				String msg=in.readLine(); //서버에서 보내준 데이터 받기
				ta.append(msg+"\n");
			}
		}catch(Exception ex) {}
	}

}
