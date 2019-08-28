package com.wechat.model;

import java.io.Serializable;
import java.util.Date;

public class FileMessage extends Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;

	

	public FileMessage(User sender, User sendee, String sendTime, String path) {
		super(sender, sendee, sendTime);
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
