package mychatapplication.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import mychatapplication.client.ServerThread;

public class Client	{
	
	private static final String host = "localhost";
    private static final int portNumber = 4444;
    
    private String userName;
    private String serverHost;
    private int serverPort;
	public static void main(String[] args) throws InterruptedException {
		
	//Enter Name
	String givenName = null;
	boolean isValid = false; //for username
	System.out.println(">​ Enter your name:");
	Scanner scanner = new Scanner(System.in);
	while(!isValid) { //get a username without whitespace
        givenName = scanner.nextLine();
        if(givenName.trim().equals("")){
            System.out.println("> Please enter a valid username:");
        }
        else {
        		isValid = true;
        }
    }
	
	Client client = new Client(givenName, host, portNumber);
    client.startClient(scanner);
    scanner.close();
    	
	}
    
    private Client(String userName, String host, int portNumber){
        this.userName = userName;
        this.serverHost = host;
        this.serverPort = portNumber;
    }
    
    private void startClient(Scanner scanner) throws InterruptedException {
	    	try {
			Socket socket = new Socket(serverHost, serverPort);
			Thread.sleep(1000);
			ServerThread serverAccessThread = new ServerThread(socket, userName);
			Thread serverThread = new Thread(serverAccessThread);
			serverThread.start();
			while(serverThread.isAlive()){
                if(scanner.hasNextLine()){
                    serverAccessThread.addMessage(scanner.nextLine());
                }
                else {
                    Thread.sleep(200);
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
	

	
