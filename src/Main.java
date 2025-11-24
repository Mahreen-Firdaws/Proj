import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main - Entry point for the Inventory Management System.
 * Displays console menu to choose between test mode or GUI mode.
 */
public class Main {
    /**
     * Main method - displays menu and launches chosen mode.
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Inventory Management System ===");
            System.out.println("1. Run Test Cases");
            System.out.println("2. Launch CLI");
            System.out.println("3. Launch GUI");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1, 2, 3, or 4): ");
            boolean running = true;
            while (running) {
                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (choice) {
                        case 1 -> {
                            System.out.println("\nStarting tests...");
                            TestInventoryManager.runAllTests();
                            System.out.println("All tests completed.");
                            System.out.println("\nThank you for using the Inventory Manager Solution!");
                            running = false;
                        }
                        
                        case 2 -> {
                            System.out.println("\nLaunching CLI...");
                            @SuppressWarnings("unused")
                                    CLIManager cli = new CLIManager(scanner);
                            running = false;
                        }
                        case 3 -> {
                            System.out.println("\nLaunching GUI...");
                            javax.swing.SwingUtilities.invokeLater(() -> {
                                new InvManGUI().setVisible(true);
                            });
                            running = false;
                        }
                        case 4 -> {
                            System.out.println("Thank you for using the Inventory Manager Solution.");
                            running = false;
                        }
                        default -> {
                            System.out.println("Invalid choice.");
                        }
                        
                    }
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter a number (1, 2, 3, or 4).");
                    scanner.nextLine(); // Clear invalid input
                }
            }
        }
    }
}
