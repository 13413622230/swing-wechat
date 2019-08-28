package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class NoticDetail {

	protected Shell shell;
	private static String ntt;
	private static String nntime;
	private static String nnman;
	private static String nncon;

	public NoticDetail(String ntt2, String nntime2, String nnman2, String nncon2) {
		this.ntt=ntt2;
		this.nntime=nntime2;
		this.nnman=nnman2;
		this.nncon=nncon2;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			NoticDetail window = new NoticDetail(ntt,nntime,nnman,nncon);
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
		viewStyle.setbgview(shell);
		shell.setSize(686, 935);
		
		ScrolledComposite ncsrc = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ncsrc.setBounds(0, 0, 684, 933);
		ncsrc.setExpandHorizontal(true);
		ncsrc.setExpandVertical(true);
		
		Composite nc = new Composite(ncsrc, SWT.NONE);
		viewStyle.move(shell, nc);
		nc.setBackgroundMode(SWT.INHERIT_DEFAULT);
		nc.setBackground(SWTResourceManager.getColor(252, 252, 252));
		
		Label nt = new Label(nc, SWT.NONE);
		nt.setText(ntt);
		nt.setForeground(SWTResourceManager.getColor(6, 6, 6));
		nt.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 14, SWT.NORMAL));
		nt.setBounds(26, 28, 607, 61);
		
		Label nman = new Label(nc, SWT.NONE);
		nman.setText(nnman);
		nman.setForeground(SWTResourceManager.getColor(128, 128, 128));
		nman.setBounds(26, 118, 100, 20);
		
		Label ntime = new Label(nc, SWT.NONE);
		ntime.setText(nntime);
		ntime.setForeground(SWTResourceManager.getColor(128, 128, 128));
		ntime.setBounds(132, 118, 100, 20);
		
		Label ncon = new Label(nc, SWT.WRAP);
		ncon.setText(nncon);	;
		ncon.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		ncon.setBounds(57, 164, 560, 695);
		
		Label biu = new Label(nc, SWT.NONE);
		biu.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		biu.setBounds(57, 95, 560, 3);
		
		Label lblX = new Label(nc, SWT.NONE);
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		lblX.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		lblX.setAlignment(SWT.CENTER);
		lblX.setBounds(634, 2, 46, 20);
		lblX.setText("X");
		ncsrc.setContent(nc);
		ncsrc.setMinSize(new Point(0, 0));

	}
}
