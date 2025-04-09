import java.util.*;

public class LB {
    private static class Server {
        private final int id;
        private int capacity;
        private int currentLoad;
        private Queue<Task> taskQueue;


        public Server(int id, int capacity) {
            this.id = id;
            this.capacity = capacity;
            this.currentLoad = 0;
            this.taskQueue = new LinkedList<>();
        }


        public boolean addTask(Task task) {
            if (currentLoad + task.getSize() <= capacity) {
                currentLoad += task.getSize();
                taskQueue.add(task);
                return true;
            }
            return false;
        }


        public void processTask() {
            if (!taskQueue.isEmpty()) {
                Task task = taskQueue.peek();
                task.decrementTimeRemaining();
                if (task.getTimeRemaining() <= 0) {
                    taskQueue.poll();
                    currentLoad -= task.getSize();
                }
            }
        }


        public int getCurrentLoad() {
            return currentLoad;
        }


        public int getCapacity() {
            return capacity;
        }


        public int getId() {
            return id;
        }
    }


    private static class Task {
        private static int nextId = 0;
        private final int id;
        private final int size;
        private int timeRemaining;


        public Task(int size, int processingTime) {
            this.id = nextId++;
            this.size = size;
            this.timeRemaining = processingTime;
        }


        public int getSize() {
            return size;
        }


        public int getTimeRemaining() {
            return timeRemaining;
        }


        public void decrementTimeRemaining() {
            if (timeRemaining > 0) {
                timeRemaining--;
            }
        }
    }


    private List<Server> servers;
    private int simulationTime;
    private Random random;


    public LB(int numServers, int serverCapacity, int simulationTime) {
        servers = new ArrayList<>();
        random = new Random();
        this.simulationTime = simulationTime;
        for (int i = 0; i < numServers; i++) {
            servers.add(new Server(i, serverCapacity));
        }
    }


    public void roundRobinLoadBalancing(List<Task> tasks) {
        int serverIndex = 0;
        for (Task task : tasks) {
            boolean assigned = false;
            for (int i = 0; i < servers.size(); i++) {
                if (servers.get(serverIndex).addTask(task)) {
                    System.out.println("Task " + task.id + " assigned to Server " + servers.get(serverIndex).getId());
                    assigned = true;
                    break;
                }
                serverIndex = (serverIndex + 1) % servers.size();
            }
            if (!assigned) {
                System.out.println("Task " + task.id + " rejected (no capacity)");
            }
        }
    }


    public void leastLoadedBalancing(List<Task> tasks) {
        for (Task task : tasks) {
            Server leastLoadedServer = servers.get(0);
            for (Server server : servers) {
                if (server.getCurrentLoad() < leastLoadedServer.getCurrentLoad()) {
                    leastLoadedServer = server;
                }
            }
            if (!leastLoadedServer.addTask(task)) {
                System.out.println("Task " + task.id + " rejected (no capacity)");
            } else {
                System.out.println("Task " + task.id + " assigned to Server " + leastLoadedServer.getId());
            }
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of servers: ");
        int numServers = scanner.nextInt();
        System.out.print("Enter server capacity: ");
        int serverCapacity = scanner.nextInt();
        System.out.print("Enter simulation time: ");
        int simulationTime = scanner.nextInt();
        System.out.print("Enter number of tasks: ");
        int numTasks = scanner.nextInt();


        LB simulation = new LB(numServers, serverCapacity, simulationTime);
        List<Task> tasks = new ArrayList<>();


        for (int i = 0; i < numTasks; i++) {
            System.out.print("Enter size and processing time for Task " + i + ": ");
            int size = scanner.nextInt();
            int processingTime = scanner.nextInt();
            tasks.add(new Task(size, processingTime));
        }


        System.out.println("Choose Load Balancing Strategy: 1 - Round Robin, 2 - Least Loaded");
        int choice = scanner.nextInt();
        if (choice == 1) {
            simulation.roundRobinLoadBalancing(tasks);
        } else {
            simulation.leastLoadedBalancing(tasks);
        }
    }
}


