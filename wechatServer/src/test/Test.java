package test;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

/**
 * ������
 */
public class Test {
    public static void main(String[] args) {
        try {
//        	int code = (int)(Math.random() * 9999);
//        	System.out.println(code);
//            MailUtil.send_mail1("1376261775@qq.com", String.valueOf(code));
//            System.out.println("�ʼ����ͳɹ�!");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
            System.out.println(df.format(new Date()));
            File file = new File("HelloWorld.java");
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println(suffix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
