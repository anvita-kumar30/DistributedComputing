import java.util.*;

public class BankersAlgorithm {
    static int n, r; // Number of processes and resource types
    static int[][] alloc, max, need;
    static int[] avail;
    static boolean[] finish;
    static int[] safeSeq;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Taking user input for number of processes and resources
        System.out.print("Enter the number of processes: ");
        n = scanner.nextInt();
        System.out.print("Enter the number of resource types: ");
        r = scanner.nextInt();
        
        // Initializing arrays
        alloc = new int[n][r];
        max = new int[n][r];
        need = new int[n][r];
        avail = new int[r];
        finish = new boolean[n];
        safeSeq = new int[n];

        // Taking input for allocation matrix
        System.out.println("Enter allocation matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < r; j++) {
                alloc[i][j] = scanner.nextInt();
            }
        }

        // Taking input for max matrix
        System.out.println("Enter max matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < r; j++) {
                max[i][j] = scanner.nextInt();
            }
        }

        // Taking input for available resources
        System.out.println("Enter available resources:");
        for (int j = 0; j < r; j++) {
            avail[j] = scanner.nextInt();
        }

        scanner.close();

        // Calculate need matrix
        calculateNeed();
        
        if (isSafe()) {
            System.out.println("\nSystem is in a safe state!");
            System.out.println("Safe Sequence: " + Arrays.toString(Arrays.stream(safeSeq).mapToObj(i -> "P" + i).toArray()));
            System.out.print("The Safe Sequence found is: ");
            for (int i = 0; i < safeSeq.length; i++) {
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
        int[] work = Arrays.copyOf(avail, r);
        boolean allocated;
        int count = 0;
        
        System.out.println("Initial Available Resources: " + Arrays.toString(avail));
        
        while (count < n) {
            allocated = false;
            for (int i = 0; i < n; i++) {
                if (!finish[i] && canAllocate(i, work)) {
                    System.out.println("\nP" + i + " executes and releases resources.");
                    System.out.println("P" + i + " needs " + Arrays.toString(need[i]) + " which is available " + Arrays.toString(work));

                    for (int j = 0; j < r; j++) {
                        work[j] += alloc[i][j];
                    }

                    System.out.println("After execution, P" + i + " releases " + Arrays.toString(alloc[i]) + ", updating available resources to " + Arrays.toString(work));
                    
                    safeSeq[count++] = i;
                    finish[i] = true;
                    allocated = true;
                    break;
                }
            }
            if (!allocated) return false;
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
