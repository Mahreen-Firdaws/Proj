/**
 * ProductNotFoundException - Custom checked exception for product search/lookup failures.
 * Thrown when attempting to find, update, or delete a product that doesn't exist.
 * 
 * Common uses:
 * - findProduct() when ID not found
 * - updateProduct() on non-existent product
 * - deleteProduct() on non-existent product
 */
public class ProductNotFoundException extends Exception {
    
    /**
     * Constructor - creates exception with descriptive error message.
     * Message should include the product ID.
     * 
     * @param message Error message indicating which product was not found
     */
    public ProductNotFoundException(String message) {
        super(message);
    }

}
