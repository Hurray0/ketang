// Project: ChatRoom
// Part: Server
// File: Thread_Group_Chat
// Note: 服务器的群聊线程
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

public class Thread_Group_Chat extends Thread {
	private JsonClass receivedjson;
	public JsonClass sendjson;
	private Socket socket;
	public JsonClass sendjson2;
	public HashMap<String,Socket> user_map;
	public Socket socket_him;

	public Thread_Group_Chat (HashMap<String,Socket> user_map,Socket socket,JsonClass receivedjson,JsonClass sendjson)
	{
		this.user_map = user_map;
		this.socket = socket;
		this.receivedjson = receivedjson;
		this.sendjson = sendjson;
		sendjson2 = new JsonClass();
	}

	public void run()
	{
		Iterator<Socket> it = user_map.values().iterator();//得到一个键的集合的迭代器
		while(it.hasNext())
		{
			try 
			{
				socket_him = user_map.get(it.next());
				if(!socket_him.isClosed())
				{
					sendjson2 = new JsonClass();
					sendjson2.setType(R.CMD_SINGLE_CHAT);
					sendjson2.setStatus(R.STATU_S_CHAT_GET);
					sendjson2.setFriendName(receivedjson.getUsername());
					new output(socket_him, sendjson2);//发送给接收者
				}
				else
				{
					throw new Exception("用户不在线");
				}
			}
			catch(Exception e_notOnline)
			{
				try
				{
					sendjson.setStatus(R.STATU_FRIEND_NOT_ONLINE);
				}catch(Exception e){}
				System.err.println("【Run】Exception:查找的好友不在线");
			}
		}

		try 
		{
			sendjson.setType(R.CMD_SINGLE_CHAT);
			sendjson.setStatus(R.STATU_S_CHAT_SUCCESS);
		}
		catch(Exception e_notOnline)
		{
			try
			{
				sendjson.setStatus(R.STATU_UNKNOW);
			}catch(Exception e){}
			System.err.println("【Error】未知错误");
		}
		new output(socket, sendjson);//提示发送者
	}

}