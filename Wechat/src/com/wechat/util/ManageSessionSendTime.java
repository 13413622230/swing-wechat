package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Label;
/*
 * �Ự�û����ֹ�����
 */
public class ManageSessionSendTime {
	private static HashMap<String, Label> SessionSendTime = new HashMap<>();
	public static void addSessionSendTime (String friend_id,Label la){
		SessionSendTime.put(friend_id, la);
	}
	public static Label getSessionSendTime (String friend_id){
		return SessionSendTime.get(friend_id);
	}
}