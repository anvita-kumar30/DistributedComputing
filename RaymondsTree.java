import java.util.*;

public class RaymondsTree {
    static int n = 5; // 5 processes (0, 1, 2, 3, and 4)
    @SuppressWarnings("unchecked")
    static List<Integer>[] requestQueue = (List<Integer>[]) new ArrayList[n];
    static int[] holder = {0, 0, 0, 1, 1};  // Holder array: Stores the parent (or owner) of each process
    static int[] token = {1, 0, 0, 0, 0};  // Token status: Indicates which process holds the token (initially, process 0 has it).
    static int[][] adjMatrix = {
        {1, 0, 0, 0, 0},
        {1, 0, 0, 0, 0},
        {1, 0, 0, 0, 0},
        {0, 1, 0, 0, 0},
        {0, 1, 0, 0, 0}
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Initialize request queues
        for (int i = 0; i < n; i++) {
            requestQueue[i] = new ArrayList<>();
        }

        System.out.println("Raymond Tree-Based Mutual Exclusion Algorithm\n");
        System.out.println("Adjacency Matrix for spanning tree:");
        for (int[] row : adjMatrix) {
            System.out.println(Arrays.toString(row));
        }

        // Read the process that wants to enter CS
        System.out.print("\nEnter the process who wants to enter critical section (CS): ");
        int reqProcess = scanner.nextInt();

        int parent = findParent(reqProcess);
        while (token[reqProcess] != 1) {
            int child = requestQueue[parent].remove(0);  // Get the first request in queue
            holder[parent] = child;
            holder[child] = 0;
            token[parent] = 0;
            token[child] = 1;

            // Clear request queue of parent after transferring the token
            requestQueue[parent].clear();

            System.out.println("\nParent process " + parent + " has the token and sends it to " + child);
            System.out.println("Request Queue: " + Arrays.deepToString(requestQueue));

            parent = child;
        }

        if (token[parent] == 1 && !requestQueue[parent].isEmpty() && requestQueue[parent].get(0) == parent) {
            requestQueue[parent].remove(0);
            holder[parent] = parent;
            System.out.println("\nProcess " + parent + " enters the Critical Section.");
        }

        if (requestQueue[parent].isEmpty()) {
            System.out.println("\nRequest Queue of process " + parent + " is empty. Releasing Critical Section.");
        }

        System.out.println("\nHolder: " + Arrays.toString(holder));
        scanner.close();
    }

    private static int findParent(int reqProcess) {
        requestQueue[reqProcess].add(reqProcess);
        int parent = -1;

        for (int i = 0; i < n; i++) {
            if (adjMatrix[reqProcess][i] == 1) {
                parent = i;
                requestQueue[parent].add(reqProcess);
                break;
            }
        }

        System.out.println("\nProcess " + reqProcess + " sending request to Parent Process " + parent);
        System.out.println("Request queue: " + Arrays.deepToString(requestQueue));

        // Fix: Stop searching if direct parent has the token
        if (token[parent] == 1) {
            return parent;
        } else {
            return findParent(parent);
        }
    }
}
