package server;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {

    private static final String BASE_DIR = "users";

    public static void createUser(String username) throws IOException {
        File userDir = new File(BASE_DIR + "/" + username);
        if (!userDir.exists()) {
            userDir.mkdirs();
            new File(userDir, "inbox.txt").createNewFile();
            new File(userDir, "sent.txt").createNewFile();
        }
    }

    public static void sendMessage(String sender, String receiver, String message) throws IOException {
        createUser(receiver);

        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        String formatted =
                "FROM: " + sender + "\n" + "TO: " + receiver + "\n"+
                "TIME: " + time + "\n" +
                "MESSAGE:\n" + message + "\n" +
                "------------------\n";

        writeToFile(receiver + "/inbox.txt", formatted);
        writeToFile(sender + "/sent.txt", formatted);
    }

    public static String readInbox(String user) throws IOException {
        return readFile(user + "/inbox.txt");
    }

    public static String readSent(String user) throws IOException {
        return readFile(user + "/sent.txt");
    }

    private static void writeToFile(String path, String content) throws IOException {
        FileWriter fw = new FileWriter(BASE_DIR + "/" + path, true);
        fw.write(content);
        fw.close();
    }

    private static String readFile(String path) throws IOException {
        File file = new File(BASE_DIR + "/" + path);
        BufferedReader br = new BufferedReader(new FileReader(file));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();

        if (sb.length() == 0) return "NO_MESSAGES";
        return sb.toString();
    }
}
