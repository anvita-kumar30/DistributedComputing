import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class GroupSender {
    private static final String MULTICAST_GROUP = "230.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
            
            System.out.println("Enter messages to send (type 'exit' to quit):");
            while (true) {
                String message = scanner.nextLine();
                if ("exit".equalsIgnoreCase(message)) break;
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                socket.send(packet);
                System.out.println("Message sent: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
