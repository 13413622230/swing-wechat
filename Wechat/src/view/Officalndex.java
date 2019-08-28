package view;

import java.awt.FileDialog;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.wb.swt.SWTResourceManager;

import com.ibm.icu.impl.UPropertyAliases;
import com.wechat.client.ManageClientThread;
import com.wechat.client.ManageWechat;
import com.wechat.model.Bulletin;
import com.wechat.model.Comment;
import com.wechat.model.EnterpriseUser;
import com.wechat.model.Moment;
import com.wechat.model.OrdinaryUser;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowData;

public class Officalndex {

	protected Shell shell;
	private Text newtitle;
	private Text text;
	private static Composite send;
	private static Composite fansCount;
	private static Composite officalinfo;
	private static Composite updateinfo;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private static Composite have;
	private static ScrolledComposite hsc;
	EnterpriseUser user;
	
	public void addBullein(Bulletin bullein){
		
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	user.getBulletin().add(bullein);
		    	viewStyle.addHaveOne(shell, have, bullein,user.getName());
		    }
		});
	}
	public void delBulletin(int b_id){
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	for(Bulletin b:user.getBulletin()){
		    		if(b.getId()==b_id){
		    			user.getBulletin().remove(b);
		    			break;
		    		}
		    	}
		    }
		});
	}
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public Officalndex(EnterpriseUser user){
		this.user = user;
	}
	public static void main(String[] args) {
//		try {
//			Officalndex window = new Officalndex();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.NONE);
		shell.setSize(1200, 700);
		shell.setText("\u4F01\u4E1A\u516C\u4F17\u53F7");
		viewStyle.setbgview(shell);
		shell.setBackgroundImage(SWTResourceManager.getImage(Index.class, "/img/index.png"));
		shell.setLayout(new FormLayout());
		Label exit = new Label(shell, SWT.NONE);
		FormData fd_exit = new FormData();
		fd_exit.top = new FormAttachment(0);
		fd_exit.right = new FormAttachment(100);
		fd_exit.left = new FormAttachment(0, 1167);
		exit.setLayoutData(fd_exit);
		exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		exit.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		exit.setAlignment(SWT.CENTER);
		exit.setText("X");

		Label ohead = new Label(shell, SWT.NONE);
		FormData fd_ohead = new FormData();
		fd_ohead.bottom = new FormAttachment(0, 88);
		fd_ohead.right = new FormAttachment(0, 125);
		fd_ohead.top = new FormAttachment(0, 33);
		fd_ohead.left = new FormAttachment(0, 73);
		ohead.setLayoutData(fd_ohead);
		viewStyle.setLabelBg(ohead, "exhead");

		Label c1 = new Label(shell, SWT.NONE);
		FormData fd_c1 = new FormData();
		fd_c1.bottom = new FormAttachment(0, 176);
		fd_c1.right = new FormAttachment(0, 159);
		fd_c1.top = new FormAttachment(0, 146);
		fd_c1.left = new FormAttachment(0, 73);
		c1.setLayoutData(fd_c1);

		c1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		c1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		c1.setText("\u73B0\u6709\u5185\u5BB9");

		Label c2 = new Label(shell, SWT.NONE);
		FormData fd_c2 = new FormData();
		fd_c2.bottom = new FormAttachment(0, 243);
		fd_c2.right = new FormAttachment(0, 159);
		fd_c2.top = new FormAttachment(0, 213);
		fd_c2.left = new FormAttachment(0, 73);
		c2.setLayoutData(fd_c2);
		c2.setText("\u53D1\u8868\u52A8\u6001");
		c2.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		c2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));

		Label c3 = new Label(shell, SWT.NONE);
		FormData fd_c3 = new FormData();
		fd_c3.bottom = new FormAttachment(0, 302);
		fd_c3.right = new FormAttachment(0, 159);
		fd_c3.top = new FormAttachment(0, 272);
		fd_c3.left = new FormAttachment(0, 73);
		c3.setLayoutData(fd_c3);
		c3.setText("\u7C89\u4E1D\u7EDF\u8BA1");
		c3.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		c3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));

		Label top = new Label(shell, SWT.NONE);
		FormData fd_top = new FormData();
		fd_top.right = new FormAttachment(0, 331);
		fd_top.top = new FormAttachment(0, 25);
		fd_top.left = new FormAttachment(0, 224);
		top.setLayoutData(fd_top);
		top.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.NORMAL));
		top.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		top.setText("\u73B0\u6709\u5185\u5BB9\uFF1A");

		Composite right = new Composite(shell, SWT.NONE);
		FormData fd_right = new FormData();
		fd_right.right = new FormAttachment(exit, 0, SWT.RIGHT);
		fd_right.left = new FormAttachment(top, 0, SWT.LEFT);
		fd_right.bottom = new FormAttachment(0, 698);
		fd_right.top = new FormAttachment(0, 81);
		right.setLayoutData(fd_right);
		StackLayout rights = new StackLayout();
		right.setLayout(rights);

		hsc = new ScrolledComposite(right, SWT.H_SCROLL | SWT.V_SCROLL);
		hsc.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		hsc.setExpandHorizontal(true);
		hsc.setExpandVertical(true);

		have = new Composite(hsc, SWT.NONE);
		have.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		have.setLayout(new RowLayout(SWT.HORIZONTAL));

		Composite all = new Composite(have, SWT.NONE);
		all.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		all.setLayoutData(new RowData(918, SWT.DEFAULT));

		Label label = new Label(all, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		label.setText("\u603B\u6587\u7AE0\u6570\uFF1A");
		label.setBounds(0, 10, 68, 20);

		Label label_1 = new Label(all, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		label_1.setText("\u603B\u83B7\u8D5E\uFF1A");
		label_1.setBounds(177, 10, 60, 20);

		Label allTextNum = new Label(all, SWT.NONE);
		allTextNum.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		allTextNum.setText("1");
		allTextNum.setForeground(SWTResourceManager.getColor(0, 51, 153));
		allTextNum.setAlignment(SWT.CENTER);
		allTextNum.setBounds(74, 10, 49, 20);

		Label allLikeNum = new Label(all, SWT.NONE);
		allLikeNum.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		allLikeNum.setText("1");
		allLikeNum.setForeground(SWTResourceManager.getColor(0, 51, 153));
		allLikeNum.setAlignment(SWT.CENTER);
		allLikeNum.setBounds(243, 10, 49, 20);
		hsc.setBackgroundMode(SWT.INHERIT_DEFAULT);
		hsc.setContent(have);
		hsc.setMinSize(new Point(0, 2000));
		send = new Composite(right, SWT.NONE);
		fansCount = new Composite(right, SWT.NONE);
		officalinfo = new Composite(right, SWT.NONE);

		send.setBackgroundImage(SWTResourceManager.getImage(Index.class, "/img/send-bg.png"));

		newtitle = new Text(send, SWT.NONE);
		newtitle.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		newtitle.setBounds(78, 67, 808, 26);
		viewStyle.setTextRGB(newtitle, 252, 252, 252);

		text = new Text(send, SWT.WRAP);
		text.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		viewStyle.setTextRGB(text, 252, 252, 252);
		text.setBounds(78, 140, 808, 317);

		Label upfile = new Label(send, SWT.NONE);
		upfile.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		upfile.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		upfile.setBounds(70, 478, 76, 24);
		upfile.setText("\u70B9\u51FB\u4E0A\u4F20");

		Label sendsub = new Label(send, SWT.NONE);
		sendsub.addMouseListener(new MouseAdapter() {
			
			//发送按钮事件
			@Override
			public void mouseDown(MouseEvent e) {
				viewStyle.setLabelBg(sendsub, "send-down");
			}

			public void mouseUp(MouseEvent e) {
				viewStyle.setLabelBg(sendsub, "send");
				String nt = newtitle.getText();
				String nc = text.getText();
				String sendTime = getNewTime();
				if ((nt == null || nt.equals("")) || (nc == null || nc.equals(""))) {
					MessageBox ee = new MessageBox(shell);
					ee.setText("提示：");
					ee.setMessage("请检查内容！");
					ee.open();
				} else {
					
//					MessageBox sf = new MessageBox(shell);
//					sf.setText("提示：");
//					sf.setMessage("发表成功！");
//					sf.open();
					
					newtitle.setText("");
					text.setText("");
					Bulletin bulletin = new Bulletin(0, user.getId(), nt, nc, sendTime,0);
					try {
						ManageClientThread.getClientConServerThread(user.getId()).addBullein(bulletin);
//						viewStyle.addHaveOne(shell, have, "2", newtitle.getText(), "0"); // 现有内容中+1
//						addBulletin();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}

			
			public String getNewTime() {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sendTime = df.format(new Date());
				return sendTime;
			}
		});
		viewStyle.setLabelBg(sendsub, "send");
		sendsub.setBounds(733, 513, 153, 58);
		sendsub.setText(" ");

		Label filename = new Label(send, SWT.NONE);
		filename.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		filename.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		filename.setBounds(153, 478, 165, 24);

		Label label_3 = new Label(fansCount, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		label_3.setBounds(0, 70, 82, 29);
		label_3.setText("\u6027\u522B\u5206\u5E03\uFF1A");//性别分布：

		Label label_4 = new Label(fansCount, SWT.NONE);
		label_4.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label_4.setBounds(130, 70, 35, 20);
		label_4.setText("\u7537\uFF1A");

		Label mancount = new Label(fansCount, SWT.NONE);
		mancount.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		mancount.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		mancount.setText("10");
		mancount.setBounds(171, 70, 59, 20);//男性数量
		

		Label label_6 = new Label(fansCount, SWT.NONE);
		label_6.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_6.setForeground(SWTResourceManager.getColor(255, 20, 147));
		label_6.setText("\u5973\uFF1A");
		label_6.setBounds(269, 70, 35, 20);

		Label womancount = new Label(fansCount, SWT.NONE);
		womancount.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		womancount.setForeground(SWTResourceManager.getColor(255, 20, 147));
		womancount.setText("10");
		womancount.setBounds(310, 70, 61, 20);//女性数量

		Label label_5 = new Label(fansCount, SWT.NONE);
		label_5.setText("\u672A\u77E5\uFF1A");
		label_5.setForeground(SWTResourceManager.getColor(128, 128, 128));
		label_5.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_5.setBounds(413, 71, 44, 20);

		Label nosexcount = new Label(fansCount, SWT.NONE);
		nosexcount.setText("5");
		nosexcount.setForeground(SWTResourceManager.getColor(128, 128, 128));
		nosexcount.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		nosexcount.setBounds(473, 71, 50, 20);//未知性别数量

		Label label_8 = new Label(fansCount, SWT.NONE);
		label_8.setText("\u603B\u6570\uFF1A");
		label_8.setForeground(SWTResourceManager.getColor(105, 105, 105));
		label_8.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_8.setBounds(591, 72, 44, 20);

		Label fanscount = new Label(fansCount, SWT.NONE);
		fanscount.setText("25");
		fanscount.setForeground(SWTResourceManager.getColor(105, 105, 105));
		fanscount.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		fanscount.setBounds(651, 72, 50, 20);//关注总数

		Label label_7 = new Label(fansCount, SWT.NONE);
		label_7.setText("\u5730\u533A\u524D\u4E09\uFF1A");
		label_7.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		label_7.setBounds(1, 135, 82, 29);

		Label p1 = new Label(fansCount, SWT.NONE);
		p1.setAlignment(SWT.CENTER);
		p1.setText("\u5E7F\u4E1C\uFF1A");
		p1.setForeground(SWTResourceManager.getColor(128, 128, 128));
		p1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		p1.setBounds(130, 135, 59, 20);

		Label p1count = new Label(fansCount, SWT.NONE);
		p1count.setText("12");
		p1count.setForeground(SWTResourceManager.getColor(128, 128, 128));
		p1count.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		p1count.setBounds(195, 135, 59, 20);//地区第一数量

		Label p2 = new Label(fansCount, SWT.NONE);
		p2.setAlignment(SWT.CENTER);
		p2.setText("\u56DB\u5DDD\uFF1A");
		p2.setForeground(SWTResourceManager.getColor(128, 128, 128));
		p2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		p2.setBounds(306, 135, 68, 20);

		Label p2count = new Label(fansCount, SWT.NONE);
		p2count.setText("8");
		p2count.setForeground(SWTResourceManager.getColor(128, 128, 128));
		p2count.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		p2count.setBounds(380, 135, 61, 20);//地区第二数量

		Label p3 = new Label(fansCount, SWT.NONE);
		p3.setAlignment(SWT.CENTER);
		p3.setText("\u9ED1\u9F99\u6C5F\uFF1A");
		p3.setForeground(SWTResourceManager.getColor(128, 128, 128));
		p3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		p3.setBounds(474, 135, 94, 20);

		Label p3count = new Label(fansCount, SWT.NONE);
		p3count.setText("5");
		p3count.setForeground(SWTResourceManager.getColor(128, 128, 128));
		p3count.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		p3count.setBounds(574, 135, 50, 20);//地区第三数量

		officalinfo.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));

		Label Ohead = new Label(officalinfo, SWT.BORDER);
		Ohead.setBounds(455, 104, 52, 55);//用户头像

		Label label_9 = new Label(officalinfo, SWT.NONE);
		label_9.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_9.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		label_9.setBounds(342, 178, 52, 20);
		label_9.setText("\u6635\u79F0\uFF1A");

		Label label_10 = new Label(officalinfo, SWT.NONE);
		label_10.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_10.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		label_10.setText("\u5E10\u53F7\uFF1A");
		label_10.setBounds(342, 214, 52, 20);

		Label label_11 = new Label(officalinfo, SWT.NONE);
		label_11.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_11.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		label_11.setText("\u90AE\u7BB1\uFF1A");
		label_11.setBounds(342, 253, 52, 20);

		Label label_12 = new Label(officalinfo, SWT.NONE);
		label_12.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_12.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		label_12.setText("\u7535\u8BDD\uFF1A");
		label_12.setBounds(342, 292, 52, 20);

		Label label_13 = new Label(officalinfo, SWT.NONE);
		label_13.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_13.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		label_13.setText("\u4ECB\u7ECD\uFF1A");
		label_13.setBounds(342, 330, 52, 20);

		Label oname = new Label(officalinfo, SWT.NONE);
		oname.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		oname.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		oname.setBounds(400, 178, 182, 20);
		oname.setText("null");//昵称

		Label oid = new Label(officalinfo, SWT.NONE);
		oid.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		oid.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		oid.setText("null");//账号
		oid.setBounds(400, 214, 182, 20);

		Label omail = new Label(officalinfo, SWT.NONE);
		omail.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		omail.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		omail.setText("null");//邮箱
		omail.setBounds(400, 253, 182, 20);

		Label otel = new Label(officalinfo, SWT.NONE);
		otel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		otel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		otel.setText("null");//电话
		otel.setBounds(400, 292, 182, 20);

		Label oint = new Label(officalinfo, SWT.WRAP);
		oint.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		oint.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		oint.setText("介绍");//介绍
		oint.setBounds(400, 330, 182, 140);

		Button upadte = new Button(officalinfo, SWT.NONE);
		upadte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				text_1.setText(user.getName());
				text_2.setText(user.getMail());
				text_3.setText("");
				text_6.setText("");
				text_4.setText(user.getTelephone()==null?"":user.getTelephone());
				text_5.setText(user.getIntroduction()==null?"":user.getIntroduction());
				hideview();
				updateinfo.setVisible(true);
			}
		});
		upadte.setText("\u66F4\u6539\u4FE1\u606F");
		upadte.setBounds(432, 488, 98, 30);

		updateinfo = new Composite(right, SWT.NONE);
		updateinfo.setBackground(SWTResourceManager.getColor(255, 255, 255));
		updateinfo.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));

		Label nohead = new Label(updateinfo, SWT.BORDER);
		nohead.setBounds(470, 74, 52, 55);
		//

		Label label_15 = new Label(updateinfo, SWT.NONE);
		label_15.setText("\u6635\u79F0\uFF1A");
		label_15.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		label_15.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_15.setBounds(336, 164, 52, 20);

		Label nomail = new Label(updateinfo, SWT.NONE);
		nomail.setText("\u65B0\u90AE\u7BB1\uFF1A");
		nomail.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		nomail.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		nomail.setBounds(336, 200, 61, 20);
		//邮箱

		Label label_17 = new Label(updateinfo, SWT.NONE);
		label_17.setText("\u65B0\u5BC6\u7801\uFF1A");
		label_17.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		label_17.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_17.setBounds(336, 276, 61, 20);

		Label label_18 = new Label(updateinfo, SWT.NONE);
		label_18.setText("\u7535\u8BDD\uFF1A");
		label_18.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		label_18.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_18.setBounds(336, 308, 52, 20);

		Label label_19 = new Label(updateinfo, SWT.NONE);
		label_19.setText("\u4ECB\u7ECD\uFF1A");
		label_19.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		label_19.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_19.setBounds(336, 346, 52, 20);

		Button button = new Button(updateinfo, SWT.NONE);
		button.setText("\u786E\u8BA4\u66F4\u6539");
		button.setBounds(336, 500, 98, 30);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String newName = text_1.getText();
				String newmail = text_2.getText();
				String code = text_6.getText();
				String pw = text_3.getText();
				String newInt = text_5.getText();
				String newTel = text_4.getText();
				EnterpriseUser temp = new EnterpriseUser
						(user.getId(), user.getPw(), null, newName, newInt, newTel, newmail, 1);
				if(newmail.equals(user.getMail())){
					ManageClientThread.getClientConServerThread(user.getId()).eUpdateNo(temp);
				}else{
					temp.setSubject(code);
					ManageClientThread.getClientConServerThread(user.getId()).eUpdate(temp);
				}
