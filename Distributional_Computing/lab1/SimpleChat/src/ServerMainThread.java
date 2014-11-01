// Project: SimpleChat
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
	private Socket sockethim;
	private JTextArea jta;
	private DataOutputStream outputToClient;
	private DataInputStream inputFromClient;
	private DataOutputStream outputToMe;
	private String username;
	private String othersname;

	// 服务器用户Hash列表
	private HashMap<String, Socket> usernameMap;

	// 构造方法
	public ServerMainThread(Socket socket, HashMap<String, Socket> usernameMap, JTextArea jta) 
	{
		this.socket = socket;
		this.jta = jta;
		this.usernameMap = usernameMap;
	}

	// 线程
	public void run() 
	{
		try
		{
			inputFromClient = new DataInputStream(socket.getInputStream());
			outputToMe = new DataOutputStream(socket.getOutputStream());
			while(true)
			{
				outputToMe.writeUTF("请先输入您的用户名\n");
				username = inputFromClient.readUTF();
				if(usernameMap.get(username) != null)
				{
					while(true)
					{
						outputToMe.writeUTF("用户名重复了！请换一个再试\n");
						username = inputFromClient.readUTF();
						if(usernameMap.get(username) == null)
							break;
					}
				}
				usernameMap.put(username, socket);
				outputToMe.writeUTF(username + "登录成功！\n");
				jta.append(username + "登录成功！\n");

				while(true)
				{
					outputToMe.writeUTF(R.MENU);

					String inputNum = inputFromClient.readUTF();
				//群聊
					if(inputNum.equals("1"))
					{
						outputToMe.writeUTF("成功进入群聊！回复发送群聊消息，输入exit退出群聊！\n");
						while(true)
						{
							String s = inputFromClient.readUTF();

							jta.append(username + "："+ s + "\n");
							if(s.equals("exit"))
								break;

							Iterator<Socket> it = usernameMap.values().iterator();//得到一个键的集合的迭代器
							while(it.hasNext())
							{
								try 
								{
									sockethim = it.next();
									outputToClient = new DataOutputStream(sockethim.getOutputStream());
									outputToClient.writeUTF("【(群聊)" +  username + "】" + s + "\n");
								}
								catch(Exception ee){}
							}
						}
					}
					//私聊
					else if(inputNum.equals("2"))
					{
						while(true)
						{
							Iterator<String> it = usernameMap.keySet().iterator();//得到一个键的集合的迭代器
							String onLineUsername = "在线用户：";
							while(it.hasNext())
							{
								try 
								{
									onLineUsername = onLineUsername + (String)it.next() + "; ";
								}
								catch(Exception ee){}
							}
							outputToMe.writeUTF(onLineUsername + "\n请输入对方用户名开始私聊\n");

							othersname = inputFromClient.readUTF();
							if(usernameMap.get(othersname) != null)
							{
								break;
							}
							else
							{
								outputToMe.writeUTF("输入有误！请重试\n");
							}
						}

						outputToMe.writeUTF("成功进入与" + othersname + "私聊，现输入内容即与其私聊；输入exit退出！\n");
						while(true)
						{
							String s = inputFromClient.readUTF();

							jta.append(username + "："+ s + "\n");
							if(s.equals("exit"))
								break;

							try
							{
								outputToClient = new DataOutputStream(usernameMap.get(othersname).getOutputStream());
								outputToClient.writeUTF("【(私聊)"+ username +"】" + s + "\n");
								outputToMe.writeUTF("【(私聊)"+ username +"】" + s + "\n");
							}
							catch(Exception friendErr)
							{
								outputToMe.writeUTF(s + "发送失败\n");
							}
						}

					}
					//退出
					else if(inputNum.equals("3"))
					{
						usernameMap.remove(username);
						outputToMe.writeUTF("成功退出登录\n");
						break;
					}
					else
					{
						outputToMe.writeUTF("输入序号错误！请重试\n");
					}
					
				}
			}
			
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
}