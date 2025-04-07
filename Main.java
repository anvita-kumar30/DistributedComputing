import java.util.ArrayList;
import java.util.List;

// Class representing a simple Round-Robin Load Balancer
class exp7 {
    private List<String> servers; // List to store server names
    private int currentIndex; // Keeps track of the next server to be assigned a request

    // Constructor to initialize the load balancer with a list of servers
    public exp7(List<String> servers) {
        this.servers = new ArrayList<>(servers); // Creating a copy of the input server list
        this.currentIndex = 0; // Start with the first server
    }

    // Method to get the next server using a Round-Robin strategy
    public String getNextServer() {
        String nextServer = servers.get(currentIndex); // Get the current server
        currentIndex = (currentIndex + 1) % servers.size(); // Move to the next server (circular rotation)
        return nextServer; // Return the selected server
    }
}

// Main class to test the Load Balancer
public class Main {
    public static void main(String[] args) {
        // Step 1: Create a list of available servers
        List<String> serverList = new ArrayList<>();
        serverList.add("Server1");
        serverList.add("Server2");
        serverList.add("Server3");

        // Step 2: Initialize the Load Balancer with the server list
        exp7 loadBalancer = new exp7(serverList);

        // Step 3: Simulate 10 incoming requests and route them to servers
        for (int i = 0; i < 10; i++) {
            String nextServer = loadBalancer.getNextServer(); // Get the next server
            System.out.println("Request " + (i + 1) + ": Routed to " + nextServer); // Print the server handling the request
        }
    }
}