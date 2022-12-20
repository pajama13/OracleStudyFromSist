package com.sist.client;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
/*

클래스의 종류
 -추상클래스 : 미완성 클래스 --> new로 메모리 할당 불가 --> 사용자가 상속 받아 처리함
  	public abstract class Name
 	{
 	   인스턴스 변수 설정
 	   생성자
 	   구현된 메소드
 	   추상메소드(선언부만 있음)
 	}
 -인터페이스 : 추상클래스의 일종(미완성 클래스), 단 다중상속 가능 --> 클래스 여러 개를 묶어 1개의 이름으로 사용
    		호환성이 좋음 (ex java.sql --> 오라클, MySQL 등)
    public interface Name
    {
       //인터페이스는 앞에 키워드 생략돼있는 것 기억하기
       (public static final) 상수형 변수 설정 - 인스턴스변수는 설정 불가
       (public abstract) void 메소드() - 추상메소드
       (public) 메소드() - 구현된 메소드
    }
 -내부클래스
    멤버클래스 : 클래스의 변수, 메소드를 쉽게 공유할 때 --> static도 가능하나 모든 것을 그렇게 처리할 수 없을 때 
    class A
    {
       공유할 변수
       공유할 메소드
       class B // 내부클래스인 B가 A클래스의 변수와 메소드 사용 가능함 (private인 경우에도)
       {
       }
    }
    
    익명의 클래스 : 상속 없이 오버라이딩 하는 경우 익명의 클래스 이용
 				***면접 질문 : 오버라이딩은 꼭 상속이 있어야 가능하다? No, 익명의 클래스는 상속 없이 오버라이딩 가능
 	class A
 	{ 
 		B b=new B();
 		{
 			public void method(){}
 		}
 	}
 	class B
 	{  
 		public void method(){}
 	}
 -종단클래스 --> java.lang의 클래스들은 대부분 종단클래스, 상속(재정의) 불가하여 그대로 사용해야 함
 	public final class Name
 	{
 	}

*/
public class WaitRoom extends JPanel{
	JTable table1,table2;
	DefaultTableModel model1, model2;
	JTextArea ta;
	JTextField tf;
	JButton b1,b2,b3,b4,b5,b6;
	JLabel la1,la2;
	public WaitRoom()
	{
		//생성자로 초기화
		//익명의 클래스 : 언제 등장? 상속 없이 오버라이딩할 때***
		String[] col1={"방이름","상태","인원"};
		String[][] row1=new String[0][3]; //=1줄에 데이터를 3개씩 집어넣음 --> 2차원 배열
		model1=new DefaultTableModel(row1,col1)
		{
			
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
			
			
		};
		table1=new JTable(model1);
		JScrollPane js1=new JScrollPane(table1);
		
		String[] col2={"아이디","이름","성별"};
		String[][] row2=new String[0][3]; //=1줄에 데이터를 3개씩 집어넣음 --> 2차원 배열
		model2=new DefaultTableModel(row2,col2) //익명의 클래스 : 상속 없이 오버라이딩
		{
			
			@Override //어노테이션 : 편집 방지
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		table2=new JTable(model2);
		JScrollPane js2=new JScrollPane(table2);
		
		ta=new JTextArea();
		ta.setEditable(false); //편집불가 설정 : 서버에서 온 값만 출력하게 하기 위함
		JScrollPane js3=new JScrollPane(ta);
		
		tf=new JTextField();
		
		la1=new JLabel("개설된 방정보");
		la2=new JLabel("접속한 사용자 정보");
		
		b1=new JButton("방만들기");
		b2=new JButton("방들어가기");
		b3=new JButton("쪽지보내기");
		b4=new JButton("정보보기");
		b5=new JButton("1:1채팅");
		b6=new JButton("종료하기");
		
		//배치 - 사용자 정의 배치
		setLayout(null); //CSS와 같은 것
		//x,y,width,height
		la1.setBounds(10,15,120,30);
		js1.setBounds(10,50,750,450);
		
		js3.setBounds(10,510,750,250);
		tf.setBounds(10,765,750,30);
		
		la2.setBounds(770,15,350,30);
		js2.setBounds(770,50,350,350);
		
		JPanel p=new JPanel();
		p.add(b1); p.add(b2); p.add(b3); p.add(b4); p.add(b5); p.add(b6);
		p.setLayout(new GridLayout(3,2,5,5)); //한줄에 몇개씩,간격,간격
		p.setBounds(770,410,350,100);
		
		add(la1);add(la2);
		add(js1);add(js2);add(js3);
		add(tf);add(p);
	}
	
}
