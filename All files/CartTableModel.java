import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class CartTableModel extends AbstractTableModel {        //this class is used to create the table for the data of the added items
    private String[] columnNames = {"Product", "Quantity", "Price"};
    private HashMap<String, Integer> productQuantities = new HashMap<>();
    private HashMap<String, Product> allProducts = new HashMap<>();
    private ArrayList<String> usedID = new ArrayList<>();

    @Override
    public int getRowCount() {
        return productQuantities.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object temp2 = null;
        String productID = usedID.get(rowIndex);

        if (columnIndex == 0) {
            temp2 = printData(productID);
        } else if (columnIndex == 1) {
            temp2 = productQuantities.get(productID);
        } else if (columnIndex == 2) {
            temp2 = allProducts.get(productID).getPrice();
        }

        return temp2;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public void transferData(ArrayList<String> finalProducts, HashMap<String, Product> allData) {
        for (String productID : finalProducts) {
            productQuantities.put(productID, productQuantities.getOrDefault(productID, 0) + 1);
        }
        allProducts = allData;
        usedID = new ArrayList<>(productQuantities.keySet());
        fireTableDataChanged();
    }

    public Object printData(String productID) {
        Object temp3 = null;

        String pID = allProducts.get(productID).getProductID();
        String pName = allProducts.get(productID).getProductName();

        temp3 = allProducts.get(productID).getProductID() +
                allProducts.get(productID).getProductName();

        if (productID.substring(0, 1).equals("A")) {
            Electronics getE = (Electronics) allProducts.get(productID);
            String pBrand = getE.getBrand();
            String pWarranty = String.valueOf(getE.getWarrantyPeriod());
            String finalInfo = pID + "," + pName + "," + pBrand + "," + pWarranty;
            temp3 = finalInfo;

        } else if (productID.substring(0, 1).equals("B")) {
            Clothing getC = (Clothing) allProducts.get(productID);
            String pSize = getC.getSize();
            String pColour = getC.getColour();
            String finalInfo = pID + "," + pName + "," + pSize + "," + pColour;
            temp3 = finalInfo;
        }
        return temp3;
    }
}

