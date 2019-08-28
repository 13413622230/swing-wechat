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
		
		  properties.put("mail.transport.protocol", "smtp");// ����Э��        
		
		  properties.put("mail.smtp.host", "smtp.qq.com");// ������        
		
		  properties.put("mail.smtp.port", 465);// �˿ں�        
		
		  properties.put("mail.smtp.auth", "true");        
		
		  properties.put("mail.smtp.ssl.enable", "true");//�����Ƿ�ʹ��ssl��ȫ����  ---һ�㶼ʹ��        
		
		  properties.put("mail.debug", "true");//�����Ƿ���ʾdebug��Ϣ  true ���ڿ���̨��ʾ�����Ϣ        
		
		//�õ��ػ�����        
		
		Session session = Session.getInstance(properties);        
		
		// ��ȡ�ʼ�����        
		
		Message message = new MimeMessage(session);        
		
		//���÷����������ַ       
		
		 message.setFrom(new InternetAddress("958911392@qq.com"));       
		
		// �����ռ��˵�ַ        
		 message.setRecipients(RecipientType.TO,new InternetAddress[] { new InternetAddress("1376261775@qq.com") });       
		
		 //�����ʼ�����        
		
			message.setSubject("���ǵ�һ��Java�ʼ�");        
			
			//�����ʼ�����        
			
			message.setText("����Ϊ�� ���ǵ�һ��java���������ʼ���");       
			
			 //�õ��ʲ����        
			
			Transport transport = session.getTransport();        
			
			//�����Լ��������˻�        
			
			transport.connect("958911392@qq.com","tynphigfurwmbbbd");//����Ϊ�ղŵõ�����Ȩ��        
			
			//�����ʼ�        
			transport.sendMessage(message, message.getAllRecipients());    
			
		}
        public boolean send_163mail(String strMail, String strTitle, String strText){  
            boolean bret = false;  
            try  
            {  
                final Properties props = new Properties();  
                props.put("mail.smtp.auth", "true");  
                props.put("mail.smtp.host", "smtp.163.com");  
      
                // �����˵��˺�  
                props.put("mail.user", "xxxxxxxx@163.com");  
                //�����˵�����  
                props.put("mail.password", "xxxxxxx");   
      
                // ������Ȩ��Ϣ�����ڽ���SMTP���������֤  
                Authenticator authenticator = new Authenticator() {  
                    @Override  
                    protected PasswordAuthentication getPasswordAuthentication() {  
                        // �û���������  
                        String userName = props.getProperty("mail.user");  
                        String password = props.getProperty("mail.password");  
                        return new PasswordAuthentication(userName, password);  
                    }  
                };  
                // ʹ�û������Ժ���Ȩ��Ϣ�������ʼ��Ự  
                Session mailSession = Session.getInstance(props, authenticator);  
                // �����ʼ���Ϣ  
                MimeMessage message = new MimeMessage(mailSession);  
                // ���÷�����  
                String username = props.getProperty("mail.user");  
                InternetAddress form = new InternetAddress(username);  
                message.setFrom(form);  
      
                // �����ռ���  
                InternetAddress to = new InternetAddress(strMail);   
                message.setRecipient(RecipientType.TO, to);  
      
                // �����ʼ�����  
                message.setSubject(strTitle);  
      
                // �����ʼ���������  
                message.setContent(strText, "text/html;charset=UTF-8");  
                // �����ʼ�  
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
