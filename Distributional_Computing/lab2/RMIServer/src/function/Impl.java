/**
 * @Author hurray
 * @Part RMI
 * @Note RMI的模板
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
        if (startTime > endTime) {
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
                        return user1 + "在本时间段内已经有安排的会晤！操作失败！";
                    }
                }
            } else if (thisMeeting.getUser1().equals(user2)) {
                if (thisMeeting.getUser2().equals(user1)) {
                    return "不可以重复预约另一用户！";
                } else {
                    if (thisMeeting.getStartTime() > endTime || thisMeeting.getEndTime() < startTime) {

                    } else {
                        return user2 + "在本时间段内已经有安排的会晤！操作失败！";
                    }
                }
            }
            if (thisMeeting.getUser2().equals(user1)) {
                if (thisMeeting.getUser1().equals(user2)) {
                    return "不可以重复预约另一用户！";
                } else {
                    if (thisMeeting.getStartTime() > endTime || thisMeeting.getEndTime() < startTime) {

                    } else {
                        return user1 + "在本时间段内已经有安排的会晤！操作失败！";
                    }
                }
            } else if (thisMeeting.getUser2().equals(user2)) {
                if (thisMeeting.getUser1().equals(user1)) {
                    return "不可以重复预约另一用户！";
                } else {
                    if (thisMeeting.getStartTime() > endTime || thisMeeting.getEndTime() < startTime) {

                    } else {
                        return user2 + "在本时间段内已经有安排的会晤！操作失败！";
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
        newMeeting.setId(meetingList.size() + 1);
        newMeeting.setStatus(1);

        meetingList.add(newMeeting);
        System.out.println(user1 + "成功添加会晤with" + user2 + "@" + startTime + "~" + endTime + ":" + label + "！");
        return user1 + "成功添加会晤with" + user2 + "@" + startTime + "~" + endTime + ":" + label + "！";
    }

    public String queryMeeting(String user1, long startTime, long endTime) {

        String msg = "";
        for (int i = 0, j = 1; i < meetingList.size(); i++) {
            Meeting thisMeeting = (Meeting) meetingList.get(i);

            if ((thisMeeting.getUser1().equals(user1) || thisMeeting.getUser2().equals(user1))
                    && (thisMeeting.getStartTime() > startTime || startTime == -1)
                    && (thisMeeting.getEndTime() < endTime || endTime == -1)
                    && thisMeeting.getStatus() == 1) {
                msg += "No" + j + ".\n发起者：" + thisMeeting.getUser1() + "\n会晤者："
                        + thisMeeting.getUser2() + "\n"
                        + "开始时间：" + thisMeeting.getStartTime() + "\n"
                        + "结束时间：" + thisMeeting.getEndTime() + "\n"
                        + "标注：" + thisMeeting.getLabel() + "\n"
                        + "会议ID：" + thisMeeting.getId() + "\n\n";
                j++;
            }
        }

        System.out.println("用户" + user1 + "成功查找会晤！");
        return msg;
    }

    public String delMeeting(String user1, int id) {

        try {
            Meeting thisMeeting = (Meeting) meetingList.get(id - 1);
            if ((thisMeeting.getUser1().equals(user1) || thisMeeting.getUser2().equals(user1))
                    && thisMeeting.getStatus() == 1) {
                thisMeeting.setStatus(0);
                System.out.println("用户" + user1 + "成功删除会晤！");
                return "成功删除会晤！";
            } else {
                return "输入序号有误！删除失败！";
            }
        } catch (Exception e) {
            return "输入序号有误！删除失败！";
        }
    }

    public String clearMeeting(String user1) {
        int i, j;
        for (i = 0, j = 0; i < meetingList.size(); i++) {
            Meeting thisMeeting = (Meeting) meetingList.get(i);
            if (thisMeeting.getUser1().equals(user1)) {
                thisMeeting.setStatus(0);
                j++;
            }
        }
        System.out.println("用户" + user1 + "成功删除" + j + "个会晤！");
        return "成功删除" + j + "个会晤！";
    }
}
