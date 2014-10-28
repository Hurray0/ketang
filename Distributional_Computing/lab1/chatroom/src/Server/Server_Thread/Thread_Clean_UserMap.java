// Project: ChatRoom
// Part: Server
// File: Thread_Clean_UserMap
// Note: 清理user_map的线程
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
//Hash队列包
import java.util.HashMap;
import java.util.Iterator;

public class Thread_Clean_UserMap extends Thread {
	private HashMap<String,Socket> user_map;

	public Thread_Clean_UserMap(HashMap<String,Socket> user_map)
	{
		this.user_map = user_map;
	}

	public void run()
	{
		System.out.println("进入清理user_map的线程！");
		while(true)
		{
			//每隔一段清理一次，防止过多占用资源
			try { Thread.sleep ( R.TIME_CLEAN_MILLIS ) ; 
			} catch (InterruptedException ie){}

			Iterator<Socket> it = user_map.values().iterator();//得到一个键的集合的迭代器
			while(it.hasNext())
			{
				if(it.next().isClosed())//如果重复次数是0，则删除此键
				{
					System.out.println("【清除user_map】" + it);
					it.remove();//删除Map中所包含值的Collection中的元素还将删除Map中相应的映射
					System.out.println("【统计】一个人注销");
					System.out.println("【统计】现在共有"+ user_map.size() +"人登陆在线");
				}
			}
		}
	}
}
