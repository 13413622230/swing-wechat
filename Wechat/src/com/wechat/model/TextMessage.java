package com.wechat.model;

import java.io.Serializable;
import java.util.Date;
/*
 * 文字信息类
 */
public class TextMessage extends Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;				//信息内容

	

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
