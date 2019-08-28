package com.wechat.model;

import java.io.Serializable;

/*
 * 	�û���
 */
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;								//�˺�
	private String pw;								//����
	private String headimg;						//ͷ��
	private String name;							//�ǳ�
	public User(String id, String pw, String headimg, String name) {
		super();
		this.id = id;
		this.pw = pw;
		this.headimg = headimg;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
