package view;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.DocumentException;
import org.eclipse.core.internal.runtime.PerformanceStatsProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.MyMouseAdapter;
import org.eclipse.wb.swt.SWTResourceManager;

import com.wechat.client.ManageClientThread;
import com.wechat.client.ManageWechat;
import com.wechat.clientThreadimpl.MessageType;
import com.wechat.model.Bulletin;
import com.wechat.model.EnterpriseUser;
import com.wechat.model.Group;
import com.wechat.model.HistorySession;
import com.wechat.model.Message;
import com.wechat.model.OrdinaryUser;
import com.wechat.model.User;
import com.wechat.util.CityUtil;
import com.wechat.util.ManageChating;
import com.wechat.util.ManageFrdinfo;
import com.wechat.util.ManageScrolledComposite;
import com.wechat.util.ManageSessionMessage;
import com.wechat.util.ManageSessionSendTime;
import com.wechat.util.SoundRecording;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.GESTUREINFO;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseTrackAdapter;

public class Index extends Thread implements MessageType{

	protected static Shell Wechat;
	private Text search;
	private Text newname;
	private Text newmail;
	private Text newremark;
	private Text user_name;
	private Text user_id;
	private Text user_mail;
	private Text remark;
	private Text newpw;
	private Text text;
	private Text code;
	private static Composite right ;
	 static Composite chat;
	private static Composite frdinfo  ;
	private static Label frdhead;
	private static Label frdname;
	private static Label frdsex ;
	private static Text frdnum;
	private static Text frdmail;
	private static Label frdprovince;
	private static Label frdcity;
	private static Label frdremark ;
	private static Label frdsend ;
	private static Label delet ;
	private static Label addfriend;
	private static Label exitadd ;
	private static Composite userinfo;
	private static Composite updateinfo;
	private static Composite offical;
	private static Composite groupp;
	private static Label head;
	private static Composite chatcomposite;
	private static  ScrolledComposite chatList ;
	private static String nowtime= viewStyle.getTime();
	private static Composite CreatGroup;
	private Text ngname;
	private Text ngid;
	private Text ngnc;
	private static Composite officals ;
	private static Label newhead;
	private static ScrolledComposite firendList;
	private static  Composite fcomposite;
	
	public OrdinaryUser user = null;
	OrdinaryUser temp = null;
	EnterpriseUser e_temp = null;
	Group g_temp = null;
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public Index(OrdinaryUser user){
		this.user = user;
		System.out.println(user.getHeadimg());
	}
	public Index(){
		
	}
	public static void main(String[] args) {
		try {
			Index window = new Index();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void run(){
		Display.getDefault().asyncExec(this.open1(this));
		
	}
	private Runnable open1(Index index) {
		return new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				index.open();
			}
		};
	}
	public void open() {
		Display display = Display.getDefault();
		createContents();
		Wechat.open();
		Wechat.layout();
		while (!Wechat.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */		

	
	protected void createContents() {
		Wechat = new Shell(SWT.NONE);
		Wechat.setImage(SWTResourceManager.getImage(Index.class, "/img/ico.png"));

		right = new Composite(Wechat, SWT.NONE);
		chat = new Composite(right, SWT.NONE);	
		frdinfo = new Composite(right, SWT.NONE);    
		frdhead = new Label(frdinfo, SWT.NONE);
		frdname = new Label(frdinfo, SWT.NONE);
		frdsex = new Label(frdinfo, SWT.NONE);
		frdnum = new Text(frdinfo, SWT.READ_ONLY);
		frdmail = new Text(frdinfo, SWT.READ_ONLY | SWT.CENTER);
		frdprovince = new Label(frdinfo, SWT.NONE);
		frdcity = new Label(frdinfo, SWT.NONE);
		frdremark = new Label(frdinfo, SWT.WRAP);
		
		viewStyle.setbgview(Wechat);
		Wechat.setBackgroundImage(SWTResourceManager.getImage(Index.class, "/img/chat-bg.png"));
		Wechat.setSize(1210, 745);
		Wechat.setText("SWT Application");
		FormLayout fl_Wechat = new FormLayout();
		Wechat.setLayout(fl_Wechat);
		

		head = new Label(Wechat, SWT.NONE);
		
		head.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
				head.setToolTipText("设置个人信息");
			}
		});
		
		viewStyle.updeLabelBg(head, user.getHeadimg());;
		FormData fd_head = new FormData();
		fd_head.bottom = new FormAttachment(0, 54);
		fd_head.right = new FormAttachment(0, 54);
		fd_head.top = new FormAttachment(0, 10);
		fd_head.left = new FormAttachment(0, 10);
		head.setLayoutData(fd_head);
		
		
		Label X = new Label(Wechat, SWT.CENTER);
		X.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Wechat.close();
				
			}
		});
		X.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		FormData fd_X = new FormData();
		fd_X.left = new FormAttachment(100, -30);
		fd_X.top = new FormAttachment(0);
		X.setLayoutData(fd_X);
		X.setText("X");
		
		Label C1 = new Label(Wechat, SWT.NONE);
		Label set = new Label(Wechat, SWT.NONE);
		viewStyle.setLabelBg(set, "set");
		set.setText("      ");
		FormData fd_set = new FormData();
		fd_set.top = new FormAttachment(100, -60);
		fd_set.right = new FormAttachment(C1, 0, SWT.RIGHT);
		fd_set.bottom = new FormAttachment(100, -30);
		fd_set.left = new FormAttachment(C1, 0, SWT.LEFT);
		set.setLayoutData(fd_set);
		FormData fd_C1 = new FormData();
		fd_C1.bottom = new FormAttachment(0, 113);
		fd_C1.right = new FormAttachment(0, 65);
		fd_C1.top = new FormAttachment(0, 90);
		fd_C1.left = new FormAttachment(0);
		C1.setLayoutData(fd_C1);
				
		Label C2 = new Label(Wechat, SWT.NONE);
		FormData fd_C2 = new FormData();
		fd_C2.right = new FormAttachment(0, 65);
		fd_C2.top = new FormAttachment(0, 155);
		fd_C2.left = new FormAttachment(0);
		C2.setLayoutData(fd_C2);
		viewStyle.setLabelBg(C1, "msg");
		viewStyle.setLabelBg(C2, "frd");
		
		Label C3 = new Label(Wechat, SWT.NONE);
		C3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				viewStyle.setLabelBg(C3, "c3-down");
				viewStyle.setLabelBg(C1, "msg");
				viewStyle.setLabelBg(C2, "frd");
				
			}
			@Override
			public void mouseUp(MouseEvent e) {
				viewStyle.setLabelBg(C3, "c3");
				if(viewStyle.frdinfo()){
					System.out.println("已经打开");
				}
				else{
					
					Frdinfo ffff = new Frdinfo();
					ManageFrdinfo.addFrdinfo(user.getId(), ffff);
					ffff.open(user);
				}
			}
		});
		C3.setText("         ");
		viewStyle.setLabelBg(C3, "c3");
		FormData fd_C3 = new FormData();
		fd_C3.bottom = new FormAttachment(C2, 73, SWT.BOTTOM);
		fd_C3.left = new FormAttachment(C1, 0, SWT.LEFT);
		fd_C3.right = new FormAttachment(C1, 0, SWT.RIGHT);
		fd_C3.top = new FormAttachment(C2, 43);
		fd_C3.width = 55;
		fd_C3.height = 25;
		C3.setLayoutData(fd_C3);
		

		search = new Text(Wechat, SWT.NONE);
		FormData fd_search = new FormData();
		fd_search.right = new FormAttachment(0, 271);
		fd_search.top = new FormAttachment(0, 25);
		search.setLayoutData(fd_search);

		
		viewStyle.setTextRGB(search, 220, 217, 216);

		Label addbt = new Label(Wechat, SWT.NONE);
		FormData fd_addbt = new FormData();
		fd_addbt.bottom = new FormAttachment(0, 50);
		fd_addbt.right = new FormAttachment(0, 311);
		fd_addbt.top = new FormAttachment(0, 25);
		fd_addbt.left = new FormAttachment(0, 286);
		addbt.setLayoutData(fd_addbt);
		viewStyle.setLabelBg(addbt, "add");
		
		 chatList = new ScrolledComposite(Wechat, SWT.H_SCROLL | SWT.V_SCROLL);
		chatList.setExpandHorizontal(true);
		fd_search.left = new FormAttachment(chatList, 0, SWT.LEFT);
		chatList.setExpandVertical(true);
		chatList.setMinHeight(0);///////**************************************************************************	////
