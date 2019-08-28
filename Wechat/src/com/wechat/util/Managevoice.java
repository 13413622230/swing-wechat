package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Label;
/*
 * 会话用户名字管理类
 */
public class Managevoice {
	private static HashMap<String, Label> voice = new HashMap<>();
	public static void addvoice (String friend_id,Label la){
		voice.put(friend_id, la);
	}
	public static Label getvoice (String friend_id){
		return voice.get(friend_id);
	}
}