// Project: Pingpong Here
// Part: Server
// File: Fight_UserInfo_Bean
// Note: 服务器的通信主线程
// Author: Hurray Zhu
// Time: 2014.09.09
// E-mail: i@ihurray.com
// Web-site: http://blog.ihurray.com

//通信包
import java.net.*;
import java.util.*;


public class Fight_UserInfo_Bean 
{
    private Socket socket_user1;//用户1为申请者
    private Socket socket_user2;//用户2为接受者
    private long start_time;//该轮比赛开始时间
    private int round_number;//回合数
    public static int fightnumber = 0;
    private int status;//比赛状态
    private int score_user1 = 0;
    private int score_user2 = 0;
    private int L_score_user1 = 0;
    private int L_score_user2 = 0;


    public Fight_UserInfo_Bean(Socket socket_user1,Socket socket_user2)
    {
        this.socket_user1 = socket_user1;
        this.socket_user2 = socket_user2;
        this.start_time = Calendar.getInstance().getTimeInMillis();
        this.round_number = 0;
        this.status = R.FIGHT_STATUS_STARTREPLY;
    }

    public Socket getSocket_user1() {
        return socket_user1;
    }

    public void setSocket_user1(Socket socket_user1) {
        this.socket_user1 = socket_user1;
    }

    public Socket getSocket_user2() {
        return socket_user2;
    }

    public void setSocket_user2(Socket socket_user2) {
        this.socket_user2 = socket_user2;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public int getRound_number() {
        return round_number;
    }

    public void setRound_number(int round_number) {
        this.round_number = round_number;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus()
    {
        return status;
    }

    public Socket getOtherSocket(Socket socket)
    {
        if(socket == socket_user1)
            return socket_user2;
        else 
            return socket_user1;
    }
    
    //获取分数
    public int getScore1()
    {
        return score_user1;
    }
    public int getScore2()
    {
        return score_user2;
    }
    //1为正常继续比赛，2为一方胜利结束比赛
    public int addScore1() 
    {
        if((score_user1 <= 10) || (score_user1 - score_user2) <= 1)
            score_user1 ++;

        if((score_user1 >= 11) && (score_user1 - score_user2) > 1)
            return 2;
        else
            return 1;
    }
    public int addScore2()
    {
        if((score_user2 <= 10) || (score_user2 - score_user1) <= 1)
            score_user2 ++;
        
        if((score_user2 >= 11) && (score_user2 - score_user1) > 1)
            return 2;
        else
            return 1;
    }
    public int getL_Score1()
    {
        return score_user1;
    }
    public int getL_Score2()
    {
        return score_user2;
    }
    public void addL_Score1()
    {
        score_user1 ++;
    }
    public void addL_Score2()
    {
        score_user2 ++;
    }

    public int addMyScore(Socket socket)
    {
        if(socket == socket_user1)
        {
            return addScore1();
        }
        else
        {
            return addScore2();
        }
    }
    public int addHisScore(Socket socket)
    {
        if(socket == socket_user1)
        {
            return addScore2();
        }
        else
        {
            return addScore1();
        }
    }
    public int getMyScore(Socket socket)
    {
        if(socket == socket_user1)
            return getScore1();
        else
            return getScore2();
    }
    public int getHisScore(Socket socket)
    {
        if(socket == socket_user1)
            return getScore2();
        else
            return getScore1();
    }
    public int getL_MyScore(Socket socket)
    {
        if(socket == socket_user1)
            return getL_Score1();
        else
            return getL_Score2();
    }
    public int getL_HisScore(Socket socket)
    {
        if(socket == socket_user1)
            return getL_Score2();
        else
            return getL_Score1();
    }
    // public int getFightnumber()
    // {
    //     return this.fightnumber;
    // }
}