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
import java.awt.*;
import javax.swing.*;

//Hash队列包
import java.util.HashMap;
//线程池包
import java.util.concurrent.*;

public class Server extends JFrame
{
	// GUI
	private JTextArea jta = new JTextArea();
	// Text area for displaying contents
	private static Lock lock = new ReentrantLock();
	private HashMap<Integer,Socket>  usermap = new HashMap<Integer,Socket> ();
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
			//GUI
			setLayout(new BorderLayout());
			add(new JScrollPane(jta), BorderLayout.CENTER);
			setTitle("Server");
			setSize(500, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);

			// Create a server socket
			ServerSocket serverSocket = new ServerSocket(R.PORT);
			jta.append("欢迎进入服务器！\n");

			// Number a client
			int clientNo = 1;

			// // 开始一个清理usermap的线程（由于本实验要求较低，就忽略了这个功能，实际使用应该清理usermap）
			// ThreadCleanUserMap threadCleanUsermap = new ThreadCleanUserMap(usermap);
			// executor.execute(threadCleanUsermap);

			while (true)
			{
				// Listen for a new connection request
				Socket socket = serverSocket.accept();

				System.out.println(socket);

				// Display the client number
				jta.append("【统计】第" + clientNo + "个连接用户，时间:"
						+ Calendar.getInstance().getTime()+
						"，HOST:" + socket.getInetAddress().getHostName() +
						"，IP:" + socket.getInetAddress().getHostAddress()+ "\n") ;
				usermap.put(clientNo, socket);
				jta.append("【统计】现在共有"+ usermap.size() +"人登陆在线\n");

				// Create a new thread for the connection
				ServerMainThread thread = new ServerMainThread(socket, usermap, jta);
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
