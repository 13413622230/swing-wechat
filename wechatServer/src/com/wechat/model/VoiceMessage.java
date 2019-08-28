package com.wechat.model;

import java.io.Serializable;
import java.util.Date;
/*
 * 语音信息类
 */
public class VoiceMessage extends Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxTime;				//信息最大时间
	private String contenPath;			//音频内容路径
	private int time;					//音频时间
	
	

	public VoiceMessage(User sender, User sendee, String string, int maxTime, String contenPath, int time) {
		super(sender, sendee, string);
		this.maxTime = maxTime;
		this.contenPath = contenPath;
		this.time = time;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public String getContenPath() {
		return contenPath;
	}

	public void setContenPath(String contenPath) {
		this.contenPath = contenPath;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	
}
