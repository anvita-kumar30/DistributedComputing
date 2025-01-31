import java.util.*;

public class LamportClkSync {
    
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
        // Create nodes in the network
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        // Simulate event flow between nodes
        System.out.println("Initial Clocks:");
        System.out.println("Node 1 clock: " + node1.clock);
        System.out.println("Node 2 clock: " + node2.clock);
        System.out.println("Node 3 clock: " + node3.clock);
        System.out.println();

        // Node 1 sends a message to Node 2
        int messageFromNode1 = node1.sendMessage();
        System.out.println("Node 1 sends message with timestamp: " + messageFromNode1);
        
        // Node 2 receives the message and updates its clock
        node2.updateClock(messageFromNode1);
        System.out.println("Node 2 receives message and updates clock: " + node2.clock);

        // Node 2 sends a message to Node 3
        int messageFromNode2 = node2.sendMessage();
        System.out.println("Node 2 sends message with timestamp: " + messageFromNode2);
        
        // Node 3 receives the message and updates its clock
        node3.updateClock(messageFromNode2);
        System.out.println("Node 3 receives message and updates clock: " + node3.clock);

        // Node 1 sends another message to Node 3
        int messageFromNode1Again = node1.sendMessage();
        System.out.println("Node 1 sends another message with timestamp: " + messageFromNode1Again);
        
        // Node 3 receives the message and updates its clock
        node3.updateClock(messageFromNode1Again);
        System.out.println("Node 3 receives message and updates clock: " + node3.clock);

        // Final state of clocks
        System.out.println("\nFinal Clocks:");
        System.out.println("Node 1 clock: " + node1.clock);
        System.out.println("Node 2 clock: " + node2.clock);
        System.out.println("Node 3 clock: " + node3.clock);
    }
}
