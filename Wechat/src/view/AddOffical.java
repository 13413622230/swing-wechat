package view;

import java.awt.Toolkit;
import java.awt.Window;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.wechat.client.ManageClientThread;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;

public class AddOffical {

	protected Shell shell;
	private Text text;
	String user_id;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AddOffical window = new AddOffical();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public AddOffical(String user_id){
		this.user_id = user_id;
	}
	public AddOffical(){
		
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
		text.setBounds(109, 49, 225, 26);

		Label lblid = new Label(shell, SWT.NONE);
		lblid.setBounds(31, 49, 68, 23);
		lblid.setText("\u4F01\u4E1A\u540D\u79F0\uFF1A");

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
				String reg = "^[0-9]*[1-9][0-9]*$";
		        boolean boo = Pattern.compile(reg).matcher(id).find();
				if(id==null||id.length()==0){
					MessageBox s = new MessageBox(shell);
					s.setText("提示：");
					s.setMessage("企业账号不能为空");
					s.open();
				}
				else if(!boo){	
					MessageBox s = new MessageBox(shell);
					s.setText("提示：");
					s.setMessage("企业账号必须为数字");
					s.open();
				}
				else{
					System.out.println(user_id);
					System.out.println("分割线------------");
					ManageClientThread.getClientConServerThread(user_id).addeUser(user_id,id);
					dipo();
					
				}
				
				
			}
		});
	}
	public void dipo(){
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
		    	shell.close();
		    	System.out.println("界面关闭");
		    }
		});
	}
}