//				MessageBox code = new MessageBox(shell);
//				code.setText("提示");
//				code.setMessage("确认更改按钮事件");
//				code.open();
			}
		});

		Label label_14 = new Label(updateinfo, SWT.NONE);
		label_14.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_14.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		label_14.setText("\u9A8C\u8BC1\u7801\uFF1A");
		label_14.setBounds(336, 236, 67, 26);

		Label sendcode = new Label(updateinfo, SWT.NONE);
		sendcode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String mail = text_2.getText();
				if(!checkEmail(mail)){
					MessageBox code = new MessageBox(shell);
					code.setText("提示");
					code.setMessage("邮箱格式不对");
					code.open();
				}
				else{
					ManageClientThread.getClientConServerThread(user.getId()).sendeCode(mail);
					MessageBox code = new MessageBox(shell);
					code.setText("提示");
					code.setMessage("验证码已发送，请在邮箱中查收");
					code.open();
				}
				sendcode.setForeground(SWTResourceManager.getColor(51, 153, 255));
			}
		});
		sendcode.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		sendcode.setText("\u83B7\u53D6\u9A8C\u8BC1\u7801");
		sendcode.setForeground(SWTResourceManager.getColor(51, 153, 255));
		sendcode.setBounds(539, 238, 67, 20);

		Button button_1 = new Button(updateinfo, SWT.NONE);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				hideview();
				top.setText("现有内容");
				hsc.setVisible(true);
				System.out.println("返回按键事件");
			}
		});
		button_1.setText("\u8FD4\u56DE");
		button_1.setBounds(508, 500, 98, 30);

		text_1 = new Text(updateinfo, SWT.BORDER);
		text_1.setBackground(SWTResourceManager.getColor(255, 255, 255));
		text_1.setBounds(415, 164, 187, 26);

		text_2 = new Text(updateinfo, SWT.BORDER);
		text_2.setBackground(SWTResourceManager.getColor(255, 255, 255));
		text_2.setBounds(415, 200, 187, 26);

		text_3 = new Text(updateinfo, SWT.BORDER);
		text_3.setBackground(SWTResourceManager.getColor(255, 255, 255));
		text_3.setBounds(415, 276, 187, 26);

		text_4 = new Text(updateinfo, SWT.BORDER);
		text_4.setBackground(SWTResourceManager.getColor(255, 255, 255));
		text_4.setBounds(415, 308, 187, 26);

		text_5 = new Text(updateinfo, SWT.BORDER);
		text_5.setBackground(SWTResourceManager.getColor(255, 255, 255));
		text_5.setBounds(415, 346, 187, 116);

		text_6 = new Text(updateinfo, SWT.BORDER);
		text_6.setBackground(SWTResourceManager.getColor(255, 255, 255));
		text_6.setBounds(415, 236, 117, 26);
		c1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				allTextNum.setText(user.getBulletin().size()+"");
				int i = 0;
				for(Bulletin bulletin:user.getBulletin()){
					i += bulletin.getLike();
				}
				
				allLikeNum.setText(i+"");
				c1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				c2.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
				c3.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
				top.setText("现有内容：");
				hideview();
				hsc.setVisible(true);
