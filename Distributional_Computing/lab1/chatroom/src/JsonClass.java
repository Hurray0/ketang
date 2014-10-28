/**
 *
 * @author hurray
 * 
 */
// {
//     "type": "(int)",
//     "status": "(int)",
//     "time": "(Long)",
//     "note": "(String)",
//     "data": {
//         "username": "(String)",
//         "password": "(String)",
//         "ip": "(String)",
//         "fightdata": {
//             "fightstatus": "(int)",
//             "fightid": "(int)",
//             "friendname": "(String)",
//             "former": "(boolean)",//是否先发球，1为先发球，0为后发球
//             "myscore": "(int)",
//             "hisscore": "(int)",
//             "L_myscore": "(int)",
//             "L_hisscore":"(int)",
//             "balldata": {
//                 【等待何志鹏来填和设计，这部分数据服务器不会处理，直接发给另一方用户的】
//             }
//         }     
//     }
// }

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonClass {
	private JSONObject jsonObj;
	private JSONObject jsondata;
    private JSONObject jsonfightdata;
    private JSONObject jsonballdata;
	
    public JsonClass()
    {
    	jsonObj = new JSONObject();
    	jsondata = new JSONObject();
        jsonfightdata = new JSONObject();
        jsonballdata = new JSONObject();
    }
    public JsonClass(String a) throws JSONException
    {
    	jsonObj = new JSONObject(a);
    	jsondata = jsonObj.getJSONObject("data");
        try{
            jsonfightdata = jsondata.getJSONObject("fightdata");
            try{
                jsonballdata = jsonfightdata.getJSONObject("balldata");
            }
            catch(Exception e)
            {
                jsonballdata = new JSONObject();
            }
        }
        catch(Exception e)
        {
            jsonfightdata = new JSONObject();
        }
    }

	//返回json结果
    public String getJsonStr() throws JSONException
    {
        //自动插入time
        jsonObj.put("time",Calendar.getInstance().getTimeInMillis());
        jsonObj.put("data",jsondata);
        jsondata.put("fightdata",jsonfightdata);
        jsonfightdata.put("balldata",jsonballdata);
    	return jsonObj.toString();
    }

	//获取time
    public long getTime() throws JSONException
    {
    	return jsonObj.getLong("time");
    }

	//设置json包属性
    public void setType(int type) throws JSONException
    {
    	jsonObj.put("type",type);
    }
	//获取type
    public int getType() throws JSONException
    {
    	return jsonObj.getInt("type");
    }


	//设置状态
    public void setStatus(int status) throws JSONException
    {
    	jsonObj.put("status",status);
    }
	//获取status
    public int getStatus() throws JSONException
    {
    	return jsonObj.getInt("status");
    }


	//设置备注
    public void setNote(String note) throws JSONException
    {
    	jsonObj.put("note",note);
    }
	//获取note
    public String getNote() throws JSONException
    {
    	return jsonObj.getString("note");
    }

	//设置ip
    public void setIp(String ip) throws JSONException
    {
    	jsondata.put("ip",ip);
    }
	//获取ip
    public String getIp() throws JSONException
    {
    	return jsondata.getString("ip");
    }

	//设置username
    public void setUsername(String username) throws JSONException
    {
    	jsondata.put("username",username);
    }
	//获取username
    public String getUsername() throws JSONException
    {
    	return jsondata.getString("username");
    }

	//设置密码
    public void setPassword(String password) throws JSONException
    {
    	jsondata.put("password",password);
    }
	//获取密码
    public String getPassword() throws JSONException
    {
    	return jsondata.getString("password");
    }
	//获取好友名
    public String getFriendName() throws JSONException {

    	return jsonfightdata.getString("friendname");
    }
	//设置好友名
    public void setFriendName(String friendname) throws JSONException {

    	jsonfightdata.put("friendname",friendname);
    }
	//比赛的id号
    public void setFightid(int fightid) throws JSONException
    {
    	jsonfightdata.put("fightid",fightid);
    }
    public int getFightid() throws JSONException
    {
    	return jsonfightdata.getInt("fightid");
    }
	//设置former
    public void setFormer(boolean former) throws JSONException
    {
    	jsonfightdata.put("former",former);
    }
	//获取former
    public boolean getFormer() throws JSONException
    {
    	return jsonfightdata.getBoolean("former");
    }
    //比赛状态fightstatus
    public void setFightStatus(int fightstatus) throws JSONException
    {
        jsonfightdata.put("fightdata",fightstatus);
    }
    public int getFightStatus() throws JSONException
    {
        return jsonfightdata.getInt("fightdata");
    }
    //自己的小分
    public void setMyscore(int myscore) throws JSONException
    {
        jsonfightdata.put("myscore",myscore);
    }
    public int getMyscore() throws JSONException
    {
        return jsonfightdata.getInt("myscore");
    }
    //别人的小分
    public void setHisscore(int hisscore) throws JSONException
    {
        jsonfightdata.put("hisscore",hisscore);
    }
    public int getHisscore() throws JSONException
    {
        return jsonfightdata.getInt("hisscore");
    }
    //自己的大分
    public void setL_Myscore(int L_myscore) throws JSONException
    {
        jsonfightdata.put("L_myscore",L_myscore);
    }
    public int getL_Myscore() throws JSONException
    {
        return jsonfightdata.getInt("L_myscore");
    }
    //别人的大分
    public void setL_Hisscore(int L_hisscore) throws JSONException
    {
        jsonfightdata.put("L_hisscore",L_hisscore);
    }
    public int getL_Hisscore() throws JSONException
    {
        return jsonfightdata.getInt("L_hisscore");
    }
    //设置比分的综合函数
    public void setAllScore(int myscore,int hisscore,int L_myscore,int L_hisscore) throws JSONException
    {
        setMyscore(myscore);
        setHisscore(hisscore);
        setL_Myscore(L_myscore);
        setL_Hisscore(L_hisscore);
    }


    
    //balldata 【何志鹏来加，方法是set里面jsonballdata.put()，get是jsonballdata.get?()，如果需要放数组请自行研究json用法】
    
}