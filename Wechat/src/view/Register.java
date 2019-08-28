package view;

import java.io.IOException;
import java.security.acl.Permission;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.MyMouseAdapter;
import org.eclipse.wb.swt.SWTResourceManager;

import com.ibm.icu.impl.StringRange;
import com.ibm.icu.text.MessagePatternUtil.MessageContentsNode;
import com.wechat.client.ClientThread;
import com.wechat.model.EnterpriseUser;
import com.wechat.model.OrdinaryUser;
import com.wechat.model.User;

import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

public class Register {

	protected Shell shell;
	private Text text;
	private Text id;
	private Text pw;
	private Text mail;
	private Text code;
	ClientThread ct = null;
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
//	public Register(ClientThread ct){
//		this.ct = ct;
//	}
	public Register(){
	}
	public static void main(String[] args) {
		try {
			Register window = new Register();
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
		shell.setImage(SWTResourceManager.getImage(Register.class, "/img/ico.png"));
		viewStyle.setbgview(shell);
		shell.setSize(630, 430);
		shell.setBackgroundImage(SWTResourceManager.getImage(Login.class, "/img/register.png"));// 背景图片
		Label label = new Label(shell, SWT.NONE);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.close();
				try {
					new Login().open();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		label.setText("  X");
		label.setForeground(SWTResourceManager.getColor(204, 204, 204));
		label.setFont(SWTResourceManager.getFont("Yu Gothic UI", 13, SWT.NORMAL));
		label.setBounds(585, 0, 33, 30);

		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(163, 371, 160, 45);

		id = new Text(shell, SWT.NONE);
		id.setFont(SWTResourceManager.getFont("Yu Gothic UI", 13, SWT.NORMAL));
		id.setBackground(SWTResourceManager.getColor(242, 242, 242));
		id.setBounds(213, 159, 263, 26);

		pw = new Text(shell, SWT.PASSWORD);
		pw.setFont(SWTResourceManager.getFont("Yu Gothic UI", 12, SWT.NORMAL));
		pw.setEchoChar('*');
		pw.setBackground(SWTResourceManager.getColor(242, 242, 242));
		pw.setBounds(213, 215, 263, 26);

		Button person = new Button(shell, SWT.RADIO);
		person.setText("\u4E2A\u4EBA");
		person.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		person.setBounds(163, 317, 90, 38);

		Button offical = new Button(shell, SWT.RADIO);
		offical.setText("\u4F01\u4E1A");
		offical.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		offical.setBounds(272, 317, 90, 38);

		mail = new Text(shell, SWT.NONE);
		mail.setFont(SWTResourceManager.getFont("Yu Gothic UI", 12, SWT.NORMAL));
		mail.setBackground(SWTResourceManager.getColor(242, 242, 242));
		mail.setBounds(213, 275, 181, 26);
		mail.setTextLimit(20);

		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		label_2.setBounds(213, 112, 195, 20);
		label_2.setText("\uFF08\u8BE6\u7EC6\u4FE1\u606F\u6210\u529F\u6CE8\u518C\u540E\u5B8C\u5584\uFF09");

		
		Label gg = new Label(shell, SWT.NONE);
		gg.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
		gg.setBounds(399, 275, 77, 20);
		gg.setText("\u83B7\u53D6\u9A8C\u8BC1\u7801");
		gg.setVisible(false);
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.addMouseListener(new MyMouseAdapter(this) {
			@Override
			public void mouseDown(MouseEvent e) {
				label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
			}

			public void mouseUp(MouseEvent e) {
//				label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
				String mails = new String();			
				mails = mail.getText();
				if(!checkEmail(mails)){
					MessageBox msg = new MessageBox(shell, SWT.ICON_INFORMATION);
					msg.setText("提示：");
					msg.setMessage("邮箱格式不对");
					msg.open();
				}
				else{
					try {
						label_3.setVisible(false);
						gg.setVisible(true);
						ct = new ClientThread();
						ct.start();
						ct.sendCode(mails,re);
//						if(){
//							msg.setText("提示：");
//							msg.setMessage("验证码已发送，请在邮箱中查收");
//							msg.open();
//						}
//						else{
//							msg.setText("提示：");
//							msg.setMessage("验证码发送失败");
//							msg.open();
//						}
//						ct.getSocket().close();
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label_3.setBounds(399, 275, 77, 20);
		label_3.setText("\u83B7\u53D6\u9A8C\u8BC1\u7801");

		
		
		
		
		code = new Text(shell, SWT.NONE);
		code.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				code.setText("");
				code.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
			}
		});
		code.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		code.setText("\u8F93\u5165\u9A8C\u8BC1\u7801");
		code.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		code.setBackground(SWTResourceManager.getColor(242, 242, 242));
		code.setBounds(399, 325, 87, 20);
		label_1.addMouseListener(new MouseAdapter() { // 注册按钮事件
			@Override
			public void mouseUp(MouseEvent e) {
				label_1.setBackgroundImage(SWTResourceManager.getImage(Login.class, "/img/resub.png"));
				if (person.getSelection()) { // 个人注册
					String id_ = id.getText();
					String pw_ = pw.getText();
					String code_ = code.getText();
					String mail_ = mail.getText();
					try {
						MessageBox a = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
						a.setText("提示");
						OrdinaryUser user = new OrdinaryUser(id_, pw_, null, id_, null, null);
						user.setMail(mail_);
						ct.userRegister(user, code_);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (offical.getSelection()) { // 企业注册
					String id_ = id.getText();
					String pw_ = pw.getText();
					String code_ = code.getText();
					String mail_ = mail.getText();
					try {
						EnterpriseUser user = new EnterpriseUser(id_, pw_, null, id_
								, null, null, null, null, null, null, mail_);
						ct.EnterpriseUserRegister(user, code_);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					MessageBox a = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
					a.setText("身份错误");
					a.setMessage("请选择登陆身份");
					a.open();
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				label_1.setBackgroundImage(SWTResourceManager.getImage(Login.class, "/img/resub-down.png"));
			}
		});

	}
	public void showMessage(String text,String message){
		System.out.println(message);
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
}
