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
//GUI
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame {
	// GUI
	private JTextField jtf = new JTextField();
	private JTextArea jta = new JTextArea();
	// IO streams
	private DataOutputStream outputToServer;
	private DataInputStream inputFromServer;

	/**
	 * 
	 * @name main
	 * @note 主入口
	 * @author Hurray
	 *
	**/
	public static void main(String[] args) {
		new Client();
	}


	/**
	 * 
	 * @note 构造函数
	 * @author Hurray
	 *
	**/
	public Client()
	{
		// GUI
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("输入消息，回车发送"), BorderLayout.WEST);
		p.add(jtf, BorderLayout.CENTER);
		jtf.setHorizontalAlignment(JTextField.LEFT);
		setLayout(new BorderLayout());
		add(p, BorderLayout.NORTH);
		add(new JScrollPane(jta), BorderLayout.CENTER);
		jtf.addActionListener(new TextFieldListener());
		setTitle("Client");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		try
		{
			// Create a socket to connect to the server
			Socket socket = new Socket(R.IPADDRESS, R.PORT);
			inputFromServer = new DataInputStream(socket.getInputStream());
			outputToServer = new DataOutputStream(socket.getOutputStream());

			jta.append("欢迎进入客户端程序！\n");
			// 如果在命令行模式请恢复下面的线程
			// ClientThreadOutput thread = new ClientThreadOutput(socket,outputToServer);
			// thread.start();	
			ClientThreadInput threadInput = new ClientThreadInput(socket,inputFromServer);
			threadInput.start();
		}
		catch(Exception e){}
	}

	/**
	 * 
	 * @name ClientThreadInput
	 * @note 服务器发送来的消息响应
	 * @author Hurray
	 *
	**/
	private class ClientThreadInput extends Thread {
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
					jta.append("【群聊消息】" + ss + "\n");
				}
				catch(Exception ex)
				{
					jta.append("error:" + ex + "\n");
				}
			}
		}
	}

	/**
	 * 
	 * @name ClientThreadOutput
	 * @note 命令时用户输入读入接口
	 * @author Hurray
	 *
	**/
	// 
	// class ClientThreadOutput extends Thread {	
	// 	public Socket socket;

	// 	public ClientThreadOutput(Socket socket,DataOutputStream outputToServer)
	// 	{
	// 		this.socket = socket;
	// 	}

	// 	public void run() 
	// 	{
	// 		java.util.Scanner sc =new java.util.Scanner(System.in);
	// 		while(true)
	// 		{
	// 			String inputMsg = sc.nextLine();
	// 			try
	// 			{
	// 				outputToServer.writeUTF(inputMsg);
	// 			}catch(Exception e){}	
	// 		}
	// 	}
	// }

	/**
	 * 
	 * @name TextFieldListener 
	 * @note 带GUI时用户输入读入接口
	 * @author Hurray
	 *
	**/
	private class TextFieldListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String input = jtf.getText();
				try
				{
					outputToServer.writeUTF(input);
				}catch(Exception ex)
				{
					jta.append("error:" + ex + "\n");
				}
			}
			catch(Exception ee)
			{
				jta.append("error:" + ee + "\n");
			}
		}
	}
}
