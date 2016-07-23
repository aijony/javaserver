package serverFiles;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import manager.CookieManager;
import manager.LogManager;


/*
 * This thread will handle a newly connected client.
*/
public class ServerSocketThread extends Thread{
	 private Socket socket;
     private int clientNumber;
     private volatile CountDownLatch outputReadyLatch;
     private CommandManager manager;
     public ServerSocketThread(Socket sock, int clientNum){
         socket = sock;
         clientNumber = clientNum;
         outputReadyLatch = new CountDownLatch(1);
         manager = new CommandManager(outputReadyLatch);
         LogManager.log("New connection with client# " + clientNumber + " at " + socket);
     }

     /**
      * Services this thread's client by first sending the
      * client a welcome message then repeatedly reading strings
      * processing them and returning an output.
      */
     public void run() {
         try {

             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             
             Cookie tempSendCookie = new Cookie();
             LogManager.log("Cookie: " + tempSendCookie);
             
             // Send a welcome message to the client.
             out.println("Hello, you are client #" + clientNumber + "." + tempSendCookie);
             out.println("Type shutdownclient quit\n" + tempSendCookie);

             // Get messages from the client, line by line; 
            
            manager.start();
             while (true) {
            	 
            	 //Gets the input
                 String input = in.readLine();
                 
                 //Sets the input
                 manager.setInput(input);
     
                 //Waits for the input to be ready
                 manager.outputReadyLatch.await();
                 //Resets the latch
                 manager.outputReadyLatch = new CountDownLatch(1);
                 
                 //Outputs the newly created buffer/output
                 out.println(manager.buffer);
         		 
             }
         } catch (IOException e) {
             LogManager.log("Error handling client# " + clientNumber + ": " + e);
         } catch (InterruptedException e) {
			LogManager.log("Error waiting for output");
			e.printStackTrace();
		} finally {
             try {
                 socket.close();
             } catch (IOException e) {
            	 LogManager.log("Couldn't close a socket, what's going on?");
             }
             LogManager.log("Connection with client# " + clientNumber + " closed");
         }
     }
}



