// Project: ChatRoom
// Part: Server
// File: Thread_Single_Chat
// Note: 服务器的私聊线程
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

public class Thread_Single_Chat extends Thread {
	private JsonClass receivedjson;
	public JsonClass sendjson;
	public JsonClass sendjson2;
	private Socket socket;
	public HashMap<String,Socket> user_map;
	public Socket socket_him;

	public Thread_Single_Chat (HashMap<String,Socket> user_map,Socket socket,JsonClass receivedjson,JsonClass sendjson)
	{
		this.user_map = user_map;
		this.socket = socket;
		this.receivedjson = receivedjson;
		this.sendjson = sendjson;
		sendjson2 = new JsonClass();
	}

	public void run()
	{
		try 
		{
			socket_him = user_map.get(receivedjson.getFriendName());
			if(!socket_him.isClosed())
			{
				sendjson.setType(R.CMD_SINGLE_CHAT);
				sendjson.setStatus(R.STATU_S_CHAT_SUCCESS);
				
				sendjson2 = new JsonClass();
				sendjson2.setType(R.CMD_SINGLE_CHAT);
				sendjson2.setStatus(R.STATU_S_CHAT_GET);
				sendjson2.setFriendName(receivedjson.getUsername());
				new output(socket_him, sendjson2);//发送给接收者
				new output(socket, sendjson);//提示发送者
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

}