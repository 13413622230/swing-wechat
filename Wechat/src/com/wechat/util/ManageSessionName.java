package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Label;
/*
 * �Ự�û����ֹ�����
 */
public class ManageSessionName {
	private static HashMap<String, Label> sessionsName = new HashMap<>();
	public static void addSessionsName(String friend_id,Label la){
		sessionsName.put(friend_id, la);
	}
	public static Label getSessionsName(String friend_id){
		return sessionsName.get(friend_id);
	}
}
