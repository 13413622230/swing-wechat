package com.wechat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
/*
 * 	��ͨ�û���
 */
public class OrdinaryUser extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;						//����ǩ��
	private String sex;							//�Ա�
	private ArrayList<Moment> monents;				//����Ȧ
	private ArrayList<OrdinaryUser> friends;				//�����б�
	private int state;								//��½״̬
	private ArrayList<Moment> allmonents;			//ȫ������Ȧ
	private ArrayList<EnterpriseUser> focus;					//���ĺ�
	private String mail;							//����
	private ArrayList<HistorySession> session;		//�û��Ự��¼
	private String region;							//�û�����
	private ArrayList<Group> groups;				//���Ⱥ��
	private HashMap<String, ArrayList<Message>> fmessages;
	public OrdinaryUser(String id, String pw, String headimg, String name, String message, String sex,
			ArrayList<Moment> monents, ArrayList<OrdinaryUser> friends, int state, ArrayList<Moment> allmonents,
			ArrayList<EnterpriseUser> focus, String mail) {
		super(id, pw, headimg, name);
		this.message = message;
		this.sex = sex;
		this.monents = monents;
		this.friends = friends;
		this.state = state;
		this.allmonents = allmonents;
		this.focus = focus;
		this.mail = mail;
	}
	
	public OrdinaryUser(String id, String pw, String headimg, String name, String message, String sex, int state,
			String mail) {
		super(id, pw, headimg, name);
		this.message = message;
		this.sex = sex;
		this.state = state;
		this.mail = mail;
	}
	
	public OrdinaryUser(String id, String pw, String headimg, String name, String sex, String region) {
		super(id, pw, headimg, name);
		this.sex = sex;
		this.region = region;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public ArrayList<Moment> getMonents() {
		return monents;
	}
	public void setMonents(ArrayList<Moment> monents) {
		this.monents = monents;
	}
	public ArrayList<OrdinaryUser> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList<OrdinaryUser> friends) {
		this.friends = friends;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public ArrayList<Moment> getAllmonents() {
		return allmonents;
	}
	public void setAllmonents(ArrayList<Moment> allmonents) {
		this.allmonents = allmonents;
	}
	public ArrayList<EnterpriseUser> getFocus() {
		return focus;
	}
	public void setFocus(ArrayList<EnterpriseUser> focus) {
		this.focus = focus;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public ArrayList<HistorySession> getSession() {
		return session;
	}
	public void setSession(ArrayList<HistorySession> session) {
		this.session = session;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public HashMap<String, ArrayList<Message>> getFmessages() {
		return fmessages;
	}

	public void setFmessages(HashMap<String, ArrayList<Message>> fmessages) {
		this.fmessages = fmessages;
	}
	
	
}
