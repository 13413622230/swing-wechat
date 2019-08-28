package com.wechat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
/*
 * 历史会话类
 */
public class HistorySession implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;								//会话id
	private User friend;							//好友
	private ArrayList<Message> messages;			//信息
	private String time;							//会话创建时间
	public HistorySession(User friend, ArrayList<Message> messages, String time) {
		super();
		this.friend = friend;
		this.messages = messages;
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getFriend() {
		return friend;
	}
	public void setFriend(User friend) {
		this.friend = friend;
	}
	public ArrayList<Message> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
