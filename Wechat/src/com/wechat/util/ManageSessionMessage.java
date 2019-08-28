package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Label;
/*
 * 会话用户名字管理类
 */
public class ManageSessionMessage {
	private static HashMap<String, Label> sessionsMessage = new HashMap<>();
	public static void addSessionsMessage(String friend_id,Label la){
		sessionsMessage.put(friend_id, la);
	}
	public static Label getSessionsMessage(String friend_id){
		return sessionsMessage.get(friend_id);
	}
}