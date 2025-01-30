package IPC;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String args[]) {
        try {
            try (ServerSocket ss = new ServerSocket(7777)) {
                int limit = 0;
                System.out.println("Server connected");
                while (true) {
                    Socket s = ss.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    String str = (String) dis.readUTF();
                    System.out.println("\nMessage " + limit +  str);
                    limit++;
                    if (limit >= 5) {
                        System.out.println("Server shutting down...");
                        ss.close();
                        break; // Exit server loop
                    }
                    
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}