//		scrolledComposite.setMinHeight(670);//     																		//9条信息，无滚动条
		FormData fd_chatList = new FormData();
		fd_chatList.top = new FormAttachment(addbt, 6);
		fd_chatList.bottom = new FormAttachment(100);
		fd_chatList.right = new FormAttachment(addbt, 0, SWT.RIGHT);
		fd_chatList.left = new FormAttachment(C1, 6);
		chatList.setLayoutData(fd_chatList);
		chatList.setExpandVertical(true);
		
		
		
		chatcomposite = new Composite(chatList, SWT.NONE);	
		chatList.setContent(chatcomposite);
		chatcomposite.setLayout(new RowLayout(SWT.VERTICAL));
		

      
		FormData fd_GroupPeople = new FormData();
		fd_GroupPeople.top = new FormAttachment(addbt, 1);
		fd_GroupPeople.right = new FormAttachment(addbt, 0, SWT.RIGHT);
		fd_GroupPeople.left = new FormAttachment(C1, 6);
		fd_GroupPeople.bottom = new FormAttachment(100, -10);
		chatcomposite.setLayoutData(fd_GroupPeople);
		
		 firendList = new ScrolledComposite(Wechat, SWT.H_SCROLL | SWT.V_SCROLL);	//好友滚动条
		firendList.setExpandVertical(true);
		firendList.setMinHeight(160);	
		FormData fd_firendList = new FormData();
		fd_firendList.top = new FormAttachment(addbt, 6);
		fd_firendList.bottom = new FormAttachment(100);
		fd_firendList.right = new FormAttachment(addbt, 0, SWT.RIGHT);
		fd_firendList.left = new FormAttachment(C1, 6);
		firendList.setLayoutData(fd_firendList);
		firendList.setExpandHorizontal(true);
		firendList.setExpandVertical(true);

		 fcomposite = new Composite(firendList, SWT.NONE);	
		firendList.setContent(fcomposite);
		fcomposite.setLayout(new RowLayout(SWT.HORIZONTAL));
		firendList.setVisible(false);
		Label officelbt = new Label(fcomposite, SWT.NONE);
		officelbt.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
				officelbt.setToolTipText("查看公众号");
			}
		});

		officelbt.setLayoutData(new RowData(218, 78));
		viewStyle.setLabelBg(officelbt, "com");
		officelbt.setText("    ");
      
//		FormData fd_composite = new FormData();
//		fd_composite.top = new FormAttachment(add, 1);
//		fd_composite.right = new FormAttachment(add, 0, SWT.RIGHT);
//		fd_composite.left = new FormAttachment(C1, 6);
//		fd_composite.bottom = new FormAttachment(100, -10);
//		composite.setLayoutData(fd_composite);
		if(user.getFriends().size()!=0){
			for(User u:user.getFriends()){
				viewStyle.addfrdone(firendList,fcomposite, SWTResourceManager.getImage(u.getHeadimg()), u);
			}
		}

		fd_X.right = new FormAttachment(right, 0, SWT.RIGHT);
		right.setLayout(new StackLayout());
										//一个聊天页
		StackLayout chatlayout= new StackLayout();
		chat.setLayout(chatlayout);	
		chat.setVisible(true);
		
//		Composite chat1 = new Composite(chat, SWT.NONE);
//		Label chatingName = new Label(chat1, SWT.NONE);
//	
//		chatingName.setText("FireMan");
//		chatingName.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
//		chatingName.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
//		chatingName.setBounds(29, -1, 139, 35);
//		
//		ScrolledComposite chatscr = new ScrolledComposite(chat1, SWT.H_SCROLL | SWT.V_SCROLL);
//		chatscr.setBounds(29, 40, 832, 532);
//		chatscr.setExpandHorizontal(true);
//		chatscr.setExpandVertical(true);
//		
//		Composite chating = new Composite(chatscr, SWT.NONE);
//		chating.setBackground(SWTResourceManager.getColor(245,245,245));
//		chating.setLayout(new RowLayout(SWT.VERTICAL));
//		chatscr.setContent(chating);
//		chatscr.setMinSize(chating.computeSize(SWT.DEFAULT, SWT.DEFAULT));
//		chatscr.setMinSize(new Point(0, 999));
//		
//		
//		Composite edittext = new Composite(chat1, SWT.NONE);												
//		edittext.setBounds(0, 575, 891, 143);
//		edittext.setBackground(SWTResourceManager.getColor(255,255,255));
//		
//		text = new Text(edittext, SWT.WRAP);
//		text.setBounds(30, 35, 810, 77);		
//		Button subsend = new Button(edittext, SWT.NONE);
//		subsend.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//			}
//		});
//		subsend.setText("\u53D1\u9001");
//		subsend.setBounds(774, 113, 66, 23);
//		
//		Label e1 = new Label(edittext, SWT.NONE);
//		e1.setBounds(30, 9, 33, 20);
//		
//		Label e2 = new Label(edittext, SWT.NONE);
//		e2.setBounds(69, 9, 33, 20);
//		
//		Label e3 = new Label(edittext, SWT.NONE);
//		e3.setBounds(108, 9, 33, 20);
//		
//		Label lblNewLabel = new Label(edittext, SWT.NONE);
//		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
//		lblNewLabel.setBounds(153, 9, 60, 20);
//		lblNewLabel.setText("发送文件");
//		subsend.addMouseListener(new MouseAdapter() {													//消息发送按钮
//			@Override
//			public void mouseUp(MouseEvent e) {
//				String msg = text.getText();
//				org.eclipse.swt.graphics.Image myheadimg = head.getBackgroundImage();
//				String time= new String("11:11");
//				viewStyle.NewMyMsg(chating, myheadimg, time, msg);
//			}
//		});
//		Label mymsg = new Label(chating, SWT.RIGHT); // 内容
//		mymsg.setText("");
//		mymsg.setBackground(SWTResourceManager.getColor(245, 245, 245));
//		mymsg.setBounds(365, 29, 369, 44);
//		
		offical = new Composite(right, SWT.NONE);
		
		
