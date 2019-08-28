package com.wechat.model;

import java.io.Serializable;
import java.util.Date;
/*
 * 企业公告信息类
 */
public class Bulletin implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String user_id;
	private String title;
	private String content;
	private String time;
	private int like;
	public Bulletin(int id, String user_id, String title, String content, String time, int like) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.title = title;
		this.content = content;
		this.time = time;
		this.like = like;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
}
