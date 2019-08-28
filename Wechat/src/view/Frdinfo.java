package view;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.management.monitor.MonitorMBean;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.wechat.client.ManageClientThread;
import com.wechat.client.ManageWechat;
import com.wechat.model.Moment;
import com.wechat.model.OrdinaryUser;
import com.wechat.util.ManageFrdinfo;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.MovementEvent;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class Frdinfo {

	protected Shell shell;
	private static org.eclipse.swt.graphics.Image likeico = SWTResourceManager.getImage(Frdinfo.class, "/img/like.png");
	private static org.eclipse.swt.graphics.Image likeed = SWTResourceManager.getImage(Frdinfo.class,
			"/img/like-down.png");
	private Text ncon;
	static ScrolledComposite m;
	static Label back;
	static Composite newmom;
	static Composite old;
	static Label npicview;
	OrdinaryUser user;
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Frdinfo window = new Frdinfo();
			
//			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(OrdinaryUser user) {
		this.user = user;
		Display display = Display.getDefault();
		createContents();
		old=F5(m);
		m.setContent(old);
		firstOp(m, old);
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
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell(SWT.NONE);
		shell.setImage(SWTResourceManager.getImage(Frdinfo.class, "/img/ico.png"));
		shell.setSize(380, 745);
		shell.setText("ÅóÓÑÈ¦");
		viewStyle.setbgview(shell);
		shell.setBackgroundImage(SWTResourceManager.getImage(Index.class, "/img/frdinfos.png"));
		shell.setLayout(new FormLayout());

		Label addmom = new Label(shell, SWT.NONE);
		FormData fd_addmom = new FormData();
		fd_addmom.top = new FormAttachment(0, 27);
		fd_addmom.left = new FormAttachment(100, -54);
		addmom.setLayoutData(fd_addmom);

		Label lblX = new Label(shell, SWT.NONE);
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		lblX.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		lblX.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		lblX.setAlignment(SWT.CENTER);
		lblX.setText("X");
		FormData fd_lblX = new FormData();
		fd_lblX.top = new FormAttachment(0);
		fd_lblX.right = new FormAttachment(addmom, 0, SWT.RIGHT);
		fd_lblX.left = new FormAttachment(0, 331);
		lblX.setLayoutData(fd_lblX);

		Composite main = new Composite(shell, SWT.NONE);
		fd_addmom.bottom = new FormAttachment(main, -24);
		fd_addmom.right = new FormAttachment(main, 0, SWT.RIGHT);
		FormData fd_main = new FormData();
		fd_main.bottom = new FormAttachment(0, 743);
		fd_main.right = new FormAttachment(0, 378);
		fd_main.top = new FormAttachment(0, 79);
		fd_main.left = new FormAttachment(0);
		main.setLayoutData(fd_main);
		StackLayout s = new StackLayout();
		main.setLayout(s);

		m = new ScrolledComposite(main, SWT.H_SCROLL | SWT.V_SCROLL);
		m.setExpandHorizontal(true);
		m.setExpandVertical(true);
		s.topControl = m;
		m.setMinSize(new Point(0, 0));

		newmom = new Composite(main, SWT.NONE);

		ncon = new Text(newmom, SWT.BORDER | SWT.WRAP);
		ncon.setBackground(SWTResourceManager.getColor(253, 253, 253));
		ncon.setBounds(24, 26, 325, 158);

		Label npname = new Label(newmom, SWT.NONE);
		npname.setBounds(24, 223, 11, 9);
		npname.setVisible(false);
		
		
		Label npic = new Label(newmom, SWT.NONE);
		 npicview = new Label(newmom, SWT.NONE);
		npicview.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {

			}
		});
		npic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				FileDialog n = new FileDialog(shell);
				n.setText("Ñ¡ÔñÍ¼Æ¬");
				n.open();
				String np1 = n.getFilterPath();
				String np2 = n.getFileName();
				String np = np1 + "\\" + np2;
				npicview.setImage(SWTResourceManager.getImage(np));
				npname.setText(np2);
			}
		});
		npic.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		npic.setText("\u4E0A\u4F20\u56FE\u7247");
		npic.setForeground(SWTResourceManager.getColor(135, 206, 250));
		npic.setBounds(24, 203, 76, 20);
		npicview.setAlignment(SWT.CENTER);
		npicview.setBounds(24, 249, 97, 95);

		Label nsend = new Label(newmom, SWT.NONE);

		nsend.setAlignment(SWT.CENTER);
		nsend.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		nsend.setText(" \u53D1\u8868 ");
		nsend.setForeground(SWTResourceManager.getColor(60, 179, 113));
		nsend.setBounds(296, 203, 53, 20);

		back = new Label(shell, SWT.NONE);

		back.setText("<");
		back.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		back.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 20, SWT.NORMAL));
		back.setAlignment(SWT.CENTER);
		FormData fd_back = new FormData();
		fd_back.bottom = new FormAttachment(main, -24);
		fd_back.right = new FormAttachment(main, 35);
		fd_back.left = new FormAttachment(main, 0, SWT.LEFT);
		back.setLayoutData(fd_back);
		back.setVisible(false);

		Label F5 = new Label(shell, SWT.NONE);
		F5.addListener(SWT.MouseDoubleClick, new Listener() {

			public void handleEvent(Event event) {
				if (event.button != 1) {
					return;
				}
				flush();
				
//				m.setContent(old);
				System.out.println("Ë«»÷666");

				
			}

			
		});
		FormData fd_F5 = new FormData();
		fd_F5.bottom = new FormAttachment(addmom, 0, SWT.BOTTOM);
		fd_F5.left = new FormAttachment(back, 104);
		fd_F5.right = new FormAttachment(addmom, -81);
		fd_F5.top = new FormAttachment(0, 12);
		F5.setLayoutData(fd_F5);
		addmom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				m.setVisible(false);
				newmom.setVisible(true);
				back.setVisible(true);
			}
		});
		nsend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String p = npname.getText();
				String wc = ncon.getText();
				if (wc == null || wc == "") {

				} else if ((p==null || p.length()==0) && (wc.length() != 0)) { // ÎÞÍ¼ÅóÓÑÈ¦
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sendTime = df.format(new Date());
					Moment temp = new Moment("1", user.getId(), user.getName(), wc, sendTime, ""
							, "", 0, new ArrayList<>());
					try {
						ManageClientThread.getClientConServerThread(user.getId()).addMement(temp);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					viewStyle.addWordmom(m, moments, "1"
//							, SWTResourceManager.getImage(getClass(), "wechat/headimg/"+user.getId()+".jpg"),
//							user.getId(), wc, temp,user,true); // ÈÝÆ÷£¬ÅóÓÑÈ¦id£¬Í·Ïñ£¬Ãû×Ö£¬ÄÚÈÝ£¬ÔÞÊý
//					System.out.println(p);
//					ncon.setText("");
//					back.setVisible(false);
//					newmom.setVisible(false);
//					m.setVisible(true);
				} else {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sendTime = df.format(new Date());
					Moment temp = new Moment("1", user.getId(), user.getName(), wc, sendTime, ""
							, "", 0, new ArrayList<>());
					try {
						ManageClientThread.getClientConServerThread(user.getId()).addMement(temp);
						
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					viewStyle.addPicmom(m, old, "1"
							, SWTResourceManager.getImage(getClass(), "wechat/headimg/"+user.getId()+".jpg"),
							user.getId(), wc
							,SWTResourceManager.getImage(getClass(), "wechat/headimg/"+user.getId()+".jpg")
							, temp,user,true);
					ncon.setText("");
					npicview.setImage(null);
					back.setVisible(false);
					newmom.setVisible(false);
					m.setVisible(true);
				}
				System.out.println("Ìí¼ÓÅóÓÑÈ¦");
			}
		});
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				back.setVisible(false);
				newmom.setVisible(false);
				m.setVisible(true);
			}
		});
		
	
		
	}
	public void addMoent(Moment moment){
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
		    	viewStyle.addWordmom(m, old
						, SWTResourceManager.getImage(getClass(), "wechat/headimg/"+user.getId()+".jpg"),
						moment,user,true); // ÈÝÆ÷£¬ÅóÓÑÈ¦id£¬Í·Ïñ£¬Ãû×Ö£¬ÄÚÈÝ£¬ÔÞÊý
//				System.out.println(p);
				ncon.setText("");
				back.setVisible(false);
				newmom.setVisible(false);
				m.setVisible(true);
		    }
		});
	}
	public  void firstOp(ScrolledComposite m,Composite moments){
		for(Moment mm:user.getAllmonents()){
			boolean boo;
			if(user.getId().equals(mm.getUser_id())){
				boo = true;
			}
			else{
				boo = false;
			}
				if ((mm.getImg() == null || mm.getImg().length()==0) && (mm.getContent().length() != 0)) { // ÎÞÍ¼ÅóÓÑÈ¦
				viewStyle.addWordmom(m, moments
						, SWTResourceManager.getImage("wechat/headimg/"+mm.getUser_id()+".jpg"),
						 mm,user,boo); // ÈÝÆ÷£¬ÅóÓÑÈ¦id£¬Í·Ïñ£¬Ãû×Ö£¬ÄÚÈÝ£¬ÔÞÊý
				ncon.setText("");
				back.setVisible(false);
				newmom.setVisible(false);
				m.setVisible(true);
			} else {
				try {
					URL url=new URL("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_ca79a146.png"); 
					HttpURLConnection conn= (HttpURLConnection)url.openConnection();
					InputStream is;
					is = conn.getInputStream();
					ImageData id=new ImageData(is);
					org.eclipse.swt.graphics.Image img= new org.eclipse.swt.graphics.Image(null, id);
					viewStyle.addPicmom(m, moments, "1", SWTResourceManager.getImage("wechat/headimg/"+mm.getUser_id()+".jpg"),
							mm.getName(), mm.getContent(), img, mm,user,boo);
					System.out.println(mm.getLike());
					ncon.setText("");
					npicview.setImage(null);
					back.setVisible(false);
					newmom.setVisible(false);
					m.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
	}
	public static  Composite F5(ScrolledComposite m){
		System.out.println("³õÊ¼»¯ÈÝÆ÷");
		Composite nmoments = new Composite(m, SWT.NONE);
		nmoments.setLayout(new RowLayout(SWT.VERTICAL));
		return nmoments;
	}
	public void flush() {
		// TODO Auto-generated method stub
		ManageClientThread.getClientConServerThread(user.getId()).flush();
	}

	public void flushfSuccess(ArrayList<Moment> moment) {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	user.setAllmonents(moment);
		    	m.setContent(null);
				m.setMinHeight(0);
				old=F5(m);
				m.setContent(old);
				firstOp(m, old);
				old.layout();
		    }
		});
	}
}
