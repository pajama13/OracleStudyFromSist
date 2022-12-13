package com.sist.main;

import java.lang.reflect.GenericArrayType;

class MyThread2 implements Runnable //쓰레드에 동작만 만들어줌
{

	@Override
	public void run() {
		try
		{
			for(int i=0;i<=10;i++)
			{
				System.out.println(Thread.currentThread()+":"+i);
				// currentThread : 현재 동작하고 있는 쓰레드 이름
				Thread.sleep(300);
			}
		}catch(Exception ex) {}
	}
	
}
public class MainClass_2{
	public static void main(String[] args) {
		MyThread2 t1=new MyThread2(); //쓰레드 아님, runnable는 쓰레드에 동작만 만들어 줌
		new Thread(t1).start(); //쓰레드임 t1이 갖고 있는 run()메소드 호출
	}
}
