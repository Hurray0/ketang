// Project: Pingpong Here
// Part: Server
// File: output
// Note: 服务器管理输出的类
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

public class output {
	public output(Socket socket,JsonClass sendjson)
	{
		try {
			DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

			String str_output = null;
			try{
				str_output = sendjson.getJsonStr();
			}
			catch(Exception ex)
			{}
			System.out.println("【发送数据包】" + str_output);
			outputToClient.writeUTF(str_output);
		} catch(Exception e)
		{
			System.err.println(e);
		}
	}
}