package view;

import java.awt.Toolkit;
import java.awt.Window;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.wechat.client.ManageClientThread;
import com.wechat.model.OrdinaryUser;
import com.wechat.model.User;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;

public class Invite {

	protected Shell shell;
	private Text text;
	int g_id;
	String user_id;

	public Invite(int groupId,String user_id) {
		// TODO Auto-generated constructor stub
		this.g_id = groupId;
		this.user_id = user_id;
	}
	public Invite(){
		
	}
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Invite window = new Invite();
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
		shell = new Shell(SWT.NONE);

		shell.setSize(455, 118);
		shell.setText("\u9080\u8BF7\u597D\u53CB");
		shell.setImage(SWTResourceManager.getImage(getClass(), "/img/ico.png"));
		shell.setBackgroundImage(SWTResourceManager.getImage(getClass(), "/img/vbg.png"));
		viewStyle.setbgview(shell);

		Label lblX = new Label(shell, SWT.NONE);
		lblX.setForeground(SWTResourceManager.getColor(128, 128, 0));
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		lblX.setAlignment(SWT.CENTER);
		lblX.setBounds(420, 0, 33, 20);
		lblX.setText("X");

		text = new Text(shell, SWT.NONE);
		text.setBounds(97, 49, 237, 26);

		Label lblid = new Label(shell, SWT.NONE);
		lblid.setBounds(31, 52, 60, 20);
		lblid.setText("\u597D\u53CBID\uFF1A");

		Label ibt = new Label(shell, SWT.NONE);

		ibt.setForeground(SWTResourceManager.getColor(240, 255, 240));
		ibt.setBackground(SWTResourceManager.getColor(50, 205, 50));
		ibt.setAlignment(SWT.CENTER);
		ibt.setBounds(360, 49, 47, 23);
		ibt.setText("确认");
		ibt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				ibt.setForeground(SWTResourceManager.getColor(240, 248, 255));
				ibt.setBackground(SWTResourceManager.getColor(60, 179, 113));
			}

			public void mouseUp(MouseEvent e) {
				ibt.setForeground(SWTResourceManager.getColor(240, 255, 240));
				ibt.setBackground(SWTResourceManager.getColor(50, 205, 50));
				
				String id = text.getText();
				ManageClientThread.getClientConServerThread(user_id).addGroupM(id,g_id);
				shell.close();
//				MessageBox s = new MessageBox(shell);
//				s.setText("提示：");
//				s.setMessage("请输入好友id");
//				s.open();
			}
		});

	}
	
}
