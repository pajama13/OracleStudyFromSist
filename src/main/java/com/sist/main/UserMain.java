package com.sist.main;
import java.util.*;
import com.sist.dao.*;
public class UserMain {

	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		StudentVO vo=new StudentVO();
		System.out.println("이름 입력:");
		vo.setName(scan.next());
		System.out.println("국어 입력:");
		vo.setKor(scan.nextInt());
		System.out.println("영어 입력:");
		vo.setEng(scan.nextInt());
		System.out.println("수학 입력:");
		vo.setMath(scan.nextInt());
		
		StudentDAO dao=new StudentDAO();
		dao.studentInsert(vo);
		System.out.println("저장 완료!");
		
		ArrayList<StudentVO> list=dao.stuArrayList();
		for(StudentVO svo:list)
		{
			System.out.println(svo.getHakbun()+" "
					+svo.getName()+" "
					+svo.getKor()+" "
					+svo.getEng()+" "
					+svo.getMath()+" "
					+svo.getTotal()+" "
					+svo.getAvg());
		}
	}

}
