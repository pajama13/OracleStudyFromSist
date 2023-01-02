/*

TNO     NOT NULL NUMBER        
TCNO             NUMBER        
NAME    NOT NULL VARCHAR2(100) 
IMAGE            VARCHAR2(500) 
CONTENT          CLOB          
ADDR    NOT NULL VARCHAR2(300) 
HIT              NUMBER        
JJIM             NUMBER        
TLIKE            NUMBER  

*/
package com.sist.dao;

public class TripVO {
	private int tno, tcno, hit, jjim, tlike;
	private String name, image, content, addr;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTno() {
		return tno;
	}
	public void setTno(int tno) {
		this.tno = tno;
	}
	public int getTcno() {
		return tcno;
	}
	public void setTcno(int tcno) {
		this.tcno = tcno;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getJjim() {
		return jjim;
	}
	public void setJjim(int jjim) {
		this.jjim = jjim;
	}
	public int getTlike() {
		return tlike;
	}
	public void setTlike(int tlike) {
		this.tlike = tlike;
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
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
}
