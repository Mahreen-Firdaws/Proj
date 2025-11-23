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
        try (Scanner scanner = new Scanner(System.in)) { // Auto-close scanner
            System.out.println("=== Inventory Management System ===");
            System.out.println("1. Run Test Cases");
            System.out.println("2. Launch CLI");
            System.out.println("3. Launch GUI");
            System.out.print("Choose an option (1, 2, or 3): ");
            
            int choice = scanner.nextInt(); // Read user choice (assumed valid integer)
            
            switch (choice) { // Switch based on user choice
                case 1 -> { // Run tests
                    System.out.println("\nStarting tests...");
                    TestInventoryManager.runAllTests(); // Call the static method to run all tests
                    System.out.println("All tests completed.");
                    System.out.println("\nThank you for using the Inventory Manager Solution!");
                }
                
                case 2 -> { // Launch CLI
                    System.out.println("\nLaunching CLI...");
                    new CLIManager(); // Instantiate CLIManager to start CLI, ignore the warning
                }
                case 3 -> { // Launch GUI
                    System.out.println("\nLaunching GUI...");
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        new InvManGUI().setVisible(true); // Create and show GUI
                    });
                }
                default -> System.out.println("Invalid choice."); // Handle invalid input
            }
        }
        
    }
}