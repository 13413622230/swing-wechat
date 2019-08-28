package test;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import java.util.Properties;

/**
 * 邮件工具类
 */
public class MailUtil {
	private int code;					//用户注册验证码
	public MailUtil() {
		super();
		this.code = (int)(Math.random() * 9999);
	}
    /**
     * 发送邮件
     * @param to 给谁发
     * @param text 发送内容
     */
    public static void send_mail(String to,String text) throws MessagingException {
        //创建连接对象 连接到邮件服务器
        Properties properties = new Properties();
        //设置发送邮件的基本参数
        //发送邮件服务器
        properties.put("mail.smtp.host", "smtp.qq.com");
        //发送端口
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        //设置发送邮件的账号和密码
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //两个参数分别是发送邮件的账户和密码
                return new PasswordAuthentication("958911392@qq.com","tynphigfurwmbbbd");
            }
        });

        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        message.setFrom(new InternetAddress("958911392@qq.com"));
        //设置收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
        //设置主题
        message.setSubject("这是一份测试邮件");
        //设置邮件正文  第二个参数是邮件发送的类型
        message.setContent(text,"text/html;charset=UTF-8");
        //发送一封邮件
        Transport.send(message);
    }
    public static void send_mail1(String to,String text) throws MessagingException {       

		  Properties properties = new Properties();
		
		  properties.put("mail.transport.protocol", "smtp");// 连接协议        
		
		  properties.put("mail.smtp.host", "smtp.qq.com");// 主机名        
		
		  properties.put("mail.smtp.port", 465);// 端口号        
		
		  properties.put("mail.smtp.auth", "true");        
		
		  properties.put("mail.smtp.ssl.enable", "true");//设置是否使用ssl安全连接  ---一般都使用        
		
		  properties.put("mail.debug", "true");//设置是否显示debug信息  true 会在控制台显示相关信息        
		
		//得到回话对象        
		
		Session session = Session.getInstance(properties);        
		
		// 获取邮件对象        
		
		Message message = new MimeMessage(session);        
		
		//设置发件人邮箱地址       
		
		 message.setFrom(new InternetAddress("958911392@qq.com"));       
		
		// 设置收件人地址        
		 message.setRecipients(RecipientType.TO,new InternetAddress[] { new InternetAddress(to) });       
		
		 //设置邮件标题        
		
			message.setSubject("类微信注册");        
			
			//设置邮件内容        
			
			message.setText(text);       
			
			 //得到邮差对象        
			
			Transport transport = session.getTransport();        
			
			//连接自己的邮箱账户        
			
			transport.connect("958911392@qq.com","tynphigfurwmbbbd");//密码为刚才得到的授权码        
			
			//发送邮件        
			transport.sendMessage(message, message.getAllRecipients());    
			
		}
    
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
    
}