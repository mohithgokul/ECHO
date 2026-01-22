package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

   private static final String BASE_DIR =
        System.getProperty("user.dir") + File.separator + "users";

    // ---------- USER SETUP ----------
    public static void createUser(String username) throws IOException {

    File userDir = new File(BASE_DIR + File.separator + username);
    if (!userDir.exists()) {
        if (!userDir.mkdirs()) {
            throw new IOException("Failed to create user directory");
        }
    }

    File friends = new File(userDir, "friends.txt");
    if (!friends.exists()) friends.createNewFile();

    File requests = new File(userDir, "requests.txt");
    if (!requests.exists()) requests.createNewFile();

    File chatsDir = new File(userDir, "chats");
    if (!chatsDir.exists()) chatsDir.mkdirs();
}

    
    public static List<String> getFriends(String user) throws IOException {
        return readLines(new File(BASE_DIR + "/" + user + "/friends.txt"));
    }

    public static String formatFriendsWithIndex(String user, List<String> friends) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (String friend : friends) {
            sb.append(i++).append(". ").append(friend).append("\n");
        }
        return sb.toString();
    }

    public static String getFriendByIndex(String user, int index) throws IOException {
        List<String> friends = getFriends(user);
        if (index < 1 || index > friends.size()) return null;
        return friends.get(index - 1);
    }

   
    public static void sendMessage(String sender, String receiver, String message) throws IOException {

    if (!isFriend(sender, receiver)) {
    return; 


    createUser(sender);
    createUser(receiver);

    File senderChat = new File(BASE_DIR + "/" + sender + "/chats/" + receiver + ".txt");
    File receiverChat = new File(BASE_DIR + "/" + receiver + "/chats/" + sender + ".txt");

    writeLine(senderChat, "[you] " + message);
    writeLine(receiverChat, "[" + sender + "] " + message);
}


    public static String readChat(String user, String friend) throws IOException {
        File chatFile = new File(BASE_DIR + "/" + user + "/chats/" + friend + ".txt");
        if (!chatFile.exists()) return "No messages yet.";
        return readFile(chatFile);
    }


    public static void sendFriendRequest(String from, String to) throws IOException {

    if (from.equals(to)) return;

    createUser(from);
    createUser(to);

    File reqFile = new File(BASE_DIR + "/" + to + "/requests.txt");
    if (!reqFile.exists()) {
        reqFile.createNewFile();
    }

    
    if (fileContains(reqFile, from)) return;

    writeLine(reqFile, from);
}




    public static String listRequests(String user) throws IOException {
        File file = new File(BASE_DIR + "/" + user + "/requests.txt");
        return readFile(file);
    }

    public static String getRequestByIndex(String user, int index) throws IOException {
        List<String> reqs = readLines(new File(BASE_DIR + "/" + user + "/requests.txt"));
        if (index < 1 || index > reqs.size()) return null;
        return reqs.get(index - 1);
    }

    public static void acceptRequest(String user, String friend) throws IOException {
        addFriend(user, friend);
        addFriend(friend, user);
        removeLine(new File(BASE_DIR + "/" + user + "/requests.txt"), friend);
    }

    public static void rejectRequest(String user, String friend) throws IOException {
        removeLine(new File(BASE_DIR + "/" + user + "/requests.txt"), friend);
    }

   
    private static boolean isFriend(String u1, String u2) throws IOException {
    File friendsFile = new File("users/" + u1 + "/friends.txt");

    if (!friendsFile.exists()) {
        return false;
    }

    BufferedReader br = new BufferedReader(new FileReader(friendsFile));
    String line;

    while ((line = br.readLine()) != null) {
        if (line.equals(u2)) {
            br.close();
            return true;
        }
    }

    br.close();
    return false;
}


    private static void addFriend(String user, String friend) throws IOException {
        File file = new File(BASE_DIR + "/" + user + "/friends.txt");
        if (!fileContains(file, friend)) {
            writeLine(file, friend);
        }
    }

    private static void writeLine(File file, String line) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        fw.write(line + "\n");
        fw.close();
    }

    private static String readFile(File file) throws IOException {
        if (!file.exists()) return "No data available.";
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        if (sb.length() == 0) return "No data available.";
        return sb.toString();
    }

    private static List<String> readLines(File file) throws IOException {
        List<String> list = new ArrayList<>();
        if (!file.exists()) return list;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        br.close();
        return list;
    }

    private static boolean fileContains(File file, String value) throws IOException {
        if (!file.exists()) return false;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.equals(value)) {
                br.close();
                return true;
            }
        }
        br.close();
        return false;
    }

    private static void removeLine(File file, String value) throws IOException {
        List<String> lines = readLines(file);
        FileWriter fw = new FileWriter(file, false);
        for (String line : lines) {
            if (!line.equals(value)) {
                fw.write(line + "\n");
            }
        }
        fw.close();
    }
}
