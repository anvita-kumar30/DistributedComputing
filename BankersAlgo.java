import java.util.Arrays;

public class BankersAlgo {
    static int n = 5, r = 3;
    static int[][] alloc = { {0, 1, 0}, {2, 0, 0}, {3, 0, 2}, {2, 1, 1}, {0, 0, 2} };
    static int[][] max = { {7, 5, 3}, {3, 2, 2}, {9, 0, 2}, {2, 2, 2}, {4, 3, 3} };
    static int[][] need = new int[n][r];
    static int[] avail = {3, 3, 2};
    static boolean[] finish = new boolean[n];
    static int[] safeSeq = new int[n];
    
    public static void main(String[] args) {
        calculateNeed();
        if (isSafe()) {
            System.out.println("\nSystem is in a safe state!");
            System.out.print("Safe Sequence: ");
            for (int p : safeSeq) {
                System.out.print("P" + p + " ");
            }
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
                    for (int j = 0; j < r; j++) {
                        work[j] += alloc[i][j];
                    }
                    safeSeq[count++] = i;
                    finish[i] = true;
                    allocated = true;
                    System.out.println("P" + i + " executes and releases resources. Available: " + Arrays.toString(work));
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