//		Composite chatone= new Composite(chatcomposite, SWT.NONE);						//聊天列表+1
//		chatone.setLayoutData(new RowData(234, 65));
//		
//		Label chathead= new Label(chatone, SWT.NONE);
//		chathead.setBounds(10, 10, 44, 44);
//		viewStyle.setLabelBg(chathead, "exhead");
//		Label chatname = new Label(chatone, SWT.NONE);
//		chatname.setText("FireMan");
//		chatname.setBounds(60, 10, 95, 20);
		System.out.println(user.getSession().size());
		//初始化会话
		if(user.getSession().size()!=0){
			for(HistorySession session:user.getSession()){
				if(session.getMessages()==null||session.getMessages().size()==0){
					viewStyle.addchatone(Wechat, chatList,chat, head, chatcomposite,
							SWTResourceManager.getImage(session.getFriend().getHeadimg())
							, session.getFriend().getName()
							,session.getTime().toString().substring(5,19)
							, "",user,session.getFriend().getId());
				}
				else if(session.getMessages().get(session.getMessages().size()-1).getType()==file){
					viewStyle.addchatone(Wechat,chatList, chat, head, chatcomposite,
							SWTResourceManager.getImage(session.getFriend().getHeadimg())
							, session.getFriend().getName()
							,(session.getMessages()==null||session.getMessages().size()==0)
							?session.getTime().toString().substring(5,19)
									:session.getMessages().get(session.getMessages().size()-1).getSendTime().substring(5,19)
							, "文件"
									,user
									,session.getFriend().getId());
				}
				else if(session.getMessages().get(session.getMessages().size()-1).getType()==VoiceMe)
				{
					viewStyle.addchatone(Wechat,chatList, chat, head, chatcomposite,
							SWTResourceManager.getImage(session.getFriend().getHeadimg())
							, session.getFriend().getName()
							,(session.getMessages()==null||session.getMessages().size()==0)
							?session.getTime().toString().substring(5,19)
									:session.getMessages().get(session.getMessages().size()-1).getSendTime().substring(5,19)
							, "语音"
									,user
									,session.getFriend().getId());
				}
				else{
					viewStyle.addchatone(Wechat, chatList,chat, head, chatcomposite,
							SWTResourceManager.getImage(session.getFriend().getHeadimg())
							, session.getFriend().getName()
							,(session.getMessages()==null||session.getMessages().size()==0)
							?session.getTime().toString().substring(5,19)
									:session.getMessages().get(session.getMessages().size()-1).getSendTime().substring(5,19)
							, (session.getMessages()==null||session.getMessages().size()==0)
							?"":session.getMessages().get(session.getMessages().size()-1).getContent()
									,user
									,session.getFriend().getId());
				}
				
			}
		}
		
