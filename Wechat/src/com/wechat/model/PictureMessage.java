package com.wechat.model;

import java.io.Serializable;
import java.util.Date;
/*
 * ͼƬ��Ϣ�࣬�����
 */
public class PictureMessage extends Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imgPath;				//ͼƬ·��

	
	public PictureMessage(User sender, User sendee, String string, String imgPath) {
		super(sender, sendee, string);
		this.imgPath = imgPath;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
