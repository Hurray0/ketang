// Project: ChatRoom
// Part: Server
// File: Server_MainThread
// Note: 服务器主程序
// Author: Hurray Zhu
// Time: 2014.10.28
// E-mail: i@ihurray.com
// Web-site: http://blog.ihurray.com


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

public class Server{
	// Text area for displaying contents
	private static Lock lock = new ReentrantLock();
	// private ArrayList<userBean> userbeans = new ArrayList<userBean>(){};
	private HashMap<String,Socket> user_map = new HashMap<String,Socket>();
	public HashMap<Integer,Fight_UserInfo_Bean> fight_map = new HashMap<Integer,Fight_UserInfo_Bean>();
	// 线程池
	ExecutorService executor = Executors.newCachedThreadPool();


	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		

		try {
			// Create a server socket
			ServerSocket serverSocket = new ServerSocket(R.Server_Port);
			System.out.println("MultiThreadServer started at " + Calendar.getInstance().getTime());

			// Number a client
			int clientNo = 1;

			// 开始一个清理user_map的线程
			Thread_Clean_UserMap thread_clean_usermap = new Thread_Clean_UserMap(user_map);
			executor.execute(thread_clean_usermap);
			//开启一个清理fight_map的线程
			Thread_Clean_FightMap thread_clean_fightmap = new Thread_Clean_FightMap(fight_map);
			executor.execute(thread_clean_fightmap);

			while (true) {
				// Listen for a new connection request
				Socket socket = serverSocket.accept();

				System.out.println(socket);

				// Display the client number
				System.out.println("【统计】第" + clientNo + "个连接用户，时间:"
						+ Calendar.getInstance().getTime()+
						"，HOST:" + socket.getInetAddress().getHostName() +
						"，IP:" + socket.getInetAddress().getHostAddress());
				System.out.println("【统计】现在共有"+ user_map.size() +"人登陆在线");

				// Create a new thread for the connection
				Server_MainThread thread = new Server_MainThread(socket,user_map,fight_map);
				executor.execute(thread);

				// Increment clientNo
				clientNo++;
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
}
