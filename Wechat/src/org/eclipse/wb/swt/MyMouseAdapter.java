package org.eclipse.wb.swt;

import org.eclipse.swt.events.MouseAdapter;

import com.wechat.model.OrdinaryUser;

import view.Index;
import view.Login;
import view.Register;

public class MyMouseAdapter extends MouseAdapter {
	public Register re;
	public Login login;
	public Index index;
	public OrdinaryUser user;
	public MyMouseAdapter(Register re){
		this.re = re;
	}
	public MyMouseAdapter(Login login){
		this.login = login;
	}
	public MyMouseAdapter(Index index){
		this.index = index;
	}
	public MyMouseAdapter(OrdinaryUser user){
		this.user = user;
	}
}
