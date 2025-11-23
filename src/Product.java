/**
 * Product - Base class for inventory products.
 * Represents standard (non-perishable) products with ID, name, price, and quantity.
 * 
 * Author: Mahreen Firdaws
 */
public class Product {

    private final int productId;
    public String productName;
    public double price;
    public int quantity;

    /**
     * Default constructor for testing purposes.
     */
    public Product() {
        this.productId = 0;
        productName = "Unnamed Product";
        this.price = 0.0;
        this.quantity = 0;
    }
    
    /**
     * Creates a product with validation.
     * 
     * @param productId Unique identifier (>= 0)
     * @param productName Product name
     * @param price Price in dollars (> 0)
     * @param quantity Stock quantity (>= 0)
     * @throws InvalidInputException if validation fails
     */
    public Product(int productId, String productName, double price, int quantity) throws InvalidInputException {
        if (productId < 0) {
            throw new InvalidInputException("Product ID cannot be negative. Currently provided: " + productId);
        }
        if (price <= 0) {
            throw new InvalidInputException("Price must be greater than zero. Currently provided: " + price);
        }
        if (quantity < 0) {
            throw new InvalidInputException("Quantity cannot be negative. Currently provided: " + quantity);
        }
        
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }


    public int getProductId() {
        return productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String newProductName) {
        productName = newProductName;
    }
    
    public double getPrice() {
        return price;
    }
    
    /**
     * Sets the price with validation.
     * 
     * @param price New price
     * @throws InvalidInputException if price <= 0
     */
    public void setPrice(double price) throws InvalidInputException {
        if (price <= 0) {
            throw new InvalidInputException("Price must be greater than zero. Currently provided: " + price);
        }
        this.price = price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Sets the quantity with validation.
     * 
     * @param quantity New quantity
     * @throws InvalidInputException if quantity < 0
     */
    public void setQuantity(int quantity) throws InvalidInputException {
        if (quantity < 0) {
            throw new InvalidInputException("Quantity cannot be negative. Currently provided: " + quantity);
        }
        this.quantity = quantity;
    }

    /**
     * Displays product information to console.
     */
    public void displayProductInfo() {
        System.out.println("Product ID: " + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("Price: $" + price);
        System.out.println("Quantity: " + quantity);
    }
    
}
