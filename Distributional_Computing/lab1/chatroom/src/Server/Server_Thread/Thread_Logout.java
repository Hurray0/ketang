// Project: Pingpong Here
// Part: Server
// File: Thread_Logout
// Note: 服务器的注销线程
// Author: Hurray Zhu
// Time: 2014.09.09
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
//mysql包
import java.sql.*;
//Hash队列包
import java.util.HashMap;

public class Thread_Logout extends Thread {
	private JsonClass receivedjson;
	public JsonClass sendjson;
	private Socket socket;
	public HashMap<String,Socket> user_map;

	public Thread_Logout (Socket socket,JsonClass receivedjson,JsonClass sendjson,HashMap<String,Socket> user_map)
	{
		this.socket = socket;
		this.receivedjson = receivedjson;
		this.sendjson = sendjson;
		this.user_map = user_map;
	}

	public void run()
	{
		
		try 
		{ 
			sendjson = new JsonClass(receivedjson.getJsonStr());
			sendjson.setType(R.CMD_LOGOUT);
			sendjson.setStatus(R.STATU_LOGOUT_SUCCESS);
			user_map.remove(receivedjson.getUsername());
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			try{
				sendjson.setStatus(R.STATU_LOGOUT_FAILED);
			} catch(Exception ee){}
		}
		new output(socket,sendjson);//发送给用户
		System.out.println("【统计】现在共有"+ user_map.size() +"人登陆在线");
	}
}