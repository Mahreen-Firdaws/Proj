public class Product {
    private static int productId;
    public static String productName;
    public double price;
    public int quantity;

    public int getProductId() {
        return productId;
    }
    public static void setProductId(int newProductId) {
        productId = newProductId;
    }
    public String getProductName() {
        return productName;
    }
    public static void setProductName(String newProductName) {
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
