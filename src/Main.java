import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("|---------------------------------|");
        System.out.println("| Cases to run:                   |");
        System.out.println("|---------------------------------|");
        System.out.println("| 1. Test Cases                   |");
        System.out.println("| 2. CLI Interface                |");
        System.out.println("| 3. GUI Interface                |");
        System.out.println("| 4. Exit                         |");
        System.out.println("|---------------------------------|");
        System.out.print("Input which case to run;");
        int choice = keyboard.nextInt();
        while (true){
            if (choice < 1 || choice > 4){
            System.out.print("Invalid input. Please enter a number between 1 and 4: ");
            choice = keyboard.nextInt();
            } else { 
                if (choice == 1){
            // Test cases will be implemented here 
            System.out.println("Starting tests...");
            // Run all tests in TestInventoryManager - call the method to run all tests
            TestInventoryManager.runAllTests();
            // Final summary
            System.out.println("All tests completed.");
            // == END ==
        } else if (choice == 2){
            InventoryManager inventoryManager = new InventoryManager(); // Create InventoryManager instance
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
            int cliChoice=keyboard.nextInt();
            while (true){
                if (cliChoice < 1 || cliChoice > 6){
                    System.out.print("Invalid input. Please enter a number between 1 and 6: ");
                    cliChoice = keyboard.nextInt();
                }  else {
                if (cliChoice == 1){
                    try {
                    System.out.print("Input Product ID:");
                    int prodID=keyboard.nextInt();
                    System.out.print("Input Product Name:");
                    String prodName=keyboard.next();
                    System.out.print("Input Product Price:");
                    double prodPrice=keyboard.nextDouble();
                    System.out.print("Input Product Quantity:");
                    int prodQuantity=keyboard.nextInt();
                    Product product = new Product(prodID, prodName, prodPrice, prodQuantity);
                    inventoryManager.addProduct(product);
                    System.out.println("Product added successfully.");
                    System.out.print("Enter your next choice: ");
                    cliChoice = keyboard.nextInt();
                } catch (InvalidInputException e) {
                    System.out.println("Failed to add product: " + e.getMessage());
                    System.out.print("Enter your next choice: ");
                    cliChoice = keyboard.nextInt();
                }
                } else if (cliChoice == 2){
                    inventoryManager.viewProducts();
                    System.out.print("Enter your next choice: ");
                    cliChoice = keyboard.nextInt();
                } else if (cliChoice == 3){
                    System.out.print("Enter Product ID to update: ");
                    int updateId = keyboard.nextInt();
                    try {
                        inventoryManager.updateProduct(updateId);
                        System.out.print("Enter your next choice: ");
                        cliChoice = keyboard.nextInt();
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                        System.out.print("Enter your next choice: ");
                        cliChoice = keyboard.nextInt();
                    }
                } else if (cliChoice == 4){
                    System.out.print("Enter Product ID to delete: ");
                    int prodID = keyboard.nextInt();
                    try{ 
                        inventoryManager.deleteProduct(prodID);
                        System.out.print("Enter your next choice: ");
                        cliChoice = keyboard.nextInt();
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                        System.out.print("Enter your next choice: ");
                        cliChoice = keyboard.nextInt();
                    }
                } else if (cliChoice == 5){
                    System.out.print("Enter Product ID to search: ");
                    System.out.print("Enter your next choice: ");
                    int searchId = keyboard.nextInt();
                    try {
                        inventoryManager.searchProduct(searchId);
                        System.out.print("Enter your next choice: ");
                        cliChoice = keyboard.nextInt();
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                        System.out.print("Enter your next choice: ");
                        cliChoice = keyboard.nextInt();
                    }
                }
                else if (cliChoice == 6){
                    System.out.println("Exiting program. Goodbye!");
                    keyboard.close();
                    System.exit(0);
                    break;
                }
            } }
        } else if (choice == 3){
            // GUI Interface will be implemented here
            
        } else if (choice == 4){
            System.out.println("Exiting program. Goodbye!");
            keyboard.close();
            System.exit(0);
        }
    } } 
    }
}