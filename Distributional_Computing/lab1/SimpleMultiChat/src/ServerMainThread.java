// Project: SimpleMultiChat
// Part: Server
// File: ServerMainThread
// Note: 服务器的通信主线程
// Author: Hurray Zhu
// Time: 2014.10.28
// E-mail: i@ihurray.com
// Web-site: http://blog.ihurray.com
// GitHub: https://github.com/Hurray0/ketang/tree/master/Distributional_Computing/lab1

//通信包
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//Hash队列包
import java.util.HashMap;
import java.util.Iterator;
//线程池包
import java.util.concurrent.*;
//GUI
import java.awt.*;
import javax.swing.*;


public class ServerMainThread extends Thread 
{
	// Socket
	private Socket socket; 
	public Socket sockethim;
	public JTextArea jta;

	// 服务器用户Hash列表
	public HashMap<Integer,Socket> usermap;

	// 构造方法
	public ServerMainThread(Socket socket, HashMap<Integer,Socket>  usermap, JTextArea jta) 
	{
		this.socket = socket;
		this.usermap = usermap;
		this.jta = jta;
	}

	// 线程
	public void run() 
	{
		try
		{
			DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
			while(true)
			{
				String s = inputFromClient.readUTF();
				jta.append("收到："+ s + "\n");
				// String outputMsg = "No." + usermap.get(socket) + "：" + s;
				Iterator<Socket> it = usermap.values().iterator();//得到一个键的集合的迭代器
				while(it.hasNext())
				{
					try 
					{
						sockethim = it.next();
						DataOutputStream outputToClient = new DataOutputStream(sockethim.getOutputStream());
						outputToClient.writeUTF(s);
					}
					catch(Exception ee){}
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
}