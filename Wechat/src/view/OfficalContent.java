package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import javax.crypto.CipherInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;

import com.wechat.client.ManageClientThread;
import com.wechat.client.ManageWechat;
import com.wechat.model.Bulletin;
import com.wechat.model.EnterpriseUser;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Button;

public class OfficalContent {

	protected static Shell shell;
	static Composite cindex;
	static Composite header;
	static Composite ncContent;
	static Label ntitle;
	static Label ncman;
	static Label ntime;
	static Label ncc;
	EnterpriseUser e_user;
	String user_id;

	public OfficalContent(EnterpriseUser e_user, String user_id) {
		// TODO Auto-generated constructor stub
		this.e_user = e_user;
		this.user_id = user_id;
	}
	public OfficalContent(){
		
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			OfficalContent window = new OfficalContent();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
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

		shell = new Shell(SWT.None);
		shell.setSize(686, 935);
		shell.setText("公众号");
		viewStyle.setbgview(shell);
		shell.setImage(SWTResourceManager.getImage(Index.class, "/img/ico.png"));
		shell.setLayout(new FormLayout());
		Composite BG = new Composite(shell, SWT.NONE);
		StackLayout bgl = new StackLayout();
		BG.setLayout(bgl);
		FormData fd_BG = new FormData();
		fd_BG.top = new FormAttachment(0);
		fd_BG.left = new FormAttachment(0);
		fd_BG.bottom = new FormAttachment(0, 933);
		fd_BG.right = new FormAttachment(0, 684);
		BG.setLayoutData(fd_BG);

		cindex = new Composite(BG, SWT.NONE);
		ncContent = new Composite(BG, SWT.NONE);

		bgl.topControl = cindex;

		header = new Composite(cindex, SWT.NONE);
		header.setBackground(SWTResourceManager.getColor(245, 245, 245));
		header.setBounds(0, 0, 684, 157);
		header.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Label oh = new Label(header, SWT.NONE);
		oh.setBounds(288, 10, 52, 55);

		Label oname = new Label(header, SWT.NONE);
		oname.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		oname.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		oname.setBounds(346, 22, 115, 20);
		oname.setText("\u8086\u65E0\u5FCC\u60EE");

		Label label = new Label(header, SWT.WRAP);
		label.setText(
				"\u6B22\u8FCE\u5404\u8DEF\u8D27\u8272\u5173\u6CE8\u201C\u8086\u65E0\u5FCC\u60EE\u201D\u3002\u672C\u516C\u4F17\u53F7\u6709\u4E09\u79CD\u529F\u80FD1\uFF0C\u5BA2\u89C2\u5206\u6790\u5F53\u4E0B\u7ECF\u6D4E\u5F62\u52BF2\uFF0C\u6570\u5B57\u8D27\u5E01\u884C\u60C5\u5206\u6790 3\uFF0C\u63ED\u7A7F\u5E01\u5708\u4F20\u9500\u9A97\u5C40\u3002\u795D\u60A8\u5728\u8FD9\u4E2A\u5947\u8469\u7684\u516C\u4F17\u5E73\u53F0\u6709\u6240\u6536\u83B7\u3002\u4E48\u4E48\u54D2\u3002");
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.NORMAL));
		label.setBounds(64, 68, 561, 63);

		Label lblNewLabel = new Label(cindex, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(245, 245, 245));
		lblNewLabel.setBounds(0, 160, 684, 22);
		lblNewLabel.setText("  \u8FD1\u671F\u516C\u544A\uFF1A");

		ScrolledComposite csc = new ScrolledComposite(cindex, SWT.H_SCROLL | SWT.V_SCROLL);
		csc.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		csc.setBounds(-3, 185, 687, 738);
		csc.setExpandHorizontal(true);
		csc.setExpandVertical(true);

		Composite c = new Composite(csc, SWT.NONE);
		c.setBackground(SWTResourceManager.getColor(250, 250, 250));
		c.setBackgroundMode(SWT.INHERIT_DEFAULT);
		csc.setContent(c);
		csc.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		csc.setMinSize(new Point(0, 1000));
		c.setLayout(new RowLayout(SWT.HORIZONTAL));

		ScrolledComposite nsc = new ScrolledComposite(ncContent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		nsc.setBounds(0, 0, 684, 933);
		nsc.setExpandHorizontal(true);
		nsc.setExpandVertical(true);

		Composite na = new Composite(nsc, SWT.NONE);
		na.setBackground(SWTResourceManager.getColor(252, 252, 252));
		na.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Label back = new Label(na, SWT.NONE);

		back.setForeground(SWTResourceManager.getColor(169, 169, 169));
		back.setSize(47, 42);
		back.setAlignment(SWT.CENTER);
		back.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 20, SWT.NORMAL));
		back.setText("<");

		ntitle = new Label(na, SWT.NONE);
		ncman = new Label(na, SWT.NONE);
		ntime = new Label(na, SWT.NONE);
		ncc = new Label(na, SWT.WRAP);
		ntitle.setForeground(SWTResourceManager.getColor(6, 6, 6));
		ntitle.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
		ntitle.setBounds(25, 47, 607, 61);
		ntitle.setText("\u6807\u9898null");

		Label biu = new Label(na, SWT.NONE);
		biu.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		biu.setBounds(56, 114, 560, 3);

		ncman.setForeground(SWTResourceManager.getColor(128, 128, 128));
		ncman.setBounds(25, 137, 100, 20);
		ncman.setText("\u4F5C\u8005null");

		ntime.setForeground(SWTResourceManager.getColor(128, 128, 128));
		ntime.setText("2018/5/30");
		ntime.setBounds(131, 137, 100, 20);

		ncc.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		ncc.setBounds(56, 183, 560, 695);
		ncc.setText("内容");
		nsc.setContent(na);
		nsc.setMinSize(new Point(0, 500));
		viewStyle.move(shell,header);

		Label lblX = new Label(header, SWT.NONE);
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		lblX.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		lblX.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		lblX.setAlignment(SWT.CENTER);
		lblX.setBounds(647, 0, 37, 31);
		lblX.setText("X");
		
		Label fllow = new Label(header, SWT.NONE);
		fllow.setForeground(SWTResourceManager.getColor(30, 144, 255));
		fllow.setBounds(622, 127, 52, 20);
		fllow.setText("关注+");
		
		Label fllowed = new Label(header, SWT.NONE);
		fllowed.setAlignment(SWT.CENTER);
		fllowed.setForeground(SWTResourceManager.getColor(30, 144, 255));
		fllowed.setBounds(622, 127, 52, 20);
		fllowed.setText("√");
		fllowed.addMouseListener(new MouseAdapter() {										//点击取消关注
			@Override
			public void mouseUp(MouseEvent e) {
				fllowed.setVisible(false);
				fllow.setVisible(true);		
				ManageWechat.getWechat(user_id).deleUser(e_user);
			}
		});
		fllow.addMouseListener(new MouseAdapter() {										//点击关注
			@Override
			public void mouseUp(MouseEvent e) {
				fllow.setVisible(false);
				fllowed.setVisible(true);
				ManageClientThread.getClientConServerThread(user_id).addeUser(user_id, e_user.getId());
				}
		});
		
		viewStyle.move(shell,ncContent);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				ncContent.setVisible(false);
				cindex.setVisible(true);
			}
		});
		fllow.setVisible(false);
		fllowed.setVisible(true);
		oname.setText(e_user.getName());
		label.setText(e_user.getIntroduction());
		oh.setImage(SWTResourceManager.getImage("wechat\\headimg\\"+e_user.getHeadimg()));
		for(Bulletin b:e_user.getBulletin()){
			viewStyle.addOCone(c, ncContent, null,b,e_user.getName());
		}
	}

	public static void viewnc() {
		cindex.setVisible(false);
		ncContent.setVisible(true);
	}
	public static void setnc(String title, String manname, String time, String nccc) {
		ntitle.setText(title);
		ncman.setText(manname);
		ntime.setText(time);
		ncc.setText(nccc);
	}	
}
