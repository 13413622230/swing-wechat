package com.wechat.model;

import java.io.Serializable;
import java.util.ArrayList;
/*
 * 群聊类
 */
public class Group implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int groupId;							//群id
	private User groupOwner;						//群主
	private ArrayList<User> groupMembers;			//群成员
	private String groupName;						//群名称
	private String groupBulletin;					//群公告
	private ArrayList<Message> messages;			//群聊天记录	
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
