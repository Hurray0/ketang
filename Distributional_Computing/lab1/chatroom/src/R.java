// Project: Pingpong Here
// Part: Server
// File: R
// Note: Resource File
// Author: Hurray Zhu
// Time: 2014.09.09
// E-mail: i@ihurray.com
// Web-site: http://blog.ihurray.com


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
	public static final int CMD_SEND_HITBALL = 9202;//发送击球信息到服务器
	public static final int CMD_RECIVE_BALL = 9203;//从服务器获取来球信息
	public static final int CMD_GET_NOWSCORE = 9204;//从服务器获取当前比分
	public static final int CMD_SEARCH_FRIEND = 9205;//发送好友匹配的消息
	public static final int CMD_GET_RANK = 9206;//获取网络排名
	public static final int CMD_REGIST = 9207;//注册用户
	public static final int CMD_LOGOUT = 9208;//注销
	public static final int CMD_SERVICE_INFO = 9209;//服务器消息
	public static final int CMD_APPLY_FIGHT = 9210;//比赛


	//相关状态声明
	public static final int STATU_LOGIN_SUCCESS = 101;//登陆成功
	public static final int STATU_LOGIN_FAILED = 102;//登陆失败
		
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
		
	public static final int STATU_U1_APPLY_FIGHT = 1001;//申请与对方进行比赛
	public static final int STATU_U2_AGREE_FIGHT = 1002;//对方接受比赛
	public static final int STATU_U2_REFUSE_FIGHT = 1003;//对方拒绝比赛
	public static final int STATU_FIGHT_READY = 1004;//准备就绪请求比赛开始
	public static final int STATU_FIGHT_BEGIN = 1005;//服务器发送的比赛开始提示，Json里面需要包含Former(true/fulse)
	public static final int STATU_FIGHT_ING = 1006;//比赛进行中
		
	public static final int STATU_RUNTIME_ERROR = 999;//访问超时
	public static final int STATU_CONNET_FAILED = 998;//网络或服务器连接失败

	//fightstatus
	public static final int STATU_CATCH = 201;//接到球
	public static final int STATU_CANNOT_CATCH = 202;//接不到球
	public static final int STATU_GETSCORE = 401;//获取比分
	public static final int STATU_CONTINUE = 402;//继续比赛
	public static final int STATU_STOP = 403;//停止比赛

	//错误提示信息
	public static final String ERR_LOGIN_NOUSER = "不存在该用户名！";
	public static final String ERR_LOGIN_WRONGWD = "密码错误！";
	public static final String ERR_UNKNOW_TYPE = "未知的Json-type";
	public static final String ERR_ALREADY_LOGIN = "已经登录过了，请先在另一边注销";

	//Fight_UserInfo_Bean的状态集
	public static final int FIGHT_STATUS_STARTREPLY = 1;//开始申请比赛
	public static final int FIGHT_STATUS_AGREEREPLY = 2;//对方同意比赛
	public static final int FIGHT_STATUS_AGREESTART1 = 3;//第一人同意开始比赛
	public static final int FIGHT_STATUS_AGREESTART2 = 4;//第两人同意开始比赛
	public static final int FIGHT_STATUS_AGREESTART_ALL = 5;//第两人同意开始比赛
	public static final int FIGHT_STATUS_START_TO_SERVES = 6;//服务器发送完比赛开始的响应，等待一方发球

}