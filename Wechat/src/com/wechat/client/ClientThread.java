package com.wechat.client;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.xml.stream.events.EndDocument;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.wb.swt.SWTResourceManager;

import com.wechat.clientThreadimpl.MessageType;
import com.wechat.clientThreadimpl.Protocal;
import com.wechat.model.Bulletin;
import com.wechat.model.Comment;
import com.wechat.model.EnterpriseUser;
import com.wechat.model.Group;
import com.wechat.model.HistorySession;
import com.wechat.model.Message;
import com.wechat.model.Moment;
import com.wechat.model.OrdinaryUser;
import com.wechat.model.User;
import com.wechat.util.ManageFrdinfo;
import com.wechat.util.ManageOfficalndex;
import com.wechat.util.ManageScrolledComposite;
import com.wechat.util.Managevoice;
import com.wechat.util.SoundRecording;

import view.Index;
import view.Login;
import view.Register;
import view.viewStyle;

public class ClientThread extends Thread implements Protocal,MessageType{
	Socket socket = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	Login login = null;
	Register register = null;
	public String id;
	public ClientThread(){
		//193.112.106.190
		try {
			this.socket = new Socket("127.0.0.1",8888);
			System.out.println("客户端连接123");
			oos = new ObjectOutputStream(this.socket.getOutputStream());
			ois = new ObjectInputStream(this.socket.getInputStream());
			System.out.println(oos+""+ois);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run(){
		try {
//			oos = new ObjectOutputStream(this.socket.getOutputStream());
			System.out.println("流成功");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			int response = 0;
			while(true){
				System.out.println("客户端流杜塞中-----");
//				response = ois.readInt();
				response = ois.readInt();
				System.out.println(response+"test");
				if((response)==-1){
					continue;
				}
				if(response==sendCodeError){
					System.out.println("发送验证码失败");
					String text = "提示：";
					String message = "验证码发送失败";
					register.showMessage(text, message);
//					register = null;
				}
				else if(response==updaHeadImgSuccess){
					System.out.println("正在接收文件");
					accpetHeadImg();
				}
				else if(response==sendCodeSuccess){
					System.out.println("发送验证码成功");
					String text = "提示：";
					String message = "发送验证码成功，请在邮箱查看";
					register.showMessage(text, message);
//					register = null;
				}
				else if(response==sendCodeSuccess){
					System.out.println("发送验证码成功");
					String text = "提示：";
					String message = "发送验证码成功，请在邮箱查看";
					register.showMessage(text, message);
//					register = null;
				}
				else if(response==registerError_code){
					
				}
				else if(response==registerError){
					System.out.println("注册失败");
					register.showMessage("提示：", "注册失败");
				}
				else if(response==registerSuccess){
					System.out.println("注册成功");
					register.showMessage("提示：", "注册成功");
				}
				else if(response==loginError){
					System.out.println("登陆失败");
					response=ois.readInt();
					if(response==notNumber){
						System.out.println("账号不存在");
						String text = "提示：";
						String message = "账号不存在!";
						login.showMessage(text, message);
					}
					else if(response==notPassword){
						System.out.println("密码错误");
						String text = "提示：";
						String message = "密码错误!";
						login.showMessage(text, message);
					}
				}
				else if(response==loginSuccess){
					System.out.println("登陆成功");
					String text = "提示：";
					String message = "登陆成功！";
					OrdinaryUser user = (OrdinaryUser) ois.readObject();
					id = user.getId();
//					for(int i=0;i<user.getSession().size();i++){
//						user.getSession().get(i).setMessages(getMessages(user.getId()
//								, user.getSession().get(i).getFriend().getId()));
//					}
					login.showMessage(text, message);
					login.loginSuccess(user,text, message);
					System.out.println("初始化主页成功！");
				}
				else if(response==eLoginSuccess){
					System.out.println("企业用户登陆成功");
					String text = "提示：";
					String message = "登陆成功,欢迎你企业用户！";
					login.showMessage(text, message);
					EnterpriseUser user = (EnterpriseUser) ois.readObject();
					id = user.getId();
					login.eLoginSuccess(user);
				}
				else if(response==sendMessageError){
					System.out.println("发送信息错误");
				}
				else if(response==sendMessageSuccess){
					System.out.println("发送信息成功");
				}
				else if(response==acceptFile){
					Message message = (Message) ois.readObject();
					accpetFile(message);
					Index.accpetMessage(message);
				}
				else if(response==acceptText){
					System.out.println("接收别人转发的文本信息中-----");
					Message message = (Message) ois.readObject();
//					saveMessage(false,message);
					Index.accpetMessage(message);
				}
				else if(response==accpetImg){
					//接收表情包
					System.out.println("接收别人发的表情包中-----");
					Message message = (Message) ois.readObject();
//					saveMessage(false,message);
					Index.accpetMessage(message);
				}
				else if(response==accpetVoice){
					//接收语音
					System.out.println("接收别人发的语音中-----");
					Message message = (Message) ois.readObject();
					accpetVFile(message);
					Index.accpetMessage(message);
				}
				else if(response==updateSendCodeSuccess){
					String id = ois.readUTF();
					System.out.println("发送验证码成功");
					String text = "提示：";
					String message = "发送验证码成功，请在邮箱查看";
					ManageWechat.getWechat(id).showMessage(text, message);
				}
				else if(response==userUpdateMessageError){
					System.out.println("更新信息失败");
					String text = "提示：";
					String message = "更新信息失败，请检查验证码";
					ManageWechat.getWechat(id).showMessage(text, message);
				}
				else if(response==userUpdateMessageSuccess){
					System.out.println("更新信息成功");
					OrdinaryUser user = (OrdinaryUser) ois.readObject();
					String text = "提示：";
					String message = "更新信息成功";
					ManageWechat.getWechat(id).showMessage(text, message);
					ManageWechat.getWechat(id).user = user;
				}
				else if(response==accpetLastMoment){
					Moment moment = accpetLastMoment();
					ManageFrdinfo.getFrdinfo(moment.getUser_id()).addMoent(moment);
					ManageWechat.getWechat(moment.getUser_id()).user.getAllmonents().add(0,moment);
					ManageFrdinfo.getFrdinfo(id).flush();
				}
//				else if(response==updateVoice){
//					
//				}
				else if(response==addeBulleinSuccess){
					accpetBulletinSuccess();
				}
				else if(response==addeBulleinError){
					addeBulletinError();
				}
				else if(response==deleBulletinSuccess){
					deleBulletinSuccess();
					
//					System.out.println(bullein.getUser_id()+"的主页实例是"+ManageOfficalndex.getSessionSendTime(bullein.getUser_id()));
					//动态添加数据和面板
//					ManageOfficalndex.getSessionSendTime(bullein.getUser_id()).addBullein(bullein);
				}
				else if(response==deleBulletinError){
					deleBulletinError();
				}
				else if(response==addSessionSuccess){
					addSessionSuccess();
				}
				else if(response==addSessionError){
					ManageWechat.getWechat(id).showMessage("提示：", "添加会话失败");
				}
				else if(response==addGroupSuccess){
					addGroupSuccess();
				}
				else if(response==addGroupError){
					ManageWechat.getWechat(id).showMessage("提示：", "添加群聊失败");
				}
				else if(response==searchUserSuccess){
					searchUserSuccess();
				}
				else if(response==searchUserError){
					ManageWechat.getWechat(id).showMessage("提示：", "好友不存在");
				}
				else if(response==addUserSuccess){
					ManageWechat.getWechat(id).addUserSuccess();
				}
				else if(response==addUserError){
					ManageWechat.getWechat(id).showMessage("提示：", "添加好友失败");
				}
				else if(response==delUserSuccess){
					ManageWechat.getWechat(id).delUserSuccess();
				}
				else if(response==delUserError){
					ManageWechat.getWechat(id).showMessage("提示：", "删除好友失败");
				}
				else if(response==addeUserSuccess){
					addeUserSuccess();
				}
				else if(response==eUpdateSuccess){
					eUpdateSuccess();
				}
				else if(response==eUpdateError){
					ManageOfficalndex.getSessionSendTime(id).showMessage("提示：", "修改信息失败");
				}
				else if(response==deleUserSuccess){
					ManageWechat.getWechat(id).deleUserSuccess();
				}
				else if(response==deleUserError){
					ManageWechat.getWechat(id).showMessage("提示：", "取消关注失败");
				}
				else if(response==delGroupMSuccess){
					ManageWechat.getWechat(id).delGroupMSuccess();
				}
				else if(response==delGroupMError){
					ManageWechat.getWechat(id).showMessage("提示：", "退出群聊失败");
				}
				else if(response==delGroupSuccess){
					ManageWechat.getWechat(id).delGroupSuccess();
				}
				else if(response==delGroupError){
					ManageWechat.getWechat(id).showMessage("提示：", "解散群聊失败");
				}
				else if(response==flushfSuccess){
					ArrayList<Moment> moment = (ArrayList<Moment>) ois.readObject();
					ManageFrdinfo.getFrdinfo(id).flushfSuccess(moment);
					
					ManageWechat.getWechat(id).user.setAllmonents(moment);
				}
				else if(response==addGroupMSuccess){
					addGroupMSuccess();
				}
				else if(response==addGroupMError){
					ManageWechat.getWechat(id).showMessage("提示：", "添加群成员失败");
				}
				else if(response==acceptGText){
					int g_id = ois.readInt();
					Message messge = (Message) ois.readObject();
					System.out.println("ggagagagg");
					ManageWechat.getWechat(id).acceptGText(messge,g_id);
					
					
				}
				else if(response==eflushSuccess){
					EnterpriseUser user = (EnterpriseUser) ois.readObject();
					ManageOfficalndex.getSessionSendTime(id).eflushSuccess(user);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void addGroupMSuccess() {
		// TODO Auto-generated method stub
		try {
			int g_id = ois.readInt();
			OrdinaryUser addUser = (OrdinaryUser) ois.readObject();
			ManageWechat.getWechat(id).addGroupMSuccess(addUser,g_id);
		} catch (Exception e) {
			
		}
		
	}
	public void eUpdateSuccess() {
		// TODO Auto-generated method stub
		try {
			EnterpriseUser user_ = (EnterpriseUser) ois.readObject();
			ManageOfficalndex.getSessionSendTime(user_.getId()).eUpdateSuccess(user_);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void addeUserSuccess() {
		// TODO Auto-generated method stub
		try {
			EnterpriseUser user = (EnterpriseUser) ois.readObject();
			System.out.println(user);
			System.out.println(id);
			ManageWechat.getWechat(id).addeUserSuccess(user);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void searchUserSuccess() {
		// TODO Auto-generated method stub
		try {
			OrdinaryUser user = (OrdinaryUser) ois.readObject();
			ManageWechat.getWechat(id).searchUserSuccess(user);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void addGroupSuccess() {
		// TODO Auto-generated method stub
		try {
			Group group = (Group) ois.readObject();
			ManageWechat.getWechat(id).addGroupSuccess(group);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void addSessionSuccess() throws ClassNotFoundException, IOException {
		HistorySession session = (HistorySession) ois.readObject();
		ManageWechat.getWechat(id).addSession(session);
	}
	public void deleBulletinError() throws IOException {
		System.out.println("删除公告失败");
		String text = "提示：";
		String message = "删除公告失败";
		String user_id = ois.readUTF();
		ManageOfficalndex.getSessionSendTime(user_id).showMessage(text, message);
	}
	public void deleBulletinSuccess() throws IOException {
		System.out.println("删除公告成功");
		String text = "提示：";
		String message = "删除公告成功";
		int b_id = ois.readInt();
		String user_id = ois.readUTF();
		ManageOfficalndex.getSessionSendTime(user_id).showMessage(text, message);
		ManageOfficalndex.getSessionSendTime(user_id).delBulletin(b_id);
	}
	public void addeBulletinError() throws IOException {
		System.out.println("发布公告失败");
		String text = "提示：";
		String message = "发布公告失败";
		String user_id = ois.readUTF();
		ManageOfficalndex.getSessionSendTime(user_id).showMessage(text, message);
	}
	public void accpetBulletinSuccess() throws IOException, ClassNotFoundException {
		System.out.println("发布公告成功");
		String text = "提示：";
		String message = "发布公告成功";
		Bulletin bullein = (Bulletin) ois.readObject();
		ManageOfficalndex.getSessionSendTime(bullein.getUser_id()).showMessage(text, message);
//		System.out.println(bullein.getUser_id()+"的主页实例是"+ManageOfficalndex.getSessionSendTime(bullein.getUser_id()));
		//动态添加数据和面板
		ManageOfficalndex.getSessionSendTime(bullein.getUser_id()).addBullein(bullein);
	}
	
	//发送验证码
	public void sendCode(String mail,Register register) throws IOException, ClassNotFoundException{
		oos.writeInt(sendCode);
		System.out.println("test");
		oos.flush();
		oos.writeUTF(mail);
		oos.flush();
		oos.writeInt(-1);
		oos.flush();
		
		this.register = register;
	}
	public void sendCode(String mail,String id) throws IOException, ClassNotFoundException{
		oos.writeInt(sendUpdateCode);
		oos.flush();
		oos.writeUTF(mail);
		oos.flush();
		oos.writeUTF(id);
		oos.flush();
		oos.writeInt(-1);
		oos.flush();
	}
	//普通用户注册
	public void userRegister(OrdinaryUser user,String code) throws IOException, ClassNotFoundException{
		oos.writeInt(userRegister);
		oos.flush();
		oos.writeUTF(code);
		oos.flush();
		oos.writeObject(user);
		oos.flush();
		
	}
	//企业用户注册
	public void EnterpriseUserRegister(EnterpriseUser user,String code) throws IOException, ClassNotFoundException{
		oos.writeInt(eUserRegister);
		oos.flush();
		oos.writeUTF(code);
		oos.flush();
		oos.writeObject(user);
		oos.flush();
		oos.writeObject(-1);
		oos.flush();
	}
	//普通用户登陆
	public void userLogin(User user,Login login) throws IOException, ClassNotFoundException{
		oos.writeInt(userLogin);
		oos.flush();
		oos.writeObject(user);
		oos.flush();
		oos.writeInt(-1);
		oos.flush();
		this.login = login;
	}
	//企业用户登陆
	public void EnterpriseUserLogin(User user,Login login) throws IOException, ClassNotFoundException{
		this.login = login;
		oos.writeInt(eUserLogin);
		oos.flush();
		oos.writeObject(user);
		oos.flush();
		
		
//		if(((int)ois.readObject())==loginSuccess){
//			return (OrdinaryUser) ois.readObject();
//		}
//		else if(((int)ois.readObject())==notNumber){
//			return "账号不存在";
//		}
//		else if(((int)ois.readObject())==notPassword){
//			return "密码错误";
//		}
//		else{
//			return "未知错误";
//		}
	}

	//发送文件
	public void sendFile(Message message) throws IOException{
//		oos.writeInt(sendFile);
//		oos.flush();
		String name = message.getContent().substring(message.getContent().lastIndexOf("\\") + 1);
		System.out.println(name);
		oos.writeObject(message);
		oos.flush();
		oos.writeUTF(name);
		oos.flush();
		System.out.println(message.getContent());
		System.out.println(name);
		FileInputStream fis = new FileInputStream(new File(message.getContent()));
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
//        return ois.readUTF();
	}
	//接收文件
	public void accpetFile(Message message) throws IOException, ClassNotFoundException{
		String name = ois.readUTF();
		String path = "wechat/"+message.getSendee().getId()+"/file";
//		String path = message.getContent().substring(0, message.getContent().lastIndexOf("\\"));
		int i = 1;
		File ff = new File(path);
		ff.mkdirs();
		path = path+"/"+name;
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
//		saveMessage(false, message);
	}
	//接收文件
		public void accpetVFile(Message message) throws IOException, ClassNotFoundException{
			String name = ois.readUTF();
			String path = "wechat\\"+message.getSendee().getId()+"\\voice";
//			String path = message.getContent().substring(0, message.getContent().lastIndexOf("\\"));
			int i = 1;
			File ff = new File(path);
			ff.mkdirs();
//			while(isRepeatFlie(path, name)){
//				name = name.substring(0,name.lastIndexOf("."))+"("+i+")"+name.substring(name.lastIndexOf("."));
//				i++;
//			}
//			if(){
//				
//			}
			path = path+"\\"+name;
			System.out.println(name+"这是客户端接收到名字");
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
//			saveMessage(false, message);
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
	//更新头像
	public void updaHeadImg(String user_id,String path) throws IOException{
		oos.writeInt(updaHeadImg);
		oos.flush();
		String suffix = path.substring(path.lastIndexOf(".") + 1);
		oos.writeUTF(user_id);
		oos.flush();
		oos.writeUTF(suffix);
		oos.flush();
		System.out.println(path);
		FileInputStream fis = new FileInputStream(new File(path));
		int flength =fis.available();
		oos.writeInt(flength);
		oos.flush();
		System.out.println(flength+"updaimg");
		byte output[] =new byte[1024];
		int len = 0;
        while ((len = fis.read(output)) != -1) {
            oos.write(output, 0, len);
            oos.flush();
        }
        oos.writeObject(null);
		oos.flush();
		System.out.println("完成");
	}
	//接收头像保存本地
	public void accpetHeadImg() throws IOException, ClassNotFoundException{
		String p = ois.readUTF();
		String user_id = ois.readUTF();
		String Suffix = ois.readUTF();
		String path = "wechat/headimg";
		new File(path).mkdirs();
		path += "/"+user_id+"."+Suffix;
		System.out.println(path);
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
		ManageWechat.getWechat(user_id).showMessage("提示：", "修改头像成功");
		ManageWechat.getWechat(user_id).setPath(p);
	}
	//普通用户修改数据
	public void userUpdateMessage(OrdinaryUser user,String code) throws IOException{
		oos.writeInt(userUpdateMessage);
		oos.flush();
		oos.writeObject(user);
		oos.flush();
		oos.writeUTF(code);
		oos.flush();
	}
	//不用邮箱验证修改数据
	public void userUpdateMessageNotCode(OrdinaryUser user) throws IOException{
		oos.writeInt(userUpdateMessageNotCode);
		oos.flush();
		oos.writeObject(user);
		oos.flush();
	}
	
	//发送信息
	public void sendMessage(Message message) throws IOException{
		System.out.println(sendMessage+"ooo");
		oos.writeInt(sendMessage);
		oos.flush();
		int sendType = sendtesxt;				//默认是文本信息
		if(message.getType()==text){
//			TextMessage tmessage = (TextMessage) message;
			oos.writeInt(sendType);
			System.out.println("sendtype已经发送");
			oos.flush();
			oos.writeObject(message);
			oos.flush();
//			saveMessage(message);
		}
		else if(message.getType()==file){
			sendType = sendFile;
			oos.writeInt(sendType);
			oos.flush();
			sendFile(message);
		}
		else if(message.getType()==Picture){
			sendType = sendImg;
//			PictureMessage pmessage = (PictureMessage) message;
			oos.writeInt(sendType);
			oos.flush();
			oos.writeObject(message);
			oos.flush();
//			saveMessage(message);
		}
		else if(message.getType()==VoiceMe){
			sendType = sendVoice;
			oos.writeInt(sendType);
			oos.flush();
			sendFile(message);
		}
		
	}
	public void sendGMessage(Message message,int id) throws IOException{
		oos.writeInt(sendGMessage);
		oos.flush();
		oos.writeInt(id);
		oos.flush();
		int sendType = sendtesxt;				//默认是文本信息
		if(message.getType()==text){
//			TextMessage tmessage = (TextMessage) message;
			oos.writeInt(sendType);
			System.out.println("sendtype已经发送");
			oos.flush();
			oos.writeObject(message);
			oos.flush();
//			saveMessage(message);
		}
		else if(message.getType()==file){
			sendType = sendFile;
			oos.writeInt(sendType);
			oos.flush();
			sendFile(message);
		}
		else if(message.getType()==Picture){
			sendType = sendImg;
//			PictureMessage pmessage = (PictureMessage) message;
			oos.writeInt(sendType);
			oos.flush();
			oos.writeObject(message);
			oos.flush();
//			saveMessage(message);
		}
		else if(message.getType()==VoiceMe){
			sendType = sendVoice;
			oos.writeInt(sendType);
			oos.flush();
			sendFile(message);
		}
		
	}
	//发送群消息
	public void sendGroupMessage(Message message) throws IOException{
		oos.writeInt(sendGroupMessage);
		oos.flush();
		int sendType = sendtesxt;				//默认是文本信息
		if(message.getType()==text){
//			TextMessage tmessage = (TextMessage) message;
			oos.writeInt(sendType);
			System.out.println("sendtype已经发送");
			oos.flush();
			oos.writeObject(message);
			oos.flush();
//			saveMessage(message);
		}
		else if(message.getType()==file){
			sendType = sendFile;
			oos.writeInt(sendType);
			oos.flush();
			sendFile(message);
		}
		else if(message.getType()==Picture){
			sendType = sendImg;
//			PictureMessage pmessage = (PictureMessage) message;
			oos.writeInt(sendType);
			oos.flush();
			oos.writeObject(message);
			oos.flush();
//			saveMessage(message);
		}
		else if(message.getType()==VoiceMe){
			sendType = sendVoice;
			oos.writeInt(sendType);
			oos.flush();
			oos.writeObject(message);
			oos.flush();
		}
	}

		//发送表情包
		public void sendImg(Message message) throws IOException{
			oos.writeInt(sendImg);
			oos.flush();
			oos.writeObject(message);
			oos.flush();
		}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public static void main(String[] args) {
//		ClientThread ct = new ClientThread();
//		ct.start();
//		try {
//			FileMessage f = new FileMessage(new User("123", null, null, "123")
//					, new User("123123", null, null, "123123"), "123123", "C:\\Users\\wuzhuhao\\Desktop\\stu123.jpg");
//			f.setType(file);
//			ct.sendMessage(f);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	public void deleteLike(int m_id) throws IOException{
		oos.writeInt(delLike);
		oos.flush();
		oos.writeInt(m_id);
		oos.flush();
	}
	public void addLike(int m_id) throws IOException{
		oos.writeInt(addLike);
		oos.flush();
		oos.writeInt(m_id);
		oos.flush();
	}
	public void addComment(Comment comment) throws IOException{
		oos.writeInt(addComment);
		oos.flush();
		oos.writeObject(comment);
		oos.flush();
	}
	public void delComment(int c_id) throws IOException{
		oos.writeInt(delComment);
		oos.flush();
		oos.writeInt(c_id);
		oos.flush();
	}
	public void addMement(Moment moment) throws IOException, ClassNotFoundException{
		oos.writeInt(addMoment);
		oos.flush();
		oos.writeObject(moment);
		oos.flush();
	}
	public Moment accpetLastMoment() throws ClassNotFoundException, IOException{
		return (Moment) ois.readObject();
	}
	public void delMement(int m_id) throws IOException{
		oos.writeInt(delMoment);
		oos.flush();
		oos.writeInt(m_id);
		oos.flush();
	}
	public void addBullein(Bulletin bullein) throws IOException{
		oos.writeInt(addeBullein);
		oos.flush();
		oos.writeObject(bullein);
		oos.flush();
	}
	public void delBulletin(int b_id,String user_id) throws IOException{
		oos.writeInt(deleBulletin);
		oos.flush();
		oos.writeInt(b_id);
		oos.flush();
		oos.writeUTF(user_id);
		oos.flush();
	}
	public void addSession(HistorySession session) throws IOException {
		oos.writeInt(addSession);
		oos.flush();
		oos.writeObject(session);
		oos.flush();
		
	}
	public void addGroup(Group group) throws IOException {
		// TODO Auto-generated method stub
		oos.writeInt(addGroup);
		oos.flush();
		oos.writeObject(group);
		oos.flush();
	}
	public void searchUser(String se) {
		// TODO Auto-generated method stub
		try {
			oos.writeInt(searchUser);
			oos.flush();
			oos.writeUTF(se);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addUser(String id) {
		// TODO Auto-generated method stub
		try {
			oos.writeInt(addUser);
			oos.flush();
			oos.writeUTF(id);
			oos.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void delUser(String f_id) {
		// TODO Auto-generated method stub
		try {
			oos.writeInt(delUser);
			oos.flush();
			oos.writeUTF(f_id);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addeUser(String u_id,String id) {
		try {
			System.out.println("口令是"+addeUser);
			oos.writeInt(addeUser);
			oos.flush();
			oos.writeUTF(u_id);
			oos.flush();
			oos.writeUTF(id);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendeCode(String mail) {
		// TODO Auto-generated method stub
		try {
			oos.writeInt(sendeCode);
			oos.flush();
			oos.writeUTF(mail);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void eUpdateNo(EnterpriseUser user) {
		// TODO Auto-generated method stub
		try {
			oos.writeInt(eUpdateNo);
			oos.flush();
			oos.writeObject(user);
			oos.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void eUpdate(EnterpriseUser temp) {
		// TODO Auto-generated method stub
		try {
			oos.writeInt(eUpdate);
			oos.flush();
			oos.writeObject(temp);
			oos.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void deleUser(String e_id) {
		// TODO Auto-generated method stub
		try {
			oos.writeInt(deleUser);
			oos.flush();
			oos.writeUTF(e_id);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void delGroupM(int g_id,String m_id){
		try {
			oos.writeInt(delGroupM);
			oos.flush();
			oos.writeInt(g_id);
			oos.flush();
			oos.writeUTF(m_id);
			oos.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//保存聊天记录到本地
//	public boolean saveMessage(int type,Message message){
//		try {
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//			String path = "wechat/"+message.getSender().getId()+"/messages/"+
//							message.getSendee().getId();
//			File f = new File(path);
//			
//			f.mkdirs();
//			System.out.println(f.getAbsolutePath());
//			File[] tempList = f.listFiles();
//			ObjectOutputStream objOut = null;
//			if(fileSize(tempList[0].getPath())<=18){
//				if(tempList[0].length()<1){
//					objOut = new ObjectOutputStream(new FileOutputStream(tempList[0],true));
//				}
//				else{
//					objOut = new MyObjectOutputStream(new FileOutputStream(tempList[0],true));
//				}
//			}
//			else{
//				String path1 = path+"/"+df.format(new Date())+".txt";
//				File f1 = new File(path1);
//				System.out.println(f1);
//				System.out.println(f1.createNewFile());
//				if(f1.length()<1){
//					objOut = new ObjectOutputStream(new FileOutputStream(f1,true));
//				}
//				else{
//					objOut = new MyObjectOutputStream(new FileOutputStream(f1,true));
//				}
//			}
//			if(type==text){
//				TextMessage text = (TextMessage) message;
//				objOut.writeObject(type);
//				objOut.writeObject(text);
//			}
//			else if(type==Picture){
//				PictureMessage pictureMessage = (PictureMessage) message;
//				objOut.writeObject(type);
//				objOut.writeObject(pictureMessage);
//			}
//			else if(type==Enterprise){
//				EnterpriseMessage enterpriseMessage = (EnterpriseMessage) message;
//				objOut.writeObject(type);
//				objOut.writeObject(enterpriseMessage);
//			}
//			else if(type==VoiceMe){
//				VoiceMessage voiceMessage = (VoiceMessage) message;
//				objOut.writeObject(type);
//				objOut.writeObject(voiceMessage);
//			}
//			else{
//				return false;
//			}
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//	//检测聊天文件的长度
//	public int fileSize(String path){
//		File f = new File(path);
//		FileInputStream in;
//		@SuppressWarnings("resource")
//		ObjectInputStream objIn;
//		int i = 0;
//		try {
//			in = new FileInputStream(f);
//			objIn = new ObjectInputStream(in);
//			Object a = null;
//			while((a=objIn.readObject())!=null){
//				i++;
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return i;
//	}
	//保存别人发送的信息过来聊天记录到本地文件
//	public boolean saveMessage(Boolean boo,Message message) throws IOException{
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");		//聊天文件命名
//		String path = "wechat/"+message.getSender().getId()+"/messages/"+
//				message.getSendee().getId();
//		if(!boo){
//			path = "wechat/"+message.getSendee().getId()+"/messages/"+
//					message.getSender().getId();
//		}
//		File f = new File(path);
//		f.mkdirs();
////		String path = "D:/wechat/"+user_id+"/"+p;
////		File f = new File(path);
//		String contentPath = path+"/"+df.format(new Date())+".txt";
//		f.mkdirs();
//		File[] tempList = f.listFiles();
//		ArrayList<File> list = new ArrayList<>();
//		if(tempList.length==0){
//			File content = new File(contentPath);
//			content.createNewFile();
//			FileOutputStream out = new FileOutputStream(content);
//			ObjectOutputStream objOut = new ObjectOutputStream(out);
//			ArrayList<Message> ms = new ArrayList<>();
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
//			for (int i = 0; i < tempList.length; i++) {
//				   if (tempList[i].isFile()) {
//					   list.add(tempList[i]);
//				   }
//			}
//			int num = 0;
//			ArrayList<Message> ms = new ArrayList<>();
//			for(File file:list){
//				if(fileSizes(file.getPath())<20){
//					ms = getFileMessages(file.getPath());
//					contentPath = file.getPath();
//					num = 1;
//					break;
//				}
//			}
//			File content = new File(contentPath);
//			content.createNewFile();
//			FileOutputStream out = new FileOutputStream(content);
//			ObjectOutputStream objOut = new ObjectOutputStream(out);
//			if(num==0){
//				if(message.getType()==VoiceMe){
//					VoiceMessage voiceMessage = (VoiceMessage) message;
//					ms.add(voiceMessage);
//				}
//				else{
//					ms.add(message);
//				}
//				objOut.writeObject(ms);
//				objOut.flush();
//				objOut.close();
//				System.out.println("保存成功");
//				return true;
//			}
//			else{
//				if(message.getType()==VoiceMe){
//					VoiceMessage voiceMessage = (VoiceMessage) message;
//					ms.add(voiceMessage);
//				}
//				else{
//					ms.add(message);
//				}
//				objOut.writeObject(ms);
//				objOut.flush();
//				objOut.close();
//				System.out.println("保存成功");
//				return true;
//			}
//		}
//	}
//保存聊天记录到本地文件
//public boolean saveMessage(Message message) throws IOException{
//	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");		//聊天文件命名
//	String path = "wechat/"+message.getSender().getId()+"/messages/"+
//			message.getSendee().getId();
//	File f = new File(path);
//	f.mkdirs();
////	String path = "D:/wechat/"+user_id+"/"+p;
////	File f = new File(path);
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
//		for(File file:list){
//			if(fileSizes(file.getPath())<20){
//				ms = getFileMessages(file.getPath());
//				contentPath = file.getPath();
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
//}
//检测聊天文件的长度
//	public static int fileSizes(String path){
//		File f = new File(path);
//		System.out.println(f);
//		FileInputStream in;
//		ObjectInputStream objIn;
//		ArrayList<Message> ms = new ArrayList<>();
//		try {
//			in = new FileInputStream(f);
//			objIn = new ObjectInputStream(in);
//			ms = (ArrayList<Message>) objIn.readObject();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ms.size();
//	}
//	//获取文件中的聊天记录链表
//	public static ArrayList<Message> getFileMessages(String path){
//		File f = new File(path);
//		System.out.println(f);
//		FileInputStream in;
//		ObjectInputStream objIn;
//		ArrayList<Message> ms = new ArrayList<>();
//		try {
//			in = new FileInputStream(f);
//			objIn = new ObjectInputStream(in);
//			ms = (ArrayList<Message>) objIn.readObject();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ms;
//	}
//	//根据会话好友id获取本地聊天记录
//	public ArrayList<Message> getMessages(String user_id,String friend_id){
//		FileInputStream in;
//		ObjectInputStream objIn;
//		ArrayList<Message> messages = new ArrayList<>();
//		String path = "wechat/"+user_id+"/messages/"+
//				friend_id;
//		File f = new File(path);
//		f.mkdirs();
//		File[] tempList = f.listFiles();
//		ArrayList<File> list = new ArrayList<>();
//		if(tempList.length!=0){
//			for(File file:tempList){
//				if(file.isFile()){
//					list.add(file);
//				}
//			}
//		}
//		if(list.size()!=0){
//			for(File file:list){
//				try {
//					in = new FileInputStream(file.getPath());
//					objIn = new ObjectInputStream(in);
//					ArrayList<Message> temp = (ArrayList<Message>) objIn.readObject();
//					messages.addAll(temp);
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		return messages;
//	}
	public void delGroup(int groupId) {
		// TODO Auto-generated method stub
		try {
			oos.writeInt(delGroup);
			oos.flush();
			oos.writeInt(groupId);
			oos.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void flush() {
		// TODO Auto-generated method stub
		try {
			oos.writeInt(flushf);
			oos.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void addGroupM(String m_id,int g_id) {
		// TODO Auto-generated method stub
		try {
			oos.writeInt(addGroupM);
			oos.writeUTF(m_id);
			oos.flush();
			oos.writeInt(g_id);
			oos.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void eflush(EnterpriseUser user) {
		try {
			oos.writeInt(eflush);
			oos.flush();
			oos.writeObject(user);
			oos.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
