package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Label;
/*
 * 会话用户名字管理类
 */
public class ManageGSessionMessage {
	private static HashMap<Integer, Label> GsessionsMessage = new HashMap<>();
	public static void addSessionsMessage(int friend_id,Label la){
		GsessionsMessage.put(friend_id, la);
	}
	public static Label getSessionsMessage(int friend_id){
		return GsessionsMessage.get(friend_id);
	}
}
