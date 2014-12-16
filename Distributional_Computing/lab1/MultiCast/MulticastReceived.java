import java.net.*;
import java.io.*;

public class MulticastReceived {

    public static void main(String[] args) throws Exception {
        InetAddress group = InetAddress.getByName("224.0.0.1");//组播地址
        int port = 4000;//端口
        MulticastSocket msr = null;
        try {
            msr = new MulticastSocket(port);//1.创建一个用于发送和接收的MulticastSocket组播套接字对象
            //msr.joinGroup(group);//3.使用组播套接字joinGroup(),将其加入到一个组播
            byte[] buffer = new byte[8192];
            System.out.println("接收数据包启动！（启动时间：）" + new java.util.Date() + ")");
            while (true) {
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);//2.创建一个指定缓冲区大小及组播地址和端口的DatagramPacket组播数据包对象
                msr.receive(dp);//4.使用组播套接字的receive（）方法，将组播数据包对象放入其中，接收组播数据包
                String s = new String(dp.getData(), 0, dp.getLength());//5.解码组播数据包提取信息，并依据得到的信息作出响应
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (msr != null) {
                try {
                    msr.leaveGroup(group);//7.使用组播套接字的leaveGroup()方法，离开组播组
                    msr.close();//关闭组播套接字
                } catch (IOException e) {
                }
            }
        }
    }
}