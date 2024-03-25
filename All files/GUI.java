import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GUI {
    private JFrame mainFrame = new JFrame("Westminster Shopping Centre");  //Main Frame for the shopping center
    private  JPanel topPanel = new JPanel(new BorderLayout()); //All the components displayed in the top part of the main frame
    private  JPanel middlePanel = new JPanel(new BorderLayout()); //All the middle displayed components
    private  JPanel bottomPanel = new JPanel(new BorderLayout()); //All the bottom displayed components
    protected WestminsterShoppingManager instance;
    protected HashMap<String, Product > GUIproductList = new HashMap<>(); //Hashmap used to intake all the available products from the WestminsterShoppingManager class
    protected ArrayList<String> addedProducts = new ArrayList<>(); //ArrayList used to store necessary products for the shopping cart GUI
    private JTable item_table;


    public GUI(WestminsterShoppingManager instance) {   //Most of the components in the GUI is called from this constructor
        this.instance = instance.getInstance();
        GUItransferData();
        product_category(); // left top corner panel
        shoppingCart_button(); // Right top corner panel
        itemsTable();

        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        mainFrame.add(topPanel, BorderLayout.NORTH);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(50,10,10,10));
        mainFrame.add(middlePanel, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setPreferredSize(new Dimension(middlePanel.getPreferredSize().width, 200));
        mainFrame.setVisible(true);
        mainFrame.setMinimumSize(new Dimension(650, 500));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public GUI(){}  //Another constructor used to initialize this class from other classes

    public void product_category() {   //Combo box for the selection of the category

        JPanel pc = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JTextArea selectText = new JTextArea("Select Product Category");
        JComboBox dropCategory = new JComboBox(new String[]{"All", "Electronics", "Clothing"});
        dropCategory.setPreferredSize(new Dimension(200, 30));

        dropCategory.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String selectedCategory = (String) dropCategory.getSelectedItem();
                    updateTable(selectedCategory);
                }
            }
        });

        pc.add(selectText);
        pc.add(dropCategory);
        topPanel.add(pc, BorderLayout.WEST);
    }

    public void shoppingCart_button() {    //Button used to call the shopping cart GUI (Added items)
        JPanel sb = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cartButton = new JButton("Shopping Cart");
        sb.setPreferredSize(new Dimension(135, 40));
        cartButton.setPreferredSize(new Dimension(135, 40));

        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI2 displayGUI2 = new GUI2(instance,GUIproductList,addedProducts);
                displayGUI2.displayGUI2();
            }
        });

        sb.add(cartButton);
        topPanel.add(sb, BorderLayout.EAST);
    }

    public void itemsTable() {      //the Table used to display the available items
        JScrollPane itPane = new JScrollPane();
        ItemTableModel table = new ItemTableModel(instance);
        item_table = new JTable(table);
        itPane.setViewportView(item_table);
        DefaultTableCellRenderer alignCenter = new DefaultTableCellRenderer();
        alignCenter.setHorizontalAlignment(JLabel.CENTER);

        itPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        JTableHeader header = item_table.getTableHeader();
        header.setBorder(BorderFactory.createLineBorder(Color.black,1));
        item_table.setBorder(BorderFactory.createLineBorder(Color.black,1));
        middlePanel.add(itPane);


        item_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()){
                        return;
                    }
                    ListSelectionModel selection = (ListSelectionModel) e.getSource();
                    if (selection.isSelectionEmpty()){
                        System.out.println("Empty");
                    } else {
                        int selectedRow2 = selection.getMinSelectionIndex();
                        Object passProductID = item_table.getValueAt(selectedRow2,0);
                        Object passCategory = item_table.getValueAt(selectedRow2,2);
                        Object passName = item_table.getValueAt(selectedRow2,1);
                        selectedItem(passProductID,passCategory,passName);
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    }
                }
        });

    }

    public void selectedItem(Object rowPI, Object rowCat, Object rowName){      //The details of the selected item are displayed
        JPanel SIpanel = new JPanel();
        SIpanel.setPreferredSize(new Dimension(100,300));
        JPanel SIpanel2 = new JPanel();
        SIpanel2.setPreferredSize(new Dimension(SIpanel.getPreferredSize()));
        SIpanel.setLayout(new BoxLayout(SIpanel, BoxLayout.Y_AXIS));
        SIpanel2.setLayout(new GridLayout(7,2,-30,10));
        Font fontStyle = new Font("Courier", Font.BOLD,12);
        JLabel detailsHeadline = new JLabel("Selected Product - Details");
        detailsHeadline.setFont(fontStyle);
        JLabel productID = new JLabel("Product ID: " + rowPI);
        JLabel category = new JLabel("Category: " + rowCat);
        JLabel name = new JLabel("Name: "+ rowName);

        if (String.valueOf(rowPI).substring(0,1).equals("A")){
            Electronics getElectronic = (Electronics) GUIproductList.get(rowPI);
            String gBrand = getElectronic.getBrand();
            int gWarranty = getElectronic.getWarrantyPeriod();
            int gItemsLeft = getElectronic.getQuantityLeft();

            JLabel warranty = new JLabel("Brand: " + gBrand);
            JLabel brand = new JLabel("Warranty: " + gWarranty+ " weeks");
            JLabel itemsLeft = new JLabel("itemsLeft: " + gItemsLeft);

            SIpanel2.add(detailsHeadline);
            SIpanel2.add(productID);
            SIpanel2.add(category);
            SIpanel2.add(name);
            SIpanel2.add(warranty);
            SIpanel2.add(brand);
            SIpanel2.add(itemsLeft);

        }else if(String.valueOf(rowPI).substring(0,1).equals("B")){
            Clothing getClothing = (Clothing) GUIproductList.get(rowPI);
            String gSize = getClothing.getSize();
            String gColour = getClothing.getColour();
            int gItemsLeft = getClothing.getQuantityLeft();
            JLabel warranty = new JLabel("Size: " + gSize );
            JLabel brand = new JLabel("Colour: " + gColour);
            JLabel itemsLeft = new JLabel("itemsLeft: " + gItemsLeft);

            SIpanel2.add(detailsHeadline);
            SIpanel2.add(productID);
            SIpanel2.add(category);
            SIpanel2.add(name);
            SIpanel2.add(warranty);
            SIpanel2.add(brand);
            SIpanel2.add(itemsLeft);
        }

        JPanel ABpanel = new JPanel();
        JButton addCart = new JButton(" Add to Shopping Cart ");
        addCart.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
        addCart.setBorder(BorderFactory.createLineBorder(Color.black,1));
        ABpanel.add(addCart);

        addCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI2transferProductID((String) rowPI);

            }
        });

        SIpanel2.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
        SIpanel.add(SIpanel2);
        SIpanel.add(ABpanel);
        SIpanel.setBorder(BorderFactory.createLineBorder(Color.black,1));
        bottomPanel.removeAll();
        bottomPanel.add(SIpanel);
        bottomPanel.revalidate();
        bottomPanel.repaint();
        bottomPanel.add(SIpanel);

    }

    public void GUI2transferProductID(String addProductID){
        addedProducts.add(addProductID);
    }


    public void GUItransferData(){      //Data from Hashmap present in the WestminsterShoppingManager are transferred
        for (Map.Entry<String,Product> entry : instance.getProductList().entrySet()){
            String key = entry.getKey();
            Product value = entry.getValue();
            GUIproductList.put(key,value);
        }
    }

    public void updateTable(String selectedCategory) {
        ItemTableModel tableModel = (ItemTableModel) item_table.getModel();
        tableModel.setCategoryFilter(selectedCategory);
        tableModel.fireTableDataChanged();
    }

}
