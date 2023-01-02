/*

FNO     NOT NULL NUMBER        
NAME    NOT NULL VARCHAR2(100) 
IMAGE            VARCHAR2(500) 
CONTENT          CLOB          
PERIOD           VARCHAR2(50)  
WEB              VARCHAR2(500) 
ADDR    NOT NULL VARCHAR2(300) 
HIT              NUMBER  

*/
package com.sist.dao;

public class FestivalVO {
	private int fno,hit;
	private String name,image,content,period,web,addr;
	public int getFno() {
		return fno;
	}
	public void setFno(int fno) {
		this.fno = fno;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
}
