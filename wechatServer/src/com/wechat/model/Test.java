package com.wechat.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		File f = new File("D:/test4.txt");
		FileOutputStream out = new FileOutputStream(f,true);
		ObjectOutputStream objOut = null;
		f.createNewFile();
//		ObjectInputStream ois = new ObjectInputStream(f);
		objOut = new ObjectOutputStream(out);
		if(f.length()<1){
			objOut = new ObjectOutputStream(out);
		}
		else{
//			objOut = new MyObjectOutputStream(out);
		}
//		FileMessage ff = new FileMessage(null, null, new Date(), "test1");
//		PictureMessage p = new PictureMessage(null, null, new Date(), "test2");
		ArrayList<Message> ms = new ArrayList<>();
//		ms.add(ff);
//		ms.add(p);
		objOut.writeObject(ms);
//		objOut.flush();
//		objOut.writeObject("类型");
//		objOut.flush();
//		objOut.writeObject(new User("test1", "test1", "test1", "test1", "test1"));
//		objOut.flush();
//		objOut.writeObject("类型");
//		objOut.flush();
		objOut.close();
		FileInputStream in = new FileInputStream(f);
		@SuppressWarnings("resource")
		ObjectInputStream objIn = new ObjectInputStream(in);
		ArrayList<Message> ms1 = (ArrayList<Message>) objIn.readObject();
		System.out.println(((FileMessage)ms1.get(0)).getPath());
		System.out.println(ms1.size());
//		User user = (User)objIn.readObject();
//		System.out.println(user.getId()+objIn.readObject());
//		User user1 = (User)objIn.readObject();
//		System.out.println(user1.getId()+objIn.readObject());
//		User user2 = (User)objIn.readObject();
//		System.out.println(user2.getId()+objIn.readObject());
//		User user3 = (User)objIn.readObject();
//		System.out.println(user3.getId()+objIn.readObject());
	}

}
