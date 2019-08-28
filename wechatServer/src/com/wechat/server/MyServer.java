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
			//1.创建一个服务器端的socket
			ServerSocket serverSocket = new ServerSocket(8888);
			Socket socket = null;
			System.out.println("我是服务器，我在8888端口监听");
			//循环监听等待客户端的连接
			while(true){
				//2.调用accept()方法开始监听，等待客户端的连接
				socket = serverSocket.accept();
				System.out.println("有客户端连接啦");
				//创建一个新的线程
				ServerThread serverThread = new ServerThread(socket);
				//启动线程
				serverThread.start();
				System.out.println("目前的数量是："+th.size());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
