import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Inventory Management System ===");
            System.out.println("1. Run Test Cases");
            System.out.println("2. Launch GUI");
            System.out.print("Choose an option (1 or 2): ");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1 -> {
                    System.out.println("\nStarting tests...");
                    TestInventoryManager.runAllTests();
                    System.out.println("All tests completed.");
                }
                case 2 -> {
                    System.out.println("\nLaunching GUI...");
                    // TODO: Launch your Swing GUI here
                    // Example: javax.swing.SwingUtilities.invokeLater(() -> {
                    //     new YourGUIClass().setVisible(true);
                    // });
                    System.out.println("GUI not yet implemented.");
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}