package com.wechat.util;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import java.util.Properties;

/**
 * �ʼ�������
 */
public class MailUtil {
	private int code;					//�û�ע����֤��
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
     * �����ʼ�
     * @param to ��˭��
     * @param text ��������
     */
    public void send_mail(String to) throws MessagingException {       

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
		 message.setRecipients(RecipientType.TO,new InternetAddress[] { new InternetAddress(to) });       
		
		 //�����ʼ�����        
		
		 message.setSubject("��΢��ע��");        
			
			//�����ʼ�����    
		 String text = this.getCode()+"";
		 message.setText(text);       
			
			 //�õ��ʲ����        
			
		 Transport transport = session.getTransport();        
			
			//�����Լ��������˻�        
			
		 transport.connect("958911392@qq.com","aqubnwdrcqdvbfjj");//����Ϊ�ղŵõ�����Ȩ��        
			
			//�����ʼ�        
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