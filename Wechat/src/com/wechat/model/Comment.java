package com.wechat.model;

import java.io.Serializable;
import java.util.Date;
/*
 * 评论类
 */
public class Comment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;						//评论id
	private String content;				//评论内容
	private String user_id;				//评论人id
	private String name;					//评论人昵称
	private String time;						//评论时间
	public Comment(String id, String content, String user_id, String name, String time) {
		super();
		this.id = id;
		this.content = content;
		this.user_id = user_id;
		this.name = name;
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
