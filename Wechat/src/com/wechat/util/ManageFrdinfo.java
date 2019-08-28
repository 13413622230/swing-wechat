package com.wechat.util;

import java.util.HashMap;

import view.Frdinfo;

public class ManageFrdinfo {
	private static HashMap<String, Frdinfo> frdinfo = new HashMap<>();
	public static void addFrdinfo(String friend_id,Frdinfo com){
		frdinfo.put(friend_id, com);
	}
	public static Frdinfo getFrdinfo(String friend_id){
		return frdinfo.get(friend_id);
	}
}
