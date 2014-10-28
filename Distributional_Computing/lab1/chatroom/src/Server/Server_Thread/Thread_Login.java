// Project: Pingpong Here
// Part: Server
// File: Thread_Login
// Note: 服务器的登陆线程
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

public class Thread_Login extends Thread {
	private JsonClass receivedjson;
	public JsonClass sendjson;
	private Socket socket;
	public HashMap<String,Socket> user_map;

	public Thread_Login (HashMap<String,Socket> user_map,Socket socket,JsonClass receivedjson,JsonClass sendjson)
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
			String sql = "select password from `user` where username = '" + receivedjson.getUsername() + "';";
			ResultSet rs = statement.executeQuery(sql);
			String code = null;
			while(rs.next()) {
				code = rs.getString("password");
			}


			sendjson.setType(R.CMD_LOGIN);
			if(code.equals(receivedjson.getPassword()))
			{
				try{
					Socket temp = user_map.get(receivedjson.getUsername());
					if(temp == null)
						throw new Exception("没有登录冲突") ;
					sendjson.setStatus(R.STATU_LOGIN_FAILED);
					sendjson.setNote(R.ERR_ALREADY_LOGIN);
				}
				catch(Exception LandedOK)
				{
					sendjson.setStatus(R.STATU_LOGIN_SUCCESS);
					user_map.put(receivedjson.getUsername(),socket);
					System.out.println("【Run】插入user_map:"+ receivedjson.getUsername() +"->"+socket);
					System.out.println("【统计】一个人登录成功！");
					System.out.println("【统计】现在共有"+ user_map.size() +"人登陆在线");
				}
			}
			else
			{
				sendjson.setStatus(R.STATU_LOGIN_FAILED);
				sendjson.setNote(R.ERR_LOGIN_WRONGWD);
			}

			conn.close();
		} catch(ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!"); 
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
			try{
				sendjson.setType(R.CMD_LOGIN);
				sendjson.setStatus(R.STATU_LOGIN_FAILED);
				sendjson.setNote(R.ERR_LOGIN_NOUSER);
			} catch(Exception ee)
			{
				ee.printStackTrace();
			}
		} 
		new output(socket,sendjson);//发送给用户
	}

	public JsonClass return_sendjson()
	{
		return sendjson;
	}
}