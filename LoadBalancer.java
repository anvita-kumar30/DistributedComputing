import java.util.*;

public class LoadBalancer {
    private Map<Integer, Integer> nodeLoads; // Node -> Load count
    private Map<String, Integer> processToNode; // Process name -> Node
    private int nodeCount; // Number of nodes

    public LoadBalancer(int initialNodes) {
        this.nodeLoads = new HashMap<>();
        this.processToNode = new HashMap<>();
        this.nodeCount = initialNodes;
        for (int i = 0; i < nodeCount; i++) {
            nodeLoads.put(i, 0); // Initialize all nodes with 0 load
        }
    }

    public void addProcess(String processName) {
        int leastLoadedNode = getLeastLoadedNode();
        processToNode.put(processName, leastLoadedNode);
        nodeLoads.put(leastLoadedNode, nodeLoads.get(leastLoadedNode) + 1);
        System.out.println("Process '" + processName + "' assigned to Node " + leastLoadedNode);
    }

    public void removeProcess(String processName) {
        if (processToNode.containsKey(processName)) {
            int node = processToNode.remove(processName);
            nodeLoads.put(node, nodeLoads.get(node) - 1);
            System.out.println("Process '" + processName + "' removed from Node " + node);
        } else {
            System.out.println("Process '" + processName + "' not found.");
        }
    }

    public void addNode() {
        nodeLoads.put(nodeCount++, 0);
        System.out.println("New Node " + (nodeCount - 1) + " added.");
    }

    public void removeNode(int nodeId) {
        if (nodeLoads.containsKey(nodeId)) {
            List<String> processesToReassign = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : processToNode.entrySet()) {
                if (entry.getValue() == nodeId) {
                    processesToReassign.add(entry.getKey());
                }
            }

            nodeLoads.remove(nodeId);
            System.out.println("Node " + nodeId + " removed. Reallocating processes...");

            for (String process : processesToReassign) {
                addProcess(process);
            }
        } else {
            System.out.println("Node " + nodeId + " does not exist.");
        }
    }

    public void displaySystemState() {
        System.out.println("Current Load Distribution: " + nodeLoads);
        System.out.println("Process Allocations: " + processToNode);
    }

    private int getLeastLoadedNode() {
        return nodeLoads.entrySet().stream()
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .get()
                .getKey();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoadBalancer lb = new LoadBalancer(3); // Start with 3 nodes

        while (true) {
            System.out.println("\n1. Add Process\n2. Remove Process\n3. Add Node\n4. Remove Node\n5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter process name: ");
                    String processName = scanner.nextLine();
                    lb.addProcess(processName);
                    break;
                case 2:
                    System.out.print("Enter process name to remove: ");
                    String removeProcess = scanner.nextLine();
                    lb.removeProcess(removeProcess);
                    break;
                case 3:
                    lb.addNode();
                    break;
                case 4:
                    System.out.print("Enter node ID to remove: ");
                    int nodeId = scanner.nextInt();
                    lb.removeNode(nodeId);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
            lb.displaySystemState();
        }
    }
}