import java.util.Scanner;

public class BullyElection {
    static int n;
    static int faulty;
    static int detector;
    
    public static int findCoordinator(int detector, int faulty, int n) {
        for (int node = detector; node <= n; node++) {
            if (node == faulty) continue;
            
            System.out.println("\n---- Node " + node + " sending ELECTION message ----");
            int oks = 0;
            
            for (int neighbor = node + 1; neighbor <= n; neighbor++) {
                if (neighbor != faulty) {
                    oks++;
                }
                System.out.println("ELECTION message sent to Node " + neighbor);
            }
            
            if (oks > 0) {
                System.out.println(oks + " (OKs) received by Node " + node);
            } else {
                System.out.println("Active higher priority process does NOT exist..");
                return node;
            }
        }
        return -1;
    }
    
    public static void bully(int detector, int faulty, int n) {
        System.out.println("\nThe Coordinator (Node " + faulty + ") has failed...");
        System.out.println("Node " + detector + " detected failure of coordinator...");
        System.out.println("Node " + detector + " initiating election process...");
        
        int newCoordinator = findCoordinator(detector, faulty, n);
        
        System.out.println("\n----- RESULT OF ELECTION PROCESS -----");
        System.out.println("Node " + newCoordinator + " elected as new coordinator !!");
        System.out.println("Node " + newCoordinator + " sending message to inform that it is elected as new coordinator...");
        
        for (int neighbor = 1; neighbor < newCoordinator; neighbor++) {
            System.out.println("Message sent to Node " + neighbor);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of nodes: ");
        n = scanner.nextInt();
        System.out.print("Enter faulty node (out of 1 to " + n + "): ");
        faulty = scanner.nextInt();
        System.out.print("Enter node that detected failure first: ");
        detector = scanner.nextInt();
        
        bully(detector, faulty, n);
        
        scanner.close();
    }
}
