package com.wechat.client;

import java.util.HashMap;

public class ManageClientThread {
	private static HashMap hm=new HashMap<String, ClientThread>();
	//�Ѵ����õ�ClientThread���뵽hm
	public static void addClientConServerThread(String qqId,ClientThread ct)
	{
		hm.put(qqId, ct);
	}
	
	//����ͨ��΢��Idȡ�ø��߳� 
	public static ClientThread getClientConServerThread(String id)
	{
		return (ClientThread)hm.get(id);
	}
}
