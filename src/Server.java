import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // Define the port number where the server listens for incoming connections
    private static final int PORT = 1234;

    public static void main(String[] args){
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("--------------------------------");
            System.out.print("| ");
            System.out.println("Server started at port: " + PORT + " |");
            System.out.println("--------------------------------");

            System.out.println("-> Waiting for a client to connect...");

            // Continuously listen for new client connections
            while (true) {
                // Accept a new client connection
                try(Socket clientSocket = serverSocket.accept()) {
                    System.out.println("-> New client connected");
                    System.out.println();

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    // Process incoming messages from the client
                    String clientMessage;
                    while ((clientMessage = in.readLine()) != null ) {
                        System.out.println("+ Client : " + clientMessage);

                        // Send an acknowledgement back to the client
                        out.write("+ Server : Msg Received ");
                        out.newLine();
                        out.flush();

                        // If the client sends "EXIT", break out of the loop to disconnect
                        if (clientMessage.equalsIgnoreCase("EXIT")) {
                            System.out.println("-> Client requested disconnection.");
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Client connection error: " + e.getMessage());
                }

            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}