import java.time.LocalDate;

/**
 * Author: Halcionne (Martin Khlopin)
 * TestInventoryManager - Comprehensive automated test suite for Inventory Management System.
 * Tests all functionality of Product, PerishableProduct, and InventoryManager classes.
 * Runs automated tests and reports pass/fail for each test.
 */
public class TestInventoryManager {
    private static int testsPassed = 0; // Count of passed tests
    private static int testsFailed = 0; // Count of failed tests

    /**
     * Main test runner method - executes all test suites in order
     * 
     * Test execution order:
     * 1. Product class tests
     * 2. PerishableProduct class tests
     * 3. InventoryManager class tests
     * 4. Final test summary
     */
    public static void runAllTests() {
        // Visual separator for test suite start
        System.out.println("=".repeat(80)); 
        System.out.println("STARTING COMPREHENSIVE TEST SUITE"); 
        System.out.println("=".repeat(80)); 
        System.out.println(); 

        // ========================================================================
        // PRODUCT CLASS TESTS
        // ========================================================================
        testProductCreation();
        testProductGettersSetters();
        testProductValidation();
        testProductDisplay();

        // ========================================================================
        // PERISHABLE PRODUCT CLASS TESTS
        // ========================================================================
        testPerishableProductCreation();
        testPerishableProductExpiration();
        testPerishableProductDisplay();

        // ========================================================================
        // INVENTORY MANAGER CLASS TESTS
        // ========================================================================
        testInvManAddProduct();
        testInvManViewProducts();
        testInvManSearchProduct();
        testInvManDeleteProduct();
        testInvManEdgeCases();
        testInvFullException();

        // ========================================================================
        // PRINT FINAL TEST SUMMARY
        // ========================================================================
        printTestSummary();
    }

    // ============================================================================
    // PRODUCT CLASS TESTS
    // ============================================================================

