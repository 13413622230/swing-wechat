package com.wechat.model;

import java.io.Serializable;
import java.util.ArrayList;
/*
 * 企业用户类
 */
public class EnterpriseUser extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String introduction;				//功能介绍**
	private String subject;					//账号主体
	private String telephone;					//客户电话**
	private String scopeOperation;				//经营范围
	private ArrayList<Bulletin> bulletin;		//公告	**
	private ArrayList<OrdinaryUser> focusUsers;		//关注用户**
	private String mail;						//邮箱**
	private int state;
	public EnterpriseUser(String id, String pw, String headimg, String name, String introduction, String subject,
			String telephone, String scopeOperation, ArrayList<Bulletin> bulletin, ArrayList<OrdinaryUser> focusUsers,
			String mail) {
		super(id, pw, headimg, name);
		this.introduction = introduction;
		this.subject = subject;
		this.telephone = telephone;
		this.scopeOperation = scopeOperation;
		this.bulletin = bulletin;
		this.focusUsers = focusUsers;
		this.mail = mail;
	}
	
	public EnterpriseUser(String id, String pw, String headimg, String name, String introduction, String telephone,
			String mail, int state) {
		super(id, pw, headimg, name);
		this.introduction = introduction;
		this.telephone = telephone;
		this.mail = mail;
		this.state = state;
	}



	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getScopeOperation() {
		return scopeOperation;
	}
	public void setScopeOperation(String scopeOperation) {
		this.scopeOperation = scopeOperation;
	}
	public ArrayList<Bulletin> getBulletin() {
		return bulletin;
	}
	public void setBulletin(ArrayList<Bulletin> bulletin) {
		this.bulletin = bulletin;
	}
	public ArrayList<OrdinaryUser> getFocusUsers() {
		return focusUsers;
	}
	public void setFocusUsers(ArrayList<OrdinaryUser> focusUsers) {
		this.focusUsers = focusUsers;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
