package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Label;
/*
 * 会话用户名字管理类
 */
public class ManageGSessionSendTime {
	private static HashMap<Integer, Label> GSessionSendTime = new HashMap<>();
	public static void addSessionSendTime (int friend_id,Label la){
		GSessionSendTime.put(friend_id, la);
	}
	public static Label getSessionSendTime (int friend_id){
		return GSessionSendTime.get(friend_id);
	}
}