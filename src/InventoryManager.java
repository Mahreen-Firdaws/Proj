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
     * @throws InvalidInputException if product is null or inventory is full (50 products)
     */
    public void addProduct(Product product) throws InvalidInputException {
        if (product == null) {
            throw new InvalidInputException("Cannot add null product to inventory.");
        }
        
        if (uniqueProductCount >= products.length) {
            throw new InvalidInputException("Inventory is full. Cannot add more products.");
        }
        
        products[uniqueProductCount++] = product;
    }

    /**
     * Displays all products in the inventory to the console.
     * Loops through products array and prints each non-null product.
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
     * @throws ProductNotFoundException if no product with given ID exists
     */
    public void updateProduct(int productId) throws ProductNotFoundException {
        for (int i = 0; i < uniqueProductCount; i++) {
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
                } catch (InvalidInputException e) {
                    System.out.println("Product update failed: " + e.getMessage());
                }
                return;
            }
        }
        throw new ProductNotFoundException("Product with ID " + productId + " not found.");
    }

    /**
     * Deletes a product from inventory by ID using soft delete (sets slot to null).
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
     * Searches for a product by ID and displays its information to console.
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
     * Gets direct reference to the internal products array for GUI access.
     * 
     * @return Internal products array (size 50, may contain nulls)
     */
    public Product[] getProducts() {
        return products;
    }
    
    /**
     * Gets the count of how many array slots have been used.
     * Note: This includes deleted products (null slots).
     * 
     * @return Number of array slots that have been used (0-50)
     */
    public int getProductCount() {
        return uniqueProductCount;
    }
}
