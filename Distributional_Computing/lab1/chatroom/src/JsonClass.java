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
//         "friendname": "(String)", 
//         "msg": "(String",   
//     }
// }

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonClass {
	private JSONObject jsonObj;
	private JSONObject jsondata;
	
    public JsonClass()
    {
    	jsonObj = new JSONObject();
    	jsondata = new JSONObject();
    }
    public JsonClass(String a) throws JSONException
    {
    	jsonObj = new JSONObject(a);
    	jsondata = jsonObj.getJSONObject("data");
    }

	//返回json结果
    public String getJsonStr() throws JSONException
    {
        //自动插入time
        jsonObj.put("time",Calendar.getInstance().getTimeInMillis());
        jsonObj.put("data",jsondata);
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
    public String getFriendName() throws JSONException 
    {
    	return jsondata.getString("friendname");
    }
	//设置好友名
    public void setFriendName(String friendname) throws JSONException 
    {
    	jsondata.put("friendname",friendname);
    }
	
}