import java.util.Arrays;

public class BankersAlgo {
    static int n = 5, r = 3; // Number of processes (P0, P1, P2, P3, P4)
    // Number of resource types
    static int[][] alloc = { {0, 1, 0}, {2, 0, 0}, {3, 0, 2}, {2, 1, 1}, {0, 0, 2} }; // Resources already allocated to each process.
    static int[][] max = { {7, 5, 3}, {3, 2, 2}, {9, 0, 2}, {2, 2, 2}, {4, 3, 3} }; // Maximum resources each process may need.
    static int[][] need = new int[n][r]; // need = max - alloc
    static int[] avail = {3, 3, 2}; // Total available resources in the system.
    static boolean[] finish = new boolean[n]; // keeps track of which processes have finished execution.
    static int[] safeSeq = new int[n]; // stores the safe sequence of process execution.
    
    public static void main(String[] args) {
        calculateNeed();
        if (isSafe()) {
            System.out.println("\nSystem is in a safe state!");
            System.out.println("Safe Sequence: " + Arrays.toString(Arrays.stream(safeSeq).mapToObj(i -> "P" + i).toArray()));
            System.out.println("\nThe system successfully executed all processes without deadlock.");
            System.out.print("The Safe Sequence found is: ");
            for (int i = 0; i < safeSeq.length; i++) {
                // System.out.print("P" + p + " -> ");
                System.out.print("P" + safeSeq[i]);
                if (i != safeSeq.length - 1) System.out.print(" -> ");
            }
            System.out.println(" -> End.");
        } else {
            System.out.println("\nSystem is NOT in a safe state!! Deadlock detected.");
        }
    }

    static void calculateNeed() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < r; j++) {
                need[i][j] = max[i][j] - alloc[i][j];
            }
        }
    }

    static boolean isSafe() {
        int[] work = Arrays.copyOf(avail, r); // Tracks available resources dynamically.
        boolean allocated; // Becomes true if any process is allocated resources in an iteration.
        int count = 0; // Number of processes successfully allocated resources.
        
        System.out.println("Initial Available Resources: " + Arrays.toString(avail));
        
        while (count < n) {
            allocated = false;
            for (int i = 0; i < n; i++) {
                // Allocate process resources
                if (!finish[i] && canAllocate(i, work)) {
                    System.out.println("\nP" + i + " executes and releases resources.");
                    System.out.println("P" + i + " needs " + Arrays.toString(need[i]) + " which is available " + Arrays.toString(work));

                    // Process execution: release resources
                    for (int j = 0; j < r; j++) {
                        work[j] += alloc[i][j]; // Release allocated resources
                    }

                    System.out.println("After execution, P" + i + " releases " + Arrays.toString(alloc[i]) + ", updating available resources to " + Arrays.toString(work));

                    safeSeq[count++] = i;
                    finish[i] = true;
                    allocated = true;
                    // System.out.println("P" + i + " executes and releases resources. Available: " + Arrays.toString(work));
                    break;
                }
            }
            if (!allocated) return false;  // Deadlock detected
        }
        return true;
    }

    static boolean canAllocate(int p, int[] work) {
        for (int j = 0; j < r; j++) {
            if (need[p][j] > work[j]) return false;
        }
        return true;
    }
}
