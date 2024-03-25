import java.util.ArrayList;

public class Clothing extends Product {
    private String size; // size taken as : small(S), medium(M), Large(L), Extra large(XL)
    private String colour;
    private ArrayList<Clothing> clothingList = new ArrayList<>();

    public Clothing(String productId, String productName, int quantity, double price, ClothingSize size, String colour) {
        super(productId, productName, quantity, price);
        this.size = String.valueOf(size);
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        colour = colour;
    }

    public ArrayList<Clothing> getClothingList() {
        return clothingList;
    }

    public void setClothingList(ArrayList<Clothing> clothingList) {
        this.clothingList = clothingList;
    }

    public void addClothing(Clothing clothing){
        clothingList.add(clothing);
    }

}
