
/**
 * @Author hurray
 * @Part Client
 * @Note 客户端
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
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
            String registryURL = "rmi://" + R.HOSTNAME + ":" + R.PORT + "/meeting";
            Interface h = (Interface) Naming.lookup(registryURL);
            System.out.println("Lookup completed ");

            //正常客户端模式
            if (args.length == 0) {
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
                            //选择1进入注册
                            case 1: {
                                System.out.println("\n您选择了注册！\n请输入用户名：");
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
                            //选择2进入登陆
                            case 2: {
                                System.out.println("\n您选择了登陆！\n请输入用户名：");
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
                            //选择3退出
                            case 3: {
                                exit = true;
                                break;
                            }

                        }
                    } else {
                        System.out.println(R.MENU2);
                        try {
                            int choice = Integer.parseInt(br.readLine());
                            switch (choice) {
                                //选择1注销
                                case 1: {
                                    System.out.println("\n您选择了注销！注销成功！");
                                    landed = false;
                                    break;
                                }
                                //选择2添加会晤
                                case 2: {
                                    System.out.println("\n您选择了添加会晤！\n请输入另一预约人用户名：");
                                    String user2 = br.readLine();
                                    System.out.println("请输入会晤开始时间(仿照格式201411011300表示2014年11月01日13点00分)：");
                                    long startTime = Long.parseLong(br.readLine());
                                    System.out.println("请输入会晤结束时间(仿照格式201411011300表示2014年11月01日13点00分)：");
                                    long endTime = Long.parseLong(br.readLine());
                                    System.out.println("请输入会议标签：");
                                    String label = br.readLine();
                                    String returnMsg = h.addMeeting(username, user2, startTime, endTime, label);
                                    break;
                                }
                                //选择3查询
                                case 3: {
                                    System.out.println("\n您选择了查询会晤！\n请输入查询起始时间(-1为不限)：");
                                    long startTime = Long.parseLong(br.readLine());
                                    System.out.println("请输入查询终止时间(-1为不限)：");
                                    long endTime = Long.parseLong(br.readLine());
                                    String returnMsg = h.queryMeeting(username, startTime, endTime);
                                    System.out.println(returnMsg);

                                    break;
                                }
                                //选择4删除
                                case 4: {
                                    System.out.println("\n您选择了删除会晤！您现有的会晤有：");
                                    System.out.println(h.queryMeeting(username, -1, -1));
                                    System.out.println("请输入会晤ID号删除会晤：");
                                    int id = Integer.parseInt(br.readLine());
                                    System.out.println(h.delMeeting(username, id));
                                    break;
                                }
                                //选择5清除
                                case 5: {
                                    System.out.println("\n您选择了清楚会晤！确定删除所有您建立的会晤？回复Y确定操作，其他则取消。");
                                    String input = br.readLine();
                                    if (input.equals("Y")) {
                                        System.out.println(h.clearMeeting(username));
                                    }
                                    break;
                                }
                                //选择6退出
                                case 6: {
                                    exit = true;
                                    break;
                                }

                            }
                        } catch (Exception ex) {
                            System.out.println("\n输入有错！请重试！");
                        }
                    }
                }

            } 
            //使用命令行传参操作模式
            else {
                if (args[0].equals("register")) {
                    for (int i = 1; i < args.length - 1; i += 2) {
                        boolean TF = h.regist(args[i], args[i + 1]);
                        if (TF) {
                            System.out.println("成功注册用户" + args[i]);
                        } else {
                            System.out.println("用户" + args[i] + "注册失败");
                        }
                    }
                } else if (args[0].equals("add")) {
                    for (int i = 1; i < args.length - 4; i += 5) {
                        System.out.println(h.addMeeting(args[i], args[i + 1],
                                Long.parseLong(args[i + 2]), Long.parseLong(args[i + 3]),
                                args[i + 4]));
                    }

                } else if (args[0].equals("query")) {
                    System.out.println(h.queryMeeting(args[1], Long.parseLong(args[2]),
                            Long.parseLong(args[3])));

                } else if (args[0].equals("delete")) {
                    System.out.println(h.delMeeting(args[1], Integer.parseInt(args[2])));

                } else if (args[0].equals("clear")) {
                    System.out.println(h.clearMeeting(args[1]));
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in HelloClient: " + e);
        }
    }
}
