package IPC;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String args[]) {
        try {
            try (ServerSocket ss = new ServerSocket(7777)) {
                int limit = 0;
                System.out.println("server connected");
                while (true) {
                    Socket s = ss.accept();
                    System.out.println("Client connected...");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    String str = (String) dis.readUTF();
                    System.out.println("\nMessage " + limit + str);
                    limit++;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}