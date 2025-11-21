
import java.time.LocalDate;

// Generate testing functions in Main class.
// Test both PerishableProduct and Product classes.
// Ensure all methods work as expected.
// Validate expiration logic in PerishableProduct.
// Check inheritance and method overriding.
// Ensure static and instance variables behave correctly.
// Focus on edge cases for expiration dates.
// Ensure proper display of product information.
// Confirm no side effects between Product and PerishableProduct instances.
// Use LocalDate for date manipulations and comparisons.
// Ensure code adheres to Java best practices.
// Utilize JUnit or similar framework for structured testing.
// Document test cases and results clearly.
// Maintain code readability and organization.
// Keep Main class focused on testing logic.
// Avoid unnecessary complexity in test implementations.
// Ensure compatibility with Java 8 or higher for LocalDate usage.
// Follow standard Java naming conventions.
// Ensure all imports are correctly handled.
// Handle potential exceptions in date handling gracefully.
// Provide clear output for test results.
// Structure tests for maintainability and future extensions.
// Ensure no modifications to Product and PerishableProduct classes.
// Test all possible edge cases like null expiration dates and past/future dates, negative quantities, and zero prices, wrong product names, incorrect IDs, etc.
// Test default constructors and parameterized constructors.
// Test ExpirationDate handling in PerishableProduct thoroughly.
// Test InventoryManager interactions if needed.
// Test both valid and invalid data inputs.
// Test instance methods and overridden methods.
// Test inventory display functions.
// Test product addition and deletion if needed.
// test uniqueProductCount handling if needed.
// Test everything in inventory manager.

public class Main {
    public static void main(String[] args) {
        // Create Product instances
        Product product1 = new Product(1, "Laptop", 999.99, 10);
        Product product2 = new Product(2, "Smartphone", 499.99, 20);

        // Create PerishableProduct instances
        PerishableProduct perishable1 = new PerishableProduct(3, "Milk", 2.99, 30, LocalDate.now().plusDays(5));
        PerishableProduct perishable2 = new PerishableProduct(4, "Yogurt", 1.99, 15, LocalDate.now().minusDays(1)); // expired

        // Display product information
        System.out.println("Product 1 Info:");
        product1.displayProductInfo();
        System.out.println();

        System.out.println("Product 2 Info:");
        product2.displayProductInfo();
        System.out.println();

        System.out.println("Perishable Product 1 Info:");
        perishable1.displayProductInfo();
        System.out.println();

        System.out.println("Perishable Product 2 Info:");
        perishable2.displayProductInfo();
        System.out.println();

        // Test expiration logic
        System.out.println("Is Perishable Product 1 expired? " + perishable1.isExpired());
        System.out.println("Is Perishable Product 2 expired? " + perishable2.isExpired());

        // Create InventoryManager instance
        InventoryManager inventoryManager = new InventoryManager();
        // Add products to inventory
        inventoryManager.addProduct(product1);
        inventoryManager.addProduct(product2);
        inventoryManager.addProduct(perishable1);
        inventoryManager.addProduct(perishable2);
        // View all products in inventory
        System.out.println("\nInventory Products:");
        inventoryManager.viewProducts();
        // Update a product
        inventoryManager.updateProduct(1); // Update product1
        // Delete a product
        inventoryManager.deleteProduct(2); // Delete product2
        // View all products after update and delete
        System.out.println("\nInventory Products after update and delete:");
        inventoryManager.viewProducts();
        // Search for a product
        System.out.println("\nSearching for Product ID 3:");
        inventoryManager.searchProduct(3);
        System.out.println("\nSearching for Product ID 2:");
        inventoryManager.searchProduct(2);
        // End of tests
    }
}