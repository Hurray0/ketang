// Project: Pingpong Here
// Part: Server
// File: Thread_Clean_FightMap
// Note: 清理fight_map的线程
// Author: Hurray Zhu
// Time: 2014.09.12
// E-mail: i@ihurray.com
// Web-site: http://blog.ihurray.com

//通信包
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//Hash队列包
import java.util.HashMap;
import java.util.Iterator;

public class Thread_Clean_FightMap extends Thread {
	private HashMap<Integer,Fight_UserInfo_Bean> fight_map;
	private Fight_UserInfo_Bean fight_userinfo_bean;

	public Thread_Clean_FightMap(HashMap<Integer,Fight_UserInfo_Bean> fight_map)
	{
		this.fight_map = fight_map;
	}

	public void run()
	{
		System.out.println("进入清理fight_map的线程！");
		while(true)
		{
			//每隔一段清理一次，防止过多占用资源
			try { Thread.sleep ( R.TIME_CLEAN_MILLIS ) ; 
			} catch (InterruptedException ie){}

			Iterator<Fight_UserInfo_Bean> it = fight_map.values().iterator();//得到一个键的集合的迭代器
			while(it.hasNext())
			{
				fight_userinfo_bean = it.next();
				if(fight_userinfo_bean.getSocket_user1().isClosed() || fight_userinfo_bean.getSocket_user2().isClosed())//如果有人断开通信则清理
				{
					System.out.println("【清除fight_map】" + it);
					it.remove();//删除Map中所包含值的Collection中的元素还将删除Map中相应的映射
					System.out.println("【统计】清除一个fight_map中元素");
					System.out.println("【统计】现在共有"+ fight_map.size() +"个元素");
				}
			}
		}
	}
}
