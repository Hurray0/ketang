// Project: ChatRoom
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
//json包
import org.json.*;
//mysql包
import java.sql.*;
//Hash队列包
import java.util.HashMap;
import java.util.Iterator;
//线程池包
import java.util.concurrent.*;

public class Server_MainThread extends Thread {
		// Socket
		private Socket socket; // A connected socket
		// 数据库变量
		private String driver = R.db_driver;
		private String url = R.db_url;// URL指向要访问的数据库名scutcs,后面一长串可以保证插入中文成功
		private String user = R.db_user; // MySQL配置时的用户名
		private String password = R.db_password;// MySQL配置时的密码
		// 服务器用户Hash列表
		public HashMap<String,Socket> user_map;
		public HashMap<Integer,Fight_UserInfo_Bean> fight_map;

		// 构造方法
		public Server_MainThread(Socket socket,HashMap<String,Socket> user_map,HashMap<Integer,Fight_UserInfo_Bean> fight_map) {
			this.socket = socket;
			this.user_map = user_map;
			this.fight_map = fight_map;
		}

		// 线程
		public void run() {
			try {
				// 线程池
				ExecutorService executor = Executors.newCachedThreadPool();

				// I/O
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());

				// Json数据处理
				JsonClass receivedjson = new JsonClass();
				JsonClass sendjson = new JsonClass();

				// 线程主程序开始
				while (true) {
					sendjson = new JsonClass();//必须刷新，否则旧数据还在
					String s = inputFromClient.readUTF();

					int type=-999;
					int status;

					try
					{
						receivedjson = new JsonClass(s);
						type = receivedjson.getType();
						status = receivedjson.getStatus();
						System.out.println("【收到数据包】"+receivedjson.getJsonStr());
					}
					catch(Exception e)
					{
						//int type=-999;
					}

					//注册
					if(type == R.CMD_REGIST)
					{
						Thread_Regist thread_regist = new Thread_Regist(socket,receivedjson,sendjson);
						executor.execute(thread_regist);//thread start
						
					}

					//登陆
					else if(type == R.CMD_LOGIN)
					{
						Thread_Login thread_login = new Thread_Login(user_map,socket,receivedjson,sendjson);
						executor.execute(thread_login);
						while(true)
						{
							sendjson = thread_login.return_sendjson();
							try
							{
								if(sendjson.getStatus()==R.STATU_LOGIN_SUCCESS)
								{
									System.out.println("【OK】进入登录逻辑");
									//进入登陆逻辑
									while(true)
									{
										sendjson = new JsonClass();//必须刷新，否则旧数据还在
										s = inputFromClient.readUTF();

										type=-999;
										status = -999;

										try
										{
											receivedjson = new JsonClass(s);
											type = receivedjson.getType();
											status = receivedjson.getStatus();
											System.out.println("【收到数据包】"+receivedjson.getJsonStr());
										}catch(Exception e){}

										//查找好友
										if(type == R.CMD_SEARCH_FRIEND)
										{
											Thread_Serch_Friend thread_reply_fight_fiend = new Thread_Serch_Friend(user_map,socket,receivedjson,sendjson);
											executor.execute(thread_reply_fight_fiend);
											continue;
										}

										//私聊
										else if(type == R.CMD_SINGLE_CHAT)
										{
											Thread_Single_Chat thread_single_chat = new Thread_Single_Chat(user_map,socket,receivedjson,sendjson);
											executor.execute(thread_single_chat);
											continue;
										}

										//群聊
										else if(type == R.CMD_GROUP_CHAT)
										{
											Thread_Group_Chat thread_group_chat = new Thread_Group_Chat(user_map,socket,receivedjson,sendjson);
											executor.execute(thread_group_chat);
											continue;
										}
										
										//注销登陆状态
										else if(type == R.CMD_LOGOUT)
										{
											Thread_Logout thread_logout = new Thread_Logout (socket,receivedjson,sendjson,user_map);
											executor.execute(thread_logout);
											if(true)
												break;
										}
										else
										{
											try 
											{
												sendjson.setType(R.STATU_UNKNOW);
												sendjson.setNote(R.ERR_UNKNOW_TYPE);
											} 
											catch(Exception e) {}
											new output(socket,sendjson);//发送给用户
										}

									}
									break;
								}
								else if(sendjson.getStatus()==R.STATU_LOGIN_FAILED)
								{
									break;
								}
								else
								{
									continue;
								}
							}
							catch (IOException e) {
							// System.err.println(e);
								try {
									socket.close();
									if(socket.isClosed())
										System.out.println("【OK】成功清理socket");
									break;
								} catch(Exception ex)
								{
									System.err.println("【Error】未能结束socket");
								}
								System.out.println("【统计】现在共有"+ user_map.size() +"人登陆在线");
							}
							catch(Exception exa)
							{}//org.json.JSONException: JSONObject["status"] not found.

						}
					}
						

					
					else
					{
						try {
							sendjson.setType(R.STATU_UNKNOW);
							sendjson.setNote(R.ERR_UNKNOW_TYPE);
						} catch(Exception e) {}
						new output(socket,sendjson);//发送给用户
					}
					
				}
			} catch (IOException e) {
				// System.err.println(e);
				try {
					socket.close();
					if(socket.isClosed())
						System.out.println("【OK】成功清理socket");
				} catch(Exception ex)
				{
					System.err.println("【Error】未能结束socket");
				}
				System.out.println("【统计】现在共有"+ user_map.size() +"人登陆在线");
			}
		}
	}