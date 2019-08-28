package com.wechat.jdbc;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.sun.mail.handlers.message_rfc822;
import com.wechat.model.Bulletin;
import com.wechat.model.Comment;
import com.wechat.model.EnterpriseUser;
import com.wechat.model.Group;
import com.wechat.model.HistorySession;
import com.wechat.model.Message;
import com.wechat.model.Moment;
import com.wechat.model.OrdinaryUser;
import com.wechat.model.User;
import com.wechat.service.Service;
/*
 * 连接数据库类
 */
public class Jdbc {
	Connection con = null;
	PreparedStatement sql;
	ResultSet rs;
	public static Connection getConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver") ;
			String url = "jdbc:mysql://localhost:3306/wechat?characterEncoding=utf8" ;    
			String username = "root" ;   
			String password = "123123" ;   
			Connection con = DriverManager.getConnection(url,username,password); 
			return con;
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
	}
	public Jdbc(){
		con = getConnection();
	}
	//普通用户登陆
	/**
	 * @param sql1 查询语句
	 * @param sql2
	 * @param id
	 * @param pw
	 * @return
	 */
	public User OrdinaryUserLogin(String sql1,String sql2,String id,String pw){
		
		PreparedStatement sql;
		ResultSet rs;
		try {
			sql = con.prepareStatement(sql2);
			sql.setString(1, id);
			sql.setString(2, pw);
			rs = sql.executeQuery();
			if(rs.next()){
				String headimg = rs.getString("headimg");
				String name = rs.getString("name");
				String message = rs.getString("message");
				String sex = rs.getString("sex");
				int state = 1;
				String mail = rs.getString("mail");
				System.out.println(id);
				OrdinaryUser user = new OrdinaryUser(id, pw, headimg
						, name, message, sex, state, mail);
				user.setRegion(rs.getString("region"));
				rs.close();
				
				ArrayList<Moment> moments = getMoments(id, name);
				for(int i =0;i<moments.size();i++){
					moments.get(i).setConments(getComment(moments.get(i).getId()));
				}
				user.setMonents(moments);
				user.setFriends(getFriends(id));
				user.setSession(getSession(id));
				String sql11 = "select * from moments where user_id = ?";
				String[] f = new String[user.getFriends().size()+1];
				int i=0;
				for(User u:user.getFriends()){
					sql11 += " or user_id = ?";
					f[i] = u.getId();
					i++;
				}
				f[i] = user.getId();
				sql11 += " order By time desc;";
				ArrayList<Moment> allmoments = getAllMomet(sql11, f);
				user.setAllmonents(allmoments);
				user.setFocus(getFocus(id));
				user.setGroups(new Service().getGroup(id));
				String sql3 = "update user set state = ? where id = ?;";
				setState(sql3, id,true);
//				user.setFmessages(fmessages);
				return user;
			}
			else{
				sql = con.prepareStatement(sql1);
				sql.setString(1, id);
				rs = sql.executeQuery();
				if(rs.next()){
					return new User("密码错误", null, null, null);
				}
				else{
					return new User("账号不存在", null, null, null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Moment> getAllmoments(String id) throws SQLException {
		ArrayList<OrdinaryUser> fff = getFriends(id);
		String sql11 = "select * from moments where user_id = ?";
		String[] f = new String[fff.size()+1];
		int i=0;
		for(User u:fff){
			sql11 += " or user_id = ?";
			f[i] = u.getId();
			i++;
		}
		f[i] = id;
		sql11 += " order By time desc;";
		ArrayList<Moment> allmoments = getAllMomet(sql11, f);
		return allmoments;
	}
	//企业用户登陆
	public User EnterpriseUserLogin(String sql1, String sql2, String id, String pw) {
		
		PreparedStatement sql;
		ResultSet rs;
		try {
			sql = con.prepareStatement(sql2);
			sql.setString(1, id);
			sql.setString(2, pw);
			rs = sql.executeQuery();
			if(rs.next()){
				String headimg = rs.getString("headimg");
				String name = rs.getString("name");
				int state = 1;
				String mail = rs.getString("mail");
				String introduction = rs.getString("introduction");
				String telephone = rs.getString("telephone");
				rs.close();
				EnterpriseUser user = new EnterpriseUser(id, pw, headimg, name, 
						introduction, telephone, mail, state);
				ArrayList<Bulletin> bulletins = getBulletins(id);
				user.setBulletin(bulletins);
				ArrayList<OrdinaryUser> focusUsers = getFocusUser(id);
				user.setFocusUsers(focusUsers);
				String sql3 = "update enterpriseuser set state = ? where id = ?;";
				setState(sql3, id,true);
				return user;
			}
			else{
				sql = con.prepareStatement(sql1);
				sql.setString(1, id);
				rs = sql.executeQuery();
				if(rs.next()){
					return new User("密码错误", null, null, null);
				}
				else{
					return new User("账号不存在", null, null, null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//普通用户注册
	public boolean registerUser(String sql1,String sql2,OrdinaryUser user){
		//sql1是企业用户查询语句，sql2为user表插入语句
		
		PreparedStatement sql;
		ResultSet rs;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, user.getId());
			rs = sql.executeQuery();
			if(rs.next()){
				return false;
			}
			sql = con.prepareStatement(sql2);
			sql.setString(1, user.getId());
			sql.setString(2, user.getPw());
			sql.setString(3, null);
			sql.setString(4, user.getId());
			sql.setString(5, null);
			sql.setString(6, null);
			sql.setInt(7, 0);
			sql.setString(8, user.getMail());
			sql.setString(9, null);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	//企业用户注册
	public boolean registerEUser(String sql1,String sql2,EnterpriseUser user){
		
		PreparedStatement sql;
		ResultSet rs;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, user.getId());
			rs = sql.executeQuery();
			if(rs.next()){
				return false;
			}
			sql = con.prepareStatement(sql2);
			sql.setString(1, user.getId());
			sql.setString(2, user.getPw());
			sql.setString(3, user.getName());
			sql.setString(4, user.getMail());
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//用用户id和昵称获得普通用户的朋友圈
	public ArrayList<Moment> getMoments(String id,String name){
		String sql1 = "select * from moments where user_id = ? order By time desc;";
		
		PreparedStatement sql;
		ResultSet rs;
		ArrayList<Moment> moments = new ArrayList<>();
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, id);
			rs = sql.executeQuery();
			while(rs.next()){
				moments.add(new Moment(rs.getString("id"),id, name, rs.getString("content"),
						rs.getString("time"), rs.getString("img"), null, rs.getInt("likes")
						,null));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moments;
	}
	//用用户id获取好友列表
	public ArrayList<OrdinaryUser> getFriends(String u_id){
		String sql1 = 
		"select friends.f_id,user.* from friends,user where friends.u_id = ? and user.id = friends.f_id;";
		
		PreparedStatement sql;
		ResultSet rs;
		ArrayList<OrdinaryUser> friends = new ArrayList<>();
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, u_id);
			rs = sql.executeQuery();
			while(rs.next()){
				OrdinaryUser user = new OrdinaryUser(rs.getString("id"), null, rs.getString("headimg")
						, rs.getString("name"), rs.getString("message"), rs.getString("sex"), 1, rs.getString("mail"));
				user.setRegion(rs.getString("region"));
				friends.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friends;
	}
	//用朋友圈id获取朋友圈的评论
	public ArrayList<Comment> getComment(String m_id){
		String sql1 = 
				"select comments.*,user.name from comments,user where comments.m_id = ? and user.id = comments.user_id;";
		
		PreparedStatement sql;
		ResultSet rs;
		ArrayList<Comment> comments = new ArrayList<>();
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, m_id);
			rs = sql.executeQuery();
			while(rs.next()){
				comments.add(new Comment(rs.getString("id"), rs.getString("content"), 
						rs.getString("user_id"), rs.getString("name"), rs.getString("time")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}
	//获取用户会话记录
	public ArrayList<HistorySession> getSession(String u_id){
		String sql1 = 
				"select friends.*,user.name,user.headimg from friends,user where friends.u_id = ? and user.id = friends.f_id;";
		
		PreparedStatement sql;
		ResultSet rs;
		ArrayList<HistorySession> session = new ArrayList<>();
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, u_id);
			rs = sql.executeQuery();
			
			while(rs.next()){
				System.out.println(rs.getString("name"));
				if(rs.getInt("sessionstate")==1){
					HistorySession s = new HistorySession(new User(rs.getString("f_id"), null, rs.getString("headimg"),
							rs.getString("name")), null, rs.getString("sessiontime"));
					String sql11 = "select * from messages where (sender_id = ? and sendee_id = ?) or (sender_id = ? and sendee_id = ?);";
					
					s.setMessages(getMessages(sql11, rs.getString("f_id"),u_id));
					session.add(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}
	public ArrayList<Message> getMessages(String sql1, String f_id,String u_id) throws SQLException {
		
		PreparedStatement sql;
		ResultSet rs;
		sql = con.prepareStatement(sql1);
		sql.setString(1, f_id);
		sql.setString(2, u_id);
		sql.setString(3, u_id);
		sql.setString(4, f_id);
		rs = sql.executeQuery();
		ArrayList<Message> messages = new ArrayList<>();
		while(rs.next()){
			Message me = new Message(getOneUser(rs.getString("sender_id")), getOneUser(rs.getString("sendee_id"))
					, rs.getString("sendtime"), rs.getInt("type"), rs.getString("content"));
			me.setId(rs.getInt("id"));
			messages.add(me);
		}
		return messages;
	}
	//获取订阅号列表
	public ArrayList<EnterpriseUser> getFocus(String u_id){
		String sql1 = 
				"select e.* from focus f,enterpriseuser e where f.e_id = e.id and f.u_id = ?;";
		
		PreparedStatement sql;
		ResultSet rs;
		ArrayList<EnterpriseUser> focus = new ArrayList<>();
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, u_id);
			rs = sql.executeQuery();
			while(rs.next()){
				EnterpriseUser temp = new EnterpriseUser(rs.getString("id"), null, rs.getString("headimg")
						, rs.getString("name"), rs.getString("introduction"), rs.getString("telephone")
						, rs.getString("mail"), rs.getInt("state"));
				temp.setBulletin(getBulletins(rs.getString("id")));
				focus.add(temp);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return focus;
	}
	//改变用户登陆状态
	public boolean setState(String sql1,String id, boolean boo){
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			if(boo){
				sql.setInt(1, 1);
			}
			else{
				sql.setInt(1, 0);
			}
			sql.setString(2, id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	//查看用户登陆状态
	public int getState(String sql1,String id){
		
		PreparedStatement sql;
		ResultSet rs;
		int state = -1;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, id);
			rs = sql.executeQuery();
			if(rs.next()){
				state = rs.getInt("state");
				return state;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return state;
		}
		return state;
	}
	//查看用户信息
	public User selectUser(String sql1,String id){
		
		PreparedStatement sql;
		ResultSet rs;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, id);
			rs = sql.executeQuery();
			if(rs.next()){
				return new OrdinaryUser(id, null, rs.getString("headimg"), rs.getString("name")
						, rs.getString("sex"), rs.getString("region"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	//获取企业用户公告
	public ArrayList<Bulletin> getBulletins(String u_id){
		ArrayList<Bulletin> bulletins = new ArrayList<>();
		String sql1 = "select * from bulletins where e_id = ?;";
		
		PreparedStatement sql;
		ResultSet rs;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, u_id);
			rs = sql.executeQuery();
			while(rs.next()){
				bulletins.add(new Bulletin( 
						rs.getInt("id"), 
						u_id,rs.getString("title"), rs.getString("content"),rs.getString("time"),rs.getInt("likes")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return bulletins;
		}
		return bulletins;
	}
	//获取订阅号列表
	public ArrayList<OrdinaryUser> getFocusUser(String e_id){
		String sql1 = 
				"select u.* from focus f,user u where f.u_id = u.id and f.e_id = ?;";
		
		PreparedStatement sql;
		ResultSet rs;
		ArrayList<OrdinaryUser> focus = new ArrayList<>();
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, e_id);
			rs = sql.executeQuery();
			while(rs.next()){
				focus.add(new OrdinaryUser(rs.getString("id"), null, rs.getString("headimg")
						, rs.getString("name"), rs.getString("sex"), rs.getString("region")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return focus;
	}
	//发布朋友圈
	public boolean addMoment(String sql1, Moment moment) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, moment.getUser_id());
			sql.setString(2, moment.getContent());
			sql.setString(3, moment.getTime());
			sql.setString(4, moment.getImg());
			sql.setInt(5, 0);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//发布评论
	public boolean addComment(String sql1, Comment comment) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, comment.getId());
			sql.setString(2, comment.getUser_id());
			sql.setString(3, comment.getContent());
			sql.setString(4, comment.getTime());
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//删除评论
	public boolean deleteComment(String sql1, int id) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setInt(1, id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//删除朋友圈
	public boolean deleteMoment(String sql1, int id) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setInt(1, id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//更新数据库头像路径
	public boolean userUpdateHeadImg(String sql1, String id, String headimg) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, headimg);
			sql.setString(2, id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//修改信息
	public boolean userUpdateMessage(String sql1, String name, String sex, String region
			, String mail, String message,
			String pw, String id) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, name);
			sql.setString(2, sex);
			sql.setString(3, region);
			sql.setString(4, mail);
			sql.setString(5, message);
			sql.setString(6, pw);
			sql.setString(7, id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sql1 = "select * from user where id = ?;";
		String sql2 = "select * from user where id = ? and pw = ?;";
		User user =  new Jdbc().OrdinaryUserLogin(sql1, sql2, "123", "123");
		System.out.println(user.getId());
//		new Service().registerUser(new OrdinaryUser("12345", "123", "123", "123", "test", "女", 0, "1376261775.qq.com"));
//		System.out.println(user.getAllmonents().size());
//		System.out.println(user.getAllmonents().get(0).getConments().get(0).getContent());
	}
	public OrdinaryUser getUser(String sql1, String id) throws SQLException {
		
		PreparedStatement sql;
		ResultSet rs;
		sql = con.prepareStatement(sql1);
		sql.setString(1, id);
		rs = sql.executeQuery();
		if(rs.next()){
			String headimg = rs.getString("headimg");
			String name = rs.getString("name");
			String message = rs.getString("message");
			String sex = rs.getString("sex");
			int state = rs.getInt("state");
			String mail = rs.getString("mail");
			System.out.println(id);
			OrdinaryUser user = new OrdinaryUser(id, rs.getString("pw"), headimg
					, name, message, sex, state, mail);
			user.setRegion(rs.getString("region"));
			rs.close();
			System.out.println(sex);
			
			ArrayList<Moment> moments = getMoments(id, name);
			for(int i =0;i<moments.size();i++){
				moments.get(i).setConments(getComment(moments.get(i).getId()));
			}
			user.setMonents(moments);
			user.setFriends(getFriends(id));
			user.setSession(getSession(id));
			ArrayList<Moment> allmoments = new ArrayList<>();
			allmoments.addAll(moments);
			for(User u:user.getFriends()){
				allmoments.addAll(getMoments(u.getId(), u.getName()));
			}
			for(int i =0;i<allmoments.size();i++){
				allmoments.get(i).setConments(getComment(allmoments.get(i).getId()));
			}
			user.setAllmonents(allmoments);
			user.setFocus(getFocus(id));
			user.setGroups(new Service().getGroup(user.getId()));
			return user;
		}
		return null;
	}
	public User getOneUser(String id) throws SQLException{
		
		PreparedStatement sql;
		ResultSet rs;
		sql = con.prepareStatement("select * from user where id = ?;");
		sql.setString(1, id);
		rs = sql.executeQuery();
		if(rs.next()){
			return new User(id, null, rs.getString("headimg"), rs.getString("name"));
		}
		else{
			return null;
		}
	}
	public ArrayList<Group> getAllGroup(String sql1, String id) throws SQLException {
		
		PreparedStatement sql;
		ResultSet rs;
		sql = con.prepareStatement(sql1);
		sql.setString(1, id);
		sql.setString(2, id);
		rs = sql.executeQuery();
		ArrayList<Group> group = new ArrayList<>();
		while(rs.next()){
			Group g = new Group(rs.getInt("id"), getOneUser(rs.getString("owner_id"))
					, null, rs.getString("name"), rs.getString("bullein"));
			g.setGroupMembers(getGroupMembers(g.getGroupId()));
			g.setMessages(getMmessage(g.getGroupId()));
			group.add(g);
		}
		return group;
	}
	
	public ArrayList<Message> getMmessage(int groupId) {
		ArrayList<Message> mess = new ArrayList<>();
		try {
			PreparedStatement sql;
			ResultSet rs;
			String sql1 = "select * from groupmessages where g_id = ?";
			System.out.println("FASDF G_ID="+groupId);
			System.out.println(sql1);
			sql = con.prepareStatement(sql1);
			sql.setInt(1, groupId);
			rs = sql.executeQuery();
			
			while(rs.next()){
				System.out.println("jinlaila ");
				Message me = new Message(getOneUser(rs.getString("sender_id")), null
						, rs.getString("sendtime"), rs.getInt("type"), rs.getString("content"));
				me.setId(rs.getInt("id"));
				mess.add(me);
				System.out.println("hahahah");
				System.out.println("jfiasdif");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mess;
	}
	public ArrayList<User> getGroupMembers(int g_id) throws SQLException{
		
		PreparedStatement sql;
		ResultSet rs;
		sql = con.prepareStatement("select * from groupmember where g_id = ?;");
		sql.setInt(1, g_id);
		rs = sql.executeQuery();
		ArrayList<User> groupMembers = new ArrayList<>();
		while(rs.next()){
			groupMembers.add(getOneUser(rs.getString("member_id")));
		}
		return groupMembers;
	}
	public User getOwner(int g_id) throws SQLException{
		PreparedStatement sql;
		ResultSet rs;
		sql = con.prepareStatement("select * from groups where id = ?;");
		sql.setInt(1, g_id);
		rs = sql.executeQuery();
		User user = null;
		while(rs.next()){
			user = getOneUser(rs.getString("owner_id"));
		}
		return user;
	}
	public boolean addmessage(String sql1, String sender_id, String sendee_id, String content,
			int type, int state, String sendtme) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, sender_id);
			sql.setString(2, sendee_id);
			sql.setString(3, content);
			sql.setInt(4, type);
			sql.setInt(5, state);
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			System.out.println(sendtme);
//			long t = sdf.parse(sendtme).getTime();
//			Date d = new Date(t);
//			System.out.println(t);
//			System.out.println(d);
			sql.setString(6, sendtme);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
//	public ArrayList<Message> getMessages(String sql1,String sender_id,String sendee_id) throws SQLException{
//		
//		PreparedStatement sql;
//		ResultSet rs;
//		sql = con.prepareStatement(sql1);
//		sql.setString(1, sender_id);
//		sql.setString(2, sendee_id);
//		rs = sql.executeQuery();
//		ArrayList<Message> messages = new ArrayList<>();
//		while(rs.next()){
//			messages.add(new Message(getOneUser(sender_id), getOneUser(sendee_id)
//					, rs.getString("sendtime"), rs.getInt("type"), rs.getString("content")));
//		}
//		return messages;
//	}
	public Group getOntGroup(String sql1, int id) throws SQLException {
		
		PreparedStatement sql;
		ResultSet rs;
		sql = con.prepareStatement(sql1);
		sql.setInt(1, id);
		rs = sql.executeQuery();
		Group g = null;
		if(rs.next()){
			g = new Group(rs.getInt("id"), getOneUser(rs.getString("owner_id"))
					, null, rs.getString("name"), rs.getString("bullein"));
			g.setGroupMembers(getGroupMembers(g.getGroupId()));
		}
		return g;
	}
	public boolean addGroup(String sql1, String group_ownerId, String group_name, String group_b) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, group_ownerId);
			sql.setString(2, group_name);
			sql.setString(3, group_b);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteMessage(String sql1, String sender_id, String sendee_id, String content, String time) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, sender_id);
			sql.setString(2, sendee_id);
			sql.setString(3, content);
			sql.setString(4, time);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public ArrayList<Moment> getAllMomet(String sql1,String[] args) throws SQLException{
		
		PreparedStatement sql;
		ResultSet rs;
		System.out.println(sql1);
		sql = con.prepareStatement(sql1);
		int i = 1;
		System.out.println(args.length);
		for(String s:args){
			sql.setString(i, s);
			i++;
		}
		rs = sql.executeQuery();
		ArrayList<Moment> moments = new ArrayList<>();
		while(rs.next()){
			Moment m = null;
			m = new Moment(rs.getString("id"), rs.getString("user_id")
					, getOneUser(rs.getString("user_id")).getName(), rs.getString("content"), rs.getString("time")
					, rs.getString("img"), null, rs.getInt("likes"), getComment(rs.getString("id")));
			moments.add(m);
		}
		return moments;
	}
	public boolean delLike(String sql1, int m_id) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setInt(1, m_id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean addLike(String sql1, int m_id) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setInt(1, m_id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public Moment getLastMoment(String sql1) throws SQLException {
		
		PreparedStatement sql;
		ResultSet rs;
		System.out.println(sql1);
		sql = con.prepareStatement(sql1);
		rs = sql.executeQuery();
		Moment m = null;
		if(rs.next()){
			m = new Moment(rs.getString("id"), rs.getString("user_id")
					, getOneUser(rs.getString("user_id")).getName(), rs.getString("content"), rs.getString("time")
					, rs.getString("img"), null, rs.getInt("likes"), getComment(rs.getString("id")));
		}
		return m;
	}
	public boolean addEBullein(String sql1, String title, String content, String e_id, String time) {
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, title);
			sql.setString(2, content);
			sql.setString(3, e_id);
			sql.setString(4, time);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public Bulletin getLastBulletin(String sql1) throws SQLException {
		
		PreparedStatement sql;
		ResultSet rs;
		System.out.println(sql1);
		sql = con.prepareStatement(sql1);
		rs = sql.executeQuery();
		Bulletin bulletin = null;
		if(rs.next()){
			bulletin = new Bulletin(rs.getInt("id"), rs.getString("e_id"),
					rs.getString("title")
					, rs.getString("content"), rs.getString("time"),rs.getInt("likes"));
		}
		return bulletin;
	}
	public boolean delBulletin(String sql1, int id) {
		System.out.println(sql1);
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setInt(1, id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean addSession(String sql1, String u_id, String f_id, String time) {
		System.out.println(sql1);
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, time);
			sql.setString(2, u_id);
			sql.setString(3, f_id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public HistorySession getSession1(String sql1, String sql11, String u_id, String f_id) throws SQLException {
		HistorySession session = new HistorySession(getOneUser(f_id), null, null);
		session.setMessages(getMessages(sql11, f_id, u_id));
		return session;
	}
//	public Moment getLastMoment(String sql1) throws SQLException {
//		
//		PreparedStatement sql;
//		ResultSet rs;
//		System.out.println(sql1);
//		sql = con.prepareStatement(sql1);
//		rs = sql.executeQuery();
//		Moment m = null;
//		if(rs.next()){
//			m = new Moment(rs.getString("id"), rs.getString("user_id")
//					, getOneUser(rs.getString("user_id")).getName(), rs.getString("content"), rs.getString("time")
//					, rs.getString("img"), null, rs.getInt("likes"), getComment(rs.getString("id")));
//		}
//		return m;
//	}
	public Group getLastGroup(String sql1) throws SQLException {
		
		PreparedStatement sql;
		ResultSet rs;
		System.out.println(sql1);
		sql = con.prepareStatement(sql1);
		rs = sql.executeQuery();
		Group g = null;
		if(rs.next()){
			g = new Group(rs.getInt("id")
					, getOneUser(rs.getString("owner_id")), getGroupMembers(rs.getInt("id"))
					, rs.getString("name"), rs.getString("bullein"));
			g.setMessages(getMmessage(g.getGroupId()));
		}
		return g;
	}
	public OrdinaryUser searchUser(String sql1, String id) throws SQLException {
		
		PreparedStatement sql;
		ResultSet rs;
		System.out.println(sql1);
		sql = con.prepareStatement(sql1);
		sql.setString(1, id);
		rs = sql.executeQuery();
		OrdinaryUser user = null;
		if(rs.next()){
			user = new OrdinaryUser(rs.getString("id"), null, rs.getString("headimg")
					, rs.getString("name"), rs.getString("message"), rs.getString("sex")
					, rs.getInt("state"), rs.getString("mail"));
			user.setRegion(rs.getString("region"));
		}
		return user;
	}
	public boolean addUser(String sql1,String id, String f_id) {
		System.out.println(sql1);
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, id);
			sql.setString(2, f_id);
			if(sql.executeUpdate()==1){
				sql = con.prepareStatement(sql1);
				sql.setString(1, f_id);
				sql.setString(2, id);
				if(sql.executeUpdate()==1){
					return true;
				}
				else{
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean delUser(String sql1, String id, String f_id) {
		System.out.println(sql1);
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, id);
			sql.setString(2, f_id);
			sql.setString(3, f_id);
			sql.setString(4, id);
			if(sql.executeUpdate()==2){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public EnterpriseUser addeUser(String sql11,String sql1, String u_id,String e_id) throws SQLException {
		
		PreparedStatement sql;
		ResultSet rs;
		System.out.println(sql1);
		System.out.println(sql11);
		sql = con.prepareStatement(sql11);
		sql.setString(1, u_id);
		sql.setString(2, e_id);
		EnterpriseUser user = null;
		if(sql.executeUpdate()==1){
			sql = con.prepareStatement(sql1);
			sql.setString(1, e_id);
			rs = sql.executeQuery();
			if(rs.next()){
				user = new EnterpriseUser(rs.getString("id"), null
						, rs.getString("headimg"), rs.getString("name")
						, rs.getString("introduction"), rs.getString("telephone")
						, rs.getString("mail"), rs.getInt("state"));
				user.setBulletin(getBulletins(user.getId()));
			}
			else{
				user = new EnterpriseUser(null, "账号不存在", null, null, null, null, null, 0);
			}
		}
		else{
			user = new EnterpriseUser(null, "关注失败", null, null, null, null, null, 0);
		}
		
		return user;
	}
	public boolean eUpdate(String sql1, String name, String mail, String pw, String tel, String intt, String id) {
		System.out.println(sql1);
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, name);
			sql.setString(2, mail);
			sql.setString(3, pw);
			sql.setString(4, tel);
			sql.setString(5, intt);
			sql.setString(6, id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleUser(String sql1, String id, String e_id) {
		System.out.println(sql1);
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setString(1, id);
			sql.setString(2, e_id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean delGroupM(String sql1, int g_id, String m_id) {
		System.out.println(sql1);
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setInt(1, g_id);
			sql.setString(2, m_id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean delGroup(String sql1, int g_id) {
		System.out.println(sql1);
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setInt(1, g_id);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean addGroupM(String sql1, String sql2, String u_id, String m_id, int g_id) throws SQLException {
		
		PreparedStatement sql;
		ResultSet rs;
		System.out.println(sql1);
		sql = con.prepareStatement(sql1);
		sql.setString(1, u_id);
		sql.setString(2, m_id);
		rs = sql.executeQuery();
		if(rs.next()){
			sql = con.prepareStatement(sql2);
			sql.setInt(1, g_id);
			sql.setString(2, m_id);
			if(sql.executeUpdate()==1){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
		
	}
	public boolean addGmessage(String sql1, int g_id, String sender_id
			, String content, String sendtime, int type) {
		System.out.println(sql1);
		
		PreparedStatement sql;
		try {
			sql = con.prepareStatement(sql1);
			sql.setInt(1, g_id);
			sql.setString(2, sender_id);
			sql.setString(3, content);
			sql.setString(4, sendtime);
			sql.setInt(5, type);
			if(sql.executeUpdate()==1){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
