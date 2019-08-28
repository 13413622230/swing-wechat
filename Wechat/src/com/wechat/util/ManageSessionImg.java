package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Label;
/*
 * 会话头像管理类
 */
public class ManageSessionImg {
	private static HashMap<String, Label> sessionsImg = new HashMap<>();
	public static void addSessionsImg(String friend_id,Label la){
		sessionsImg.put(friend_id, la);
	}
	public static Label getSessionsImg(String friend_id){
		return sessionsImg.get(friend_id);
	}
}