//				for(Bulletin bulletin:user.getBulletin()){
//					viewStyle.addHaveOne(shell, have, bulletin,user.getName());
//				}
			}
		});
		c2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				c2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				c1.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
				c3.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
				top.setText("发表动态：");
				hideview();
				send.setVisible(true);

			}
		});
		c3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				c3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				c2.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
				c1.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
				top.setText("粉丝统计：");
				fanscount.setText(user.getFocusUsers().size()+"");
				int m = 0;
				int w = 0;
				int ww = 0;
				for(OrdinaryUser user_:user.getFocusUsers()){
					if(user_.getSex()==null){
						ww++;
					}
					if(user_.getSex().equals("男")){
						m++;
					}
					else if(user_.getSex().equals("女")){
						w++;
					}
				}
				mancount.setText(m+"");
				womancount.setText(w+"");
				nosexcount.setText(ww+"");
				fanscount.setText(user.getFocusUsers().size()+"");
				HashMap<String, Integer> jj = new HashMap<>();
				for(OrdinaryUser user_:user.getFocusUsers()){
					String s[] = null;
					if(user_.getRegion()!=null&&user_.getRegion().length()!=0){
						s = user_.getRegion().split("-");
						if(s.length!=2){
							continue;
						}
					}
					else{
						continue;
					}
					Set<String> key = jj.keySet();
					int i = 0;
					for(String k:key){
						if(k.equals(s[0])){
							jj.put(k, jj.get(k)+1);
							break;
						}
						i++;
					}
					if(i==jj.size()){
						jj.put(s[0], 1);
					}
				}
				
				int max = 0;
				String maxCity = "";
				int two = 0;
				String twoCity = "";
				int th = 0;
				String thCity = "";
				Set<String> key = jj.keySet();
				for(String k:key){
					if(max<jj.get(k)){
						maxCity = k;
						max = jj.get(k);
					}
				}
				jj.remove(maxCity);
				key = jj.keySet();
				for(String k:key){
					if(two<jj.get(k)){
						twoCity = k;
						two = jj.get(k);
					}
				}
				jj.remove(twoCity);
				key = jj.keySet();
				for(String k:key){
					if(th<jj.get(k)){
						thCity = k;
						th = jj.get(k);
					}
				}
				jj.remove(thCity);
				p1.setText(maxCity);
				p2.setText(twoCity);
				p3.setText(thCity);
				p1count.setText(max+"");
				p2count.setText(two+"");
				p3count.setText(th+"");
				hideview();
				fansCount.setVisible(true);
			}
		});
		upfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				System.out.println("选择附件事件");
				upfile.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
				org.eclipse.swt.widgets.FileDialog ff = new org.eclipse.swt.widgets.FileDialog(shell);
				ff.setText("选择附件");
				ff.open();
				filename.setText(ff.getFileName());
			}

			@Override
			public void mouseDown(MouseEvent e) {
				upfile.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			}
		});
		ohead.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				hideview();
