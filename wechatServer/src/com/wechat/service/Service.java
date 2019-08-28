package com.wechat.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.wechat.dao.Dao;
import com.wechat.jdbc.Jdbc;
import com.wechat.model.Bulletin;
import com.wechat.model.Comment;
import com.wechat.model.EnterpriseUser;
import com.wechat.model.Group;
import com.wechat.model.HistorySession;
import com.wechat.model.Message;
import com.wechat.model.Moment;
import com.wechat.model.OrdinaryUser;
import com.wechat.model.TextMessage;
import com.wechat.model.User;
/*
 * 业务逻辑处理类
 */
public class Service {
	public boolean registerUser(OrdinaryUser user){
		String sql1 = "select * from enterpriseuser where id = ?;";
		String sql2 = "insert into user values (?,?,?,?,?,?,?,?,?);";
		return new Dao().registerUser(sql1, sql2, user);
	}
	public boolean registerEUser(EnterpriseUser user){
		String sql1 = "select * from user where id = ?;";
		String sql2 = "insert into enterpriseuser (id,pw,name,mail,state) values (?,?,?,?,0);";
		return new Dao().registerEUser(sql1, sql2, user);
	}
	public User OrdinaryUserLogin(User user){
		String sql1 = "select * from user where id = ?;";
		String sql2 = "select * from user where id = ? and pw = ?;";
		return new Dao().OrdinaryUserLogin(sql1, sql2, user.getId(), user.getPw());
	}
	public User EnterpriseUserLogin(User user){
		String sql1 = "select * from enterpriseuser where id = ?;";
		String sql2 = "select * from enterpriseuser where id = ? and pw = ?;";
		return new Dao().EnterpriseUserLogin(sql1, sql2, user.getId(), user.getPw());
	}
	public boolean sendtext(TextMessage message){
		String f_id = message.getSendee().getId();
		String sql1 = "select state from user where id = ?;";
		int state = new Dao().getState(sql1, f_id);
		if(state==1){
			return true;
		}
		else
			return false;
	}
	
	
	public boolean userUpdateHeadImg(String id,String headimg){
		String sql1 = "update user set headimg = ? where id = ?";
		return new Dao().userUpdateHeadImg(sql1,id,headimg);
	}
	public boolean userUpdateMessage(OrdinaryUser user){
		String sql1 = "update user set name = ?,sex = ?,region = ?,mail = ?,message = ?,pw = ? where id = ?";
		String name = user.getName();
		String sex = user.getSex();
		String region = user.getRegion();
		String mail = user.getMail();
		String message = user.getMessage();
		String pw = user.getPw();
		String id = user.getId();
		return new Dao().userUpdateMessage(sql1,name,sex,region,mail,message,pw,id);
	}
	public OrdinaryUser getUser(String id){
		String sql1 = "select * from user where id = ?;";
		return new Dao().getUser(sql1,id);
	}
	public int getState(String id){
		String sql1 = "select * from user where id = ?;";
		return new Dao().getState(sql1, id);
	}
	public ArrayList<Group> getGroup(String id) throws SQLException{
		String sql1 = "select * from groups where groups.id in (select id from groups where owner_id = ? union select g_id id from groupmember where member_id = ?)";
		return new Dao().getGroup(sql1, id);
	}
	public static void main(String[] args) {
//		try {
////			System.out.println(getGroup("123").size());
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	public boolean addmessage(String sender_id, String sendee_id, String content, int type, int state,String sendtme) {
		String sql1 = "insert messages (sender_id,sendee_id,content,type,state,sendtime) values (?,?,?,?,?,?);";
		return new Dao().addmessage(sql1,sender_id,sendee_id,content,type,state,sendtme);
	}
	public Group getOntGroup(int id){
		String sql1 = "select * from groups where id = ?;";
		try {
			return new Dao().getOntGroup(sql1,id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public boolean addGroup(Group g){
		String group_name = g.getGroupName();
		String group_b = g.getGroupBulletin();
		String group_ownerId = g.getGroupOwner().getId();
		String sql1 = "insert into groups (owner_id,name,bullein) values (?,?,?);";
		return new Dao().addGroup(sql1,group_ownerId,group_name,group_b);
	}
	//撤回消息
	public boolean deleteMessage(Message message){
		String sender_id = message.getSender().getId();
		String sendee_id = message.getSendee().getId();
		String content = message.getContent();
		String time = message.getSendTime();
		String sql1 = "delete from messgaes where sender_id = ? and sendee_id = ? and content = ? and sendtime = ?";
		return new Dao().deleteMessage(sql1,sender_id,sendee_id,content,time);
	}
	public boolean delLike(int m_id){
		String sql1 = "update moments set likes = likes-1 where id = ?";
		return new Dao().delLike(sql1,m_id);
	}
	public boolean addLike(int m_id){
		String sql1 = "update moments set likes = likes+1 where id = ?";
		return new Dao().addLike(sql1,m_id);
	}
//	public boolean addMoment(Moment moment){
//		String user_id = moment.getUser_id();
//		String content = moment.getContent();
//		String time = moment.getTime();
//		String sql1 = "insert moments values (?,?,?,?,0)";
//		return new Dao().addMoment(sql1, moment);
//	}
	public boolean deleteMoment(int m_id){
		String sql1 = "delete from moments where id = ?;";
		return new Dao().deleteMoment(sql1, m_id);
	}
	public boolean addMoment(Moment moment){
		String sql1 = "insert into moments (user_id,content,time,img,likes) values (?,?,?,?,?);";
		return new Dao().addMoment(sql1,moment);
	}
	public boolean addComment(Comment comment){
		String sql1 = "insert into comments (m_id,user_id,content,time) values (?,?,?,?);";
		return new Dao().addComment(sql1,comment);
	}
	public boolean deleteComment(int c_id){
		String sql1 = "delete from comments where id = ?;";
		return new Dao().deleteComment(sql1,c_id);
	}
	public boolean deleteMoment(Moment moment){
		String sql1 = "delete from moments where id = ?;";
		int id = Integer.parseInt(moment.getId());
		return new Dao().deleteMoment(sql1,id);
	}
	public Moment getLastMoment() throws SQLException {
		// TODO Auto-generated method stub
		String sql1 = "select * from moments order by id desc limit 1;";
		return new Dao().getLastMoment(sql1);
	}
	public boolean addEBullein(Bulletin bulletin){
		String sql1 = "insert into bulletins (title,content,e_id,time,likes) values (?,?,?,?,0);";
		String title = bulletin.getTitle();
		String content = bulletin.getContent();
		String e_id = bulletin.getUser_id();
		String time = bulletin.getTime();
		return new Dao().addEBullein(sql1,title,content,e_id,time);
	}
	public Bulletin getLastBulletin() {
		String sql1 = "select * from bulletins order by id desc limit 1;";
		return new Dao().getLastBulletin(sql1);
	}
	public boolean delBulletin(int id){
		String sql1 = "delete from bulletins where id = ?;";
		return new Dao().delBulletin(sql1,id);
	}
	public boolean addSession(HistorySession session){
		String sql1 = "update friends set sessionstate = 1,sessiontime = ? where u_id = ? and f_id = ?;";
		String u_id = session.getId();
		String f_id = session.getFriend().getId();
		String time = session.getTime();
		return new Dao().addSession(sql1,u_id,f_id,time);
	}
	public HistorySession getSession(String u_id, String f_id) {
		// TODO Auto-generated method stub
		String sql1 = "select * from user where id = ?";
		String sql11 = "select * from messages where (sender_id = ? and sendee_id = ?) or (sender_id = ? and sendee_id = ?);";
		return new Dao().getSession(sql1,sql11,u_id,f_id);
	}
	public Group getLastGroup() {
		String sql1 = "select * from groups order by id desc limit 1;";
		return new Dao().getLastGroup(sql1);
	}
	public OrdinaryUser searchUser(String id) {
		// TODO Auto-generated method stub
		String sql1 = "select * from user where id = ?";
		return new Dao().searchUser(sql1,id);
	}
	public boolean addUser(String id, String f_id) {
		// TODO Auto-generated method stub
		String sql1 = "insert friends (u_id,f_id,state,sessionstate) values (?,?,1,0);";
		return new Jdbc().addUser(sql1,id,f_id);
	}
	public boolean delUser(String id, String f_id) {
		// TODO Auto-generated method stub
		String sql1 = "delete from friends where (u_id = ? and f_id = ?) or (u_id = ? and f_id = ?);";
		return new Jdbc().delUser(sql1,id,f_id);
	}
	public EnterpriseUser addeUser(String u_id,String e_id) {
		// TODO Auto-generated method stub
		String sql11 = "insert focus (u_id,e_id) values (?,?);";
		String sql1 = "select * from enterpriseuser where id = ?";
		return new Dao().addeUser(sql11,sql1,u_id,e_id);
	}
	public boolean eUpdate(EnterpriseUser user) {
		String sql1 = "update enterpriseuser set name = ?,mail = ?,pw = ?,telephone = ?,introduction = ? where id = ?";
		String name = user.getName();
		String id = user.getId();
		String mail = user.getMail();
		String pw = user.getPw();
		String tel = user.getTelephone();
		String intt = user.getIntroduction();
		return new Dao().eUpdate(sql1,name,mail,pw,tel,intt,id);
	}
	public boolean deleUser(String id, String e_id) {
		String sql1 = "delete from focus where u_id = ? and e_id = ?;";
		return new Dao().deleUser(sql1,id,e_id);
	}
	public boolean delGroupM(int g_id, String m_id) {
		String sql1 = "delete from groupmember where g_id = ? and member_id = ?;";
		return new Dao().delGroupM(sql1,g_id,m_id);
	}
	public boolean delGroup(int g_id) {
		String sql1 = "delete from groups where id = ?";
		return new Dao().delGroup(sql1,g_id);
	}
	public boolean addGroupM(String u_id,String m_id,int g_id) {
		// TODO Auto-generated method stub
		String sql1 = "select * from friends where u_id = ? and f_id = ?;";
		String sql2 = "insert groupmember (g_id,member_id) values (?,?);";
		try {
			return new Jdbc().addGroupM(sql1,sql2,u_id,m_id,g_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
