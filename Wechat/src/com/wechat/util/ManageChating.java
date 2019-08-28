package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Composite;

public class ManageChating {
	private static HashMap<String, Composite> Chatings = new HashMap<>();
	public static void addChating(String friend_id,Composite com){
		Chatings.put(friend_id, com);
	}
	public static Composite getChating(String friend_id){
		return Chatings.get(friend_id);
	}
}