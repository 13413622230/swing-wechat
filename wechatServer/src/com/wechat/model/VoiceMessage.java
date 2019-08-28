package com.wechat.model;

import java.io.Serializable;
import java.util.Date;
/*
 * ������Ϣ��
 */
public class VoiceMessage extends Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxTime;				//��Ϣ���ʱ��
	private String contenPath;			//��Ƶ����·��
	private int time;					//��Ƶʱ��
	
	

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
