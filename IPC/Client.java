package IPC;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String args[]) {
        try {
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println("Client connected. Enter a message:\n");
                while (true) {
                        Socket s = new Socket("localhost", 7777);
                        DataOutputStream dis = new DataOutputStream(s.getOutputStream());
                        String str = sc.nextLine();
                        dis.writeUTF(" Client 1 has sent " + str);
                        dis.flush();
                        dis.close();
                        s.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}