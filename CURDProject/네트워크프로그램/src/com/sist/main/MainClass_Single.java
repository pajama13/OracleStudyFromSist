package com.sist.main;

public class MainClass_Single {

	public static void main(String[] args) {
		long start=System.currentTimeMillis();
		for(int i=0;i<100;i++)
		{
			System.out.print("★");
		}
		long end=System.currentTimeMillis();
		System.out.println("소요시간:"+(end-start));
		
		for(int i=0;i<100;i++)
		{
			System.out.print("☆");
		}
		end=System.currentTimeMillis();
		System.out.println("소요시간:"+(end-start));
	}

}
