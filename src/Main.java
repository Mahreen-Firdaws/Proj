


// Generate testing functions in Main class.
// Test every method in Product, PerishableProduct, and InventoryManager classes.
// Display results to verify correctness.
// Ensure edge cases are covered, such as expired products and inventory updates, deletions, and searches for non-existent products, negative quantities, and zero prices, negative prices.
// Follow best practices for code readability and organization.
// Use comments to explain each test case.
// Test cases should cover:
// - Creating products and perishable products
// - Displaying product information
// - Checking expiration status of perishable products
// - Adding, updating, deleting, and viewing products in inventory
// - Searching for products in inventory
// - Handling edge cases like expired products, negative quantities, zero and negative prices
// - Verifying that all methods work as intended
// - Ensuring that the output is clear and informative
// - Using assertions where appropriate to validate expected outcomes
// - Structuring the tests in a logical order for clarity
// - Including comments to explain the purpose of each test case
// - Ensuring that the tests are comprehensive and cover all aspects of the classes' functionality
// - Making sure that the tests are easy to read and understand for future maintenance
// - Running the tests to confirm that all functionalities are working as expected
// - Documenting any assumptions made during testing
// - Highlighting any potential issues or areas for improvement in the classes being tested
// - Providing a summary of the test results at the end of the testing process


public class Main {
    public static void main(String[] args) {
        // Test cases will be implemented here
        System.out.println("Starting tests...");
        TestInventoryManager.runAllTests();
        System.out.println("All tests completed.");
        
    }

}