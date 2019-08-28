package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.MyMouseAdapter;
import org.eclipse.wb.swt.SWTResourceManager;

import com.wechat.client.ClientThread;
import com.wechat.client.ManageClientThread;
import com.wechat.client.ManageWechat;
import com.wechat.model.EnterpriseUser;
import com.wechat.model.OrdinaryUser;
import com.wechat.model.User;
import com.wechat.util.ManageOfficalndex;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseTrackAdapter;

public class Login extends Thread{

	protected Shell shell;
	private Text text;
	private Text text_1;
	ClientThread ct = null;

	/**
	 * Launch the application.
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Login() throws UnknownHostException, IOException{
//		System.out.println(123);
		
//		ClientConnect.init();
//		ClientConnect.init();
		System.out.println("初始化成功");
	}
	public static void main(String[] args) {
		try {
			new Login().open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void run(){
		try {
			this.open();
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
		shell = new Shell(SWT.NONE);
		viewStyle.setbgview(shell);
		shell.setSize(550,400);
		shell.setBackgroundImage(SWTResourceManager.getImage(Login.class, "/img/login.png"));//背景图片		
		text = new Text(shell, SWT.NONE);
		viewStyle.setTextRGB(text,242,242,242);
		text.setFont(SWTResourceManager.getFont("Yu Gothic UI", 13, SWT.NORMAL));
		text.setBounds(163, 167, 263, 26);
		
		text_1 = new Text(shell, SWT.PASSWORD);
		text_1.setEchoChar('*');
		text_1.setFont(SWTResourceManager.getFont("Yu Gothic UI", 12, SWT.NORMAL));
		text_1.setBackground(SWTResourceManager.getColor(242, 242, 242));
		text_1.setBounds(163, 220, 263, 26);
		
		Label exit = new Label(shell, SWT.NONE);

		exit.setForeground(SWTResourceManager.getColor(204, 204, 204));
		exit.setFont(SWTResourceManager.getFont("Yu Gothic UI", 13, SWT.NORMAL));
		
		exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.close();
			}
		});
		exit.setBounds(506, 0, 33, 30);
		exit.setText("  X");
		
		Button person = new Button(shell, SWT.RADIO);
		person.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		person.setBounds(115, 267, 90, 38);
		person.setText("\u4E2A\u4EBA");
		
		Button offical = new Button(shell, SWT.RADIO);
		offical.setText("\u4F01\u4E1A");
		offical.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		offical.setBounds(224, 267, 90, 38);
		
		Label label = new Label(shell, SWT.WRAP);												//注册按钮
		label.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
				label.setForeground(SWTResourceManager.getColor(10, 155, 227));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				label.setForeground(SWTResourceManager.getColor(102, 204, 255));
			}
		});

		label.addMouseListener(new MouseAdapter() {
			
			public void mouseDown(MouseEvent e) {
				label.setForeground(SWTResourceManager.getColor(10, 155, 227));
			}
			@Override
			public void mouseUp(MouseEvent e) {
				label.setForeground(SWTResourceManager.getColor(102, 204, 255));
				shell.dispose();
				new Register().open();
				
			}
		});
		label.setForeground(SWTResourceManager.getColor(102, 204, 255));
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		label.setBounds(356, 274, 82, 26);
		label.setText("  \u7ACB\u5373\u6CE8\u518C");
		
		Label label_1 = new Label(shell, SWT.NONE);                                              //登录按钮
		label_1.addMouseListener(new MyMouseAdapter(this) {
			@Override
			public void mouseUp(MouseEvent e) {				
				viewStyle.setLabelBg(label_1, "submit");
				if(person.getSelection()){
					String id = text.getText();
					String pw = text_1.getText();
					OrdinaryUser user = (OrdinaryUser) new OrdinaryUser(id, pw, null, id, null, null);
					ct = new ClientThread();
					ct.start();
					try {
						ct.userLogin(user,login);
//						new Index(ct.userLogin(user,login)).open();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					new Index().open();
					System.out.println("login success");
//					try {
//						sleep(10000);
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					shell.close();
				}
				else if(offical.getSelection()){
					String id = text.getText();
					String pw = text_1.getText();
					User u = new User(id, pw, null, null);
					ct = new ClientThread();
					ct.start();
					try {
						ct.EnterpriseUserLogin(u,login);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					new Officalndex().open();
//					shell.close();
				}
				else{
					MessageBox a = new MessageBox(shell,SWT.OK|SWT.ICON_INFORMATION);
					a.setText("身份错误");
					a.setMessage("请选择登陆身份");
					a.open();
				}
				
			}
			@Override
			public void mouseDown(MouseEvent e) {
				viewStyle.setLabelBg(label_1, "submit-down");
			}
		});
		label_1.setBounds(115, 334, 160, 45);
		label_1.setText("             ");

	}

	public Color getShellBackground() {
		return shell.getBackground();
	}
	public void setShellBackground(Color background) {
		shell.setBackground(background);
	}
	public void showMessage(String text,String message){
//		JOptionPane.showMessageDialog(null, message);
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	MessageBox smsg = new MessageBox(shell, SWT.ICON_INFORMATION);
				smsg.setText(text);
				smsg.setMessage(message);
				smsg.open();
		    }
		});
	}
	public void loginSuccess(OrdinaryUser user,String text,String message){
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
				shell.close();
		    	System.out.println("关闭成功");
				
		    	System.out.println(ct);
		    	System.out.println(0000);
		    	Index wechat = new Index(user);
		    	//添加线程进线程管理类和添加用户主页进主页管理类
		    	ct.id = user.getId();
		    	ManageWechat.addWechat(user.getId(), wechat);
		    	ManageClientThread.addClientConServerThread(user.getId(), ct);
		    	wechat.open();
		    }
		});
	}
	public void eLoginSuccess(EnterpriseUser user){
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
				shell.close();
		    	System.out.println("关闭成功");
				
		    	System.out.println(ct);
		    	System.out.println(0000);
				Officalndex officalinde = new Officalndex(user);
//				shell.close();
		    	//添加线程进线程管理类和添加用户主页进主页管理类
		    	ct.id = user.getId();
		    	ManageOfficalndex.addSessionSendTime(user.getId(), officalinde);
		    	System.out.println(user.getId()+"的主页是"+ManageOfficalndex.getSessionSendTime(user.getId()));
		    	ManageClientThread.addClientConServerThread(user.getId(), ct);
		    	officalinde.open();
		    }
		});
	}
	public void closed(){
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	shell.close();
		    	System.out.println("关闭成功");
		    }
		});
	}
}
