package server;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {

        System.out.println("Starting Chat Server...");

        try (ServerSocket serverSocket = new ServerSocket(5000)) {

            System.out.println("Chat Server started on port 5000");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
