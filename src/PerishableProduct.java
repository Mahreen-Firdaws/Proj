import java.time.LocalDate;
import java.util.Optional;

/**
 * PerishableProduct - Extended product class for items that can expire.
 * Inherits from Product class and adds expiration date functionality.
 * 
 * Author: Halcionne (Martin Khlopin)
 * 
 * Uses Optional<LocalDate> for null-safe date handling - learned this from
 * Oracle docs as a better alternative to null checks. The .map() and .orElse()
 * took some time to understand but makes the code cleaner.
 */
public class PerishableProduct extends Product {
    // Expiration date - when product expires (Optional for null-safe handling)
    private Optional<LocalDate> expirationDate;

    /**
     * Constructor for creating a perishable product WITHOUT an expiration date.
     * 
     * @param productId Unique identifier (must be >= 0)
     * @param productName Name of the product
     * @param price Price in dollars (must be > 0)
     * @param quantity Stock quantity (must be >= 0)
     * @throws InvalidInputException if validation fails
     */
    public PerishableProduct(int productId, String productName, double price, int quantity) throws InvalidInputException {
        super(productId, productName, price, quantity);
        this.expirationDate = Optional.empty();
    }
    
    /**
     * Constructor for creating a perishable product WITH an expiration date.
     * 
     * @param productId Unique identifier (must be >= 0)
     * @param productName Name of the product
     * @param price Price in dollars (must be > 0)
     * @param quantity Stock quantity (must be >= 0)
     * @param expirationDate Date when product expires (can be null)
     * @throws InvalidInputException if validation fails
     */
    public PerishableProduct(int productId, String productName, double price, int quantity, LocalDate expirationDate) throws InvalidInputException {
        super(productId, productName, price, quantity);
        this.expirationDate = Optional.ofNullable(expirationDate);
    }

    /**
     * Gets the expiration date of the product (if one exists).
     * 
     * @return Optional<LocalDate> containing the expiration date if set, or Optional.empty() if no date
     */
    public Optional<LocalDate> getExpirationDate() {
        return expirationDate;
    }
    
    /**
     * Sets or updates the expiration date of the product.
     * 
     * @param expirationDate New expiration date (can be null to clear/remove the date)
     */
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = Optional.ofNullable(expirationDate);
    }
    
    /**
     * Checks if the product has expired.
     * 
     * @return true if product has expired, false if not expired or if no expiration date is set
     */
    public boolean isExpired() {
        return expirationDate
            .map(d -> LocalDate.now().isAfter(d))
            .orElse(false);
    }
    
    /**
     * Displays all product information including expiration details to the console.
     * Overrides the parent's displayProductInfo() to add expiration-specific information.
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
