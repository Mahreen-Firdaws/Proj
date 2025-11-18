
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

public class Main {
    public static void main(String[] args) {
        // Test Product class
        Product product1 = new Product(1, "Apple", 0.5, 100);
        product1.displayProductInfo();
        System.out.println();

        Product product2 = new Product(-1, "Beepple", -0.5, -100);
        product2.displayProductInfo();
        System.out.println();

        Product product3 = new Product();
        product3.displayProductInfo();
        System.out.println();

        // Test PerishableProduct class
        PerishableProduct perishable1 = new PerishableProduct(2, "Milk", 1.5, 50, LocalDate.now().plusDays(5));
        perishable1.displayProductInfo();
        System.out.println();

        PerishableProduct perishable2 = new PerishableProduct(3, "Yogurt", 0.8, 30, LocalDate.now().minusDays(1));
        perishable2.displayProductInfo();
        System.out.println();

        PerishableProduct perishable3 = new PerishableProduct(4, "Cheese", 2.0, 20, null);
        perishable3.displayProductInfo();
        System.out.println();

        PerishableProduct perishable4 = new PerishableProduct(-4, "Thing", -2.0, -20, null);
        perishable4.displayProductInfo();
        System.out.println();


       
    }
}