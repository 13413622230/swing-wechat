package com.wechat.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MyServer {
	static HashMap<String,ServerThread> th =new HashMap<String, ServerThread>();
	public static void main(String[] args) {
		new MyServer();
	}
	public MyServer(){
		try {
			//1.����һ���������˵�socket
			ServerSocket serverSocket = new ServerSocket(8888);
			Socket socket = null;
			System.out.println("���Ƿ�����������8888�˿ڼ���");
			//ѭ�������ȴ��ͻ��˵�����
			while(true){
				//2.����accept()������ʼ�������ȴ��ͻ��˵�����
				socket = serverSocket.accept();
				System.out.println("�пͻ���������");
				//����һ���µ��߳�
				ServerThread serverThread = new ServerThread(socket);
				//�����߳�
				serverThread.start();
				System.out.println("Ŀǰ�������ǣ�"+th.size());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
