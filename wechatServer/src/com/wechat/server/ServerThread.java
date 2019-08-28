package com.wechat.server;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import com.wechat.jdbc.Jdbc;
import com.wechat.model.Bulletin;
import com.wechat.model.Comment;
import com.wechat.model.EnterpriseUser;
import com.wechat.model.Group;
import com.wechat.model.HistorySession;
import com.wechat.model.Message;
import com.wechat.model.MessageType;
import com.wechat.model.Moment;
import com.wechat.model.OrdinaryUser;
import com.wechat.model.TextMessage;
import com.wechat.model.User;
import com.wechat.model.VoiceMessage;
import com.wechat.service.Service;
import com.wechat.util.MailUtil;

public class ServerThread extends Thread implements Protocal,MessageType{
	Socket socket = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	MailUtil ma = null;
	boolean clo = false;
	public String id;
	public ServerThread(Socket socket){
		this.socket = socket;
		
	}
	public void run(){
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(this.socket.getOutputStream());
			System.out.println(999);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("客户端连接成功："+socket.getLocalAddress());
			int request = 0;
			while(true){
				System.out.println("服务器流堵塞中");
				request = ois.readInt();
				if((request)==-1){
					continue;
				}
				if(request==sendCode){
					String to = ois.readUTF();
					ma = new MailUtil();
					ma.send_mail(to);
					System.out.println(ma);
					System.out.println("发送验证码成功");
					oos.writeInt(sendCodeSuccess);
					oos.flush();
				}
				else if(request==userRegister){
					String c = ois.readUTF();
					System.out.println(c+"验证码a？？？？");
					System.out.println(ma);
					OrdinaryUser user = (OrdinaryUser) ois.readObject();
					if(ma==null||!ma.verification(c)){
						System.out.println("注册失败，验证码不正确");
						oos.writeInt(registerError_code);
						oos.flush();
					}
					else{
						boolean boo = new Service().registerUser(user);
						System.out.println(boo);
						if(boo){
							System.out.println("注册成功");
							oos.writeInt(registerSuccess);
							oos.flush();
						}
						else{
							System.out.println("注册失败，账号已经存在");
							oos.writeInt(registerError_id);
							oos.writeObject(-1);
							oos.flush();
						}
					}
				}
				else if(request==eUserRegister){
					String c = ois.readUTF();
					EnterpriseUser user = (EnterpriseUser) ois.readObject();
					if(ma==null||!ma.verification(c)){
						System.out.println("注册失败，验证码不正确");
						oos.writeInt(registerError);
						oos.writeInt(registerError_code);
						oos.writeInt(-1);
						oos.flush();
					}
					else{
						boolean boo = new Service().registerEUser(user);
						if(boo){
							System.out.println("注册成功");
							oos.writeInt(registerSuccess);
							oos.writeInt(-1);
							oos.flush();
						}
						else{
							System.out.println("注册失败，账号已经存在");
							oos.writeInt(registerError_id);
							oos.writeInt(-1);
							oos.flush();
						}
					}
				}
				else if(request==userLogin){
					User user =  (User) ois.readObject();
					User respons = new Service().OrdinaryUserLogin(user);
					if(respons.getId().equals("账号不存在")){
						System.out.println(respons.getId());
						oos.writeInt(loginError);
						oos.flush();
						oos.writeInt(notNumber);
						oos.flush();
						oos.writeInt(-1);
						oos.flush();
					}
					else if(respons.getId().equals("密码错误")){
						System.out.println(respons.getId());
						oos.writeInt(loginError);
						oos.flush();
						oos.writeInt(notPassword);
						oos.flush();
						oos.writeInt(-1);
						oos.flush();
					}
					else{
						System.out.println(respons.getId()+"登陆成功");
						MyServer.th.put(respons.getId(), this);
						id = respons.getId();
						oos.writeInt(loginSuccess);
						oos.flush();
						oos.writeObject(respons);
						oos.flush();
						oos.writeInt(-1);
						oos.flush();
					}
				}
				else if(request==eUserLogin){
					User user =  (User) ois.readObject();
					User respons = new Service().EnterpriseUserLogin(user);
					if(respons.getId().equals("账号不存在")){
						oos.writeInt(loginError);
						oos.flush();
						System.out.println(respons.getId());
						oos.writeInt(notNumber);
						oos.flush();
					}
					else if(respons.getId().equals("密码错误")){
						System.out.println(respons.getId());
						oos.writeInt(loginError);
						oos.flush();
						oos.writeInt(notPassword);
						oos.flush();
					}
					else{
						System.out.println(respons.getId()+"登陆成功");
						MyServer.th.put(respons.getId(), this);
						oos.writeInt(eLoginSuccess);
						oos.flush();
						oos.writeObject(respons);
						oos.flush();
					}
				}
				else if(request==updaHeadImg){
					System.out.println("客户端更新头像");
					accpetHeadImg();
				}
//				else if(request==sendtesxt){
//					TextMessage message = (TextMessage) ois.readObject();
//					if(new Service().sendtext(message)){
//						System.out.println("发送成功");
//					}
//				}
				else if(request==sendUpdateCode){
					String to = ois.readUTF();
					String id = ois.readUTF();
					ma = new MailUtil();
					ma.send_mail(to);
					System.out.println("发送验证码成功");
					oos.writeInt(updateSendCodeSuccess);
					oos.flush();
					oos.writeUTF(id);
					oos.flush();
					oos.writeInt(-1);
					oos.flush();
				}
				else if(request==userUpdateMessage){
					userUpdateMessage();
				}
				else if(request==userUpdateMessageNotCode){
					userUpdateMessageNotCode();
				}
				else if(request==sendMessage){
					//接收消息
					System.out.println("服务端接收信息");
					accpetMessage();
				}
				else if(request==sendGMessage){
					//接收消息
					System.out.println("服务端接收群信息");
					accpetGmessage();
				}
				else if(request==addLike){
					int m_id = ois.readInt();
					new Service().addLike(m_id);
				}
				else if(request==addComment){
					Comment com = (Comment) ois.readObject();
					new Service().addComment(com);
				}
				else if(request==addMoment){
					Moment m = (Moment) ois.readObject();
					new Service().addMoment(m);
					oos.writeInt(accpetLastMoment);
					oos.flush();
					oos.writeObject(new Service().getLastMoment());
					oos.flush();
					
				}
				else if(request==delLike){
					int m_id = ois.readInt();
					new Service().delLike(m_id);
				}
				else if(request==delComment){
					int c_id = ois.readInt();
					new Service().deleteComment(c_id);
				}
				else if(request==delMoment){
					int m_id = ois.readInt();
					if(new Service().deleteMoment(m_id)){
//						oos.writeInt();
					}
				}
				else if(request==addeBullein){
					addeBullein();
				}
				else if(request==deleBulletin){
					deleBulletin();
				}
				else if(request==addSession){
					addSession();
				}
				else if(request==addGroup){
					addGroup();
				}
				else if(request==searchUser){
					searchUser();
				}
				else if(request==addUser){
					addUser();
				}
				else if(request==delUser){
					delUser();
				}
				else if(request==addeUser){
					addeUser();
				}
				else if(request==sendeCode){
					sendeCode();
				}
				else if(request==eUpdateNo){
					eUpdateNo();
				}
				else if(request==eUpdate){
					eUpdate();
				}
				else if(request==deleUser){
					deleUser();
				}
				else if(request==delGroupM){
					delGroupM();
				}
				else if(request==delGroup){
					delGroup();
				}
				else if(request==flushf){
					ArrayList<Moment> moments = new Jdbc().getAllmoments(id);
					oos.writeInt(flushfSuccess);
					oos.flush();
					oos.writeObject(moments);
					oos.flush();
				}
				else if(request==addGroupM){
					addGroupM();
				}
				else if(request==eflush){
					EnterpriseUser user = (EnterpriseUser) ois.readObject();
					User respons = new Service().EnterpriseUserLogin(user);
					oos.writeInt(eflushSuccess);
					oos.flush();
					oos.writeObject(respons);
					oos.flush();
				}
			}
		} 
		catch (SocketException e) {
			for(int i=0;i<MyServer.th.size();i++){
				if(MyServer.th.get(i)==this){
					try {
						MyServer.th.get(i).socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					MyServer.th.remove(i);
					break;
				}
			}
			System.out.println("用户登陆失败，其线程已经关闭");
		}
		catch (Exception e) {
			e.printStackTrace();
			clo = true;
		}
	}
	public void accpetGmessage() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("服务端进来中");
		int g_id = ois.readInt();
		int type = ois.readInt();
		System.out.println(type);
		if(type==sendtesxt){
			Message tMessage = (Message) ois.readObject();
			
			saveGMessage(tMessage,g_id);
			ArrayList<User> gUser;
			try {
				gUser = new Jdbc().getGroupMembers(g_id);
				User owner = new Jdbc().getOwner(g_id);
				if(!owner.getId().equals(id)){
					notifyOther(owner.getId(), acceptGText);
					notifyOther(owner.getId(), g_id);
					ForwardOther(owner.getId(), tMessage);
				}
				for(User uu:gUser){
					if(!uu.getId().equals(id)){
						if(new Service().getState(uu.getId())!=0){
							notifyOther(uu.getId(), acceptGText);
							notifyOther(uu.getId(), g_id);
							ForwardOther(uu.getId(), tMessage);
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(type==sendImg){
			Message tMessage = (Message) ois.readObject();
			
			saveGMessage(tMessage,g_id);
			ArrayList<User> gUser;
			try {
				gUser = new Jdbc().getGroupMembers(g_id);
				for(User uu:gUser){
					if(new Service().getState(uu.getId())!=0){
						notifyOther(uu.getId(), acceptGImg);
						notifyOther(uu.getId(), g_id);
						ForwardOther(uu.getId(), tMessage);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			System.out.println("未知类型");
		}
	}
	public void addGroupM() {
		// TODO Auto-generated method stub
		try {
			String m_id = ois.readUTF();
			int g_id = ois.readInt();
			if(new Service().addGroupM(id,m_id,g_id)){
				oos.writeInt(addGroupMSuccess);
				oos.flush();
				oos.writeInt(g_id);
				oos.flush();
				oos.writeObject(new Service().getUser(m_id));
				oos.flush();
			}
			else{
				oos.writeInt(addGroupMError);
				oos.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void delGroup() {
		// TODO Auto-generated method stub
		try {
			int g_id = ois.readInt();
			if(new Service().delGroup(g_id)){
				oos.writeInt(delGroupSuccess);
				oos.flush();
			}
			else{
				oos.writeInt(delGroupError);
				oos.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void delGroupM() {
		// TODO Auto-generated method stub
		try {
			int g_id = ois.readInt();
			String m_id = ois.readUTF();
			if(new Service().delGroupM(g_id,m_id)){
				oos.writeInt(delGroupMSuccess);
				oos.flush();
			}
			else{
				oos.writeInt(delGroupMError);
				oos.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void deleUser() {
		// TODO Auto-generated method stub
		try {
			String e_id = ois.readUTF();
			if(new Service().deleUser(id,e_id)){
				oos.writeInt(deleUserSuccess);
				oos.flush();
			}
			else{
				oos.writeInt(deleUserError);
				oos.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void eUpdate() {
		// TODO Auto-generated method stub
		
		try {
			EnterpriseUser user = (EnterpriseUser) ois.readObject();
			String code = user.getSubject();
			if(ma==null||!ma.verification(code)){
				oos.writeInt(eUpdateError);
				oos.flush();
			}
			else if(new Service().eUpdate(user)){
				oos.writeInt(eUpdateSuccess);
				oos.flush();
				oos.writeObject(new Service().EnterpriseUserLogin(user));
				oos.flush();
			}
			else{
				oos.writeInt(eUpdateError);
				oos.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void eUpdateNo() {
		// TODO Auto-generated method stub
		try {
			EnterpriseUser user = (EnterpriseUser) ois.readObject();
			if(new Service().eUpdate(user)){
				oos.writeInt(eUpdateSuccess);
				oos.flush();
				oos.writeObject(new Service().EnterpriseUserLogin(user));
				oos.flush();
			}
			else{
				oos.writeInt(eUpdateError);
				oos.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void sendeCode() {
		// TODO Auto-generated method stub
		String to;
		try {
			to = ois.readUTF();
			ma = new MailUtil();
			ma.send_mail(to);
			System.out.println("发送验证码成功");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addeUser() {
		// TODO Auto-generated method stub
		try {
			String u_id = ois.readUTF();
			String e_id = ois.readUTF();
			EnterpriseUser user = new Service().addeUser(u_id,e_id);
			if(user.getId()==null){
				oos.writeInt(addeUserError);
				oos.flush();
				System.out.println("吼吼欧鸿哦呵呵");
				oos.writeUTF(user.getPw());
				oos.flush();
			}
			else{
				oos.writeInt(addeUserSuccess);
				oos.flush();
				oos.writeObject(user);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void delUser() {
		// TODO Auto-generated method stub
		try {
			String f_id = ois.readUTF();
			if(new Service().delUser(id,f_id)){
				oos.writeInt(delUserSuccess);
				oos.flush();
			}
			else{
				oos.writeInt(delUserError);
				oos.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addUser() {
		// TODO Auto-generated method stub
		try {
			String f_id = ois.readUTF();
			if(new Service().addUser(id,f_id)){
				oos.writeInt(addUserSuccess);
				oos.flush();
			}
			else{
				oos.writeInt(addUserError);
				oos.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void searchUser() {
		// TODO Auto-generated method stub
		try {
			String se = ois.readUTF();
			OrdinaryUser user = new Service().searchUser(se);
			if(user!=null){
				oos.writeInt(searchUserSuccess);
				oos.flush();
				oos.writeObject(user);
				oos.flush();
			}
			else{
				oos.writeInt(searchUserError);
				oos.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void addGroup() {
		// TODO Auto-generated method stub
		try {
			Group group = (Group) ois.readObject();
			System.out.println(group.getGroupName());
			if(new Service().addGroup(group)){
				oos.writeInt(addGroupSuccess);
				oos.flush();
				oos.writeObject(new Service().getLastGroup());
				
				oos.flush();
			}
			else{
				oos.writeInt(addGroupError);
				oos.flush();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addSession() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		HistorySession session = (HistorySession) ois.readObject();
		if(new Service().addSession(session)){
			oos.writeInt(addSessionSuccess);
			oos.flush();
			oos.writeObject(new Service().getSession(session.getId(),session.getFriend().getId()));
			oos.flush();
		}
		else{
			oos.writeInt(addSessionError);
			oos.flush();
		}
	}
	public void deleBulletin() throws IOException {
		int b_id = ois.readInt();
		String user_id = ois.readUTF();
		if(new Service().delBulletin(b_id)){
			oos.writeInt(deleBulletinSuccess);
			oos.flush();
			oos.writeInt(b_id);
			oos.flush();
			oos.writeUTF(user_id);
			oos.flush();
		}
		else{
			oos.writeInt(deleBulletinError);
			oos.flush();
			oos.writeUTF(user_id);
			oos.flush();
		}
	}
	public void addeBullein() throws ClassNotFoundException, IOException {
		Bulletin bullein = (Bulletin) ois.readObject();
		if(new Service().addEBullein(bullein)){
			oos.writeInt(addeBulleinSuccess);
			oos.flush();
			oos.writeObject(new Service().getLastBulletin());
			oos.flush();
		}
		else{
			oos.writeInt(addeBulleinError);
			oos.flush();
			oos.writeUTF(bullein.getUser_id());
			oos.flush();
		}
	}
	public void userUpdateMessage(){
		try {
			OrdinaryUser user = (OrdinaryUser) ois.readObject();
			String code = ois.readUTF();
			if(ma==null||!ma.verification(code)){
				System.out.println("注册失败，验证码不正确");
				oos.writeInt(userUpdateMessageError);
				oos.flush();
				oos.writeInt(-1);
				oos.flush();
			}
			else if(new Service().userUpdateMessage(user)){
				oos.writeInt(userUpdateMessageSuccess);
				oos.flush();
				oos.writeObject(new Service().getUser(user.getId()));
				oos.flush();
			}
			else{
				oos.writeInt(userUpdateMessageError);
				oos.flush();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void userUpdateMessageNotCode(){
		try {
			OrdinaryUser user = (OrdinaryUser) ois.readObject();
			if(new Service().userUpdateMessage(user)){
				oos.writeInt(userUpdateMessageSuccess);
				oos.flush();
				oos.writeObject(new Service().getUser(user.getId()));
				oos.flush();
			}
			else{
				oos.writeInt(userUpdateMessageError);
				oos.flush();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//没用方法
	public void sendf(TextMessage messgage){
		try {
			oos.writeObject(acceptText);
			oos.writeObject(messgage);
			oos.writeObject(-1);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
					//接收文件保存到服务器本地项目目录wechat下的用户id文件的file中
	public void accpetFile() throws IOException, ClassNotFoundException{
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Message message = (Message) ois.readObject();
		String name = ois.readUTF();
		String path = "wechat\\"+message.getSender().getId()+"\\file";
		int i = 1;
		File ff = new File(path);
		ff.mkdirs();
		while(isRepeatFlie(path, name)){
			name = name.substring(0,name.lastIndexOf("."))+"("+i+")"+name.substring(name.lastIndexOf("."));
			i++;
		}
//		if(){
//			
//		}
		path = path+"\\"+name;
		System.out.println(name);
		FileOutputStream fos = new FileOutputStream(new File(path));
		byte input[] =new byte[1024];
		int len = 0;
		int flen=ois.readInt();
		int l = 0;
		while((len = ois.read(input))!= -1) 
        {
			l+=input.length;
            fos.write(input,0,len);  
        } 
		fos.close();
		System.out.println("服务器接收文件成功"+name);
		ois.readObject();
		saveMessage(message);
//		saveMessage(message.getSender().getId(), "message", message);
		if(new Service().getState(message.getSendee().getId())!=0){
			notifyOtherFile(message, path);
		}
		
		
//		return path;
	}
	public void accpetVFile() throws IOException, ClassNotFoundException{
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Message message =  (Message) ois.readObject();
		String name = ois.readUTF();
		String path = "wechat/"+message.getSender().getId()+"/voice";
		int i = 1;
		File ff = new File(path);
		ff.mkdirs();
		while(isRepeatFlie(path, name)){
			name = name.substring(0,name.lastIndexOf("."))+"("+i+")"+name.substring(name.lastIndexOf("."));
			i++;
		}
//		if(){
//			
//		}
		path = path+"/"+name;
		System.out.println(name);
		FileOutputStream fos = new FileOutputStream(new File(path));
		byte input[] =new byte[1024];
		int len = 0;
		int flen=ois.readInt();
		int l = 0;
		while((len = ois.read(input))!= -1) 
        {
			l+=input.length;
            fos.write(input,0,len);  
        } 
		fos.close();
		System.out.println("服务器接收文件成功"+name);
		ois.readObject();
		saveMessage(message);
//		oos.writeInt(updateVoice);
//		oos.flush();
//		saveMessage(message.getSender().getId(), "message", message);
		notifyOtherVFile(message, path);
//		return path;
	}
				//检测文件夹是不是有重复名字的文件
	public boolean isRepeatFlie(String path,String name){
		File file = new File(path);
		File[] tempFile = file.listFiles();
		for(File f:tempFile){
			if(name.equals(f.getPath().substring(f.getPath().lastIndexOf("\\")+1))){
				return true;
			}
		}
		return false;
	}
			//通知用户线程接收文件信息
	public void notifyOtherFile(Message message,String path){
		MyServer.th.get(message.getSendee().getId());
		try {
			MyServer.th.get(message.getSendee().getId()).sendFile(message,path);
			System.out.println("进来转发文件了");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				message.setContent(path);
//				saveMessage(message.getSendee().getId(), "temp", message);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public void notifyOtherVFile(Message message,String path){
		MyServer.th.get(message.getSendee().getId());
		try {
			MyServer.th.get(message.getSendee().getId()).sendVFile(message,path);
			System.out.println("进来转发文件了");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				message.setContent(path);
//				saveMessage(message.getSendee().getId(), "temp", message);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public void sendFile(Message message,String path) throws IOException{
		oos.writeInt(acceptFile);
		oos.flush();
		String name = message.getContent().substring(message.getContent().lastIndexOf("\\") + 1);
		oos.writeObject(message);
		oos.flush();
		oos.writeUTF(name);
		oos.flush();
		System.out.println(message.getContent());
		System.out.println(name);
		FileInputStream fis = new FileInputStream(new File(path));
		int flength =fis.available();
		oos.writeInt(flength);
		oos.flush();
		System.out.println(flength+"tt");
		byte output[] =new byte[1024];
		int len = 0;
        while ((len = fis.read(output)) != -1) {
            oos.write(output, 0, len);
            oos.flush();
        }
        oos.writeObject(null);
		oos.flush();
	}
	public void sendVFile(Message message,String path) throws IOException{
		oos.writeInt(accpetVoice);
		oos.flush();
		String name = path.substring(path.lastIndexOf("/") + 1);
		System.out.println(name+"这是服务器弄得名字");
		oos.writeObject(message);
		oos.flush();
		oos.writeUTF(name);
		oos.flush();
		System.out.println(message.getContent());
		System.out.println(name);
		FileInputStream fis = new FileInputStream(new File(path));
		int flength =fis.available();
		oos.writeInt(flength);
		oos.flush();
		System.out.println(flength+"tt");
		byte output[] =new byte[1024];
		int len = 0;
        while ((len = fis.read(output)) != -1) {
            oos.write(output, 0, len);
            oos.flush();
        }
        oos.writeObject(null);
		oos.flush();
	}
//	//发送表情包
//	public void sendImg(Message message) throws IOException{
//		oos.writeInt(sendImg);
//		oos.flush();
//		oos.writeObject(message);
//		oos.flush();
//	}
	
	
	//发送语音文件给客户端
	public void sendVoidMessage(VoiceMessage message,String path) throws IOException{
		oos.writeInt(accpetVoice);
		oos.flush();
		String name = path.substring(path.lastIndexOf("\\") + 1);
		oos.writeObject(message);
		oos.flush();
		oos.writeUTF(name);
		oos.flush();
//		System.out.println(message.getPath());
		System.out.println(name);
		FileInputStream fis = new FileInputStream(new File(message.getContenPath()));
		int flength =fis.available();
		oos.writeInt(flength);
		oos.flush();
		System.out.println(flength+"tt");
		byte output[] =new byte[1024];
		int len = 0;
        while ((len = fis.read(output)) != -1) {
            oos.write(output, 0, len);
            oos.flush();
        }
        oos.writeObject(null);
		oos.flush();
	}
	//更新头像,接收头像模块
	public void accpetHeadImg() throws IOException, ClassNotFoundException{
		String user_id = ois.readUTF();
		String Suffix = ois.readUTF();
		System.out.println(user_id);
		System.out.println(Suffix);
		String path = "D:/wechat/headimg";
		new File(path).mkdirs();
		path += "/"+user_id+"."+Suffix;
		FileOutputStream fos = new FileOutputStream(new File(path));
		byte input[] =new byte[1024];
		int len = 0;
		int flen=ois.readInt();
		int l = 0;
		while((len = ois.read(input))!= -1) 
        {
			l+=input.length;
            fos.write(input,0,len);  
        } 
		fos.close();
		System.out.println("头像接收成功");
		ois.readObject();
		new Service().userUpdateHeadImg(user_id, "G:/wechat/headimg"+"/"+user_id+"."+Suffix);
		oos.writeInt(updaHeadImgSuccess);
		String p = "G:/wechat/headimg"+"/"+user_id+"."+Suffix;
		oos.writeUTF(p);
		System.out.println(updaHeadImgSuccess);
		oos.flush();
		updaHeadImg(user_id, Suffix);
	}
	//更新头像，发送头像模块
	public void updaHeadImg(String user_id,String Suffix) throws IOException{
		oos.writeUTF(user_id);
		oos.flush();
		oos.writeUTF(Suffix);
		oos.flush();
		String path = "D:/wechat/headimg/"+user_id+"."+Suffix;
		System.out.println(path);
		FileInputStream fis = new FileInputStream(new File(path));
		int flength =fis.available();
		oos.writeInt(flength);
		oos.flush();
		System.out.println(flength);
		byte output[] =new byte[1024];
		int len = 0;
        while ((len = fis.read(output)) != -1) {
            oos.write(output, 0, len);
            oos.flush();
        }
        oos.writeObject(null);
//        oos.writeInt(-1);
		oos.flush();
		System.out.println("发送给客户端");
	}
	//接收用户发来的信息
	public void accpetMessage() throws IOException, ClassNotFoundException{
		System.out.println("服务端进来中");
		int type = ois.readInt();
		System.out.println(type);
		if(type==sendtesxt){
			Message tMessage = (Message) ois.readObject();
			String sendee_id = tMessage.getSendee().getId();
			if(new Service().getState(sendee_id)!=0){
				notifyOther(sendee_id, acceptText);
				ForwardOther(sendee_id, tMessage);
			}
			saveMessage(tMessage);
		}
		else if(type==sendFile){
			accpetFile();
		}
		else if(type==sendVoice){
			accpetVFile();
		}
		else if(type==sendImg){
			Message tMessage = (Message) ois.readObject();
			String sendee_id = tMessage.getSendee().getId();
			if(new Service().getState(sendee_id)!=0){
				notifyOther(sendee_id, accpetImg);
				ForwardOther(sendee_id, tMessage);
			}
			saveMessage(tMessage);
		}
		else{
			System.out.println("未知类型");
		}
	}
	
	public boolean saveGMessage(Message message,int g_id){
		String sender_id = message.getSender().getId();
		String content = message.getContent();
		int type = message.getType();
		String sendtime = message.getSendTime();
		String sql1 = "insert groupmessages (g_id,sender_id,content,sendtime,type) values (?,?,?,?,?);";
		if(new Jdbc().addGmessage(sql1,g_id,sender_id,content,sendtime,type)){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean saveMessage(Message message){
		String sender_id = message.getSender().getId();
		String sendee_id = message.getSendee().getId();
		String content = message.getContent();
		int type = message.getType();
		int state = 1;
		String sendtime = message.getSendTime();
		if(new Service().getState(sendee_id)==1){
//			if(type==text){
//				notifyOther(sendee_id, acceptText);
//				ForwardOther(sendee_id, message);
//			}
//			else if(type==file){
//				FileMessage fm = (FileMessage) message;
//				notifyOther(sendee_id, acceptFile);
//				notifyOtherFile(fm, path);
//			}
//			else if(type==VoiceMe){
//				FileMessage fm = (FileMessage) message;
//				notifyOther(sendee_id, accpetVoice);
//				notifyOtherFile(fm, path);
//			}
//			else if(type==Picture){
//				notifyOther(sendee_id, accpetImg);
//				ForwardOther(sendee_id, message);
//			}
			return new Service().addmessage(sender_id,sendee_id,content,type,state,sendtime);
		}
		else{
			state = 0;
			return new Service().addmessage(sender_id,sendee_id,content,type,state,sendtime);
		}
	}
	
	
	
	//通知接收信息用户线程接收信息
	public void notifyOther(String u_id,int type){
		MyServer.th.get(u_id);
		try {
			
			ObjectOutputStream oos_ = MyServer.th.get(u_id).oos;
			System.out.println("进来通知线程啦");
			System.out.println(type);
			oos_.writeInt(type);
			System.out.println(type+"已经发送");
			oos_.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//转发信息给其他线程
	public void ForwardOther(String u_id,Message message){
		MyServer.th.get(u_id);
		try {
			ObjectOutputStream oos_ = MyServer.th.get(u_id).oos;
			System.out.println("进来转发线程了");
			oos_.writeObject(message);
			oos_.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		System.out.println(fileSize("D:/test4.txt"));
	}
	//检测文件夹是不是有重复名字的文件
//	public boolean isRepeatFlie(String path,String name){
//	File file = new File(path);
//	File[] tempFile = file.listFiles();
//	for(File f:tempFile){
//		if(name.equals(f.getPath().substring(f.getPath().lastIndexOf("\\")+1))){
//			return true;
//		}
//	}
//	return false;
//	}
//	
//	//检测聊天文件的长度
//	public static int fileSize(String path){
//	File f = new File(path);
//	System.out.println(f);
//	FileInputStream in;
//	ObjectInputStream objIn;
//	ArrayList<Message> ms = new ArrayList<>();
//	try {
//		in = new FileInputStream(f);
//		objIn = new ObjectInputStream(in);
//		ms = (ArrayList<Message>) objIn.readObject();
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (ClassNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	return ms.size();
//	}
//	
//	
//	//获取文件中的聊天记录链表
//	public static ArrayList<Message> getFileMessages(String path){
//	File f = new File(path);
//	System.out.println(f);
//	FileInputStream in;
//	ObjectInputStream objIn;
//	ArrayList<Message> ms = new ArrayList<>();
//	try {
//		in = new FileInputStream(f);
//		objIn = new ObjectInputStream(in);
//		ms = (ArrayList<Message>) objIn.readObject();
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (ClassNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	return ms;
//	}
//	
//	
//	//检测temp文件是不是有文件
//	public void getTempFile(String user_id) throws IOException, ClassNotFoundException{
//	String path = "D:\\wechat\\"+user_id+"\\temp";
//	File f = new File(path);
//	File[] fs = f.listFiles();
//	ArrayList<File> temp = new ArrayList<>();
//	FileInputStream in = null;
//	ObjectInputStream obin = null;
//	for(File file:fs){
//		if(file.isFile()){
//			temp.add(file);
//		}
//	}
//	if(temp.size()!=0){
//		for(File file1:temp){
//			in = new FileInputStream(file1);
//			obin = new ObjectInputStream(in);
//			ArrayList<Message> ms = (ArrayList<Message>) obin.readObject();
//			for(Message m:ms){
//				if(m.getType()==text){
//					oos.writeInt(acceptText);
//					oos.flush();
//					oos.writeObject(m);
//					oos.flush();
//				}
//				else if(m.getType()==VoiceMe){
//					VoiceMessage vm = (VoiceMessage) m;
//					sendVoidMessage(vm, "");
//				}
//				else if(m.getType()==Picture){
//					oos.writeInt(accpetImg);
//					oos.flush();
//					oos.writeObject(m);
//					oos.flush();
//				}
//				else if(m.getType()==file){
//					FileMessage fm = (FileMessage) m;
//					sendFile(fm, fm.getContent());
//				}
//				saveMessage(user_id, "message", m);
//			}
//			file1.delete();
//		}
//	}
//	}
//	
//	
//	//保存聊天记录到本地文件  参数p是保存到的文件名字 temp或者message
//	public boolean saveMessage(String user_id,String p,Message message) throws IOException{
//	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");		//聊天文件命名
//	String path = "D:/wechat/"+user_id+"/"+p;
//	File f = new File(path);
//	String contentPath = path+"/"+df.format(new Date())+".txt";
//	f.mkdirs();
//	File[] tempList = f.listFiles();
//	ArrayList<File> list = new ArrayList<>();
//	if(tempList.length==0){
//		File content = new File(contentPath);
//		content.createNewFile();
//		FileOutputStream out = new FileOutputStream(content);
//		ObjectOutputStream objOut = new ObjectOutputStream(out);
//		ArrayList<Message> ms = new ArrayList<>();
//		if(message.getType()==VoiceMe){
//			VoiceMessage voiceMessage = (VoiceMessage) message;
//			ms.add(voiceMessage);
//		}
//		else{
//			ms.add(message);
//		}
//		objOut.writeObject(ms);
//		objOut.flush();
//		objOut.close();
//		System.out.println("保存成功");
//		return true;
//	}
//	else{
//		for (int i = 0; i < tempList.length; i++) {
//			   if (tempList[i].isFile()) {
//				   list.add(tempList[i]);
//			   }
//		}
//		int num = 0;
//		ArrayList<Message> ms = new ArrayList<>();
//		for(int i=list.size()-1;i>=0;i--){
//			System.out.println("文件中聊天记录的数量："+fileSize(list.get(i).getPath()));
//			if(fileSize(list.get(i).getPath())<20){
//				ms = getFileMessages(list.get(i).getPath());
//				contentPath = list.get(i).getPath();
//				num = 1;
//				break;
//			}
//		}
//		File content = new File(contentPath);
//		content.createNewFile();
//		FileOutputStream out = new FileOutputStream(content);
//		ObjectOutputStream objOut = new ObjectOutputStream(out);
//		if(num==0){
//			if(message.getType()==VoiceMe){
//				VoiceMessage voiceMessage = (VoiceMessage) message;
//				ms.add(voiceMessage);
//			}
//			else{
//				ms.add(message);
//			}
//			objOut.writeObject(ms);
//			objOut.flush();
//			objOut.close();
//			System.out.println("保存成功");
//			return true;
//		}
//		else{
//			if(message.getType()==VoiceMe){
//				VoiceMessage voiceMessage = (VoiceMessage) message;
//				ms.add(voiceMessage);
//			}
//			else{
//				ms.add(message);
//			}
//			objOut.writeObject(ms);
//			objOut.flush();
//			objOut.close();
//			System.out.println("保存成功");
//			return true;
//		}
//	}
//	}

}
