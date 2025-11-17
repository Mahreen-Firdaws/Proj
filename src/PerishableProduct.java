// Use java.time package for handling dates
// and extend the Product class to include expiration date functionality.
// Inspect java.time documentation for relevant classes and methods - see https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/package-summary.html
// Specifically, consider using LocalDate for representing expiration dates.
// Specifically, you might want to add a field for expiration date and methods to check if the product is expired.
// Look into the java.time to look for a constraint that can help with date comparisons. 
// Specifically looking into constraints around Days, Months (Months is given in general function), 
// and Years could be useful for expiration date logic.
import java.time.LocalDate;
public class PerishableProduct extends Product {
    
}
