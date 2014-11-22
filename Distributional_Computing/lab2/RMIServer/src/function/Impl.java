/**
 * @Author hurray
 * @Part
 * @Note
 * @Encoding UTF-8
 * @Date 2014-11-22 03:59:14
 * @Copyright Hurray@BUPT
 * @MainPage http://www.ihurray.com
 *
 */
package function;

import bundle.Meeting;
import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;
import java.util.List;

/**
 * @Author hurray
 * @Class Regist
 */
public class Impl extends UnicastRemoteObject implements Interface {

    private HashMap<String, String> user_map;
    private List meetingList;

    public Impl(HashMap<String, String> user_map, List meetingList) throws RemoteException {
        super();
        this.user_map = user_map;
        this.meetingList = meetingList;
    }

    public boolean regist(String username, String passwd) throws RemoteException {
        if (user_map.containsKey(username)) {
            System.out.println("用户" + username + "注册失败！已有该用户名！");
            return false;
        } else {
            user_map.put(username, passwd);
            System.out.println("用户" + username + "成功注册！");
            return true;
        }
    }

    public boolean landed(String username, String passwd) throws RemoteException {
        if (user_map.get(username).equals(passwd)) {
            System.out.println("用户" + username + "登陆成功！");
            return true;
        } else {
            System.out.println("用户" + username + "登陆失败！");
            return false;
        }
    }

    public String addMeeting(String user1, String user2, long startTime, long endTime, String label) {

        if (user_map.get(user2) == null) {
            return "不存在该用户！";
        }
        if(startTime>endTime)
        {
            return "时间格式错误!请检查!";
        }
        for (int i = 0; i < meetingList.size(); i++) {
            Meeting thisMeeting = (Meeting) meetingList.get(i);
            if (thisMeeting.getUser1().equals(user1)) {
                if (thisMeeting.getUser2().equals(user2)) {
                    return "不可以重复预约另一用户！";
                } else {
                    if (thisMeeting.getStartTime() > endTime || thisMeeting.getEndTime() < startTime) {

                    } else {
                        return "您在本时间段内已经有安排的会晤！操作失败！";
                    }
                }
            } else if (thisMeeting.getUser1().equals(user2)) {
                if (thisMeeting.getUser2().equals(user1)) {
                    return "不可以重复预约另一用户！";
                } else {
                    if (thisMeeting.getStartTime() > endTime || thisMeeting.getEndTime() < startTime) {

                    } else {
                        return "对方在本时间段内已经有安排的会晤！操作失败！";
                    }
                }
            }
            if (thisMeeting.getUser2().equals(user1)) {
                if (thisMeeting.getUser1().equals(user2)) {
                    return "不可以重复预约另一用户！";
                } else {
                    if (thisMeeting.getStartTime() > endTime || thisMeeting.getEndTime() < startTime) {

                    } else {
                        return "您在本时间段内已经有安排的会晤！操作失败！";
                    }
                }
            } else if (thisMeeting.getUser2().equals(user2)) {
                if (thisMeeting.getUser1().equals(user1)) {
                    return "不可以重复预约另一用户！";
                } else {
                    if (thisMeeting.getStartTime() > endTime || thisMeeting.getEndTime() < startTime) {

                    } else {
                        return "对方在本时间段内已经有安排的会晤！操作失败！";
                    }
                }
            }
        }
        Meeting newMeeting = new Meeting();
        newMeeting.setUser1(user1);
        newMeeting.setUser2(user2);
        newMeeting.setStartTime(startTime);
        newMeeting.setEndTime(endTime);
        newMeeting.setLabel(label);

        meetingList.add(newMeeting);
        System.out.println(user1 + "成功添加会晤with" + user2 + "@" + startTime + "~" + endTime + ":" + label + "！");
        return "true";
    }
}
