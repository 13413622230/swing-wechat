package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Label;
/*
 * �Ự�û����ֹ�����
 */
public class ManageGSessionName {
	private static HashMap<Integer, Label> GsessionsName = new HashMap<>();
	public static void addSessionsName(int friend_id,Label la){
		GsessionsName.put(friend_id, la);
	}
	public static Label getSessionsName(int friend_id){
		return GsessionsName.get(friend_id);
	}
}