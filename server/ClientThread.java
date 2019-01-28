package mychatapplication.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
	
	private Socket socket; // this is socket on the server side that connects to the CLIENT
	private MessageServer server;
	private PrintWriter clientOut;
	int num; // keeps track of its number just for identifying purposes

	public ClientThread(MessageServer server, Socket socket, int num) {
		this.server = server;
		this.socket = socket;
		this.num = num;
	}
	
	private PrintWriter getWriter() {
		return clientOut;
	}
	
	/**
	 * Gets input stream coming from client, and shoots it through every output stream in the client list.
	 */
	@Override
	public void run() {
		printSocketInfo(socket); // just print some information at the server side about the connection
		try {
			//setup
			clientOut = new PrintWriter(socket.getOutputStream(), false);
			Scanner in = new Scanner(socket.getInputStream());
			//start communicating
			while (!socket.isClosed()) {
				if (in.hasNextLine()) {
					String input = in.nextLine();
					System.out.println(input);
					for (ClientThread clientThread : server.getClients()) { //Iterating through every client thread the server has.
						if (clientThread != this) {
							PrintWriter clientOut = clientThread.getWriter();
		                    	if(clientOut != null){
		                    		clientOut.print(input + "\r\n");
		                    		clientOut.flush();
		                    	}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // end of method run()
		void printSocketInfo(Socket s) {
			System.out.print("Socket on Server " + Thread.currentThread() + " ");
			System.out.print("Server socket Local Address: " + s.getLocalAddress()
					+ ":" + s.getLocalPort());
			System.out.println("  Server socket Remote Address: "
					+ s.getRemoteSocketAddress());
		} // end of printSocketInfo
		
	} // end of class ClientHandler

