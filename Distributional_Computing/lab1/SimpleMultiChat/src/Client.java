// Project: SimpleMultiChat
// Part: Client
// File: Client
// Note: 客户端主程序
// Author: Hurray Zhu
// Time: 2014.10.28
// E-mail: i@ihurray.com
// Web-site: http://blog.ihurray.com
// GitHub: https://github.com/Hurray0/ketang/tree/master/Distributional_Computing/lab1

//通信包
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	// IO streams
	private DataOutputStream outputToServer;
	private DataInputStream inputFromServer;
	public int landed;
	public String inputUsername;

	public static void main(String[] args) {
		new Client();
	}


	public Client()
	{
		landed = 0;
		try
		{
		// Create a socket to connect to the server
			Socket socket = new Socket(R.IPADDRESS, R.PORT);
			inputFromServer = new DataInputStream(socket.getInputStream());
			outputToServer = new DataOutputStream(socket.getOutputStream());

			System.out.println("欢迎进入客户端程序！");
			ClientThreadOutput thread = new ClientThreadOutput(socket,outputToServer);
			thread.start();	
			ClientThreadInput thread_input = new ClientThreadInput(socket,inputFromServer);
			thread_input.start();
		}
		catch(Exception e){}
	}

	class ClientThreadInput extends Thread {
		public Socket socket;

		public ClientThreadInput(Socket socket,DataInputStream inputFromServer)
		{
			this.socket = socket;
		}

		public void run(){
			while(true)
			{
				try
				{
					String ss = inputFromServer.readUTF();
					System.out.println("【群聊消息】" + ss);
				}
				catch(Exception ex)
				{
					System.out.println("error:"+ex);
				}
			}
		}
	}

	class ClientThreadOutput extends Thread {	
		public Socket socket;

		public ClientThreadOutput(Socket socket,DataOutputStream outputToServer)
		{
			this.socket = socket;
		}

		public void run() 
		{
			java.util.Scanner sc =new java.util.Scanner(System.in);
			while(true)
			{
				String inputMsg = sc.nextLine();
				try
				{
					outputToServer.writeUTF(inputMsg);
				}catch(Exception e){}	
			}
		}
	}
}
