package mychatapplication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class ServerThread implements Runnable {
	
	private Socket socket;
	private String name;
	private boolean isAlive;
	private boolean hasMessages = false;
	private LinkedList<String> messagesQue;
	
	public ServerThread(Socket socket, String name) {
		this.socket = socket;
		this.name = name;
		messagesQue = new LinkedList<String>();
	}
	
	public void addMessage(String message) {
		synchronized (messagesQue) {
			hasMessages = true;
			messagesQue.push(message);
		}
		
	}
	
	/**
	 * Recieves input stream from server and prints it to console. Uses messagesQue to store messages to send.
	 * If there are messages in the que, it will shoot them to the output stream (to the server).
	 */
	
	@Override
	public void run() {
		System.out.println("Welcome: " + name + "!");
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), false); 
			InputStream serverIn = socket.getInputStream();
			Scanner userIn = new Scanner(serverIn);
			while(!socket.isClosed() ) {
				if(serverIn.available() > 0) {
					if (userIn.hasNextLine()) {
						System.out.println(userIn.nextLine());
					}
				}
				if (hasMessages) {
					String nextMsg = "";
					synchronized(messagesQue){
	                    nextMsg = messagesQue.pop();
	                    hasMessages = !messagesQue.isEmpty();
	                }
				out.println(name + " > " + nextMsg);
				out.flush();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