    /**
     * Test: Product Creation
     * 
     * Tests the Product class constructors with various inputs:
     * - Valid product with all parameters
     * - Default constructor with no parameters
     * - Product with zero quantity (edge case)
     * 
     * Validates that product fields are initialized correctly.
     */
    @SuppressWarnings("UseSpecificCatch")
    private static void testProductCreation() {
        // Visual separator for testProductCreation section
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: Product Creation");
        System.out.println("-".repeat(80));

        // Test 1: Valid product creation
        // Purpose: Verify that a Product object can be created with valid parameters
        // Expected: Product should be created successfully with correct values stored
        try {
            Product validLaptopProduct = new Product(1, "Laptop", 999.99, 10); // Create product with ID=1, name="Laptop", price=999.99, quantity=10
            // Verify all fields were set correctly by calling getters
            if (validLaptopProduct.getProductId() == 1 && validLaptopProduct.getProductName().equals("Laptop")
                && validLaptopProduct.getPrice() == 999.99 && validLaptopProduct.getQuantity() == 10) {
                testPass("Valid product creation"); 
            } else {
                testFail("Valid product creation - incorrect values");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions (InvalidInputException should NOT be thrown here)
            testFail("Valid product creation threw exception: " + caughtException.getMessage());
        }

        // Test 2: Default constructor
        // Purpose: Verify that the default constructor initializes fields to default values
        // Expected: Product should be created with default values (ID=0, Name="Unnamed Product", Price=0.0, Quantity=0)
        try {
            Product defaultProduct = new Product();
            if (defaultProduct.getProductId() == 0 && defaultProduct.getProductName().equals("Unnamed Product")
                && defaultProduct.getPrice() == 0.0 && defaultProduct.getQuantity() == 0) {
                testPass("Default product constructor");
            } else {
                testFail("Default product constructor - incorrect values");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("Default product constructor threw exception: " + caughtException.getMessage());
        }

        // Test 3: Zero quantity (valid edge case)
        // Purpose: Verify that a Product can be created with zero quantity
        // Expected: Product should be created successfully with quantity set to 0
        try {
            Product zeroQtyMouse = new Product(3, "Mouse", 25.50, 0);
            if (zeroQtyMouse.getQuantity() == 0) {
                testPass("Product creation with zero quantity");
            } else {
                testFail("Product creation with zero quantity");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions (zero quantity should be allowed)
            testFail("Zero quantity threw exception: " + caughtException.getMessage());
        }
    }

    /**
     * Test: Product Getters and Setters
     * 
     * Tests all getter and setter methods in Product class:
     * - setProductName() and getProductName()
     * - setPrice() and getPrice() with valid values
     * - setQuantity() and getQuantity() with valid values
     * 
     * Validates that values are stored and retrieved correctly.
     */
    @SuppressWarnings("UseSpecificCatch")
    private static void testProductGettersSetters() {
        // Visual separator for testProductGettersSetters section
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: Product Getters and Setters");
        System.out.println("-".repeat(80));

        try {
            Product testKeyboardProduct = new Product(10, "Keyboard", 75.00, 15); // Create initial product for testing

            // Test 1: setProductName and getProductName
            // Purpose: Verify that product name can be updated and retrieved correctly
            // Expected: Name should be updated to new value
            testKeyboardProduct.setProductName("Mechanical Keyboard"); // Change name from "Keyboard" to "Mechanical Keyboard"
            if (testKeyboardProduct.getProductName().equals("Mechanical Keyboard")) {
                testPass("setProductName and getProductName");
            } else {
                testFail("setProductName and getProductName");
            }

            // Test 2: setPrice with valid value
            // Purpose: Verify that price can be updated with a valid positive value
            // Expected: Price should be updated without exception
            testKeyboardProduct.setPrice(85.00); // Change price from 75.00 to 85.00
            if (testKeyboardProduct.getPrice() == 85.00) {
                testPass("setPrice with valid value");
            } else {
                testFail("setPrice with valid value");
            }

            // Test 3: setQuantity with valid value
            // Purpose: Verify that quantity can be updated with a valid non-negative value
            // Expected: Quantity should be updated without exception
            testKeyboardProduct.setQuantity(20); // Change quantity from 15 to 20
            if (testKeyboardProduct.getQuantity() == 20) {
                testPass("setQuantity with valid value");
            } else {
                testFail("setQuantity with valid value");
            }

        } catch (Exception caughtException) { // Catch any unexpected exceptions (no exceptions should be thrown with valid values)
            testFail("Getters/Setters threw unexpected exception: " + caughtException.getMessage());
        }
    }

    /**
     * Test: Product Validation (Edge Cases)
     * 
     * Tests input validation for Product class:
     * - Constructor rejects negative prices
     * - Constructor rejects zero prices
     * - Constructor rejects negative quantities
     * - setPrice() rejects negative values
     * - setPrice() rejects zero values
     * - setQuantity() rejects negative values
     * 
     * Validates that InvalidInputException is thrown for invalid inputs.
     */
    private static void testProductValidation() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: Product Validation (Edge Cases)");
        System.out.println("-".repeat(80));

        // Test 1: Negative price in constructor
        // Purpose: Verify that the constructor rejects negative prices
        // Expected: InvalidInputException should be thrown (price must be > 0)
        try {
            @SuppressWarnings("unused")
            Product invalid = new Product(1, "Invalid", -50.0, 10);
            testFail("Constructor should reject negative price");
        } catch (InvalidInputException caughtException) {
            testPass("Constructor rejects negative price");
        } catch (Exception wrongException) { 
            testFail("Wrong exception type for negative price: " + wrongException.getClass().getName());
        }

        // Test 2: Zero price in constructor
        // Purpose: Verify that the constructor rejects zero prices
        // Expected: InvalidInputException should be thrown (price must be > 0, not >= 0)
        try {
            @SuppressWarnings("unused")
            Product zeroPrice = new Product(2, "Invalid", 0.0, 10);
            testFail("Constructor should reject zero price");
        } catch (InvalidInputException caughtException) {
            testPass("Constructor rejects zero price");
        } catch (Exception wrongException) { 
            testFail("Wrong exception type for zero price: " + wrongException.getClass().getName());
        }

        // Test 3: Negative quantity in constructor
        // Purpose: Verify that the constructor rejects negative quantities
        // Expected: InvalidInputException should be thrown (quantity must be >= 0)
        try {
            @SuppressWarnings("unused")
            Product negQty = new Product(3, "Invalid", 50.0, -5);
            testFail("Constructor should reject negative quantity");
        } catch (InvalidInputException caughInputException) {
            testPass("Constructor rejects negative quantity");
        } catch (Exception wrongException) { 
            testFail("Wrong exception type for negative quantity: " + wrongException.getClass().getName());
        }

        // Test 4: Negative product ID in constructor
        // Purpose: Verify that the constructor rejects negative product IDs
        // Expected: InvalidInputException should be thrown (ID must be >= 0)
        try {
            @SuppressWarnings("unused")
            Product negId = new Product(-1, "Invalid", 50.0, 10);
            testFail("Constructor should reject negative product ID");
        } catch (InvalidInputException invalidInputException) {
            testPass("Constructor rejects negative product ID");
        } catch (Exception wrongException) { // Wrong exception type thrown
            testFail("Wrong exception type for negative product ID: " + wrongException.getClass().getName());
        }

        // Test 5: Negative price via setter
        // Purpose: Verify that setPrice() rejects negative values after object creation
        // Expected: InvalidInputException should be thrown
        try {
            Product validProductForSetPrice = new Product(4, "Test", 100.0, 5); // Create valid product first
            validProductForSetPrice.setPrice(-25.0); // Attempt to set negative price
            testFail("setPrice should reject negative price"); // If no exception thrown, test fails
        } catch (InvalidInputException validationException) { // Expected exception - validation is working correctly
            testPass("setPrice rejects negative price");
        } catch (Exception wrongException) { // Wrong exception type thrown
            testFail("Wrong exception for setPrice negative: " + wrongException.getClass().getName());
        }

        // Test 6: Zero price via setter
        // Purpose: Verify that setPrice() rejects zero values after object creation
        // Expected: InvalidInputException should be thrown
        try {
            Product validProductForZeroPriceTest = new Product(5, "Test", 100.0, 5); // Create valid product first
            validProductForZeroPriceTest.setPrice(0.0); // Attempt to set zero price
            testFail("setPrice should reject zero price"); // If no exception thrown, test fails
        } catch (InvalidInputException validationException) { // Expected exception - validation is working correctly
            testPass("setPrice rejects zero price");
        } catch (Exception e) {
            testFail("Wrong exception for setPrice zero: " + e.getClass().getName());
        }

        // Test 7: Negative quantity via setter
        // Purpose: Verify that setQuantity() rejects negative values after object creation
        // Expected: InvalidInputException should be thrown
        try {
            Product testProduct3 = new Product(6, "Test", 100.0, 5);
            testProduct3.setQuantity(-10);
            testFail("setQuantity should reject negative quantity");
        } catch (InvalidInputException e) {
            testPass("setQuantity rejects negative quantity");
        } catch (Exception wrongException) { // Wrong exception type thrown
            testFail("Wrong exception for setQuantity negative: " + wrongException.getClass().getName());
        }
    }

    /**
     * Test: Product Display
     * 
     * Tests the displayProductInfo() method:
     * - Verifies that product information is printed to console
     * - Checks for proper formatting of output
     * 
     * Expected output: Product ID, Name, Price, Quantity
     */
    @SuppressWarnings("UseSpecificCatch")
    private static void testProductDisplay() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: Product Display");
        System.out.println("-".repeat(80));
        // Test 1: displayProductInfo output
        // Purpose: Verify that displayProductInfo() prints product details correctly
        // Expected: Product ID, Name, Price, and Quantity should be printed to console
        try {
            Product monitor = new Product(100, "Monitor", 299.99, 8);
            System.out.println("Expected output:");
            monitor.displayProductInfo();
            testPass("Product displayProductInfo executed");
        } catch (Exception caughtException) { // Catch any unexpected exceptions during display
            testFail("displayProductInfo threw exception: " + caughtException.getMessage());
        }
    }

    // ============================================================================
    // PERISHABLE PRODUCT CLASS TESTS
    // ============================================================================

    /**
     * Test: PerishableProduct Creation
     * 
     * Tests PerishableProduct constructors with various inputs:
     * - Creation with specific expiration date
     * - Creation with null expiration date
     * - Creation using 4-parameter constructor (no expiration)
     * 
     * Validates proper use of Optional<LocalDate> for expiration dates.
     */
    @SuppressWarnings("UseSpecificCatch")
    private static void testPerishableProductCreation() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: PerishableProduct Creation");
        System.out.println("-".repeat(80));

        // Test 1: Create with expiration date
        // Purpose: Verify PerishableProduct can be created with a specific expiration date
        // Expected: Product should be created with expiration date stored in Optional
        try {
            LocalDate expirationDateForMilk = LocalDate.of(2025, 12, 31); // Set expiration to Dec 31, 2025
            PerishableProduct freshMilkProduct = new PerishableProduct(200, "Milk", 4.99, 50, expirationDateForMilk);
            // Verify all fields including expiration date
            if (freshMilkProduct.getProductId() == 200 && freshMilkProduct.getProductName().equals("Milk")
                && freshMilkProduct.getExpirationDate().isPresent() // Optional should contain a date
                && freshMilkProduct.getExpirationDate().get().equals(expirationDateForMilk)) { // Date should match what we set
                testPass("PerishableProduct creation with expiration date");
            } else {
                testFail("PerishableProduct values incorrect");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("PerishableProduct creation threw exception: " + caughtException.getMessage());
        }

        // Test 2: Create without expiration date (null)
        // Purpose: Verify PerishableProduct can be created with null expiration (non-perishable items)
        // Expected: getExpirationDate() should return empty Optional
        try {
            PerishableProduct cannedGoodsProduct = new PerishableProduct(201, "Canned Goods", 3.50, 100, null); // Pass null for expiration
            if (cannedGoodsProduct.getExpirationDate().isEmpty()) { // Optional should be empty when null passed
                testPass("PerishableProduct creation with null expiration date");
            } else {
                testFail("PerishableProduct should have empty Optional for null date");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("PerishableProduct with null date threw exception: " + caughtException.getMessage());
        }

        // Test 3: Create with no expiration constructor (4-parameter version)
        // Purpose: Verify 4-parameter constructor initializes with no expiration date
        // Expected: getExpirationDate() should return empty Optional
        try {
            PerishableProduct cheeseProduct = new PerishableProduct(202, "Cheese", 6.99, 20); // Use 4-param constructor
            if (cheeseProduct.getExpirationDate().isEmpty()) { // Should default to empty Optional
                testPass("PerishableProduct creation without expiration (4-param constructor)");
            } else {
                testFail("PerishableProduct should have empty Optional");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("PerishableProduct 4-param constructor threw exception: " + caughtException.getMessage());
        }
    }

    /**
     * Test: PerishableProduct Expiration Logic
     * 
     * Tests the expiration checking functionality:
     * - isExpired() returns true for past dates
     * - isExpired() returns false for future dates
     * - isExpired() returns false when no expiration date set
     * - setExpirationDate() properly updates the expiration date
     * 
     * Validates date comparison logic using LocalDate.
     */
    @SuppressWarnings("UseSpecificCatch")
    private static void testPerishableProductExpiration() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: PerishableProduct Expiration Logic");
        System.out.println("-".repeat(80));

        // Test 1: Expired product (past date)
        // Purpose: Verify isExpired() returns true when expiration date is in the past
        // Expected: isExpired() should return true (current date is after expiration)
        try {
            LocalDate pastExpirationDate = LocalDate.of(2020, 1, 1); // Set date far in the past
            PerishableProduct expiredMilkProduct = new PerishableProduct(300, "Old Milk", 4.99, 10, pastExpirationDate);
            if (expiredMilkProduct.isExpired()) { // Should be true since 2020 < 2025
                testPass("isExpired returns true for past date");
            } else {
                testFail("isExpired should return true for past date");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("Expired product test threw exception: " + caughtException.getMessage());
        }

        // Test 2: Not expired product (future date)
        // Purpose: Verify isExpired() returns false when expiration date is in the future
        // Expected: isExpired() should return false (current date is before expiration)
        try {
            LocalDate futureExpirationDate = LocalDate.of(2030, 12, 31); // Set date in the future
            PerishableProduct nonExpiredMilkProduct = new PerishableProduct(301, "Fresh Milk", 5.99, 25, futureExpirationDate);
            if (!nonExpiredMilkProduct.isExpired()) { // Should be false since 2025 < 2030
                testPass("isExpired returns false for future date");
            } else {
                testFail("isExpired should return false for future date");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("Non-expired product test threw exception: " + caughtException.getMessage());
        }

        // Test 3: Product with no expiration date
        // Purpose: Verify isExpired() returns false when no expiration date is set
        // Expected: isExpired() should return false (products without expiration never expire)
        try {
            PerishableProduct honeyProduct = new PerishableProduct(302, "Honey", 8.99, 15, null); // No expiration date
            if (!honeyProduct.isExpired()) { // Should be false for products without expiration
                testPass("isExpired returns false for no expiration date");
            } else {
                testFail("isExpired should return false when no expiration date");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("No expiration date test threw exception: " + caughtException.getMessage());
        }

        // Test 4: setExpirationDate
        // Purpose: Verify that expiration date can be updated after creation
        // Expected: getExpirationDate() should return the new date set via setter
        try {
            PerishableProduct yogurtProduct = new PerishableProduct(303, "Yogurt", 3.50, 30); // Create without expiration
            LocalDate updatedExpirationDate = LocalDate.of(2026, 6, 15); // Define new expiration date
            yogurtProduct.setExpirationDate(updatedExpirationDate); // Update expiration date using setter
            if (yogurtProduct.getExpirationDate().isPresent() && yogurtProduct.getExpirationDate().get().equals(updatedExpirationDate)) {
                testPass("setExpirationDate updates date correctly");
            } else {
                testFail("setExpirationDate failed to update");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("setExpirationDate threw exception: " + caughtException.getMessage());
        }
    }

    /**
     * Test: PerishableProduct Display
     * 
     * Tests the overridden displayProductInfo() method:
     * - Verifies that base Product information is displayed
     * - Verifies that expiration date is displayed
     * - Verifies that expiration status (isExpired) is displayed
     * 
     * Expected output: Product info + Expiration Date + Is Expired status
     */
    @SuppressWarnings("UseSpecificCatch")
    private static void testPerishableProductDisplay() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: PerishableProduct Display");
        System.out.println("-".repeat(80));

        // Purpose: Verify that PerishableProduct's displayProductInfo() includes expiration info
        // Expected: Should display Product fields PLUS expiration date and expired status
        try {
            LocalDate expirationDateForEggs = LocalDate.of(2025, 12, 25); // Christmas 2025
            PerishableProduct eggsProduct = new PerishableProduct(400, "Eggs", 3.99, 60, expirationDateForEggs);
            System.out.println("Expected output:");
            eggsProduct.displayProductInfo(); // Should print Product info + expiration date + isExpired status
            testPass("PerishableProduct displayProductInfo executed");
        } catch (Exception caughtException) { // Catch any unexpected exceptions during display
            testFail("displayProductInfo threw exception: " + caughtException.getMessage());
        }
    }

    // ============================================================================
    // INVENTORY MANAGER CLASS TESTS
    // ============================================================================

    /**
     * Test: InventoryManager Add Product
     * 
     * Tests the addProduct() method with various scenarios:
     * - Adding a single valid product
     * - Adding multiple products sequentially
     * - Adding null product (should throw InvalidInputException)
     * - Adding PerishableProduct (polymorphism test)
     * 
     * Validates proper inventory insertion and exception handling.
     */
    @SuppressWarnings("UseSpecificCatch")
    private static void testInvManAddProduct() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: InventoryManager Add Product");
        System.out.println("-".repeat(80));

        // Test 1: Add valid product
        // Purpose: Verify that a valid product can be added to inventory
        // Expected: Product should be added successfully without exceptions
        try {
            InventoryManager invManAddTest = new InventoryManager(); // Create new inventory (capacity = 50)
            Product tabletProduct = new Product(1, "Tablet", 399.99, 5);
            invManAddTest.addProduct(tabletProduct); // Add product to inventory
            testPass("Add valid product to inventory");
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("Add valid product threw exception: " + caughtException.getMessage());
        }

        // Test 2: Add multiple products
        // Purpose: Verify that multiple products can be added sequentially
        // Expected: All products should be added without conflicts or exceptions
        try {
            InventoryManager invManMultipleProducts = new InventoryManager();
            Product firstTestProduct = new Product(1, "Item1", 10.0, 5);
            Product secondTestProduct = new Product(2, "Item2", 20.0, 10);
            Product thirdTestProduct = new Product(3, "Item3", 30.0, 15);
            invManMultipleProducts.addProduct(firstTestProduct); // Add first product
            invManMultipleProducts.addProduct(secondTestProduct); // Add second product
            invManMultipleProducts.addProduct(thirdTestProduct); // Add third product
            testPass("Add multiple products to inventory");
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("Add multiple products threw exception: " + caughtException.getMessage());
        }

        // Test 3: Add null product
        // Purpose: Verify that null products are rejected
        // Expected: InvalidInputException should be thrown (cannot add null)
        try {
            InventoryManager invManNullTest = new InventoryManager();
            invManNullTest.addProduct(null); // Attempt to add null product
            testFail("Should throw InvalidInputException for null product"); // If no exception, test fails
        } catch (InvalidInputException validationException) { // Expected exception - validation is working
            testPass("Add null product throws InvalidInputException");
        } catch (Exception wrongException) { // Wrong exception type thrown
            testFail("Wrong exception for null product: " + wrongException.getClass().getName());
        }

        // Test 4: Add perishable product
        // Purpose: Verify that PerishableProduct (subclass of Product) can be added
        // Expected: Perishable product should be added successfully (polymorphism)
        try {
            InventoryManager invManPerishableTest = new InventoryManager();
            PerishableProduct breadProduct = new PerishableProduct(100, "Bread", 2.99, 40, LocalDate.of(2025, 12, 1));
            invManPerishableTest.addProduct(breadProduct); // Add perishable product (should work due to inheritance)
            testPass("Add perishable product to inventory");
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("Add perishable product threw exception: " + caughtException.getMessage());
        }
    }

    /**
     * Test: InventoryManager View Products
     * 
     * Tests the viewProducts() method:
     * - Displays all products in populated inventory
     * - Handles empty inventory gracefully
     * 
     * Validates that all products are displayed with correct information.
     */
    @SuppressWarnings("UseSpecificCatch")
    private static void testInvManViewProducts() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: InventoryManager View Products");
        System.out.println("-".repeat(80));

        // Test 1: View products in populated inventory
        // Purpose: Verify that viewProducts() displays all products in inventory
        // Expected: All added products should be displayed with their information
        try {
            InventoryManager invManViewTest = new InventoryManager();
            Product phoneProduct = new Product(10, "Phone", 699.99, 15);
            Product chargerProduct = new Product(11, "Charger", 19.99, 50);
            PerishableProduct juiceProduct = new PerishableProduct(12, "Juice", 3.50, 30, LocalDate.of(2025, 12, 15));
            
            invManViewTest.addProduct(phoneProduct);   // Add first product
            invManViewTest.addProduct(chargerProduct); // Add second product
            invManViewTest.addProduct(juiceProduct);   // Add third product (perishable)
            
            System.out.println("Expected output (3 products):");
            invManViewTest.viewProducts(); // Should display all 3 products with their details
            testPass("viewProducts displays all products");
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("viewProducts threw exception: " + caughtException.getMessage());
        }

        // Test 2: View empty inventory
        // Purpose: Verify that viewProducts() handles empty inventory gracefully
        // Expected: No output (or appropriate message), no exceptions thrown
        try {
            InventoryManager emptyInvManViewTest = new InventoryManager(); // Create empty inventory
            System.out.println("Expected output (empty):");
            emptyInvManViewTest.viewProducts(); // Should handle empty inventory without errors
            testPass("viewProducts on empty inventory");
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("viewProducts on empty inventory threw exception: " + caughtException.getMessage());
        }
    }

    /**
     * Test: InventoryManager Search Product
     * 
     * Tests the searchProduct() method:
     * - Searching for existing product by ID (should display product)
     * - Searching for non-existent product (should throw ProductNotFoundException)
     * - Searching in empty inventory (should throw ProductNotFoundException)
     * 
     * Validates product lookup and exception handling.
     */
    @SuppressWarnings("UseSpecificCatch")
    private static void testInvManSearchProduct() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: InventoryManager Search Product");
        System.out.println("-".repeat(80));

        // Test 1: Search existing product
        // Purpose: Verify that searchProduct() finds and displays products by ID
        // Expected: Product with matching ID should be found and displayed
        try {
            InventoryManager invManSearchTest = new InventoryManager();
            Product headphonesProduct = new Product(20, "Headphones", 79.99, 12);
            Product speakerProduct = new Product(21, "Speaker", 149.99, 8);
            invManSearchTest.addProduct(headphonesProduct);
            invManSearchTest.addProduct(speakerProduct);
            
            System.out.println("Searching for product ID 20:");
            invManSearchTest.searchProduct(20); // Should find and display headphones
            testPass("Search for existing product");
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("Search existing product threw exception: " + caughtException.getMessage());
        }

        // Test 2: Search non-existent product
        // Purpose: Verify that searchProduct() throws exception when product not found
        // Expected: ProductNotFoundException should be thrown for non-existent ID
        try {
            InventoryManager invManNonExistentSearch = new InventoryManager();
            Product mouseProduct = new Product(30, "Mouse", 25.00, 20);
            invManNonExistentSearch.addProduct(mouseProduct);
            invManNonExistentSearch.searchProduct(999); // Search for ID that doesn't exist
            testFail("Should throw ProductNotFoundException for non-existent product"); // If no exception, test fails
        } catch (ProductNotFoundException expectedNotFoundException) { // Expected exception - product not found
            testPass("Search non-existent product throws ProductNotFoundException");
        } catch (Exception wrongException) { // Wrong exception type thrown
            testFail("Wrong exception for non-existent product: " + wrongException.getClass().getName());
        }

        // Test 3: Search in empty inventory
        // Purpose: Verify that searchProduct() throws exception when inventory is empty
        // Expected: ProductNotFoundException should be thrown
        try {
            InventoryManager emptyInvManSearchTest = new InventoryManager(); // Empty inventory
            emptyInvManSearchTest.searchProduct(1); // Try to search in empty inventory
            testFail("Should throw ProductNotFoundException for empty inventory"); // If no exception, test fails
        } catch (ProductNotFoundException expectedNotFoundException) { // Expected exception - inventory is empty
            testPass("Search in empty inventory throws ProductNotFoundException");
        } catch (Exception wrongException) { // Wrong exception type thrown
            testFail("Wrong exception for empty inventory search: " + wrongException.getClass().getName());
        }
    }

    /**
     * Test: InventoryManager Delete Product
     * 
     * Tests the deleteProduct() method:
     * - Deleting existing product (verifies deletion by searching)
     * - Deleting non-existent product (should throw ProductNotFoundException)
     * - Deleting from empty inventory (should throw ProductNotFoundException)
     * 
     * Validates product removal and exception handling.
     */
    @SuppressWarnings("UseSpecificCatch")
    private static void testInvManDeleteProduct() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: InventoryManager Delete Product");
        System.out.println("-".repeat(80));

        // Test 1: Delete existing product
        // Purpose: Verify that deleteProduct() successfully removes products by ID
        // Expected: Product should be deleted (set to null) and no longer searchable
        try {
            InventoryManager invManDeleteTest = new InventoryManager();
            Product cameraProduct = new Product(40, "Camera", 499.99, 3);
            Product lensProduct = new Product(41, "Lens", 299.99, 5);
            invManDeleteTest.addProduct(cameraProduct);
            invManDeleteTest.addProduct(lensProduct);
            invManDeleteTest.deleteProduct(40); // Delete camera by ID
            testPass("Delete existing product");
            
            // Verify deletion by trying to search for deleted product
            try {
                invManDeleteTest.searchProduct(40); // Try to find deleted product
                testFail("Deleted product should not be found"); // If found, deletion failed
            } catch (ProductNotFoundException expectedNotFoundException) { // Expected - product should not exist
                testPass("Deleted product cannot be found (verified)");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions during deletion
            testFail("Delete existing product threw exception: " + caughtException.getMessage());
        }

        // Test 2: Delete non-existent product
        // Purpose: Verify that deleteProduct() throws exception when product doesn't exist
        // Expected: ProductNotFoundException should be thrown for non-existent ID
        try {
            InventoryManager invManDeleteNonExistent = new InventoryManager();
            Product productForDeleteTest = new Product(50, "Test", 100.0, 10);
            invManDeleteNonExistent.addProduct(productForDeleteTest);
            invManDeleteNonExistent.deleteProduct(999); // Try to delete product that doesn't exist
            testFail("Should throw ProductNotFoundException when deleting non-existent product"); // If no exception, test fails
        } catch (ProductNotFoundException expectedNotFoundException) { // Expected exception - product not found
            testPass("Delete non-existent product throws ProductNotFoundException");
        } catch (Exception wrongException) { // Wrong exception type thrown
            testFail("Wrong exception for delete non-existent: " + wrongException.getClass().getName());
        }

        // Test 3: Delete from empty inventory
        // Purpose: Verify that deleteProduct() throws exception when inventory is empty
        // Expected: ProductNotFoundException should be thrown
        try {
            InventoryManager emptyInvManDeleteTest = new InventoryManager(); // Empty inventory
            emptyInvManDeleteTest.deleteProduct(1); // Try to delete from empty inventory
            testFail("Should throw ProductNotFoundException for empty inventory"); // If no exception, test fails
        } catch (ProductNotFoundException expectedNotFoundException) { // Expected exception - inventory is empty
            testPass("Delete from empty inventory throws ProductNotFoundException");
        } catch (Exception wrongException) { // Wrong exception type thrown
            testFail("Wrong exception for empty inventory delete: " + wrongException.getClass().getName());
        }
    }

    /**
     * Test: InventoryManager Edge Cases
     * 
     * Tests unusual but valid scenarios:
     * - Adding expired perishable products (allowed but marked expired)
     * - Products with very large quantities (Integer.MAX_VALUE)
     * - Products with very small prices (0.01)
     * - Multiple operations in sequence (add, delete, search)
     * 
     * Validates system robustness with boundary values.
     */
    @SuppressWarnings("UseSpecificCatch")
    private static void testInvManEdgeCases() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: InventoryManager Edge Cases");
        System.out.println("-".repeat(80));

        // Test 1: Add expired perishable product
        // Purpose: Verify that expired products can still be added to inventory
        // Expected: Product should be added successfully but isExpired() should return true
        try {
            InventoryManager invManExpiredTest = new InventoryManager();
            LocalDate pastExpirationDateTest = LocalDate.of(2020, 1, 1); // Date far in the past
            PerishableProduct expiredMilkProduct = new PerishableProduct(60, "Expired Milk", 4.99, 10, pastExpirationDateTest);
            invManExpiredTest.addProduct(expiredMilkProduct); // Should allow adding expired products
            
            if (expiredMilkProduct.isExpired()) { // Verify it's marked as expired
                testPass("Add expired perishable product (allowed, but marked expired)");
            } else {
                testFail("Expired product should be marked as expired");
            }
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("Add expired product threw exception: " + caughtException.getMessage());
        }

        // Test 2: Product with very large quantity
        // Purpose: Verify that system handles maximum integer values for quantity
        // Expected: Product should be added successfully with Integer.MAX_VALUE quantity
        try {
            InventoryManager invManLargeQuantityTest = new InventoryManager();
            Product productWithLargeQuantity = new Product(70, "Bulk Item", 1.0, Integer.MAX_VALUE); // Maximum possible quantity
            invManLargeQuantityTest.addProduct(productWithLargeQuantity); // Should handle large quantities
            testPass("Add product with very large quantity");
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("Large quantity threw exception: " + caughtException.getMessage());
        }

        // Test 3: Product with very small price
        // Purpose: Verify that system accepts minimum valid prices (just above zero)
        // Expected: Product should be added successfully with price of 0.01
        try {
            InventoryManager invManForSmallPriceTest = new InventoryManager();
            Product productWithSmallPrice = new Product(71, "Cheap Item", 0.01, 1000); // Minimum practical price
            invManForSmallPriceTest.addProduct(productWithSmallPrice); // Should accept very small but positive prices
            testPass("Add product with very small price (0.01)");
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("Very small price threw exception: " + caughtException.getMessage());
        }

        // Test 4: Multiple operations sequence
        // Purpose: Verify that different operations work correctly in combination
        // Expected: Add, delete, and search should all work together without conflicts
        try {
            InventoryManager invManMultiOperations = new InventoryManager();
            Product firstProductForSequence = new Product(80, "Item1", 50.0, 10);
            Product secondProductForSequence = new Product(81, "Item2", 60.0, 20);
            Product thirdProductForSequence = new Product(82, "Item3", 70.0, 30);
            
            invManMultiOperations.addProduct(firstProductForSequence);    // Add first product
            invManMultiOperations.addProduct(secondProductForSequence);    // Add second product
            invManMultiOperations.addProduct(thirdProductForSequence);    // Add third product
            invManMultiOperations.deleteProduct(81);    // Delete middle product
            invManMultiOperations.searchProduct(82);    // Search for remaining product
            
            testPass("Multiple operations in sequence");
        } catch (Exception caughtException) { // Catch any unexpected exceptions
            testFail("Multiple operations threw exception: " + caughtException.getMessage());
        }
    }

    /**
     * Test: Inventory Full Exception
     * 
     * Tests inventory capacity limits:
     * - Fills inventory to maximum capacity (50 products)
     * - Attempts to add 51st product (should throw InvalidInputException)
     * 
     * Validates that inventory enforces capacity constraints.
     */
    private static void testInvFullException() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("TEST: Inventory Full Exception");
        System.out.println("-".repeat(80));

        // Purpose: Verify that inventory rejects products when full (capacity = 50)
        // Expected: InvalidInputException should be thrown when trying to add 51st product
        try {
            InventoryManager fullInvManForTest = new InventoryManager(); // Create inventory with capacity of 50
            
            // Fill inventory to maximum capacity
            for (int loopIndexFillInv = 0; loopIndexFillInv < 50; loopIndexFillInv++) {
                Product productFillInv = new Product(loopIndexFillInv, "Product" + loopIndexFillInv, 10.0 + loopIndexFillInv, loopIndexFillInv + 1);
                fullInvManForTest.addProduct(productFillInv); // Add products until inventory is full
            }
            
            // Try to add 51st product (should exceed capacity)
            Product overflowProdTrigException = new Product(999, "Overflow", 100.0, 1);
            fullInvManForTest.addProduct(overflowProdTrigException); // This should throw InvalidInputException
            testFail("Should throw InvalidInputException when inventory is full"); // If no exception, test fails
        } catch (InvalidInputException validationException) { // Expected exception - inventory is full
            testPass("Inventory full throws InvalidInputException");
        } catch (Exception wrongException) { // Wrong exception type thrown
            testFail("Wrong exception for full inventory: " + wrongException.getClass().getName());
        }
    }

