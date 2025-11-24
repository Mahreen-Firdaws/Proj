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
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Inventory Management System ===");
        System.out.println("1. Run Test Cases");
        System.out.println("2. Launch CLI");
        System.out.println("3. Launch GUI");
        System.out.print("Choose an option (1, 2, or 3): ");
        try {
        int choice = scanner.nextInt();
        scanner.nextLine();
        
            switch (choice) {
            case 1 -> {
                System.out.println("\nStarting tests...");
                TestInventoryManager.runAllTests();
                System.out.println("All tests completed.");
                System.out.println("\nThank you for using the Inventory Manager Solution!");
                scanner.close();
            }
            
            case 2 -> {
                System.out.println("\nLaunching CLI...");
                @SuppressWarnings("unused")
                CLIManager cli = new CLIManager(scanner);
            }
            
            case 3 -> {
                System.out.println("\nLaunching GUI...");
                scanner.close();
                javax.swing.SwingUtilities.invokeLater(() -> {
                    new InvManGUI().setVisible(true);
                });
            }
            default -> {
                System.out.println("Invalid choice.");
                scanner.close();
            }
        }    
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
            scanner.nextLine(); // Clear invalid input
            main(new String[] {}); // Restart main method
        } 
    }
}