//		Label lastnews = new Label(chatone, SWT.NONE);
//		lastnews.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
//		lastnews.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
//		lastnews.setBounds(60, 36, 152, 20);
//		lastnews.setText("\u6700\u65B0\u4FE1\u606F~~~~");
//		
//		Label lasttime = new Label(chatone, SWT.NONE);
//		lasttime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
//		lasttime.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
//		lasttime.setBounds(161, 10, 51, 20);
//		lasttime.setText("18\uFF1A20");
//		String n = chatname.getText();
//		chatList.setBackgroundMode(SWT.INHERIT_DEFAULT);    // label透明条件

		
		
		
//		Composite[] compositeq = new Composite[20];									//聊天信息列表
//		Label[] labelq = new Label[20];
//		Label[] label1 = new Label[20];
//
//		for(int i=0;i<10;i++){
//			compositeq[i] = new Composite(composite, SWT.NONE);
//			compositeq[i].setLayoutData(new RowData(234, 65));
//			
//			labelq[i] = new Label(compositeq[i], SWT.NONE);
//			labelq[i].setBounds(10, 10, 44, 44);
//			viewStyle.setLabelBg(labelq[i], "exhead");
//			label1[i] = new Label(compositeq[i], SWT.NONE);
//			label1[i].setText("FireMan"+i);
//			label1[i].setBounds(87, 10, 76, 20);
//			String n = label1[i].getText();
//			
//			compositeq[i].addMouseListener(new MouseAdapter() {
//				@Override
//				public void mouseDown(MouseEvent e) {
//					chatName.setText(n);
//					chat.setVisible(true);
//				}
//			});
//		}
		
		FormData fd_right = new FormData();
		fd_right.top = new FormAttachment(X, 5);
		fd_right.bottom = new FormAttachment(100);
		fd_right.left = new FormAttachment(addbt, 6);
		fd_right.right = new FormAttachment(100);
		right.setLayoutData(fd_right);
		
                              //点击好友头像右侧出现的信息		
		frdhead= new Label(frdinfo, SWT.NONE);
		frdhead.setBounds(420, 122, 64, 64);

		frdname.setAlignment(SWT.CENTER);
		frdname.setText("null");
		frdname.setForeground(SWTResourceManager.getColor(70, 130, 180));
		frdname.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		frdname.setBounds(348, 211, 212, 35);
		
		frdsend = new Label(frdinfo, SWT.NONE);
		delet = new Label(frdinfo, SWT.NONE);
		delet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				System.out.println(frdnum.getText());
				for(OrdinaryUser tu:user.getFriends()){
					if(tu.getId().equals(frdnum.getText())){
						temp = tu;
					}
				}
				ManageClientThread.getClientConServerThread(user.getId()).delUser(temp.getId());
			}
		});
		addfriend = new Label(frdinfo, SWT.NONE);
		addfriend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				ManageClientThread.getClientConServerThread(user.getId()).addUser(temp.getId());
			}
		});
		
		frdsend.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		frdsend.setForeground(SWTResourceManager.getColor(50, 205, 50));
		frdsend.setBounds(348, 499, 103, 29);
		frdsend.setText("\u53D1\u9001\u4FE1\u606F");
		
		delet.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		delet.setText("\u5220\u9664\u597D\u53CB");
		delet.setForeground(SWTResourceManager.getColor(255, 69, 0));
		delet.setBounds(513, 499, 72, 24);
		

		frdsex.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		frdsex.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		frdsex.setBounds(436, 249, 33, 35);
		frdsex.setSize(15, 15);
		viewStyle.setLabelBg(frdsex, "man");
		
		Label label_12 = new Label(frdinfo, SWT.NONE);
		label_12.setText("\u5E10\u53F7\uFF1A");
		label_12.setBounds(342, 270, 64, 26);
		
		frdnum.setText("null");
		frdnum.setBounds(412, 270, 173, 35);
		
		Label label_14 = new Label(frdinfo, SWT.NONE);
		label_14.setText("\u90AE\u7BB1\uFF1A");
		label_14.setBounds(342, 302, 64, 26);
		

		frdmail.setText("null");
		frdmail.setBounds(412, 302, 173, 35);
		
		Label label_15 = new Label(frdinfo, SWT.NONE);
		label_15.setText("\u5730\u533A\uFF1A");
		label_15.setBounds(342, 341, 64, 20);
		frdprovince.setText("\u5E7F\u4E1C");
		frdprovince.setBounds(420, 341, 64, 20);

		frdcity.setText("\u60E0\u5DDE");
		frdcity.setBounds(497, 341, 64, 20);
		
		Label label_18 = new Label(frdinfo, SWT.NONE);
		label_18.setText("\u4E2A\u6027\u7B7E\u540D\uFF1A");
		label_18.setBounds(342, 381, 64, 26);
	
		frdremark.setBounds(420, 381, 179, 99);
		frdremark.setText("\u8FD9\u4E2A\u4EBA\u5F88\u61D2\uFF0C\u4EC0\u4E48\u90FD\u6CA1\u6709\u7559\u4E0B~");
		

		addfriend.setText("\u6DFB\u52A0\u597D\u53CB");
		addfriend.setForeground(SWTResourceManager.getColor(50, 205, 50));
		addfriend.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		addfriend.setBounds(348, 499, 72, 29);
		addfriend.setVisible(false);
		
		exitadd = new Label(frdinfo, SWT.NONE);
		exitadd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Turnfrdinfo(1);
				frdinfo.setVisible(false);
				System.out.println("关闭啦");
			}
		});
		exitadd.setAlignment(SWT.CENTER);
		exitadd.setVisible(false);
		exitadd.setText("\u8FD4\u56DE");
		exitadd.setForeground(SWTResourceManager.getColor(50, 205, 50));
		exitadd.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		exitadd.setBounds(513, 499, 72, 29);
		
		
		userinfo = new Composite(right, SWT.NONE);
		updateinfo = new Composite(right, SWT.NONE);//个人信息
		userinfo.setForeground(SWTResourceManager.getColor(106, 90, 205));
			
													 //设置个人信息	
		Button button_1 = new Button(userinfo, SWT.NONE);
		
		button_1.setBounds(387, 526, 98, 30);
		button_1.setText("\u66F4\u6539\u4FE1\u606F");
		
		Label usersex = new Label(userinfo, SWT.NONE);
		usersex.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		usersex.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		usersex.setBounds(566, 273, 26, 20);
		usersex.setSize(20,20);
		viewStyle.setLabelBg(usersex, "man");
		
		Label userhead = new Label(userinfo, SWT.NORMAL);
		userhead.setBounds(407, 155, 55, 54);
		viewStyle.updeLabelBg(userhead, user.getHeadimg());
		
		Label label_6 = new Label(userinfo, SWT.NONE);
		label_6.setBounds(317, 273, 64, 26);
		label_6.setText("\u7528\u6237\u540D\uFF1A");
		
		user_name = new Text(userinfo, SWT.READ_ONLY | SWT.CENTER);
		user_name.setText("FireMan");
		user_name.setBounds(387, 273, 173, 35);
		
		Label label_7 = new Label(userinfo, SWT.NONE);
		label_7.setText("\u5E10\u53F7\uFF1A");
		label_7.setBounds(317, 227, 64, 26);
		
		user_id = new Text(userinfo, SWT.READ_ONLY | SWT.CENTER);
		user_id.setText("1026177662");
		user_id.setBounds(387, 227, 173, 35);
		
		Label label_8 = new Label(userinfo, SWT.NONE);
		label_8.setText("\u90AE\u7BB1\uFF1A");
		label_8.setBounds(317, 314, 64, 26);
		
		user_mail = new Text(userinfo, SWT.READ_ONLY | SWT.CENTER);
		user_mail.setText("1026177662@qq.com");
		user_mail.setBounds(387, 314, 173, 35);
		
		remark = new Text(userinfo, SWT.READ_ONLY | SWT.WRAP | SWT.CENTER);
		remark.setText("\u8FD9\u4E2A\u4EBA\u5F88\u61D2\uFF0C\u4EC0\u4E48\u90FD\u6CA1\u6709\u7559\u4E0B~");
		remark.setBounds(387, 393, 210, 128);
		
		Label label_9 = new Label(userinfo, SWT.NONE);
		label_9.setText("\u4E2A\u6027\u7B7E\u540D\uFF1A");
		label_9.setBounds(317, 393, 64, 26);
		
		Label label_10 = new Label(userinfo, SWT.NONE);
		label_10.setText("\u5730\u533A\uFF1A");
		label_10.setBounds(317, 353, 64, 20);
		
		Label province = new Label(userinfo, SWT.NONE);
		province.setText("\u5E7F\u4E1C");
		province.setBounds(397, 355, 64, 20);
		
		Label city = new Label(userinfo, SWT.NONE);
		city.setText("\u60E0\u5DDE");
		city.setBounds(474, 355, 64, 20);
		
		newhead = new Label(updateinfo, SWT.NONE);
		newhead.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				FileDialog headfile  = new FileDialog(Wechat, SWT.OPEN);
				headfile.setText("选择头像文件");
				headfile.open();
				String path = headfile.getFilterPath();
				if(headfile.getFileName().endsWith(".jpg")||headfile.getFileName().endsWith(".png")){
					path = path+"\\"+headfile.getFileName();
					System.out.println(path);
					try {
						ManageClientThread.
						getClientConServerThread(user.getId()).updaHeadImg(user.getId(), path);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else{
					MessageBox smsg = new MessageBox(Wechat, SWT.ICON_INFORMATION);
					smsg.setText("提示：");
					smsg.setMessage("图片文件格式不对，必须是jpg或者png！");
					smsg.open();
				}
//				System.out.println();
			}
		});
		newhead.setBackgroundImage(head.getBackgroundImage());
		newhead.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
				userhead.setToolTipText("点击更改头像");
				
			}
			
		});
		newhead.setBounds(445, 124, 65, 66);	
		viewStyle.updeLabelBg(newhead, user.getHeadimg());
		newname = new Text(updateinfo, SWT.NONE);
		viewStyle.setTextRGB(newname, 246, 246, 246);
		newname.setText("FireMan");
		newname.setForeground(SWTResourceManager.getColor(70, 130, 180));
		newname.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.NORMAL));
		newname.setBounds(398, 216, 204, 26);
		
		newmail = new Text(updateinfo, SWT.NONE);
		viewStyle.setTextRGB(newmail, 246, 246, 246);
		newmail.setText("10261776622qq.com");
		newmail.setForeground(SWTResourceManager.getColor(112, 128, 144));
		newmail.setBounds(398, 342, 204, 26);
		
		Label label_2 = new Label(updateinfo, SWT.NONE);
		
		label_2.setBounds(299, 216, 76, 20);
		label_2.setText("\u65B0\u7528\u6237\u540D\uFF1A");
		
		Label label_4 = new Label(updateinfo, SWT.NONE);
		label_4.setBounds(299, 342, 76, 26);
		label_4.setText("\u65B0\u90AE\u7BB1\uFF1A");
		
		newremark = new Text(updateinfo, SWT.WRAP);
		newremark.setText("\u8FD9\u4E2A\u4EBA\u5F88\u61D2\uFF0C\u4EC0\u4E48\u90FD\u6CA1\u6709\u7559\u4E0B~");
		newremark.setBounds(398, 452, 204, 86);
		viewStyle.setTextRGB(newremark, 246, 246, 246);
		Label label_1 = new Label(updateinfo, SWT.NONE);
		label_1.setBounds(299, 452, 76, 20);
		label_1.setText("\u4E2A\u6027\u7B7E\u540D\uFF1A");
		
		Button button_2 = new Button(updateinfo, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		
		button_2.setBounds(299, 569, 98, 30);
		button_2.setText("\u786E\u8BA4\u4FEE\u6539");
		
		Label label_3 = new Label(updateinfo, SWT.NONE);
		label_3.setBounds(299, 261, 76, 20);
		label_3.setText("\u6027\u522B\uFF1A");
		
		Label manpic = new Label(updateinfo, SWT.NONE);
		manpic.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		manpic.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		manpic.setBounds(435, 266, 15, 15);
		viewStyle.setLabelBg(manpic, "man");
		
		
		Label womanpic = new Label(updateinfo, SWT.NONE);
		womanpic.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		womanpic.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		womanpic.setBounds(511, 266, 15, 15);
		viewStyle.setLabelBg(womanpic,"woman");
		
		Button uwoman = new Button(updateinfo, SWT.RADIO);
		uwoman.setBounds(398, 264, 31, 20);
		
		Button button_4 = new Button(updateinfo, SWT.RADIO);
		button_4.setBounds(479, 264, 31, 20);
		offical.setLayout(new FormLayout());
		
		Label label_11 = new Label(updateinfo, SWT.NONE);
		label_11.setText("\u5730\u533A\uFF1A");
		label_11.setBounds(299, 303, 64, 20);
		
		Combo newprovince = new Combo(updateinfo, SWT.READ_ONLY);									//个人信息省份选项
		
		newprovince.setBounds(398, 300, 92, 28);
		try {
			String[] citys = CityUtil.getCity();
			newprovince.add("");
			for(String c:citys){
				newprovince.add(c);
			}
			newprovince.select(0);
		} catch (DocumentException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
//		newprovince.add("广东");
//		newprovince.add("四川");
//		newprovince.add("广西");	
		
		Combo newcity = new Combo(updateinfo, SWT.READ_ONLY);										//个人信息市选项
		newcity.setBounds(510, 299, 92, 28);
		newprovince.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("-----");
				System.out.println(e.getSource().toString());
				System.out.println(newprovince.getItem(newprovince.getSelectionIndex()));
				String name = newprovince.getItem(newprovince.getSelectionIndex());
				try {
					String[] items = CityUtil.getItme(name);
					newcity.removeAll();
					if(items!=null){
						for(String s:items){
							newcity.add(s);
						}
					}
					newcity.select(0);
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Label label_5 = new Label(updateinfo, SWT.NONE);
		label_5.setText("\u65B0\u5BC6\u7801\uFF1A");
		label_5.setBounds(299, 411, 76, 26);
		
		newpw = new Text(updateinfo, SWT.PASSWORD);
		newpw.setText("10261776622qq.com");
		newpw.setForeground(SWTResourceManager.getColor(112, 128, 144));
		newpw.setBounds(398, 411, 204, 26);
		
		Button backinfo = new Button(updateinfo, SWT.NONE);
		backinfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userinfo.setVisible(true);
				updateinfo.setVisible(false);
			}
		});
		button_2.addMouseListener(new MyMouseAdapter(user) {
			@Override
			public void mouseUp(MouseEvent e) {
				String code_ = code.getText();
				String name_  = newname.getText();
				String mail_ = newmail.getText();
				String pw = newpw.getText();
				if(pw==null||pw.length()==0){
					pw=user.getPw();
				}
				String message = newremark.getText();
				String region = newprovince.getItem(newprovince.getSelectionIndex())+"-"+
						newcity.getItem(newcity.getSelectionIndex());
				String sex = new String();
				if(mail_.equals(user.getMail())){
					if(uwoman.getSelection()){
						sex = "男";
					}
					else if(button_4.getSelection()){
						sex= "女";
					}
					else{
						MessageBox msg = new MessageBox(Wechat, SWT.ICON_INFORMATION);
						msg.setText("提示：");
						msg.setMessage("请选择性别");
						msg.open();
					}
					if(!(sex==null||sex.length()==0)){
						OrdinaryUser user_ = new OrdinaryUser(user.getId(), pw, null, name_, sex, region);
						user_.setMessage(message);
						user_.setMail(mail_);
						try {
							ManageClientThread.getClientConServerThread(user.getId()).userUpdateMessageNotCode
							(user_);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				else{
					if(uwoman.getSelection()){
						sex = "男";
					}
					else if(button_4.getSelection()){
						sex= "女";
					}
					else{
						MessageBox msg = new MessageBox(Wechat, SWT.ICON_INFORMATION);
						msg.setText("提示：");
						msg.setMessage("请选择性别");
						msg.open();
					}
					if(!(sex==null||sex.length()==0)){
						OrdinaryUser user_ = new OrdinaryUser(user.getId(), pw, null, name_, sex, region);
						user_.setMessage(message);
						user_.setMail(mail_);
						try {
							ManageClientThread.getClientConServerThread(user.getId()).userUpdateMessage
							(user_,code_);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
				
			}
		});
		backinfo.setText("\u8FD4\u56DE");
		backinfo.setBounds(511, 569, 98, 30);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				newname.setText(user.getName());
				newmail.setText(user.getMail());
				newpw.setText("");
				if(!(user.getMessage()==null||user.getMessage().length()==0)){
					newremark.setText(user.getMessage());
				}
				if(user.getSex()==null||user.getSex().length()==0){
					
				}
				else if(user.getSex().equals("男")){
					System.out.println(111);
					uwoman.setSelection(true);
				}
				else if(user.getSex().equals("女")){
					button_4.setSelection(true);
				}
				if(!(user.getRegion()==null||user.getRegion().length()==0)){
					String[] region = user.getRegion().split("-");
					System.out.println(region[0]);
					String[] citys = null;
					try {
						citys = CityUtil.getCity();
					} catch (DocumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int i = 0;
					int index = 0;
					for(String s:citys){
						if(s.equals(region[0])){
							index = i+1;
							break;
						}
						i++;
					}
					newprovince.select(index);
					try {
						String[] items = CityUtil.getItme(region[0]);
						newcity.removeAll();
						for(String s:items){
							newcity.add(s);
						}
						i = 0;
						index = 0;
						for(String s:items){
							if(s.equals(region[1])){
								index = i;
								break;
							}
							i++;
						}
						newcity.select(index);
					} catch (DocumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				userinfo.setVisible(false);
				updateinfo.setVisible(true);				
			}
		});
		Label label = new Label(updateinfo, SWT.NONE);
		label.setText("\u9A8C\u8BC1\u7801\uFF1A");
		label.setBounds(299, 374, 76, 26);
		
		Label label_13 = new Label(updateinfo, SWT.NONE);
		label_13.setText("\u83B7\u53D6\u9A8C\u8BC1\u7801");
		label_13.setForeground(SWTResourceManager.getColor(51, 153, 255));
		label_13.setBounds(511, 374, 77, 20);
		
		label_13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				label_13.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
				
			}
			public void mouseUp(MouseEvent e) {
				String mail_ = newmail.getText();
				if(!checkEmail(mail_)){
					MessageBox msg = new MessageBox(Wechat, SWT.ICON_INFORMATION);
					msg.setText("提示：");
					msg.setMessage("邮箱格式不对");
					msg.open();
				}
				else if(mail_.equals(user.getMail())){
					MessageBox msg = new MessageBox(Wechat, SWT.ICON_INFORMATION);
					msg.setText("提示：");
					msg.setMessage("邮箱不能和原来的一样");
					msg.open();
				}
				else{
//					code.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
//					label_13.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
					try {
						ManageClientThread.
						getClientConServerThread(user.getId()).sendCode(mail_,user.getId());
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		
		code = new Text(updateinfo, SWT.NONE);
		code.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				code.setText("");
			}
		});

		code.setText("\u8F93\u5165\u9A8C\u8BC1\u7801");
		code.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		code.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		code.setBackground(SWTResourceManager.getColor(242, 242, 242));
		code.setBounds(398, 374, 87, 20);
		
		
		
		Label name = new Label(offical, SWT.NONE);
		FormData fd_name = new FormData();
		fd_name.right = new FormAttachment(0, 117);
		fd_name.top = new FormAttachment(0);
		fd_name.left = new FormAttachment(0, 29);
		name.setLayoutData(fd_name);
		name.setText("\u516C\u4F17\u53F7");
		name.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		name.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		
		 officals = new Composite(offical, SWT.NONE);
		FormData fd_officals = new FormData();
		fd_officals.bottom = new FormAttachment(0, 681);
		fd_officals.right = new FormAttachment(0, 853);
		fd_officals.top = new FormAttachment(0, 41);
		fd_officals.left = new FormAttachment(0, 39);
		officals.setLayoutData(fd_officals);
		officals.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		
		
		Composite oibt = new Composite(officals, SWT.NONE);
		Label oione = new Label(oibt, SWT.NONE);
		
		oibt.setLayoutData(new RowData(120, 120));
		oione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				oione.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				oione.setForeground(SWTResourceManager.getColor(105, 105, 105));
			}
			public void mouseUp(MouseEvent e) {
				Display.getDefault().asyncExec(new Runnable() {
				    public void run() {
				    	new AddOffical(user.getId()).open();
						oione.setBackground(SWTResourceManager.getColor(240,240,240));
						oione.setForeground(SWTResourceManager.getColor(192, 192, 192));
				    }
				});
				
			}
		});
		oione.setForeground(SWTResourceManager.getColor(192, 192, 192));
		oione.setFont(SWTResourceManager.getFont("Microsoft YaHei UI Light", 50, SWT.NORMAL));
		oione.setAlignment(SWT.CENTER);
		oione.setText("+");
		oione.setBounds(0, 0, 120, 120);
		oione.setBackgroundImage(null);
		for(EnterpriseUser teemp:user.getFocus()){
			viewStyle.AddOfficalOne(officals, teemp,user.getId());
		}
		groupp = new Composite(right, SWT.NONE);
		groupp.setLayout(new StackLayout());
		
		 CreatGroup = new Composite(right, SWT.NONE);
		Label ccghead = new Label(CreatGroup, SWT.NONE);
		ccghead.setImage(SWTResourceManager.getImage(Index.class, "/img/group.png"));
		ccghead.setBounds(434, 137, 44, 44);
		
		Label label_16 = new Label(CreatGroup, SWT.NONE);
		label_16.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_16.setText("\u7FA4\u6635\u79F0\uFF1A");
		label_16.setBounds(310, 216, 64, 26);
		
		ngname = new Text(CreatGroup, SWT.NONE);
		ngname.setBackground(SWTResourceManager.getColor(250,250,250));
		ngname.setBounds(380, 216, 193, 26);
		
		Label lblid = new Label(CreatGroup, SWT.NONE);
		lblid.setText("\u7FA4ID\uFF1A");
		lblid.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		lblid.setBounds(310, 276, 64, 26);
		
		ngid = new Text(CreatGroup, SWT.NONE);
		ngid.setBackground(SWTResourceManager.getColor(250,250,250));
		ngid.setBounds(380, 276, 193, 26);
		
		Label label_17 = new Label(CreatGroup, SWT.NONE);
		label_17.setText("\u7FA4\u516C\u544A\uFF1A");
		label_17.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_17.setBounds(310, 334, 64, 26);
		
		ngnc = new Text(CreatGroup, SWT.NONE);
		ngnc.setBackground(SWTResourceManager.getColor(250,250,250));
		ngnc.setBounds(380, 334, 193, 132);
		
		Label cconfim = new Label(CreatGroup, SWT.NONE);
		cconfim.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				System.out.println(ngname.getText());
				System.out.println(ngnc.getText());
				String namet = ngname.getText();
				String gbulletin = ngnc.getText();
				if(namet==null||namet.length()==0){
					showMessage("提示：", "群名字不能为空");
					return;
				}
				com.wechat.model.Group group = new com.wechat.model.Group
						(0, new User(user.getId(), null, null, null), null, namet, gbulletin);
				try {
					ManageClientThread.getClientConServerThread(user.getId()).addGroup(group);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
//				viewStyle.ViewGroupInfo(Group, ngname.getText(), Integer.parseInt(ngid.getText()), ngnc.getText(),user.getId());
//				CreatGroup.setVisible(false);
//				Group.setVisible(true);
			}
		});
		cconfim.setForeground(SWTResourceManager.getColor(50, 205, 50));
		cconfim.setText("\u521B\u5EFA");
		cconfim.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		cconfim.setBounds(380, 546, 64, 26);
		
		Label cback = new Label(CreatGroup, SWT.NONE);
		cback.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				CreatGroup.setVisible(false);
			}
		});
		cback.setForeground(SWTResourceManager.getColor(160, 82, 45));
		cback.setText("\u8FD4\u56DE");
		cback.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		cback.setBounds(529, 546, 44, 26);


		
		officelbt.addMouseListener(new MouseAdapter() {							//公众号按钮
			@Override
			public void mouseDown(MouseEvent e) {
				viewStyle.setLabelBg(officelbt, "com-down");
			}
			@Override
			public void mouseUp(MouseEvent e) {
				viewStyle.setLabelBg(officelbt, "com");
				hideview();
				offical.setVisible(true);
				
			}
		});
		C1.addMouseListener(new MouseAdapter() {

			public void mouseDown(MouseEvent e) {
				viewStyle.setLabelBg(C1, "msg-down");
				viewStyle.setLabelBg(C2, "frd");
				hideview();
//				chat.setVisible(true);
				chatList.setVisible(true);
				firendList.setVisible(false);
			}
		});
		C2.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				viewStyle.setLabelBg(C2, "frd-down");
				viewStyle.setLabelBg(C1, "msg");
				hideview();
				chatList.setVisible(false);
				firendList.setVisible(true);
				right.layout();
			}
		});
		addbt.addMouseListener(new MouseAdapter() {														//加号事件
			@Override
			public void mouseDown(MouseEvent e) {
				viewStyle.setLabelBg(addbt, "add-down");
				viewStyle.setLabelBg(C1, "msg");
				viewStyle.setLabelBg(C2, "frd");
			}

			@Override
			public void mouseUp(MouseEvent e) {
				viewStyle.setLabelBg(addbt, "add");		
				hideview();
//				frdinfo.setVisible(true);
//				Turnfrdinfo(0);
				
				String se = search.getText();		
				String reg = "^[0-9]*[1-9][0-9]*$";
		        boolean boo = Pattern.compile(reg).matcher(se).find();
				//////////////////////////////////5.30
				if(se == null || se.length()==0 || se.equals("") || se.trim().isEmpty()){     //如搜索框为空则为创建群聊操作
					CreatGroup.setVisible(true);
				}
				if(boo){
					ManageClientThread.getClientConServerThread(user.getId()).searchUser(se);
				}
				
//				if(){																						//搜索群
//					hideview();
//				viewStyle.ViewNewGroupInfo(CreatGroup, gname, gid, Gn);
//				}
//				if(){																			//搜索好友
//				hideview();
//					viewfrdinfo(frdhead1, frdname1, frdsex1, frdnum1, frdmail1, frdprovince1, frdcity1, frdremark1);
//				}
//				
				
			}
			
		});
		set.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				new MyWechatVersion().open();
			}
		});

		head.addMouseListener(new MouseAdapter() {														//点击左上头像事件
			@Override
			public void mouseUp(MouseEvent e) {
				firendList.setVisible(false);
				chatList.setVisible(false);
				hideview();
				userinfo.setVisible(true);
				user_name.setText(user.getName());
				user_id.setText(user.getId());
				user_mail.setText(user.getMail());
				userhead.setBackgroundImage(head.getBackgroundImage());
				if(user.getRegion()==null||user.getRegion().length()==0){
					province.setText("");
					city.setText("");
				}
				else{
					String[] region = user.getRegion().split("-");
					province.setText(region[0]);
					city.setText(region[1]);
				}
				if(!(user.getMessage()==null||user.getMessage().length()==0)){
					remark.setText(user.getMessage());
				}
				if(user.getSex()==null||user.getSex().length()==0){
//					viewStyle.setLabelBg(usersex, "qweqwe");
					usersex.setVisible(false);
				}
				else if(user.getSex().equals("女")){
					viewStyle.setLabelBg(usersex, "woman");
				}
				
			}
		});
		
		frdsend.addMouseListener(new MouseAdapter() {														//好友信息页面发送按钮事件
			@Override
			public void mouseUp(MouseEvent e) {
				viewStyle.setLabelBg(C1, "msg-down");
				viewStyle.setLabelBg(C2, "frd");
				hideview();
				chatList.setVisible(true);
				String id__ = frdnum.getText();
				User tempUser = null;	
				for(HistorySession session:user.getSession()){
					if(session.getFriend().getId().equals(id__)){
//						frdinfo.setVisible(false);
						
						viewStyle.newchating(Wechat, chat, head, session.getFriend().getName(), user, session.getFriend().getId());
						System.out.println("我找到啦！！！！");
						return;
					}
				}
				for(User uuu:user.getFriends()){
					if(uuu.getId().equals(id__)){
						tempUser = uuu;
					}
				}
				HistorySession session = new HistorySession(tempUser, null, getNewTime());
				session.setId(user.getId());
				try {
					ManageClientThread.getClientConServerThread(user.getId()).addSession(session);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
//				viewStyle.addchatone(Wechat, chatList,right, head, chatcomposite,
//				SWTResourceManager.getImage(tempUser.getHeadimg())
//				, tempUser.getName()
//				,getNewTime().toString().substring(5,19)
//				, "",user,tempUser.getId());
//				viewStyle.newchating(Wechat, chat, head, session.getFriend().getName(), user, session.getFriend().getId());
				
			}
			public String getNewTime() {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				return sendTime;
			}
		});
//		chathead.addMouseListener(new MouseAdapter() {															//聊天头像	
//			@Override
//			public void mouseDown(MouseEvent e) {						
//					viewStyle.newchating(Wechat,chat,head,chatname.getText());				
//					
//				chatone.setBackground(SWTResourceManager.getColor(248,248,248));
//			}
//		});
		for(Group group:user.getGroups()){
			viewStyle.AddGroupOne(firendList, groupp, fcomposite
					, SWTResourceManager.getImage(Index.class, "/img/group.png"), group, user.getId());
//			System.out.println(group.getGroupOwner().getId()+"这是群主id");
		}
	}
	public static String getNewTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sendTime = df.format(new Date());
		return sendTime;
	}
	public static  void viewfrdinfo(org.eclipse.swt.graphics.Image frdhead1,String frdname1,org.eclipse.swt.graphics.Image frdsex1,	//好友信息赋值
			String frdnum1,String frdmail1,String frdprovince1,String frdcity1,String frdremark1){		
			frdhead.setBackgroundImage(frdhead1);
			frdname.setText(frdname1);
			frdsex.setBackgroundImage(frdsex1);
			frdnum.setText(frdnum1);
			frdmail.setText(frdmail1);
			frdprovince.setText(frdprovince1);
			frdcity.setText(frdcity1);
			frdremark.setText(frdremark1);
			frdinfo.setVisible(true);
			System.out.println(frdinfo);
	}
	public static void Turnfrdinfo(int i){
		
		if(i==1){
			 frdsend.setVisible(true);
				delet.setVisible(true);
				addfriend.setVisible(false);
				exitadd.setVisible(false);
		}
		if(i==0){
			 frdsend.setVisible(false);
				delet.setVisible(false);
				addfriend.setVisible(true);
				exitadd.setVisible(true);
		}
		
	}
	public static void hideview() {
		chat.setVisible(false);
		frdinfo.setVisible(false);
		userinfo.setVisible(false);
		updateinfo.setVisible(false);
		offical.setVisible(false);
		groupp.setVisible(false);
		CreatGroup.setVisible(false);
	}
	
	public void NewGroupChat(String chatingman,String group_id){
		org.eclipse.swt.graphics.Image a= SWTResourceManager.getImage(Index.class,"/img/group.png");
		viewStyle.addchatone(Wechat,chatList, right, head, chatcomposite,a, chatingman, "11:12", "嘿嘿~~~",user,group_id);
		viewStyle.newchating(Wechat, chat, head, chatingman,user,group_id);
		chatList.setVisible(true);
	}
	public void ViewGroupchat(String chatingman,String group_id){
		viewStyle.newchating(Wechat, chat, head, chatingman,user,group_id);
	}
	public void setPath(String path){
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	user.setHeadimg(path);
//		    	head.
		    	viewStyle.updeLabelBg(head, user.getHeadimg());
		    	viewStyle.updeLabelBg(newhead, user.getHeadimg());
		    }
		});
	}
	public void showMessage(String text,String message){
//		JOptionPane.showMessageDialog(null, message);
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	MessageBox smsg = new MessageBox(Wechat, SWT.ICON_INFORMATION);
				smsg.setText(text);
				smsg.setMessage(message);
				smsg.open();
		    }
		});
	}
	public static void accpetMessage(Message message){
		
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
		    	if(message.getType()==MessageType.text){
		    		ManageSessionMessage.getSessionsMessage
					(message.getSender().getId()).setText(message.getContent());
					ManageSessionMessage.getSessionsMessage
					(message.getSender().getId()).setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					ManageSessionSendTime.getSessionSendTime
					(message.getSender().getId()).setText(message.getSendTime());
					if(ManageChating.getChating(message.getSender().getId())!=null){
						if(ManageScrolledComposite.getManageScrolledComposite(message.getSender().getId())!=null){
							viewStyle.Newbackmsg(
				    				ManageScrolledComposite.getManageScrolledComposite(message.getSender().getId())
				    				,ManageChating.getChating(message.getSender().getId())
				    				, SWTResourceManager.getImage(message.getSender().getHeadimg())
				    				, message.getSendTime()
				    				, message.getContent());
						}
			    	}
		    	}
		    	else if(message.getType()==Picture){
		    		ManageSessionMessage.getSessionsMessage
					(message.getSender().getId()).setText("[动画图片]");
					ManageSessionMessage.getSessionsMessage
					(message.getSender().getId()).setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					ManageSessionSendTime.getSessionSendTime
					(message.getSender().getId()).setText(message.getSendTime());
					if(ManageChating.getChating(message.getSender().getId())!=null){
						if(ManageScrolledComposite.getManageScrolledComposite(message.getSender().getId())!=null){
							viewStyle.NewBackEmsg(
				    				ManageScrolledComposite.getManageScrolledComposite(message.getSender().getId())
				    				,ManageChating.getChating(message.getSender().getId())
				    				, SWTResourceManager.getImage(message.getSender().getHeadimg())
				    				, message.getSendTime()
				    				, SWTResourceManager.getImage(Index.class, message.getContent()));
						}
			    		
			    	}
		    	}
		    	else if(message.getType()==VoiceMe){
		    		ManageSessionMessage.getSessionsMessage
					(message.getSender().getId()).setText("[语音]");
					ManageSessionMessage.getSessionsMessage
					(message.getSender().getId()).setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					ManageSessionSendTime.getSessionSendTime
					(message.getSender().getId()).setText(message.getSendTime());
					//显示语音还不行
					if(ManageChating.getChating(message.getSender().getId())!=null){
						if(ManageScrolledComposite.getManageScrolledComposite(message.getSender().getId())!=null){
							viewStyle.NewBackEmsg(
				    				ManageScrolledComposite.getManageScrolledComposite(message.getSender().getId())
				    				,ManageChating.getChating(message.getSender().getId())
				    				, SWTResourceManager.getImage(message.getSender().getHeadimg())
				    				, message.getSendTime()
				    				, SWTResourceManager.getImage(Index.class, message.getContent()));
							viewStyle.NewbackVmsg(
									ManageScrolledComposite.getManageScrolledComposite(message.getSender().getId())
									, ManageChating.getChating(message.getSender().getId()), 
									head.getBackgroundImage(), "", message);
						}
			    		
			    	}
		    	}
		    	else if(message.getType()==file){
		    		ManageSessionMessage.getSessionsMessage
					(message.getSender().getId()).setText("[文件]");
					ManageSessionMessage.getSessionsMessage
					(message.getSender().getId()).setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					ManageSessionSendTime.getSessionSendTime
					(message.getSender().getId()).setText(message.getSendTime());
					
					if(ManageChating.getChating(message.getSender().getId())!=null){
						if(ManageScrolledComposite.getManageScrolledComposite(message.getSender().getId())!=null){
							viewStyle.BackFmsg(
				    				ManageScrolledComposite.getManageScrolledComposite(message.getSender().getId())
				    				,ManageChating.getChating(message.getSender().getId())
				    				, SWTResourceManager.getImage(message.getSender().getHeadimg())
				    				, message.getSendTime()
				    				, "wechat\\"+message.getSendee().getId()+"\\file\\"+
				    				message.getContent().substring(message.getContent().lastIndexOf("\\")+1));
						}
			    	}
					System.out.println("进来拉屎");
//					for(HistorySession session:ManageWechat.getWechat(message.getSendee().getId()).user.getSession()){
//						if(session.getFriend().getId().equals(message.getSender().getId())){
//							session.getMessages().add(message);
//						}
//					}
//					return;
		    	}
		    	//加聊天记录进去
		    	for(HistorySession session:ManageWechat.getWechat(message.getSendee().getId()).user.getSession()){
					if(session.getFriend().getId().equals(message.getSender().getId())){
						session.getMessages().add(message);
					}
				}
//		    	viewStyle.AddGroupOne(chatList, CreatGroup, fcomposite, ghead, gname, gid, user_id);
		    	
		    }
		});
	}
	public static boolean checkEmail(String email){
		boolean flag = false;
		try{
		String check = "^\\w+@(qq|163)\\.com$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		flag = matcher.matches();
		}catch(Exception e){
		flag = false;
		}
		return flag;
		}
	public void addSession(HistorySession session) {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	user.getSession().add(session);
		    	viewStyle.addchatone(Wechat, chatList,chat, head, chatcomposite,
						SWTResourceManager.getImage(session.getFriend().getHeadimg())
						, session.getFriend().getName()
						,getNewTime().toString().substring(5,19)
						, "",user,session.getFriend().getId());
		    	System.out.println("煞笔我在这");
		    	viewStyle.newchating(Wechat, chat, head, session.getFriend().getName(), user, session.getFriend().getId());
		    }
		});
	}
	static org.eclipse.swt.graphics.Image ghead = SWTResourceManager.getImage(Index.class, "/img/group.png");
	public void addGroupSuccess(Group group2) {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	System.out.println(user.getGroups()+"群聊实例");
		    	user.getGroups().add(group2);
		    	showMessage("提示：", "添加群聊成功");
		    	viewStyle.AddGroupOne(firendList,
		    			groupp, fcomposite, ghead, group2, user.getId());
		    	System.out.println("添加列表");
		    }
		});
	}
	public void searchUserSuccess(OrdinaryUser user_) {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	hideview();
		    	temp = user_;
		    	 org.eclipse.swt.graphics.Image man = SWTResourceManager.getImage(getClass(), "/img/man.png");
		    	 org.eclipse.swt.graphics.Image woman = SWTResourceManager.getImage(getClass(), "/img/woman.png");
		    	 if(user_.getSex()==null||user_.getSex().length()==0){

			    		viewfrdinfo(SWTResourceManager.getImage(user_.getHeadimg()==null?"":user_.getHeadimg()), user_.getName(), null
				    			, user_.getId(), user_.getMail()
				    			, user_.getRegion()==null?"":user_.getRegion().split("-")[0]
				    					, user_.getRegion()==null?"":user_.getRegion().split("-")[1]
				    							, user_.getMessage()==null?"":user_.getMessage());
		    	 }
		    	 else if(user_.getSex().equals("男")){
		    		 viewfrdinfo(SWTResourceManager.getImage
		    				 (user_.getHeadimg()==null?"":user_.getHeadimg()), user_.getName(), man
				    			, user_.getId(), user_.getMail()
				    			, user_.getRegion()==null?"":user_.getRegion().split("-")[0]
				    					, user_.getRegion()==null?"":user_.getRegion().split("-")[1]
				    							, user_.getMessage()==null?"":user_.getMessage());
		    	}
		    	else if(user_.getSex().equals("女")){
		    		viewfrdinfo(SWTResourceManager.getImage
		    				(user_.getHeadimg()==null?"":user_.getHeadimg()), user_.getName(), woman
			    			, user_.getId(), user_.getMail()
			    			, user_.getRegion()==null?"":user_.getRegion().split("-")[0]
			    					, user_.getRegion()==null?"":user_.getRegion().split("-")[1]
			    							, user_.getMessage()==null?"":user_.getMessage());
		    	}
		    	 Turnfrdinfo(0);
		    	frdinfo.setVisible(true);
		    }
		});
	}
	public void addUserSuccess() {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	showMessage("提示：", "添加好友成功");
		    	user.getFriends().add(temp);
		    	viewStyle.addfrdone(firendList, fcomposite
		    			, SWTResourceManager.getImage("wechat\\headimg\\"+temp.getHeadimg()), temp);
		    	System.out.println("进来添加好友了");
		    }
		});
	}
	public void delUserSuccess() {
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	showMessage("提示：", "删除好友成功");
		    	viewStyle.onefrdlist.get(temp.getId()).dispose();
		    	user.getFriends().remove(temp);
		    	frdinfo.setVisible(false);
		    	fcomposite.layout();
//		    	viewStyle.addfrdone(firendList, fcomposite
//		    			, SWTResourceManager.getImage("wechat\\headimg\\"+temp.getHeadimg()), temp);
		    }
		});
	}
	public void addeUserSuccess(EnterpriseUser e_user) {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	System.out.println(user.getFocus().size());
		    	showMessage("提示：", "关注成功");
		    	user.getFocus().add(e_user);
		    	System.out.println(officals);
		    	viewStyle.AddOfficalOne(officals, e_user,user.getId());
		    	//添加操作
//		    	viewStyle.AddOfficalOne(officals,e_user);
		    }
		});
		
	}
	public void deleUserSuccess() {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	user.getFocus().remove(e_temp);
		    	//删除界面公众号动作
		    	viewStyle.officalss.get(e_temp.getId()).dispose();
		    	viewStyle.officalss.remove(e_temp.getId());
		    	officals.layout();
		    }
		});
	}
	public void deleUser(EnterpriseUser e_user1) {
		// TODO Auto-generated method stub
		e_temp = e_user1;
		ManageClientThread.getClientConServerThread(user.getId()).deleUser(e_user1.getId());
	}
	public void delGroupMSuccess() {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	user.getGroups().remove(g_temp);
		    	showMessage("提示：", "退出群聊成功");
		    	//删除界面群聊操作
		    	viewStyle.fgroupCom.get(g_temp.getGroupId()).dispose();
		    	viewStyle.fgroupCom.remove(g_temp.getGroupId());
		    	viewStyle.groupInfoss.get(g_temp.getGroupId()).dispose();
		    	viewStyle.groupInfoss.remove(g_temp.getGroupId());
		    	fcomposite.layout();
				firendList.layout();
		    }
		});
	}
	public void delGroup(Group del_group){
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	g_temp = del_group;
		    	ManageClientThread.getClientConServerThread(user.getId()).delGroup(del_group.getGroupId());
		    }
		});
	}
	public void delGroupM(String m_id,Group del_group){
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	g_temp = del_group;
		    	ManageClientThread.getClientConServerThread(user.getId()).delGroupM(del_group.getGroupId(), m_id);
		    }
		});
	}
	public void delGroupSuccess() {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	System.out.println(g_temp);
		    	showMessage("提示：", "解散群聊成功");
		    	//删除界面群聊操作
		    	System.out.println(viewStyle.fgroupCom.get(g_temp.getGroupId()));
		    	viewStyle.fgroupCom.get(g_temp.getGroupId()).dispose();
		    	viewStyle.fgroupCom.remove(g_temp.getGroupId());
		    	viewStyle.groupInfoss.get(g_temp.getGroupId()).dispose();
		    	viewStyle.groupInfoss.remove(g_temp.getGroupId());
		    	user.getGroups().remove(g_temp);
		    	fcomposite.layout();
				firendList.layout();
		    }
		});
	}
	public void addGroupMSuccess(OrdinaryUser addUser,int g_id) {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	for(Group gg:user.getGroups()){
		    		if(gg.getGroupId()==g_id){
		    			gg.getGroupMembers().add(addUser);
		    		}
		    	}
		    	viewStyle.AddGroupOne(viewStyle.GroupPeopless.get(g_id),addUser);
		    	showMessage("提示：", "添加成员成功");
		    }
		});
	}
	public void acceptGText(Message message,int g_id) {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	for(Group g:user.getGroups()){
					if(g.getGroupId()==g_id){
						g.getMessages().add(message);
					}
				}
		    	System.out.println("接收消息了");
				viewStyle.Newbackmsg(viewStyle.manageScrolledComposite.get(g_id)
						,viewStyle.chatings.get(g_id)
						, viewStyle.myhead
						, message.getSendTime()
						, message.getContent());
				System.out.println("接收消息之后");
		    }
		});
		
	}
	
}
