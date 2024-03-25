import java.io.Serializable;

public abstract class Product implements Serializable {
    private String productID;
    private String productName;
    private int quantity;
    private double price;

    public Product(String productId, String productName, int quantityLeft, double price){
        this.productID = productId;
        this.productName = productName;
        this.quantity = quantityLeft;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantityLeft() {
        return quantity;
    }

    public void setQuantityLeft(int quantityLeft) {
        this.quantity = quantityLeft;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



}
