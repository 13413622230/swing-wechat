package com.wechat.model;

import java.io.Serializable;


/*
 * ��Ϣ��
 */
public class Message implements Serializable,MessageType{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private User sender;				//������
	private User sendee;				//������
	private String sendTime;				//����ʱ��
	private int type;					//��Ϣ����
	private String content;			//��Ϣ����
	
	public Message(User sender, User sendee, String sendTime) {
		super();
		this.sender = sender;
		this.sendee = sendee;
		this.sendTime = sendTime;
	}
	
	public Message(User sender, User sendee, String sendTime, int type, String content) {
		super();
		this.sender = sender;
		this.sendee = sendee;
		this.sendTime = sendTime;
		this.type = type;
		this.content = content;
	}

	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getSendee() {
		return sendee;
	}
	public void setSendee(User sendee) {
		this.sendee = sendee;
	}
	
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
