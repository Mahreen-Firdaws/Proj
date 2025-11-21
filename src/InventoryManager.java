//2.	In InventoryManager, implement:
//	addProduct() → Add new product to array. Throw custom exception if array full.
//	viewProducts() → Display all stored products.
//	updateProduct() → Update price/quantity by product ID.
//	deleteProduct() → Remove product by ID (set to null).
//  searchProduct() → Find and display details for given ID

import java.util.Scanner;

public class InventoryManager {
    private final Product[] products;
    private int uniqueProductCount;

    public InventoryManager() {
        products = new Product[50];
        uniqueProductCount = 0;
    }

    public void addProduct(Product product) throws InvalidInputException { // Add new product to array
        if (product == null) { // If user inputs null product (nothing inputed), an exception is thrown
            throw new InvalidInputException("Cannot add null product to inventory.");
        }
        if (uniqueProductCount >= products.length) {
            throw new InvalidInputException("Inventory is full. Cannot add more products."); // Added exception for full inventory
        }
        products[uniqueProductCount++] = product;
    }

    public void viewProducts() {
        for (int i = 0; i < uniqueProductCount; i++) {
            if (products[i] != null) {
                products[i].displayProductInfo();
                System.out.println();
            }
        }
    }

    public void updateProduct(int productId) throws ProductNotFoundException {  // Update price or quantity of product by ID
        for (int i=0; i< uniqueProductCount ; i++){
            if (products[i] != null && products[i].getProductId() == productId) {
                try (Scanner keyboard = new Scanner(System.in)) {
                    System.out.print("Enter either 'price' or 'quantity' to update: ");
                    String choice = keyboard.nextLine();
                    if (choice.equalsIgnoreCase("price")) {
                        System.out.print("Enter new price: ");
                        double newPrice = keyboard.nextDouble();
                        products[i].setPrice(newPrice);
                        System.out.println("Price updated.");
                    } else if (choice.equalsIgnoreCase("quantity")) {
                        System.out.print("Enter new quantity: ");
                        int newQuantity = keyboard.nextInt();
                        products[i].setQuantity(newQuantity);
                        System.out.println("Quantity updated.");
                    } else {
                        System.out.println("Invalid choice.");
                    }
                } catch (InvalidInputException e) { // Catching invalid input exceptions for price/quantity updates
                    System.out.println("Product update failed: " + e.getMessage());
                }
                return; 
            }
        }
        throw new ProductNotFoundException("Product with ID " + productId + " not found."); // Exception if product ID not found
    }

    public void deleteProduct(int productId) throws ProductNotFoundException { // Delete product by ID
        for (int i = 0; i < uniqueProductCount; i++) {
            if (products[i] != null && products[i].getProductId() == productId) {
                products[i] = null;
                System.out.println("Product with ID " + productId + " deleted.");
                return;
            }
        }
        throw new ProductNotFoundException("Product with ID " + productId + " not found."); // Exception if product ID not found
    }

    public void searchProduct(int productId) throws ProductNotFoundException { // Search and display product details by ID
        for (int i = 0; i < uniqueProductCount; i++) {
            if (products[i] != null && products[i].getProductId() == productId) {
                products[i].displayProductInfo();
                return;
            }
        }
        throw new ProductNotFoundException("Product with ID " + productId + " not found."); // Exception if product ID not found
    }
}
