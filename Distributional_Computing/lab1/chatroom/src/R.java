// Project: ChatRoom
// Part: Server
// File: R
// Note: Resource File
// Author: Hurray Zhu
// Time: 2014.10.28
// E-mail: i@ihurray.com
// Web-site: http://blog.ihurray.com
// GitHub: https://github.com/Hurray0/ketang/tree/master/Distributional_Computing/lab1


public class R {
	//常量
	public static final int Server_Port = 8000;//服务器端口
	public static final String db_driver = "com.mysql.jdbc.Driver";// 驱动程序名
	public static final String db_url = "jdbc:mysql://127.0.0.1:3306/PPH?useUnicode=true&characterEncoding=UTF-8";//URL指向要访问的数据库名scutcs,后面一长串可以保证插入中文成功       
	public static final String db_user = "BadongAdmin"; // MySQL配置时的用户名         
    public static final String db_password = "admin";// MySQL配置时的密码
    public static final int num_executor = 10; //服务器线程池线程最大数
    public static final int TIME_CLEAN_MILLIS = 1000; //清理user_map的时间周期（单位毫秒）

    //serviceHandler相关命令
	public static final int CMD_LOGIN = 9201;//登录
	public static final int CMD_REGIST = 9207;//注册用户
	public static final int CMD_SEARCH_FRIEND = 9205;//发送好友匹配的消息
	public static final int CMD_LOGOUT = 9208;//注销
	public static final int CMD_SINGLE_CHAT = 9202;//私聊
	public static final int CMD_GROUP_CHAT = 9203;//群聊


	//相关状态声明
	public static final int STATU_LOGIN_SUCCESS = 101;//登陆成功
	public static final int STATU_LOGIN_FAILED = 102;//登陆失败

	public static final int STATU_S_CHAT_SUCCESS = 201;//发送私聊成功
	public static final int STATU_S_CHAT_FAILED = 202;//发送私聊失败
	public static final int STATU_S_CHAT_GET = 203;//接收私聊

	public static final int STATU_G_CHAT_SUCCESS = 301;//发送群聊成功
	public static final int STATU_G_CHAT_FAILED = 302;//发送群聊失败
	public static final int STATU_G_CHAT_GET = 303;//接收群聊
		
	public static final int STATU_REG_SUCCESS = 701;//注册成功
	public static final int STATU_REG_FAILED = 702;//注册失败

	public static final int STATU_LOGOUT_SUCCESS = 801;//注销成功
	public static final int STATU_LOGOUT_FAILED = 802;//注销失败
		
	public static final int STATU_FRIEND_NOT_FOUND = 501;//找不到好友
	public static final int STATU_FRIEND_FOUND = 502;//找到好友
	public static final int STATU_FRIEND_NOT_ONLINE = 503;//找到好友，好友不在线
		
	public static final int STATU_UNKNOW = 901;//未知状态
	public static final int STATU_SERVER_WRONG = 902;//服务器出错
	public static final int STATU_UNKNOW_ERROR = 903;//未知错误
	public static final int STATU_ERROR_FORMAT = 904;//发送的格式错误
		
	public static final int STATU_RUNTIME_ERROR = 999;//访问超时
	public static final int STATU_CONNET_FAILED = 998;//网络或服务器连接失败

	//错误提示信息
	public static final String ERR_LOGIN_NOUSER = "不存在该用户名！";
	public static final String ERR_LOGIN_WRONGWD = "密码错误！";
	public static final String ERR_UNKNOW_TYPE = "未知的Json-type";
	public static final String ERR_ALREADY_LOGIN = "已经登录过了，请先在另一边注销";

}