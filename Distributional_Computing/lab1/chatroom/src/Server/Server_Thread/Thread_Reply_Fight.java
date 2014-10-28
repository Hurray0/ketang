// Project: Pingpong Here
// Part: Server
// File: Thread_Reply_Fight
// Note: 申请比赛开始的线程
// Author: Hurray Zhu
// Time: 2014.09.11
// E-mail: i@ihurray.com
// Web-site: http://blog.ihurray.com

//通信包
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//json包
import org.json.*;
//Hash队列包
import java.util.HashMap;

public class Thread_Reply_Fight extends Thread
{
	private JsonClass receivedjson;
	public JsonClass sendjson;
	public JsonClass sendjson2;
	private Socket socket;
	public HashMap<String,Socket> user_map;
	public HashMap<Integer,Fight_UserInfo_Bean> fight_map;
	public Socket socket_him;
	private int onload_status;
	private Fight_UserInfo_Bean fight_userinfo_bean;

	public Thread_Reply_Fight(HashMap<String,Socket> user_map,Socket socket,JsonClass receivedjson,JsonClass sendjson,HashMap<Integer,Fight_UserInfo_Bean> fight_map)
	{
		this.user_map = user_map;
		this.socket = socket;
		this.receivedjson = receivedjson;
		this.sendjson = sendjson;
		this.onload_status = 0;
		this.fight_map = fight_map;
	}

	public void run()
	{
		System.out.println("进入Thread_Reply_Fight");
		
		try 
		{
			int i = 0;
			sendjson = new JsonClass(receivedjson.getJsonStr());
			sendjson2 = new JsonClass(receivedjson.getJsonStr());
			sendjson2.setUsername(receivedjson.getFriendName());
			sendjson2.setFriendName(receivedjson.getUsername());
			socket_him = user_map.get(receivedjson.getFriendName());
			if(!socket_him.isClosed())
			{
				fight_userinfo_bean = new Fight_UserInfo_Bean(socket,socket_him);
				Fight_UserInfo_Bean.fightnumber++;
				fight_map.put(Fight_UserInfo_Bean.fightnumber,fight_userinfo_bean);//在对战表中插入本次对战信息
				sendjson2.setFightid(Fight_UserInfo_Bean.fightnumber);
				new output(socket_him,sendjson2);//发送给被动者对战请求
			}
			else
			{
				throw new Exception("用户不在线");
			}

			
		}
		catch(Exception e_notOnline)
		{
			System.err.println(e_notOnline);
			try
			{
				sendjson.setStatus(R.STATU_FRIEND_NOT_ONLINE);
			}
			catch(Exception eee){}
			System.err.println("【Run】好友已经离线");
			new output(socket,sendjson);//发送给用户
		}


			
		
		
	}

	public int getOnloadStatus()
	{
		return onload_status;
	}
}