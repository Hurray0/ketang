// Project: Pingpong Here
// Part: Server
// File: Thread_Serch_Friend
// Note: 查找好友的线程
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

public class Thread_Serch_Friend extends Thread 
{
	private JsonClass receivedjson;
	public JsonClass sendjson;
	public JsonClass sendjson2;
	private Socket socket;
	public HashMap<String,Socket> user_map;
	public Socket socket_him;

	public Thread_Serch_Friend(HashMap<String,Socket> user_map,Socket socket,JsonClass receivedjson,JsonClass sendjson)
	{
		this.user_map = user_map;
		this.socket = socket;
		this.receivedjson = receivedjson;
		this.sendjson = sendjson;
	}

	public void run()
	{
		
		try { 
			sendjson = new JsonClass(receivedjson.getJsonStr());
            // 加载驱动程序
			Class.forName(R.db_driver);
           	// 连续数据库
			Connection conn = DriverManager.getConnection(R.db_url, R.db_user, R.db_password);
			if(!conn.isClosed()) 
				System.out.println("【Run】数据库连接成功");
			else
				System.out.println("【Error】数据库连接错误");
            // statement用来执行SQL语句
			Statement statement = conn.createStatement();
			String sql = "select password from `user` where username = '" + receivedjson.getFriendName() + "';";
			ResultSet rs = statement.executeQuery(sql);
			String code = null;
			while(rs.next()) {
				code = rs.getString("password");
			}
			if(code==null || code=="")
				throw new Exception("找不到好友");
			try 
			{
				socket_him = user_map.get(receivedjson.getFriendName());
				if(!socket_him.isClosed())
				{

					sendjson.setStatus(R.STATU_FRIEND_FOUND);
					sendjson.setPassword(code);
				}
				else
				{
					throw new Exception("用户不在线");
				}
			}catch(Exception e_notOnline)
			{
				sendjson.setStatus(R.STATU_FRIEND_NOT_ONLINE);
				System.err.println("【Run】Exception:查找的好友不在线");
			}


			
			conn.close();
		} catch(ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!"); 
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
			try{
				sendjson.setStatus(R.STATU_FRIEND_NOT_FOUND);
				System.err.println("【Run】Exception:找不到好友");
			} catch(Exception ee)
			{
				ee.printStackTrace();
			}
		} 
		new output(socket,sendjson);//发送给用户
	}
}