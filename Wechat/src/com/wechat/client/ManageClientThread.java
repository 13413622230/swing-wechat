package com.wechat.client;

import java.util.HashMap;

public class ManageClientThread {
	private static HashMap hm=new HashMap<String, ClientThread>();
	//把创建好的ClientThread放入到hm
	public static void addClientConServerThread(String qqId,ClientThread ct)
	{
		hm.put(qqId, ct);
	}
	
	//可以通过微信Id取得该线程 
	public static ClientThread getClientConServerThread(String id)
	{
		return (ClientThread)hm.get(id);
	}
}
