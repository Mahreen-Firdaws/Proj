/**
 * InvalidInputException - Custom checked exception for invalid product data.
 * Thrown when validation fails for product-related operations.
 * 
 * Common causes:
 * - Price <= 0
 * - Quantity < 0  
 * - Product ID < 0
 * - Null product
 * - Inventory full (50 product limit)
 */
public class InvalidInputException extends Exception {
    
    /**
     * Constructor - creates exception with descriptive error message.
     * 
     * @param message Error message explaining validation failure
     */
    public InvalidInputException(String message) {
        super(message);
    }

}
