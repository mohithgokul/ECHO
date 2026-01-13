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

            // read ENTER_USERNAME
            readBlock(in);

            System.out.print("Enter username: ");
            String username = sc.nextLine();
            out.println(username);

            // read LOGIN_SUCCESS
            readBlock(in);

            while (true) {
                System.out.println("\n1. Send Message");
                System.out.println("2. View Inbox");
                System.out.println("3. View Sent");
                System.out.println("4. Logout");
                System.out.print("Choice: ");

                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    System.out.print("To: ");
                    String to = sc.nextLine();
                    System.out.print("Message: ");
                    String msg = sc.nextLine();
                    out.println("SEND " + to + " " + msg);
                    readBlock(in);
                }
                else if (choice == 2) {
                    out.println("INBOX");
                    readBlock(in);
                }
                else if (choice == 3) {
                    out.println("SENT");
                    readBlock(in);
                }
                else if (choice == 4) {
                    out.println("LOGOUT");
                    readBlock(in);
                    break;
                }
            }

            socket.close();
            sc.close();

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
}
