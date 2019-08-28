package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Composite;

public class ManageSession {
	private static HashMap<String, Composite> sessions = new HashMap<>();
	public static void addSession(String friend_id,Composite com){
		sessions.put(friend_id, com);
	}
	public static Composite getSession(String friend_id){
		return sessions.get(friend_id);
	}
}
