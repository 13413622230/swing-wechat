package com.wechat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
/*
 * 朋友圈类
 */
public class Moment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;							//朋友圈id
	private String user_id;					//发布人id
	private String name;						//发布人昵称
	private String content;					//文字内容
	private String time;							//发布时间
	private String img;						//图片内容
	private String location;					//发布地点
	private int like;							//点赞人数
	private ArrayList<Comment> conments;		//评论
	public Moment(String id, String user_id, String name, String content, String time, String img, String location,
			int like, ArrayList<Comment> conments) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.name = name;
		this.content = content;
		this.time = time;
		this.img = img;
		this.location = location;
		this.like = like;
		this.conments = conments;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public ArrayList<Comment> getConments() {
		return conments;
	}
	public void setConments(ArrayList<Comment> conments) {
		this.conments = conments;
	}
	
	
}
