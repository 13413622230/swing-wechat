package com.wechat.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.wechat.jdbc.Jdbc;
import com.wechat.model.Bulletin;
import com.wechat.model.Comment;
import com.wechat.model.EnterpriseUser;
import com.wechat.model.Group;
import com.wechat.model.HistorySession;
import com.wechat.model.Moment;
import com.wechat.model.OrdinaryUser;
import com.wechat.model.User;
/*
 * 调用数据库操作类
 */
public class Dao {
	public boolean registerUser(String sql1,String sql2,OrdinaryUser user){
		return new Jdbc().registerUser(sql1, sql2, user);
	}
	public boolean registerEUser(String sql1,String sql2,EnterpriseUser user){
		return new Jdbc().registerEUser(sql1, sql2, user);
	}
	public User OrdinaryUserLogin(String sql1,String sql2,String id,String pw){
		return new Jdbc().OrdinaryUserLogin(sql1, sql2, id, pw);
	}
	public int getState(String sql1,String id){
		return new Jdbc().getState(sql1, id);
	}
	public User EnterpriseUserLogin(String sql1, String sql2, String id, String pw) {
		return new Jdbc().EnterpriseUserLogin(sql1,sql2,id,pw);
	}
	public boolean addMoment(String sql1, Moment moment) {
		return new Jdbc().addMoment(sql1, moment);
	}
	public boolean addComment(String sql1, Comment comment) {
		return new Jdbc().addComment(sql1, comment);
	}
	public boolean deleteComment(String sql1, int id) {
		return new Jdbc().deleteComment(sql1, id);
	}
	public boolean deleteMoment(String sql1, int id) {
		return new Jdbc().deleteMoment(sql1,id);
	}
	public boolean userUpdateHeadImg(String sql1, String id, String headimg) {
		return new Jdbc().userUpdateHeadImg(sql1,id,headimg);
	}
	public boolean userUpdateMessage(String sql1, String name, String sex, String region
			, String mail, String message,
			String pw, String id) {
		return new Jdbc().userUpdateMessage(sql1,name,sex,region,mail,message,pw,id);
	}
	public OrdinaryUser getUser(String sql1, String id) {
		// TODO Auto-generated method stub
		try {
			return new Jdbc().getUser(sql1,id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Group> getGroup(String sql1, String id) throws SQLException {
		// TODO Auto-generated method stub
		return new Jdbc().getAllGroup(sql1,id);
	}
	public boolean addmessage(String sql1, String sender_id, String sendee_id, String content, int type, int state, String sendtme) {
		return new Jdbc().addmessage(sql1,sender_id,sendee_id,content,type,state,sendtme);
	}
	public Group getOntGroup(String sql1, int id) throws SQLException {
		return new Jdbc().getOntGroup(sql1,id);
	}
	public boolean addGroup(String sql1, String group_ownerId, String group_name, String group_b) {
		return new Jdbc().addGroup(sql1,group_ownerId,group_name,group_b);
	}
	public boolean deleteMessage(String sql1, String sender_id, String sendee_id, String content, String time) {
		return new Jdbc().deleteMessage(sql1,sender_id,sendee_id,content,time);
	}
	public boolean delLike(String sql1, int m_id) {
		return new Jdbc().delLike(sql1,m_id);
	}
	public boolean addLike(String sql1, int m_id) {
		return new Jdbc().addLike(sql1,m_id);
	}
	public Moment getLastMoment(String sql1) throws SQLException {
		return new Jdbc().getLastMoment(sql1);
	}
	public boolean addEBullein(String sql1, String title, String content, String e_id, String time) {
		return new Jdbc().addEBullein(sql1,title,content,e_id,time);
	}
	public Bulletin getLastBulletin(String sql1) {
		// TODO Auto-generated method stub
		try {
			return new Jdbc().getLastBulletin(sql1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean delBulletin(String sql1, int id) {
		// TODO Auto-generated method stub
		return new Jdbc().delBulletin(sql1,id);
	}
	public boolean addSession(String sql1, String u_id, String f_id, String time) {
		// TODO Auto-generated method stub
		return new Jdbc().addSession(sql1,u_id,f_id,time);
	}
	public HistorySession getSession(String sql1, String sql11, String u_id, String f_id) {
		// TODO Auto-generated method stub
		try {
			return new Jdbc().getSession1(sql1,sql11,u_id,f_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Group getLastGroup(String sql1) {
		// TODO Auto-generated method stub
		try {
			return new Jdbc().getLastGroup(sql1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public OrdinaryUser searchUser(String sql1, String id) {
		try {
			return new Jdbc().searchUser(sql1,id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public EnterpriseUser addeUser(String sql11,String sql1, String u_id,String e_id) {
		// TODO Auto-generated method stub
		try {
			return new Jdbc().addeUser(sql11,sql1,u_id,e_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean eUpdate(String sql1, String name, String mail, String pw, String tel, String intt, String id) {
		// TODO Auto-generated method stub
		return new Jdbc().eUpdate(sql1,name,mail,pw,tel,intt,id);
	}
	public boolean deleUser(String sql1, String id, String e_id) {
		// TODO Auto-generated method stub
		return new Jdbc().deleUser(sql1,id,e_id);
	}
	public boolean delGroupM(String sql1, int g_id, String m_id) {
		// TODO Auto-generated method stub
		return new Jdbc().delGroupM(sql1,g_id,m_id);
	}
	public boolean delGroup(String sql1, int g_id) {
		// TODO Auto-generated method stub
		return new Jdbc().delGroup(sql1,g_id);
	}
}
