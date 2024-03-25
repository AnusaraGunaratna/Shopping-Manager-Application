import java.util.ArrayList;
import java.util.Arrays;

public class Electronics extends Product{
    private String brand;
    private int warrantyPeriod; //warranty period is taken as the number of months
    //private Electronics[] electronicList = new Electronics [50]; ///////////////////////

    private ArrayList<Electronics> electronicList = new ArrayList<>();


    public Electronics(String productId, String productName, int quantity, double price, String brand, int warrantyPeriod) {
        super(productId,productName, quantity, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
        //numOfProducts();
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    //////////////////////////////////////////////////////////////////
    /*

    public Electronics[] getElectronicList() {
        return electronicList;
    }

    public void setElectronicList(Electronics[] electronicList) {
        //electronicList.add(electronicList);
        this.electronicList = electronicList;
    }

     */

    public ArrayList<Electronics> getElectronicList() {
        return electronicList;
    }

    public void setElectronicList(ArrayList<Electronics> electronicList) {
        this.electronicList = electronicList;
    }

    public void addElectronics(Electronics electronics){
        electronicList.add(electronics);
    }

}
