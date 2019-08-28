package view;

import java.awt.FileDialog;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.eclipse.core.internal.runtime.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.ibm.icu.impl.StringRange;
import com.wechat.client.ClientThread;
import com.wechat.client.ManageClientThread;
import com.wechat.client.ManageWechat;
import com.wechat.clientThreadimpl.MessageType;
import com.wechat.model.Bulletin;
import com.wechat.model.Comment;
import com.wechat.model.EnterpriseUser;
import com.wechat.model.Group;
import com.wechat.model.HistorySession;
import com.wechat.model.Message;
import com.wechat.model.Moment;
import com.wechat.model.OrdinaryUser;
import com.wechat.model.User;
import com.wechat.util.ManageChating;
import com.wechat.util.ManageFrdinfo;
import com.wechat.util.ManageGSessionMessage;
import com.wechat.util.ManageGSessionName;
import com.wechat.util.ManageGSessionSendTime;
import com.wechat.util.ManageGroupSession;
import com.wechat.util.ManageScrolledComposite;
import com.wechat.util.ManageSession;
import com.wechat.util.ManageSessionImg;
import com.wechat.util.ManageSessionMessage;
import com.wechat.util.ManageSessionName;
import com.wechat.util.ManageSessionSendTime;
import com.wechat.util.Managevoice;
import com.wechat.util.SoundRecording;

public class viewStyle implements MessageType{
	static org.eclipse.swt.graphics.Image ghead = SWTResourceManager.getImage(Index.class, "/img/group.png");
	public static org.eclipse.swt.graphics.Image myhead = SWTResourceManager.
			getImage(Index.class, "wechat/headimg/123.jpg");
	static String nowtime;
	public static HashMap<String, Composite> jm = new HashMap<>();
	public static HashMap<Integer, Composite> jm1 = new HashMap<>();
	public static HashMap<String, Composite> officalss = new HashMap<>();
	public static HashMap<Integer, Composite> fgroupCom = new HashMap<>();
	public static HashMap<Integer, Composite> groupInfoss = new HashMap<>();
	public static HashMap<String, Composite> onefrdlist = new HashMap<>();
	public static HashMap<Integer, Composite> GroupPeopless = new HashMap<>();
	public static HashMap<Integer, ScrolledComposite> manageScrolledComposite = new HashMap<>();
	public static HashMap<Integer, Composite> chatings = new HashMap<>();
	public static HashMap<Integer, Composite> ones = new HashMap<>();

