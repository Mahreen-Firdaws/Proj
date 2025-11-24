/**
 * InventoryManager - Core business logic class that manages the product inventory.
 * Handles all CRUD operations (Create, Read, Update, Delete) for products.
 * Uses array-based storage with fixed capacity of 50 products.
 */

import java.util.Scanner;

public class InventoryManager {
    // Fixed-size array to store products (capacity: 50)
    private final Product[] products;
    
    // Tracks number of products added (does not decrease on deletion)
    private int uniqueProductCount;

    /**
     * Constructor - Initializes an empty inventory with capacity for 50 products.
     */
    public InventoryManager() {
        products = new Product[50];
        uniqueProductCount = 0;
    }

    /**
     * Adds a new product to the inventory.
     * 
     * @param product The product to add to inventory
     * @throws InvalidInputException if product is null, inventory is full (50 products), or ID already exists
     */
    public void addProduct(Product product) throws InvalidInputException {
        if (product == null) {
            throw new InvalidInputException("Cannot add null product to inventory.");
        }
        
        if (uniqueProductCount >= products.length) {
            throw new InvalidInputException("Inventory is full. Cannot add more products.");
        }
        
        // Check for duplicate product ID
        for (int i = 0; i < uniqueProductCount; i++) {
            if (products[i] != null && products[i].getProductId() == product.getProductId()) {
                throw new InvalidInputException("Product with ID " + product.getProductId() + " already exists.");
            }
        }
        
        products[uniqueProductCount++] = product;
    }

    /**
     * Displays all products in the inventory to the console.
     */
    public void viewProducts() {
        for (int i = 0; i < uniqueProductCount; i++) {
            if (products[i] != null) {
                products[i].displayProductInfo();
                System.out.println();
            }
        }
    }

    /**
     * Updates a product's price or quantity by ID using console-based interaction.
     * 
     * @param productId The ID of the product to update
     * @param keyboard The Scanner to use for input
     * @throws ProductNotFoundException if no product with given ID exists
     */
    public void updateProduct(int productId, Scanner keyboard) throws ProductNotFoundException {
        for (int i = 0; i < uniqueProductCount; i++) {
            if (products[i] != null && products[i].getProductId() == productId) {
                try {
                    System.out.print("Enter either 'price' or 'quantity' to update: ");
                    String choice = keyboard.nextLine();
                    
                    if (choice.equalsIgnoreCase("price")) {
                        System.out.print("Enter new price: ");
                        double newPrice = keyboard.nextDouble();
                        keyboard.nextLine(); // Clear buffer
                        products[i].setPrice(newPrice);
                        System.out.println("Price updated.");
                        
                    } else if (choice.equalsIgnoreCase("quantity")) {
                        System.out.print("Enter new quantity: ");
                        int newQuantity = keyboard.nextInt();
                        keyboard.nextLine(); // Clear buffer
                        products[i].setQuantity(newQuantity);
                        System.out.println("Quantity updated.");
                        
                    } else {
                        System.out.println("Invalid choice.");
                    }
                } catch (InvalidInputException e) {
                    System.out.println("Product update failed: " + e.getMessage());
                }
                return;
            }
        }
        throw new ProductNotFoundException("Product with ID " + productId + " not found.");
    }

    /**
     * Deletes a product from inventory by ID (soft delete).
     * 
     * @param productId The ID of the product to delete
     * @throws ProductNotFoundException if no product with given ID exists
     */
    public void deleteProduct(int productId) throws ProductNotFoundException {
        for (int i = 0; i < uniqueProductCount; i++) {
            if (products[i] != null && products[i].getProductId() == productId) {
                products[i] = null;
                System.out.println("Product with ID " + productId + " deleted.");
                return;
            }
        }
        throw new ProductNotFoundException("Product with ID " + productId + " not found.");
    }

    /**
     * Searches for a product by ID and displays its information.
     * 
     * @param productId The ID of the product to search for
     * @throws ProductNotFoundException if no product with given ID exists
     */
    public void searchProduct(int productId) throws ProductNotFoundException {
        for (int i = 0; i < uniqueProductCount; i++) {
            if (products[i] != null && products[i].getProductId() == productId) {
                products[i].displayProductInfo();
                return;
            }
        }
        throw new ProductNotFoundException("Product with ID " + productId + " not found.");
    }
    
    /**
     * Gets the internal products array.
     * 
     * @return Products array (size 50, may contain nulls)
     */
    public Product[] getProducts() {
        return products;
    }
    
    /**
     * Gets the count of array slots used (includes deleted products).
     * 
     * @return Number of slots used (0-50)
     */
    public int getProductCount() {
        return uniqueProductCount;
    }
}
