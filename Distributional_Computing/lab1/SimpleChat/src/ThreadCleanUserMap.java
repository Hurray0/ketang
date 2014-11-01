// Project: SimpleChat
// Part: Server
// File: ServerMainThread
// Note: 服务器的用户表清理线程
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
//GUI
import java.awt.*;
import javax.swing.*;

public class ThreadCleanUserMap extends Thread {
	// GUI
	private JTextArea jta;
	// HashMap
	private HashMap<String, Socket> usernameMap;

	public ThreadCleanUserMap(HashMap<String, Socket> usernameMap, JTextArea jta)
	{
		this.usernameMap = usernameMap;
		this.jta = jta;
	}

	public void run()
	{
		jta.append("进入清理usernameMap的线程！\n");
		while(true)
		{
			//每隔一段清理一次，防止过多占用资源
			try { Thread.sleep ( R.TIME_CLEAN_MILLIS ) ; 
			} catch (InterruptedException ie){}

			Iterator<Socket> it = usernameMap.values().iterator();//得到一个键的集合的迭代器
			while(it.hasNext())
			{
				Socket thissocket = it.next();
				if(thissocket.isClosed())//如果有人断开通信则清理
				{
					jta.append("【清除fight_map】" + it + "\n");
					it.remove();//删除Map中所包含值的Collection中的元素还将删除Map中相应的映射
					jta.append("【统计】一名用户离线\n");
					jta.append("【统计】现在共有"+ usernameMap.size() +"个用户在线\n");
				}
			}
		}
	}
}
