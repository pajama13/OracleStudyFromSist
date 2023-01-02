/*
	1/1
	
	푸드 카테고리 
    fcno NUMBER,
    title VARCHAR2(300) CONSTRAINT fc_title_nn_4 NOT NULL,
    subtitle VARCHAR2(1000) CONSTRAINT fc_subtitle_nn_4 NOT NULL,
    image VARCHAR2(260) CONSTRAINT fc_image_nn_4 NOT NULL,
    link VARCHAR2(100) CONSTRAINT fc_link_nn_4 NOT NULL,


*/
package com.sist.dao;

public class CategoryVO {
	private int fcno;
	private String title, subtitle, image, link;
	public int getFcno() {
		return fcno;
	}
	public void setFcno(int fcno) {
		this.fcno = fcno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
