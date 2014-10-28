//通信包
import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

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
			Socket socket = new Socket(R.IPADDRESS, R.Server_Port);
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
			// System.out.println("进入输入线程");
			while(true)
			{
				try{
					String ss = inputFromServer.readUTF();
					System.out.println("Get back:" + ss);
					JsonClass receivedjson = new JsonClass(ss);

					int receivedType = receivedjson.getType();
					System.out.println("get type:"+receivedType);
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

		public void run() {
			try {
				// System.out.println("进入输出线程");
				java.util.Scanner sc =new java.util.Scanner(System.in);
				while(true)
				{
					System.out.println("1.登录\n2.注册\n3.退出\n4.群聊\n5.私聊\n请选择服务序号：");
					int inputNumber = Integer.parseInt(sc.nextLine());
					try
					{
						switch(inputNumber)
						{
							case 1:
							if(landed == 1) 
							{
								System.out.println("已经登录过了！请重新选择！\n");
								continue;
							}
							else
							{
								JsonClass myjson = new JsonClass();
								myjson.setType(R.CMD_LOGIN);
								System.out.println("请输入用户名：");
								inputUsername = sc.nextLine();
								System.out.println("请输入密码：");
								String inputPasswd = sc.nextLine();
								myjson.setUsername(inputUsername);
								myjson.setPassword(inputPasswd);
								String r = myjson.getJsonStr();
								outputToServer.writeUTF(r);
							}
							break;

							case 2:
							if(landed == 1) 
							{
								System.out.println("您处于登录状态！请重新选择！\n");
								continue;
							}
							else
							{
								JsonClass myjson = new JsonClass();
								myjson.setType(R.CMD_LOGIN);
								System.out.println("请输入用户名：");
								inputUsername = sc.nextLine();
								System.out.println("请输入密码：");
								String inputPasswd = sc.nextLine();
								myjson.setUsername(inputUsername);
								myjson.setPassword(inputPasswd);
								String r = myjson.getJsonStr();
								outputToServer.writeUTF(r);
							}
							case 3:
							break;

							case 4:
							if(landed == 0) 
							{
								System.out.println("您还未登录！请先登录！\n");
								continue;
							}
							else
							{
								while(true)
								{
									System.out.println("请输入群聊内容，输入exit退出群聊：\n");
									String msg = sc.nextLine();
									if(msg.equals("exit"))
										break;
									else
									{
										JsonClass myjson = new JsonClass();
										myjson.setType(R.CMD_GROUP_CHAT);
										myjson.setUsername(inputUsername);
										myjson.setMsg(msg);
										String r = myjson.getJsonStr();
										outputToServer.writeUTF(r);
									}
								}
							}
							break;
							case 5:
						}
					}catch (Exception eeee){}
					
					outputToServer.flush();	
				}
			} catch (IOException ex) {
				System.out.println(ex.toString() + '\n');
			}
		}	
	}
}
