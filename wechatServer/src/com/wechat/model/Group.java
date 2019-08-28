package com.wechat.model;

import java.io.Serializable;
import java.util.ArrayList;
/*
 * Ⱥ����
 */
public class Group implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int groupId;							//Ⱥid
	private User groupOwner;						//Ⱥ��
	private ArrayList<User> groupMembers;			//Ⱥ��Ա
	private String groupName;						//Ⱥ����
	private String groupBulletin;					//Ⱥ����
	private ArrayList<Message> messages;			//Ⱥ�����¼	
	public Group(int groupId, User groupOwner, ArrayList<User> groupMembers, String groupName, String groupBulletin) {
		super();
		this.groupId = groupId;
		this.groupOwner = groupOwner;
		this.groupMembers = groupMembers;
		this.groupName = groupName;
		this.groupBulletin = groupBulletin;
	}

	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public User getGroupOwner() {
		return groupOwner;
	}
	public void setGroupOwner(User groupOwner) {
		this.groupOwner = groupOwner;
	}
	public ArrayList<User> getGroupMembers() {
		return groupMembers;
	}
	public void setGroupMembers(ArrayList<User> groupMembers) {
		this.groupMembers = groupMembers;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupBulletin() {
		return groupBulletin;
	}
	public void setGroupBulletin(String groupBulletin) {
		this.groupBulletin = groupBulletin;
	}
	public ArrayList<Message> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	
	
}
