package com.sist.dao;
//변수는 메소드 통해서 기능 수행 : 읽기/쓰기 getter/setter --> 캡슐화 코딩
//숫자 5개가 1개의 우편번호
public class ZipcodeVO {
	private String zipcode,sido,gugun,dong,bunji;
	private String address;
	
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getGugun() {
		return gugun;
	}
	public void setGugun(String gugun) {
		this.gugun = gugun;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getBunji() {
		return bunji;
	}
	public void setBunji(String bunji) {
		this.bunji = bunji;
	}
	public String getAddress() {
		return sido+" "+gugun+" "+dong+" "+bunji; //return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
