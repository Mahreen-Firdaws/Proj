import java.util.Scanner;
public class CLIManager {

    public CLIManager() {
    
        Scanner keyboard = new Scanner(System.in);
        InventoryManager inventoryManager = new InventoryManager(); // Create InventoryManager instance
        
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
            int cliChoice = keyboard.nextInt();
            
            if (cliChoice < 1 || cliChoice > 6) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }
            
            switch (cliChoice) {
                    case 1 -> {
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
                        try {
                            inventoryManager.updateProduct(updateId);
                        } catch (ProductNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 4 -> {
                        System.out.print("Enter Product ID to delete: ");
                        int prodID = keyboard.nextInt();
                        try{
                            inventoryManager.deleteProduct(prodID);
                        } catch (ProductNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 5 -> {
                        System.out.print("Enter Product ID to search: ");
                        int searchId = keyboard.nextInt();
                        try {
                            inventoryManager.searchProduct(searchId);
                        } catch (ProductNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 6 -> {
                        System.out.println("Exiting program. Goodbye!");
                        keyboard.close();
                        running = false;
                    }
                }
            }
       }

}
