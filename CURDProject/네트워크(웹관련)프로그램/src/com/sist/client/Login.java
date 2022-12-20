package com.sist.client;
import javax.swing.*;
import java.awt.*;

public class Login extends JPanel {
	JTextField tf1,tf2; //포함클래스 (상속과 달리 포함은 여러 개 할 수 있음)
	JRadioButton rb1,rb2;
	JButton b1,b2;
	JLabel la1,la2,la3;
	public Login()
	{
		//생성자로 초기화 --> 메모리 할당
		la1=new JLabel("ID");
		la2=new JLabel("비번");
		la3=new JLabel("성별");
		tf1=new JTextField(); //ID 입력창
		tf2=new JTextField(); //비번 입력창
		rb1=new JRadioButton("남자");
		rb2=new JRadioButton("여자");
		ButtonGroup bg=new ButtonGroup(); //라디오버튼은 그룹으로 묶어줘야 함 (그룹 안 돼있으면 모두 선택돼버림)
		bg.add(rb1); bg.add(rb2);
		rb1.setSelected(true);
		b1=new JButton("로그인");
		b2=new JButton("취소");
		
		//자바에서 배치 : Layout / 웹에서 배치 : CSS
		setLayout(null); //사용자 정의, 직접 배치
		la1.setBounds(10,15,50,30); //ID
		tf1.setBounds(65,15,150,30); //ID입력창
		
		la2.setBounds(10,50,50,30);
		tf2.setBounds(65,50,150,30);
		
		la3.setBounds(10,85,50,30);
		rb1.setBounds(65,85,70,30);
		rb2.setBounds(140,85,70,30);
		
		add(la1);add(tf1);
		add(la2);add(tf2);
		add(la3);add(rb1);add(rb2);
		
		JPanel p=new JPanel();  //정렬 위해 묶어서 한번에 배치 --> JPanel
		p.add(b1);
		p.add(b2);
		p.setBounds(10,125,205,35);
		add(p);
		
	}
}
