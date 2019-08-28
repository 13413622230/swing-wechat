package com.wechat.client;

import java.util.HashMap;

import view.Index;

public class ManageWechat {
	private static HashMap<String, Index> wechathm = new HashMap<>();
	static Index wechat = null;
	public static void addWechat(String id,Index wechat){
		wechathm.put(id, wechat);
	}
	//È¡³ö
	public static Index getWechat(String loginIdAnFriendId )
	{
		return wechathm.get(loginIdAnFriendId);
	}
}
