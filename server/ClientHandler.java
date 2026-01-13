package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())),
                    true
            );

            sendBlock("ENTER_USERNAME");
            username = in.readLine();

            FileManager.createUser(username);
            sendBlock("LOGIN_SUCCESS");

            String command;
            while ((command = in.readLine()) != null) {

                if (command.startsWith("SEND")) {
                    String[] parts = command.split(" ", 3);
                    FileManager.sendMessage(username, parts[1], parts[2]);
                    sendBlock("MESSAGE_SENT");
                }

                else if (command.equals("INBOX")) {
                    sendBlock(FileManager.readInbox(username));
                }

                else if (command.equals("SENT")) {
                    sendBlock(FileManager.readSent(username));
                }

                else if (command.equals("LOGOUT")) {
                    sendBlock("LOGGED_OUT");
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Client disconnected: " + username);
        }
    }

    private void sendBlock(String msg) {
        out.println("BEGIN");
        for (String line : msg.split("\n")) {
            out.println(line);
        }
        out.println("END");
        out.flush();
    }
}
