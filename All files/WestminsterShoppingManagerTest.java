import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class WestminsterShoppingManagerTest {
    WestminsterShoppingManager test1 = new WestminsterShoppingManager();
    Product product = new Electronics("A123","test",5,50.00,"testBrand",10);
    @Test
    public void testAddProduct() {
        test1.getProductList().put(product.getProductID(),product);
        assertTrue(test1.getProductList().containsKey(product.getProductID()),"Product is added successfully.");
    }

    @Test
    public void testDeleteProduct() {
        test1.getProductList().remove(product.getProductID());
        assertFalse(test1.getProductList().containsKey(product.getProductID()),"Product is deleted successfully.");
    }


    @Test
    public void testSaveProducts() {
        test1.loadProducts(); //Can be checked only after the productList is saved.
    }


}
