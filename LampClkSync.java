import java.util.Scanner;

public class LampClkSync {
    // A node in the network
    public static class Node {
        public int id;
        public int clock;

        // Initialize the node with an ID and its logical clock
        Node(int id) {
            this.id = id;
            this.clock = 0; // Initial logical clock value
        }

        // Increment the node's logical clock
        public void incrementClock() {
            clock++;
        }

        // Update the clock based on received message's timestamp
        public void updateClock(int receivedTime) {
            clock = Math.max(clock, receivedTime) + 1;
        }

        // Send message with the current logical clock
        public int sendMessage() {
            incrementClock();
            return clock;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of nodes: ");
        int numNodes = scanner.nextInt();
        Node[] nodes = new Node[numNodes];

        // Initialize nodes
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(i + 1);
        }

        System.out.println("\nInitial Clocks:");
        for (Node node : nodes) {
            System.out.println("Node " + node.id + " clock: " + node.clock);
        }
        System.out.println();

        while (true) {
            System.out.print("Enter sender node (1 to " + numNodes + ", 0 to exit): ");
            int senderId = scanner.nextInt();
            if (senderId == 0) break;

            System.out.print("Enter receiver node (1 to " + numNodes + "): ");
            int receiverId = scanner.nextInt();

            if (senderId < 1 || senderId > numNodes || receiverId < 1 || receiverId > numNodes || senderId == receiverId) {
                System.out.println("Invalid sender or receiver. Try again.");
                continue;
            }

            // Message passing
            int messageTimestamp = nodes[senderId - 1].sendMessage();
            System.out.println("Node " + senderId + " sends message with timestamp: " + messageTimestamp);

            nodes[receiverId - 1].updateClock(messageTimestamp);
            System.out.println("Node " + receiverId + " receives message and updates clock: " + nodes[receiverId - 1].clock);
        }

        // Final state of clocks
        System.out.println("\nFinal Clocks:");
        for (Node node : nodes) {
            System.out.println("Node " + node.id + " clock: " + node.clock);
        }

        scanner.close();
    }
}
