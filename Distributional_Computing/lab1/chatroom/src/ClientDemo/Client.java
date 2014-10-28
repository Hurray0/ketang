//通信包
import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

public class Client {
	// IO streams
	private DataOutputStream outputToServer;
	private DataInputStream inputFromServer;

	public static void main(String[] args) {
		new Client();
	}


	public Client()
	{
		try
		{
		// Create a socket to connect to the server
			Socket socket = new Socket("localhost", R.Server_Port);
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
			System.out.println("进入输入线程");
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
				System.out.println("进入输出线程");
				java.util.Scanner sc =new java.util.Scanner(System.in);
				while(true)
				{
					System.out.println("输入type");
					int input_type = Integer.parseInt(sc.nextLine());
					System.out.println("输入status");
					int status = Integer.parseInt(sc.nextLine());
					System.out.println("输入username");
					String input_username = sc.nextLine();
					System.out.println("输入code");
					String s = sc.nextLine();
					System.out.println("输入data:friendname");
					String friendname = sc.nextLine();
					try{
						JsonClass myjson = new JsonClass();
						// myjson.setNote(s);
						myjson.setType(input_type);
						myjson.setStatus(status);
						// myjson.setIp("1111111");
						myjson.setUsername(input_username);
						myjson.setPassword(s);
						myjson.setFriendName(friendname);
						String r = myjson.getJsonStr();
						outputToServer.writeUTF(r);
					}
					catch(Exception ex)
					{}
					
					outputToServer.flush();	
				}
			} catch (IOException ex) {
				System.out.println(ex.toString() + '\n');
			}
		}	
	}
}
