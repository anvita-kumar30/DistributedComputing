import java.util.Scanner;

public class LoadBalancing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // User Input
        System.out.print("Enter number of servers: ");
        int numServers = sc.nextInt();
        System.out.print("Enter number of processes: ");
        int numProcesses = sc.nextInt();
        
        while (true) {
            printServerLoad(numServers, numProcesses);
            displayMenu();
            System.out.print("> ");
            int choice = sc.nextInt();
            int temp;

            switch (choice) {
                case 1:
                    System.out.print("Enter number of servers to be added: ");
                    temp = sc.nextInt();
                    if (temp > 0) numServers += temp;
                    else System.out.println("Invalid input! Enter a positive number.");
                    break;

                case 2:
                    System.out.print("Enter number of servers to be removed: ");
                    temp = sc.nextInt();
                    if (temp > 0 && numServers - temp > 0) numServers -= temp;
                    else System.out.println("Cannot remove servers! At least one server must remain.");
                    break;

                case 3:
                    System.out.print("Enter number of processes to be added: ");
                    temp = sc.nextInt();
                    if (temp > 0) numProcesses += temp;
                    else System.out.println("Invalid input! Enter a positive number.");
                    break;

                case 4:
                    System.out.print("Enter number of processes to be removed: ");
                    temp = sc.nextInt();
                    if (temp > 0 && numProcesses - temp >= 0) numProcesses -= temp;
                    else System.out.println("Cannot remove more processes than available.");
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
                    break;
            }
        }
    }

    static void printServerLoad(int numServers, int numProcesses) {
        System.out.println("\nCurrent Load Distribution:");
        int processesPerServer = numProcesses / numServers;
        int extraProcesses = numProcesses % numServers;

        for (int i = 0; i < numServers; i++) {
            int assignedProcesses = (i < extraProcesses) ? (processesPerServer + 1) : processesPerServer;
            System.out.println("Server " + (i + 1) + " has " + assignedProcesses + " processes.");
        }
    }

    static void displayMenu() {
        System.out.println("\nOptions:");
        System.out.println("1. Add Server");
        System.out.println("2. Remove Server");
        System.out.println("3. Add Processes");
        System.out.println("4. Remove Processes");
        System.out.println("5. Exit");
    }
}
