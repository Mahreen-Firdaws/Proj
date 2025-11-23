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
        
        int choice = scanner.nextInt();
        
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
                // Don't close scanner - CLIManager will create its own and close it
                @SuppressWarnings("unused")
                CLIManager cli = new CLIManager();
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
        
    }
}