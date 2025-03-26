
import java.util.*;
import java.io.*;

class Server {
    int load;
    int maxLoad;
    int id;
    List<Integer> processes;

    Server (int maxLoad, int id) {
        this.id = id;
        this.maxLoad = maxLoad;
    }
}

public class LoadBalancingAlgo {
    static int currentServer = 0;
    static int uuid = 0;
    static List<Server> servers = new ArrayList<>();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void printServerLoads(List<Server> servers) {
        for (Server s : servers) {
            System.out.println(s.id + " -> "+ s.load + " -> "+ s.maxLoad);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nCurrent Load Distribution: ");
            printServerLoads(servers);
            System.out.println("\n1. Add a Process");
            System.out.println("2. Remove a Process");
            System.out.println("3. Add a Node");
            System.out.println("4. Remove a Node");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    addProcess();
                    break;
                case 2:
                    removeProcess();
                    break;
                case 3:
                    addNode();
                    break;
                case 4:
                    removeNode();
                    break;
                case 5:
                    System.out.println("Exiting Load Balancer Simulation.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addProcess(List<Server> servers) {
        if (servers.get(currentServer).maxLoad >= servers.get(currentServer).load + 1) {
            System.out.println("Process assigned to server "+ currentServer);
            servers.get(currentServer).load += 1;
            currentServer = (currentServer + 1) % servers.size();
        }

    }

    private static void removeProcess() throws IOException {
        System.out.println("Enter id of process to remove: ");
        int id = Integer.parseInt(br.readLine());
        int serverId = -1;
        for (Server server : servers) {
            for (int pid : server.processes) {
                if (pid == id) serverId = server.id;
            }
        }
        servers.get(serverId).processes.remove(id);
    }

    private static void addNode() throws IOException {
        int maxLoad = Integer.parseInt(br.readLine());
        Server server = new Server(maxLoad, uuid);
        uuid++;
        servers.add(server);
        System.out.println("Node " + (uuid - 1) + " added with max load " + maxLoad + ".");
    }

    private static void removeNode() {
        if (nodeLoads.size() > 1) {
            int nodeToRemove = nodeLoads.keySet().iterator().next();
            int processesToRedistribute = nodeLoads.get(nodeToRemove);
            nodeLoads.remove(nodeToRemove);

            for (int i = 0; i < processesToRedistribute; i++) {
                addProcess();
            }
            System.out.println("Node " + nodeToRemove + " removed, processes redistributed.");
        } else {
            System.out.println("Cannot remove the last node.");
        }
    }
}
