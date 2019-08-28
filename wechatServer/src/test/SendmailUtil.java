package test;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import javax.mail.Transport;

import javax.mail.internet.AddressException;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMessage.RecipientType;

public class SendmailUtil {    

        public static void main(String[] args) throws AddressException,MessagingException {       

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
		 message.setRecipients(RecipientType.TO,new InternetAddress[] { new InternetAddress("1376261775@qq.com") });       
		
		 //设置邮件标题        
		
			message.setSubject("这是第一封Java邮件");        
			
			//设置邮件内容        
			
			message.setText("内容为： 这是第一封java发送来的邮件。");       
			
			 //得到邮差对象        
			
			Transport transport = session.getTransport();        
			
			//连接自己的邮箱账户        
			
			transport.connect("958911392@qq.com","tynphigfurwmbbbd");//密码为刚才得到的授权码        
			
			//发送邮件        
			transport.sendMessage(message, message.getAllRecipients());    
			
		}
        public boolean send_163mail(String strMail, String strTitle, String strText){  
            boolean bret = false;  
            try  
            {  
                final Properties props = new Properties();  
                props.put("mail.smtp.auth", "true");  
                props.put("mail.smtp.host", "smtp.163.com");  
      
                // 发件人的账号  
                props.put("mail.user", "xxxxxxxx@163.com");  
                //发件人的密码  
                props.put("mail.password", "xxxxxxx");   
      
                // 构建授权信息，用于进行SMTP进行身份验证  
                Authenticator authenticator = new Authenticator() {  
                    @Override  
                    protected PasswordAuthentication getPasswordAuthentication() {  
                        // 用户名、密码  
                        String userName = props.getProperty("mail.user");  
                        String password = props.getProperty("mail.password");  
                        return new PasswordAuthentication(userName, password);  
                    }  
                };  
                // 使用环境属性和授权信息，创建邮件会话  
                Session mailSession = Session.getInstance(props, authenticator);  
                // 创建邮件消息  
                MimeMessage message = new MimeMessage(mailSession);  
                // 设置发件人  
                String username = props.getProperty("mail.user");  
                InternetAddress form = new InternetAddress(username);  
                message.setFrom(form);  
      
                // 设置收件人  
                InternetAddress to = new InternetAddress(strMail);   
                message.setRecipient(RecipientType.TO, to);  
      
                // 设置邮件标题  
                message.setSubject(strTitle);  
      
                // 设置邮件的内容体  
                message.setContent(strText, "text/html;charset=UTF-8");  
                // 发送邮件  
                Transport.send(message);  
                bret = true;  
            }  
            catch (AddressException e) {  
                 e.printStackTrace();  
            }  
            catch (MessagingException e) {  
                 e.printStackTrace();  
            }  
            catch (Exception e){  
                e.printStackTrace();  
            }  
            return bret;  
        }  

 }
