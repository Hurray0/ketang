
/**
 * @Author hurray
 * @Part
 * @Note
 * @Encoding UTF-8
 * @Date 2014-11-22 04:12:55
 * @Copyright Hurray@BUPT
 * @MainPage http://www.ihurray.com
 *
 */
import java.io.*;
import java.rmi.*;
import function.*;
import resource.R;

/**
 * @Author hurray
 * @Class RMIClient
 */
public class RMIClient {

    public static void main(String args[]) {
        try {
            int RMIPort;
            String hostName;
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
            String registryURL = "rmi://" + R.HOSTNAME + ":" + R.PORT + "/meeting";
            Interface h = (Interface) Naming.lookup(registryURL);
            System.out.println("Lookup completed ");

            boolean landed = false;
            boolean exit = false;
            String username = null;
            while (true) {
                if (exit) {
                    break;
                }
                if (!landed) {
                    System.out.println(R.MENU1);
                    int choice = Integer.parseInt(br.readLine());
                    switch (choice) {
                        case 1: {
                            System.out.println("您选择了注册！\n请输入用户名：");
                            username = br.readLine();
                            System.out.println("请输入密码：");
                            String passwd = br.readLine();
                            landed = h.regist(username, passwd);
                            if (landed) {
                                System.out.println("注册成功！");
                            } else {
                                System.out.println("注册失败！请更换用户名重试！");
                            }
                            break;
                        }
                        case 2: {
                            System.out.println("您选择了登陆！\n请输入用户名：");
                            username = br.readLine();
                            System.out.println("请输入密码：");
                            String passwd = br.readLine();
                            landed = h.landed(username, passwd);
                            if (landed) {
                                System.out.println("登陆成功！");
                            } else {
                                System.out.println("登陆失败！请重试！");
                            }
                            break;
                        }
                        case 3: {
                            exit = true;
                            break;
                        }

                    }
                } else {
                    System.out.println(R.MENU2);
                    int choice = Integer.parseInt(br.readLine());
                    switch (choice) {
                        case 1: {
                            System.out.println("您选择了注销！注销成功！");
                            landed = false;
                            break;
                        }
                        case 2: {
                            System.out.println("您选择了添加会晤！\n请输入另一预约人用户名：");
                            String user2 = br.readLine();
                            System.out.println("请输入会晤开始时间(仿照格式201411011300表示2014年11月01日13点00分)：");
                            long startTime = Integer.parseInt(br.readLine());
                            System.out.println("请输入会晤结束时间(仿照格式201411011300表示2014年11月01日13点00分)：");
                            long endTime = Integer.parseInt(br.readLine());
                            System.out.println("请输入会议标签：");
                            String label = br.readLine();
                            String returnMsg = h.addMeeting(username, user2, startTime, endTime, label);
                            if(returnMsg.equals("true"))
                                System.out.println("添加会议成功！");
                            else
                                System.out.println(returnMsg);
                            
                            break;
                        }
                        case 3: {
                            exit = true;
                            break;
                        }

                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Exception in HelloClient: " + e);
        }
    }
}
