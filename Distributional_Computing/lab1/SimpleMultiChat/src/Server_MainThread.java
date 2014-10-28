// Project: SimpleMultiChat
// Part: Server
// File: Server_MainThread
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

public class Server_MainThread extends Thread 
{
		// Socket
	private Socket socket; 
	public Socket socket_him;

		// 服务器用户Hash列表
	public HashMap<Integer,Socket> user_map;

		// 构造方法
	public Server_MainThread(Socket socket, HashMap<Integer,Socket>  user_map) 
	{
		this.socket = socket;
		this.user_map = user_map;
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
				System.out.println("收到："+ s);
				// String outputMsg = "No." + user_map.get(socket) + "：" + s;
				Iterator<Socket> it = user_map.values().iterator();//得到一个键的集合的迭代器
				while(it.hasNext())
				{
					try 
					{
						socket_him = it.next();
						DataOutputStream outputToClient = new DataOutputStream(socket_him.getOutputStream());
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