import java.util.List;
public interface ShoppingManager {
    void addProduct(Product var1);

    void removeProduct(Product var1);

    List<Product> getAllProducts();

    void displayMenu();
}
