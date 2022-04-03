COMS 319   
Chat Application   
James Volpe

### About
As my first project for COM S 319: Software Construction and User Interfaces, I developed a basic chat application in Java using sockets.
I started by reading up about sockets which subsequentley led to watching dozens of youtube tutorials. Eventually, I got the hang of using socket input and output streams, and developed my own implementation of client and server threads. For a full details on the implementation, check out the "Project Report" PDF in the github repository. To see the app in action, check out [this video](https://www.youtube.com/embed/rdpS6XJVVl8).

### To Use Server and Client:


1. To start server, run MessageServer.java.
2. To connect to server, run Client.java and enter a username.
3. Type any message into client console, and see that your message will be sent to the server console, and any other clients connected to the server.


### To Change Port:


1. Change the portNumber variable value specified in MessageServer.java and Client.java.
“private static final int portNumber”
