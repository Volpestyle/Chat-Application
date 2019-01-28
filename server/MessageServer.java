package mychatapplication.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

public class MessageServer {
	
	private static final int portNumber = 4444; //Change if you want to change port
	private int port;
	private List<ClientThread> clients;
	private int clientNum = 0;
	
	public static void main(String[] args) throws IOException {
		MessageServer server = new MessageServer(portNumber);
		server.startServer();
	}
		
	public MessageServer(int port) {
		this.port = port;
	}
	
	public List<ClientThread> getClients() {
		return clients;
	}
	
	private void startServer() {
		clients = new ArrayList<ClientThread>();
		ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            acceptClients(serverSocket);
        } catch (IOException e){
            System.err.println("Could not listen on port: "+port);
            System.exit(1);
        }
	}
		
	public void acceptClients(ServerSocket serverSocket) {
		System.out.println("Server started at port " + serverSocket.getLocalSocketAddress());
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("Server got connected to client "
						+ ++clientNum);
				ClientThread clientAccessThread = new ClientThread(this, socket, clientNum);
				Thread clientThread = new Thread(clientAccessThread);
				clientThread.start();
				clients.add(clientAccessThread);
			}
			catch(IOException e) {
				System.out.println("Accept failed: " + port);
				System.exit(-1);
			}
			
		}
		
	}
	
	
}