//				oname.setText(user.getName());
//				oid.setText(user.getId());
//				omail.setText(user.getMail());
//				otel.setText(user.getTelephone());
//				oint.setText(user.getIntroduction());
				top.setText("企业信息：");
				officalinfo.setVisible(true);
				oname.setText(user.getName());
				oid.setText(user.getId());
				omail.setText(user.getMail());
				otel.setText(user.getTelephone()==null?"":user.getTelephone());
				oint.setText(user.getIntroduction()==null?"":user.getIntroduction());
			}
		});
		ohead.addListener(SWT.MouseDoubleClick, new Listener() {  
	    	
	    public void handleEvent(Event event) {  
	        if(event.button != 1) { 
	            return;  
	        }  
	        ManageClientThread.getClientConServerThread(user.getId()).eflush(user);
	    }  
	});  	
		for(Bulletin bulletin:user.getBulletin()){
			viewStyle.addHaveOne(shell, have, bulletin,user.getName());
		}
	}

	public static void hideview() {
		hsc.setVisible(false);
		send.setVisible(false);
		fansCount.setVisible(false);
		officalinfo.setVisible(false);
		updateinfo.setVisible(false);
	}
	
	
	
					////////		哈哈哈哈=======方法========哈哈哈哈			/////////
	public void showMessage(String text,String message){
//		JOptionPane.showMessageDialog(null, message);
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	MessageBox smsg = new MessageBox(shell);
				smsg.setText(text);
				smsg.setMessage(message);
				smsg.open();
		    }
		});
	}
	public String getNewTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sendTime = df.format(new Date());
		return sendTime;
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
	
	public void eUpdateSuccess(EnterpriseUser user_) {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	showMessage("提示：", "修改信息成功");
		    	user = user_;
		    	//添加操作
//		    	viewStyle.AddOfficalOne(offical,e_user);
		    }
		});
	}
	public void eflushSuccess(EnterpriseUser user2) {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	user = user2;
		    	Set<Integer> oneID = viewStyle.ones.keySet();
		    	for(int i:oneID){
		    		viewStyle.ones.get(i).dispose();
		    		have.layout();
		    	}
		    	for(Bulletin bulletin:user.getBulletin()){
					viewStyle.addHaveOne(shell, have, bulletin,user.getName());
				}
		    }
		});
	}
}
