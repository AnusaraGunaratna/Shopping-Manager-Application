import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class ItemTableModel extends AbstractTableModel {  //This class is to create the table of showing the available products
    private String[] columnNames = {"Product ID","Name", "Category", "Price(\u00A3)", "Info"};
    private WestminsterShoppingManager passInstance;
    private HashMap<String,Product> GUItemList = new HashMap<>();
    private ArrayList<String> GUIproductIDList = new ArrayList<>();
    private String info;
    private String categoryFilter = "All";


    public ItemTableModel(WestminsterShoppingManager passInstance){
        this.passInstance = passInstance.getInstance();
        transferData();
    }

    public void transferData(){
        for (Map.Entry<String,Product> entry : passInstance.getProductList().entrySet()){
            String key = entry.getKey();
            Product value = entry.getValue();
            GUItemList.put(key,value);
            GUIproductIDList.add(key);
            sortData();
        }
    }

    public void sortData(){
        Collections.sort(GUIproductIDList);
    }

    @Override
    public int getRowCount() {

        int count = 0;
        for (String productId : GUIproductIDList) {
            Product getProduct = GUItemList.get(productId);

            if (getProduct != null) {
                if (categoryFilter.equals("All") ||
                        (categoryFilter.equals("Electronics") && productId.startsWith("A")) ||
                        (categoryFilter.equals("Clothing") && productId.startsWith("B"))) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (rowIndex < 0 || rowIndex >= GUIproductIDList.size()) {
            return null;
        }

        String keyName = GUIproductIDList.get(rowIndex);
        Product getProduct = GUItemList.get(keyName);


        if (getProduct == null) {
            System.out.println("Product is null for key: " + keyName);
            return null;
        }



        if (columnIndex == 0) {
            return getProduct.getProductID();
        } else if (columnIndex == 1) {
            return getProduct.getProductName();
        } else if (columnIndex == 2) {
            if (getProduct.getProductID().substring(0,1).equals("A")){
                Electronics getElectronic = (Electronics) getProduct;  //Type casting
                info = getElectronic.getBrand() +" ,"+ getElectronic.getWarrantyPeriod()+" weeks warranty";
                return "Electronics";
            }else if (getProduct.getProductID().substring(0,1).equals("B")){
                Clothing getClothing = (Clothing) getProduct; // Type casting
                info = getClothing.getSize()+ " ," + getClothing.getColour();
                return "Clothing";
            }
        } else if (columnIndex == 3) {
                return getProduct.getPrice();
        } else if (columnIndex == 4) {
            return info;
        } else {
            return null;
        }
        return null;
    }

    public String getColumnName(int col){
        return columnNames[col];
    }

    public void setCategoryFilter(String categoryFilter) {
        this.categoryFilter = categoryFilter;
        fireTableDataChanged();
    }

}
