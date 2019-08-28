package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Composite;

public class ManageGroupSession {
	private static HashMap<String, Composite> GroupSession = new HashMap<>();
	public static void addGroupSession(String friend_id,Composite com){
		GroupSession.put(friend_id, com);
	}
	public static Composite getGroupSession(String friend_id){
		return GroupSession.get(friend_id);
	}
}
