import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    // Define the server host and port to connect to
    private static final int PORT = 1234;
    private static final String HOST = "localhost";

    public static void main(String[] args){
        try(Socket clientSocket = new Socket(HOST, PORT)){
            System.out.println("-> Connected to the server");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            Scanner scanner = new Scanner(System.in);
            System.out.println("Type your message");

            // Continuously read user input and send messages to the server
            while (true){
                String msgToSend = scanner.nextLine();

                // Send the user's message to the server
                out.write(msgToSend);
                out.newLine();
                out.flush();

                // Read and display the server's response
                String msgFromServer = in.readLine();
                if (msgFromServer == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }
                System.out.println(msgFromServer);

                // If the user types "EXIT", break the loop and disconnect
                if (msgToSend.equalsIgnoreCase("EXIT")){
                    break;
                }
            }
        }catch(IOException e){
            System.out.println("Client error: " + e.getMessage());
        }
    }
}