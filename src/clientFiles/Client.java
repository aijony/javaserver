package clientFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import manager.LogManager;

/**
 * A simple Swing-based client for the server.
 * It has a main frame window with a text field for entering
 * strings and a textarea to see the results of capitalizing
 * them.
 * 
 * Credit goes to Loyola Marymount University CS department for 
 * the creation of the client's GUI code. Original code
 * found here: http://cs.lmu.edu/~ray/notes/javanetexamples/
 */
public class Client {

    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Client");
    private JTextField dataField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(20, 50);
    
    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the server.
     */
    public Client() {

        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");

        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
            	//output to server
                out.println(dataField.getText() + CookieClient.getCookie());
                   String response;
                   
                try {
                    response = decipher(in.readLine());
                    
                    
                } catch (IOException ex) {
                       response = "Error: " + ex;
                }
                
                //Later responses go out to text field
                messageArea.append(response + "\n");
                dataField.selectAll();
            }

           
        });
    }

    /*
     * Deciphers server input,
     * Checks if there is a cookie or if the client needs to be shut down.
    */
    private String decipher(String readLine) {
	
    	CookieClient.setCookie(readLine);
    	String input = CookieClient.removeCookie(readLine);
    	
    	if(!CookieClient.checkCookie(readLine)){
    		LogManager.log("Client missing cookie input");
    	}
    	
    	
    	
    	if (input.equals("shutdownclient")) {
            System.exit(0);
        }
    	return input;
    	
	} 
    
   

	/*
	 * Connects to the server and assigns the correct streams to 
	 * the GUI elements of the client.
     */
    
    public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
        String serverAddress = JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Capitalization Program",
            JOptionPane.QUESTION_MESSAGE);

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 9898);
        
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        
        // Consume the initial welcoming messages from the server
        
        for (int i = 0; i < 3; i++) {
            messageArea.append(decipher(in.readLine()) + "\n");
        }
    }

    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.pack();
        client.frame.setVisible(true);
        client.connectToServer();
    }
}

