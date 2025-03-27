import java.util.*;

class Process {
    int cpuDemand; // Processing power needed
    int memoryUsage; // Memory required
    double instructionMixFactor; // Complexity of instructions

    public Process(int cpuDemand, int memoryUsage, double instructionMixFactor) {
        this.cpuDemand = cpuDemand;
        this.memoryUsage = memoryUsage;
        this.instructionMixFactor = instructionMixFactor;
    }

    public double getWorkload() {
        return (cpuDemand * instructionMixFactor) + (memoryUsage * 0.5); // Weighted formula
    }
}

class Node {
    int id;
    double processingSpeed; // GHz or arbitrary unit
    String architecture; // Type of architecture (e.g., "x86", "ARM")
    List<Process> processes;

    public Node(int id, double processingSpeed, String architecture) {
        this.id = id;
        this.processingSpeed = processingSpeed;
        this.architecture = architecture;
        this.processes = new ArrayList<>();
    }

    public double computeWorkload() {
        double totalWorkload = 0;
        for (Process p : processes) {
            totalWorkload += p.getWorkload();
        }
        return totalWorkload / processingSpeed; // Normalize by speed
    }

    public void addProcess(Process p) {
        processes.add(p);
    }

    public void removeProcess() {
        if (!processes.isEmpty()) {
            processes.remove(0);
        }
    }
}

public class LoadBalancingAlgo {
    private static Map<Integer, Node> nodes = new HashMap<>();
    private static int nodeId = 0;
    private static Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeNodes();

        while (true) {
            System.out.println("\nCurrent Workload Distribution:");
            for (Node node : nodes.values()) {
                System.out.println("Node " + node.id + " Workload: " + node.computeWorkload());
            }

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

    private static void initializeNodes() {
        nodes.put(0, new Node(0, 3.5, "x86")); // ID, speed (GHz), architecture
        nodes.put(1, new Node(1, 2.8, "ARM"));
        nodes.put(2, new Node(2, 4.0, "x86"));
        nodeId = 3;
    }

    private static void addProcess() {
        Node leastLoaded = Collections.min(nodes.values(), Comparator.comparingDouble(Node::computeWorkload));
        Process newProcess = new Process(random.nextInt(10) + 1, random.nextInt(500) + 100, random.nextDouble() + 0.5);
        leastLoaded.addProcess(newProcess);
        System.out.println("Process added to Node " + leastLoaded.id);
    }

    private static void removeProcess() {
        Node mostLoaded = Collections.max(nodes.values(), Comparator.comparingDouble(Node::computeWorkload));
        mostLoaded.removeProcess();
        System.out.println("Process removed from Node " + mostLoaded.id);
    }

    private static void addNode() {
        nodes.put(nodeId, new Node(nodeId, random.nextDouble() * 3 + 2, "ARM"));
        System.out.println("Node " + nodeId + " added.");
        nodeId++;
    }

    private static void removeNode() {
        if (nodes.size() > 1) {
            int nodeToRemove = nodes.keySet().iterator().next();
            Node removedNode = nodes.remove(nodeToRemove);

            for (Process p : removedNode.processes) {
                addProcess();
            }
            System.out.println("Node " + nodeToRemove + " removed, processes redistributed.");
        } else {
            System.out.println("Cannot remove the last node.");
        }
    }
}