	public static void setbgview(Shell shell) {
		Listener listener = new Listener() { // 无标题可移动
			int startX, startY;

			public void handleEvent(Event e) {
				// TODO Auto-generated method stub
				if (e.type == SWT.MouseDown && e.button == 1) {
					startX = e.x;
					startY = e.y;
				}
				if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
					Point p = shell.toDisplay(e.x, e.y);
					p.x -= startX;
					p.y -= startY;
					shell.setLocation(p);
				}
			}
		};
		shell.addListener(SWT.MouseDown, listener);
		shell.addListener(SWT.MouseMove, listener);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT); // label透明条件
		
		int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;								//居中
		int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
		int shellH = shell.getBounds().height;
		int shellW = shell.getBounds().width;
		if(shellH > screenH)
		shellH = screenH;
		if(shellW > screenW)
		shellW = screenW;
		shell.setLocation(((screenW - shellW) / 2), ((screenH - shellH) / 2));
		
		
	}

	public static void setTextRGB(org.eclipse.swt.widgets.Text text, int r, int g, int b) {
		((Control) text).setBackground(SWTResourceManager.getColor(r, g, b));// RGB颜色
	}

	public static String getTime() {
		Date t = new Date();
		int o = t.getHours();
		int m = t.getMinutes();
		nowtime = o + ":" + m;
		return nowtime;
	}

	public static void setLabelBg(Label label, String picid) {

		label.setBackgroundImage(SWTResourceManager.getImage(Login.class, "/img/" + picid + ".png"));// 设置label背景
	}
	public static void updeLabelBg(Label label, String picid) {
		label.setImage(SWTResourceManager.getImage(picid));
//		label.setBackgroundImage(SWTResourceManager.getImage(Login.class, picid));// 设置label背景
	}


	//聊天会话添加
	public static void addchatone(Shell Wechat, ScrolledComposite chatList, Composite chat,Label head,Composite chatcomposite,
			org.eclipse.swt.graphics.Image chatinghead, String chatingname, String ltime
			, String lmsg,OrdinaryUser user,String friend_id) {
		Composite chatone = new Composite(chatcomposite, SWT.NONE); // 聊天列表+1
		//添加会话到会话管理类
		ManageSession.addSession(friend_id, chatone);
		
		chatone.setLayoutData(new RowData(234, 65));
		Label chathead = new Label(chatone, SWT.NONE);
		chathead.setBounds(10, 10, 44, 44);
		chathead.setImage(chatinghead);
		//添加会话头像到会话管理类
		ManageSessionImg.addSessionsImg(friend_id, chathead);
		Label chatname = new Label(chatone, SWT.NONE);
		chatname.setText(chatingname);
		chatname.setBounds(60, 10, 95, 20);
		//添加会话用户名字到管理类
		ManageSessionName.addSessionsName(friend_id, chatname);

		Label lastmsg = new Label(chatone, SWT.NONE);
		lastmsg.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		lastmsg.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lastmsg.setBounds(60, 36, 152, 20);
		lastmsg.setText(lmsg);
		//添加会话最近信息到管理类
		ManageSessionMessage.addSessionsMessage(friend_id, lastmsg);

		Label lasttime = new Label(chatone, SWT.NONE);
		lasttime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		lasttime.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lasttime.setBounds(161, 10, 51, 20);
		lasttime.setText(ltime);
		//添加最近消息组件到管理类
		ManageSessionSendTime.addSessionSendTime(friend_id, lasttime);
		
		chathead.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {				
				//显示群聊界面
				if (chatinghead.equals(ghead)) {
					ManageWechat.getWechat(user.getId()).ViewGroupchat(chatingname,friend_id);				
				}
				else{
					newchating(Wechat, chat, head, chatingname,user,friend_id);	
					System.out.println("成功点击会话头像asdasd");
				}

			}
		});
		chatList.setMinHeight(chatList.getMinHeight()+68);
		chatList.layout();
		chatcomposite.layout(); //// !!!!!
	}

	public static void addfrdone
	(ScrolledComposite firendList,Composite fcomposite, org.eclipse.swt.graphics.Image frdhead1
			, User friend) {
		// 好友列表加一
		Composite onefrd = new Composite(fcomposite, SWT.NONE);
		onefrd.setLayoutData(new RowData(243, 64));
		onefrdlist.put(friend.getId(), onefrd);

		Label frdhead = new Label(onefrd, SWT.NONE); // 头像
		frdhead.setBounds(10, 10, 44, 44);
		frdhead.setBackgroundImage(frdhead1);

		Label frdname = new Label(onefrd, SWT.NONE); // 昵称
		frdname.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		frdname.setText(friend.getName());
		frdname.setBounds(87, 20, 100, 34);

		frdname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Index.viewfrdinfo(frdhead1, friend.getName(), frdhead1, "", "", "", "", "");
			}
		});
		frdhead.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.println(friend.getId());
				System.out.println(((OrdinaryUser)friend).getRegion());
				System.out.println(((OrdinaryUser)friend).getMessage()==null);
				Index.viewfrdinfo(frdhead1, friend.getName(), frdhead1, friend.getId(), ((OrdinaryUser)friend).getMail()
						, (((OrdinaryUser)friend).getRegion()==null)?"":((OrdinaryUser)friend).getRegion().split("-")[0]
						, (((OrdinaryUser)friend).getRegion()==null)?"":((OrdinaryUser)friend).getRegion().split("-")[1]
								, (((OrdinaryUser)friend).getMessage()==null)?"":((OrdinaryUser)friend).getMessage());

			}
		});
		firendList.setMinHeight(firendList.getMinHeight()+64);
		firendList.layout();
		fcomposite.layout();
	}

	public static void AddGroupOne(ScrolledComposite firendList,Composite Group, Composite fcomposite, org.eclipse.swt.graphics.Image ghead,
			Group group,String user_id) {
		// 好友列表群聊+1

		Composite onefrd = new Composite(fcomposite, SWT.NONE);
		fgroupCom.put(group.getGroupId(), onefrd);
		onefrd.setLayoutData(new RowData(243, 64));

		Label frdhead = new Label(onefrd, SWT.NONE); // 头像
		frdhead.setBounds(10, 10, 44, 44);
		frdhead.setBackgroundImage(ghead);

		Label frdname = new Label(onefrd, SWT.NONE); // 昵称
		frdname.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		frdname.setText(group.getGroupName());
		frdname.setBounds(87, 20, 100, 34);

		frdname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Index.hideview();
				ViewGroupInfo(Group, group,user_id);
				Group.setVisible(true);
			}
		});
		frdhead.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Index.hideview();
				ViewGroupInfo(Group, group,user_id);
				Group.setVisible(true);
				
							
			}
		});
		firendList.setMinHeight(firendList.getMinHeight()+64);
		firendList.layout();
		fcomposite.layout();
		
	}

	private static int frdinfo = 0; // 判断朋友圈是否打开

	public static int getFrdinfo() {
		return frdinfo;
	}

	public static void setFrdinfo(int frdinfo) {
		viewStyle.frdinfo = frdinfo;
	}

	public static boolean frdinfo() {
		if (getFrdinfo() == 1) {
			return true;
		} else {
			return false;
		}

	}
	
	//聊天界面


	public static void newchating(Shell Wechat, Composite chat, Label head
			, String chatingman,OrdinaryUser user,String friend_id) { // 新的聊天界面
		Composite chat1 = new Composite(chat, SWT.NONE);
		Set<String> names = jm.keySet();
		int i = 0;
		for (String n : names) {
			if (n.equals(friend_id)) {
				chat1 = jm.get(n);
				break;
			}
			i++;
		}
		if (i == jm.size()) {
			jm.put(friend_id, chat1);
		}
		else{
			chat.layout();
			chat.setVisible(true);
			chat1.setVisible(true);
			return;
		}
		Label chatingName = new Label(chat1, SWT.NONE);

		chatingName.setText(chatingman);
		chatingName.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		chatingName.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		chatingName.setBounds(29, -1, 139, 35);

		ScrolledComposite chatscr = new ScrolledComposite(chat1, SWT.H_SCROLL | SWT.V_SCROLL); // 聊天信息显示
		chatscr.setBounds(29, 40, 832, 532);
		chatscr.setExpandHorizontal(true);
		chatscr.setExpandVertical(true);
		ManageScrolledComposite.addManageScrolledComposite(friend_id, chatscr);

		Composite chating = new Composite(chatscr, SWT.NONE);
		chating.setBackground(SWTResourceManager.getColor(245, 245, 245));
		chating.setLayout(new RowLayout(SWT.VERTICAL));
		chatscr.setContent(chating);
		chatscr.setMinSize(chating.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		chatscr.setMinSize(new Point(0, 0));
		//添加聊天信息显示框进管理类
		ManageChating.addChating(friend_id, chating);
		Composite edittext = new Composite(chat1, SWT.NONE);
		edittext.setBounds(0, 575, 891, 143);
		edittext.setBackground(SWTResourceManager.getColor(255, 255, 255));

		Text text = new Text(edittext, SWT.WRAP);
		text.setBounds(30, 35, 810, 77);

		Label e1 = new Label(edittext, SWT.NONE);
		e1.setBounds(30, 7, 33, 20);
		setLabelBg(e1, "e1c");
		e1.setSize(32, 32);

		Label e2 = new Label(edittext, SWT.NONE);
		e2.setBounds(80, 7, 33, 20);
		setLabelBg(e2, "e2c");
		e2.setSize(31, 32);

		Label e3 = new Label(edittext, SWT.NONE);
		e3.setBounds(130, 7, 33, 20);
		setLabelBg(e3, "e3c");
		e3.setSize(31, 32);

		org.eclipse.swt.graphics.Image e = SWTResourceManager.getImage(Index.class, "/img/e1.png"); // 表情
		org.eclipse.swt.graphics.Image ee = SWTResourceManager.getImage(Index.class, "/img/e2.png");
		org.eclipse.swt.graphics.Image eee = SWTResourceManager.getImage(Index.class, "/img/e3.png");
		org.eclipse.swt.graphics.Image myheadimg = head.getBackgroundImage();
		
		for(HistorySession session:user.getSession()){
			if(friend_id.equals(session.getFriend().getId())){
				for(Message m:session.getMessages()){
					//显示好友发来的信息
					if(m.getSender().getId().equals(friend_id)){
						if(m.getType()==file){
//							Message fm = (Message) m;
//							NewMyfile(chatscr,chating, myheadimg, fm.getSendTime(), fm.getPath());
							BackFmsg(chatscr,chating, myheadimg, m.getSendTime()
									, "wechat\\"+user.getId()+"\\file\\"+m.getContent().substring(m.getContent().lastIndexOf("\\")+1));
						}
						else if(m.getType()==Picture){
							//显示表情包
							NewBackEmsg(chatscr,
									chating
				    				, SWTResourceManager.getImage(m.getSender().getHeadimg())
				    				, m.getSendTime()
				    				, SWTResourceManager.getImage(Index.class, m.getContent()));
						}
						else if(m.getType()==VoiceMe){
							//显示语音
							NewbackVmsg(chatscr, chating, myheadimg, "", m);
						}
						else{
							Newbackmsg(chatscr
									,chating
									, SWTResourceManager.getImage(m.getSender().getHeadimg())
									, m.getSendTime()
									, m.getContent());
						}
					}
					//显示自己发给好友的信息
					else{
						if(m.getType()==file){
							//显示文件
//							FileMessage fm = (FileMessage) m;
							NewMyfile(chatscr,chating, myheadimg, m.getSendTime(), m.getContent());
						}
						else if(m.getType()==Picture){
							//显示表情包
							NewMyEmsg(chatscr
									,chating
				    				, SWTResourceManager.getImage(user.getHeadimg())
				    				, m.getSendTime()
				    				, SWTResourceManager.getImage(Index.class, m.getContent()));
						}
						else if(m.getType()==VoiceMe){
							//显示语音
							NewMyVmsg(chatscr, chating, myheadimg, "", m);
						}
						else{
							//显示文本
							NewMyMsg(chatscr
									,chating
									, SWTResourceManager.getImage(user.getHeadimg())
									, m.getSendTime()
									, m.getContent());
						}
//						(chatscr,chating, SWTResourceManager.getImage(user.getHeadimg()), sendTime, msg);
					}
				}
				
			}
		}

		//发送表情包
		e1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				User u = null;
				for(User uu:user.getFriends()){
					if(uu.getId().equals(friend_id)){
						u = uu;
					}
				}
				String sendTime = getNewTime();
				Message m = new Message(user, u, sendTime);
				m.setContent("/img/e1.png");
				m.setType(Picture);
				viewStyle.NewMyEmsg(chatscr,chating, SWTResourceManager.getImage(user.getHeadimg()), sendTime, e);
				try {
					ManageClientThread.getClientConServerThread(user.getId()).sendMessage(m);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			public String getNewTime() {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				return sendTime;
			}
		});
		e2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				User u = null;
				for(User uu:user.getFriends()){
					if(uu.getId().equals(friend_id)){
						u = uu;
					}
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				Message m = new Message(user, u, sendTime);
				m.setContent("/img/e2.png");
				m.setType(Picture);
				viewStyle.NewMyEmsg(chatscr,chating, SWTResourceManager.getImage(user.getHeadimg()), sendTime, ee);
				try {
					ManageClientThread.getClientConServerThread(user.getId()).sendMessage(m);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		e3.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				User u = null;
				for(User uu:user.getFriends()){
					if(uu.getId().equals(friend_id)){
						u = uu;
					}
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				Message m = new Message(user, u, sendTime);
				m.setContent("/img/e3.png");
				m.setType(Picture);
				viewStyle.NewMyEmsg(chatscr,chating, SWTResourceManager.getImage(user.getHeadimg()), sendTime, eee);
				try {
					ManageClientThread.getClientConServerThread(user.getId()).sendMessage(m);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
//		OSoundRecording sr = null;
		Label voice = new Label(edittext, SWT.NONE);															//语音按钮
		voice.setImage(SWTResourceManager.getImage(Index.class,"/img/v.png"));
		voice.setBounds(180, 5, 33, 29);
		voice.addMouseListener(new MouseAdapter() {
			SoundRecording sr = new SoundRecording(user.getId());
			public void mouseDown(MouseEvent e){
				
				System.out.println("录音啦");
				sr.capture();
				voice.setImage(SWTResourceManager.getImage(Index.class,"/img/v-down.png"));//图片再说
			}
			public void mouseUp(MouseEvent e){
				voice.setImage(SWTResourceManager.getImage(Index.class,"/img/v.png"));
				sr.stop();
				String path = sr.save();
				System.out.println(path);
				System.out.println("结束录音");
				User u = null;
				for(User uu:user.getFriends()){
					if(uu.getId().equals(friend_id)){
						u = uu;
					}
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				Message message = new Message(user, u, sendTime, VoiceMe, path);
				NewMyVmsg(chatscr, chating, myheadimg, "", message);
				try {
//					sr.play1(path);
					ManageClientThread.getClientConServerThread(user.getId()).sendMessage(message);
//					sr = new SoundRecording(user.getId());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		Managevoice.addvoice(friend_id, voice);
		
		Label snedfile = new Label(edittext, SWT.NONE);
		snedfile.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
//		snedfile.setBounds(180, 12, 60, 20);
		snedfile.setBounds(227, 9, 60, 20);
		snedfile.setText("发送文件");
		snedfile.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				org.eclipse.swt.widgets.FileDialog a = new org.eclipse.swt.widgets.FileDialog(Wechat);
				a.setText("选择文件");
				a.open();
				if (a.getFilterPath() == "" + "") {
				} else{
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
					String sendTime = df.format(new Date());
					Message fm = new Message(user, new User(friend_id, null, null, null), sendTime
							, file, a.getFilterPath()+"\\"+a.getFileName());
//					Message fm = new Message(user, new User(friend_id, null, null, null)
//							, sendTime, a.getFilterPath()+"\\"+a.getFileName());	
					System.out.println(a.getFilterPath()+"\\"+a.getFileName());
					fm.setType(file);
					try {
						ManageClientThread.getClientConServerThread(user.getId()).sendMessage(fm);
						NewMyfile(chatscr, chating, myheadimg, fm.getSendTime(), fm.getContent());
//						ManageClientThread.getClientConServerThread(user.getId()).saveMessage(fm);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//					NewMyfile(chating, myheadimg, nowtime, a.getFileName());
			}
		});

		Button subsend = new Button(edittext, SWT.NONE);
		subsend.setText("发送");
		subsend.setBounds(774, 113, 66, 23);
		subsend.addMouseListener(new MouseAdapter() { // 消息发送按钮
			@Override
			public void mouseUp(MouseEvent e) {
				String msg = text.getText();
				User friend = null;
				for(User u:user.getFriends()){
					if(u.getId().equals(friend_id)){
						friend = u;
					}
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				Message message = new Message(user, friend, sendTime);
				
				if (msg == null || msg.length() == 0 || msg.equals("") || msg.trim().isEmpty()) {
				} else {
					NewMyMsg(chatscr,chating, SWTResourceManager.getImage(user.getHeadimg()), sendTime, msg);
					text.setText("");
					ManageSessionMessage.getSessionsMessage(friend_id).setText(msg);
					ManageSessionSendTime.getSessionSendTime(friend_id).setText(sendTime);
					try {
						message.setContent(msg);
						message.setType(viewStyle.text);
						ManageClientThread.getClientConServerThread(user.getId()).sendMessage(message);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		chat.layout();
		chat1.setVisible(true);
		chat.setVisible(true);
	}
	//群聊聊天界面
	public static void newGchating(Shell Wechat, Composite chat, org.eclipse.swt.graphics.Image head
			, String chatingman,OrdinaryUser user,Group group) { // 新的聊天界面
		Composite chat1 = new Composite(chat, SWT.NONE);
		Set<Integer> names = jm1.keySet();
		int i = 0;
		for (int n : names) {
			if (n==group.getGroupId()) {
				chat1 = jm1.get(n);
				break;
			}
			i++;
		}
		if (i == jm1.size()) {
			jm1.put(group.getGroupId(), chat1);
		}
		else{
			System.out.println("已经加载聊天记录面板了");
			chat.layout();
			chat.setVisible(true);
			chat1.setVisible(true);
			System.out.println("显示啊你");
			return;
		}
		Label chatingName = new Label(chat1, SWT.NONE);

		chatingName.setText(chatingman);
		chatingName.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		chatingName.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		chatingName.setBounds(29, -1, 139, 35);

		ScrolledComposite chatscr = new ScrolledComposite(chat1, SWT.H_SCROLL | SWT.V_SCROLL); // 聊天信息显示
		chatscr.setBounds(29, 40, 832, 532);
		chatscr.setExpandHorizontal(true);
		chatscr.setExpandVertical(true);
		manageScrolledComposite.put(group.getGroupId(), chatscr);

		Composite chating = new Composite(chatscr, SWT.NONE);
		chating.setBackground(SWTResourceManager.getColor(245, 245, 245));
		chating.setLayout(new RowLayout(SWT.VERTICAL));
		chatscr.setContent(chating);
		chatscr.setMinSize(chating.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		chatscr.setMinSize(new Point(0, 0));
		chatings.put(group.getGroupId(), chating);
		//添加聊天信息显示框进管理类
//		ManageChating.addChating(friend_id, chating);
		Composite edittext = new Composite(chat1, SWT.NONE);
		edittext.setBounds(0, 575, 891, 143);
		edittext.setBackground(SWTResourceManager.getColor(255, 255, 255));

		Text text = new Text(edittext, SWT.WRAP);
		text.setBounds(30, 35, 810, 77);

		Label e1 = new Label(edittext, SWT.NONE);
		e1.setBounds(30, 7, 33, 20);
		setLabelBg(e1, "e1c");
		e1.setSize(32, 32);

		Label e2 = new Label(edittext, SWT.NONE);
		e2.setBounds(80, 7, 33, 20);
		setLabelBg(e2, "e2c");
		e2.setSize(31, 32);

		Label e3 = new Label(edittext, SWT.NONE);
		e3.setBounds(130, 7, 33, 20);
		setLabelBg(e3, "e3c");
		e3.setSize(31, 32);

		org.eclipse.swt.graphics.Image e = SWTResourceManager.getImage(Index.class, "/img/e1.png"); // 表情
		org.eclipse.swt.graphics.Image ee = SWTResourceManager.getImage(Index.class, "/img/e2.png");
		org.eclipse.swt.graphics.Image eee = SWTResourceManager.getImage(Index.class, "/img/e3.png");
		org.eclipse.swt.graphics.Image myheadimg = head;
		System.out.println(group.getMessages().size()+"啊哈哈哈");
		for(Message m:group.getMessages()){
			
			if(!m.getSender().getId().equals(user.getId())){
				if(m.getType()==file){
//					Message fm = (Message) m;
//					NewMyfile(chatscr,chating, myheadimg, fm.getSendTime(), fm.getPath());
					BackFmsg(chatscr,chating, myheadimg, m.getSendTime()
							, "wechat\\"+user.getId()+"\\file\\"+m.getContent().substring(m.getContent().lastIndexOf("\\")+1));
				}
				else if(m.getType()==Picture){
					//显示表情包
					NewBackEmsg(chatscr,
							chating
		    				, SWTResourceManager.getImage(m.getSender().getHeadimg())
		    				, m.getSendTime()
		    				, SWTResourceManager.getImage(Index.class, m.getContent()));
				}
				else if(m.getType()==VoiceMe){
					//显示语音
					NewbackVmsg(chatscr, chating, myheadimg, "", m);
				}
				else if(m.getType()==MessageType.text){
					Newbackmsg(chatscr
							,chating
							, SWTResourceManager.getImage(m.getSender().getHeadimg())
							, m.getSendTime()
							, m.getContent());
				}
			}
			else{
				if(m.getType()==file){
					//显示文件
//					FileMessage fm = (FileMessage) m;
					NewMyfile(chatscr,chating, myheadimg, m.getSendTime(), m.getContent());
				}
				else if(m.getType()==Picture){
					//显示表情包
					NewMyEmsg(chatscr
							,chating
		    				, SWTResourceManager.getImage(user.getHeadimg())
		    				, m.getSendTime()
		    				, SWTResourceManager.getImage(Index.class, m.getContent()));
				}
				else if(m.getType()==VoiceMe){
					//显示语音
					NewMyVmsg(chatscr, chating, myheadimg, "", m);
				}
				else if(m.getType()==MessageType.text){
					//显示文本
					NewMyMsg(chatscr
							,chating
							, SWTResourceManager.getImage(user.getHeadimg())
							, m.getSendTime()
							, m.getContent());
				}
//				(chatscr,chating, SWTResourceManager.getImage(user.getHeadimg()), sendTime, msg);
			}
		}
		//发送表情包
		e1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				String sendTime = getNewTime();
				Message m = new Message(user, null, sendTime);
				m.setContent("/img/e1.png");
				m.setType(Picture);
				viewStyle.NewMyEmsg(chatscr,chating, SWTResourceManager.getImage(user.getHeadimg()), sendTime, e);
				try {
					
					//
					ManageClientThread.getClientConServerThread(user.getId()).sendMessage(m);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			public String getNewTime() {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				return sendTime;
			}
		});
		e2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				Message m = new Message(user, null, sendTime);
				m.setContent("/img/e2.png");
				m.setType(Picture);
				viewStyle.NewMyEmsg(chatscr,chating, SWTResourceManager.getImage(user.getHeadimg()), sendTime, ee);
				try {
					
					
					ManageClientThread.getClientConServerThread(user.getId()).sendMessage(m);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		e3.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				Message m = new Message(user, null, sendTime);
				m.setContent("/img/e3.png");
				m.setType(Picture);
				viewStyle.NewMyEmsg(chatscr,chating, SWTResourceManager.getImage(user.getHeadimg()), sendTime, eee);
				try {
					
					//
					ManageClientThread.getClientConServerThread(user.getId()).sendMessage(m);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
//		OSoundRecording sr = null;
		Label voice = new Label(edittext, SWT.NONE);															//语音按钮
		voice.setImage(SWTResourceManager.getImage(Index.class,"/img/v.png"));
		voice.setBounds(180, 5, 33, 29);
		voice.addMouseListener(new MouseAdapter() {
			SoundRecording sr = new SoundRecording(user.getId());
			public void mouseDown(MouseEvent e){
				
//				NewMyVmsg(chatscr, chating, myheadimg, "", "");
//				NewbackVmsg(chatscr, chating, myheadimg, "", "");
				System.out.println("录音啦");
				sr.capture();
				voice.setImage(SWTResourceManager.getImage(Index.class,"/img/v-down.png"));//图片再说
			}
			public void mouseUp(MouseEvent e){
				voice.setImage(SWTResourceManager.getImage(Index.class,"/img/v.png"));
				sr.stop();
				String path = sr.save();
				System.out.println(path);
				System.out.println("结束录音");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				Message message = new Message(user, null, sendTime, VoiceMe, path);
				NewMyVmsg(chatscr, chating, myheadimg, "", message);
				try {
//					sr.play1(path);
					
					//发送群语音
					ManageClientThread.getClientConServerThread(user.getId()).sendMessage(message);
//					sr = new SoundRecording(user.getId());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		
		Label snedfile = new Label(edittext, SWT.NONE);
		snedfile.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
//		snedfile.setBounds(180, 12, 60, 20);
		snedfile.setBounds(227, 9, 60, 20);
		snedfile.setText("发送文件");
		snedfile.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				org.eclipse.swt.widgets.FileDialog a = new org.eclipse.swt.widgets.FileDialog(Wechat);
				a.setText("选择文件");
				a.open();
				if (a.getFilterPath() == "" + "") {
				} else{
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
					String sendTime = df.format(new Date());
					Message fm = new Message(user, null, sendTime
							, file, a.getFilterPath()+"\\"+a.getFileName());
//					Message fm = new Message(user, new User(friend_id, null, null, null)
//							, sendTime, a.getFilterPath()+"\\"+a.getFileName());	
					System.out.println(a.getFilterPath()+"\\"+a.getFileName());
					fm.setType(file);
					try {
						
						
						//发送文件
						ManageClientThread.getClientConServerThread(user.getId()).sendMessage(fm);
//						ManageClientThread.getClientConServerThread(user.getId()).saveMessage(fm);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//					NewMyfile(chating, myheadimg, nowtime, a.getFileName());
			}
		});

		Button subsend = new Button(edittext, SWT.NONE);
		subsend.setText("发送");
		subsend.setBounds(774, 113, 66, 23);
		subsend.addMouseListener(new MouseAdapter() { // 消息发送按钮
			@Override
			public void mouseUp(MouseEvent e) {
				String msg = text.getText();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				Message message = new Message(user, null, sendTime);
				
				if (msg == null || msg.length() == 0 || msg.equals("") || msg.trim().isEmpty()) {
				} else {
					NewMyMsg(chatscr,chating, SWTResourceManager.getImage(user.getHeadimg()), sendTime, msg);
					text.setText("");
//					ManageSessionMessage.getSessionsMessage(friend_id).setText(msg);
//					ManageSessionSendTime.getSessionSendTime(friend_id).setText(sendTime);
//					System.out.println("发送信息");
					try {
						message.setContent(msg);
						message.setType(viewStyle.text);
						for(Group g:ManageWechat.getWechat(user.getId()).user.getGroups()){
							if(g.getGroupId()==group.getGroupId()){
								g.getMessages().add(message);
							}
						}
						ManageClientThread.
						getClientConServerThread(user.getId()).sendGMessage(message, group.getGroupId());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		chat.layout();
		chat1.setVisible(true);
		chat.setVisible(true);
	}


	
	public static void NewMyVmsg(ScrolledComposite chatscr,Composite chating
			, org.eclipse.swt.graphics.Image myheadimg,
			Object OrdinaryUser,Message message) { // 我的V信息框

		Composite sendmsg = new Composite(chating, SWT.NONE); // 发送信息框	
		sendmsg.setBackground(SWTResourceManager.getColor(245, 245, 245));
		chating.setBackgroundMode(SWT.INHERIT_DEFAULT); 
		Label myhead = new Label(sendmsg, SWT.NORMAL); // 我方头像
		myhead.setImage(myheadimg);
		myhead.setBounds(752, 28, 44, 44);

		Label mytime = new Label(sendmsg, SWT.NONE); // 消息发送/接受时间
		mytime.setText(message.getSendTime().substring(11,19));
		mytime.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		mytime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		mytime.setBounds(368, 0, 44, 20);

		Label myvtime = new Label(sendmsg, SWT.NONE);
		myvtime.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		myvtime.setText("5s");
		myvtime.setBounds(675, 46, 33, 20);
		
		Label myvoice = new Label(sendmsg, SWT.NONE);
		myvoice.setImage(SWTResourceManager.getImage(Index.class,"/img/myvoice.png"));
		myvoice.setBounds(616, 43, 33, 29);
		
		myvoice.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e){
				myvoice.setImage(SWTResourceManager.getImage(Index.class,"/img/myvoice-down.png"));
			}
		});
		
		myvoice.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
		        if (e.button == 3) {
		            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		            try {
		            	long diff = new Date().getTime()-df.parse(message.getSendTime()).getTime();
						long days = diff / (1000 * 60 * 60 * 24);  
					    long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
					    long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
						if(days==0&&hours==0){
							if(minutes==0){
								
							}
							else{
								
							}
						}
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		    }
			public void mouseUp(MouseEvent arg0) {
				try {
					File directory = new File("");// 参数为空
			        String courseFile = directory.getCanonicalPath();
			        System.out.println(courseFile);
					SoundRecording.play1(courseFile+"\\wechat\\"+message.getSender().getId()+"\\voice\\"
+message.getContent().substring(message.getContent().lastIndexOf("\\")+1));
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		chatscr.setMinHeight(chatscr.getMinHeight()+80);
		chatscr.getVerticalBar().setSelection(chatscr.getMinHeight());//滚动跟随滚动
		chatscr.layout();
		chating.layout();
	}
	public static void NewbackVmsg(ScrolledComposite chatscr,Composite chating
			, org.eclipse.swt.graphics.Image backheadimg,
			Object OrdinaryUser,Message message) {
		
		chatscr.setMinHeight(chatscr.getMinHeight()+82);							
		chatscr.getVerticalBar().setSelection(chatscr.getMinHeight());//滚动跟随滚动
	
		Composite backmsg = new Composite(chating, SWT.NONE); // 接受信息框
		backmsg.setLayoutData(new RowData(810, 82));
		backmsg.setBackground(SWTResourceManager.getColor(245, 245, 245));

		Label backhead = new Label(backmsg, SWT.NORMAL);
		backhead.setBounds(10, 28, 44, 44);
		backhead.setImage(backheadimg);

		Label backtime = new Label(backmsg, SWT.NONE);
		backtime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		backtime.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		backtime.setBounds(368, 0, 44, 20);
		backtime.setText(message.getSendTime().substring(11,19));

		Label backvtime = new Label(backmsg, SWT.NONE);
		backvtime.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		backvtime.setText("5s");
		backvtime.setBounds(80, 46, 33, 20);
		
		Label bvoice = new Label(backmsg, SWT.NONE);
		bvoice.setImage(SWTResourceManager.getImage(Index.class,"/img/backvoice.png"));
		bvoice.setBounds(130, 43, 33, 29);
		
		bvoice.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e){
				bvoice.setImage(SWTResourceManager.getImage(Index.class,"/img/backvoice-down.png"));
			}
			public void mouseUp(MouseEvent arg0) {
				try {
					File directory = new File("");// 参数为空
			        String courseFile = directory.getCanonicalPath();
			        System.out.println(courseFile);
					SoundRecording.play1(courseFile+"\\wechat\\"+message.getSendee().getId()+"\\voice\\"
+message.getContent().substring(message.getContent().lastIndexOf("\\")+1));
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		
		chatscr.setMinHeight(chatscr.getMinHeight()+80);
		chatscr.getVerticalBar().setSelection(chatscr.getMinHeight());//滚动跟随滚动
		chatscr.layout();
		chating.layout();
	}
	
	public static void NewMyMsg(ScrolledComposite chatscr,Composite chating, org.eclipse.swt.graphics.Image myheadimg, String time,
			String content) { // 我的信息框

		Composite sendmsg = new Composite(chating, SWT.NONE); // 发送信息框	
//		chating.setBackgroundMode(SWT.INHERIT_DEFAULT); 
		sendmsg.setBackground(SWTResourceManager.getColor(245, 245, 245));
		
		Label myhead = new Label(sendmsg, SWT.NORMAL); // 我方头像
		myhead.setImage(myheadimg);
		myhead.setBounds(752, 28, 44, 44);

		Label mytime = new Label(sendmsg, SWT.NONE); // 消息发送/接受时间
		mytime.setText(time.substring(12,19));
		mytime.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		mytime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		mytime.setBounds(368, 0, 44, 20);

		//判断字数   24长度一行  一行宽22
		Label mymsg = new Label(sendmsg, SWT.WRAP |SWT.RIGHT); // 内容
		if(content.length()<=24){
			mymsg = new Label(sendmsg,  SWT.WRAP |SWT.RIGHT); // 内容
			mymsg.setText(content);
			mymsg.setBackground(SWTResourceManager.getColor(245, 245, 245));
			mymsg.setBounds(365, 29, 369, 44);
			sendmsg.setLayoutData(new RowData(806, 80));
			chatscr.setMinHeight(chatscr.getMinHeight()+80);
		}
		if(content.length()>=24&&content.length()<=200){
			mymsg = new Label(sendmsg,  SWT.WRAP |SWT.LEFT); // 内容
			mymsg.setText(content);
			mymsg.setBackground(SWTResourceManager.getColor(245, 245, 245));
			int h = ((content.length()/22)+1);
			mymsg.setBounds(365, 29, 369, 22*h);
			sendmsg.setLayoutData(new RowData(806, (80+(22*h))));
			chatscr.setMinHeight(chatscr.getMinHeight()+(80+(22*h)));
		}
		
		
		System.out.println("new完毕");
		mymsg.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
		        if (e.button == 3) {
		            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		            try {
		            	long diff = new Date().getTime()-df.parse(time).getTime();
						long days = diff / (1000 * 60 * 60 * 24);  
					    long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
					    long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
						if(days==0&&hours==0){
							if(minutes==0){
								System.out.println("可以撤回");
							}
							else{
								System.out.println("不可以撤回");
							}
						}
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		    }
		});
		
		chatscr.getVerticalBar().setSelection(chatscr.getMinHeight());//滚动跟随滚动
		chatscr.layout();
		chating.layout();
	}

	//对方消息框
	public static void Newbackmsg(ScrolledComposite chatscr,Composite chating, org.eclipse.swt.graphics.Image backheadimg, String time,
			String content) {
		
		chatscr.setMinHeight(chatscr.getMinHeight()+82);							
		chatscr.getVerticalBar().setSelection(chatscr.getMinHeight());//滚动跟随滚动
	
		Composite backmsg = new Composite(chating, SWT.NONE); // 接受信息框
		backmsg.setLayoutData(new RowData(810, 82));
		backmsg.setBackground(SWTResourceManager.getColor(245, 245, 245));

		Label backhead = new Label(backmsg, SWT.NORMAL);
		backhead.setBounds(10, 28, 44, 44);
		backhead.setImage(backheadimg);

		Label backtime = new Label(backmsg, SWT.NONE);
		backtime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		backtime.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		backtime.setBounds(368, 0, 44, 20);
		backtime.setText(time.substring(11,19));

		//判断字数   24长度一行  一行宽22
		Label bkmsgcontent = new Label(backmsg, SWT.WRAP |SWT.LEFT);
		if(content.length()<=24){
			bkmsgcontent = new Label(backmsg,  SWT.WRAP |SWT.LEFT); // 内容
			bkmsgcontent.setText(content);
			bkmsgcontent.setBackground(SWTResourceManager.getColor(245, 245, 245));
			bkmsgcontent.setBounds(72, 29, 369, 44);
			backmsg.setLayoutData(new RowData(806, 80));
			chatscr.setMinHeight(chatscr.getMinHeight()+80);
		}
		if(content.length()>=24&&content.length()<=200){
			bkmsgcontent = new Label(backmsg,  SWT.WRAP |SWT.RIGHT); // 内容
			bkmsgcontent.setText(content);
			bkmsgcontent.setBackground(SWTResourceManager.getColor(245, 245, 245));
			int h = ((content.length()/22)+1);
			bkmsgcontent.setBounds(72, 29, 369, 22*h);
			backmsg.setLayoutData(new RowData(806, (80+(22*h))));
			chatscr.setMinHeight(chatscr.getMinHeight()+(80+(22*h)));
		}
		
		chatscr.layout();
		chating.layout();
	}

	public static void NewMyEmsg(ScrolledComposite chatscr,Composite chating, org.eclipse.swt.graphics.Image myheadimg, String time,
			org.eclipse.swt.graphics.Image myemojiimg) {

		chatscr.setMinHeight(chatscr.getMinHeight()+130);							
		chatscr.getVerticalBar().setSelection(chatscr.getMinHeight());//滚动跟随滚动
		
		Composite sendemoji = new Composite(chating, SWT.NONE); // 发送表情
		sendemoji.setLayoutData(new RowData(805, 130));
		sendemoji.setBackground(SWTResourceManager.getColor(245, 245, 245));

		Label myehead = new Label(sendemoji, SWT.NONE);
		myehead.setImage(myheadimg);
		myehead.setBounds(752, 28, 44, 44);

		Label myetime = new Label(sendemoji, SWT.NONE);
		myetime.setText(time.substring(11,19));
		myetime.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		myetime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		myetime.setBounds(368, 0, 44, 20);

		Label myemoji = new Label(sendemoji, SWT.NONE);
		myemoji.setImage(myemojiimg);
		myemoji.setBounds(588, 10, 126, 120);
		myemoji.setBackground(SWTResourceManager.getColor(245, 245, 245));
		chatscr.layout();
		chating.layout();
	}

	public static void NewBackEmsg(ScrolledComposite chatscr,Composite chating, org.eclipse.swt.graphics.Image backheadimg, String time,
			org.eclipse.swt.graphics.Image backemojiimg) {

		chatscr.setMinHeight(chatscr.getMinHeight()+130);							
		chatscr.getVerticalBar().setSelection(chatscr.getMinHeight());//滚动跟随滚动

		Composite backemoji = new Composite(chating, SWT.NONE); // 接收表情
		backemoji.setLayoutData(new RowData(808, 130));
		backemoji.setBackground(SWTResourceManager.getColor(245, 245, 245));

		Label label_16 = new Label(backemoji, SWT.NONE);
		label_16.setImage(backheadimg);
		label_16.setBounds(10, 28, 44, 44);

		Label backetime = new Label(backemoji, SWT.NONE);
		backetime.setText(time.substring(11,19));
		backetime.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		backetime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		backetime.setBounds(368, 0, 44, 20);

		Label backe = new Label(backemoji, SWT.NONE);
		backe.setImage(backemojiimg);
		backe.setBackground(SWTResourceManager.getColor(245, 245, 245));
		backe.setBounds(93, 10, 126, 120);
		chatscr.layout();
		chating.layout();
	}

	public static void NewMyfile(ScrolledComposite chatscr,Composite chating
			, org.eclipse.swt.graphics.Image myheadimg, String time,
			String myfilename) {
			
		chatscr.setMinHeight(chatscr.getMinHeight()+95);							
		chatscr.getVerticalBar().setSelection(chatscr.getMinHeight());//滚动跟随滚动
		
		Composite sendfile = new Composite(chating, SWT.NONE); // 发送文件
		sendfile.setLayoutData(new RowData(SWT.DEFAULT, 95));
		sendfile.setBackground(SWTResourceManager.getColor(245, 245, 245));

		Label myfhead = new Label(sendfile, SWT.NONE);
		myfhead.setImage(myheadimg);
		myfhead.setBounds(752, 28, 44, 44);

		Label myftime = new Label(sendfile, SWT.NONE);
		myftime.setText(time.substring(11,19));
		myftime.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		myftime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		myftime.setBounds(368, 0, 44, 20);

		Label filetips = new Label(sendfile, SWT.RIGHT);
		filetips.setText("发送成功");
		filetips.setBackground(SWTResourceManager.getColor(245, 245, 245));
		filetips.setBounds(564, 28, 162, 20);

		Label filename = new Label(sendfile, SWT.RIGHT);
		filename.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		filename.setText(myfilename);
		filename.setBackground(SWTResourceManager.getColor(245, 245, 245));
		filename.setBounds(450, 54, 276, 20);
		filename.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				try {
					System.out.println("打开文件夹");
//			        fstring+filename.getText().toString().replace("\\","/")+lstring
//					Process p = Runtime.getRuntime()
//							.exec(filename.getText().substring(0,filename.getText().lastIndexOf("\\")));
					try {
//						p.waitFor();
//						File file = new File(filename.getText());
//						Desktop.getDesktop().open(file);
						Runtime.getRuntime().exec("explorer /select, "+filename.getText());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		chatscr.layout();
		chating.layout();
	}

	public static void BackFmsg(ScrolledComposite chatscr,Composite chating, org.eclipse.swt.graphics.Image backheadimg, String time,
			String backfilename) { // 收到文件
		
		chatscr.setMinHeight(chatscr.getMinHeight()+97);							
		chatscr.getVerticalBar().setSelection(chatscr.getMinHeight()+97);//滚动跟随滚动
		
		Composite backfile = new Composite(chating, SWT.NONE);
		backfile.setLayoutData(new RowData(806, 97));
		backfile.setBackground(SWTResourceManager.getColor(245, 245, 245));

		Label backfhead = new Label(backfile, SWT.NONE);
		backfhead.setImage(backheadimg);
		backfhead.setBounds(10, 28, 44, 44);

		Label backftime = new Label(backfile, SWT.NONE);
		backftime.setText(time.substring(11,19));
		backftime.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		backftime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		backftime.setBounds(368, 0, 44, 20);

		Label btips = new Label(backfile, SWT.RIGHT);
		btips.setAlignment(SWT.LEFT);
		btips.setText("\u6536\u5230\u4E00\u4E2A\u6587\u4EF6\u70B9\u51FB\u67E5\u770B");
		btips.setBackground(SWTResourceManager.getColor(245, 245, 245));
		btips.setBounds(74, 28, 162, 20);

		Label backfname = new Label(backfile, SWT.RIGHT);
		backfname.setAlignment(SWT.LEFT);
		backfname.setText(backfilename);
		backfname.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		backfname.setBackground(SWTResourceManager.getColor(245, 245, 245));
		backfname.setBounds(74, 52, 276, 20);
		backfname.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				try {
					System.out.println("打开文件夹");
//			        fstring+filename.getText().toString().replace("\\","/")+lstring
//					Process p = Runtime.getRuntime()
//							.exec(filename.getText().substring(0,filename.getText().lastIndexOf("\\")));
					try {
//						p.waitFor();
//						File file = new File(filename.getText());
//						Desktop.getDesktop().open(file);
						
						File directory = new File("");// 参数为空
				        String courseFile = directory.getCanonicalPath();
				        System.out.println(courseFile);
				        Runtime.getRuntime().exec("explorer /select, "+courseFile+"\\"+backfname.getText());
				        System.out.println(courseFile+"\\"+backfname.getText());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		chatscr.layout();
		chating.layout();
	}

	public static void AddGroupOne(Composite GroupPeople,User g_user) {
		Composite gone = new Composite(GroupPeople, SWT.NONE); // 群成员+1
		Label gpName = new Label(gone, SWT.NONE);
		Label gghead = new Label(gone, SWT.NONE);
		gone.setLayoutData(new RowData(120, 120));

		gpName.setAlignment(SWT.CENTER);
		gpName.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		gpName.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		gpName.setBounds(0, 90, 120, 20);
		gpName.setText(g_user.getName());
		gghead.setBounds(30, 10, 60, 60);
		gghead.setBackgroundImage(ghead);
		GroupPeople.layout();
	}

	public static void addWordmom(ScrolledComposite m ,Composite moments
			,org.eclipse.swt.graphics.Image whead,
			Moment moment,OrdinaryUser user, boolean boo){															//添加文字朋友圈
		Composite WordMo = new Composite(moments, SWT.NONE);							//文字朋友圈
		WordMo.setLayout(new RowLayout(SWT.HORIZONTAL));
//		WordMo.setLayoutData(new RowData(347, 154));
			
		Composite bbbbbb = new Composite(WordMo, SWT.NONE);											//间隔条
		bbbbbb.setLayoutData(new RowData(347, 3));
		
		Label label_11 = new Label(bbbbbb, SWT.NONE);
		label_11.setBackground(SWTResourceManager.getColor(253,253,253));
		label_11.setBounds(35, 0, 278, 3);
		
		Composite comtent = new Composite(WordMo, SWT.NONE);	
		Label wmhead = new Label(comtent, SWT.NONE);
		wmhead.setImage(whead);        						//	头像
		wmhead.setBounds(10, 5, 50, 50);
	
		Label wmname = new Label(comtent, SWT.NONE);
		wmname.setText(moment.getName());							//用户名
		wmname.setForeground(SWTResourceManager.getColor(70, 130, 180));
		wmname.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		wmname.setBounds(72, 5, 110, 27);
		
		Label wmcon = new Label(comtent, SWT.WRAP);
		//22px一行 15字

		int  wh =moment.getContent().length()/15;
		WordMo.setLayoutData(new RowData(347, 100+(22*wh+22)));
		wmcon.setBounds(82, 38, 236, 22*wh+22);
		wmcon.setText(moment.getContent());
		m.setMinHeight(m.getMinHeight()+(100+(22*wh+22)));
		
		Label wmid = new Label(comtent, SWT.NONE);
		wmid.setBounds(316, 5, 21, 20);
		wmid.setVisible(false);
		
//		int wh = new Date().getHours();
//		int wm = new Date().getMinutes();
//		String tt= wh+":"+wm;
		Label wmtime = new Label(comtent, SWT.NONE);
		wmtime.setBounds(289, 5, 60, 20);
		wmtime.setForeground(SWTResourceManager.getColor(128, 128, 128));
		wmtime.setText(getDistance(moment.getTime()));
		
		Composite wtools = new Composite(WordMo, SWT.NONE);
		
		Label likenum = new Label(wtools, SWT.NONE);
		likenum.setText(moment.getLike()+"");
		likenum.setForeground(SWTResourceManager.getColor(50, 205, 50));
		likenum.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		likenum.setAlignment(SWT.CENTER);
		likenum.setBounds(0, 8, 35, 32);
		
		Label like = new Label(wtools, SWT.NONE);

		like.setImage(SWTResourceManager.getImage(Frdinfo.class, "/img/like.png"));
		like.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		like.setBounds(37, 0, 35, 40);
		
		Label com = new Label(wtools, SWT.NONE);
		com.setText("\u8BC4\u8BBA");
		com.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		com.setBounds(312, 10, 35, 20);
		
		Label send = new Label(wtools, SWT.NONE);

		send.setText("发送");
		send.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		send.setBounds(312, 10, 35, 20);
		send.setVisible(false);
		
		Label del = new Label(wtools, SWT.NONE);
		del.setText("\u5220\u9664");
		del.setForeground(SWTResourceManager.getColor(220, 20, 60));
		del.setBounds(276, 10, 35, 20);
		del.setVisible(false);
		if(boo){
			del.setVisible(true);							//如果是自己的朋友圈显示删除按钮
		}
		del.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
//				text.setVisible(true);;
//				send.setVisible(true);
//				del.setVisible(false);
//				com.setVisible(false);
				System.out.println("点击删除");
				WordMo.dispose();
				moments.layout();
				try {
					ManageClientThread.getClientConServerThread(user.getId()).delMement
					(Integer.parseInt(moment.getId()));
					ManageFrdinfo.getFrdinfo(user.getId()).user.getAllmonents().remove(moment);
					ManageWechat.getWechat(user.getId()).user.getAllmonents().remove(moment);
					System.out.println("删除成功");
//					for(Moment moments_:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
//						if(moments_.getId().equals(((Moment)wnum).getId())){
//							ManageWechat.getWechat(user.getId()).user.getAllmonents().remove(moments_);
//							break;
//						}
//					}
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		Text text = new Text(wtools, SWT.NONE);
		text.setBackground(SWTResourceManager.getColor(250, 250, 250));
		text.setBounds(78, 8, 192, 26);
		text.setVisible(false);
		
		
		com.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				text.setVisible(true);;
				send.setVisible(true);
				del.setVisible(false);
				com.setVisible(false);
			}
		});
		send.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {			
				
				String s= WordMo.getSize()+"";				
				String h =s.substring(12, 15);
				String t = text.getText();
				if(!(t==null||t.length()==0)){
					int m_id = Integer.parseInt(moment.getId());
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sendTime = df.format(new Date());
					Comment comm = new Comment(m_id+"", t, user.getId(), user.getName(), sendTime);
					viewStyle.addWcom(m,moments,WordMo, user.getName()+": "+t,Integer.parseInt(h),boo,comm,user.getId());
					try {
						ManageClientThread.getClientConServerThread(user.getId()).addComment(comm);
						for(Moment monment:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
							if(monment.getId().equals(m_id+"")){
								monment.getConments().add(comm);
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
				text.setText("");
				text.setVisible(false);
				send.setVisible(false);
				com.setVisible(true);
				if(boo){
				del.setVisible(true);							//如果是自己的朋友圈显示删除按钮
			}
			
			}
		});
		like.addMouseListener(new MouseAdapter() {
			int Hlikenum = Integer.parseInt(likenum.getText());
			public void mouseUp(MouseEvent e) {
				if(like.getImage()==likeico){
					like.setImage(likeed);
					likenum.setText(Hlikenum+1+"");
					Hlikenum+=1;
					try {
						ManageClientThread.getClientConServerThread(user.getId()).addLike
						(Integer.parseInt(moment.getId()));
						for(Moment moments_:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
							if(moments_.getId().equals(moment.getId())){
								moments_.setLike(moments_.getLike()+1);
								break;
							}
						}
					} catch (NumberFormatException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				else{
					like.setImage(likeico);
					likenum.setText(Hlikenum-1+"");
					Hlikenum-=1;
					try {
						ManageClientThread.getClientConServerThread(user.getId()).deleteLike
						(Integer.parseInt(moment.getId()));
						for(Moment moments_:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
							if(moments_.getId().equals(moment.getId())){
								moments_.setLike(moments_.getLike()-1);
								break;
							}
						}
					} catch (NumberFormatException | IOException e1) {
						e1.printStackTrace();
					}
				}			
			}
			
		});
		moments.layout();
		for(Comment c:moment.getConments()){
			String s= WordMo.getSize()+"";				
			String h =s.substring(12, 15);
			viewStyle.addWcom(m,moments,WordMo, c.getName()+": "+c.getContent(),Integer.parseInt(h),boo,c,user.getId());
		}
		
	}
	public static void AddOfficalOne(Composite officals,EnterpriseUser e_user, String user_id) {
		Composite one = new Composite(officals, SWT.NONE); // 公众号+1
		officalss.put(e_user.getId(), one);
		Label OfficalName = new Label(one, SWT.NONE);
		
		Label OfficalHead = new Label(one, SWT.NONE);
		
		OfficalHead.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {

				new OfficalContent(e_user,user_id).open();

			}
		});
		one.setLayoutData(new RowData(120, 120));

		OfficalName.setAlignment(SWT.CENTER);
		OfficalName.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		OfficalName.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		OfficalName.setBounds(0, 90, 120, 20);
		OfficalName.setText(e_user.getName());
		OfficalHead.setBounds(30, 10, 60, 60);
		viewStyle.setLabelBg(OfficalHead, "exhead");
		officals.layout();
	}

	public static void ViewGroupInfo(Composite Group, 
			Group group,String user_id) {
		Composite groupinfo = new Composite(Group, SWT.NONE); // 点击已有群的信息
		groupInfoss.put(group.getGroupId(), groupinfo);
		groupinfo.setLayout(new FormLayout());

		Label groupname = new Label(groupinfo, SWT.NONE);
		FormData fd_groupname = new FormData();
		fd_groupname.top = new FormAttachment(0);
		fd_groupname.left = new FormAttachment(0, 45);
		groupname.setLayoutData(fd_groupname);
		groupname.setText(group.getGroupName());
		groupname.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		groupname.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));

		Composite GroupPeople = new Composite(groupinfo, SWT.NONE);
		GroupPeopless.put(group.getGroupId(), GroupPeople);
		FormData fd_GroupPeople1 = new FormData();
		fd_GroupPeople1.right = new FormAttachment(100, -32);
		fd_GroupPeople1.left = new FormAttachment(0, 71);
		GroupPeople.setLayoutData(fd_GroupPeople1);
		GroupPeople.setLayout(new RowLayout(SWT.HORIZONTAL));

		Label groupId = new Label(groupinfo, SWT.NONE);
		fd_groupname.right = new FormAttachment(groupId, -6);
		groupId.setText(group.getGroupId() + "");
		groupId.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		groupId.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		FormData fd_groupId = new FormData();
		fd_groupId.top = new FormAttachment(groupname, 0, SWT.TOP);
		fd_groupId.left = new FormAttachment(0, 183);
		groupId.setLayoutData(fd_groupId);

		Label sendgroup = new Label(groupinfo, SWT.NONE);
		fd_GroupPeople1.bottom = new FormAttachment(100, -144);
		sendgroup.setText("发送信息");
		sendgroup.setForeground(SWTResourceManager.getColor(50, 205, 50));
		sendgroup.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		FormData fd_sendgroup = new FormData();
		fd_sendgroup.top = new FormAttachment(GroupPeople, 33);
		sendgroup.setLayoutData(fd_sendgroup);

		Label delgroup = new Label(groupinfo, SWT.NONE);
		fd_sendgroup.right = new FormAttachment(delgroup, -65);
		delgroup.setText("退出群聊");
		delgroup.setForeground(SWTResourceManager.getColor(255, 69, 0));
		delgroup.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		FormData fd_delgroup = new FormData();
		fd_delgroup.left = new FormAttachment(0, 501);
		fd_delgroup.top = new FormAttachment(GroupPeople, 33);
		delgroup.setLayoutData(fd_delgroup);
		delgroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(group.getGroupOwner().getId().equals(user_id)){
					ManageWechat.getWechat(user_id).delGroup(group);
//					ManageClientThread.getClientConServerThread(user_id).delGroup(group.getGroupId());
				}
				else{
//					ManageClientThread.getClientConServerThread(user_id).delGroupM(group.getGroupId(), user_id);
					ManageWechat.getWechat(user_id).delGroupM(user_id, group);
				}
//				ManageClientThread.getClientConServerThread(user_id)
			}
		});

		Label gp = new Label(groupinfo, SWT.NONE);
		fd_GroupPeople1.top = new FormAttachment(gp, 22);
		gp.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		gp.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		FormData fd_gp = new FormData();
		fd_gp.top = new FormAttachment(groupname, 12);
		fd_gp.left = new FormAttachment(0, 44);
		gp.setLayoutData(fd_gp);
		gp.setText("群公告：");

		Label GroupNotice = new Label(groupinfo, SWT.NONE);
		GroupNotice.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		FormData fd_GroupNotice = new FormData();
		fd_GroupNotice.top = new FormAttachment(groupname, 14);
		fd_GroupNotice.right = new FormAttachment(gp, 694, SWT.RIGHT);
		fd_GroupNotice.bottom = new FormAttachment(100, -620);
		fd_GroupNotice.left = new FormAttachment(gp, 6);
		GroupNotice.setLayoutData(fd_GroupNotice);
		GroupNotice.setText(group.getGroupBulletin());
		
		Composite ibt = new Composite(GroupPeople, SWT.NONE);
		Label ione = new Label(ibt, SWT.NONE);
		ibt.setLayoutData(new RowData(120, 120));
		ione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				ione.setForeground(SWTResourceManager.getColor(105, 105, 105));
			}
			public void mouseUp(MouseEvent e) {
				new Invite(group.getGroupId(),user_id).open();
				ione.setForeground(SWTResourceManager.getColor(192, 192, 192));
			}
		});
		ione.setForeground(SWTResourceManager.getColor(192, 192, 192));
		ione.setFont(SWTResourceManager.getFont("Microsoft YaHei UI Light", 50, SWT.NORMAL));
		ione.setAlignment(SWT.CENTER);
		ione.setText("+");
		ione.setBounds(0, 0, 120, 120);
		ione.setBackgroundImage(null);
		
		Group.layout();
		groupinfo.setVisible(true);

		sendgroup.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				newGchating(ManageWechat.getWechat(user_id).Wechat
						, ManageWechat.getWechat(user_id).chat, myhead, group.getGroupName()
						, ManageWechat.getWechat(user_id).user, group);
//				ManageWechat.getWechat(user_id).NewGroupChat(group.getGroupName(),group.getGroupId()+"");
			}
		});
		AddGroupOne(GroupPeople,group.getGroupOwner());
		for(User u:group.getGroupMembers()){
			AddGroupOne(GroupPeople,u);
		}
	}

	public static void ViewNewGroupInfo(Composite Group, String gname, int gid, String Gn,String user_id) {
		Composite groupinfo = new Composite(Group, SWT.NONE); // 通过查找显示新群信息
		groupinfo.setLayout(new FormLayout());

		Label groupname = new Label(groupinfo, SWT.NONE);
		FormData fd_groupname = new FormData();
		fd_groupname.top = new FormAttachment(0);
		fd_groupname.left = new FormAttachment(0, 45);
		groupname.setLayoutData(fd_groupname);
		groupname.setText(gname);
		groupname.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		groupname.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));

		Composite GroupPeople = new Composite(groupinfo, SWT.NONE);
		FormData fd_GroupPeople1 = new FormData();
		fd_GroupPeople1.right = new FormAttachment(100, -32);
		fd_GroupPeople1.left = new FormAttachment(0, 71);
		GroupPeople.setLayoutData(fd_GroupPeople1);
		GroupPeople.setLayout(new RowLayout(SWT.HORIZONTAL));

		Label groupId = new Label(groupinfo, SWT.NONE);
		fd_groupname.right = new FormAttachment(groupId, -6);
		groupId.setText(gid + "");
		groupId.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		groupId.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		FormData fd_groupId = new FormData();
		fd_groupId.top = new FormAttachment(groupname, 0, SWT.TOP);
		fd_groupId.left = new FormAttachment(0, 183);
		groupId.setLayoutData(fd_groupId);

		Label addgroup = new Label(groupinfo, SWT.NONE);
		fd_GroupPeople1.bottom = new FormAttachment(100, -144);
		addgroup.setText("添加群聊");
		addgroup.setForeground(SWTResourceManager.getColor(50, 205, 50));
		addgroup.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		FormData fd_addgroup = new FormData();
		fd_addgroup.top = new FormAttachment(GroupPeople, 33);
		addgroup.setLayoutData(fd_addgroup);

		Label gback = new Label(groupinfo, SWT.NONE);
		fd_addgroup.right = new FormAttachment(gback, -65);
		gback.setText("返回");
		gback.setForeground(SWTResourceManager.getColor(255, 69, 0));
		gback.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		FormData fd_gback = new FormData();
		fd_gback.left = new FormAttachment(0, 501);
		fd_gback.top = new FormAttachment(GroupPeople, 33);
		gback.setLayoutData(fd_gback);

		Label gp = new Label(groupinfo, SWT.NONE);
		fd_GroupPeople1.top = new FormAttachment(gp, 22);
		gp.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		gp.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		FormData fd_gp = new FormData();
		fd_gp.top = new FormAttachment(groupname, 12);
		fd_gp.left = new FormAttachment(0, 44);
		gp.setLayoutData(fd_gp);
		gp.setText("群公告：");

		Label GroupNotice = new Label(groupinfo, SWT.NONE);
		GroupNotice.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		FormData fd_GroupNotice = new FormData();
		fd_GroupNotice.top = new FormAttachment(groupname, 14);
		fd_GroupNotice.right = new FormAttachment(gp, 694, SWT.RIGHT);
		fd_GroupNotice.bottom = new FormAttachment(100, -620);
		fd_GroupNotice.left = new FormAttachment(gp, 6);
		GroupNotice.setLayoutData(fd_GroupNotice);
		GroupNotice.setText(Gn);
		Group.layout();
		groupinfo.setVisible(true);

		addgroup.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				ManageWechat.getWechat(user_id).NewGroupChat(gname,gid+"");
			}
		});
		gback.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				Group.setVisible(false);
			}
		});

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 5.29分割线

	
	public static void addHaveOne(Shell shell, Composite have, Bulletin bulletin,String user_name) { // 公众号已有文章+1
		Composite one = new Composite(have, SWT.NONE);
		ones.put(bulletin.getId(), one);
		
		one.setForeground(SWTResourceManager.getColor(100, 149, 237));
		one.setBounds(0, 50, 949, 55);

		Label sort = new Label(one, SWT.NONE);
		sort.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_FOREGROUND));
		sort.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		sort.setBounds(10, 20, 32, 35);
		sort.setText(bulletin.getId()+"");

		Label title = new Label(one, SWT.NONE);
		title.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_FOREGROUND));
		title.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		title.setBounds(48, 20, 604, 35);
		title.setText(bulletin.getTitle());

		Label likenum = new Label(one, SWT.NONE);
		likenum.setAlignment(SWT.CENTER);
		likenum.setText(bulletin.getLike()+"");
		likenum.setForeground(SWTResourceManager.getColor(100, 149, 237));
		likenum.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		likenum.setBounds(808, 20, 52, 25);

		Label label_2 = new Label(one, SWT.NONE);
		label_2.setText("获赞数");
		label_2.setForeground(SWTResourceManager.getColor(100, 149, 237));
		label_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		label_2.setBounds(748, 20, 54, 25);

		Label del = new Label(one, SWT.NONE);
		del.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				del.setForeground(SWTResourceManager.getColor(200, 20, 60));
			}

			public void mouseUp(MouseEvent e) {
				del.setForeground(SWTResourceManager.getColor(240, 20, 60));
				one.dispose();
				have.layout();
//				MessageBox dm = new MessageBox(shell);
//				dm.setText("提示：");
//				dm.setMessage("删除成功！");
//				dm.open();
				try {
					ManageClientThread.getClientConServerThread(
							bulletin.getUser_id()).delBulletin(bulletin.getId(), bulletin.getUser_id());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//删除事件
				
			}
		});
		title.addMouseListener(new MouseAdapter() {

			public void mouseUp(MouseEvent e) {		
				new NoticDetail(bulletin.getTitle(), bulletin.getTime(), user_name, bulletin.getContent());
			}
		});
		del.setText("删除");
		del.setForeground(SWTResourceManager.getColor(240, 20, 60));
		del.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		del.setBounds(883, 20, 45, 29);
		have.layout();
	}
	
	public static void addOCone(Composite c,Composite ncContent,
			org.eclipse.swt.graphics.Image ap,Bulletin bulletin,String user_name){		//企业公众号近期公告+1
		Composite a = new Composite(c, SWT.NONE);
		a.setBounds(10, 0, 646, 144);	
		Label atitle = new Label(a, SWT.WRAP);
		atitle.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		atitle.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		atitle.setBounds(32, 25, 445, 74);
		atitle.setText(bulletin.getTitle());	
		Label apic = new Label(a, SWT.NONE);
		apic.setBounds(524, 25, 89, 83);
		apic.setImage(ap);
		
		Label atime = new Label(a, SWT.NONE);
		atime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		atime.setBounds(30, 105, 89, 20);
		atime.setText(bulletin.getTime());
		
		Label biubiubiu = new Label(a, SWT.NONE);
		biubiubiu.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		biubiubiu.setBounds(58, 131, 537, 3);
		atitle.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				OfficalContent.setnc(bulletin.getTitle(), user_name, bulletin.getTime(), bulletin.getContent());
				OfficalContent.viewnc();
			}
		});
		c.layout();
	}
	public static void move(Shell shell,Composite ddd) {
		Listener listener = new Listener() { // 无标题可移动
			int startX, startY;

			public void handleEvent(Event e) {
				// TODO Auto-generated method stub
				if (e.type == SWT.MouseDown && e.button == 1) {
					startX = e.x;
					startY = e.y;
				}
				if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
					Point p = ddd.toDisplay(e.x, e.y);
					p.x -= startX;
					p.y -= startY;
					shell.setLocation(p);
				}
			}
		};
		ddd.addListener(SWT.MouseDown, listener);
		ddd.addListener(SWT.MouseMove, listener);
	}
	
	
	
							//////
	public static void addWcom(ScrolledComposite m ,Composite moments,Composite WordMo
			,String t,int wmh,boolean boo,Comment comment,String user_id){	
		WordMo.setLayoutData(new RowData(347, wmh+25));					//文字朋友圈评论
		m.setMinHeight(m.getMinHeight()+25);;		
		Label c = new Label(WordMo, SWT.WRAP);
		c.setForeground(SWTResourceManager.getColor(105, 105, 105));
		c.setLayoutData(new RowData(339, 20));
		c.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		c.setText("    "+t);
		c.addListener(SWT.MouseDoubleClick, new Listener() {  
		    	
		    public void handleEvent(Event event) {  
		        if(event.button != 1) { 
		            return;  
		        }  
		        if(boo){
		        	String s= WordMo.getSize()+"";				
					String h =s.substring(12, 15);
					int hh =Integer.parseInt(h);
			        WordMo.setLayoutData(new RowData(347, hh-25));
					m.setMinHeight(m.getMinHeight()+25);;	
			        c.dispose();
			        WordMo.layout();
			        moments.layout();
			        try {
						ManageClientThread.getClientConServerThread
						(user_id).delComment(Integer.parseInt(comment.getId()));
						for(Moment monmet_:ManageWechat.getWechat(user_id).user.getAllmonents()){
							for(Comment comment_:monmet_.getConments()){
								if(comment_.getId().equals(comment.getId())){
									monmet_.getConments().remove(comment_);
									break;
								}
							}
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        System.out.println("删除评论啦");
		        }
		        else{
		        	System.out.println("你没有权限删除评论");
		        }
		        
		    }  
		});  	
		WordMo.layout();
		moments.layout();
		m.layout();
	}

	static org.eclipse.swt.graphics.Image likeico = SWTResourceManager.getImage(Frdinfo.class, "/img/like.png");
	static org.eclipse.swt.graphics.Image likeed = SWTResourceManager.getImage(Frdinfo.class, "/img/like-down.png");
	
	public static String getDistance(String time){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long diff;
		try {
			diff = new Date().getTime()-df.parse(time).getTime();
			long days = diff / (1000 * 60 * 60 * 24);  
		    long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
		    long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		    if(days==0){
		    	return time.split(" ")[1].substring(0, 5);
		    }
		    else{
		    	switch ((int)days) {
				case 1:
					return "一天前";
				case 2:
					return "两天前";
				case 3:
					return "三天前";
				case 4:
					return "四天前";
				case 5:
					return "五天前";
				case 6:
					return "六天前";
				case 7:
					return "一周前";
		    	}
		    	return days+"天前";
		    }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "出错了";
		}
		
	}
	/**
	 * @wbp.parser.entryPoint
	 */
//	public static void addWordmom(ScrolledComposite m ,Composite moments
//			,String wid,org.eclipse.swt.graphics.Image whead,String wname,String wcon
//			,Object wnum,OrdinaryUser user, boolean boo){															//添加文字朋友圈
//		Composite WordMo = new Composite(moments, SWT.NONE);							//文字朋友圈
//		WordMo.setLayout(new RowLayout(SWT.HORIZONTAL));
//		WordMo.setLayoutData(new RowData(347, 154));
//			
//		Composite bbbbbb = new Composite(WordMo, SWT.NONE);											//间隔条
//		bbbbbb.setLayoutData(new RowData(347, 3));
//		
//		Label label_11 = new Label(bbbbbb, SWT.NONE);
//		label_11.setBackground(SWTResourceManager.getColor(253,253,253));
//		label_11.setBounds(35, 0, 278, 3);
//		
//		Composite comtent = new Composite(WordMo, SWT.NONE);	
//		Label wmhead = new Label(comtent, SWT.NONE);
//		wmhead.setImage(whead);        						//	头像
//		wmhead.setBounds(10, 5, 50, 50);
//	
//		Label wmname = new Label(comtent, SWT.NONE);
//		wmname.setText(wname);							//用户名
//		wmname.setForeground(SWTResourceManager.getColor(70, 130, 180));
//		wmname.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
//		wmname.setBounds(72, 5, 110, 27);
//		
//		Label wmcon = new Label(comtent, SWT.WRAP);
//		//22px一行 15字
//		if(wcon.length()<=35){
//			wmcon.setBounds(82, 38, 236, 68);
//			wmcon.setText(wcon);
//			m.setMinHeight(m.getMinHeight()+170);
//		}
////		if(wcon.length()>35&&wcon.length()<=150){
////			int h = wcon.length();
////			wmcon.setBounds(82, 38, 236, 68);
////			wmcon.setText(wcon);
////			m.setMinHeight(m.getMinHeight()+100+);
////		}	
//		Label wmid = new Label(comtent, SWT.NONE);
//		wmid.setBounds(316, 5, 21, 20);
//		wmid.setVisible(false);
//		
////		int wh = new Date().getHours();
////		int wm = new Date().getMinutes();
////		String tt= wh+":"+wm;
//		Label wmtime = new Label(comtent, SWT.NONE);
//		wmtime.setBounds(289, 5, 60, 20);
//		wmtime.setForeground(SWTResourceManager.getColor(128, 128, 128));
//		wmtime.setText(getDistance(((Moment)wnum).getTime()));
//		
//		Composite wtools = new Composite(WordMo, SWT.NONE);
//		
//		Label likenum = new Label(wtools, SWT.NONE);
//		likenum.setText(((Moment)wnum).getLike()+"");
//		likenum.setForeground(SWTResourceManager.getColor(50, 205, 50));
//		likenum.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
//		likenum.setAlignment(SWT.CENTER);
//		likenum.setBounds(0, 8, 35, 32);
//		
//		Label like = new Label(wtools, SWT.NONE);
//
//		like.setImage(SWTResourceManager.getImage(Frdinfo.class, "/img/like.png"));
//		like.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
//		like.setBounds(37, 0, 35, 40);
//		
//		Label com = new Label(wtools, SWT.NONE);
//		com.setText("\u8BC4\u8BBA");
//		com.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
//		com.setBounds(312, 10, 35, 20);
//		
//		Label send = new Label(wtools, SWT.NONE);
//
//		send.setText("发送");
//		send.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
//		send.setBounds(312, 10, 35, 20);
//		send.setVisible(false);
//		
//		Label del = new Label(wtools, SWT.NONE);
//		del.setText("\u5220\u9664");
//		del.setForeground(SWTResourceManager.getColor(220, 20, 60));
//		del.setBounds(276, 10, 35, 20);
//		del.setVisible(false);
//		if(boo){
//			del.setVisible(true);							//如果是自己的朋友圈显示删除按钮
//		}
//		del.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseUp(MouseEvent e) {
////				text.setVisible(true);;
////				send.setVisible(true);
////				del.setVisible(false);
////				com.setVisible(false);
//				System.out.println("点击删除");
//				WordMo.dispose();
//				moments.layout();
//				try {
//					ManageClientThread.getClientConServerThread(user.getId()).delMement
//					(Integer.parseInt(((Moment)wnum).getId()));
//					System.out.println("删除成功");
////					for(Moment moments_:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
////						if(moments_.getId().equals(((Moment)wnum).getId())){
////							ManageWechat.getWechat(user.getId()).user.getAllmonents().remove(moments_);
////							break;
////						}
////					}
//				} catch (NumberFormatException | IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//			}
//		});
//		
//		Text text = new Text(wtools, SWT.NONE);
//		text.setBackground(SWTResourceManager.getColor(250, 250, 250));
//		text.setBounds(78, 8, 192, 26);
//		text.setVisible(false);
//		
//		
//		com.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseUp(MouseEvent e) {
//				text.setVisible(true);;
//				send.setVisible(true);
//				del.setVisible(false);
//				com.setVisible(false);
//			}
//		});
//		send.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseUp(MouseEvent e) {			
//				
//				String s= WordMo.getSize()+"";				
//				String h =s.substring(12, 15);
//				String t = text.getText();
//				if(!(t==null||t.length()==0)){
//					int m_id = Integer.parseInt(((Moment)wnum).getId());
//					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					String sendTime = df.format(new Date());
//					Comment comm = new Comment(m_id+"", t, user.getId(), user.getName(), sendTime);
//					viewStyle.addWcom(m,moments,WordMo, user.getName()+": "+t,Integer.parseInt(h),boo,comm,user.getId());
//					try {
//						ManageClientThread.getClientConServerThread(user.getId()).addComment(comm);
//						for(Moment monment:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
//							if(monment.getId().equals(m_id+"")){
//								monment.getConments().add(comm);
//							}
//						}
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}	
//				text.setText("");
//				text.setVisible(false);
//				send.setVisible(false);
//				com.setVisible(true);
//				if(boo){
//				del.setVisible(true);							//如果是自己的朋友圈显示删除按钮
//			}
//			
//			}
//		});
//		like.addMouseListener(new MouseAdapter() {
//			int Hlikenum = Integer.parseInt(likenum.getText());
//			public void mouseUp(MouseEvent e) {
//				if(like.getImage()==likeico){
//					like.setImage(likeed);
//					likenum.setText(Hlikenum+1+"");
//					Hlikenum+=1;
//					try {
//						ManageClientThread.getClientConServerThread(user.getId()).addLike
//						(Integer.parseInt(((Moment)wnum).getId()));
//						for(Moment moments_:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
//							if(moments_.getId().equals(((Moment)wnum).getId())){
//								moments_.setLike(moments_.getLike()+1);
//								break;
//							}
//						}
//					} catch (NumberFormatException | IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					
//					
//				}
//				else{
//					like.setImage(likeico);
//					likenum.setText(Hlikenum-1+"");
//					Hlikenum-=1;
//					try {
//						ManageClientThread.getClientConServerThread(user.getId()).deleteLike
//						(Integer.parseInt(((Moment)wnum).getId()));
//						for(Moment moments_:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
//							if(moments_.getId().equals(((Moment)wnum).getId())){
//								moments_.setLike(moments_.getLike()-1);
//								break;
//							}
//						}
//					} catch (NumberFormatException | IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}			
//			}
//			
//		});
//		moments.layout();
//		for(Comment c:((Moment)wnum).getConments()){
//			String s= WordMo.getSize()+"";				
//			String h =s.substring(12, 15);
//			viewStyle.addWcom(m,moments,WordMo, c.getName()+": "+c.getContent(),Integer.parseInt(h),boo,c,user.getId());
//		}
//		
//	}
	public static void addPcom(ScrolledComposite m,Composite moments,Composite PicMo,String t,int pmh,
			boolean boo,Comment comment,String user_id){			//图片朋友圈评论
		PicMo.setLayoutData(new RowData(347, pmh+23));
		m.setMinHeight(m.getMinHeight()+25);;
		Label lblNewLabel_1 = new Label(PicMo, SWT.WRAP);
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(105, 105, 105));
		lblNewLabel_1.setLayoutData(new RowData(339, 20));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		lblNewLabel_1.setText("    "+t);
		
		lblNewLabel_1.addListener(SWT.MouseDoubleClick, new Listener() {  
	    	
		    public void handleEvent(Event event) {  
		        if(event.button != 1) { 
		            return;  
		        }  
		    	
		        
		        
		        if(boo){
		        	String s= PicMo.getSize()+"";				
					String h =s.substring(12, 15);
					int hh =Integer.parseInt(h);
					PicMo.setLayoutData(new RowData(347, hh-25));
					m.setMinHeight(m.getMinHeight()+25);;	
					lblNewLabel_1.dispose();
			        PicMo.layout();
			        moments.layout();
			        try {
						ManageClientThread.getClientConServerThread
						(user_id).delComment(Integer.parseInt(comment.getId()));
						for(Moment monmet_:ManageWechat.getWechat(user_id).user.getAllmonents()){
							for(Comment comment_:monmet_.getConments()){
								if(comment_.getId().equals(comment.getId())){
									monmet_.getConments().remove(comment_);
									break;
								}
							}
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        System.out.println("删除评论啦");
		        }
		        else{
		        	System.out.println("你没有权限删除评论");
		        }
		    }  
		});  
		
		moments.layout();
		PicMo.layout();
	}
	public static void addPicmom(ScrolledComposite m,Composite moments
			,String pid,org.eclipse.swt.graphics.Image phead,String pname,
			String pcon,org.eclipse.swt.graphics.Image ppic,Object pnum,OrdinaryUser user,boolean boo){                //图片朋友圈
		Composite PicMo = new Composite(moments, SWT.NONE);
		PicMo.setLayoutData(new RowData(352, 258));
		PicMo.setLayout(new RowLayout(SWT.VERTICAL));
		
		Composite bbbbbb = new Composite(PicMo, SWT.NONE);											//间隔条
		bbbbbb.setLayoutData(new RowData(347, 3));
		
		Label label_11 = new Label(bbbbbb, SWT.NONE);
		label_11.setBackground(SWTResourceManager.getColor(253,253,253));
		label_11.setBounds(35, 0, 278, 3);
		
		Composite Pmom = new Composite(PicMo, SWT.NONE);
		Pmom.setLayoutData(new RowData(SWT.DEFAULT, 202));
		
		Label pmhead = new Label(Pmom, SWT.NONE);
		pmhead.setImage(phead);
		pmhead.setBounds(10, 5, 50, 50);
		
		Label pmname = new Label(Pmom, SWT.NONE);
		pmname.setText(pname);
		pmname.setForeground(SWTResourceManager.getColor(70, 130, 180));
		pmname.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		pmname.setBounds(72, 5, 110, 27);
		
		Label pmcon = new Label(Pmom, SWT.WRAP);
		pmcon.setText(pcon);
		pmcon.setBounds(82, 38, 236, 43);
		
		Label pmid = new Label(Pmom, SWT.NONE);
		pmid.setVisible(false);
		pmid.setBounds(316, 5, 21, 20);
		
		Label pmtime = new Label(Pmom, SWT.NONE);
		pmtime.setText(getDistance(((Moment)pnum).getTime()));
		pmtime.setForeground(SWTResourceManager.getColor(128, 128, 128));
		pmtime.setBounds(289, 5, 58, 20);
		
		Label pmpic = new Label(Pmom, SWT.NONE);
		pmpic.setBounds(82, 90, 137, 95);
		pmpic.setImage(ppic);
		
		pmpic.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e){
				try {
					Runtime.getRuntime().exec("rundll32 c:\\Windows\\System32\\shimgvw.dll,ImageView_Fullscreen "+"C:\\Users\\10261\\Desktop\\img\\e1.png");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});	
		
		Composite ptools = new Composite(PicMo, SWT.NONE);
		
		Label plikenum = new Label(ptools, SWT.NONE);
		plikenum.setText(((Moment)pnum).getLike()+"");
		plikenum.setForeground(SWTResourceManager.getColor(50, 205, 50));
		plikenum.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		plikenum.setAlignment(SWT.CENTER);
		plikenum.setBounds(0, 8, 35, 32);
		
		Label plike = new Label(ptools, SWT.NONE);
		plike.setImage(SWTResourceManager.getImage(Frdinfo.class, "/img/like.png"));
		plike.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		plike.setBounds(37, 0, 35, 40);
		
		Label pcom = new Label(ptools, SWT.NONE);
		pcom.setText("\u8BC4\u8BBA");
		pcom.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		pcom.setBounds(312, 10, 35, 20);
		
		Label psend = new Label(ptools, SWT.NONE);
		psend.setVisible(false);
		psend.setText("\u53D1\u9001");
		psend.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		psend.setBounds(312, 10, 35, 20);
		
		Label pdel = new Label(ptools, SWT.NONE);
	
		pdel.setText("\u5220\u9664");
		pdel.setForeground(SWTResourceManager.getColor(220, 20, 60));
		pdel.setBounds(276, 10, 35, 20);
		pdel.setVisible(false);
		if(boo){
			pdel.setVisible(true);							//如果是自己的朋友圈显示删除按钮
		}	
		
		pdel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
//				ptext.setVisible(true);;
//				psend.setVisible(true);
//				pdel.setVisible(false);
//				pcom.setVisible(false);
				try {
					ManageClientThread.getClientConServerThread(user.getId()).delMement
					(Integer.parseInt(((Moment)pnum).getId()));
//					for(Moment moments_:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
//						if(moments_.getId().equals(((Moment)pnum).getId())){
//							ManageWechat.getWechat(user.getId()).user.getAllmonents().remove(moments_);
//							break;
//						}
//					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		Text ptext = new Text(ptools, SWT.NONE);
		ptext.setVisible(false);
		ptext.setBackground(SWTResourceManager.getColor(250, 250, 250));
		ptext.setBounds(78, 8, 192, 26);
		
		pcom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				ptext.setVisible(true);;
				psend.setVisible(true);
				pdel.setVisible(false);
				pcom.setVisible(false);
			}
		});
		psend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {			
				String s= PicMo.getSize()+"";				
				String h =s.substring(12, 15);
				String t = ptext.getText();
				if(!(t==null||t.length()==0)){
					int m_id = Integer.parseInt(((Moment)pnum).getId());
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sendTime = df.format(new Date());
					Comment comm = new Comment(m_id+"", t, user.getId(), user.getName(), sendTime);
					viewStyle.addPcom
					(m,moments,PicMo, user.getName()+": "+t,Integer.parseInt(h),boo,comm,user.getId());
					try {
						ManageClientThread.getClientConServerThread(user.getId()).addComment(comm);
						for(Moment monment:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
							if(monment.getId().equals(m_id+"")){
								monment.getConments().add(comm);
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
				ptext.setText("");
				ptext.setVisible(false);
				psend.setVisible(false);
				pcom.setVisible(true);
//				if(){
//				del.setVisible(true);							如果是自己的朋友圈显示删除按钮
//			}
			
			}
		});
		plike.addMouseListener(new MouseAdapter() {
			int Hlikenum = Integer.parseInt(plikenum.getText());
			public void mouseUp(MouseEvent e) {
				if(plike.getImage()==likeico){
					plike.setImage(likeed);
					plikenum.setText(Hlikenum+1+"");
					Hlikenum+=1;
					try {
						ManageClientThread.getClientConServerThread(user.getId()).addLike
						(Integer.parseInt(((Moment)pnum).getId()));
						for(Moment moments_:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
							if(moments_.getId().equals(((Moment)pnum).getId())){
								moments_.setLike(moments_.getLike()+1);
								break;
							}
						}
					} catch (NumberFormatException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else{
					plike.setImage(likeico);
					plikenum.setText(Hlikenum-1+"");
					Hlikenum-=1;

					try {
						ManageClientThread.getClientConServerThread(user.getId()).deleteLike
						(Integer.parseInt(((Moment)pnum).getId()));
						for(Moment moments_:ManageWechat.getWechat(user.getId()).user.getAllmonents()){
							if(moments_.getId().equals(((Moment)pnum).getId())){
								moments_.setLike(moments_.getLike()-1);
								break;
							}
						}
					} catch (NumberFormatException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}			
			}
			
		});	
		moments.layout();
		m.setMinHeight(m.getMinHeight()+370);
		for(Comment c:((Moment)pnum).getConments()){
			String s= PicMo.getSize()+"";				
			String h =s.substring(12, 15);
			viewStyle.addPcom(m,moments,PicMo, c.getName()+": "+c.getContent(),Integer.parseInt(h),boo,c,user.getId());
		}
	}
}
