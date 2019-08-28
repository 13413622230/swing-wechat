package com.wechat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
/*
 * ����Ȧ��
 */
public class Moment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;							//����Ȧid
	private String user_id;					//������id
	private String name;						//�������ǳ�
	private String content;					//��������
	private String time;							//����ʱ��
	private String img;						//ͼƬ����
	private String location;					//�����ص�
	private int like;							//��������
	private ArrayList<Comment> conments;		//����
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
