// Project: SimpleMultiChat
// Part: Server
// File: Server
// Note: 服务器主程序
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
//线程池包
import java.util.concurrent.*;

public class Server
{
	// Text area for displaying contents
	private static Lock lock = new ReentrantLock();
	private HashMap<Integer,Socket>  user_map = new HashMap<Integer,Socket> ();
	// 线程池
	ExecutorService executor = Executors.newCachedThreadPool();
	
	public static void main(String[] args)
	{
		new Server();
	}

	public Server() 
	{
		try 
		{
			// Create a server socket
			ServerSocket serverSocket = new ServerSocket(R.PORT);
			System.out.println("欢迎进入服务器！");

			// Number a client
			int clientNo = 1;

			// // 开始一个清理user_map的线程
			// Thread_Clean_UserMap thread_clean_usermap = new Thread_Clean_UserMap(user_map);
			// executor.execute(thread_clean_usermap);

			while (true)
			{
				// Listen for a new connection request
				Socket socket = serverSocket.accept();

				System.out.println(socket);

				// Display the client number
				System.out.println("【统计】第" + clientNo + "个连接用户，时间:"
						+ Calendar.getInstance().getTime()+
						"，HOST:" + socket.getInetAddress().getHostName() +
						"，IP:" + socket.getInetAddress().getHostAddress());
				user_map.put(clientNo, socket);
				System.out.println("【统计】现在共有"+ user_map.size() +"人登陆在线");

				// Create a new thread for the connection
				Server_MainThread thread = new Server_MainThread(socket, user_map);
				executor.execute(thread);

				// Increment clientNo
				clientNo++;
			}
		} 
		catch (IOException ex) 
		{
			System.err.println(ex);
		}
	}
	
}