    // ============================================================================
    // HELPER METHODS
    // ============================================================================

    /**
     * Marks a test as passed and increments pass counter
     * 
     * @param testName The name/description of the test that passed
     */
    private static void testPass(String testName) {
        System.out.println("!!!PASS!!!: " + testName);
        testsPassed++;
    }

    /**
     * Marks a test as failed and increments fail counter
     * 
     * @param testName The name/description of the test that failed
     */
    private static void testFail(String testName) {
        System.out.println("???FAIL???: " + testName);
        testsFailed++;
    }

    /**
     * Prints comprehensive test summary with statistics
     * 
     * Displays:
     * - Total number of tests run
     * - Number of tests passed
     * - Number of tests failed
     * - Pass rate percentage (if any failures)
     * - Success message (if all tests passed)
     */
    private static void printTestSummary() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TEST SUMMARY");
        System.out.println("=".repeat(80));
        System.out.println("Total Tests: " + (testsPassed + testsFailed));
        System.out.println("Tests Passed: " + testsPassed);
        System.out.println("Tests Failed: " + testsFailed);
        
        if (testsFailed == 0) {
            System.out.println("\n ALL TESTS PASSED! ");
        } else {
            double passRate = (testsPassed * 100.0) / (testsPassed + testsFailed);
            System.out.println(String.format("\nPass Rate: %.2f%%", passRate));
        }
        System.out.println("=".repeat(80));
    }
}
