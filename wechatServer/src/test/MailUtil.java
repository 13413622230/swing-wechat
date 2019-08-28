package test;

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
		this.code = (int)(Math.random() * 9999);
	}
    /**
     * �����ʼ�
     * @param to ��˭��
     * @param text ��������
     */
    public static void send_mail(String to,String text) throws MessagingException {
        //�������Ӷ��� ���ӵ��ʼ�������
        Properties properties = new Properties();
        //���÷����ʼ��Ļ�������
        //�����ʼ�������
        properties.put("mail.smtp.host", "smtp.qq.com");
        //���Ͷ˿�
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        //���÷����ʼ����˺ź�����
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //���������ֱ��Ƿ����ʼ����˻�������
                return new PasswordAuthentication("958911392@qq.com","tynphigfurwmbbbd");
            }
        });

        //�����ʼ�����
        Message message = new MimeMessage(session);
        //���÷�����
        message.setFrom(new InternetAddress("958911392@qq.com"));
        //�����ռ���
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
        //��������
        message.setSubject("����һ�ݲ����ʼ�");
        //�����ʼ�����  �ڶ����������ʼ����͵�����
        message.setContent(text,"text/html;charset=UTF-8");
        //����һ���ʼ�
        Transport.send(message);
    }
    public static void send_mail1(String to,String text) throws MessagingException {       

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
			
			message.setText(text);       
			
			 //�õ��ʲ����        
			
			Transport transport = session.getTransport();        
			
			//�����Լ��������˻�        
			
			transport.connect("958911392@qq.com","tynphigfurwmbbbd");//����Ϊ�ղŵõ�����Ȩ��        
			
			//�����ʼ�        
			transport.sendMessage(message, message.getAllRecipients());    
			
		}
    
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
    
}