package com.wechat.model;

import java.io.Serializable;
import java.util.Date;
/*
 * ������Ϣ��
 */
public class TextMessage extends Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;				//��Ϣ����

	

	public TextMessage(User sender, User sendee, String string, String content) {
		super(sender, sendee, string);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
