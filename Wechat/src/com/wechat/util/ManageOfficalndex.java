package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.widgets.Label;

import view.Officalndex;
/*
 * 会话用户名字管理类
 */
public class ManageOfficalndex {
	private static HashMap<String, Officalndex> officalndex = new HashMap<>();
	public static void addSessionSendTime (String e_id,Officalndex index){
		officalndex.put(e_id, index);
	}
	public static Officalndex getSessionSendTime (String e_id){
		return officalndex.get(e_id);
	}
}