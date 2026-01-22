package client;

import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter server IP: ");
            String serverIP = sc.nextLine();

            System.out.print("Enter server port: ");
            int port = Integer.parseInt(sc.nextLine());

            Socket socket = new Socket(serverIP, port);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // ---- LOGIN ----
            readBlock(in);

            System.out.print("Enter username: ");
            String username = sc.nextLine();
            out.println(username);

            readBlock(in);

            
            while (true) {

                System.out.println("---------------------------------------------------------------");
                System.out.println("                          ECHO");
                System.out.println("---------------------------------------------------------------");
                System.out.println("1. INBOX   2. PENDING REQUESTS   3. SEND FRIEND REQUEST   4. LOGOUT");
                System.out.print("\nOPTION: ");

                String option = sc.nextLine();

                switch (option) {
                    case "1":
                        openInbox(sc, out, in);
                        break;

                    case "2":
                        openPendingRequests(sc, out, in);
                        break;

                    case "3":
                        sendFriendRequest(sc, out, in);
                        break;

                    case "4":
                        out.println("LOGOUT");
                        readBlock(in);
                        socket.close();
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid option.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
    private static void readBlock(BufferedReader in) throws IOException {
        String line;

        while (!(line = in.readLine()).equals("BEGIN")) {}

        while (!(line = in.readLine()).equals("END")) {
            System.out.println(line);
        }
    }

    
    private static void openInbox(
            Scanner sc, PrintWriter out, BufferedReader in) throws IOException {

        out.println("LIST_FRIENDS");
        readBlock(in);

        System.out.println("Enter friend number to open chat");
        System.out.println("0. Back");
        System.out.print("> ");

        String choice = sc.nextLine();
        if (choice.equals("0")) return;

        openChat(choice, sc, out, in);
    }


    private static void openChat(
            String friendIndex,
            Scanner sc,
            PrintWriter out,
            BufferedReader in
    ) throws IOException {

        out.println("OPEN_CHAT " + friendIndex);
        readBlock(in);

       while (true) {
    System.out.print("> ");
    String msg = sc.nextLine();

   
    if (msg.equals("0")) return;

  
    if (msg.trim().isEmpty()) {
        out.println("OPEN_CHAT " + friendIndex);
        readBlock(in);
        continue;
    }

    out.println("SEND " + friendIndex + " " + msg);
    readBlock(in);
}

    }

   
    private static void openPendingRequests(
            Scanner sc, PrintWriter out, BufferedReader in) throws IOException {

        out.println("LIST_REQUESTS");
        readBlock(in);

        System.out.println("Enter number to ACCEPT");
        System.out.println("R<number> to REJECT");
        System.out.println("0. Back");
        System.out.print("> ");

        String input = sc.nextLine();
        if (input.equals("0")) return;

        if (input.startsWith("R")) {
            out.println("REJECT_REQUEST " + input.substring(1));
        } else {
            out.println("ACCEPT_REQUEST " + input);
        }

        readBlock(in);
    }

    
    private static void sendFriendRequest(
            Scanner sc, PrintWriter out, BufferedReader in) throws IOException {

        System.out.print("Enter username: ");
        String user = sc.nextLine();

        out.println("SEND_REQUEST " + user);
        readBlock(in);
    }
}
