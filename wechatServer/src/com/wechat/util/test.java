package com.wechat.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

public class test {
	public static void main(String[] args) throws MessagingException, IOException {
		MailUtil ma = new MailUtil();
		System.out.println(ma.getCode());
////		ma.send_mail("13413622230@163.com");
//		ma.send_mail("1376261775@qq.com");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String path = "D:/wechat/"+"test10"+"/"+
				"123";
		File f = new File(path);
		System.out.println(f);
		System.out.println(f.mkdirs());
		String path1 = path+"/"+df.format(new Date())+".txt";
		File f1 = new File(path1);
		System.out.println(f1);
		System.out.println(f1.createNewFile());
		File[] tempList = f.listFiles();
		System.out.println("文件夹的文件数量为："+tempList.length);
		for (int i = 0; i < tempList.length; i++) {
			   if (tempList[i].isFile()) {
			    System.out.println("文     件："+tempList[i].getPath());
			   }
		}
	}
}
