// Project: ChatRoom
// Part: Server
// File: Thread_Regist
// Note: 服务器的注册线程
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

public class Thread_Regist extends Thread{
	private JsonClass receivedjson;
	public JsonClass sendjson;
	private Socket socket;
	private int status;

	public Thread_Regist(Socket socket,JsonClass receivedjson,JsonClass sendjson){
		this.socket = socket;
		this.receivedjson = receivedjson;
		this.sendjson = sendjson;
		this.status = 0;
	}
	public void run()
	{
	//以后做解密判断
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
			String sql = "insert into `user` (`username`,`password`,`ip`,`regtime`)"+
			" values ('"+receivedjson.getUsername()+"','"+receivedjson.getPassword()+"','"+socket.getInetAddress().getHostAddress()+"','"+receivedjson.getTime()+"');";
			int rs2 = statement.executeUpdate(sql);
			if(rs2==1)
			{
				System.out.println("【实现】成功注册！");
				status = 1;
			}

			//编写返回的Json包
			
			sendjson.setType(R.CMD_REGIST);
			if(status==1)
				sendjson.setStatus(R.STATU_REG_SUCCESS);
			else
			{
				sendjson.setStatus(R.STATU_REG_FAILED);
				sendjson.setNote("注册失败");//以后再写重复判断什么的逻辑
			}

			conn.close();
		} catch(ClassNotFoundException e) {
			System.out.println("【Error】找不到驱动"); 
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} 

		new output(socket,sendjson);//发送给用户
	}

	public JsonClass getReturnJson()
	{
		return this.sendjson;
	}
}