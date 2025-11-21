public class Product {
    private final int productId;
    public String productName;
    public double price;
    public int quantity;


    public Product() {
        this.productId = -1;
        productName = "Unnamed Product";
        this.price = 0.0;
        this.quantity = 0;
    }
    public Product(int productId, String productName, double price, int quantity) throws InvalidInputException {
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
    public void setPrice(double price) throws InvalidInputException {
        if (price <= 0) {
            throw new InvalidInputException("Price must be greater than zero. Currently provided: " + price);
        }
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) throws InvalidInputException {
        if (quantity < 0) {
            throw new InvalidInputException("Quantity cannot be negative. Currently provided: " + quantity);
        }
        this.quantity = quantity;
    }

    public void displayProductInfo() {
        System.out.println("Product ID: " + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("Price: $" + price);
        System.out.println("Quantity: " + quantity);
    }
    
}
