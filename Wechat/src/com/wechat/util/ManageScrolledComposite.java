package com.wechat.util;

import java.util.HashMap;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Label;
/*
 * �Ự�û����ֹ�����
 */
public class ManageScrolledComposite {
	private static HashMap<String, ScrolledComposite> Scrol = new HashMap<>();
	public static void addManageScrolledComposite (String friend_id,ScrolledComposite la){
		Scrol.put(friend_id, la);
	}
	public static ScrolledComposite getManageScrolledComposite (String friend_id){
		return Scrol.get(friend_id);
	}
}
