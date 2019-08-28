package com.wechat.util;

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
		this.code = (int)(Math.random() * 10000);
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
    /**
     * 发送邮件
     * @param to 给谁发
     * @param text 发送内容
     */
    public void send_mail(String to) throws MessagingException {       

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
		 String text = this.getCode()+"";
		 message.setText(text);       
			
			 //得到邮差对象        
			
		 Transport transport = session.getTransport();        
			
			//连接自己的邮箱账户        
			
		 transport.connect("958911392@qq.com","aqubnwdrcqdvbfjj");//密码为刚才得到的授权码        
			
			//发送邮件        
		 transport.sendMessage(message, message.getAllRecipients());    
			
	}
    public String tostring(){
    	return code+"";
    }
    public boolean verification(String userCode){
    	System.out.println(tostring());
    	if(tostring().equals(userCode)){
    		return true;
    	}
    	return false;
    }
}