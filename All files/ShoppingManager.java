import java.io.IOException;

public interface ShoppingManager {
    void addProduct();
    void deleteProduct();
    void deleteProductInfo(String productID);
    void printProducts();
    void saveProducts() throws IOException;
    void loadProducts() throws IOException, RuntimeException, ClassNotFoundException;
    boolean productsLimit();

}
