import java.time.LocalDate;
import java.util.Optional;

public class PerishableProduct extends Product {
    private Optional<LocalDate> expirationDate;

    public PerishableProduct(int productId, String productName, double price, int quantity) throws InvalidInputException {
        super(productId, productName, price, quantity);
        this.expirationDate = Optional.empty(); // no expiration by default
    }
    public PerishableProduct(int productId, String productName, double price, int quantity, LocalDate expirationDate) throws InvalidInputException {
        super(productId, productName, price, quantity);
        this.expirationDate = Optional.ofNullable(expirationDate);
    }

    public Optional<LocalDate> getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = Optional.ofNullable(expirationDate);
    }
    public boolean isExpired() {
        return expirationDate.map(d -> LocalDate.now().isAfter(d)).orElse(false);
    }
    @Override
    public void displayProductInfo() {
        super.displayProductInfo();
        System.out.println("Expiration Date: " + expirationDate.map(LocalDate::toString).orElse("No expiration"));
        System.out.println("Is Expired: " + isExpired());
    }
}
