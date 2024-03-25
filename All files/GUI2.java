import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GUI2 extends GUI{      //The GUI for the shopping cart, this is inherited from the main GUI
    private JFrame mainFrame2 = new JFrame("Shopping cart");
    private JPanel topPanel2 = new JPanel(new BorderLayout());      //All the components displayed in the top part of the main frame
    private JPanel bottomPanel2 = new JPanel(new BorderLayout());       //All the components displayed in the bottom part of the main frame
    private static JTable detailsTable;
    static int elecCount=0;     // to take the total count of electronic products added
    static int clothCount = 0;      // to take the total count of clothing products added

    public GUI2(WestminsterShoppingManager instance2, HashMap<String, Product> GUIproductList2, ArrayList<String> addedProducts2){
        super();
        this.instance = instance2;
        this.GUIproductList = GUIproductList2;
        this.addedProducts = addedProducts2;
        GUItransferData();
        addedData();
        cartTable();
        displayGUI2();
        totalBilling();
    }

    public GUI2(){}

    public void displayGUI2(){
        mainFrame2.setVisible(true);
        mainFrame2.setMinimumSize(new Dimension(650, 500));
        mainFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void cartTable(){
        JScrollPane dataRows = new JScrollPane();
        dataRows.setPreferredSize(new Dimension(200,300));
        CartTableModel createDetailsTable = new CartTableModel();
        createDetailsTable.transferData(addedProducts, GUIproductList);
        detailsTable = new JTable(createDetailsTable);
        dataRows.setViewportView(detailsTable);

        DefaultTableCellRenderer alignCenter = new DefaultTableCellRenderer();
        alignCenter.setHorizontalAlignment(JLabel.CENTER);
        dataRows.setBorder(BorderFactory.createEmptyBorder(30,10,10,10));

        JTableHeader header = detailsTable.getTableHeader();
        header.setBorder(BorderFactory.createLineBorder(Color.black,1));

        detailsTable.setBorder(BorderFactory.createLineBorder(Color.black,1));
        topPanel2.add(dataRows);
        mainFrame2.add(topPanel2, BorderLayout.CENTER);
    }

    public void addedData(){
        CartTableModel passData = new CartTableModel();
        passData.transferData(addedProducts,GUIproductList);
    }

    public void totalBilling(){
        JPanel totalBillingDetails = new JPanel();
        totalBillingDetails.setLayout(new GridLayout(4,2,-30,10));
        float totalValue = columnSum(2);

        float first_discount = firstPurchaseDiscount(totalValue);


        for (int rowCount = 0; rowCount<detailsTable.getRowCount(); rowCount++){
            Object entryText = detailsTable.getValueAt(rowCount,0);

            if (entryText instanceof String && ((String) entryText).startsWith("A")){
                elecCount = elecCount + 1;
            }else if(entryText instanceof String && ((String) entryText).startsWith("B")){
                clothCount = clothCount + 1;
            }
        }
        float sameCate_discount = sameCategoryDiscount(totalValue,elecCount,clothCount);

        float finalTotal = totalValue - (first_discount + sameCate_discount);


        JLabel total = new JLabel("                                                          Total   " + totalValue +"\u00A3");
        JLabel firstDiscount = new JLabel("          First Purchase Discount (10%)   -"+ first_discount+"\u00A3");
        JLabel sameCategory = new JLabel("Three items in same category (20%)   -"+ sameCate_discount+"\u00A3");
        JLabel finalT = new JLabel("                                                Final Total   "+ finalTotal+"\u00A3");

        totalBillingDetails.add(total);
        totalBillingDetails.add(firstDiscount);
        totalBillingDetails.add(sameCategory);
        totalBillingDetails.add(finalT);

        bottomPanel2.setBorder(BorderFactory.createEmptyBorder(0,30,80,200));
        bottomPanel2.setLayout(new BorderLayout());
        bottomPanel2.add(totalBillingDetails, BorderLayout.EAST);
        mainFrame2.add(bottomPanel2,BorderLayout.SOUTH);

    }

    public int columnSum(int columnIndex) {
        int total = 0;

        for (int row = 0; row < detailsTable.getRowCount(); row++) {
            Object value = detailsTable.getValueAt(row, columnIndex);

            if (value instanceof Number) {
                total += ((Number) value).intValue();
            } else if (value instanceof String) {
                try {
                    total += Integer.parseInt((String) value);
                } catch (NumberFormatException e) {
                }
            }
        }

        return total;
    }

    public float firstPurchaseDiscount(float theTotal){
        float afterDiscount;
        if (!User.preRegister){
            afterDiscount = (theTotal/10);
        }else{
            afterDiscount = 0;
        }

        return afterDiscount;
    }


    public float sameCategoryDiscount(float theTotal, int theElecCount, int theClothCount){
        float afterDiscount2 = 0;
        if(theElecCount>2){
            afterDiscount2 = theTotal/5;
        }
        if(theClothCount>2){
            afterDiscount2 = theTotal/5;
        }

        return afterDiscount2;
    }

}
