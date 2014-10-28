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
	private Socket socket;
	public HashMap<String,Socket> user_map;

	public Thread_Single_Chat (HashMap<String,Socket> user_map,Socket socket,JsonClass receivedjson,JsonClass sendjson)
	{
		this.user_map = user_map;
		this.socket = socket;
		this.receivedjson = receivedjson;
		this.sendjson = sendjson;
	}

	public void run()
	{
		
	}

	public JsonClass return_sendjson()
	{
		return sendjson;
	}
}