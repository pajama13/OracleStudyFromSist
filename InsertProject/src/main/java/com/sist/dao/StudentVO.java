/*


문자형
 CHAR --> 고정메모리
 VARCHAR2(1~4000) --> 가변메모리 (입력된 글자수 만큼)
 CLOB(4기가) --> 가변메모리
 
숫자형
 NUMBER(4) --> int
 NUMBER(8,2) --> double
 
날짜형
 DATE --> java.util.Date
 
기타형
 BLOB/BFILE --> java.io.InputStream


*/
//오라클의 데이터 1줄(row단위) 저장!
package com.sist.dao;
//import java.util.*;
//import java.sql.*;
public class StudentVO {
	private int hakbun;
	private String name;
	private int kor,eng,math,total;
	private double avg;
	public int getHakbun() {
		return hakbun;
	}
	public void setHakbun(int hakbun) {
		this.hakbun = hakbun;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
}