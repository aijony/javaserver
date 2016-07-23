package serverFiles;


import java.net.ServerSocket;



public class Server {

    /*
     * Creates a socket that is ready to listen to any clients. Once a client connects
     * a new thread will be created that will deal with that client.
     * 
     * Credit goes to Loyola Marymount University CS department for the use of their socket/server code
     * as a base for this program. Most of the socket connection process is their code. Original code
     * found here: http://cs.lmu.edu/~ray/notes/javanetexamples/
     */
    public static void main(String[] args) throws Exception {
        System.out.println("The server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                new ServerSocketThread(listener.accept(), clientNumber++).start();;
            }
        } finally {
            listener.close();
        }
    }
    
    }
    
