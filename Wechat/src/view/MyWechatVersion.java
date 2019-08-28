package view;

import java.awt.Toolkit;
import java.awt.Window;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MyWechatVersion {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyWechatVersion window = new MyWechatVersion();
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

		shell.setSize(500, 350);
		shell.setText("΢�Ű汾");
		shell.setImage(SWTResourceManager.getImage(getClass(), "/img/ico.png"));
		shell.setBackgroundImage(SWTResourceManager.getImage(getClass(), "/img/vbg.png"));
		viewStyle.setbgview(shell);
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(105, 105, 105));
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		lblNewLabel.setBounds(145, 78, 234, 28);
		lblNewLabel.setText("\u5C0F\u7EC4\uFF1A\u5C0F\u706B\u8F66BiuBiuBiu~~");
		
		Label label = new Label(shell, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(105, 105, 105));
		label.setAlignment(SWT.CENTER);
		label.setText("\u7C7B\u5FAE\u4FE1\u7EC8\u7ED3\u7248");
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label.setBounds(171, 31, 166, 28);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setForeground(SWTResourceManager.getColor(105, 105, 105));
		label_1.setText("\u7EC4\u5458\uFF1A");
		label_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_1.setBounds(145, 131, 51, 28);
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setForeground(SWTResourceManager.getColor(105, 105, 105));
		label_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_2.setBounds(145, 165, 234, 132);
		
		Label lblX = new Label(shell, SWT.NONE);
		lblX.setForeground(SWTResourceManager.getColor(128, 128, 0));
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		lblX.setAlignment(SWT.CENTER);
		lblX.setBounds(465, 0, 33, 20);
		lblX.setText("X");
		

	}

}
