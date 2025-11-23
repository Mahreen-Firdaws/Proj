import java.time.LocalDate;
import java.util.Optional;

/**
 * PerishableProduct - Extended product class for items that can expire.
 * Inherits from Product class and adds expiration date functionality.
 * 
 * Author: Halcionne (Martin Khlopin)
 */
public class PerishableProduct extends Product {
    private Optional<LocalDate> expirationDate;

    /**
     * Constructor for creating a perishable product without an expiration date.
     * 
     * @param productId Unique identifier
     * @param productName Name of the product
     * @param price Price in dollars
     * @param quantity Stock quantity
     * @throws InvalidInputException if validation fails
     */
    public PerishableProduct(int productId, String productName, double price, int quantity) throws InvalidInputException {
        super(productId, productName, price, quantity);
        this.expirationDate = Optional.empty();
    }
    
    /**
     * Constructor for creating a perishable product with an expiration date.
     * 
     * @param productId Unique identifier
     * @param productName Name of the product
     * @param price Price in dollars
     * @param quantity Stock quantity
     * @param expirationDate Date when product expires
     * @throws InvalidInputException if validation fails
     */
    public PerishableProduct(int productId, String productName, double price, int quantity, LocalDate expirationDate) throws InvalidInputException {
        super(productId, productName, price, quantity);
        this.expirationDate = Optional.ofNullable(expirationDate);
    }

    /**
     * Gets the expiration date.
     * 
     * @return Optional containing the expiration date, or empty if not set
     */
    public Optional<LocalDate> getExpirationDate() {
        return expirationDate;
    }
    
    /**
     * Sets or updates the expiration date.
     * 
     * @param expirationDate New expiration date
     */
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = Optional.ofNullable(expirationDate);
    }
    
    /**
     * Checks if the product has expired.
     * 
     * @return true if expired, false otherwise
     */
    public boolean isExpired() {
        return expirationDate
            .map(d -> LocalDate.now().isAfter(d))
            .orElse(false);
    }
    
    /**
     * Displays product information including expiration details.
     */
    @Override
    public void displayProductInfo() {
        super.displayProductInfo();
        
        System.out.println("Expiration Date: " + 
            expirationDate.map(LocalDate::toString)
                          .orElse("No expiration"));
        
        System.out.println("Is Expired: " + isExpired());
    }
}
