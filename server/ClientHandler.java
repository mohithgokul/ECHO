package server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
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
                System.out.println("SERVER RECEIVED: [" + command + "]");


               
                if (command.equals("LIST_FRIENDS")) {
                    List<String> friends = FileManager.getFriends(username);

                    if (friends.isEmpty()) {
                        sendBlock("No friends added.\nAdd friends to chat.");
                    } else {
                        sendBlock(FileManager.formatFriendsWithIndex(username, friends));
                    }
                }

               
                else if (command.startsWith("OPEN_CHAT")) {
                    int index = Integer.parseInt(command.split(" ")[1]);
                    String friend = FileManager.getFriendByIndex(username, index);

                    if (friend == null) {
                        sendBlock("Invalid selection.");
                    } else {
                        sendBlock(FileManager.readChat(username, friend));
                    }
                }

               
else if (command.startsWith("SEND_REQUEST")) {
    try {
        String[] parts = command.split(" ", 2);

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            sendBlock("ERROR: Please provide a username.");
        } else {
            String target = parts[1].trim();
            FileManager.sendFriendRequest(username, target);
            sendBlock("Friend request sent to " + target);
        }
    } catch (Exception e) {
        e.printStackTrace();
        sendBlock("SERVER_ERROR: Could not send request.");
    }
}

else if (command.startsWith("SEND")) {
    String[] parts = command.split(" ", 3);
    int index = Integer.parseInt(parts[1]);
    String message = parts[2];

    String friend = FileManager.getFriendByIndex(username, index);
    if (friend == null) {
        sendBlock("Invalid friend selection.");
    } else {
        FileManager.sendMessage(username, friend, message);
        sendBlock("Message sent");
    }
}

                else if (command.equals("LIST_REQUESTS")) {
                    sendBlock(FileManager.listRequests(username));
                }

                else if (command.startsWith("ACCEPT_REQUEST")) {
                    int index = Integer.parseInt(command.split(" ")[1]);
                    String requester = FileManager.getRequestByIndex(username, index);

                    if (requester == null) {
                        sendBlock("Invalid request.");
                    } else {
                        FileManager.acceptRequest(username, requester);
                        sendBlock("Friend request accepted.");
                    }
                }

                
                else if (command.startsWith("REJECT_REQUEST")) {
                    int index = Integer.parseInt(command.split(" ")[1]);
                    String requester = FileManager.getRequestByIndex(username, index);

                    if (requester == null) {
                        sendBlock("Invalid request.");
                    } else {
                        FileManager.rejectRequest(username, requester);
                        sendBlock("Friend request rejected.");
                    }
                }

            


               
                else if (command.equals("LOGOUT")) {
                    sendBlock("LOGGED_OUT");
                    break;
                }

                
                else {
                    sendBlock("Unknown command");
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
