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
    public Product(int productId, String productName, double price, int quantity) {
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
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void displayProductInfo() {
        System.out.println("Product ID: " + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("Price: $" + price);
        System.out.println("Quantity: " + quantity);
    }
    
}
