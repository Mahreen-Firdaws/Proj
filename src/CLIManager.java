import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Manages the command-line interface (CLI) for the Inventory Management System.
 * <p>
 * Provides a menu-driven interface for users to interact with the inventory,
 * including adding, viewing, updating, deleting, and searching for products.
 */
public class CLIManager {

    /**
     * Constructs a CLIManager using the standard input stream.
     * <p>
     * This constructor initializes the CLIManager with a Scanner that reads from
     * {@code System.in}.
     */
    public CLIManager() {
        this(new Scanner(System.in));
    }
    
    /**
     * Constructs a CLIManager with the specified Scanner for input.
     *
     * @param keyboard the Scanner to use for reading user input
     */
    public CLIManager(Scanner keyboard) {
        InventoryManager inventoryManager = new InventoryManager();
        
        boolean running = true;
        while (running) {
            System.out.println("|---------------------------------|");
            System.out.println("|   Inventory Management System   |");
            System.out.println("|---------------------------------|");
            System.out.println("| 1. Add Product                  |");
            System.out.println("| 2. View Products                |");
            System.out.println("| 3. Update Product               |");
            System.out.println("| 4. Delete Product               |");
            System.out.println("| 5. Search Product by ID         |");
            System.out.println("| 6. Exit                         |");
            System.out.println("|---------------------------------|");
            System.out.print("Enter your choice: ");

            try{
            int cliChoice = keyboard.nextInt();
            keyboard.nextLine();
            
            if (cliChoice < 1 || cliChoice > 6) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }
            
            switch (cliChoice) {
                    case 1 -> {
                        try {
                            System.out.print("Input Product ID:");
                            int prodID=keyboard.nextInt();
                            keyboard.nextLine();
                            System.out.print("Input Product Name:");
                            String prodName=keyboard.nextLine();
                            System.out.print("Input Product Price:");
                            double prodPrice=keyboard.nextDouble();
                            keyboard.nextLine();
                            
                            // Validate price
                            if (prodPrice <= 0) {
                                System.out.println("Failed to add product: Price must be greater than zero!");
                                continue;
                            }
                            if (prodPrice > 999999999999.99) {
                                System.out.println("Failed to add product: Price is too high! Maximum allowed: $999,999,999,999.99");
                                continue;
                            }
                            if (Double.isInfinite(prodPrice) || Double.isNaN(prodPrice)) {
                                System.out.println("Failed to add product: Price is too large!");
                                continue;
                            }
                            
                            System.out.print("Input Product Quantity:");
                            int prodQuantity=keyboard.nextInt();
                            keyboard.nextLine();
                            
                            // Validate quantity
                            if (prodQuantity < 0) {
                                System.out.println("Failed to add product: Quantity cannot be negative!");
                                continue;
                            }
                            
                            Product product = new Product(prodID, prodName, prodPrice, prodQuantity);
                            inventoryManager.addProduct(product);
                            System.out.println("Product added successfully.");
                        } catch (InputMismatchException e) {
                            System.out.println("Failed to add product: Invalid input. Quantity must be a valid integer (max 2,147,483,647).");
                            keyboard.nextLine(); // Clear invalid input
                        } catch (InvalidInputException e) {
                            System.out.println("Failed to add product: " + e.getMessage());
                        }
                    }
                    case 2 -> {
                        inventoryManager.viewProducts();
                    }
                    case 3 -> {
                        System.out.print("Enter Product ID to update: ");
                        int updateId = keyboard.nextInt();
                        keyboard.nextLine(); // Clear buffer
                        try {
                            inventoryManager.updateProduct(updateId, keyboard);
                        } catch (ProductNotFoundException e) {
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Failed to update product: " + e.getMessage());
                        }
                    }
                    case 4 -> {
                        System.out.print("Enter Product ID to delete: ");
                        int prodID = keyboard.nextInt();
                        keyboard.nextLine(); // Clear buffer
                        try{
                            inventoryManager.deleteProduct(prodID);
                        } catch (ProductNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 5 -> {
                        System.out.print("Enter Product ID to search: ");
                        int searchId = keyboard.nextInt();
                        keyboard.nextLine(); // Clear buffer
                        try {
                            inventoryManager.searchProduct(searchId);
                        } catch (ProductNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 6 -> {
                        System.out.println("Exiting program. Goodbye!");
                        // Do not close `keyboard` here because it may have been
                        // provided by the caller (e.g., `Main`). Closing a
                        // Scanner wrapping `System.in` here can cause
                        // IllegalStateException when other code tries to use it.
                        running = false;
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter the correct data type.");
                keyboard.nextLine(); // Clear invalid input
            } catch (java.util.NoSuchElementException e) {
                System.out.println("No input available. Please try again.");
                // Continue the loop to re-display the menu
            }
       }
    }
}
