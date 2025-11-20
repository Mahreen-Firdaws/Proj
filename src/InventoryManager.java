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

    public void addProduct(Product product)  {
        if (uniqueProductCount >= products.length) {
            System.out.println("Inventory is full. Cannot add more products.");
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

    public void updateProduct(int productId){
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
                }
            }
        }
    }

    public void deleteProduct(int productId) {
        for (int i = 0; i < uniqueProductCount; i++) {
            if (products[i] != null && products[i].getProductId() == productId) {
                products[i] = null;
                System.out.println("Product with ID " + productId + " deleted.");
                return;
            }
        }
        System.out.println("Product with ID " + productId + " not found.");
    }

    public void searchProduct(int productId) {
        for (int i = 0; i < uniqueProductCount; i++) {
            if (products[i] != null && products[i].getProductId() == productId) {
                products[i].displayProductInfo();
                return;
            }
        }
        System.out.println("Product with ID " + productId + " not found.");
    }
}
